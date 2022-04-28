package com.aetherwars.event;

import javafx.util.Pair;

public class ClickEvent implements Event {
    private String clickType;
    private int idxClicked;

    public ClickEvent(String clickType, int idxClicked) {
        this.clickType = clickType;
        this.idxClicked = idxClicked;
    }

    @Override
    public Object getEvent() {
        return new Pair<String, Integer>(this.clickType, this.idxClicked);
    }
}
