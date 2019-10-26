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
import net.ConnectionListener;
import net.TCPClient;
import net.TCPServer;
import sys.Config;

/**
 *
 * @author rafiul islam
 */
public class TCPSetupActivity extends AbstractIndex{
   
    private JTextField ipInputField, portInputField, usernameInputField;
    private JRadioButton optionServer, optionClient;
    
    public TCPSetupActivity(){
        super();
    }
    
    @Override
    public void setContent(){
        setupOptions();
        setupIPForm();
        setupPortForm();
        setupNameForm();
        setupRequest();
    }
    
    private void setupOptions(){
        optionServer = new JRadioButton("Create a new chat box");
        optionServer.setBounds(50, 10, 200, 50);
        optionServer.setFont(font);
        optionServer.setBackground(Color.LIGHT_GRAY);
        
        optionClient = new JRadioButton("Request to a chat box");
        optionClient.setBackground(Color.LIGHT_GRAY);
        optionClient.setFont(font);
        optionClient.setBounds(50, 80, 200, 50);
        
        ButtonGroup group = new ButtonGroup();
        group.add(optionServer);
        group.add(optionClient);
        
        contentPanel.add(optionServer);
        contentPanel.add(optionClient);
        
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
        
        ipInputField = new JTextField();
        ipInputField.setText("localhost");
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
        
        addOnContent(nameLabel);
        addOnContent(usernameInputField);
    }
   
    private void setupRequest(){
        JButton requst = new JButton("Request");
        requst.setFont(font);
        requst.setBounds(100, 350, 150, 40);
        requst.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(optionClient.isSelected()){
                    clientRequest();
                }
                else if(optionServer.isSelected()){
                    serverRequest();
                }
                
            }
        });
    }
    
    private void clientRequest(){
        String host = ipInputField.getText();
        int port = Integer.getInteger(portInputField.getText());
        String username = usernameInputField.getName();
        TCPClient client = new TCPClient(host, port);
        client.connect(new ConnectionListener() {
            @Override
            public void onSuccess(Socket socket) {
                Config.setSocket(socket);
                Config.setClientName(username);
                // Switch Activity
            }

            @Override
            public void onFailed(IOException exception) {
                JOptionPane.showMessageDialog(null, exception.toString());
            }
        });
    }
    
    private void serverRequest(){
        int port = Integer.getInteger(portInputField.getText());
        String username = usernameInputField.getText();
        TCPServer server = new TCPServer(port);
        server.connect(new ConnectionListener() {
            @Override
            public void onSuccess(Socket socket) {
                Config.setSocket(socket);
                Config.setClientName(username);
                // switch Activity
            }

            @Override
            public void onFailed(IOException exception) {
                JOptionPane.showMessageDialog(null, exception.toString());
            }
        });
    }
}
