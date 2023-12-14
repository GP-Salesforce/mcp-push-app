package com.goldenplanet.mcppushdemo_aos;

import android.app.Application;

import com.evergage.android.ClientConfiguration;
import com.evergage.android.Evergage;
import com.evergage.android.LogLevel;
import com.goldenplanet.mcppushdemo_aos.util.SharedPrefHelper;

/**
 * Created by Kim Namhoon on 12/14/23.
 */

public class GlobalApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPrefHelper.init(this);

        if (BuildConfig.DEBUG) {
            Evergage.setLogLevel(LogLevel.DEBUG);
        }

        // MCP SDK 초기화
        Evergage.initialize(this);
        Evergage evergage = Evergage.getInstance();

        String userId = SharedPrefHelper.getUserId();
        String account = SharedPrefHelper.getAccount();
        String dataSet = SharedPrefHelper.getDataSet();


        evergage.setUserId(userId);

        evergage.start(new ClientConfiguration.Builder()
                .account(account)
                .dataset(dataSet)
                .usePushNotifications(true)
                .build());

    }
}
