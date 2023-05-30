package main.spacegame.service;

import com.almasb.fxgl.core.EngineService;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;

import static com.almasb.fxgl.dsl.FXGL.byType;
import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static main.spacegame.Config.MAX_ENEMIES_PRESSURE;
import static main.spacegame.SpaceGameType.*;
import static java.lang.Math.min;

/**
 * Provides dynamic difficulty gameplay by controlling how many enemies are being spawned,
 * based on the "pressure" factor.
 */

public class LevelPressureService extends EngineService{
    
    /**
     * In range [0..1], where
     * 0 means no pressure (few enemies) and
     * 1 means max pressure (a lot of enemies).
     */
    private ReadOnlyDoubleWrapper pressureProp = new ReadOnlyDoubleWrapper();

    private boolean isSpawningEnemies = true;
    private boolean isOnCooldown = false;

    public double getPressure() {
        return pressureProp.get();
    }

    public ReadOnlyDoubleProperty pressurePropProperty() {
        return pressureProp.getReadOnlyProperty();
    }

    public boolean isSpawningEnemies() {
        return isSpawningEnemies && !isAmongUsPresent();
    }

    public boolean isAmongUsPresent() {
        return byType(AMONGUS).size() > 0;
    }

    @Override
    public void onGameUpdate(double tpf) {
        // not quite correct since this includes pick ups and other entities
        // but should be sufficient
        var numEntities = (getGameWorld().getEntities().size() - byType(WİNGEDALİEN, BOMBER, BOSS, BULLET).size()) * 1.0;

        var value = min(numEntities / MAX_ENEMIES_PRESSURE, 1.0);

        pressureProp.setValue(value);

        if (isOnCooldown && value < 0.25) {
            isOnCooldown = false;
            isSpawningEnemies = true;
        }

        if (value >= 0.80) {
            isOnCooldown = true;
            isSpawningEnemies = false;
        }
    }
}