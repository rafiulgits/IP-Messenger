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
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import sys.Service;

/**
 *
 * @author rafiul islam
 */
public class BroadCastService implements Service, Handler.CallBack{
   
    private ServerSocket mServerSocket;
    private volatile Handler mHandler;
    
    private volatile Hashtable clientsTable;
    
    private ConnectionListener mConnectionListener = null;
    private boolean selfJoined = false;
    
    public BroadCastService(ServerSocket pServerSocket){
        mServerSocket = pServerSocket;
        mHandler = new Handler(this);
        clientsTable = BroadcastServer.clientsTable;
    }
    
    public void join(ConnectionListener pConnectionListener){
        mConnectionListener = pConnectionListener;
    }
   
    @Override
    public void operation(){
        while(!mServerSocket.isClosed() || mServerSocket!=null){
            try {
                Socket socket = mServerSocket.accept();
                BroadCastClient client = new BroadCastClient(socket, mHandler);
                clientsTable.put(socket.getInetAddress().getHostAddress(), client);
                if(! selfJoined){
                    if(mConnectionListener != null){
                        mConnectionListener.onSuccess(socket);
                        selfJoined = false;
                    }
                }
            } catch (IOException ex) {
                if(!selfJoined){
                    if(mConnectionListener != null){
                        mConnectionListener.onFailed(ex);
                    }
                }
                continue;
            }
        }
    }
    
    
    @Override
    public void onCallBack(int code, Object message){
        if(code == MessageReceiver.MESSAGE_RECEIVE){
            // broadcast the message
            for(Object address : clientsTable.keySet()){
                try{
                    BroadCastClient client = (BroadCastClient)clientsTable.get(message);
                    client.send(message.toString());
                }catch(IOException ex){
                    clientsTable.remove(address);
                    continue;
                }
            }
        }
    }
    
    
}
