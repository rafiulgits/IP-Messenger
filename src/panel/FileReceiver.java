/*
 * Copyright 2018 rafiul islam.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package panel;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JProgressBar;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author rafiul islam
 */
public abstract class FileReceiver extends Thread implements FileProgressListener{
    
    private String host;
    private int port;
    private Socket socket;
    private File file;
    private String fileName;
    private long fileSize;
    private JProgressBar bar;
    
    private FileProgressListener fileListener = this;
    
    public FileReceiver(String host, int port){
        this.host = host;
        this.port = port;
        this.bar = bar;
    }
    
    
    
    public void setInfo(String fileName, long fileSize){
        this.fileName = fileName;
        this.fileSize = fileSize;
    }
    
    
    @Override 
    public void run(){
        try{
            String docDir = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
            file = new File(docDir, "IP_Messenger");
            if(! file.exists()){
                file.mkdir();
            }
            socket = new Socket(host, port);
            
            BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
            file = new File(file.getAbsolutePath()+"\\"+fileName);
            FileOutputStream fos = new FileOutputStream(file);
            byte[] kb_64 = new byte[1024*64];
            int len; long received = 0;
            while((len = bis.read(kb_64)) > 0){
                fos.write(kb_64, 0, len);
                received += len;
                fileListener.onFileProgress(FileProgress.getProgress(fileSize, received));
                if(len == 0 || received == fileSize){
                    // flush and close the file output stream for file
                    fos.flush();
                    fos.close();
                    break;
                }
            }
            
            fileListener.onFileFinish(DONE);
            
            // close the buffered input stream for the socket
            bis.close();
            
            // close the socket
            socket.close();
            
        } catch(IOException ex){
            Logger.getLogger("Receiver Thread: ").log(Level.SEVERE, ex.toString());
        }
        
        Logger.getLogger("Receiver Thread: ").log(Level.SEVERE, "socket thread end");
    }
}
