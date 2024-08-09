
import 'my_pda_scanner_platform_interface.dart';

class MyPdaScanner {
  Future<String?> getPlatformVersion() {
    return MyPdaScannerPlatform.instance.getPlatformVersion();
  }
}
