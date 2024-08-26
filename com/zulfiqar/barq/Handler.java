package com.zulfiqar.barq;

import java.io.*;
import java.net.Socket;

public class Handler implements Runnable {
    private final Socket socket;
    private final HtmlRenderer renderer;

    public Handler(Socket socket, HtmlRenderer renderer) {
        this.socket = socket;
        this.renderer = renderer;
    }

    @Override()
    public void run() {
        try {
            var out = socket.getOutputStream();
            var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            renderer.render(out, in);


        } catch (IOException e) {
            System.out.println("Handler.run() " + e.getMessage());
        }
    }
}