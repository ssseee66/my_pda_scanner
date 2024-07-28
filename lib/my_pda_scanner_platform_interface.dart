import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'my_pda_scanner_method_channel.dart';

abstract class MyPdaScannerPlatform extends PlatformInterface {
  /// Constructs a MyPdaScannerPlatform.
  MyPdaScannerPlatform() : super(token: _token);

  static final Object _token = Object();

  static MyPdaScannerPlatform _instance = MethodChannelMyPdaScanner();

  /// The default instance of [MyPdaScannerPlatform] to use.
  ///
  /// Defaults to [MethodChannelMyPdaScanner].
  static MyPdaScannerPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [MyPdaScannerPlatform] when
  /// they register themselves.
  static set instance(MyPdaScannerPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
