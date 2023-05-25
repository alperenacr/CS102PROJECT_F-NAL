package main.spacegame.component;

import main.spacegame.Config;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;


import static com.almasb.fxgl.dsl.FXGL.*;

public class BackgroundMapComponent extends Component {

    private static final double FRONT1_SPEED = 0.25;

    private Texture background;

    @Override
    public void onAdded() {
        background = texture("background/bg_0.png");

        entity.getViewComponent().addChild(background);

        entity.setOpacity(0.75);
    }
    
    
    
    public void onUpdate(double tpf) {
        var viewport = getGameScene().getViewport();

        background.setTranslateX(-Config.OUTSIDE_DISTANCE -FRONT1_SPEED * viewport.getX());
        background.setTranslateY(-Config.OUTSIDE_DISTANCE -FRONT1_SPEED * viewport.getY());
    }

}