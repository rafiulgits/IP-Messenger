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
package panel;

/**
 *
 * @author rafiul islam
 */
public class FileProgress {
    
    private static final int MAX_BYTE = 1024;
    private static final int MAX_KB = MAX_BYTE * 1024;
    private static final long MAX_MB = MAX_KB * 1024;
    private static final long MAX_GB = MAX_MB * 1024;
    
    public static int getMax(long bytes){
        switch(getType(bytes)){
            case 1: return (int)bytes;
            case 2: return (int)(bytes/MAX_BYTE);
            case 3: return (int)(bytes/MAX_KB);
            default:return (int)(bytes/MAX_MB);
        }
    }
    public static int getProgress(long bytes, long prog){
        switch(getType(bytes)){
            case 1: return (int)prog;
            case 2: return (int)(prog/MAX_BYTE);
            case 3: return (int)(prog/MAX_KB);
            default:return (int)(prog/MAX_MB);
        }
    }
    private static int getType(long bytes){
        if(bytes <= MAX_BYTE){
            return 1;
        }
        if(bytes <= MAX_KB){
            return 2;
        }
        if(bytes <= MAX_MB){
            return 3;
        }
        return 4;
    }
}
