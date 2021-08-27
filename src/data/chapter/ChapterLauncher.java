package data.chapter;

import model.GameStation;
import model.dice.DiceGroup;
import model.race.Group;
import model.race.character.CharacterGroup;
import model.race.monster.Monster;
import model.race.monster.MonsterGroup;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChapterLauncher {

    public static Scanner scanner;

    //@: TO get the basic information of raceGroup.
    public static void load(GameStation station, int chapterIndex, int levelIndex) {
        try {
            scanner = new Scanner(new File("src/data/chapter/" + chapterIndex + "/level" + levelIndex + ".in"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while(scanner.hasNextLine()) {
            String s = scanner.nextLine();
            CharacterGroup chrGroup = loadCharacterGroup(s);
            station.add(chrGroup);
            MonsterGroup monGroup = loadMonsterGroup(s);
            station.add(monGroup);
        }
    }

    public static CharacterGroup loadCharacterGroup(String s) {
        String[] info = s.split(" ");
        if(info[0].equals("#CharacterGroup")) {
            return new CharacterGroup(Group.HUMAN);
        }
        return null;
    }

    public static MonsterGroup loadMonsterGroup(String s) {
        String[] info = s.split(" ");
        if(info[0].equals("#MonsterGroup")) return new MonsterGroup(Group.MONSTER);
        return null;
    }

    public static List<Monster> loadMonster(int chapterIndex, int levelIndex) {
        try {
            scanner = new Scanner(new File("src/data/chapter/" + chapterIndex + "/level" + levelIndex + ".in"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List<Monster> monsters = new ArrayList<>();
        while(scanner.hasNextLine()) {
            String[] monsterInfo = scanner.nextLine().split(" ");
            if(!monsterInfo[0].equals("#Monster")) continue;
            monsters.add(Monster.get(monsterInfo[1]));
        }
        return monsters;
    }
}
