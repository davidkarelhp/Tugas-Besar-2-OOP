package com.aetherwars.controller;

import com.aetherwars.event.Event;
import com.aetherwars.event.GameChannel;
import com.aetherwars.event.Publisher;
import com.aetherwars.event.Subscriber;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class BoardController implements Initializable, Publisher, Subscriber {
    @FXML
    StackPane character1;

    @FXML
    StackPane character2;

    @FXML
    StackPane character3;

    @FXML
    StackPane character4;

    @FXML
    StackPane character5;

    private GameChannel channel;

    public BoardController(GameChannel channel) {
        this.channel = channel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void publish(Event event) {

    }

    @Override
    public void onEvent(Event event) {

    }
}
