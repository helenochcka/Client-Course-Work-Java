package com.example.clientcourseworkjava.net;

import com.example.clientcourseworkjava.Client;
import com.example.clientcourseworkjava.model.Entry;
import javafx.collections.ObservableList;

import java.net.Socket;

import static com.example.clientcourseworkjava.Client.EXTERNAL_SEPARATOR;

public class ReadHandler extends  RequestHandler {
    ObservableList<Entry> entriesList;
    String pattern;


    public ReadHandler(Socket socket, String pattern, ObservableList<Entry> entriesList) {
        super(socket);
        this.pattern = pattern;
        this.entriesList = entriesList;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        read();
    }

    private void read() {
            String writeStr = String.valueOf(PacketType.READ.ordinal()) + Client.INTERNAL_SEPARATOR + pattern + Client.EXTERNAL_SEPARATOR;
            out.write(writeStr);
            out.flush();
            Client.logger.info("Read request sent...");
            StringBuilder response = new StringBuilder();
            try {
                byte i;
        while ((i = (byte)in.read()) != EXTERNAL_SEPARATOR) {
            response.append((char) i);
        }
    } catch (Exception e) {
        Client.logger.error("Response wasn't received... " + e);
    }
            if (response.toString().equals("-")) return;
    String[] responseTokens = response.toString().split("/");
            entriesList.clear();
            for (int i = 1; i < responseTokens.length; i++) {
                entriesList.add(Entry.parseEntry(responseTokens[i], Client.SUBINTERNAL_SEPARATOR));
            }

            Client.logger.info("Read request completed successfully...");
    }
}
