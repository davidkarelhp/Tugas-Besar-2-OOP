package com.aetherwars.controller;

import com.aetherwars.event.*;
import com.aetherwars.model.Phase;
import com.aetherwars.model.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.net.URL;
import java.util.ResourceBundle;

public class BoardController implements Initializable, Publisher, Subscriber {
    @FXML
    StackPane character1, character2, character3, character4, character5;

    private GameChannel channel;
    private Player player;
    private StackPane[] charArr;
    private AnchorPane[] sumCharArr;

    public BoardController(GameChannel channel, Player player) {
        this.channel = channel;
        this.player = player;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.charArr = new StackPane[]{character1, character2, character3, character4, character5};
    }

    public void prepareToMoveToBoardEventHandler(int idxHand) {
//        refreshBoard(this.player.equals(player));
        for (int i = 0; i < 5; i++) {
            int idx = i;

            int[] idxHandBoard = new int[]{idxHand, idx};
            this.charArr[i].setOnMouseClicked(e -> {
                publish(new MoveToBoardEvent(idxHandBoard, this.player));
                System.out.println("move");
            });

            this.charArr[i].setOnMouseEntered(e -> {
                this.charArr[idx].getScene().setCursor(Cursor.HAND);
                this.charArr[idx].setStyle("-fx-border-color: gold;");
            });

            this.charArr[i].setOnMouseExited(e -> {
                this.charArr[idx].getScene().setCursor(Cursor.DEFAULT);
                this.charArr[idx].setStyle("-fx-border-color: black;");
            });

            if (this.sumCharArr[i] != null) {
                this.sumCharArr[i].setOnMouseClicked(null);

            }

        }
    }

    public void refreshBoard(boolean currentTurn, Phase phase) {
        publish(new MoveInfoUpEvent());
        System.out.println("refrboard");
        sumCharArr = new AnchorPane[5];
        for (int i = 0; i < 5; i++) {
            int idx = i;
            this.charArr[i].getChildren().clear();

            if (this.player.getBoard().getAtSlot(i) != null) {
//                if (currentTurn) {
                this.charArr[i].setOnMouseEntered(e -> {
                    if (this.player.getBoard().getAtSlot(idx).isPlayable() && currentTurn) {
                        this.charArr[idx].getScene().setCursor(Cursor.HAND);
                    }

                    this.charArr[idx].setStyle("-fx-border-color: gold;");
                });

                this.charArr[i].setOnMouseExited(e -> {
                    this.charArr[idx].getScene().setCursor(Cursor.DEFAULT);
                    this.charArr[idx].setStyle("-fx-border-color: black;");
                });

//                }

                FXMLLoader sumCharFXML = new FXMLLoader(getClass().getResource("../SummonedCharacter.fxml"));

                sumCharFXML.setControllerFactory(c -> new SummonedCharacterController(this.channel, this.player.getBoard().selectedChar(idx)));
                AnchorPane sumCharPane = null;

                System.out.println("before try");
                try {
                    sumCharPane = sumCharFXML.load();
                    sumCharArr[i] = sumCharPane;
                    this.charArr[i].getChildren().add(sumCharPane);

                    if (currentTurn) {
//                        System.out.println("phase: " + phase.toString());
                        if (phase == Phase.PLAN) {
                            System.out.println("plan");
                            sumCharPane.setOnMouseClicked(e -> {
                                showCharacterOptions(this.charArr[idx], idx);
                                publish(new ClickEvent("board", idx));
                            });

                        } else if (phase == Phase.ATTACK && this.player.getBoard().getAtSlot(idx).isPlayable()) {
                            sumCharPane.setOnMouseClicked(e -> {
                                showCharacterAttackOptions(this.charArr[idx], idx);
                                publish(new ClickEvent("board", idx));
                            });
                            System.out.println("draw");

                        }
                    }
                        System.out.println("after phase");

                        AnchorPane temp = sumCharPane;

                        sumCharPane.setOnMouseEntered(e -> {
                            temp.setBackground(new Background(new BackgroundFill(new Color(0.6, 0.6, 0.6, 0.5), CornerRadii.EMPTY, Insets.EMPTY)));
                            publish(new SummonedCharacterHoverEvent(this.player.getBoard().getAtSlot(idx)));
                            System.out.println("Summonedcharacter hover");
                        });

                        sumCharPane.setOnMouseExited(e -> {
                            temp.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                            publish(new SummonedCharacterUnhoverEvent());
                        });

                    System.out.println("success try");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("fail try");
                }

            } else {
                sumCharArr[i] = null;
                this.charArr[i].setOnMouseEntered(null);
                this.charArr[i].setOnMouseExited(null);
                this.charArr[i].getScene().setCursor(Cursor.DEFAULT);
                this.charArr[i].setStyle("-fx-border-color: black;");
            }
        }
    }

