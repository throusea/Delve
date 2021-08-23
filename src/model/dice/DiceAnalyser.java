package model.dice;

import model.race.character.Cleric;
import model.race.character.Rogue;
import model.race.character.Wizard;

public class DiceAnalyser {

    public static int analyse(String diceString) {
        int[] a = new int[7];
        int[] b = new int[7];
        if(diceString == null) return -1;
        for(int i = 0; i < diceString.length(); i++) {
            a[diceString.charAt(i) - '0']++;
        }
        for(int i = 1; i <= 6; i++) {
            b[a[i]]++;
        }
        if(b[3] > 0 && b[2] > 0) return Rogue.CRIPPLING_STRIKE;
        if(b[4] > 0) return Wizard.FIREBALL;
        if(b[5] > 0) return Wizard.CHAIN_LIGHTNING;
        if(b[6] > 0) return Wizard.DEMISE;
        if(b[1] == 4) return Cleric.MINOR_HEAL;
        if(b[1] == 5) return Cleric.HEAL;
        if(b[1] == 6) return Cleric.MIRACLE;
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(analyse("55353"));
        System.out.println(analyse("22111"));
        System.out.println(analyse("11133"));
        System.out.println(analyse("53535"));
        System.out.println(analyse("5555"));
        System.out.println(analyse("55555"));
        System.out.println(analyse("555555"));
        System.out.println(analyse("1234"));
        System.out.println(analyse("12345"));
        System.out.println(analyse("123456"));
    }
}