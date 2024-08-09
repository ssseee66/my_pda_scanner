import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class MyPdaScannerUtil {
  MyPdaScannerUtil._();

  factory MyPdaScannerUtil() => _instance;
  static final MyPdaScannerUtil _instance = MyPdaScannerUtil._();
  bool showLog = true;

  EventChannel eventChannel = EventChannel('my_pda_channel');

  void printLog(dynamic log) {
    if (showLog) {
      debugPrint('PDA_LOG: $log');
    }
  }

  StreamSubscription? streamSubscription;
  Stream? stream;

  Stream start() {
    stream ??= eventChannel.receiveBroadcastStream();
    return stream!;
  }

  /// 监听扫码数据
  void listen(ValueChanged<String> codeHandle) {
    streamSubscription = start().listen((event) {
      if (event != null) {
        printLog('扫描到数据$event');
        codeHandle.call(event.toString());
      }
    });
  }

  /// 关闭监听
  void cancel() {
    streamSubscription?.cancel();
  }
}
