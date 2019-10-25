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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author rafiul islam
 */
public class WelcomeActivity extends AbstractIndex{
    
    private JButton btnSingle, btnGroup, btnAndroid;
    
    @Override
    void setContent() {        
        setupSingleButton();
        setupGroupButton();
        setupAndroidButton();
    }
    
    private void setupSingleButton(){
        btnSingle = new JButton();
        btnSingle.setText("Single Chat");
        btnSingle.setBounds(100, 150, 150, 40);
        btnSingle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                App.switchContent(new TCPSetupActivity());
            }
        });
        contentPanel.add(btnSingle);
    }
    
    private void setupGroupButton(){
        btnGroup = new JButton();
        btnGroup.setText("Group Chat");
        btnGroup.setBounds(100, 200, 150, 40);
        btnGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                App.switchContent(new UDPSetupActivity());
            }
        });
        contentPanel.add(btnGroup);
    }
    
    private void setupAndroidButton(){
        btnAndroid = new JButton();
        btnAndroid.setText("Android");
        btnAndroid.setBounds(100, 250, 150, 40);
        btnAndroid.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                androidConnectOptions();
            }
        });
        contentPanel.add(btnAndroid);
    }
    
}
