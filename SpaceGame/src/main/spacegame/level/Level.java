package main.spacegame.level;

public class Level {
    
    private ArrayList<Entity> entities = new ArrayList<Entity>();

    private ArrayList<Animations> animations = new ArrayList<Animations>();
    public void start(){
        for(int i = 0; i < 100; i++)
        {
            Entity entity = entityBuilder().spawn("Bomber");
            entities.add(entity);
        }
    }
    public void end(){
        for(int i = 0; i < 100;i++)
        {
            animations.get(i).stop(); // ??
        }

        for(int i = 0; i <  )
        {
            if(entities.get(i).isActive())
            {
                entities.removeFromWorld();
            }

        }

    }
    public void onStart(){

    }
    public void onEnd(){

    }
    public void onUpdate(double tpf){
        for(int i = 0; i < animations.size();i++)
        {
            // ?????????????????????????
        }
    }

}
