package com.aetherwars.controller;

import com.aetherwars.event.Event;
import com.aetherwars.event.Publisher;
import com.aetherwars.event.Subscriber;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class HandController implements Initializable, Publisher, Subscriber {
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
