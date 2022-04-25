package com.aetherwars.event;

import com.aetherwars.model.Phase;

public class CurrentPhaseEvent implements Event {

    private Phase phase;

    public CurrentPhaseEvent(Phase phase) {
        this.phase = phase;
    }

    @Override
    public Object getEvent() {
        return this.phase;
    }
}
