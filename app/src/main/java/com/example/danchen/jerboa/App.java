package com.example.danchen.jerboa;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Tiffanie on 15-08-31.
 */
public class App extends Application {

    public void onCreate() {
       super.onCreate();
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "kfDdVWjDzVz5m5JKbETfPvR2u7NLGS4oTHB2vczN", "ElbkPnUHks44zRZm5hdNt21Sva0o4GxN6ZJlAwkJ");

    }

}
