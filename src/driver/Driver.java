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

import javax.swing.SwingUtilities;

/**
 *
 * @author rafiul islam
 */
public class Driver {
    public static void main(String[] args) {
        /**
         * To run a swing program it is better to use another
         * thread for swing execution instead of main thread itself.
         * SwingUtilities static method invokeLeter provide a thread
         * for run swing program by passing an implemented runnable interface.
        */
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                new FrameWindow();
            }
        });
    }
}