    public void refreshBoardClicked(boolean currentTurn, Phase phase, int idxClicked) {
        publish(new MoveInfoUpEvent());
        System.out.println("refrboardClicked");

        for (int i = 0; i < 5; i++) {
            if (i != idxClicked) {
                sumCharArr[i] = null;

            }
        }
        for (int i = 0; i < 5; i++) {
            int idx = i;

            if (i != idxClicked) {
                this.charArr[i].getChildren().clear();
            }

            if (this.player.getBoard().getAtSlot(i) != null && i != idxClicked) {
//                if (currentTurn) {
                this.charArr[i].setOnMouseEntered(e -> {
                    if (this.player.getBoard().getAtSlot(idx).isPlayable() && currentTurn) {
                        this.charArr[idx].getScene().setCursor(Cursor.HAND);
                    }

                    this.charArr[idx].setStyle("-fx-border-color: gold;");
                });

                this.charArr[i].setOnMouseExited(e -> {
                    this.charArr[idx].getScene().setCursor(Cursor.DEFAULT);
                    this.charArr[idx].setStyle("-fx-border-color: black;");
                });

//                }

                FXMLLoader sumCharFXML = new FXMLLoader(getClass().getResource("../SummonedCharacter.fxml"));

                sumCharFXML.setControllerFactory(c -> new SummonedCharacterController(this.channel, this.player.getBoard().selectedChar(idx)));
                AnchorPane sumCharPane = null;

                System.out.println("before try");
                try {
                    sumCharPane = sumCharFXML.load();
                    sumCharArr[i] = sumCharPane;
                    this.charArr[i].getChildren().add(sumCharPane);

                    if (currentTurn) {
//                        System.out.println("phase: " + phase.toString());
                        if (phase == Phase.PLAN) {
                            System.out.println("plan");
                            sumCharPane.setOnMouseClicked(e -> {
                                showCharacterOptions(this.charArr[idx], idx);
                                publish(new ClickEvent("board", idx));
                            });

                        } else if (phase == Phase.ATTACK && this.player.getBoard().getAtSlot(idx).isPlayable()) {
                            sumCharPane.setOnMouseClicked(e -> {
                                showCharacterAttackOptions(this.charArr[idx], idx);
                                publish(new ClickEvent("board", idx));
                            });
                            System.out.println("draw");

                        }
                    }
                    System.out.println("after phase");

                    AnchorPane temp = sumCharPane;

                    sumCharPane.setOnMouseEntered(e -> {
                        temp.setBackground(new Background(new BackgroundFill(new Color(0.6, 0.6, 0.6, 0.5), CornerRadii.EMPTY, Insets.EMPTY)));
                        publish(new SummonedCharacterHoverEvent(this.player.getBoard().getAtSlot(idx)));
                        System.out.println("Summonedcharacter hover");
                    });

                    sumCharPane.setOnMouseExited(e -> {
                        temp.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                        publish(new SummonedCharacterUnhoverEvent());
                    });

                    System.out.println("success try");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("fail try");
                }

            } else {
                sumCharArr[i] = null;
                this.charArr[i].setOnMouseEntered(null);
                this.charArr[i].setOnMouseExited(null);
                this.charArr[i].getScene().setCursor(Cursor.DEFAULT);
                this.charArr[i].setStyle("-fx-border-color: black;");
            }
        }
    }

