package model.round;

import listener.StageListener;
import model.affector.Affector;

import java.util.ArrayList;
import java.util.List;

public class GameStage {

    public StageListener listener;

    protected List<Affector> stageAffectors = new ArrayList<>();

    public void run() {
        stageAffectors.forEach(Affector::start);
        new ArrayList<>(stageAffectors).forEach(affector -> {
            if(!affector.isPermanent()) {
                stageAffectors.remove(affector);
            }
        });
    }

    public List<Affector> getStageAffectors() { return stageAffectors; }

    public void saveData() {}

    public void registerListener(StageListener listener) {
        this.listener = listener;
    }

    public void unregisterListener() { listener = null; }

    @Override
    public String toString() {
        return "GameStage";
    }
}
