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
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Label;
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
    StackPane backPane, backHandPane, drawPhase, planPhase, attackPhase, endPhase;

    @FXML
    Button buttonSkip;

    Label[] phases_bar;

    @FXML
    Label labelPlayer1, labelPlayer2, labelRound, labelDeck, labelMana, dataCardLabel, titleCardLabel, descCardLabel, labelHand, atack_p, draw_p, end_p, plan_p;

    @FXML
    ProgressBar healthPlayer1, healthPlayer2;

    @FXML
    GridPane handGrid, gridBoard;

    @FXML
    ImageView imageCardHover;

    private final int MAX_HEALTH = 80;
    private final Color CURRENT_PHASE_COLOR = Color.AQUAMARINE;

    private GameChannel channel;
    private GridPane drawPane;
    private List<StackPane> handList;
    private int currentPlayerIdx = -1;
    private Player currentPlayer;
    private GameEngine engine;

    public MainController(GameChannel channel) {
        this.channel = channel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.phases_bar = new Label[]{draw_p, plan_p, atack_p, end_p};

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

        this.labelPlayer1.textProperty().bind(Bindings.when(
                gameEngine.currentPlayerProperty().isEqualTo(0))
                .then("Current Player: " + players[0].getPlayerName())
                .otherwise(players[0].getPlayerName())
        );

        this.labelPlayer2.textProperty().bind(Bindings.when(
                gameEngine.currentPlayerProperty().isEqualTo(1))
                .then("Current Player: " + players[1].getPlayerName())
                .otherwise(players[1].getPlayerName())
        );

        this.labelRound.textProperty().bind(Bindings.concat("Round ", gameEngine.currentRoundProperty()));

        this.healthPlayer1.progressProperty().bind(players[0].healthPointsProperty().divide(this.MAX_HEALTH));
        this.healthPlayer2.progressProperty().bind(players[1].healthPointsProperty().divide(this.MAX_HEALTH));

        this.healthPlayer1.styleProperty().bind(Bindings
                .when(players[0].healthPointsProperty().greaterThan(50))
                .then("-fx-accent: green;")
                .otherwise(Bindings.when(players[0].healthPointsProperty().greaterThan(20))
                        .then("-fx-accent: yellow;")
                        .otherwise("-fx-accent: red;"))
        );

        this.healthPlayer2.styleProperty().bind(Bindings
                .when(players[1].healthPointsProperty().greaterThan(50))
                .then("-fx-accent: green;")
                .otherwise(Bindings.when(players[1].healthPointsProperty().greaterThan(20))
                        .then("-fx-accent: yellow;")
                        .otherwise("-fx-accent: red;"))
        );

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

        this.channel.addSubscriber(this, leftGridFXML.getController());
        this.channel.addSubscriber(this, rightGridFXML.getController());

        this.channel.addSubscriber(this.engine, leftGridFXML.getController());
        this.channel.addSubscriber(this.engine, rightGridFXML.getController());

        this.channel.addSubscriber(leftGridFXML.getController(), this.engine);
        this.channel.addSubscriber(rightGridFXML.getController(), this.engine);

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
            int idx = i;
            cardPane.setOnMouseClicked(e -> {

                showCardOptions(cardPane, idx);
                try {
                    refreshHandClicked(player, idx);
                } catch (IOException ex) {}
            });

            StackPane.setMargin(cardPane, new Insets(10, 10, 10, 10));
            handList.add(cardPane);
            this.handGrid.add(cardPane, i, 0);
            i++;
        }
    }

    public void refreshHandClicked(Player player, int idxClicked) throws IOException {
        labelHand.setText("");
        buttonSkip.setDisable(false);
        handList = new ArrayList<>();
        handGrid.getChildren().removeIf(node -> GridPane.getColumnIndex(node) != idxClicked);
        List<Card> hand = player.getHand().getHand();
        int i = 0;
        for (Card card: hand) {
            if (i != idxClicked) {
                FXMLLoader cardFXML = new FXMLLoader(getClass().getResource("../Card.fxml"));
                cardFXML.setControllerFactory(c -> new CardController(card, this));
                StackPane cardPane = cardFXML.load();
                int idx = i;
                cardPane.setOnMouseClicked(e -> {

                    showCardOptions(cardPane, idx);
                    try {
                        refreshHandClicked(player, idx);
                    } catch (IOException ex) {
                    }

                });

                StackPane.setMargin(cardPane, new Insets(10, 10, 10, 10));
                handList.add(cardPane);
                this.handGrid.add(cardPane, i, 0);

            }
            i++;
        }
    }

    public void showCardOptions(StackPane cardPane, int idx) {
        StackPane optionPane = new StackPane();
        optionPane.setBackground(new Background(new BackgroundFill(new Color(0.6, 0.6, 0.6, 0.5), CornerRadii.EMPTY, Insets.EMPTY)));
        cardPane.getChildren().add(optionPane);

        Button board = new Button("Board");
        Button discard = new Button("Discard");
        Button cancel = new Button("Cancel");

        StackPane.setMargin(board, new Insets(10, 0, 0, 0));
        StackPane.setMargin(cancel, new Insets(0, 0, 10, 0));

        StackPane.setAlignment(board, Pos.TOP_CENTER);
        StackPane.setAlignment(discard, Pos.CENTER);
        StackPane.setAlignment(cancel, Pos.BOTTOM_CENTER);

        optionPane.getChildren().add(board);
        optionPane.getChildren().add(discard);
        optionPane.getChildren().add(cancel);

        board.setOnAction(e -> {
            publish(new PrepareToMoveToBoardEvent(idx));
            System.out.println("prepare");
        });

        cancel.setOnAction(e -> {
            cardPane.getChildren().remove(optionPane);
            cardPane.setOnMouseClicked(ev -> showCardOptions(cardPane, idx));
        });
        cardPane.setOnMouseClicked(null);

        discard.setOnAction(e -> publish(new DiscardEvent(idx)));

    }

    public void rebindDeckAndMana(Player player) throws IOException {
        labelDeck.textProperty().unbind();
        labelMana.textProperty().unbind();

        labelDeck.textProperty().bind(Bindings.concat(player.getDeck().deckFillProperty(), "/", player.getDeck().getDeckSize()));
        labelMana.textProperty().bind(Bindings.concat(player.manaProperty(), "/", player.manaLimitProperty()));
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
                this.currentPlayer = (Player) event.getEvent();
                this.refreshHand((Player) event.getEvent());
                this.rebindDeckAndMana((Player) event.getEvent());

            } else if (event instanceof CurrentPhaseEvent) {
                this.phaseColoring((Phase) event.getEvent());

                if ((Phase) event.getEvent() == Phase.ATTACK) {
                    for (Node node: handGrid.getChildren()) {
                        node.setOnMouseClicked(null);
                    }
                }

            } else if (event instanceof DrawPhaseEvent) {
                this.displayDraw((List<Card>) event.getEvent());

            } else if (event instanceof HandFullEvent) {
                this.discardToDraw();

            } else if (event instanceof RefreshHandEvent) {
                this.refreshHand((Player) event.getEvent());

            } else if (event instanceof MessageEvent) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message Here!");
                alert.setContentText((String) event.getEvent());
                alert.show();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
