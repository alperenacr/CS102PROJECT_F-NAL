package main.spacegame.level;

import com.almasb.fxgl.animation.Animation;
import com.almasb.fxgl.core.collection.Array;
import com.almasb.fxgl.entity.Entity;

import static com.almasb.fxgl.dsl.FXGL.*;

public class Level {
    
    protected final Array<Entity> entities = new Array<>(10000);

    protected final Array<Animation<?>> animations = new Array<>(10000);

    public void start(){
        for(int i = 0; i < 100; i++)
        {
            var myEntity = spawn("Bomber");
            myEntity.setOpacity(0);

            entities.add(myEntity);
        }

        animations.forEach(Animation::start);

    }
    public void end(){
        for(int i = 0; i < 100;i++)
        {
            animations.get(i).stop(); // ??
        }
        animations.clear();

        for(int i = 0; i < 100;i++ )
        {
            if(entities.get(i).isActive())
            {
                entities.get(i).removeFromWorld();
            }

        }

    }
    public void onStart(){

    }
    public void onEnd(){

    }
    public void onUpdate(double tpf){
        for (int i = 0; i < 100; i++) {
            animations.get(i).onUpdate(tpf);
        }
    }

}
