package com.example.clientcourseworkjava.net;

import com.example.clientcourseworkjava.Client;
import com.example.clientcourseworkjava.model.Entry;

import java.net.Socket;

public class UpdateHandler extends  RequestHandler {

    private Entry entry;


    public UpdateHandler(Socket socket, Entry entry) {
        super(socket);
        this.entry = entry;
    }

    @Override
    public void run() {
        update();
    }

    private void update() {
        out.write(entry.pack(PacketType.UPDATE));
        out.flush();
        Client.logger.info("Entry: " + entry + " updated successfully...");
    }
}
