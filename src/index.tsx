import { useEffect, useState } from 'react';
import { NativeEventEmitter, NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-is-multi-window' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const ReactNativeIsMultiWindowModule = NativeModules.ReactNativeIsMultiWindow
  ? NativeModules.ReactNativeIsMultiWindow
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

const ReactNativeIsMultiWindowEmitter =
  Platform.OS === 'ios' || Platform.OS === 'android'
    ? new NativeEventEmitter(ReactNativeIsMultiWindowModule)
    : undefined;

const addListener = (callback: (isMultiMode: boolean) => void): number => {
  return 0;
};

const removeListener = (id: number) => {};

export const isMultiWindowMode = async () => {
  return true;
};

export const useMultiWindowMode = () => {
  const [isMultiMode, setMultiMode] = useState(false);

  useEffect(() => {
    isMultiWindowMode().then(setMultiMode);
    const listener = addListener((mode) => {
      setMultiMode(mode);
    });
    return () => {
      removeListener(listener);
    };
  }, []);

  return { isMultiMode, isMultiWindowMode };
};
