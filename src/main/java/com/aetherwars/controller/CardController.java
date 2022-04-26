package com.aetherwars.controller;


import com.aetherwars.event.*;
import com.aetherwars.model.cards.Card;
import com.aetherwars.model.cards.character.Character;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class CardController implements Initializable {
    @FXML
    ImageView cardImage;

    @FXML
    Label labelName;

    @FXML
    Label labelMana;

    @FXML
    Label labelAtkHp;

    @FXML
    StackPane stackpane;

    private String cardName;
    private int mana;
    private String image_path;
    private double atk;
    private double hp;
    private String desc;
    private GameChannel channel;
    private Character card;
    private MainController controller;

    public CardController(String cardName, int mana, String image_path, double atk, double hp, String desc, Character card, MainController controller) {
        this.cardName = cardName;
        this.mana = mana;
        this.image_path = image_path;
        this.atk = atk;
        this.hp = hp;
        this.desc = desc;
        this.card = card;
        this.controller = controller;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File file = null;
        try {
            file = new File(getClass().getResource("../" + this.image_path).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        labelName.setText(this.cardName);
        labelMana.setText("MANA " + this.mana);
        labelAtkHp.setText("ATK " + atk + "/HP " + hp);
        cardImage.setImage(new Image(file.toURI().toString(), 90, 120, true, true));

    }

    public void onMouseEnter(MouseEvent mouseEvent) {

        stackpane.setStyle("-fx-background-color: gold;" + "-fx-border-color: seagreen;" + "-fx-border-width: 4;");

        this.controller.onHoverCard(this.card);
    }


    public void onMouseExit(MouseEvent mouseEvent) {
        stackpane.setStyle("-fx-background-color: gold;" + "-fx-border-color: brown;" + "-fx-border-width: 4;");
        this.controller.onUnHoverCard(this.card);

        //publish(new HoverEvent(this.card));
    }




}
