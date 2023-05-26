package main.spacegame.level;

public class LevelFactory {

    private static final double BOMBER_WIDTH = (202 * 0.15);
    private static final double BOMBER_HEIGHT = (166 * 0.15);
    
    public Level level(){
        return new Level(){
            @Override
            protected void onStart(){
                for(int i = 0; i < entities.size(); i++)
                {
                    Entity entity = entities.get(i);

                    Animation animation = animationBuilder()
                            .repeatInfinitely()
                            .autoReverse(true)
                            .duration(Duration.seconds(10))
                            .delay(Duration.seconds(i * 0.1))
                            .translate(entity)
                            .from(new Point2D(0, 0))
                            .to(new Point2D(getAppWidth() - BOMBER_WIDTH, getAppHeight() - BOMBER_HEIGHT))
                            .build();

                    Animation animation2 = animationBuilder()
                            .duration(Duration.seconds(1))
                            .delay(Duration.seconds(i * 0.1))
                            .fadeIn(entity)
                            .build();

                    animations.add(animation);
                    animations.add(animation2);
                }
            }
        }
    }


}
