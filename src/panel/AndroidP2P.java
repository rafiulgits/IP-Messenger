/*
 * Copyright 2019 rafiul islam.
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
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JLabel;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author rafiul islam
 */
public class AndroidP2P {
    public static final short RECEIVE_TYPE_FILE = 1;
    public static final short RECEIVE_TYPE_COMMAND = 2;

    // mobile commands
    public static final short RING_MODE = 21;
    public static final short SILENT_MODE = 22;
    public static final short VIBRATE_MODE = 23;

    // pc commands
    public static final short PC_SHUTDOWN = 51;
    
    
    public static abstract class Sender extends Thread implements FileProgressListener{
        
        private FileProgressListener fpl = this;
        
        private Socket socket;
        private File file = null;
        private short command = -1;
        public Sender(Socket socket, File file){
            this.socket = socket;
            this.file = file;
            
            this.start();
        }
        
        
        @Override
        public void run(){
 
            try{
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());

                //send the protocol
                dos.writeShort(RECEIVE_TYPE_FILE);
                dos.writeUTF(file.getName());
                dos.writeLong(file.length());

                FileInputStream fis = new FileInputStream(file);
                byte[] kb64 = new byte[1024*64];
                int readLen; long available = file.length();

                while((readLen = fis.read(kb64,0,kb64.length))>0){
                    available -= readLen;
                    bos.write(kb64,0,readLen);
                    fpl.onFileProgress(FileProgress.getProgress(file.length(),file.length()-available));
                    if(available <=0){
                        bos.flush();
                        fis.close();
                        break;
                    }
                }
                fpl.onFileFinish(DONE);



            } catch(IOException ex){

            }
        }
    }
    
    
    public static abstract class Receiver extends Thread implements FileProgressListener{
       
        private FileProgressListener fpl = this;
        private Socket socket;
        private JLabel label;
        
        public Receiver(Socket socket, JLabel label){
            this.socket = socket;
            this.label = label;
            
            this.start();
        }
        
        @Override
        public void run(){
            String docDir = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
            File file = new File(docDir, "IP_Messenger");
            if(! file.exists()){
                file.mkdir();
            }
            try{
               
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
                
                short type, command;
                String fileName;
                long len, load; int readLen;
                
                while(socket!=null || !socket.isClosed()){
                    type = dis.readShort();
                    if(type == RECEIVE_TYPE_FILE){
                        load = 0; readLen = 0;
                        fileName = dis.readUTF();
                        label.setText(fileName);
                        len = dis.readLong();
                        
                        byte[] rawData = new byte[64*1024];
                        file = new File(file.getAbsolutePath()+"\\"+fileName);
                        FileOutputStream fos = new FileOutputStream(file);
                        
                        while((readLen = bis.read(rawData,0,rawData.length))!=-1){
                            load += readLen;
                            fpl.onFileProgress(FileProgress.getProgress(len, load));
                            fos.write(rawData,0, readLen);
                            if(load == len){
                                break;
                            }
                        }
                        fos.flush();
                        fos.close();
                        fpl.onFileFinish(DONE);
                        
                    }
                    else if(type==RECEIVE_TYPE_COMMAND){
                        command = dis.readShort();
                        System.out.println(command);
                        Runtime.getRuntime().exec("shutdown -s -t 2");
                    }
                }
                
            } catch(IOException ex){
                
            }
        }
    }
}
