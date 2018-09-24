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
package driver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author rafiul islam
 */
public class MultiClientHandle implements Runnable{

    private Socket socket;
    private Thread thread;
    private DataInputStream dis;
    public MultiClientHandle(Socket socket){
        this.socket = socket;
        try{
            dis = new DataInputStream(socket.getInputStream());
        } catch(IOException ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        thread = new Thread(this);
        thread.start();
    }
    @Override
    public void run() {
        while(! socket.isClosed()){
            String messString;
            try{
                messString = dis.readUTF();
                for(int i=0; i<Opening.list.size(); i++){
                    if(Opening.list.get(i) == socket) continue;
                    if(! Opening.list.get(i).isClosed()){
                        try{
                            new DataOutputStream(Opening.list.get(i).getOutputStream()).
                                    writeUTF(messString);
                        }catch(IOException ex){
                            JOptionPane.showMessageDialog(null, ex);
                            Opening.list.remove(i);
                            continue;
                        }
                    }
                }
            } catch(IOException ex){
                JOptionPane.showMessageDialog(null, ex);;
            }
        }
    }
    
}
