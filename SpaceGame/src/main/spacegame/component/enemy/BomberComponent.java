package main.spacegame.component.enemy;

import main.spacegame.factory.SpaceGameFactory;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;

public class BomberComponent extends AmongUsComponent{

    SpaceGameFactory factory = new SpaceGameFactory();

    BomberComponent(int moveSpeed, Entity player){
        super(moveSpeed, player);
    }

    @Override
    public void onUpdate(double tpf) {
        factory.spawnBomb();
    }
}
