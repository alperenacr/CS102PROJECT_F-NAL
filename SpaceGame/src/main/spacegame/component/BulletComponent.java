package main.spacegame.component;

import com.almasb.fxgl.dsl.components.AccumulatedUpdateComponent;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import main.spacegame.SpaceGameType;
import javafx.geometry.Point2D;

import static com.almasb.fxgl.dsl.FXGL.*;


public class BulletComponent extends AccumulatedUpdateComponent {

    private Entity grid;
    private GridComponent gComponent;
    private Point2D velocity;
    

    public BulletComponent() {
        super(5);
    }

    @Override
    public void onAdded() {
        velocity = entity.getComponent(ProjectileComponent.class).getVelocity();
    }

    @Override
    public void onAccumulatedUpdate(double tpf) {
        if (grid == null) {
            grid = getGameWorld().getSingleton(SpaceGameType.GRID);
            gComponent = grid.getComponent(GridComponent.class);
        }

        
        var p = entity.getPosition().add(entity.getTransformComponent().getRotationOrigin());

        gComponent.applyImplosiveForce(velocity.magnitude() / 60 * 10, p, 80 * 60 * tpf / getNumFramesToSkip());
    }

}