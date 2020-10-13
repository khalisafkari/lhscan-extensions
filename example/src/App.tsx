import * as React from 'react';
import { StyleSheet, View, Text } from 'react-native';
import { postList } from 'lhscan-extensions';

export default function App() {
  React.useEffect(() => {
    postList({}).then(console.log);
  }, []);

  return (
    <View style={styles.container}>
      <Text>DEMO</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
});
