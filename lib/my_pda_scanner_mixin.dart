import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:my_pda_scanner/my_pda_scanner_util.dart';

/// 商米扫描设备混入
mixin MyPdaScannerMixin<T extends StatefulWidget> on State<T> {
  late StreamSubscription streamSubscription;
  final MyPdaScannerUtil util = MyPdaScannerUtil();

  @override
  void initState() {
    super.initState();

    /// 开始监听流
    Future.microtask(() {
      streamSubscription = util.start().listen((event) {
        util.printLog("接收扫描数据:$event");
        if (event != null) {
          myPdaScannerCodeHandle(event);
        }
      });
    });
  }

  ///
  /// 可以在这里处理相关逻辑
  ///
  Future<void> myPdaScannerCodeHandle(Map<String, String> code);

  @override
  void dispose() {
    super.dispose();

    /// 关闭监听流，防止内存泄漏
    streamSubscription.cancel();
  }
}
