
import 'my_pda_scanner_platform_interface.dart';
import 'package:flutter/services.dart';

class MyPdaScanner {
  static const MethodChannel _channel = const MethodChannel("my_pda_scanner");
  Future<String?> getPlatformVersion() {
    return MyPdaScannerPlatform.instance.getPlatformVersion();
  }
  static Future<String?> pdaScannerInit() async {
    final String? code = await _channel.invokeMethod('init');
    return code;
  }
}
