package com.example.my_pda_scanner;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.BatteryManager;
import android.os.IBinder;

import io.flutter.app.FlutterActivity;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import io.flutter.view.FlutterView;

/** MyPdaScannerPlugin */
public class MyPdaScannerPlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  private EventChannel eventChannel;
  private Context applicationContext;

  private static final String ACTION_DATA_CODE_RECEIVED =
      "com.service.scanner.data";
  private static final String DATA = "data";
  private static final String SOURCE = "source_byte";

  private static final String CHARGING_CHANNEL = "pda_scanner_channel";

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "my_pda_scanner");
    channel.setMethodCallHandler(this);

    eventChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(), CHARGING_CHANNEL);
    eventChannel.setStreamHandler(new EventChannel.StreamHandler() {
      private BroadcastReceiver chargingStateChangeReceiver;
      @Override
      public void onListen(Object arguments, EventChannel.EventSink events) {
        chargingStateChangeReceiver = createChargingStateChangeReceiver(events);
        IntentFilter filter = new IntentFilter();
        System.out.println("lsjsls");
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
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android: FORs" + android.os.Build.VERSION.RELEASE);
    } else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "my_pda_scanner");
    channel.setMethodCallHandler(new MyPdaScannerPlugin());
  }

  private BroadcastReceiver createChargingStateChangeReceiver(final EventChannel.EventSink events) {
    return new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
        String code = intent.getStringExtra(DATA);
        byte[] arr = intent.getByteArrayExtra(SOURCE);
        if (code != null && !code.isEmpty()) {
          System.out.println("手机接收到广播数据>>>>>>>>>>>>>>>>>>>>>>>>>" + code);
          events.success(code);
        }

      }
    };
  }
}
