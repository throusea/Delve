package model.affector;

import model.dice.Dice;
import model.race.ActionMode;
import model.race.Race;
import model.race.character.Cleric;
import model.race.character.Rogue;
import model.race.character.Wizard;

public class SkillGainer extends Affector{

    private int actionMode;

    private int damage = 3;

    public SkillGainer(Race race, int actionMode) {
        this.race = race;
        this.actionMode = actionMode;
    }

    public SkillGainer(Race race, int actionMode, int damage) {
        this.race = race;
        this.actionMode = actionMode;
        this.damage = damage;
    }

    @Override
    public void start() {
        run();
    }

    @Override
    protected Void call() throws Exception {
        System.out.printf("%d %d", actionMode, damage);
        switch (actionMode) {
            case Rogue.CRIPPLING_STRIKE: {
                race.getDiceString().set("55533");
                race.setKeyDice(new Dice(damage));
                race.setReadyAction(true);
                break;
            }
            case Wizard.FIREBALL:{
                race.getDiceString().set("4444");
                break;
            }
            case Wizard.CHAIN_LIGHTNING:{
                race.getDiceString().set("44444");
                race.setKeyDice(new Dice(damage));
                race.setReadyAction(true);
                break;
            }
            case Wizard.DEMISE: {
                race.getDiceString().set("444444");
                break;
            }
            case Cleric.MINOR_HEAL:{
                race.getDiceString().set("1234");
                break;
            }
            case Cleric.HEAL:{
                race.getDiceString().set("12345");
                race.setKeyDice(new Dice(damage));
                race.setReadyAction(true);
                break;
            }
            case Cleric.MIRACLE:{
                race.getDiceString().set("123456");
                break;
            }
        }
        return null;
    }
}
