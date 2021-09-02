package controller;

import data.chapter.ChapterLauncher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import listener.StageListener;
import model.GameStation;
import model.auto.AutoMonsterGroup;
import model.dice.DiceGroup;
import model.race.RaceGroup;
import model.race.action.Action;
import model.race.Group;
import model.race.Location;
import model.race.character.Character;
import model.race.character.*;
import model.race.monster.Monster;
import model.race.monster.MonsterGroup;
import model.round.GameRound;
import util.NextUtil;
import view.GamePanel;
import view.component.*;
import view.component.group.DiceGroupComponent;
import view.component.group.MonsterGroupComponent;
import view.component.group.RaceGroupComponent;
import view.component.modifier.GameModifierComponent;
import view.sceneShift.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameController implements Initializable, StageListener {

    public static CardLayout cardLayout;

    public final static int ADVENTURE = 1, PVP = 2;

    @FXML
    public Pane mainPane, leftPane, rightPane, bottomPane;

    @FXML
    public StackPane skPane;

    @FXML
    public BorderPane gamePane;

    @FXML
    public Button rollButton;

    public Pane glassPane, stageTabPane, menuPane, selectPane;
    public List<GameStation> stationList;

    public List<DiceGroupComponent> diceGroupCpts;

    public GameStation currentStation;

    public GameClock clock;

    public GamePanel gamePanel;

    public Button addButton;

    public AutoMonsterGroup autoMonGroup;

    public int chapterIndex = 1, levelIndex = 1, currentGameMode;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gamePanel = new GamePanel(mainPane);

        clock = new GameClock();
        clock.registerListener(this);

        glassPane = new GlassPane();
        stageTabPane = new StageTabPane();
        menuPane = new MenuPane();
        selectPane = new SelectPane(this);
        leftPane.getChildren().add(clock);
        skPane.setManaged(false);
        skPane.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ESCAPE) {
                ((GlassPane)glassPane).show();
                if(!glassPane.isDisable()) clock.pause();
                else clock.run();
            }
        });
        skPane.getChildren().add(stageTabPane);
        skPane.getChildren().add(glassPane);
        skPane.getChildren().add(menuPane);
        skPane.getChildren().add(selectPane);

        cardLayout = new CardLayout();
        cardLayout.add(gamePane, "Game");
        cardLayout.add(menuPane, "Menu");
        cardLayout.add(selectPane, "Select");
        cardLayout.show("Menu");
    }

    public void initCharacterGroup(Location loc) {
        RaceGroupComponent chrGroupCpt = new RaceGroupComponent(loc.getBound(), Group.HUMAN);
        CharacterGroup chrGroup = (CharacterGroup) currentStation.getRaceGroup(loc.getIndex());

        chrGroup.setRaceGroupCpt(chrGroupCpt);
        chrGroupCpt.registerListener(chrGroup);
        gamePanel.add(chrGroupCpt);

        loadCharacterData(chrGroup);
        chrGroupCpt.setStaticLocation(loc.getStaticLocation());
        initDice(loc.getIndex());

        GameModifierComponent GMCpt = new GameModifierComponent(currentStation.getRaceGroup(loc.getIndex()), (int) loc.getModifiedLocation().x, (int) loc.getModifiedLocation().y);
        GMCpt.registerListener(this);
        rightPane.getChildren().add(GMCpt);
    }

    public void initMonsterGroup(Location loc) {
        MonsterGroupComponent monGroupCpt = new MonsterGroupComponent(loc.getBound(), Group.MONSTER);
        MonsterGroup monGroup = (MonsterGroup) currentStation.getRaceGroup(loc.getIndex());
        monGroup.setRaceGroupCpt(monGroupCpt);
        monGroupCpt.registerListener(monGroup);
        gamePanel.add(monGroupCpt);
        initDice(loc.getIndex());

        loadMonsterData(monGroup);
        monGroupCpt.setStaticLocation(loc.getStaticLocation());
        autoMonGroup = new AutoMonsterGroup(this, monGroup);
        gamePanel.registerAutoListener(autoMonGroup);
    }

    public void initGroup() {
        if(currentGameMode == ADVENTURE) {
            ChapterLauncher.load(currentStation, chapterIndex, levelIndex);
            initCharacterGroup(new Location(0, mainPane.getBoundsInLocal()));
            initMonsterGroup(new Location(1, mainPane.getBoundsInLocal()));
        }
        if(currentGameMode == PVP) {
            ChapterLauncher.load(currentStation, chapterIndex,levelIndex);
            initCharacterGroup(new Location(0, mainPane.getBoundsInLocal()));
            initCharacterGroup(new Location(1, mainPane.getBoundsInLocal()));
        }
    }

    public void initDice(int loc) {
        DiceGroupComponent diceGroupComponent = new DiceGroupComponent(
                currentStation.getDiceGroup(loc),
                new Location(2, mainPane.getBoundsInLocal()).getBound()
        );

        for(DiceComponent diceComponent: diceGroupComponent.getList()) {
            mainPane.getChildren().add(diceComponent);
        }

        gamePanel.addDiceGroupCpt(diceGroupComponent);
        diceGroupCpts.add(diceGroupComponent);
    }

    public void initStation() {
        diceGroupCpts = new ArrayList<>();
        currentStation = new GameStation();
        currentStation.registerListener(this);
    }

    public void initGame() {
        initStation();
        if(currentGameMode == ADVENTURE) {
            chapterIndex = 1;
            levelIndex = 1;
        }
        if(currentGameMode == PVP) {
            chapterIndex = 1;
            levelIndex = 0;
        }
        initGroup();
        gameStart();
    }

    public void loadGame(CharacterGroup characterGroup) {
        initStation();

        initGroup();
        currentStation.getRaceGroup(0).setHealth(characterGroup);
        gameStart();
    }

    public void clearStation() {
        mainPane.getChildren().clear();
        gamePanel.clear();
        clock.setVisible(false);
    }

    public void nextStation() {
        clearStation();
        levelIndex = NextUtil.next(levelIndex, 1, 6);
        loadGame((CharacterGroup) currentStation.getRaceGroup(0));
    }

    public void roll() {
        ArrayList<Integer> list = currentStation.rollDice();
        System.out.println(list);
        System.out.println(getDiceGroup());
        if(list == null) {
            rollState(true);
        }
    }

    public void loadCharacterData(CharacterGroup chrGroup) {
        if(currentGameMode == ADVENTURE) {
            addCharacter(new Fighter(5), chrGroup);
            addCharacter(new Rogue(3), chrGroup);
            addCharacter(new Wizard(1), chrGroup);
            addCharacter(new Cleric(3), chrGroup);
        }
        if(currentGameMode == PVP) {
            addCharacter(new Fighter(6), chrGroup);
            addCharacter(new Rogue(4), chrGroup);
            addCharacter(new Wizard(4), chrGroup);
            addCharacter(new Cleric(5), chrGroup);
        }
    }

    public void loadMonsterData(MonsterGroup monGroup) {
        List<Monster> monsters = ChapterLauncher.loadMonster(chapterIndex,levelIndex);
        monsters.forEach(monster -> addMonster(monster, monGroup));
    }

    public void setCurrentStation(int stationId) {
        currentStation = stationList.get(stationId);
    }

    public void gameStart() {
        clock.setVisible(true);
        clock.start();
        currentStation.playFromStart();
    }

    public void gameRestart() {

    }

    public void gamePlay() {

    }

    public void gameStop() {}

    public void gameEnd() {}

    public void addCharacter(Character character, CharacterGroup chrGroup) {
        CharacterComponent characterComponent = new CharacterComponent(80,60);
        RaceStaticComponent raceStaticComponent = new RaceStaticComponent(character);
        character.setRaceComponent(characterComponent);
        characterComponent.registerListener(character);

        chrGroup.add(character);
        chrGroup.raceGroupComponent.add(characterComponent, mainPane);
        chrGroup.raceGroupComponent.add(raceStaticComponent, mainPane);
    }

    public void addMonster(Monster monster, MonsterGroup monGroup) {
        MonsterComponent monsterComponent = new MonsterComponent(80,60);
        monster.setRaceComponent(monsterComponent);
        monsterComponent.registerListener(monster);

        monGroup.add(monster);
        monGroup.raceGroupComponent.add(monsterComponent, mainPane);
    }

    public void actionHandle(MouseEvent event) {
        actionHandle();
    }

    @Override
    public void actionHandle() {
        List<Action> actions = currentStation.getRaceGroup(currentStation.getCurrentRound()).actionHandle();
        currentStation.disposeAction(actions);
    }

    @Override
    public void rollState(boolean value) {
        rollButton.setDisable(!value);
        if(!value) {
            currentStation.getDiceGroup(currentStation.getCurrentRound()).reset();
            diceGroupCpts.get(currentStation.getCurrentRound()).setRandPos();
        }
    }

    @Override
    public void attackState(boolean value) {

    }

    @Override
    public void selectState(boolean value) {
        System.out.println("Select " + value);
        gamePanel.getCurrentRoundRaceGroup().getRaceComponentList().forEach(raceCpt -> raceCpt.setSelectDisable(!value));
    }

    @Override
    public void nextStep() {
        currentStation.nextStep();
        clock.start();
    }

    @Override
    public void autoState() {
        if(getRound().isAuto()) autoMonGroup.autoAction(getRound().getCurrentStage());
    }

    @Override
    public void gameState() {
        RaceGroup raceGroup = currentStation.getRaceGroup(currentStation.getCurrentRound());
        if(raceGroup.getTotalHealth() == 0) {
            if(raceGroup instanceof CharacterGroup) {
                new Alert(Alert.AlertType.CONFIRMATION, "You lose!").show();
            }
            if(raceGroup instanceof MonsterGroup) {
                new Alert(Alert.AlertType.CONFIRMATION, "You win!").show();
            }
        }
    }

    @Override
    public void reset() {
        currentStation.getRaceGroup(currentStation.getCurrentRound()).reset();
    }

    @Override
    public void addAffector() {
        currentStation.addAffector();
    }

    @Override
    public DiceGroup getDiceGroup() {
        return currentStation.getDiceGroup();
    }

    public GameRound getRound() {return currentStation.getRound(currentStation.getCurrentRound()); }
}