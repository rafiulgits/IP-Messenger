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

import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;


/**
 *
 * @author rafiul islam
 */
public class FrameWindow {
    
    private JFrame frame;
    private Container pane;
    
    public FrameWindow(){
        frame = new JFrame("IP Messenger");
        frame.setPreferredSize(new Dimension(350, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        
        pane = frame.getContentPane();
        
        Opening opening = new Opening(frame);
        
        pane.add(opening);
        
        frame.pack();
        frame.setVisible(true);
    }
}
