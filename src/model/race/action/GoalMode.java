package model.race.action;

public enum GoalMode {
    SINGLE(1), ALL(2);

    int value;
    GoalMode(int value) {
        this.value = value;
    }
}
