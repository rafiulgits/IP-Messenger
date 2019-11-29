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
package io;

/**
 *
 * @author rafiul islam
 */
public class Handler {
    CallBack mCallBack;
    
    public Handler(CallBack pCallBack){
        mCallBack = pCallBack;
    }
    
    public void send(int code, Object data){
        mCallBack.onCallBack(code, data);
    
    }
    
    public static interface CallBack {
        public void onCallBack(int code, Object data);
    } 
}
