package com.aetherwars.controller;

import com.aetherwars.model.Player;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;


import java.net.URL;
import java.util.ResourceBundle;

public class WinController implements Initializable {
    @FXML
    Label labelWinPlayer;

    private Player winner;

    public WinController(Player winner) {
        this.winner = winner;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        labelWinPlayer.setText("Selamat, " + this.winner.getPlayerName() + " menang!");
    }
}
