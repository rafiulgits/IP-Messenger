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
import java.awt.Component;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author rafiul islam
 */
public abstract class AbstractIndex extends BaseActivity{
    JPanel contentPanel;
    Font font;
    public AbstractIndex(){
        super();
    }
    
    @Override
    public void setupComponent() {
        contentPanel = new JPanel(); 
        contentPanel.setBounds(0, 100, 350, 420);
        contentPanel.setBackground(Color.LIGHT_GRAY);
        contentPanel.setLayout(null);
        
        font = new Font("arial",Font.BOLD, 15);
        contentPanel.setFont(font);
        
        setBackground(Color.DARK_GRAY);
        setTitle();
        setFooter();
        setContent();
        add(contentPanel);
    }
    
    private void setTitle(){
        ImageIcon titleIc = new ImageIcon(getClass().getResource("/icon/title.png"));
        JLabel title = new JLabel(titleIc);
        title.setBounds(50, 10, 250, 80);
        this.add(title);
    }
    
    private void setFooter(){
        ImageIcon footerIc = new ImageIcon(getClass().getResource("/icon/copywriter.png"));
        JLabel footer = new JLabel(footerIc);
        footer.setBounds(25, 500, 280, 80);
        this.add(footer);
    }
    
    abstract void setContent();
}
