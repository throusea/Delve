package model.race.action;

public enum ActionMode {
    TO_ENEMY(1), TO_PARTNER(2);

    int value;
    ActionMode(int value) {
        this.value = value;
    }

    public int intValue() {
        return value;
    }
}
