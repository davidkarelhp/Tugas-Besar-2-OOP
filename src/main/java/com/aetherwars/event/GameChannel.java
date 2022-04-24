package com.aetherwars.event;

import com.aetherwars.controller.MainController;
import com.aetherwars.model.Phase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameChannel implements EventChannel {
    private Map<Publisher, List<Subscriber>> channel;
    private MainController mainController;
    private Phase phase;
    private int currentPlayer;

    public GameChannel() {
        this.channel = new HashMap<>();
        this.phase = Phase.DRAW;
    }

    public void setController(MainController controller) {
        this.mainController = controller;
    }

    @Override
    public void sendEvent(Publisher publisher, Event event) {

    }

    @Override
    public void addSubscriber(Publisher publisher, Subscriber subscriber) {

    }

    @Override
    public void addPublisher(Publisher publisher) {

    }
}
