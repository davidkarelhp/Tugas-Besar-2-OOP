package com.aetherwars;

import com.aetherwars.event.*;
import com.aetherwars.model.Phase;
import com.aetherwars.model.Player;
import com.aetherwars.model.cards.Card;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class GameEngine implements Publisher, Subscriber {
    private GameChannel eventChannel;
    private Player[] players;
    private int currentPlayer;
    private IntegerProperty currentRound;
    private int currentPhase;
    private static Phase[] phases = {Phase.DRAW, Phase.PLAN, Phase.ATTACK, Phase.END};
    private List<Card> backToDeck;
    private Card toHand;

    public GameEngine(Player p1, Player p2, GameChannel eventChannel) {
        this.players = new Player[2];
        this.players[0] = p1;
        this.players[1] = p2;
        this.eventChannel = eventChannel;
        this.currentPlayer = 0;
        this.currentRound = new SimpleIntegerProperty(1);
        this.currentPhase = 0;
    }

    public int getCurrentRound() {
        return currentRound.get();
    }

    public int getCurrentPlayer(){
        return this.currentPlayer;
    }

    public IntegerProperty currentRoundProperty() {
        return currentRound;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setupGame() {
        this.drawBoth();
        publish(new ChangePlayerEvent(this.players[this.currentPlayer]));
        publish(new CurrentPhaseEvent(currentPhase()));
        phaseController();
    }

    public void drawBoth() {
        // di sini draw-nya gak dikembaliin
        this.players[1].drawOnly();
        this.players[0].drawOnly();
    }

    public void nextPhase() {
        this.currentPhase = this.currentPhase == 3 ? 0 : this.currentPhase + 1;
        publish(new CurrentPhaseEvent(currentPhase()));
    }

    public Phase currentPhase() {
        return phases[this.currentPhase];
    }

    public void nextPhaseProcess() {
        this.nextPhase();
        phaseController();
    }

    public void phaseController() {
        if (currentPhase() == Phase.DRAW) {
            // ini draw yang dikembaliin
            this.players[this.currentPlayer].increaseManaLimit();
            this.players[this.currentPlayer].resetMana();
            publish(new DrawPhaseEvent(this.players[this.currentPlayer].draw()));

        } else if (currentPhase() == Phase.PLAN){


        } else if (currentPhase() == Phase.ATTACK) {

        } else if (currentPhase()  == Phase.END) {
            // Jalanin prosedur END phase disini, semua kartu currentplayer statusnya dijadiin belum dipakai dan sebagainya

            if (this.currentPlayer == 1) {
                this.currentRound.set(this.getCurrentRound() + 1);
                this.players[0].getBoard().incrementRound();
                this.players[1].getBoard().incrementRound();
            }

            this.currentPlayer = (this.currentPlayer == 0) ? 1 : 0;

            publish(new ChangePlayerEvent(this.players[this.currentPlayer]));

            // Langsung draw phase lagi setelah end
            this.nextPhaseProcess();
        }
    }

    public void drawnCardClicked(List<Card> cards, int idxCard) {
        toHand = null;
        backToDeck = new ArrayList<>();

        int i = 0;
        for (Card card: cards) {
            System.out.println(card.getName());
            if (i == idxCard) {
                toHand = cards.get(i);
            } else {
                backToDeck.add(cards.get(i));
            }
            i++;
        }

        cards.clear();

        if (this.players[this.currentPlayer].getHand().getHand().size() < 5) {
            this.players[this.currentPlayer].putCardToDeckAndShuffle(backToDeck);
            this.players[this.currentPlayer].addToHand(toHand);
            publish(new ChangePlayerEvent(this.players[this.currentPlayer]));
            nextPhaseProcess();

        } else {
            publish(new HandFullEvent());
        }

    }

    public void discardAndDraw(int idxDiscarded) {
        this.players[this.currentPlayer].getHand().discardAtIndex(idxDiscarded);
        this.players[this.currentPlayer].putCardToDeckAndShuffle(backToDeck);
        this.players[this.currentPlayer].addToHand(toHand);
        publish(new RefreshHandEvent(this.players[this.currentPlayer]));
        nextPhaseProcess();
    }

    public void discard(int idxDiscarded) {
        this.players[this.currentPlayer].getHand().discardAtIndex(idxDiscarded);
        publish(new RefreshHandEvent(this.players[this.currentPlayer]));
    }

    @Override
    public void publish(Event event) {
        this.eventChannel.sendEvent(this, event);
    }

    @Override
    public void onEvent(Event event) {
        try {

            if (event instanceof NextPhaseEvent) {
                nextPhaseProcess();

            } else if (event instanceof DrawnCardClicked) {
                Pair<List<Card>, Integer> pair =  (Pair<List<Card>, Integer>) event.getEvent();
                drawnCardClicked(pair.getKey(), pair.getValue());

            } else if (event instanceof DiscardToDrawEvent) {
                discardAndDraw((int)event.getEvent());

            } else if (event instanceof DiscardEvent) {
                discard((int) event.getEvent());

            } else if (event instanceof MoveToBoardEvent) {
                System.out.println("catched");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
