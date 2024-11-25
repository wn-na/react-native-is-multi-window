import {useEffect, useState} from 'react';
import {Button, StyleSheet, Text, View} from 'react-native';
import {
  isMultiWindowMode,
  useMultiWindowMode,
} from 'react-native-is-multi-window';

export default function Main() {
  const {isMultiMode} = useMultiWindowMode();
  const [result, setResult] = useState(false);

  useEffect(() => {
    setResult(isMultiMode);
  }, [isMultiMode]);

  return (
    <View style={styles.container}>
      <Text>Result: {result ? 'true' : 'false'}</Text>
      <Button
        title="test"
        onPress={() => {
          isMultiWindowMode().then(console.log);
        }}></Button>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
