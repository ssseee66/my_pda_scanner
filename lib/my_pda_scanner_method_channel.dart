import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'my_pda_scanner_platform_interface.dart';

/// An implementation of [MyPdaScannerPlatform] that uses method channels.
class MethodChannelMyPdaScanner extends MyPdaScannerPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('my_pda_scanner');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
}
