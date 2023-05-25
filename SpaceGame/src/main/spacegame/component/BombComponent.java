package main.spacegame.component;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.BoundingBoxComponent;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import static com.almasb.fxgl.dsl.FXGL.*;

import main.spacegame.SpaceGameApp;
import main.spacegame.SpaceGameType;

import static com.almasb.fxgl.dsl.FXGL.getGameWorld;

public class BombComponent extends Component {

    int radius;

    BombComponent(int radius) {
        this.radius = radius;
    }

    public void explode() {

        getGameWorld()
                .getEntitiesInRange(entity.getBoundingBoxComponent().range(radius, radius))
                .stream()
                .filter(e -> e.isType(HealthIntComponent.class))
                .forEach(e -> FXGL.<SpaceGameApp>getAppCast().killEnemy(e));

        getGameWorld().getSingleton(SpaceGameType.GRID)
                .getComponent(GridComponent.class)
                .applyExplosiveForce(2500, entity.getCenter(), 200);

    }


    public int getRadius() {
        return radius;
    }

    public boolean isComponentInjectionRequired() {
        return false;
    }
}
