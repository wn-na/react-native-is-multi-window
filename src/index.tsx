import { useEffect, useState } from 'react';
import { NativeEventEmitter, NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-is-multi-window' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const ReactNativeIsMultiWindowModule = NativeModules.IsMultiWindow
  ? NativeModules.IsMultiWindow
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

const isSupportPlatform = () => {
  return Platform.OS === 'android' || Platform.OS === 'ios';
};

const ReactNativeIsMultiWindowEmitter = isSupportPlatform()
  ? new NativeEventEmitter(ReactNativeIsMultiWindowModule)
  : undefined;

const addListener = (callback: (isMultiMode: boolean) => void) => {
  if (!isSupportPlatform()) {
    console.log(
      `[react-native-is-multi-window] ${Platform.OS} is not supported`
    );
    return;
  }
  ReactNativeIsMultiWindowEmitter?.addListener(
    'onMultiWindowModeChanged',
    callback
  );
};

export const isMultiWindowMode = async (): Promise<boolean> => {
  if (!isSupportPlatform()) {
    console.log(
      `[react-native-is-multi-window] ${Platform.OS} is not supported`
    );
    return false;
  }
  return await ReactNativeIsMultiWindowModule?.isMultiWindowMode?.();
};

export const useMultiWindowMode = () => {
  const [isMultiMode, setMultiMode] = useState(false);

  useEffect(() => {
    isMultiWindowMode().then(setMultiMode);
    addListener((mode) => {
      setMultiMode(mode);
    });
  }, []);

  return { isMultiMode };
};
