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

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
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
import javax.swing.SwingConstants;
import static panel.FileProgressListener.DONE;

/**
 *
 * @author rafiul islam
 */
public class AndroidPanel extends JPanel{
       
    private JFrame frame;
    private Container pane;
    
    private JButton btFileChooser;
    private JLabel sendingFileLabel,receivingFileLabel;
    private JButton btFileSend, btDisconnect;
    private JProgressBar sendingProgress, receivingProgress;
    
    private JButton btRing, btSilent, btVibrate;

    
    private boolean fileSelected;
    private File file;
    
    private Socket socket;
    private String name;
    private DataOutputStream dos;
    
    public AndroidPanel(JFrame frame, Socket socket){
        
        this.frame = frame;
        this.socket = socket;
        
        setBackground(Color.DARK_GRAY);
        setLayout(null);
        setBounds(0, 0, 350, 600);
        
        //initStreams();
        setFileContent();
        setCommandContent();
        
        pane = frame.getContentPane();
        pane.removeAll();
        pane.setLayout(null);
        pane.add(this);
        pane.repaint();
        
        new AndroidP2P.Receiver(socket, receivingFileLabel) {
            @Override
            public void onFileProgress(int progress) {
                receivingProgress.setValue(progress);
            }
            
            @Override
            public void onFileFinish(int state) {
                if(state == DONE){
                    receivingProgress.setValue(receivingProgress.getMaximum());
                }
            }
        };
    }
 
    private void setFileContent(){
        Font f = new Font("arial", Font.BOLD, 15);
        receivingFileLabel = new JLabel("No File Receiving");
        receivingFileLabel.setBounds(10, 230 ,325 ,50);
        receivingFileLabel.setForeground(Color.LIGHT_GRAY);
        receivingFileLabel.setFont(f);
        receivingFileLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(receivingFileLabel);
        
        receivingProgress = new JProgressBar();
        receivingProgress.setBounds(10, 285, 325, 20);
        receivingProgress.setMinimum(0);
        receivingProgress.setString("Received: "+receivingProgress.getValue()+"%");
        receivingProgress.setStringPainted(true);
        receivingProgress.setVisible(true);
        add(receivingProgress);
        
        sendingFileLabel = new JLabel("No File Selected");
        sendingFileLabel.setBounds(10, 320, 325, 50);
        sendingFileLabel.setLayout(null);
        sendingFileLabel.setFont(f);
        sendingFileLabel.setHorizontalAlignment(SwingConstants.CENTER);
        sendingFileLabel.setBackground(Color.lightGray);
        sendingFileLabel.setForeground(Color.LIGHT_GRAY);
        add(sendingFileLabel);
        
        sendingProgress = new JProgressBar();
        sendingProgress.setBounds(10, 375,325, 20);
        sendingProgress.setMinimum(0);
        sendingProgress.setString("Sending : "+sendingProgress.getValue()+"%");
        sendingProgress.setStringPainted(true);
        sendingProgress.setVisible(true); 
        add(sendingProgress);
        
        btFileChooser = new JButton("Pick A File");
        btFileChooser.setBounds(95, 420, 150, 40);
        btFileChooser.setFont(f);
        btFileChooser.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int status = fileChooser.showOpenDialog(null);
                if(status == JFileChooser.APPROVE_OPTION){
                    file = fileChooser.getSelectedFile();
                    sendingFileLabel.setText(file.getAbsoluteFile().getName());
                    fileSelected = true;
                }
            }
        });
        add(btFileChooser);
        
        fileSelected = false;
        btFileSend = new JButton("Send");
        btFileSend.setFont(f);
        btFileSend.setBounds(95, 470, 150, 40);
        btFileSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileSelected == true){
                    btFileChooser.setEnabled(false);
                    btFileSend.setEnabled(false);
                    
                    new AndroidP2P.Sender(socket, file) {
                        @Override
                        public void onFileProgress(int progress) {
                            sendingProgress.setValue(progress);
                        }
                        
                        @Override
                        public void onFileFinish(int state) {
                            if(state == DONE){
                                sendingProgress.setValue(sendingProgress.getMaximum());
                                fileSelected = false;
                                btFileChooser.setEnabled(true);
                                btFileSend.setEnabled(true);
                            }
                        }
                    };
                }
            }
        });
        add(btFileSend);
        
        btDisconnect = new JButton("Disconnect");
        btDisconnect.setFont(f);
        btDisconnect.setBounds(95,520,150,40);
        btDisconnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    socket.close();
                } catch(IOException ex){
                    
                }
            }
        });
        add(btDisconnect);
    }
    
    private void setCommandContent(){
        try{
            dos = new DataOutputStream(socket.getOutputStream());
        } catch(IOException ex){
            
        }
        
        btRing = new JButton("Mobile in Ring Mode");
        btRing.setBounds(50, 40, 250, 50);
        btRing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileSelected) return;
                try{
                    dos.writeShort(AndroidP2P.RECEIVE_TYPE_COMMAND);
                    dos.writeShort(AndroidP2P.RING_MODE);
                    dos.flush();
                } catch(IOException ex){
                    
                }
            }
        });
        
        btSilent = new JButton("Mobile in Silent Mode");
        btSilent.setBounds(50,100, 250,50);
        btSilent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileSelected) return;
                try{
                    dos.writeShort(AndroidP2P.RECEIVE_TYPE_COMMAND);
                    dos.writeShort(AndroidP2P.SILENT_MODE);
                    dos.flush();
                } catch(IOException ex){
                    
                }
            }
        });
        
        btVibrate = new JButton("Mobile in Vibrate Mode");
        btVibrate.setBounds(50,160,250,50);
        btVibrate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileSelected) return;
                try{
                    dos.writeShort(AndroidP2P.RECEIVE_TYPE_COMMAND);
                    dos.writeShort(AndroidP2P.SILENT_MODE);
                    dos.flush();
                } catch(IOException ex){
                    
                }
            }
        });
        
        
        this.add(btRing);
        this.add(btSilent);
        this.add(btVibrate);
    }
}
