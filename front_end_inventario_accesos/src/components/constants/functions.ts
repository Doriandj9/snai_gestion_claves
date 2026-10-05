import { createToaster } from "@chakra-ui/react";
import {type ColorMode, type UseColorModeReturn } from "../ui/color-mode";

import {  useTheme } from "next-themes";


export function useColorModeValue<T>(light: T, dark: T) {
  const { colorMode } = useColorMode();
  return colorMode === "dark" ? dark : light;
}

export function useColorMode(): UseColorModeReturn {
  const { resolvedTheme, setTheme, forcedTheme } = useTheme();
  const colorMode = forcedTheme || resolvedTheme;
  const toggleColorMode = () => {
    setTheme(resolvedTheme === "dark" ? "light" : "dark");
  };
  return {
    colorMode: colorMode as ColorMode,
    setColorMode: setTheme,
    toggleColorMode,
  };
}

export const toaster = createToaster({
  placement: "bottom-end",
  pauseOnPageIdle: true,
});