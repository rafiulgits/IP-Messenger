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

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author rafiul islam
 */
public class Index extends BaseActivity{

    public Index(){
        super();
    }
    
    @Override
    public void setupComponent() {
        setTitle();
        setFooter();
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
    
    
    
    
}
