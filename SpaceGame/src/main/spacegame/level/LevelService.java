package main.spacegame.level;

import com.almasb.fxgl.core.EngineService;
import com.almasb.fxgl.core.math.FXGLMath;

import static main.spacegame.level.LevelFactory.level;


public class LevelService extends EngineService {
    private static Level levels = new Level(){
        level()
    };

    private Level level = null;

    public void spawnLevel(){
        if (level != null) {
            level.end();
        }

        level = FXGLMath.random(level).get();
        level.start();

    }

    public void onGameUpdate(double tpf){
        if(level != null)
        {
            level.onUpdate(tpf);
        }
    }
}
