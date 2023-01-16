package com.example.clientcourseworkjava.net;

import java.io.*;
import java.net.Socket;

public class RequestHandler extends Thread {

    protected final Socket socket;
    protected PrintWriter out;
    protected InputStream in;

    public RequestHandler(Socket socket) {
        this.socket = socket;
        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
