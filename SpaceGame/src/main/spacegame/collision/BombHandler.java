package main.spacegame.collision;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.physics.CollisionHandler;
import  main.spacegame.component.BombComponent;
import main.spacegame.component.BombComponent;

import static com.almasb.fxgl.dsl.FXGL.getGameScene;
import static com.almasb.fxgl.dsl.FXGL.spawn;
import static main.spacegame.SpaceGameType.BULLET;
import static main.spacegame.SpaceGameType.BOMB;



public class BombHandler extends CollisionHandler {
    public BombHandler(){
        super(BULLET,BOMB);
    }

    @Override

    protected void onCollisionBegin(Entity bullet, Entity bomb){


      bullet.removeFromWorld();
      bomb.removeFromWorld();
    }


}
