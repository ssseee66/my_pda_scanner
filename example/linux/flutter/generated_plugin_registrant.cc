//
//  Generated file. Do not edit.
//

// clang-format off

#include "generated_plugin_registrant.h"

#include <my_pda_scanner/my_pda_scanner_plugin.h>

void fl_register_plugins(FlPluginRegistry* registry) {
  g_autoptr(FlPluginRegistrar) my_pda_scanner_registrar =
      fl_plugin_registry_get_registrar_for_plugin(registry, "MyPdaScannerPlugin");
  my_pda_scanner_plugin_register_with_registrar(my_pda_scanner_registrar);
}
