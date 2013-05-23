package com.archilog.jygo;

import com.archilog.jygo.util.StringUtil;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JygoServer {
    private static Process process;

    public static void main(String[] args) {
        String line;
        Scanner scan = new Scanner(System.in);

        // Lancement du serveur avec args
        ProcessBuilder builder = new ProcessBuilder(StringUtil.splitArguments("gnugo --mode gtp"));
        builder.redirectErrorStream(true);

        try {
            process = builder.start();
        } catch (IOException ex) {
            Logger.getLogger(JygoServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Get streams from previous process
        OutputStream stdin = process.getOutputStream();
        InputStream stdout = process.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));

        try {
            // process.waitFor();
            while (scan.hasNext()) {
                String input = scan.nextLine();
                if (input.trim().equals("exit")) {
                    // Putting 'exit' amongst the echo --EOF--s below doesn't work.
                    writer.write("exit\n");
                } else {
                    writer.write(input + "\n");
                }
                writer.flush();

                line = reader.readLine();
                while (line != null && !line.trim().equals("--EOF--")) {
                    System.out.println("Stdout: " + line);
                    line = reader.readLine();
                }
                if (line == null) {
                    break;
                }
            }
        } catch (Exception e) {
            // TODO
            System.out.print("c");
        }
    }
    
    public void run(String cmd) {
        String line;

        // Lancement du serveur avec args
        ProcessBuilder builder = new ProcessBuilder(StringUtil.splitArguments("gnugo --mode gtp"));
        builder.redirectErrorStream(true);

        try {
            process = builder.start();
        } catch (IOException ex) {
            Logger.getLogger(JygoServer.class.getName()).log(Level.SEVERE, null, ex);
        }

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
            while (line != null && !line.trim().equals("--EOF--")) {
                System.out.println("Stdout: " + line);
                line = reader.readLine();
            }
        } catch (Exception e) {
            // TODO
            System.out.print("c");
        }
    }
}