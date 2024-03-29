package main.spacegame.factory;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.AutoRotationComponent;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.dsl.components.IntervalSwitchComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.Texture;

import main.spacegame.SpaceGameApp;
import main.spacegame.component.PlayerComponent;
import main.spacegame.component.enemy.*;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;

import static main.spacegame.Config.*;
import static main.spacegame.SpaceGameType.*;
import main.spacegame.SpaceGameApp;

public class EnemyFactory implements EntityFactory{
    
    private static final int SPAWN_DISTANCE = 50;

    /**
     * These correspond to top-left, top-right, bottom-right, bottom-left.
     */
    private static final Point2D[] spawnPoints = {
            new Point2D(SPAWN_DISTANCE, SPAWN_DISTANCE),
            new Point2D(getAppWidth() - SPAWN_DISTANCE, SPAWN_DISTANCE),
            new Point2D(getAppWidth() - SPAWN_DISTANCE, getAppHeight() - SPAWN_DISTANCE),
            new Point2D(SPAWN_DISTANCE, getAppHeight() - SPAWN_DISTANCE)
    };

    private static Point2D getRandomSpawnPoint() {
        return spawnPoints[FXGLMath.random(0, 3)];
    }
    //should be looked collision for obstacles

    private static final Texture WINGEDALIEN_TEXTURE = texture("WingedAlien.png", 60, 60);
    private static final Texture BOMBER_TEXTURE = texture("Bomber.png", 80, 80);
    private static final Texture BOSS_TEXTURE = texture("Boss.png", 100, 100);
    private static final Texture AMONGUS_TEXTURE = texture("AmongUs.png", 80, 80);


    @Spawns("Winged Alien")
    public Entity spawnWingedAlien(SpawnData data) {
          var e = entityBuilder()
                .type(WİNGEDALİEN)
                .at(getRandomSpawnPoint())
                .viewWithBBox(WINGEDALIEN_TEXTURE.copy())  //.viewWithBBox(BOSS_TEXTURE.copy())
                .with(new HealthIntComponent(ENEMY_HP))
                .with(new CollidableComponent(true))
                .with(new BossComponent(WINGEDALIEN_MOVE_SPEED, FXGL.<SpaceGameApp>getAppCast().getPlayer()))
                .zIndex(ENEMIES_Z_INDEX)
                .build();

        e.setReusable(true);


        return e;
    }

    public static void respawnWingedAlien(Entity entity) {
        entity.getComponent(HealthIntComponent.class).setValue(ENEMY_HP);
        entity.setPosition(getRandomSpawnPoint());

        entity.getComponent(WingedAlienComponent.class).resume();  
    }

    @Spawns("Bomber")
    public  Entity spawnBomber(SpawnData data) {

        var e = entityBuilder()
                .type(BOMBER)
                .at(getRandomSpawnPoint())
                .viewWithBBox(BOMBER_TEXTURE.copy()) //.viewWithBBox(BOMBER_TEXTURE.copy())
                .with(new HealthIntComponent(ENEMY_HP))
                .with(new CollidableComponent(true))
                .with(new BomberComponent())
                .zIndex(ENEMIES_Z_INDEX)
                .build();


        return e;
    }

    public static void respawnBomber(Entity entity) {
        entity.getComponent(HealthIntComponent.class).setValue(ENEMY_HP);
        entity.setPosition(getRandomSpawnPoint());

        entity.getComponent(BomberComponent.class).resume();  
    }

    @Spawns("Boss")
    public Entity spawnBoss(SpawnData data) {

        var e = entityBuilder()
                .type(BOSS)
                .at(getRandomSpawnPoint())
                .viewWithBBox(BOSS_TEXTURE.copy())  //.viewWithBBox(BOSS_TEXTURE.copy())
                .with(new HealthIntComponent(ENEMY_HP))
                .with(new CollidableComponent(true))
                .with(new BossComponent(BOSS_MOVE_SPEED, FXGL.<SpaceGameApp>getAppCast().getPlayer()))
                .zIndex(ENEMIES_Z_INDEX)
                .build();


        return e;
    }

    public static void respawnBoss(Entity entity) {
        entity.getComponent(HealthIntComponent.class).setValue(ENEMY_HP);
        entity.setPosition(getRandomSpawnPoint());

        entity.getComponent(BossComponent.class).resume();  
    }

    @Spawns("AmongUs")
    public Entity spawnAmongUs(SpawnData data) {

        var e = entityBuilder()
                .type(AMONGUS)
                .at(getRandomSpawnPoint())
                .viewWithBBox(AMONGUS_TEXTURE.copy())
                .with(new HealthIntComponent(ENEMY_HP))
                .with(new CollidableComponent(true))
                .with(new AmongUsComponent(AMONGUS_MOVE_SPEED, FXGL.<SpaceGameApp>getAppCast().getPlayer()))
                .zIndex(ENEMIES_Z_INDEX)
                .build();


        return e;
    }

    public static void respawnAmongUs(Entity entity) {
        entity.getComponent(HealthIntComponent.class).setValue(ENEMY_HP);
        entity.setPosition(getRandomSpawnPoint());

        entity.getComponent(AmongUsComponent.class).resume();  
    }

}
