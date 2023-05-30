package main.spacegame.component;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.particle.ParticleComponent;
import com.almasb.fxgl.particle.ParticleEmitters;
import com.almasb.fxgl.time.LocalTimer;
import main.spacegame.factory.*;
import main.spacegame.WeaponType;
import main.spacegame.factory.SpaceGameFactory;
import javafx.geometry.Point2D;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import static main.spacegame.WeaponType.*;

import java.util.ArrayList;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.geto;

import static main.spacegame.Config.WEAPON_DELAY;


public class PlayerComponent extends Component {
    
    public Point2D oldPosition;
    public int playerSpeed=10;
    public double speed=1;
    public int health=10;
    DashComponent dash = new DashComponent(200);

    public LocalTimer weaponTimer = newLocalTimer();

    public PlayerComponent(int playerSpeed, Entity player) 
    {
        // düzeltme  yapılacak
        this.playerSpeed = playerSpeed;
    }

    @Override
    public void onAdded() 
    {
        oldPosition = entity.getPosition();
    }
    
    public void DecreaseHealth(int buKadarAzalt)
    {
        health=health-buKadarAzalt;
    }

    @Override
    public void onUpdate(double tpf) {
        
        speed = tpf * playerSpeed;

        var direction = entity.getPosition().subtract(oldPosition);

        boolean sttmnt = !entity.getPosition().equals(oldPosition);
        if (sttmnt)
        {
            entity
                .rotateToVector(direction);
        }
        oldPosition = entity
            .getPosition();
    }

    public double getSpeed() {
        return speed;
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
    
    public void shoot(Point2D shootPointCartesian) 
    {
        Point2D position;
        Point2D vectorToMouse;
        
        // Get the position of the player entity
        position = entity.getCenter();
        
        // Calculate the vector from the player to the mouse click position
        vectorToMouse = shootPointCartesian.subtract(position);

        // Call the shootDirection method with the calculated vector
        shootDirection(vectorToMouse);
     }

    public void shootDirection(Point2D shootdirection) 
     {
        Point2D position;
        Point2D vectorToMouse;

        boolean sttmnt = weaponTimer.elapsed(WEAPON_DELAY);
        // Check if enough time has passed since the last shot
        if (sttmnt) 
        {

            // Get the position of the player entity
            position = entity.getCenter();

            // Use the given direction as the bullet's direction
            vectorToMouse = shootdirection;

            // Get the current weapon type
            WeaponType type = geto("weaponType");

            // Create a list to hold all bullets that will be spawned
            List<Entity> bullets = new ArrayList<>();

            // Spawn bullets based on weapon type
            switch (type) 
            {
                case TRIPLE:

                    // Spawn two extra bullets at an angle from the main bullet
                    bullets
                        .add(spawnBullet(position
                                            .subtract(

                                 new Point2D(vectorToMouse
                                        .getY(),
                                        -vectorToMouse.getX())
                                        .normalize()
                                        .multiply(15)),
                                        vectorToMouse));
                case DOUBLE:

                    // Spawn one extra bullet at an angle from the main bullet
                    bullets
                        .add(spawnBullet(position.add(
                            new Point2D(vectorToMouse
                                        .getY(),
                                        -vectorToMouse
                                        .getX())
                                        .normalize()
                                        .multiply(15)),
                                        vectorToMouse));

                case SINGLE:

                     // Spawn a single bullet in the given direction
                    bullets
                        .add(spawnBullet(
                            position,
                            vectorToMouse));

                default:
                    // Spawn a single bullet in the given direction
                    bullets
                        .add(spawnBullet(
                            position,
                            vectorToMouse));
                    break;
            }

            // Capture the current time as the last shot time
            weaponTimer.capture();

        }
    }

    /**
     * Spawns a bullet entity at the given position and with the given direction.
     *
     * position  The position to spawn the bullet at.
     * direction The direction for the bullet to travel in.
     * @return The spawned bullet entity.
     */
    public Entity spawnBullet(Point2D position, Point2D direction) 
    {
        var BulletData;
        var entity;
        
        BulletData = new SpawnData(position
                                   .getX(),
                                   position
                                   .getY())
                                   .put("direction",
                                    direction);
       
        entity = spawn("Bullet", data);

        spaceGameFactory.respawnBullet(e, data);

        return entity;
    }

    
}
