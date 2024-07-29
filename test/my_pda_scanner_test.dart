import 'package:flutter_test/flutter_test.dart';
import 'package:flutter/services.dart';



void main() {
  const MethodChannel channel = MethodChannel('pda_scanner_channel');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger.setMockMethodCallHandler(channel,(message) async {
      return '42';

    },);
  });
}
