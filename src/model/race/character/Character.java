package model.race.character;

import model.race.Race;

public class Character extends Race {

    public Character() {
        super();
    }

    public Character(int maxHealth) {
        super();
        setMaxHealth(maxHealth);
        setHealth(maxHealth);
    }
}
