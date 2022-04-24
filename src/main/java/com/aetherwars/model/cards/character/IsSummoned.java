package com.aetherwars.model.cards.character;

public interface IsSummoned {
    // getter
    int getLevel();

    int getExp();

    // menambahkan spell ke dalam daftar spell aktif
//    void addSpell(Spell s);

    // mengembalikan daftar spell aktif
//    List<Spell> getActiveSpells();

    // meningkatkan level karakter sebanyak 1
    // mereset xp ke 0
    // meningkatkan baseAtk dan baseHp sebanyak attackUp dan healthUp
    void levelUp();
}
