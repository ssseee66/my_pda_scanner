package com.example.my_pda_scanner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.EventChannel;

/**
 * MyPdaScannerPlugin
 */
public class MyPdaScannerPlugin implements FlutterPlugin {

    private EventChannel eventChannel;
    private Context applicationContext;

    private static final String ACTION_DATA_CODE_RECEIVED = "com.service.scanner.data";
    private static final String DATA = "ScanCode";

    private static final String CHARGING_CHANNEL = "my_pda_channel";

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {

        eventChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(), CHARGING_CHANNEL);
        eventChannel.setStreamHandler(new EventChannel.StreamHandler() {

            private BroadcastReceiver chargingStateChangeReceiver;

            @Override
            public void onListen(Object arguments, EventChannel.EventSink events) {
                chargingStateChangeReceiver = createChargingStateChangeReceiver(events);
                IntentFilter filter = new IntentFilter();
                filter.addAction(ACTION_DATA_CODE_RECEIVED);
                applicationContext.registerReceiver(
                        chargingStateChangeReceiver, filter);
            }

            @Override
            public void onCancel(Object arguments) {
                applicationContext.unregisterReceiver(chargingStateChangeReceiver);
                chargingStateChangeReceiver = null;
            }
        });

        applicationContext = flutterPluginBinding.getApplicationContext();
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        eventChannel.setStreamHandler(null);
    }

    private BroadcastReceiver createChargingStateChangeReceiver(final EventChannel.EventSink events) {
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String code = intent.getStringExtra(DATA);
                if (code != null) {
                    events.success(code);
                }

            }
        };
    }

}
