import { WebPlugin } from '@capacitor/core';


export class ShellWebPlugin extends WebPlugin {
  async executeCommand( ): Promise<{ output: string }> {
    throw Error('Not implemented on web.');
  }
}

const Shell = new ShellWebPlugin();

export { Shell };
