import { NativeModules } from 'react-native';

type LhscanExtensionsType = {
  multiply(a: number, b: number): Promise<number>;
};

const { LhscanExtensions } = NativeModules;

export default LhscanExtensions as LhscanExtensionsType;
