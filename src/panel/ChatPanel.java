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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
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
    
    private JButton btFileChooser;
    private JLabel fileLabel;
    private JButton btFileSend;
    private JProgressBar bar;
    
    private boolean fileSelected;
    private File file;
    
    private Socket socket;
    private String name;
    private DataInputStream dis;
    private DataOutputStream dos;
    
    private boolean isGrouped;
    
    public ChatPanel(JFrame frame, Socket socket, String name, boolean isGrouped){
        this.frame = frame;
        this.socket = socket;
        this.name = name;
        this.isGrouped = isGrouped;
        
        try{
            dos = new DataOutputStream(socket.getOutputStream());
        } catch(IOException ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        
        setBackground(Color.DARK_GRAY);
        setLayout(null);
        setBounds(0, 0, 350, 600);
        setContent();
        setFileContent();
        
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
                    String message;
                    while(!socket.isClosed() || socket != null){
                        message = dis.readUTF();
                        
                        /**
                         * Whenever a message contain 1 that is means that this is 
                         * not a regular message.
                         * 
                         * It also indicate that next incoming message is fileName and
                         * fileSize and using those information a file receiver thread 
                         * started to receive the file from another node.
                         * 
                         */
                        if(message.charAt(0) == '1'){
                            /**
                             * Whenever input stream notify for an upcoming file, first thing
                             * that will come is the host and port name for create another socket
                             * connection file transferring.
                             * 
                             * Then, the file name and file size
                             */
                            String host = dis.readUTF();
                            int port = dis.readInt();
                            
                            String fileName = dis.readUTF();
                            long fileSize = dis.readLong();
                            
                            FileReceiver receiver = new FileReceiver(host, port) {
                                @Override
                                public void onFileProgress(int progress){
                                    bar.setValue(progress);
                                }
                                
                                @Override
                                public void onFileFinish(int state) {
                                    if(state == DONE){
                                        bar.setValue(bar.getMaximum());
                                        bar.setString("Successfully Received");
                                        /**
                                         * File I/O done.
                                        */
                                        btFileChooser.setEnabled(true);
                                        btFileSend.setEnabled(true);
                                    }
                                }
                            };
                            
                            receiver.setInfo(fileName, fileSize);
                            
                            /**
                             * Receiver will wait for a confirmation message from 
                             * sender socket that a server socket is created for 
                             * file transfer.
                             */
                            dis.readBoolean();
                            receiver.start();
                            /**
                             * unable to access file UI components when an operation 
                             * already running.
                            */
                            btFileChooser.setEnabled(false);
                            btFileSend.setEnabled(false);
                            
                            fileLabel.setText(fileName);
                            
                            bar.setString("Receiving");
                            bar.setStringPainted(true);
                            bar.setMaximum(FileProgress.getMax(fileSize));
                            bar.setVisible(true);
                            
                            continue;
                        }
                        
                        messageBox.append("\n"+message);
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
                        // clean the message
                        message = message.replaceAll("\\n", "");
                        // except any empty message
                        if(message.length() == 0){
                            // reset the writer for any empty message
                            writer.setText("");
                            return;
                        }
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
    
    private void setFileContent(){
        if(isGrouped) return;
        
        fileLabel = new JLabel("No File Selected");
        fileLabel.setBounds(120, 505, 200, 20);
        fileLabel.setLayout(null);
        fileLabel.setBackground(Color.lightGray);
        fileLabel.setForeground(Color.LIGHT_GRAY);
        add(fileLabel);
        
        btFileChooser = new JButton("Pick A File");
        btFileChooser.setBounds(5, 505, 100, 20);
        btFileChooser.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int status = fileChooser.showOpenDialog(null);
                if(status == JFileChooser.APPROVE_OPTION){
                    file = fileChooser.getSelectedFile();
                    fileLabel.setText(file.getAbsoluteFile().getName());
                    fileSelected = true;
                }
            }
        });
        add(btFileChooser);
        
        fileSelected = false;
        btFileSend = new JButton("Send");
        btFileSend.setBounds(5, 530, 100, 20);
        btFileSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!fileSelected)
                    return;
                try{
                    /**
                     * Notify remote socket that a file will be sent.
                     * Next sent the host address and port
                     * Next sent the file name and file size
                     */ 
                    
                    // port to connect
                    int port = 49600;
                    
                    dos.writeUTF("1");
                    
                    // send the host and port
                    dos.writeUTF(socket.getInetAddress().getHostAddress());
                    dos.writeInt(port);
                    
                    // send the file name and file length
                    dos.writeUTF(file.getAbsoluteFile().getName());
                    dos.writeLong(file.getAbsoluteFile().length());
                    
                    FileSender fileSender = new FileSender(port) {
                        @Override
                        public void onFileProgress(int progress){
                            bar.setValue(progress);
                        }
                        
                        @Override
                        public void onFileFinish(int state) {
                            if(state == DONE){
                                // I/O done
                                btFileChooser.setEnabled(true);
                                btFileSend.setEnabled(true);
                                bar.setValue(bar.getMaximum());
                                bar.setString("Successfully Sent");
                            }
                        }
                        
                    };
                    fileSender.setInfo(file);
                    fileSender.start();
                    btFileChooser.setEnabled(false);
                    btFileSend.setEnabled(false);

                    /** 
                     * Sent the confirmation to the receiver socket.
                     */
                    dos.writeBoolean(true);
                    
                    bar.setMaximum(FileProgress.getMax(file.length()));
                    bar.setString("Sending");
                    bar.setStringPainted(true);
                    bar.setVisible(true);
                } catch(IOException ex){
                    JOptionPane.showMessageDialog(null, ex);
                    return;
                }
                
            }
        });
        add(btFileSend);
        
        bar = new JProgressBar();
        bar.setBounds(110, 530, 220, 20);
        bar.setMinimum(0);
        bar.setString("Loaded : "+bar.getValue()+"%");
        bar.setStringPainted(true);
        bar.setVisible(false);
        
        add(bar);
    }
}
