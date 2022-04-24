package com.aetherwars.controller;

import com.aetherwars.GameEngine;
import com.aetherwars.event.GameChannel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;


import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    Button buttonSkip;

    public MainController(GameChannel channel) {

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.buttonSkip.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setContentText("Terklik");
            alert.show();
        });
    }

    public void startGame(GameEngine gameEngine) {
        System.out.println("halo");
    }
}
