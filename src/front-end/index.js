/**
 * @format
 */

import { AppRegistry } from 'react-native';
import App from './App';
import { name as appName } from './app.json';
import { LogBox } from 'react-native';
import * as encoding from 'text-encoding';

LogBox.ignoreAllLogs(true);

AppRegistry.registerComponent(appName, () => App);