    public void showCharacterOptions(StackPane characterPane, int idx) {
        StackPane optionPane = new StackPane();
        optionPane.setBackground(new Background(new BackgroundFill(new Color(0.6, 0.6, 0.6, 0.5), CornerRadii.EMPTY, Insets.EMPTY)));
        characterPane.getChildren().add(optionPane);

        Button throwOut = new Button("Throw Out");
        Button cancel = new Button("Cancel");

        throwOut.setPrefWidth(80);
        cancel.setPrefWidth(80);

        StackPane.setMargin(throwOut, new Insets(10, 10, 10, 10));
        StackPane.setMargin(cancel, new Insets(10, 10, 10, 10));

        StackPane.setAlignment(throwOut, Pos.TOP_CENTER);
        StackPane.setAlignment(cancel, Pos.BOTTOM_CENTER);

        optionPane.getChildren().add(throwOut);
        optionPane.getChildren().add(cancel);

        throwOut.setOnAction(e -> publish(new ThrowOutFromBoardEvent(idx)));

        cancel.setOnAction(e -> {
            characterPane.getChildren().remove(optionPane);
            characterPane.setOnMouseClicked(ev -> showCharacterOptions(characterPane, idx));
            publish(new CancelEvent());
        });
        characterPane.setOnMouseClicked(null);

        if (this.player.getBoard().getAtSlot(idx).getLevel() < 10) {

            Button exp = new Button("Add EXP");
            exp.setPrefWidth(80);
            StackPane.setAlignment(exp, Pos.CENTER);
            optionPane.getChildren().add(exp);

            StackPane expPane = new StackPane();
            expPane.setBackground(new Background(new BackgroundFill(Color.THISTLE, CornerRadii.EMPTY, Insets.EMPTY)));

            Button addExp = new Button("Add EXP");
            Button cancelExp = new Button("Cancel");
            Label labelExp = new Label();

            Slider s = new Slider();
            s.setBlockIncrement(1);
            s.setMin(0);

            if (this.player.getBoard().getAtSlot(idx).getLevel() < 9) {
                s.maxProperty().bind(this.player.manaProperty());
            } else {
                s.setMax(Math.min(this.player.getMana(), 17 - this.player.getBoard().getAtSlot(idx).getExp()));
            }
            s.setValue(0);

            s.valueProperty().addListener((obs, oldval, newVal) ->
                    s.setValue(newVal.intValue()));

            labelExp.textProperty().bind(s.valueProperty().asString());
    //        labelExp.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

            StackPane.setAlignment(s, Pos.TOP_CENTER);
            StackPane.setAlignment(labelExp, Pos.CENTER);
            StackPane.setAlignment(addExp, Pos.BOTTOM_CENTER);

            StackPane.setMargin(s, new Insets(10, 0, 0, 0));
            StackPane.setMargin(addExp, new Insets(0, 0, 10, 0));

            expPane.getChildren().add(s);
            expPane.getChildren().add(addExp);
            expPane.getChildren().add(labelExp);

            exp.setOnAction(e -> {
                characterPane.getChildren().remove(optionPane);
                characterPane.getChildren().add(expPane);
            });

            addExp.setOnAction(e -> {
                System.out.println(s.getValue());
                publish(new AddExpEvent((int) s.getValue(), idx));

                characterPane.getChildren().remove(expPane);
            });

            cancelExp.setOnAction(e -> {
                characterPane.getChildren().remove(expPane);
                characterPane.getChildren().add(optionPane);
                publish(new CancelEvent());
            });
        }

    }

    public void changePlayerEventHandler(Player player) {
//        publish(new MoveInfoUpEvent());
        if (!this.player.equals(player)) {
            for (int i = 0; i < 5; i++) {
                this.charArr[i].setOnMouseClicked(null);
                this.charArr[i].setOnMouseEntered(null);
                this.charArr[i].setOnMouseExited(null);
                this.charArr[i].getChildren().clear();
            }
        }

        refreshBoard(this.player.equals(player), null);
    }

