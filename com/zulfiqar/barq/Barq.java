package com.zulfiqar.barq;

import com.beust.jcommander.JCommander;

import java.io.*;
import java.net.ServerSocket;
import java.util.concurrent.Executors;

public class Barq {
    public static void main(String... argv) {
        var args = new Args();
        JCommander.newBuilder()
                .addObject(args)
                .build()
                .parse(argv);

        try {
            var configuration = Configuration.fromArgs(args);
            var barq = new Barq();

            barq.run(configuration);
        } catch (IOException e) {
            System.err.println("An IO Exception occurred: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            System.exit(1);
        }
    }

    public void run(Configuration configuration) throws IOException {
        var htmlRenderer = new HtmlRenderer(configuration);
        var port = configuration.port();

        try (var executorService = Executors.newFixedThreadPool(10)) {
            try(var server = new ServerSocket(port)){
                System.out.println("Server listening on port: " + port);

                //noinspection InfiniteLoopStatement
                while (true) {
                    Handler h = new Handler(server.accept(), htmlRenderer);
                    executorService.submit(h);
                }
            }
        }
    }
}
