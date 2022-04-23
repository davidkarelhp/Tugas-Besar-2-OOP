package com.aetherwars;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import com.aetherwars.model.Type;
import com.aetherwars.model.Character;
import com.aetherwars.util.CSVReader;

public class AetherWars extends Application {
  private static final String CHARACTER_CSV_FILE_PATH = "card/data/character.csv";

  public void loadCards() throws IOException, URISyntaxException {
    File characterCSVFile = new File(getClass().getResource(CHARACTER_CSV_FILE_PATH).toURI());
    CSVReader characterReader = new CSVReader(characterCSVFile, "\t");
    characterReader.setSkipHeader(true);
    List<String[]> characterRows = characterReader.read();
    for (String[] row : characterRows) {
      Character c = new Character(row[1], row[3], Type.valueOf(row[2]), Integer.parseInt(row[5]), Integer.parseInt(row[6]), Integer.parseInt(row[7]), Integer.parseInt(row[8]), Integer.parseInt(row[9]));
      System.out.println(c);
    }
  }

  @Override
  public void start(Stage stage) throws IOException {
    Text text = new Text();
    text.setText("Loading...");
    text.setX(50);
    text.setY(50);
//
//    Group root = new Group();
//    root.getChildren().add(text);

    Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));

//    Scene scene = new Scene(root, 1280, 720);
    Scene scene = new Scene(root);

    stage.setTitle("Minecraft: Aether Wars");
    stage.setScene(scene);
    stage.setMaximized(true);
    stage.show();

    try {
      this.loadCards();
      text.setText("Minecraft: Aether Wars!");
    } catch (Exception e) {
      text.setText("Failed to load cards: " + e);
    }
  }

  public int add(int x, int y) {
    return x + y;
  }

  public int minus(int x, int y) {
    return x - y;
  }

  public static void main(String[] args) {
    launch();
  }
}
