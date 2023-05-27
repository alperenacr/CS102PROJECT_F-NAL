package main.spacegame.level;

public class LevelService extends EngineService {
    private static Level level = new Level(){
        level()
    };

    private Level level = null;

    public void spawnLevel(){
        //

    }

    public void onGameUpdate(double tpf){
        if(level != null)
        {
            level.onUpdate(tpf);
        }
    }
}
