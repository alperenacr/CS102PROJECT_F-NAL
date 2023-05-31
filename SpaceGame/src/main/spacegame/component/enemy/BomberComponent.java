package main.spacegame.component.enemy;

import main.spacegame.factory.SpaceGameFactory;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.time.LocalTimer;

import static com.almasb.fxgl.dsl.FXGL.*;

import javafx.util.Duration;

public class BomberComponent extends Component {

    private Entity bomber;
    SpaceGameFactory factory = new SpaceGameFactory();
    protected LocalTimer timer = FXGL.newLocalTimer();
    protected Duration delay = Duration.seconds(5);

    @Override
    public void onUpdate(double tpf) {
        
        if (timer.elapsed(delay)) {
        factory.spawnBomb(new SpawnData(entity.getX(), entity.getY()));
        }
    }

    @Override
    public void onAdded() {
        bomber = entity;
    }
}
