package main.spacegame.component;

import static com.almasb.fxgl.dsl.FXGL.*;
import com.almasb.fxgl.entity.Entity;

public class DashComponent {

    int dashAmount;

    public DashComponent(int dashAmount) {
        this.dashAmount = dashAmount;
    }

    public void dashUp(Entity player) {
        player.translateY(-dashAmount);
        play("dash.wav");
    }

    public void dashDown(Entity player) {
        player.translateY(dashAmount);
        play("dash.wav");
    }

    public void dashRight(Entity player) {
        player.translateX(dashAmount);
        play("dash.wav");
    }

    public void dashLeft(Entity player) {
        player.translateX(-dashAmount);
        play("dash.wav");
    }


}