    public void attackPhaseEventHandler(Player p) {
        if (p.equals(this.player)) {
            for (int i = 0; i < 5; i++) {
                if (this.player.getBoard().getAtSlot(i) != null) {
                    int idx = i;
                    this.sumCharArr[i].setOnMouseClicked(e -> {
                        showCharacterAttackOptions(this.charArr[idx], idx);
                        publish(new ClickEvent("board", idx));
                    });

                }
            }
        }
    }

    public void showCharacterAttackOptions(StackPane characterPane, int idx) {
        StackPane optionPane = new StackPane();
        optionPane.setBackground(new Background(new BackgroundFill(new Color(0.6, 0.6, 0.6, 0.5), CornerRadii.EMPTY, Insets.EMPTY)));
        characterPane.getChildren().add(optionPane);

        Button attack = new Button("Attack");
        Button cancel = new Button("Cancel");

        StackPane.setMargin(attack, new Insets(10, 10, 10, 10));
        StackPane.setMargin(cancel, new Insets(10, 10, 10, 10));

        StackPane.setAlignment(attack, Pos.TOP_CENTER);
        StackPane.setAlignment(cancel, Pos.BOTTOM_CENTER);

        optionPane.getChildren().add(attack);
        optionPane.getChildren().add(cancel);

        attack.setOnAction( e -> {
            System.out.println(idx);
            publish(new TryToAttackPlayerEvent(idx));
        });

        cancel.setOnAction(e -> {
            characterPane.getChildren().remove(optionPane);
//            characterPane.setOnMouseClicked(ev -> showCharacterOptions(characterPane, idx));
            publish(new CancelEvent());
        });
        characterPane.setOnMouseClicked(null);
    }

    public void prepareToAttackCharacterEventHandler(Player p, int idxAttacker) {
        publish(new MoveInfoDownEvent("Choose enemy character to attack."));
        if (!this.player.equals(p)) {
            for (int i = 0; i < 5; i++) {
                int idxDefender = i;

                this.charArr[i].setOnMouseClicked(e -> {
                    publish(new AttackCharacterEvent(idxAttacker, idxDefender));
                    System.out.println("move");
                    publish(new MoveInfoUpEvent());
                });

                this.charArr[i].setOnMouseEntered(e -> {
                    this.charArr[idxDefender].getScene().setCursor(Cursor.HAND);
                    this.charArr[idxDefender].setStyle("-fx-border-color: gold;");
                });

                this.charArr[i].setOnMouseExited(e -> {
                    this.charArr[idxDefender].getScene().setCursor(Cursor.DEFAULT);
                    this.charArr[idxDefender].setStyle("-fx-border-color: black;");
                });

                if (this.sumCharArr[i] != null) {
                    this.sumCharArr[i].setOnMouseClicked(null);

                }

            }

        }
    }

    @Override
    public void publish(Event event) {
        this.channel.sendEvent(this, event);
    }

    @Override
    public void onEvent(Event event) {

        if (event instanceof PrepareToMoveToBoardEvent) {

            int e = (int) event.getEvent();
            prepareToMoveToBoardEventHandler(e);

        } else if (event instanceof RefreshBoardEvent) {
            Pair<Player, Phase> e = (Pair<Player, Phase>) event.getEvent();
            Player player = e.getKey();
            refreshBoard(this.player.equals(player), e.getValue());

        } else if (event instanceof ChangePlayerEvent) {
            changePlayerEventHandler((Player) event.getEvent());

        } else if (event instanceof AttackPhaseEvent) {
            System.out.println("attack");
            Player p = (Player) event.getEvent();
            attackPhaseEventHandler(p);

        } else if (event instanceof PrepareToAttackCharacterEvent) {
            Pair<Player, Integer>e = (Pair<Player, Integer>) event.getEvent();
            prepareToAttackCharacterEventHandler(e.getKey(), e.getValue());

        } else if (event instanceof RefreshBoardClickedEvent) {
            Pair<Integer, Pair<Player, Phase>> e = (Pair<Integer, Pair<Player, Phase>>) event.getEvent();
            Pair<Player, Phase> e2 = e.getValue();
            refreshBoardClicked(e2.getKey().equals(this.player), e2.getValue(), e.getKey());
        }
    }
}
