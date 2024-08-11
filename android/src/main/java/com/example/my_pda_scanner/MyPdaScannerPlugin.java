package com.example.my_pda_scanner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodChannel;

/**
 * MyPdaScannerPlugin
 */
public class MyPdaScannerPlugin implements FlutterPlugin {

    private EventChannel eventChannel;
    private MethodChannel flutterChannel;
    private Context applicationContext;

    private static String ACTION_DATA_CODE_RECEIVED = "";
    private static String DATA = "";

    private static final String CHARGING_CHANNEL = "my_pda_channel";
    private static final String FLUTTER_TO_ANDROID_CHANNEL = "flutter_to_android";

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

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);

        // 注册MethodChannel
        flutterChannel = new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), FLUTTER_TO_ANDROID_CHANNEL);
        //接受flutter消息
        flutterChannel.setMethodCallHandler(new MethodCallHandler() {
            @Override
            public void onMethodCall(MethodCall call, Result result) {
                if (call.method.equals("sendMessage")) {
                    String pda_action = call.argument("pda_action");
                    String data_tag = call.argument("data_tag");
                    // 处理Flutter端发送的消息
                    ACTION_DATA_CODE_RECEIVED = pda_action;
                    DATA = data_tag;
                    result.success(null); // 返回结果给Flutter端
                    } else {
                    result.notImplemented();
                }
            }
        });
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
