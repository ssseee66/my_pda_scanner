#include "include/my_pda_scanner/my_pda_scanner_plugin_c_api.h"

#include <flutter/plugin_registrar_windows.h>

#include "my_pda_scanner_plugin.h"

void MyPdaScannerPluginCApiRegisterWithRegistrar(
    FlutterDesktopPluginRegistrarRef registrar) {
  my_pda_scanner::MyPdaScannerPlugin::RegisterWithRegistrar(
      flutter::PluginRegistrarManager::GetInstance()
          ->GetRegistrar<flutter::PluginRegistrarWindows>(registrar));
}
