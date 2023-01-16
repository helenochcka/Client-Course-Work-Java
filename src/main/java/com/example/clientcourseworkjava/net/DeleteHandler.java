package com.example.clientcourseworkjava.net;

import com.example.clientcourseworkjava.Client;
import com.example.clientcourseworkjava.model.Entry;

import java.net.Socket;

public class DeleteHandler extends  RequestHandler {

    private Entry entry;


    public DeleteHandler(Socket socket, Entry entry) {
        super(socket);
        this.entry = entry;
    }

    @Override
    public void run() {
        delete();
    }

    private void delete() {
        out.write(entry.pack(PacketType.DELETE));
        out.flush();
        Client.logger.info("Entry: " + entry + " deleted successfully...");
    }
}
