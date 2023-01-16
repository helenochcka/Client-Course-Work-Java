package com.example.clientcourseworkjava.net;

import com.example.clientcourseworkjava.Client;
import com.example.clientcourseworkjava.model.Entry;

import java.net.Socket;

public class CreateHandler extends  RequestHandler {
    private Entry entry;


    public CreateHandler(Socket socket, Entry entry) {
        super(socket);
        this.entry = entry;
    }

    @Override
    public void run() {
        create();
    }

    private void create() {
            out.write(entry.pack(PacketType.CREATE));
            out.flush();
            Client.logger.info("Entry: " + entry + " created successfully...");
    }

}
