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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author rafiul islam
 */
public class AndroidSetupActivity extends AbstractIndex{
    private JTextField portInputField;
    private JButton requestButton;
    
    @Override
    void setContent() {
        setupIPView();
        setupPortForm();
        setupRequestButton();
    }
    
    
    private void setupIPView(){
        JLabel ipLabel = new JLabel();
        try{
            ipLabel.setText("Your Host: "+InetAddress.getLocalHost().getHostAddress());
        } catch(UnknownHostException ex){
            
        }
        ipLabel.setFont(font);
        ipLabel.setBounds(10, 100, 325, 100);
        ipLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addOnContent(ipLabel);
    }
    
    private void setupPortForm(){
        JLabel portLabel = new JLabel("PORT");
        portLabel.setFont(font);
        portLabel.setBounds(20, 250, 100, 40);
        contentPanel.add(portLabel);
        
        portInputField = new JTextField();
        portInputField.setText("9876");
        portInputField.setBounds(105, 250, 60, 40);
        portInputField.setFont(font);
        addOnContent(portInputField);  
    }
    
    private void setupRequestButton(){
        requestButton = new JButton("Create Server");
        requestButton.setFont(font);
        requestButton.setBounds(100, 350, 150, 40);
        requestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              // request TCP Server connection
            }
        });
        addOnContent(requestButton);
    }
}
