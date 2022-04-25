package com.aetherwars.event;

import com.aetherwars.controller.MainController;
import com.aetherwars.model.Phase;

import java.util.ArrayList;
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

    public Phase getPhase(){
        return  this.phase;
    }

    public void setPlayerById(int id){
        this.currentPlayer = id;
    }

    public void setPhase(Phase phase){
        this.phase = phase;
    }

    @Override
    public void sendEvent(Publisher publisher, Event event) {
        for (Subscriber sub: this.channel.get(publisher)) {
            sub.onEvent(event);
        }
    }

    @Override
    public void addSubscriber(Publisher publisher, Subscriber subscriber) {
        this.addPublisher(publisher);
        this.channel.get(publisher).add(subscriber);
    }

    @Override
    public void addPublisher(Publisher publisher) {
        this.channel.putIfAbsent(publisher, new ArrayList<>());
    }
}
