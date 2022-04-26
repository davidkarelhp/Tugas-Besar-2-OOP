package com.aetherwars.controller;

import com.aetherwars.GameEngine;
import com.aetherwars.event.EventChannel;
import com.aetherwars.event.GameChannel;
import com.aetherwars.model.Phase;
import com.aetherwars.event.*;
import com.aetherwars.model.Phase;
import com.aetherwars.model.Player;
import com.aetherwars.model.cards.Card;
import com.aetherwars.model.cards.character.Character;
import com.aetherwars.model.cards.spell.Spell;
import com.aetherwars.model.cards.spell.enums.SpellType;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventType;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable, Publisher, Subscriber {
    @FXML
    StackPane backPane;

    @FXML
    Button buttonSkip;

    @FXML
    Label atack_p, draw_p, end_p, plan_p;

    Phase[] phases = new Phase[] { Phase.DRAW, Phase.PLAN, Phase.ATTACK, Phase.END };

    Label[] phases_bar;

    int phase_id = 0;

    @FXML
    Label labelPlayer1;

    @FXML
    Label labelPlayer2;

    @FXML
    Label labelRound;

    @FXML
    ProgressBar healthPlayer1;

    @FXML
    ProgressBar healthPlayer2;

    @FXML
    GridPane handGrid;

    @FXML
    Label labelDeck;

    @FXML
    Label labelMana;

    @FXML
    StackPane drawPhase;

    @FXML
    StackPane attackPhase;

    @FXML
    StackPane endPhase;

    @FXML
    StackPane planPhase;

    @FXML
    Label dataCardLabel, titleCardLabel, descCardLabel;

    @FXML
    ImageView imageCardHover;

    @FXML
    Label labelHand;

    @FXML
    GridPane gridBoard;

    private final int MAX_HEALTH = 80;
    private final Color CURRENT_PHASE_COLOR = Color.AQUAMARINE;

    private GameChannel channel;
    private GridPane drawPane;
    private List<StackPane> handList;

    GameEngine engine;

    public MainController(GameChannel channel) {
        this.channel = channel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.phases_bar = new Label[]{draw_p, plan_p, atack_p, end_p};
        this.healthPlayer1.setStyle("-fx-accent: green;");
        this.healthPlayer2.setStyle("-fx-accent: green;");

        this.buttonSkip.setOnAction(e -> {
            publish(new NextPhaseEvent());
        });

        this.buttonSkip.setOnMouseEntered(e -> {
           this.buttonSkip.setStyle("-fx-background-color: grey;");
           this.buttonSkip.getScene().setCursor(Cursor.HAND);
        });

        this.buttonSkip.setOnMouseExited(e -> {
            this.buttonSkip.setStyle("-fx-background-color: black;");
            this.buttonSkip.getScene().setCursor(Cursor.DEFAULT);
        });

        titleCardLabel.setStyle("-fx-text-fill: white;");
        dataCardLabel.setStyle("-fx-text-fill: white;");
        descCardLabel.setStyle("-fx-text-fill: white;");

        titleCardLabel.setText("-------");
        dataCardLabel.setText("------------------------------------");
        descCardLabel.setText("------------------------------------");



    }

    public void startGame(GameEngine gameEngine) throws IOException {

        this.engine = gameEngine;

        Player[] players = gameEngine.getPlayers();

        this.labelPlayer1.setText(players[0].getPlayerName());
        this.labelPlayer2.setText(players[1].getPlayerName());

        this.labelRound.textProperty().bind(Bindings.concat("Round ", gameEngine.currentRoundProperty()));

        this.healthPlayer1.progressProperty().bind(players[0].healthPointsProperty().divide(this.MAX_HEALTH));
        this.healthPlayer2.progressProperty().bind(players[1].healthPointsProperty().divide(this.MAX_HEALTH));

        this.channel.addSubscriber(this, gameEngine);
        this.channel.addSubscriber(gameEngine, this);

        FXMLLoader leftGridFXML = new FXMLLoader(getClass().getResource("../leftBoard.fxml"));
        FXMLLoader rightGridFXML = new FXMLLoader(getClass().getResource("../rightBoard.fxml"));

        leftGridFXML.setControllerFactory(c -> new BoardController(this.channel, players[0]));
        rightGridFXML.setControllerFactory(c -> new BoardController(this.channel, players[1]));

        GridPane leftGrid = null;
        GridPane rightGrid = null;

        try {
            leftGrid = leftGridFXML.load();
            rightGrid = rightGridFXML.load();

        } catch (IOException e) {
        }

        GridPane.setHalignment(leftGrid, HPos.LEFT);
        GridPane.setHalignment(rightGrid, HPos.RIGHT);

        gridBoard.add(leftGrid, 0, 0);
        gridBoard.add(rightGrid, 1, 0);

        gameEngine.setupGame();
    }

    public void displayDraw(List<Card> cards) throws IOException {
        if (backPane.getChildren().contains(drawPane)) {
            backPane.getChildren().remove(drawPane);
        }

        // Contoh kalau mau nampilin sesuatu pakai button, di sini yang ditampilin tuh 3 kartu yang dipilih pas nge-draw
        drawPane = new GridPane();
        // drawPane.setGridLinesVisible(true);

        RowConstraints constraintsRow = new RowConstraints();
        constraintsRow.setVgrow(Priority.ALWAYS);
        drawPane.getRowConstraints().add(constraintsRow);

        int i = 0;

        for (Card card: cards) {
            FXMLLoader cardFXML = new FXMLLoader(getClass().getResource("../Card.fxml"));
            cardFXML.setControllerFactory(c -> new CardController(card, this));
            StackPane cardPane = cardFXML.load();

            StackPane.setMargin(cardPane, new Insets(10, 10, 10, 10));

            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setHgrow(Priority.ALWAYS);
            drawPane.getColumnConstraints().add(constraints);

            cardPane.setMaxSize(150, 300);
            GridPane.setHalignment(cardPane, HPos.CENTER);

            drawPane.add(cardPane, i, 0);
            int idxCard = i;
            cardPane.setOnMouseClicked(e -> this.onDrawnCardClicked(cards, idxCard));
            i++;

        }
        drawPane.setBackground(new Background(new BackgroundFill(new Color(0.6, 0.6, 0.6, 0.5), CornerRadii.EMPTY, Insets.EMPTY)));
        backPane.getChildren().add(drawPane);
    }

    public void onDrawnCardClicked(List<Card> cards, int i) {
        // Method kalau salah satu kartu yang di-draw diklik, untuk sekarang kembali ke main panel aja
        backPane.getChildren().remove(drawPane);

        publish(new DrawnCardClicked(cards, i));
    }

    public void onHoverCard(Card card){

        File file = null;
        try {
            file = new File(getClass().getResource("../" + card.getImagePath()).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        titleCardLabel.setStyle("-fx-text-fill: black;");
        dataCardLabel.setStyle("-fx-text-fill: black;");
        descCardLabel.setStyle("-fx-text-fill: black; -fx-font-style: italic;");

        titleCardLabel.setText(card.getName());
        if (card instanceof Character) {
            Character character = (Character) card;
            dataCardLabel.setText("ATK: " +  character.getBaseAttack() + "\nHP: " + character.getBaseHealth() + "\nLevel: " + character.getLevel() + "\nType: " + character.getCharacterType());

        } else if (card instanceof Spell) {
            Spell spell = (Spell) card;
            if (spell.getType() == SpellType.POTION) {
                dataCardLabel.setText("SPELL PTN");
            } else if (spell.getType() == SpellType.LEVELUP) {
                dataCardLabel.setText("SPELL LEVEL UP");
            } else if (spell.getType() == SpellType.LEVELDOWN) {
                dataCardLabel.setText("SPELL LEVEL DOWN");
            } else if (spell.getType() == SpellType.SWAP) {
                dataCardLabel.setText("SPELL SWAP");
            } else if (spell.getType() == SpellType.MORPH) {
                dataCardLabel.setText("SPELL MORPH");
            }
        }
        descCardLabel.setText("\"" +  card.getDescription() + "\"");

        imageCardHover.setImage(new Image(file.toURI().toString(), 60, 80, true, true));

    }

    public void onUnHoverCard(Card card){
        titleCardLabel.setStyle("-fx-text-fill: white;");
        dataCardLabel.setStyle("-fx-text-fill: white;");
        descCardLabel.setStyle("-fx-text-fill: white;");

        imageCardHover.setImage(null);
    }

    public void refreshHand(Player player) throws IOException {
        labelHand.setText("");
        buttonSkip.setDisable(false);
        handList = new ArrayList<>();
        handGrid.getChildren().clear();
        List<Card> hand = player.getHand().getHand();
        int i = 0;
        for (Card card: hand) {
            FXMLLoader cardFXML = new FXMLLoader(getClass().getResource("../Card.fxml"));
            cardFXML.setControllerFactory(c -> new CardController(card, this));
            StackPane cardPane = cardFXML.load();

            StackPane.setMargin(cardPane, new Insets(10, 10, 10, 10));
            handList.add(cardPane);
            this.handGrid.add(cardPane, i, 0);
            i++;
        }
    }

    public void rebindDeckAndMana(Player player) throws IOException {
        labelDeck.textProperty().unbind();
        labelMana.textProperty().unbind();

        labelDeck.textProperty().bind(Bindings.concat(player.getDeck().deckFillProperty(), "/", player.getDeck().getDeckSize()));
        labelMana.textProperty().bind(Bindings.concat(player.manaProperty(), "/" + player.getManaLimit()));
    }

    public void phaseColoring(Phase phase) {
        switch (phase) {
            case DRAW:
                drawPhase.setBackground(new Background(new BackgroundFill(this.CURRENT_PHASE_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
                endPhase.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                break;

            case PLAN:
                drawPhase.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                planPhase.setBackground(new Background(new BackgroundFill(this.CURRENT_PHASE_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
                break;

            case ATTACK:
                planPhase.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                attackPhase.setBackground(new Background(new BackgroundFill(this.CURRENT_PHASE_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
                break;

            case END:
                attackPhase.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                endPhase.setBackground(new Background(new BackgroundFill(this.CURRENT_PHASE_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
                break;
        }
    }

    public void discardToDraw() {
        buttonSkip.setDisable(true);
        labelHand.setText("Discard salah satu kartu!");

        int i = 0;
        for (StackPane cardPane: handList) {
            int idxDiscard = i;
            cardPane.setOnMouseClicked(e -> publish(new DiscardToDrawEvent(idxDiscard)));
            i++;
        }
    }

    @Override
    public void publish(Event event) {
        this.channel.sendEvent(this, event);
    }

    @Override
    public void onEvent(Event event) {
        try {

            if (event instanceof ChangePlayerEvent) {
                this.refreshHand((Player) event.getEvent());
                this.rebindDeckAndMana((Player) event.getEvent());

            } else if (event instanceof CurrentPhaseEvent) {
                this.phaseColoring((Phase) event.getEvent());

            } else if (event instanceof DrawPhaseEvent) {
                this.displayDraw((List<Card>) event.getEvent());

            } else if (event instanceof HandFullEvent) {
                this.discardToDraw();
            }

        } catch (IOException e) {
            e.printStackTrace();


        }

    }



    public void currentPhase(ActionEvent event){
        phases_bar[phase_id].setStyle("-fx-background-color : darkgray;" + "-fx-color: dimgray");
        phase_id++;
        switch(phase_id){
            case 1:
                System.out.println("phase 1");
                break;

            case 2:
                System.out.println("phase 2");
                break;

            case 3:
                System.out.println(phase_id);
                break;

            case 4:
                System.out.println(phase_id);
                break;
//                if (!this.targeting.isEmpty()) {
//                    this.targeting.get(0).toggleSelected();
//                    for (SummonedCharacterController summonedchara_controller : this.player_controllers[this.channel
//                            .getPlayerID() % 2 + 1].getSummonedCharaController()) {
//                        summonedchara_controller.setHinting(false);
//                    }
//                    this.player_controllers[this.channel.getPlayerID() % 2 + 1].setHinting(false);
//                    this.targeting.clear();
//                }
//
//                int cur_player = this.engine.getCurrentPlayer()
//                if (this.game_engine.getPlayer(cur_player).getHand().size() > Player.MAX_HAND) {
//                    this.channel.setPhase(Phase.DISCARD);
//                }
//                break;
//            case 4:
//                int prev_player = game_engine.getCurPlayer() % 2 + 1;
//                if (this.game_engine.getPlayer(prev_player).getHand().size() > Player.MAX_HAND) {
//                    phase_id--;
//                    phase_bar[phase_id].setStyle("-fx-background-color: aquamarine;" + "-fx-color: black");
//                    AlertBox.display(1280 / 1.5, 720 / 1.5, "Hand card limit exceeded", "Discard one card to continue.\nDouble Click to Discard.");
//                    return;
//                }
//
//                for (int i = 1; i <= 2; i++) {
//                    this.player_controllers[i].flipHand();
//                }
//                break;
            default:
                break;

        }

        phase_id %= 4;
//        this.engine.stageController(phases[phase_id]);
//        if (phase_id == 0) {
//            this.channel.setPlayerById(this.engine.getCurrentPlayer());
//        }
//        if (this.channel.getPhase() != Phase.DISCARD) {
//            this.channel.setPhase(phases[phase_id]);
//        }

        phases_bar[phase_id].setStyle("-fx-background-color: aquamarine;" + "-fx-color: black");
//        if (phase_id==0) {sleep(500, true);}
    }
}
