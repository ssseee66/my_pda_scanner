#ifndef FLUTTER_PLUGIN_MY_PDA_SCANNER_PLUGIN_H_
#define FLUTTER_PLUGIN_MY_PDA_SCANNER_PLUGIN_H_

#include <flutter/method_channel.h>
#include <flutter/plugin_registrar_windows.h>

#include <memory>

namespace my_pda_scanner {

class MyPdaScannerPlugin : public flutter::Plugin {
 public:
  static void RegisterWithRegistrar(flutter::PluginRegistrarWindows *registrar);

  MyPdaScannerPlugin();

  virtual ~MyPdaScannerPlugin();

  // Disallow copy and assign.
  MyPdaScannerPlugin(const MyPdaScannerPlugin&) = delete;
  MyPdaScannerPlugin& operator=(const MyPdaScannerPlugin&) = delete;

  // Called when a method is called on this plugin's channel from Dart.
  void HandleMethodCall(
      const flutter::MethodCall<flutter::EncodableValue> &method_call,
      std::unique_ptr<flutter::MethodResult<flutter::EncodableValue>> result);
};

}  // namespace my_pda_scanner

#endif  // FLUTTER_PLUGIN_MY_PDA_SCANNER_PLUGIN_H_
