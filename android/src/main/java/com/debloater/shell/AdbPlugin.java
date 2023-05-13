package com.debloater.shell;

import android.content.Context;
import android.util.Log;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

@CapacitorPlugin(name = "Shell")
public class AdbPlugin extends Plugin {
    private static final String TAG = "AdbPlugin";

    @PluginMethod
    public void executeAdbCommand(PluginCall call) {
        try {
            Context context = getContext();
            String command = call.getString("command");
            call.setKeepAlive(true);

            String adbPath = context.getApplicationInfo().nativeLibraryDir + "/libadb.so";

            ProcessBuilder processBuilder = new ProcessBuilder(adbPath, command);
            processBuilder.directory(context.getFilesDir());

            Map<String, String> environment = processBuilder.environment();
            environment.put("HOME", context.getFilesDir().getPath());
            environment.put("TMPDIR", context.getCacheDir().getPath());

            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            StringBuilder errorOutput = new StringBuilder();
            while ((line = errorReader.readLine()) != null) {
                errorOutput.append(line).append("\n");
            }

            process.waitFor();
            errorReader.close();
            reader.close();

            int exitCode = process.exitValue();
            String result = output.toString();
            String errorResult = errorOutput.toString();

            JSObject response = new JSObject();
            Log.i("Echo", result);
            response.put("output", result);
            response.put("exitCode", exitCode);
            response.put("errorOutput", errorResult);
            call.resolve(response);
        } catch (Exception e) {
            Log.e(TAG, "Error executing shell command", e);
            call.reject("Error executing shell command: " + e.getMessage());
        }
    }
    @PluginMethod
    public void executeNormalCommand(PluginCall call) {
        try {
            Context context = getContext();
            String command = call.getString("command");
            call.setKeepAlive(true);

            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.directory(context.getFilesDir());

            Map<String, String> environment = processBuilder.environment();
            environment.put("HOME", context.getFilesDir().getPath());
            environment.put("TMPDIR", context.getCacheDir().getPath());

            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            StringBuilder errorOutput = new StringBuilder();
            while ((line = errorReader.readLine()) != null) {
                errorOutput.append(line).append("\n");
            }

            process.waitFor();
            errorReader.close();
            reader.close();

            int exitCode = process.exitValue();
            String result = output.toString();
            String errorResult = errorOutput.toString();

            JSObject response = new JSObject();
            Log.i("Echo", result);
            response.put("output", result);
            response.put("exitCode", exitCode);
            response.put("errorOutput", errorResult);
            call.resolve(response);
        } catch (Exception e) {
            Log.e(TAG, "Error executing shell command", e);
            call.reject("Error executing shell command: " + e.getMessage());
        }
    }
}
