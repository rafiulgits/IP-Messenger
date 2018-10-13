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
    private int index;
    
    public MultiClientHandle(Socket socket, int index){
        this.socket = socket;
        this.index = index;
        
        //intialize the socket reader 
        try{
            dis = new DataInputStream(socket.getInputStream());
        } catch(IOException ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        
        // initialize the this client thread and invoke
        thread = new Thread(this);
        thread.start();
    }
    /**
     * @param running flag for thread looping
     */
    private boolean running = true;
    
    @Override
    public void run() {
        String messString;
        while(! socket.isClosed() && running){   
            try{
                // read a message from connected client
                messString = dis.readUTF();
                // send this message to others
                sendMessage(messString);
            } catch(IOException ex){
                running = false;
                Opening.list.remove(index);
                sendMessage("SERVER: "+socket.getInetAddress()+" removed");
            }
        }
    }
    private void sendMessage(String message){
        for(Socket reciver : Opening.list){
            // ignore to write self
            if(reciver == socket)
                continue;
            
            try{
                new DataOutputStream(reciver.getOutputStream()).writeUTF(message);
            } catch(IOException ex){
                JOptionPane.showMessageDialog(null, ex);
            } finally{
                continue;
            }
        }
    }
}
