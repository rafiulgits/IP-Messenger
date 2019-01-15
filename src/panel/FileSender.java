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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JProgressBar;

/**
 *
 * @author rafiul islam
 */
public abstract class FileSender extends Thread implements FileProgressListener{
    
    private JProgressBar bar;
    private int port;
    private File file;
    private Socket socket;
    
    private FileProgressListener fileListener = this;
    
    public FileSender(int port){
        this.port = port;
        this.bar = bar;
    }
    
    public void setInfo(File file){
        this.file = file;
    }
    
    
    @Override
    public void run(){
        try{
            ServerSocket ss = new ServerSocket(port);
            socket = ss.accept();
            
            BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
            FileInputStream fis = new FileInputStream(file);
            
            byte[] kb_64 = new byte[1024*64];
            int len; long sent = 0;
            while((len = fis.read(kb_64)) > 0){
                bos.write(kb_64, 0, len);
                sent += len;
                fileListener.onFileProgress(FileProgress.getProgress(file.length(), sent));
             }
            
            fileListener.onFileFinish(DONE);
            
            // close the file input stream for the file
            fis.close();
            
            // flush and close the buffered output stream for socket
            bos.flush();
            bos.close();
            
            // close the socket and server socket
            socket.close();
            ss.close();
            
            
        } catch(IOException ex){
            Logger.getLogger("Sender Thread: ").log(Level.SEVERE, ex.toString());
        }
        Logger.getLogger("Sender Thread: ").log(Level.SEVERE, "socket thread end");
    }
}
