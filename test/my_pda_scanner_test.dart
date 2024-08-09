import 'package:flutter_test/flutter_test.dart';
import 'package:my_pda_scanner/my_pda_scanner.dart';
import 'package:my_pda_scanner/my_pda_scanner_platform_interface.dart';
import 'package:my_pda_scanner/my_pda_scanner_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockMyPdaScannerPlatform
    with MockPlatformInterfaceMixin
    implements MyPdaScannerPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final MyPdaScannerPlatform initialPlatform = MyPdaScannerPlatform.instance;

  test('$MethodChannelMyPdaScanner is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelMyPdaScanner>());
  });

  test('getPlatformVersion', () async {
    MyPdaScanner myPdaScannerPlugin = MyPdaScanner();
    MockMyPdaScannerPlatform fakePlatform = MockMyPdaScannerPlatform();
    MyPdaScannerPlatform.instance = fakePlatform;

    expect(await myPdaScannerPlugin.getPlatformVersion(), '42');
  });
}
