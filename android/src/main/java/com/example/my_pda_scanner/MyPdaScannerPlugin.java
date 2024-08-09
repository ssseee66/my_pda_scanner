package com.example.my_pda_scanner;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** MyPdaScannerPlugin */
public class MyPdaScannerPlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "my_pda_scanner");
    channel.setMethodCallHandler(this);
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  /**
   * MyPdaScannerPlugin
   */
  public static class MyPdaScannerPlugin implements FlutterPlugin {

    private EventChannel eventChannel;
    private Context applicationContext;

    private static final String ACTION_DATA_CODE_RECEIVED =
            "com.service.scanner.data";
    private static final String DATA = "data";

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
          String code = intent.getStringExtra("ScanCode");
          if (code != null) {
            events.success(code);
          }

        }
      };
    }

  }
}
