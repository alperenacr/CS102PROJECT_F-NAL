package main.spacegame.component;

import com.almasb.fxgl.entity.Entity;

public class DashComponent {

    Entity player;
    int dashAmount;

    DashComponent(Entity player, int dashAmount){
        this.player = player;
        this.dashAmount = dashAmount;
    }

    public void dashUp() {
        player.translateY(dashAmount);
    }
    public void dashDown() {
        player.translateY(-dashAmount);
    }
    public void dashRight() {
        player.translateX(dashAmount);
    }
    public void dashLeft() {
        player.translateX(-dashAmount);
    }



}
