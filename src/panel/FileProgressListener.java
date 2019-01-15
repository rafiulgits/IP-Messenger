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
public interface FileProgressListener {
    public static final int STATE_RECEIVED = 1;
    public static final int STATE_SENT = 0;
    public static final int DONE = -1;
    
    public void onFileProgress(int progress);
    public void onFileFinish(int state);
}
