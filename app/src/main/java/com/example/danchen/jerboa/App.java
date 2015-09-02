package com.example.danchen.jerboa;

import android.app.Application;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;

/**
 * Created by Tiffanie on 15-08-31.
 */
public class App extends Application {

    public void onCreate() {
       super.onCreate();
        //如果使用美国节点，请加上这行代码 AVOSCloud.useAVCloudUS();
        AVOSCloud.initialize(this, "NodF24j2nk6OdsvLj7hiWEyX", "yroBY6xCj1ND2wyDWCqBHm6z");

        AVObject testObject = new AVObject("TestObject");
       testObject.put("foo", "bar");
       testObject.saveInBackground();
   }


}
