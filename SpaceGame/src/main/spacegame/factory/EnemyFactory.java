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
//        var beepSwitch = new IntervalSwitchComponent(false, Duration.seconds(0.5));

        var e = entityBuilder(data)
                .type(WİNGEDALİEN)
                .at(getRandomSpawnPoint())
//                .bbox(new HitBox(new Point2D(15, 15), BoundingShape.box(30, 30))) //???????????????????????????
                .view(WINGEDALIEN_TEXTURE.copy())
//                .with(beepSwitch)
                .with(new HealthIntComponent(ENEMY_HP))
                .with(new CollidableComponent(true))
                .with(new WingedAlienComponent(WINGEDALIEN_MOVE_SPEED, FXGL.<SpaceGameApp>getAppCast().getPlayer()))   
                .zIndex(ENEMIES_Z_INDEX)
                .build();

        e.setReusable(true);


        return e;
    }


    @Spawns("Bomber")
    public  Entity spawnBomber(SpawnData data) {

        var e = entityBuilder()
                .type(BOMBER)
                .at(getRandomSpawnPoint())
                .view(BOMBER_TEXTURE.copy())
                .with(new HealthIntComponent(ENEMY_HP))
                .with(new CollidableComponent(true))
                .with(new BomberComponent(BOMBER_MOVE_SPEED, FXGL.<SpaceGameApp>getAppCast().getPlayer()))
                .zIndex(ENEMIES_Z_INDEX)
                .build();


        return e;
    }

    @Spawns("Boss")
    public Entity spawnBoss(SpawnData data) {

        var e = entityBuilder()
                .type(BOSS)
                .at(getRandomSpawnPoint())
                .view(BOSS_TEXTURE.copy())
                .with(new HealthIntComponent(ENEMY_HP))
                .with(new CollidableComponent(true))
                .with(new BossComponent(BOSS_MOVE_SPEED, FXGL.<SpaceGameApp>getAppCast().getPlayer()))
                .zIndex(ENEMIES_Z_INDEX)
                .build();


        return e;
    }

    @Spawns("Amongus")
    public Entity spawnAmongUs(SpawnData data) {

        var e = entityBuilder()
                .type(AMONGUS)
                .at(getRandomSpawnPoint())
                .view(AMONGUS_TEXTURE.copy())
                .with(new HealthIntComponent(ENEMY_HP))
                .with(new CollidableComponent(true))
                .with(new AmongUsComponent(AMONGUS_MOVE_SPEED, FXGL.<SpaceGameApp>getAppCast().getPlayer()))
                .zIndex(ENEMIES_Z_INDEX)
                .build();


        return e;
    }

}
