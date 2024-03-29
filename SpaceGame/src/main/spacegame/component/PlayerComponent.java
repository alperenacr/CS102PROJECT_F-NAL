package main.spacegame.component;
 import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import main.spacegame.component.DashComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.particle.ParticleComponent;
import com.almasb.fxgl.particle.ParticleEmitters;
import com.almasb.fxgl.time.LocalTimer;
import main.spacegame.factory.SpaceGameFactory;
import main.spacegame.SpaceGameType;
import javafx.geometry.Point2D;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.geto;
import static main.spacegame.Config.*;
import static main.spacegame.SpaceGameType.*;
import main.spacegame.WeaponType;

public class PlayerComponent extends Component {

    private Point2D oldPosition;
    DashComponent dash = new DashComponent(200);
    private int playerSpeed;
    private double speed;

    private boolean isShockwaveReady = true;

    private LocalTimer weaponTimer = newLocalTimer();

    public PlayerComponent(int playerSpeed) {
        this.playerSpeed = playerSpeed;
    }

    @Override
    public void onAdded() {
        oldPosition = entity.getPosition();
    }

    @Override
    public void onUpdate(double tpf) {
        
        speed = tpf * playerSpeed;

        var dir = entity.getPosition().subtract(oldPosition);

        if (!entity.getPosition().equals(oldPosition))
            entity.rotateToVector(dir);

        oldPosition = entity.getPosition();
    }

    public double getSpeed() {
        return speed;
    }

    public void shoot(Point2D shootPoint) {
        Point2D position = entity.getCenter();
        Point2D vectorToMouse = shootPoint.subtract(position);

        shootDirection(vectorToMouse);
    }

    public void shootDirection(Point2D direction) {
        if (weaponTimer.elapsed(WEAPON_DELAY)) {
            Point2D position = entity.getCenter();
            Point2D vectorToMouse = direction;

            WeaponType type = geto("weaponType");

            List<Entity> bullets = new ArrayList<>();

            switch (type) {
                case TRIPLE:

                    // spawn extra bullet
                    bullets.add(spawnBullet(position.subtract(
                            new Point2D(vectorToMouse.getY(), -vectorToMouse.getX()).normalize().multiply(15)
                    ), vectorToMouse));

                case DOUBLE:

                    // spawn extra bullet
                    bullets.add(spawnBullet(position.add(
                            new Point2D(vectorToMouse.getY(), -vectorToMouse.getX()).normalize().multiply(15)
                    ), vectorToMouse));

                case SINGLE:
                default:
                    bullets.add(spawnBullet(position, vectorToMouse));
                    break;
            }

            

            weaponTimer.capture();
        }
    }

    /**
     * @param position typically player center
     * @param direction bullet direction
     * @return bullet
     */
    private Entity spawnBullet(Point2D position, Point2D direction) {
        var data = new SpawnData(position.getX(), position.getY())
                .put("direction", direction);
        var e =  spawn("Bullet", data);

        SpaceGameFactory.respawnBullet(e, data);

        return e;
    }

    public boolean isShockwaveReady() {
        return isShockwaveReady;
    }

    public void setShockwaveReady(boolean shockwaveReady) {
        isShockwaveReady = shockwaveReady;
    }

    public void releaseShockwave() {
        if (isShockwaveReady) {
            isShockwaveReady = false;
            spawn("Shockwave", entity.getCenter());
        }
    }

    public void left(boolean flag) {
        if(!flag){
            entity.translateX(-speed);
        }
        else{
            dash.dashLeft(entity);
        }
    }

    public void right(boolean flag) {
        if(!flag){
            entity.translateX(speed);
        }
        else{
            dash.dashRight(entity);
        }
    }

    public void up(boolean flag) {
        if(!flag){
            entity.translateY(-speed);
        }
        else{
            dash.dashUp(entity);
        }
    }

    public void down(boolean flag) {
        if(!flag){
            entity.translateY(speed);
        }
        else{
            dash.dashDown(entity);
        }
    }

    public void playSpawnAnimation() {
        for (int i = 0; i < 6; i++) {
            final int j = i;

            runOnce(() -> {
                byType(GRID).get(0).getComponent(GridComponent.class)
                        .applyExplosiveForce(1500 + j*100, new Point2D(getAppWidth() / 2.0, getAppHeight() / 2.0), j*50 + 50);
            }, Duration.seconds(i * 0.4));
        }

        var emitter = ParticleEmitters.newExplosionEmitter(450);
        emitter.setSize(1, 16);
        emitter.setBlendMode(BlendMode.SRC_OVER);
        emitter.setStartColor(Color.color(1.0, 1.0, 1.0, 0.5));
        emitter.setEndColor(Color.BLUE);
        emitter.setMaxEmissions(20);
        emitter.setEmissionRate(0.5);

        entityBuilder()
                .at(entity.getCenter().subtract(8, 8))
                .with(new ParticleComponent(emitter))
                .with(new ExpireCleanComponent(Duration.seconds(3)))
                .buildAndAttach();

        animationBuilder()
                .fadeIn(entity)
                .buildAndPlay();
    }


}
 