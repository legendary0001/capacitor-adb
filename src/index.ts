import { registerPlugin } from '@capacitor/core';

import type { AdbPlugin } from './definitions';

const Shell = registerPlugin<AdbPlugin>('Adb', {
  web: () => import('./web').then(m => new m.ShellWebPlugin()),
});

export * from './definitions';
export { Shell };
