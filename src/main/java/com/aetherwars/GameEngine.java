package com.aetherwars;

import com.aetherwars.event.*;
import com.aetherwars.model.Phase;
import com.aetherwars.model.Player;
import com.aetherwars.model.cards.Card;
import com.aetherwars.model.cards.character.Character;
import com.aetherwars.model.cards.character.SummonedCharacter;
import com.aetherwars.model.cards.spell.Spell;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class GameEngine implements Publisher, Subscriber {
    private GameChannel eventChannel;
    private Player[] players;
    private IntegerProperty currentPlayer;
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
        this.currentPlayer = new SimpleIntegerProperty(0);
        this.currentRound = new SimpleIntegerProperty(1);
        this.currentPhase = 0;
    }

    public int getCurrentRound() {
        return currentRound.get();
    }

    public int getCurrentPlayer() {
        return currentPlayer.get();
    }

    public IntegerProperty currentPlayerProperty() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer.set(currentPlayer);
    }

    public IntegerProperty currentRoundProperty() {
        return currentRound;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setupGame() {
        this.drawBoth();
        publish(new ChangePlayerEvent(this.players[this.getCurrentPlayer()]));
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
            this.players[this.getCurrentPlayer()].increaseManaLimit();
            this.players[this.getCurrentPlayer()].resetMana();

            List<Card> drawChoiceCards = this.players[this.getCurrentPlayer()].draw();

            if (this.players[this.getCurrentPlayer()].getDeck().getDeckFill() <= 0) {
                publish(new WinEvent(this.players[this.getCurrentPlayer() == 1 ? 0 : 1]));
            } else {
                publish(new DrawPhaseEvent(this.players[this.getCurrentPlayer()].draw()));
            }

        } else if (currentPhase() == Phase.PLAN) {
            publish(new RefreshBoardEvent(this.players[this.getCurrentPlayer()], currentPhase()));
        } else if (currentPhase() == Phase.ATTACK) {
            publish(new AttackPhaseEvent(this.players[this.getCurrentPlayer()]));

        } else if (currentPhase()  == Phase.END) {
            // Jalanin prosedur END phase disini, semua kartu currentplayer statusnya dijadiin belum dipakai dan sebagainya

            if (this.getCurrentPlayer() == 1) {
                this.currentRound.set(this.getCurrentRound() + 1);
                this.players[0].getBoard().incrementRound();
                this.players[1].getBoard().incrementRound();
            }

            this.setCurrentPlayer((this.getCurrentPlayer() == 0) ? 1 : 0);

            publish(new ChangePlayerEvent(this.players[this.getCurrentPlayer()]));
            publish(new RefreshBoardEvent(this.players[this.getCurrentPlayer()], null));

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

        if (this.players[this.getCurrentPlayer()].getHand().getHand().size() < 5) {
            this.players[this.getCurrentPlayer()].putCardToDeckAndShuffle(backToDeck);
            this.players[this.getCurrentPlayer()].addToHand(toHand);
            publish(new ChangePlayerEvent(this.players[this.getCurrentPlayer()]));
            nextPhaseProcess();

        } else {
            publish(new HandFullEvent());
        }

    }

    public void discardAndDraw(int idxDiscarded) {
        this.players[this.getCurrentPlayer()].getHand().discardAtIndex(idxDiscarded);
        this.players[this.getCurrentPlayer()].putCardToDeckAndShuffle(backToDeck);
        this.players[this.getCurrentPlayer()].addToHand(toHand);
        publish(new RefreshHandEvent(this.players[this.getCurrentPlayer()], currentPhase()));
        nextPhaseProcess();
    }

    public void discard(int idxDiscarded) {
        this.players[this.getCurrentPlayer()].getHand().discardAtIndex(idxDiscarded);
        publish(new RefreshHandEvent(this.players[this.getCurrentPlayer()], currentPhase()));
    }

    public void moveToBoardEventHandler(int[] idxHandBoard, Player player) {
        int idxHand = idxHandBoard[0];
        int idxBoard = idxHandBoard[1];

        if (this.players[this.getCurrentPlayer()].getHand().getCardAtIndex(idxHand) instanceof  Character && !player.equals(this.players[this.getCurrentPlayer()])) {
            publish(new MessageEvent("Tidak bisa memasukkan karakter ke board lawan."));

        } else {
            System.out.println(idxHand);
            if (this.players[this.getCurrentPlayer()].getMana() < this.players[this.getCurrentPlayer()].getHand().getCardAtIndex(idxHand).getMana()) {
                publish(new MessageEvent("Mana tidak mencukupi."));

            } else {
                if (this.players[this.getCurrentPlayer()].getHand().getCardAtIndex(idxHand) instanceof  Character) {
                    Character c = (Character) this.players[this.getCurrentPlayer()].getHand().getCardAtIndex(idxHand);
                    player.getBoard().putInSlot(idxBoard, c);

                    this.players[this.getCurrentPlayer()].setMana(this.players[this.getCurrentPlayer()].getMana() - this.players[this.getCurrentPlayer()].getHand().getCardAtIndex(idxHand).getMana());
                    this.players[this.getCurrentPlayer()].getHand().discardAtIndex(idxHand);

                } else if (this.players[this.getCurrentPlayer()].getHand().getCardAtIndex(idxHand) instanceof Spell) {
                    if (player.getBoard().getAtSlot(idxBoard) != null) {
                        Spell s = (Spell) this.players[this.getCurrentPlayer()].getHand().getCardAtIndex(idxHand);

                        this.players[this.getCurrentPlayer()].setMana(this.players[this.getCurrentPlayer()].getMana() - this.players[this.getCurrentPlayer()].getHand().getCardAtIndex(idxHand).getMana());

                        player.getBoard().getAtSlot(idxBoard).addSpell(s);
                        this.players[this.getCurrentPlayer()].getHand().discardAtIndex(idxHand);

                    } else {
                        System.out.println("Slot kosong!");
                        publish(new MessageEvent("Tidak ada karakter pada slot ini!"));
                    }
                }
            }

        }

        publish(new RefreshBoardEvent(this.players[this.getCurrentPlayer()], currentPhase()));
        publish(new RefreshHandEvent(this.players[this.getCurrentPlayer()], currentPhase()));
    }

    public void throwOutFromBoardEventHandler(int idxBoard) {
        System.out.println("idb = " + idxBoard);
        System.out.println(this.players[this.getCurrentPlayer()].getBoard().getAtSlot(idxBoard));
        this.players[this.getCurrentPlayer()].getBoard().removeCardAtSlot(idxBoard);
        System.out.println(this.players[this.getCurrentPlayer()].getBoard().getAtSlot(idxBoard));
        publish(new RefreshBoardEvent(this.players[this.getCurrentPlayer()], currentPhase()));
    }

    public void addExpEventHandler(int[] arr) {
        this.players[this.getCurrentPlayer()].setMana(this.players[this.getCurrentPlayer()].getMana() - arr[0]);
        this.players[this.getCurrentPlayer()].getBoard().getAtSlot(arr[1]).addExp(arr[0]);
    }

    public void tryToAttackPlayerEventHandler(int idxBoard) {
        Player enemy = (this.getCurrentPlayer() == 0) ? this.players[1] : this.players[0];

        if (enemy.getBoard().isEmpty()) {
            this.players[this.getCurrentPlayer()].getBoard().getAtSlot(idxBoard).attackPlayer(enemy);

            if (enemy.getHealthPoints() <= 0) {
                publish(new WinEvent(this.players[this.getCurrentPlayer()]));
            } else {
                publish(new RefreshBoardEvent(this.players[this.getCurrentPlayer()], currentPhase()));
            }

        } else {
            publish(new PrepareToAttackCharacterEvent(this.players[this.getCurrentPlayer()], idxBoard));
        }

    }

    public void attackCharacterEventHandler(int idxAttacker, int idxDefender) {
        SummonedCharacter attacker = this.players[this.getCurrentPlayer()].getBoard().getAtSlot(idxAttacker);
        SummonedCharacter defender = this.players[this.getCurrentPlayer() == 0 ? 1 : 0].getBoard().getAtSlot(idxDefender);

        attacker.attackEnemy(defender);

        if (defender.getHealth() <= 0) {
            this.players[this.getCurrentPlayer() == 0 ? 1 : 0].getBoard().removeCardAtSlot(idxDefender);
        }

        publish(new RefreshBoardEvent(this.players[this.getCurrentPlayer()], currentPhase()));
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
                Pair<int[], Player> e = (Pair<int[], Player>) event.getEvent();
                moveToBoardEventHandler(e.getKey(), e.getValue());

            } else if (event instanceof ThrowOutFromBoardEvent) {
                throwOutFromBoardEventHandler((int) event.getEvent());

            } else if (event instanceof AddExpEvent) {
                addExpEventHandler((int[]) event.getEvent());

            } else if (event instanceof TryToAttackPlayerEvent) {
                tryToAttackPlayerEventHandler((int) event.getEvent());

            } else if (event instanceof AttackCharacterEvent) {
                int[] idxAttackerDefender = (int[]) event.getEvent();
                attackCharacterEventHandler(idxAttackerDefender[0], idxAttackerDefender[1]);

            } else if (event instanceof ClickEvent) {
                Pair<String, Integer> e = (Pair<String, Integer>) event.getEvent();

                if (e.getKey().equals("hand")) {
                    publish(new RefreshHandClickedEvent(this.players[this.getCurrentPlayer()], e.getValue()));
                    publish(new RefreshBoardEvent(this.players[this.getCurrentPlayer()], currentPhase()));

                } else if (e.getKey().equals("board")) {
                    System.out.println("board clicked");
                    publish(new RefreshHandEvent(this.players[this.getCurrentPlayer()], currentPhase()));
                    publish(new RefreshBoardClickedEvent(this.players[this.getCurrentPlayer()], currentPhase(), e.getValue()));

                }

            } else if (event instanceof CancelEvent) {
                System.out.println("cancel event");
                publish(new RefreshBoardEvent(this.players[this.getCurrentPlayer()], currentPhase()));
                publish(new RefreshHandEvent(this.players[this.getCurrentPlayer()], currentPhase()));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
