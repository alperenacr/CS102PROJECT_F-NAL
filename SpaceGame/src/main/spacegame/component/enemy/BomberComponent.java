package main.spacegame.component.enemy;

import main.spacegame.factory.SpaceGameFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Entity;
public class BomberComponent extends AmongUsComponent{

    SpaceGameFactory factory = new SpaceGameFactory();

    BomberComponent(int moveSpeed, Entity player){
        super(moveSpeed, player);
    }

    @Override
    public void onUpdate(double tpf) {
        if (timer.elapsed(delay)) {
            factory.spawnBomb(new SpawnData(entity.getX(), entity.getY()));
        }
        
    }
}
