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
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author rafiul islam
 */
public class TCPSetupActivity extends AbstractIndex{
    
    private Font font;
    private JTextField ipInputField, portInputField, usernameInputField;
    private JRadioButton optionServer, optionClient;
    
    public TCPSetupActivity(){
        super();
    }
    
    @Override
    public void setContent(){
        font = new Font("arial",Font.BOLD,15);
        contentPanel.setFont(font);
        setupOptions();
        setupForm();
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
    
    private void setupForm(){
        /**
         * Text Field for input IP, port and username.
         */
        JLabel ipLabel = new JLabel("IP");
        ipLabel.setFont(font);
        ipLabel.setBounds(20, 200, 80, 40);
        contentPanel.add(ipLabel);
        
        JLabel portLabel = new JLabel("PORT");
        portLabel.setFont(font);
        portLabel.setBounds(20, 250, 100, 40);
        contentPanel.add(portLabel);
        
        JLabel nameLabel = new JLabel("Name");
        nameLabel.setFont(font);
        nameLabel.setBounds(20,300,100,40);
        contentPanel.add(nameLabel);
        
        ipInputField = new JTextField();
        ipInputField.setText("localhost");
        ipInputField.setBounds(105, 200, 180, 40);
        ipInputField.setFont(font);
        contentPanel.add(ipInputField);
        
        portInputField = new JTextField();
        portInputField.setText("9876");
        portInputField.setBounds(105, 250, 60, 40);
        portInputField.setFont(font);
        contentPanel.add(portInputField);
        
        usernameInputField = new JTextField("annonymous");
        usernameInputField.setBounds(105, 300, 200, 30);
        usernameInputField.setFont(font);
        contentPanel.add(usernameInputField);
    }
    
    private void setupRequest(){
        JButton requst = new JButton("Request");
        requst.setFont(font);
        requst.setBounds(100, 350, 150, 40);
        requst.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                 set request
//                new ChatPanel(frame, socket, name, false);
            }
        });
    }

}
