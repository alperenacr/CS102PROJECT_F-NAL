package main.spacegame.collision;


import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.CollisionHandler;
import main.spacegame.component.PickUpComponent;
import javafx.animation.Interpolator;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;
import static main.spacegame.SpaceGameType.PICKUP_DASH;
import static main.spacegame.SpaceGameType.PLAYER;

public class PlayerPickUpHandler extends CollisionHandler {

    public PlayerPickUpHandler(){
        super(PLAYER, PICKUP_DASH);


    }

    protected void onCollisionBegin(Entity Player, Entity pickup){
        //pickup.getComponent(PickUpComponent.class).pickUp();   pickup componenttan sonra çalışcak

        animationBuilder().duration(Duration.seconds(0.5))
        .onFinished(pickup::removeFromWorld)
        .interpolator(Interpolator.EASE_IN)
        .scale(pickup)
        .from(new Point2D(pickup.getScaleX(), pickup.getScaleY()))
        .to(new Point2D(Player.getScaleX(), Player.getScaleY()))
        .buildAndPlay();


    }
    
}
