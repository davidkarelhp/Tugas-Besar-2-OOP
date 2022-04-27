package com.aetherwars;

import com.aetherwars.model.cards.character.Character;
import com.aetherwars.model.cards.character.CharacterType;
import com.aetherwars.model.cards.character.SummonedCharacter;
import com.aetherwars.model.cards.spell.Morph;
import com.aetherwars.model.cards.spell.Potion;
import com.aetherwars.model.cards.spell.Spell;
import com.aetherwars.model.cards.spell.Swap;
import com.aetherwars.model.cards.spell.enums.EffectDurationType;
import com.aetherwars.model.cards.spell.enums.SpellType;
import com.aetherwars.util.CSVReader;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class FileLoader {
    private static final String CHARACTER_CSV_FILE_PATH = "card/data/character.csv";
    private static final String SPELL_MORPH_CSV_FILE_PATH = "card/data/spell_morph.csv";
    private static final String SPELL_PTN_CSV_FILE_PATH = "card/data/spell_ptn.csv";
    private static final String SPELL_SWAP_CSV_FILE_PATH = "card/data/spell_swap.csv";

    public static void loadFiles() throws IOException, URISyntaxException {
        FileLoader.loadCharacter();
        FileLoader.loadSpellMorph();
        FileLoader.loadSpellPtn();
        FileLoader.loadSpellSwap();
    }

    private static void loadCharacter() throws IOException, URISyntaxException {
        File characterCSVFile = new File(FileLoader.class.getResource(CHARACTER_CSV_FILE_PATH).toURI());
        CSVReader characterReader = new CSVReader(characterCSVFile, "\t");
        characterReader.setSkipHeader(true);
        List<String[]> characterRows = characterReader.read();
        for (String[] row : characterRows) {
            int id = Integer.parseInt(row[0]);
            String name = row[1];
            CharacterType characterType = CharacterType.valueOf(row[2]);
            String description = row[3];
            String imagePath = row[4];
            int attack = Integer.parseInt(row[5]);
            int health = Integer.parseInt(row[6]);
            int mana = Integer.parseInt(row[7]);
            int attackUp = Integer.parseInt(row[8]);
            int healthUp = Integer.parseInt(row[9]);

            Character c = new Character(id, name, description, mana, imagePath, characterType, attack, health, attackUp, healthUp);
            Character.characterList.add(c);
        }
    }

    private static void loadSpellMorph() throws IOException, URISyntaxException {
        File characterCSVFile = new File(FileLoader.class.getResource(SPELL_MORPH_CSV_FILE_PATH).toURI());
        CSVReader characterReader = new CSVReader(characterCSVFile, "\t");
        characterReader.setSkipHeader(true);
        List<String[]> characterRows = characterReader.read();
        for (String[] row : characterRows) {
            int id = Integer.parseInt(row[0]);
            String name = row[1];
            String description = row[2];
            String imagePath = row[3];
            int targetId = Integer.parseInt(row[4]);
            int mana = Integer.parseInt(row[5]);

            Morph m = new Morph(targetId);
            Morph.MorphList.add(m);

            Spell s = new Spell(id, name, description, mana, imagePath, m);
            Spell.SpellList.add(s);
        }
    }

    private static void loadSpellPtn() throws IOException, URISyntaxException {
        File characterCSVFile = new File(FileLoader.class.getResource(SPELL_PTN_CSV_FILE_PATH).toURI());
        CSVReader characterReader = new CSVReader(characterCSVFile, "\t");
        characterReader.setSkipHeader(true);
        List<String[]> characterRows = characterReader.read();
        for (String[] row : characterRows) {
            int id = Integer.parseInt(row[0]);
            String name = row[1];
            String description = row[2];
            String imagePath = row[3];
            int attack = Integer.parseInt(row[4]);
            int hp = Integer.parseInt(row[5]);
            int mana = Integer.parseInt(row[6]);
            int duration = Integer.parseInt(row[7]);

            Potion p = new Potion(attack, hp, duration);
            Potion.PotionList.add(p);

            Spell s = new Spell(id, name, description, mana, imagePath, p);
            Spell.SpellList.add(s);
        }
    }

    private static void loadSpellSwap() throws IOException, URISyntaxException {
        File characterCSVFile = new File(FileLoader.class.getResource(SPELL_SWAP_CSV_FILE_PATH).toURI());
        CSVReader characterReader = new CSVReader(characterCSVFile, "\t");
        characterReader.setSkipHeader(true);
        List<String[]> characterRows = characterReader.read();
        for (String[] row : characterRows) {
            int id = Integer.parseInt(row[0]);
            String name = row[1];
            String description = row[2];
            String imagePath = row[3];
            int duration = Integer.parseInt(row[4]);
            int mana = Integer.parseInt(row[5]);

            Swap s = new Swap(duration);
            Swap.SwapList.add(s);

            Spell sp = new Spell(id, name, description, mana, imagePath, s);
            Spell.SpellList.add(sp);
        }
    }
}
