package main.spacegame.component.enemy;

import main.spacegame.factory.SpaceGameFactory;
import com.almasb.fxgl.entity.Entity;
import static com.almasb.fxgl.dsl.FXGL.*;

public class BomberComponent extends AmongUsComponent{

    SpaceGameFactory factory = new SpaceGameFactory();

    public BomberComponent(int moveSpeed, Entity player){
        super(moveSpeed, player);
    }

    @Override
    public void onUpdate(double tpf) {
        if (timer.elapsed(delay)) {
            spawn("Bomb");
        }
        
    }
}
