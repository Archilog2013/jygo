package com.archilog.jygo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JygoServer {
    private static Process process;
    private static ProcessBuilder builder;
    
    public JygoServer() {
        // Lancement du serveur avec args
        //ProcessBuilder builder = new ProcessBuilder(StringUtil.splitArguments("gnugo --mode gtp")); // <- linux
        builder = new ProcessBuilder("C:\\gnugo-3.8\\gnugo.exe", "--mode", "gtp");
        builder.redirectErrorStream(true);

        try {
            process = builder.start();
        } catch (IOException ex) {
            Logger.getLogger(JygoServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String launchCommand(String cmd) {
        String line = "";
        StringBuilder sb = new StringBuilder();

        // Get streams from previous process
        OutputStream stdin = process.getOutputStream();
        InputStream stdout = process.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));
        
        try {
            String input = cmd;
            if (input.trim().equals("exit")) {
                writer.write("exit\n");
            } else {
                writer.write(input + "\n");
            }
            writer.flush();

            line = reader.readLine();
            while (line != null && !"".equals(line) && !line.trim().equals("--EOF--")) {
                sb.append(line).append("<br>");
                line = reader.readLine();
            }
        } catch (Exception e) {
            // TODO
            System.out.print("c");
        }
        
        return sb.toString();
    }
}