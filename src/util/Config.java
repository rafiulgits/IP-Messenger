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
package util;

import java.net.Socket;

/**
 *
 * @author rafiul islam
 */
public class Config {
    private static String clientName = "N/A";
    private static Socket clientSocket = null;
    
    public static void setSocket(Socket socket){
        clientSocket = socket;
    }
    
    public static Socket getSocket(){
        return clientSocket;
    }
    
    public static void setClientName(String name){
        clientName = name;
    }
    
    public static String getClientName(){
        return clientName;
    }
    
}
