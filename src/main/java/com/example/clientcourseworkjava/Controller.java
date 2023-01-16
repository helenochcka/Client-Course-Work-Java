package com.example.clientcourseworkjava;

import com.example.clientcourseworkjava.net.*;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.clientcourseworkjava.model.Entry;

import java.net.Socket;

public class Controller {

    public Button btnInsert;
    public Button btnDelete;
    public Button btnUpdate;

    public TableView<Entry> tvEntries;

    public TableColumn<Entry, Integer> colID;
    public TableColumn<Entry, String> colProofOfOwnership;
    public TableColumn<Entry, String> colOwner;
    public TableColumn<Entry, String> colRealEstateType;
    public TableColumn<Entry, String> colLocation;
    public TableColumn<Entry, Integer> colSquareFootage;
    public TableColumn<Entry, String> colDateOfPurchase;

    public TextField tfProofOfOwnership;
    public TextField tfOwner;
    public TextField tfRealEstateType;
    public TextField tfLocation;
    public TextField tfSquareFootage;
    public TextField tfDateOfPurchase;

    public TextField tfSearch;

    private final ObservableList<Entry> entriesList = FXCollections.observableArrayList();
    public Button btnFind;
    private Socket socket;

    @FXML
    public void initialize() {

        try {
            this.socket = new Socket(Client.serverIP, Client.serverPortNumber);
            Client.logger.info("Connected to the server: " + Client.serverIP + ":" + Client.serverPortNumber);
        } catch (Exception e) {
            Client.logger.fatal("Connection attempt failed, server not responding...");
            System.exit(-1);
        }

        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProofOfOwnership.setCellValueFactory(new PropertyValueFactory<>("proofOfOwnership"));
        colOwner.setCellValueFactory(new PropertyValueFactory<>("owner"));
        colRealEstateType.setCellValueFactory(new PropertyValueFactory<>("realEstateType"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colSquareFootage.setCellValueFactory(new PropertyValueFactory<>("squareFootage"));
        colDateOfPurchase.setCellValueFactory(new PropertyValueFactory<>("dateOfPurchase"));
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        tvEntries.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Entry entry = tvEntries.getSelectionModel().getSelectedItem();
                tfProofOfOwnership.setText(entry.getProofOfOwnership());
                tfOwner.setText(entry.getOwner());
                tfRealEstateType.setText(entry.getRealEstateType());
                tfLocation.setText(entry.getLocation());
                tfSquareFootage.setText(String.valueOf(entry.getSquareFootage()));
                tfDateOfPurchase.setText(entry.getDateOfPurchase());

                btnInsert.setDisable(true);
                btnUpdate.setDisable(false);
                btnDelete.setDisable(false);
            }
        });


        ReadHandler readHandler = new ReadHandler(socket, "", entriesList);
        readHandler.start();
        tvEntries.setItems(entriesList);

        AnimationTimer frameRateMeter = new AnimationTimer() {
            @Override
            public void handle(long l) {

            }
        };
        frameRateMeter.start();
    }

    public void OnButtonInsertClicked() {
        Entry entry;

        try {
            String proofOfOwnership = tfProofOfOwnership.getText();
            String owner = tfOwner.getText();
            String realEstateType = tfRealEstateType.getText();
            String location = tfLocation.getText();
            int squareFootage = Integer.parseInt(tfSquareFootage.getText());
            String dateOfPurchase = tfDateOfPurchase.getText();
            entry = new Entry(proofOfOwnership, owner, realEstateType, location, squareFootage, dateOfPurchase);
        } catch (Exception e) {
            //todo
            return;
        }


        CreateHandler createHandler = new CreateHandler(socket, entry);
        createHandler.start();

        ReadHandler readHandler = new ReadHandler(socket, "", entriesList);
        readHandler.start();

        tvEntries.setItems(entriesList);
        clearTextFields();
    }

    public void OnButtonUpdateClicked() {
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        btnInsert.setDisable(false);
        Entry entry = tvEntries.getSelectionModel().getSelectedItem();
        tvEntries.getSelectionModel().select(null);

        entry.setProofOfOwnership(tfProofOfOwnership.getText());
        entry.setOwner(tfOwner.getText());
        entry.setRealEstateType(tfRealEstateType.getText());
        entry.setLocation(tfLocation.getText());
        entry.setSquareFootage(Integer.parseInt(tfSquareFootage.getText()));
        entry.setDateOfPurchase(tfDateOfPurchase.getText());


        if (entry == null) return;
        UpdateHandler updateHandler = new UpdateHandler(socket, entry);
        updateHandler.start();

        ReadHandler readHandler = new ReadHandler(socket, "", entriesList);
        readHandler.start();

        tvEntries.setItems(entriesList);

        clearTextFields();
    }

    public void OnButtonDeleteClicked() {
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        Entry entry = tvEntries.getSelectionModel().getSelectedItem();

        tvEntries.getSelectionModel().select(null);

        if (entry == null) return;
        DeleteHandler deleteHandler = new DeleteHandler(socket, entry);
        deleteHandler.start();

        ReadHandler readHandler = new ReadHandler(socket, "", entriesList);
        readHandler.start();

        tvEntries.setItems(entriesList);
        clearTextFields();
    }

    private void clearTextFields() {
        tfProofOfOwnership.clear();
        tfOwner.clear();
        tfRealEstateType.clear();
        tfLocation.clear();
        tfSquareFootage.clear();
        tfDateOfPurchase.clear();
    }


    public void OnWindowClicked() {
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        btnInsert.setDisable(false);
        tvEntries.getSelectionModel().select(null);
        clearTextFields();
    }

    public void OnButtonFindClicked() {
        if (tfSearch.getText().equals("")) {
            ReadHandler readHandler = new ReadHandler(socket, "", entriesList);
            readHandler.start();
        }
        else {
            ReadHandler readHandler = new ReadHandler(socket, tfSearch.getText(), entriesList);
            readHandler.start();
        }
        tvEntries.setItems(entriesList);
        clearTextFields();
    }
}