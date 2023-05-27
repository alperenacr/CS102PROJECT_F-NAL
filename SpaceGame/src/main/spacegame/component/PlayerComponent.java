package com.almasb.fxglgames.geowars.component;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.particle.ParticleComponent;
import com.almasb.fxgl.particle.ParticleEmitters;
import com.almasb.fxgl.time.LocalTimer;
import com.almasb.fxglgames.geowars.factory.GeoWarsFactory;
import com.almasb.fxglgames.geowars.WeaponType;
import javafx.geometry.Point2D;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.geto;
import static com.almasb.fxglgames.geowars.Config.MAX_CHARGES_SECONDARY;
import static com.almasb.fxglgames.geowars.Config.WEAPON_DELAY;
import static com.almasb.fxglgames.geowars.GeoWarsType.GRID;

public class PlayerComponent extends Component {
    
    public Point2D oldPosition;
    public int playerSpeed=10;
    public double speed=1;
    public int health=10;
 
    public LocalTimer weaponTimer = newLocalTimer();

    public PlayerComponent(int playerSpeed) 
    {
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

        if (!entity.getPosition().equals(oldPosition))
        {
            entity.rotateToVector(direction);
        }
        oldPosition = entity.getPosition();
    }

    public double getSpeed() {
        return speed;
    }

    public void left() {
        entity.translateX(-speed);
    }

    public void right() {
        entity.translateX(speed);
    }

    public void up() {
        entity.translateY(-speed);
    }

    public void down() {
        entity.translateY(speed);
    }

    
}
