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
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import net.Client;
import net.ConnectionListener;
import net.UnicastServer;
import sys.Config;

/**
 *
 * @author rafiul islam
 */
public class GroupSetupActivity extends AbstractIndex{

    private JRadioButton optionServer, optionClient;
    private JTextField ipInputField, portInputField, usernameInputField;
    
    public GroupSetupActivity(){
        super();
        
    }
    
    @Override
    public void setContent() {
        setupOptions();
        setupIPForm();
        setupPortForm();
        setupNameForm();
        setupRequest();
    }
    
    private void setupOptions(){
        optionServer = new JRadioButton("Start a group chat");
        optionServer.setBounds(50, 10, 200, 50);
        optionServer.setFont(font);
        optionServer.setBackground(Color.LIGHT_GRAY);
        
        optionClient = new JRadioButton("Join a group chat");
        optionClient.setFont(font);
        optionClient.setBackground(Color.LIGHT_GRAY);
        optionClient.setBounds(50, 80, 200, 50);
        
        /**
         * Join the radio buttons for one selection at a time.
        */
        ButtonGroup group = new ButtonGroup();
        group.add(optionServer);
        group.add(optionClient);
        
        addOnContent(optionServer);
        addOnContent(optionClient);
        
        optionServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ipInputField.setEnabled(false);
            }
        });
        optionClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ipInputField.setEnabled(true);
            }
        });
    }
    
    private void setupIPForm(){
        JLabel ipLabel = new JLabel("IP");
        ipLabel.setFont(font);
        ipLabel.setBounds(20, 200, 80, 40);
  
        ipInputField = new JTextField("localhost");
        ipInputField.setBounds(105, 200, 180, 40);
        ipInputField.setFont(font);
        
        addOnContent(ipLabel);
        addOnContent(ipInputField);
    }
    
    private void setupPortForm(){
        JLabel portLabel = new JLabel("PORT");
        portLabel.setFont(font);
        portLabel.setBounds(20, 250, 100, 40);
        
        portInputField = new JTextField();
        portInputField.setText("9876");
        portInputField.setBounds(105, 250, 60, 40);
        portInputField.setFont(font);
        
        addOnContent(portLabel);
        addOnContent(portInputField);
    }
    
    private void setupNameForm(){
        JLabel nameLabel = new JLabel("Name");
        nameLabel.setFont(font);
        nameLabel.setBounds(20,300,100,40);
        
        usernameInputField = new JTextField("annonymous");
        usernameInputField.setBounds(105, 300, 200, 30);
        usernameInputField.setFont(font);
        
        addOnContent(usernameInputField);
        addOnContent(nameLabel);
    }
    
    private void setupRequest(){
        JButton requst = new JButton("Request");
        requst.setFont(font);
        requst.setBounds(100, 350, 150, 40);
        contentPanel.add(requst);
        
        requst.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(optionClient.isSelected()){
                   clientRequest();
                }
                else{
                    // call UDP Server
                }
            }
        });
    }    
    
    private void clientRequest(){
        String host = ipInputField.getText();
        int port = Integer.parseInt(portInputField.getText());
        String username = usernameInputField.getText();
        Client client = new Client(host, port);
        client.connect(new ConnectionListener() {
            @Override
            public void onSuccess(Socket socket) {
                Config.setSocket(socket);
                Config.setClientName(username);
                // Switch Activity
//                App.switchContent(new TCPChat());
            }

            @Override
            public void onFailed(IOException exception) {
                JOptionPane.showMessageDialog(null, exception.toString());
            }
        });
    }
    
    private void serverRequest(){
        int port = Integer.parseInt(portInputField.getText());
        String username = usernameInputField.getText();
        UnicastServer server = new UnicastServer(port);
        server.connect(new ConnectionListener() {
            @Override
            public void onSuccess(Socket socket) {
                Config.setSocket(socket);
                Config.setClientName(username);
                // switch Activity
//                App.switchContent(new TCPChat());
            }

            @Override
            public void onFailed(IOException exception) {
                JOptionPane.showMessageDialog(null, exception.toString());
            }
        });
    }
}
