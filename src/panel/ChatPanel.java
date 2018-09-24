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

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author rafiul islam
 */
public class ChatPanel extends JPanel{
    /**
     * Chat Panel will provide a I/O environment for messaging.
     * 
     */
    private JFrame frame;
    private Container pane;
    
    private JTextArea messageBox;
    private JTextArea writer;
    
    private Socket socket;
    private String name;
    private DataInputStream dis;
    private DataOutputStream dos;
    
    public ChatPanel(JFrame frame, Socket socket, String name){
        this.frame = frame;
        this.socket = socket;
        this.name = name;
        
        try{
            dos = new DataOutputStream(socket.getOutputStream());
        } catch(IOException ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        
        setBackground(Color.DARK_GRAY);
        setLayout(null);
        setBounds(0, 0, 350, 600);
        setContent();
        
        pane = frame.getContentPane();
        pane.removeAll();
        pane.setLayout(null);
        pane.add(this);
        
        JScrollPane scrollBox = new JScrollPane(messageBox);
        scrollBox.setBounds(5, 5, 335, 400);
        scrollBox.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        pane.add(scrollBox);
       
        
        new Thread(new Runnable(){
            public void run(){
                try{
                    dis = new DataInputStream(socket.getInputStream());
                    while(!socket.isClosed()){
                        messageBox.append("\n"+dis.readUTF());
                    }
                } catch(IOException ex){
                    JOptionPane.showMessageDialog(null, ex);
                }
            }
        }).start();
        
        pane.repaint();
        frame.revalidate();
        frame.repaint();
    }
    private void setContent(){
        Font font = new Font("arial",Font.BOLD,15);
        messageBox = new JTextArea();
        messageBox.setWrapStyleWord(true);
        messageBox.setLineWrap(true);
        messageBox.setBounds(5, 5, 335, 400);
        messageBox.setFont(font);
        messageBox.setEditable(false);
        messageBox.setText("Connected : "+socket.getRemoteSocketAddress());
        
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
                        dos.writeUTF(name+": "+message);
                        messageBox.append("\nYou: "+message);
                        writer.setText("");
                    } catch(IOException ex){
                        JOptionPane.showMessageDialog(null, ex);
                    }
                }
            }
        });
        add(writer); 
    } 
}
