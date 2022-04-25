package com.aetherwars.controller;

import com.aetherwars.GameEngine;
import com.aetherwars.event.EventChannel;
import com.aetherwars.event.GameChannel;
import com.aetherwars.model.Phase;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    StackPane backPane;

    @FXML
    Button buttonSkip;

    @FXML
    Label attack_p, draw_p, end_p, plan_p;

    Phase[] phases = new Phase[] { Phase.DRAW, Phase.PLAN, Phase.ATTACK, Phase.END };

    Label[] phases_bar;

    int phase_id = 0;

    private EventChannel channel;
    private GridPane drawPane;

    public MainController(GameChannel channel) {
        this.channel = channel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.phases_bar = new Label[]{draw_p, plan_p, attack_p, end_p};
        this.buttonSkip.setOnAction(e -> {
            // Aturan ini buat skip phase, tapi ini  contoh aja
            switch(this.channel.getPhase())
        });

    }

    public void displayDraw() {
        // Contoh kalau mau nampilin sesuatu pakai button, di sini yang ditampilin tuh 3 kartu yang dipilih pas nge-draw
        drawPane = new GridPane();
//        drawPane.setGridLinesVisible(true);

        RowConstraints constraintsR = new RowConstraints();
        constraintsR.setVgrow(Priority.ALWAYS);
        drawPane.getRowConstraints().add(constraintsR);

        for (int i = 0 ; i < 3; i++) {
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setHgrow(Priority.ALWAYS);
            drawPane.getColumnConstraints().add(constraints);

            StackPane temp = new StackPane();
            temp.setMaxSize(150, 300);
            GridPane.setHalignment(temp, HPos.CENTER);

            temp.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            drawPane.add(temp, i, 0);
            temp.setOnMouseClicked(e -> this.onDrawnCardClicked());

        }
        drawPane.setBackground(new Background(new BackgroundFill(new Color(0.6, 0.6, 0.6, 0.5), CornerRadii.EMPTY, Insets.EMPTY)));
        backPane.getChildren().add(drawPane);
    }

    public void onDrawnCardClicked() {
        // Method kalau salah satu kartu yang di-draw diklik, untuk sekarang kembali ke main panel aja
        backPane.getChildren().remove(drawPane);
    }

    public void startGame(GameEngine gameEngine) {
        System.out.println("halo");
    }
}
