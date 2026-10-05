import { ChakraProvider, createSystem, defaultConfig, defaultSystem, defineConfig } from "@chakra-ui/react";
import {
  ColorModeProvider,
  type ColorModeProviderProps,
} from "./color-mode";


const config = defineConfig({
  preflight: false,
  theme: {
    tokens: {
      colors: {} ,
    },
  },
});


const system = createSystem(defaultConfig,defaultSystem._config, config );

export function Provider(props: ColorModeProviderProps) {
  return (
    <ChakraProvider value={system}>
      <ColorModeProvider {...props} />
    </ChakraProvider>
  );
}
