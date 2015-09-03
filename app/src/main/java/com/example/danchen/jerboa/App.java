package com.example.danchen.jerboa;

import android.app.Application;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.example.danchen.jerboa.Server.ServerCommunication;

import java.util.List;

/**
 * Created by Tiffanie on 15-08-31.
 */
public class App extends Application {

    public void onCreate() {
       super.onCreate();
       //如果使用美国节点，请加上这行代码 AVOSCloud.useAVCloudUS();
       AVOSCloud.initialize(this, "NodF24j2nk6OdsvLj7hiWEyX", "yroBY6xCj1ND2wyDWCqBHm6z");
    }

}
