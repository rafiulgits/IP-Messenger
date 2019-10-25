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
package driver;

import activity.BaseActivity;
import activity.WelcomeActivity;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author rafiul islam
 */
class Window {
    private JFrame windowFrame;
    private Container container;
    private BaseActivity currentActivity;
    
    Window(){
        windowFrame = new JFrame("IP Messenger");
        windowFrame.setPreferredSize(new Dimension(350, 600));
        windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windowFrame.setResizable(false);
        container = windowFrame.getContentPane();
        
    }    
    void show(){
        currentActivity = new WelcomeActivity();
        container.add(currentActivity);
        container.repaint();
        windowFrame.pack();
        windowFrame.setVisible(true);
    }
    
    
    void close(){
        windowFrame.dispose();
        System.exit(0);
    }
    
    
    void updateContent(BaseActivity activity){
        container.remove(currentActivity);
        container.add(activity);
        container.repaint();
        windowFrame.pack();
    }
    
}
