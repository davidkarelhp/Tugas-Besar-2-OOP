package com.aetherwars.controller;


import com.aetherwars.model.cards.Card;
import com.aetherwars.model.cards.character.Character;
import com.aetherwars.model.cards.spell.Spell;
import com.aetherwars.model.cards.spell.enums.SpellType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class CardController implements Initializable {
    @FXML
    ImageView cardImage;

    @FXML
    Label labelName, labelMana, labelAtkHp;

    @FXML
    StackPane cardStackPane;

    private Card card;
    private MainController controller;

    public CardController(Card card, MainController controller) {
        this.card = card;
        this.controller = controller;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File file = null;
        try {
            file = new File(getClass().getResource("../" + card.getImagePath()).toURI());
        } catch (Exception e) {
//            e.printStackTrace();
            System.out.println("no picture");
        }

        labelName.setText(card.getName());
        if (card instanceof Spell) {
            Spell s = (Spell) card;
            if (s.getType().equals(SpellType.LEVELUP) || s.getType().equals(SpellType.LEVELDOWN)) {
                labelMana.setText("");
            } else {
                labelMana.setText("MANA " + card.getMana());
            }
        } else {
            labelMana.setText("MANA " + card.getMana());

        }

        if (this.card instanceof Character) {
            Character character = (Character) this.card;
            labelAtkHp.setText("ATK " + character.getBaseAttack() + "/HP " + character.getBaseHealth());

        } else if (this.card instanceof Spell) {
            Spell spell = (Spell) this.card;
            if (spell.getType() == SpellType.POTION) {
                labelAtkHp.setText("SPELL POTION");
            } else if (spell.getType() == SpellType.LEVELUP) {
                labelAtkHp.setText("SPELL LEVEL UP");
            } else if (spell.getType() == SpellType.LEVELDOWN) {
                labelAtkHp.setText("SPELL LEVEL DOWN");
            } else if (spell.getType() == SpellType.SWAP) {
                labelAtkHp.setText("SPELL SWAP");
            } else if (spell.getType() == SpellType.MORPH) {
                labelAtkHp.setText("SPELL MORPH");
            } else if (spell.getType() == SpellType.HEAL) {
                labelAtkHp.setText("SPELL HEAL");
            }
        }
        try {
            cardImage.setImage(new Image(file.toURI().toString(), 90, 120, true, true));

        } catch (Exception e) {
            cardImage.setImage(null);
        }

    }

    public void onMouseEnter(MouseEvent mouseEvent) {

        cardStackPane.setStyle("-fx-background-color: gold;" + "-fx-border-color: seagreen;" + "-fx-border-width: 4;");

        cardStackPane.setCursor(Cursor.HAND);
        this.controller.onHoverCard(this.card);
    }


    public void onMouseExit(MouseEvent mouseEvent) {
        cardStackPane.setStyle("-fx-background-color: gold;" + "-fx-border-color: MEDIUMPURPLE;" + "-fx-border-width: 4;");
        this.controller.onUnhover();

        cardStackPane.setCursor(Cursor.HAND);
    }




}
