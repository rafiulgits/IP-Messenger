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
import io.MessageReceiver;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import sys.Executor;

/**
 *
 * @author rafiul islam
 */
class BroadCastClient {
    private Socket mSocket;
    private Handler mHandler;
    private DataOutputStream mDataOutputStream;
    
    public BroadCastClient(Socket pSocket, Handler pHandler){
        mSocket = pSocket;
        mHandler = pHandler;
        try {
            mDataOutputStream = new DataOutputStream(mSocket.getOutputStream());
            MessageReceiver receiver = new MessageReceiver(pHandler);
            receiver.setSocket(mSocket);
            Executor.execute(receiver);
        } catch (IOException e) {
            mDataOutputStream = null;
        }
    }
    
    public void send(String message) throws IOException{
        if(mDataOutputStream != null){
            mDataOutputStream.writeUTF(message);
        }
    }
}
