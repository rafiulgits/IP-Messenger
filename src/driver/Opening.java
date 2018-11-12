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

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import panel.ChatPanel;

/**
 *
 * @author rafiul islam
 */
public class Opening extends JPanel{
    /**
     * Opening class with display messenger content for UI interaction.
     * Opening panel will contain some more JPanel for display various contents
     * 
     */
    private JFrame frame; // main window fram
    private JPanel contentPanel; // content display panel
    
    public static volatile ArrayList<Socket> list;//group chat socket list
    
    public Opening(JFrame frame){
        this.frame = frame;
        
        setBackground(Color.DARK_GRAY); // set main panel background
        setLayout(null); // set main panel layout
        setTitle(); // set title content on main panel
        setFooter(); // set footer content on main panel
        setContentPanel(); // set a new panel whose will contain other stuffs
    }
    
    private void setContentPanel(){
        /**
         * This panel will carry buttons and edit box for user interaction with messenger.
         * First this panel will added two button for choice whether user want to 
         * established one-one messenger service or many-many more precisely group chat
         */
        contentPanel = new JPanel(); 
        contentPanel.setBounds(0, 100, 350, 420);
        contentPanel.setBackground(Color.LIGHT_GRAY);
        contentPanel.setLayout(null);
        add(contentPanel);
        setOptions();
    }
    
    private void setTitle(){
        /**
         * Set the messenger title on content page header
         */
        ImageIcon titleIc = new ImageIcon(getClass().getResource("/icon/title.png"));
        JLabel title = new JLabel(titleIc);
        title.setBounds(50, 10, 250, 80);
        add(title);
    }
    
    private void setFooter(){
        /**
         * set the developer name as the footer of the content panel
         */
        ImageIcon footerIc = new ImageIcon(getClass().getResource("/icon/copywriter.png"));
        JLabel footer = new JLabel(footerIc);
        footer.setBounds(25, 500, 280, 80);
        add(footer);
    }
    
