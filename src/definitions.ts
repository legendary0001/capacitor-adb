/// <reference types="@capacitor/cli" />



export interface AdbPlugin {
  executeNormalCommand(options   : { command: string }): Promise<{ output: string , exitCode: number ,errorOutput: string}>;
 executeAdbCommand(options   : { command: string }): Promise<{ output: string , exitCode: number ,errorOutput: string}>;
}
