package main.spacegame.component;

import main.spacegame.Config;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;


import static com.almasb.fxgl.dsl.FXGL.*;

public class BackgroundMapComponent extends Component {

    private static final double FRONT1_SPEED = 0.25;

    private Texture starsLayer0;

    @Override
    public void onAdded() {
        starsLayer0 = texture("Background.png");

        entity.getViewComponent().addChild(starsLayer0);

        entity.setOpacity(0.75);
    }
    
    
    
    public void onUpdate(double tpf) {
        var viewport = getGameScene().getViewport();

        starsLayer0.setTranslateX(-Config.OUTSIDE_DISTANCE -FRONT1_SPEED * viewport.getX());
        starsLayer0.setTranslateY(-Config.OUTSIDE_DISTANCE -FRONT1_SPEED * viewport.getY());
    }

}