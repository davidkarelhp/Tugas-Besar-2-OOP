package com.aetherwars.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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

    private String cardName;
    private int mana;
    private String image_path;
    private double atk;
    private double hp;

    public CardController(String cardName, int mana, String image_path, double atk, double hp) {
        this.cardName = cardName;
        this.mana = mana;
        this.image_path = image_path;
        this.atk = atk;
        this.hp = hp;
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
}
