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
public class Group extends BaseActivity{

    private Font font;
    private JRadioButton optionServer, optionClient;
    JTextField ipInputField, portInputField, usernameInputField;
    
    public Group(){
        super();
        font = new Font("arial",Font.BOLD, 15);
    }
    
    @Override
    public void setupComponent() {
        setupOptions();
        setupForm();
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
        
        /**
         * add these radio buttons on many-many panel.
         */
        this.add(optionServer);
        this.add(optionClient);
        
        optionServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**
                 * For create a server socket no IP needed, so disable it.
                 */
                ipInputField.setEnabled(false);
            }
        });
        optionClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**
                 * To create a socket an IP needed, so enable this field.
                 */
                ipInputField.setEnabled(true);
            }
        });
    }
    
    private void setupForm(){
        /**
         * Label for IP, port and username edit box.
         */
        JLabel ipLabel = new JLabel("IP");
        ipLabel.setFont(font);
        ipLabel.setBounds(20, 200, 80, 40);
        this.add(ipLabel);
        
        JLabel portLabel = new JLabel("PORT");
        portLabel.setFont(font);
        portLabel.setBounds(20, 250, 100, 40);
        this.add(portLabel);
        
        JLabel nameLabel = new JLabel("Name");
        nameLabel.setFont(font);
        nameLabel.setBounds(20,300,100,40);
        this.add(nameLabel);
        
        /**
         * Edit box for input IP, port and username.
         */
        ipInputField = new JTextField("localhost");
        ipInputField.setBounds(105, 200, 180, 40);
        ipInputField.setFont(font);
        this.add(ipInputField);
        
        portInputField = new JTextField();
        portInputField.setText("9876");
        portInputField.setBounds(105, 250, 60, 40);
        portInputField.setFont(font);
        this.add(portInputField);
        
        usernameInputField = new JTextField("annonymous");
        usernameInputField.setBounds(105, 300, 200, 30);
        usernameInputField.setFont(font);
        this.add(usernameInputField);
    }
    
    private void setupRequest(){
        JButton requst = new JButton("Request");
        requst.setFont(font);
        requst.setBounds(100, 350, 150, 40);
        this.add(requst);
        
        requst.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(optionClient.isSelected()){
                    // call UDP Client
                }
                else{
                    // call UDP Server
                }
            }
        });
    }    
}
