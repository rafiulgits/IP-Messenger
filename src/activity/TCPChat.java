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
package activity;

import driver.App;
import io.BroadcasterReceiver;
import io.Handler;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import net.MessageReceiver;
import sys.Config;
import sys.Executor;

/**
 *
 * @author rafiul islam
 */
public class TCPChat extends BaseActivity implements BroadcasterReceiver{

    private Font font;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private JTextArea messageBox, writer;
    
    public TCPChat() {
        setupStreams();
    }
    
    
    private void setupStreams(){
        try{
            dataOutputStream = new DataOutputStream(Config.getSocket().getOutputStream());
            Handler handler = new Handler(this);
            MessageReceiver messageReceiver = new MessageReceiver(handler);
            Executor.execute(messageReceiver);
        } catch(IOException ex){
            JOptionPane.showMessageDialog(null, ex.toString());
            App.stop();
        }
    }
    
    
    @Override
    public void onReceive(int code, Object data){
        if(code == MessageReceiver.MESSAGE_RECEIVE){
            messageBox.append("\n"+(String)data);
            return;
        }
        if(code == MessageReceiver.CONNECTION_FAILED){
           JOptionPane.showMessageDialog(null, data.toString());
           App.stop();
        }
    }
    
    @Override
    public void setupComponent() {
        font = new Font("arial",Font.BOLD,15);
        setupMessageBox();
        setupWriter();
        setupDisconnector();
    }   
    
    private void setupMessageBox(){
        messageBox = new JTextArea();
        messageBox.setWrapStyleWord(true);
        messageBox.setLineWrap(true);
        messageBox.setBounds(5, 5, 335, 400);
        messageBox.setFont(font);
        messageBox.setEditable(false);
        messageBox.setText("Connected : "+Config.getSocket().getRemoteSocketAddress());
        
        JScrollPane scrollBox = new JScrollPane(messageBox);
        scrollBox.setBounds(5, 5, 335, 400);
        scrollBox.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.add(scrollBox);
    }

    private void setupWriter(){
        writer = new JTextArea();
        writer.setWrapStyleWord(true);
        writer.setLineWrap(true);
        writer.setBounds(5, 420, 335, 80);
        writer.setFont(font);
        writer.setEditable(true);
        writer.setText("Say Hello");
        writer.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    try{
                        String message = writer.getText();
                        message = message.replaceAll("\\n", "");
                        if(message.length() == 0){
                            // reset the writer for any empty message
                            writer.setText("");
                            return;
                        }
                        dataOutputStream.writeUTF(Config.getClientName()+": "+message);
                        messageBox.append("\nYou: "+message);
                        writer.setText("");
                    } catch(IOException ex){
                        JOptionPane.showMessageDialog(null, ex);
                    }
                }
            }
        });
        this.add(writer);
    }
    
    private void setupDisconnector(){
        JButton btDisconnect = new JButton("Disconnect");
        btDisconnect.setFont(font);
        btDisconnect.setBounds(100, 510, 150, 40);
        btDisconnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Config.getSocket().close();
                    App.stop();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.toString());
                    App.stop();
                }
            }
        });
        this.add(btDisconnect);
    }
}
