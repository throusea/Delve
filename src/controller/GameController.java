package controller;

import data.chapter.ChapterLauncher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import listener.StageListener;
import model.GameStation;
import model.dice.DiceGroup;
import model.race.action.Action;
import model.race.Group;
import model.race.Location;
import model.race.character.Character;
import model.race.character.*;
import model.race.monster.Monster;
import model.race.monster.MonsterGroup;
import view.GamePanel;
import view.component.*;
import view.component.group.DiceGroupComponent;
import view.component.group.MonsterGroupComponent;
import view.component.group.RaceGroupComponent;
import view.component.modifier.GameModifierComponent;
import view.sceneShift.CardLayout;
import view.sceneShift.GlassPane;
import view.sceneShift.MenuPane;
import view.sceneShift.StageTabPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameController implements Initializable, StageListener {

    public static CardLayout cardLayout;

    @FXML
    public Pane mainPane, leftPane, rightPane, bottomPane;

    @FXML
    public StackPane skPane;

    @FXML
    public BorderPane gamePane;

    @FXML
    public Button rollButton;

    public GlassPane glassPane;

    public StageTabPane stageTabPane;

    public MenuPane menuPane;

    public List<GameStation> stationList;

    public List<DiceGroupComponent> diceGroupCpts;

    public GameStation currentStation;

    public GameClock clock;

    public GamePanel gamePanel;

    public Button addButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gamePanel = new GamePanel(mainPane);

        clock = new GameClock();
        clock.registerListener(this);

//        Text text = new Text();
//        gamePane.setOnMouseMoved(event -> {
//            text.setText(event.getPickResult().getIntersectedNode().toString());
//            text.relocate(event.getX() + 5, event.getY() + 5);
//        });
//        skPane.getChildren().add(text);

        glassPane = new GlassPane();
        stageTabPane = new StageTabPane();
        menuPane = new MenuPane();
        leftPane.getChildren().add(clock);
        skPane.setManaged(false);
        skPane.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ESCAPE) {
                glassPane.show();
                if(!glassPane.isDisable()) clock.pause();
                else clock.run();
            }
        });
        skPane.getChildren().add(stageTabPane);
        skPane.getChildren().add(glassPane);
        skPane.getChildren().add(menuPane);
        cardLayout = new CardLayout();
        cardLayout.add(gamePane, "Game");
        cardLayout.add(menuPane, "Menu");
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
        monGroupCpt.registerListener(monGroup);
        gamePanel.add(monGroupCpt);
        initDice(loc.getIndex());

        loadMonsterData(monGroup);
        monGroupCpt.setStaticLocation(loc.getStaticLocation());
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
        ChapterLauncher.load(currentStation, 1,0);
        initCharacterGroup(new Location(0, mainPane.getBoundsInLocal()));
        initCharacterGroup(new Location(1, mainPane.getBoundsInLocal()));
        //initMonsterGroup(new Location(1, mainPane.getBoundsInLocal()));

        clock.setVisible(true);
        clock.start();
        currentStation.playFromStart();
    }

    public void loadStation(CharacterGroup characterGroup) {
        initStation();
        currentStation.getRaceGroup(0).setHealth(characterGroup);
    }

    public void clearStation() {
        mainPane.getChildren().clear();
        gamePanel.clear();
        clock.setVisible(false);
    }

    public void nextStation() {
        clearStation();
        loadStation((CharacterGroup) currentStation.getRaceGroup(0));
    }

    public void roll() {
        ArrayList<Integer> list = currentStation.rollDice();
        System.out.println(list);
        if(list == null) {
            rollState(true);
        }
    }

    public void loadCharacterData(CharacterGroup chrGroup) {
        addCharacter(new Fighter(),chrGroup);
        addCharacter(new Rogue(),chrGroup);
        addCharacter(new Wizard(), chrGroup);
        addCharacter(new Cleric(),chrGroup);
    }

    public void loadMonsterData(MonsterGroup monGroup) {
        List<Monster> monsters = ChapterLauncher.loadMonster(1,1);
        monsters.forEach(monster -> addMonster(monster, monGroup));
    }

    public void addStation(GameStation station) {
        stationList.add(station);
    }

    public void setCurrentStation(int stationId) {
        currentStation = stationList.get(stationId);
    }

    public void gameStart(int id) {
        setCurrentStation(id);
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
        System.out.println(actions.toArray());
        currentStation.disposeAction(actions);
    }

    @Override
    public void rollState(boolean value) {
        rollButton.setDisable(!value);
        if(!value) {
            System.out.println("roll reset!");
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
        gamePanel.getCurrentRoundRaceGroup().getRaceComponentList().forEach(raceCpt -> {
            raceCpt.setSelectDisabled(!value);
        });
    }

    @Override
    public void nextStep() {
        currentStation.nextStep();
        clock.start();
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
        return currentStation.getDiceGroup(currentStation.getCurrentRound());
    }

//    public void initRaceGroup(int gameMode) {
//        Bounds bounds = mainPane.getBoundsInLocal();
//
//        RaceGroupComponent chrGroupCpt1 = new RaceGroupComponent(new Bound(0,0, bounds.getWidth()/2, bounds.getHeight()/2), Group.HUMAN);
//        RaceGroupComponent chrGroupCpt2 = new RaceGroupComponent(new Bound(bounds.getWidth()/2, bounds.getHeight()/2, bounds.getWidth()/2, bounds.getHeight()/2), Group.HUMAN);
//
//        chrGroupCpt1.register(currentStation.getRaceGroup(0));
//        chrGroupCpt2.register(currentStation.getRaceGroup(1));
//
//        gamePanel.add(chrGroupCpt1);
//        gamePanel.add(chrGroupCpt2);
//
//        GameModifierComponent GMCpt1, GMCpt2;
//        GMCpt1 = new GameModifierComponent(currentStation.getRaceGroup(0), 2,300);
//        GMCpt2 = new GameModifierComponent(currentStation.getRaceGroup(1),2,500);
//        GMCpt1.registerListener(this);
//        GMCpt2.registerListener(this);
//        rightPane.getChildren().add(GMCpt1);
//        rightPane.getChildren().add(GMCpt2);
//
//        addCharacter(new Fighter(),0);
//        addCharacter(new Fighter(),1);
//        addCharacter(new Rogue(),0);
//        addCharacter(new Rogue(),1);
//        addCharacter(new Wizard(), 0);
//        addCharacter(new Wizard(), 1);
//        addCharacter(new Cleric(),0);
//        addCharacter(new Cleric(),1);
//        chrGroupCpt1.setStaticLocation(0,0);
//        chrGroupCpt2.setStaticLocation(bounds.getWidth() - 680, bounds.getHeight() - 65);
//    }
}