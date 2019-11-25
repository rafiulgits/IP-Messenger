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
package net;

import io.Handler;
import java.io.DataInputStream;
import java.io.IOException;
import sys.Config;
import sys.Service;

/**
 *
 * @author rafiul islam
 */
public class MessageReceiver implements Service{
    
    public static final int CONNECTION_FAILED = 500;
    public static final int MESSAGE_RECEIVE = 200;
    
    private DataInputStream dataInputStream;
    private volatile Handler mHandler;
    
    public MessageReceiver(Handler pHandler) throws IOException{
        mHandler = pHandler;
        dataInputStream = new DataInputStream(Config.getSocket().getInputStream());
    }
    
    @Override
    public void operation() {
        String message = "";
        while(Config.getSocket() != null){
            try {
                message =  dataInputStream.readUTF();
                mHandler.send(MESSAGE_RECEIVE, message);
            } catch (IOException ex) {
                mHandler.send(CONNECTION_FAILED, ex);
            }
        }
    }
    
}
