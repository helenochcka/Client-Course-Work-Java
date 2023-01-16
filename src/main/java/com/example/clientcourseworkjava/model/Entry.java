package com.example.clientcourseworkjava.model;

import com.example.clientcourseworkjava.Client;
import com.example.clientcourseworkjava.net.PacketType;

import java.util.UUID;

public class Entry {

    private String id;
    private String proofOfOwnership;
    private String owner;
    private String realEstateType;
    private String location;
    private int squareFootage;
    private String dateOfPurchase;

    public Entry(String proofOfOwnership, String owner, String realEstateType,
                 String location, int squareFootage, String dateOfPurchase) {

        String randomID = UUID.randomUUID().toString();
        String[] randomIDTokens = randomID.split("-");
        this.id = randomIDTokens[randomIDTokens.length - 1];

        this.proofOfOwnership = proofOfOwnership;
        this.owner = owner;
        this.realEstateType = realEstateType;
        this.location = location;
        this.squareFootage = squareFootage;
        this.dateOfPurchase = dateOfPurchase;
    }

    public Entry() {}

    public void setId(String id) {
        this.id = id;
    }

    public void setProofOfOwnership(String proofOfOwnership) {
        this.proofOfOwnership = proofOfOwnership;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setRealEstateType(String realEstateType) {
        this.realEstateType = realEstateType;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setSquareFootage(int squareFootage) {
        this.squareFootage = squareFootage;
    }

    public void setDateOfPurchase(String dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }




    @Override
    public String toString() {
        return "(" + id + " | " +
                proofOfOwnership + " | " +
                owner + " | " +
                realEstateType + " | " +
                location + " | " +
                squareFootage + " | " +
                dateOfPurchase + ")";
    }

    public String pack(PacketType packetType) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(packetType.ordinal())
                .append(Client.INTERNAL_SEPARATOR)
                .append(id)
                .append(Client.SUBINTERNAL_SEPARATOR)
                .append(proofOfOwnership)
                .append(Client.SUBINTERNAL_SEPARATOR)
                .append(owner)
                .append(Client.SUBINTERNAL_SEPARATOR)
                .append(realEstateType)
                .append(Client.SUBINTERNAL_SEPARATOR)
                .append(location)
                .append(Client.SUBINTERNAL_SEPARATOR)
                .append(squareFootage)
                .append(Client.SUBINTERNAL_SEPARATOR)
                .append(dateOfPurchase)
                .append(Client.EXTERNAL_SEPARATOR);
        return stringBuilder.toString();
    }

    public static Entry parseEntry(String string, Character fieldSeparator) {
        String[] stringTokens = string.split(String.valueOf(fieldSeparator));
        Entry entry = new Entry();

        try {
            entry.setId(stringTokens[0]);
            entry.setProofOfOwnership(stringTokens[1]);
            entry.setOwner(stringTokens[2]);
            entry.setRealEstateType(stringTokens[3]);
            entry.setLocation(stringTokens[4]);
            entry.setSquareFootage(Integer.parseInt(stringTokens[5]));
            entry.setDateOfPurchase(stringTokens[6]);
        } catch (NumberFormatException e) {
            //todo
        }

        return entry;
    }

    public String getId() {
        return id;
    }

    public String getProofOfOwnership() {
        return proofOfOwnership;
    }

    public String getOwner() {
        return owner;
    }

    public String getRealEstateType() {
        return realEstateType;
    }

    public String getLocation() {
        return location;
    }

    public int getSquareFootage() {
        return squareFootage;
    }

    public String getDateOfPurchase() {
        return dateOfPurchase;
    }
}