    private void setOptions(){
        /**
         * :::::: content stuffs ::::::::
         */
        /**
         * Create a button on content panel for one - one connection choose.
         */
        JButton oneToOne = new JButton();
        oneToOne.setText("Single Chat");
        oneToOne.setBounds(100, 150, 150, 40);
        oneToOne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                singleChatOptions();
            }
        });
        contentPanel.add(oneToOne);
        
        /**
         * Create a button on content panel for many-many connection choose.
        */
        JButton manyToMany = new JButton();
        manyToMany.setText("Group Chat");
        manyToMany.setBounds(100, 200, 150, 40);
        manyToMany.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                groupChatOptions();
            }
        });
        contentPanel.add(manyToMany);
    }
    
    private void singleChatOptions(){
        /**
         * This method will create all stuff for an one-one connection. 
         */
        
        /**
         * create a new panel that will show the one-one connection stuffs.
         */
        JPanel o2oPanel = new JPanel();
        o2oPanel.setLayout(null);
        o2oPanel.setBackground(Color.LIGHT_GRAY);
        o2oPanel.setBounds(0, 100, 350, 420);
        
        /**
         * This font will display on every label
         */
        Font font = new Font("arial",Font.BOLD,15);
        
        /**
         * Two radio button for client , server chooser.
         * A user can create a server or can request to another server to
         * establish a socket connection.
         */
        JRadioButton opServer = new JRadioButton("Create a new chat box");
        opServer.setBounds(50, 10, 200, 50);
        opServer.setFont(font);
        opServer.setBackground(Color.LIGHT_GRAY);
        JRadioButton opClient = new JRadioButton("Request to a chat box");
        opClient.setBackground(Color.LIGHT_GRAY);
        opClient.setFont(font);
        opClient.setBounds(50, 80, 200, 50);
        
        /**
         * Group this two button as for user can only select one option at a time.
        */
        ButtonGroup group = new ButtonGroup();
        group.add(opServer);
        group.add(opClient);
        
        /**
         * add this two radio button on one-one panel.
        */
        o2oPanel.add(opServer);
        o2oPanel.add(opClient);
        
        /**
         * Label for IP , port and username text box.
        */
        JLabel ipLabel = new JLabel("IP");
        ipLabel.setFont(font);
        ipLabel.setBounds(20, 200, 80, 40);
        o2oPanel.add(ipLabel);
        
        JLabel portLabel = new JLabel("PORT");
        portLabel.setFont(font);
        portLabel.setBounds(20, 250, 100, 40);
        o2oPanel.add(portLabel);
        
        JLabel nameLabel = new JLabel("Name");
        nameLabel.setFont(font);
        nameLabel.setBounds(20,300,100,40);
        o2oPanel.add(nameLabel);
        
        
        /**
         * Text Field for input IP, port and username.
         */
        JTextField ipField = new JTextField();
        ipField.setText("localhost");
        ipField.setBounds(105, 200, 180, 40);
        ipField.setFont(font);
        o2oPanel.add(ipField);
        
        JTextField portField = new JTextField();
        portField.setText("9876");
        portField.setBounds(105, 250, 60, 40);
        portField.setFont(font);
        o2oPanel.add(portField);
        
        JTextField nameField = new JTextField("annonymous");
        nameField.setBounds(105, 300, 200, 30);
        nameField.setFont(font);
        o2oPanel.add(nameField);
        
        /**
         * Set action Listener for radio buttons
         */
        opServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**
                 * For create a server we don't need any IP.
                 * So unable to IP input for server chooser.
                 */
                ipField.setEnabled(false);
            }
        });
        opClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**
                 * For create a socket request there must need a IP.
                 * So enable it.
                */
                ipField.setEnabled(true);
            }
        });
        
        /**
         * This Button will for create a socket request or server socket request.
         */
        JButton requst = new JButton("Request");
        requst.setFont(font);
        requst.setBounds(100, 350, 150, 40);
        requst.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(opClient.isSelected()){
                    try {
                        /**
                         * For request to another chat box there need a socket.Here this socket
                         * will try to make connection with given IP's port and after accepted 
                         * by the sever ChatPanel will call for messaging view.
                         */
                        
                        // check the name 
                        String name = nameField.getText().trim();
                        if(! isAValidName(name))
                            return;
                        
                        int port = Integer.parseInt(portField.getText());
                        Socket socket = new Socket(ipField.getText(), port);
                        new ChatPanel(frame, socket , name, false);
                        
                    } catch (IOException ex) {
                        /**
                         * For any error POP up window will display the error message
                         */
                        JOptionPane.showMessageDialog(null, ex);
                    }
                }
                else if(opServer.isSelected()){
                    try{
                        /**
                         * For create a new chat box there need to create a ServerSocket.
                         * After server socket accept the requested socket ChatPanel will called.
                         */
                        
                        // check the name 
                        String name = nameField.getText().trim();
                        if(! isAValidName(name))
                            return;
                        
                        int port = Integer.parseInt(portField.getText());
                        
                        ServerSocket ss = new ServerSocket(port);
                        Socket socket = ss.accept();
                        new ChatPanel(frame, socket, name, false);
                        
                    } catch(IOException ex){
                        /**
                         * Display the error with a pop up window.
                         */
                        JOptionPane.showMessageDialog(null, ex);
                    }
                }
            }
        });
        
        /**
         * add this one-one panel on main panel after remove the previous one and repaint 
         * the main panel.
         */
        o2oPanel.add(requst);
        
        remove(contentPanel);
        add(o2oPanel);
        
        repaint();
    }
    
    private void groupChatOptions(){
        /**
         * create a new panel for display many-many connection stuffs.
         */
        JPanel m2mPanel = new JPanel();
        m2mPanel.setLayout(null);
        m2mPanel.setBackground(Color.LIGHT_GRAY);
        m2mPanel.setBounds(0, 100, 350, 420);
        
        /**
         * Create a default font for stuffs.
         */
        Font font = new Font("arial",Font.BOLD, 15);
        
        /**
         * Radio button for chooser create.
         */
        JRadioButton opServer = new JRadioButton("Start a group chat");
        opServer.setBounds(50, 10, 200, 50);
        opServer.setFont(font);
        opServer.setBackground(Color.LIGHT_GRAY);
        
        JRadioButton opClient = new JRadioButton("Join a group chat");
        opClient.setFont(font);
        opClient.setBackground(Color.LIGHT_GRAY);
        opClient.setBounds(50, 80, 200, 50);
        
        /**
         * Join the radio buttons for one selection at a time.
        */
        ButtonGroup group = new ButtonGroup();
        group.add(opServer);
        group.add(opClient);
        
        /**
         * add these radio buttons on many-many panel.
         */
        m2mPanel.add(opServer);
        m2mPanel.add(opClient);
        
        /**
         * Label for IP, port and username edit box.
         */
        JLabel ipLabel = new JLabel("IP");
        ipLabel.setFont(font);
        ipLabel.setBounds(20, 200, 80, 40);
        m2mPanel.add(ipLabel);
        
        JLabel portLabel = new JLabel("PORT");
        portLabel.setFont(font);
        portLabel.setBounds(20, 250, 100, 40);
        m2mPanel.add(portLabel);
        
        JLabel nameLabel = new JLabel("Name");
        nameLabel.setFont(font);
        nameLabel.setBounds(20,300,100,40);
        m2mPanel.add(nameLabel);
        
        /**
         * Edit box for input IP, port and username.
         */
        JTextField ipField = new JTextField("localhost");
        ipField.setBounds(105, 200, 180, 40);
        ipField.setFont(font);
        m2mPanel.add(ipField);
        
        JTextField portField = new JTextField();
        portField.setText("9876");
        portField.setBounds(105, 250, 60, 40);
        portField.setFont(font);
        m2mPanel.add(portField);
        
        JTextField nameField = new JTextField("annonymous");
        nameField.setBounds(105, 300, 200, 30);
        nameField.setFont(font);
        m2mPanel.add(nameField);
        
        /**
         * add action on radio buttons.
         */
        opServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**
                 * For create a server socket no IP needed, so disable it.
                 */
                ipField.setEnabled(false);
            }
        });
        opClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**
                 * To create a socket an IP needed, so enable this field.
                 */
                ipField.setEnabled(true);
            }
        });
        
        /**
         * Request button to create a server socket or socket.
         */
        JButton requst = new JButton("Request");
        requst.setFont(font);
        requst.setBounds(100, 350, 150, 40);
        m2mPanel.add(requst);
        requst.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(opClient.isSelected()){
                    /**
                     * To join a server socket just create a socket request to the server
                     * socket.
                     */
                    
                    // check the name 
                    String name = nameField.getText().trim();
                    if(! isAValidName(name))
                        return;

                    int port = Integer.parseInt(portField.getText());
                    try{
                        Socket socket = new Socket(InetAddress.getLocalHost(),port);
                        new ChatPanel(frame, socket, name, true);
                    }catch(IOException ex){
                        JOptionPane.showMessageDialog(null, ex);
                    }
                                    
                }
                else if(opServer.isSelected()){
                    try{
                        /**
                         * For Group messaging there will be many one-one connection through
                         * a single server socket.
                         * 
                         * Server socket will run in a thread a accept all request and add the
                         * request socket in array list.
                         * 
                         * MultiClientHandle class is a runnable class that will invoke for
                         * a new client created on server socket and do I/O operations
                         * with an individual thread.
                         * 
                         * For every new client joint server socket will create a new thread
                         * to communicate.
                         * 
                         * @see MultiClientHandle
                         */
                        
                        // check the name 
                        String name = nameField.getText().trim();
                        if(! isAValidName(name))
                            return;
                        
                        int port = Integer.parseInt(portField.getText());
                        
                        ServerSocket ss = new ServerSocket(port);
                        
                        list = new ArrayList<>(); // socket list initialize
                        Socket fc = ss.accept();
                        list.add(fc);
                        new MultiClientHandle(fc,list.size()-1);
                        /**
                         * When user create a new chat box, user is the second client of this chat
                         * box. This thread will be executed after first client added.
                         * So here a socket will created for the server created user.
                         */
                        new Thread(new Runnable(){
                            public void run(){
                                try{
                                    /**
                                     * As this is a thread so it must created after server socket
                                     * is bound. So 200 millisecond is for server socket bound.
                                     */
                                    Socket socket = new Socket(InetAddress.getLocalHost(),port);
                                    new ChatPanel(frame, socket, name, true);
                                } catch(IOException ex){
                                    JOptionPane.showMessageDialog(null, ex);
                                } 
                            }
                        }).start();
                        
                        new Thread(new Runnable(){
                            private boolean serverRunning = true;
                            public void run(){
                                while(!ss.isClosed() && serverRunning){
                                    try {
                                        /**
                                         * Every time server socket accept a socket request it
                                         * will create a thread for this socket communication,
                                         * and add this into socket list.
                                         */
                                        Socket s = ss.accept();
                                        list.add(s);
                                        new MultiClientHandle(s,list.size()-1);
                                    } catch (IOException ex){
                                        JOptionPane.showMessageDialog(null, ex);
                                        serverRunning = false;
                                    }
                                }
                            }
                        }).start();
                    } catch(IOException ex){
                        JOptionPane.showMessageDialog(null, ex);
                    }
                }
            }
        });
        
        remove(contentPanel);
        add(m2mPanel);
        repaint();
    }
    
    private boolean isAValidName(String name){
        if(name.length() < 4){
            // name at least 4 length
            JOptionPane.showMessageDialog(null, "Name must carry at least 4 char");
            return false;
        }
        if(!Character.isAlphabetic(name.charAt(0))){
            // name must be started with an alphabat
            JOptionPane.showMessageDialog(null, "Name must be started with an alphabet");
            return false;
        }
        return true;
    }
}
