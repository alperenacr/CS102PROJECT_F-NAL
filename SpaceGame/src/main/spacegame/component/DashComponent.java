package main.spacegame.component;

import static com.almasb.fxgl.dsl.FXGL.*;
import com.almasb.fxgl.entity.Entity;

public class DashComponent {

    Entity player;
    int dashAmount;

    public DashComponent(Entity player, int dashAmount) {
        this.player = player;
        this.dashAmount = dashAmount;
    }

    public boolean dashUp() {
        if (player.getY() - dashAmount >= 0) {
            player.translateY(dashAmount);
            return true;
        }
        return false;
    }

    public boolean dashDown() {
        if (player.getBottomY() + dashAmount >= getAppHeight()) {
            player.translateY(-dashAmount);
            return true;
        }
        return false;
    }

    public boolean dashRight() {
        if (player.getRightX() + dashAmount <= getAppWidth()) {
            player.translateX(dashAmount);
            return true;
        }
        return false;
    }

    public boolean dashLeft() {
        if (player.getX() - dashAmount >= 0) {
            player.translateX(-dashAmount);
            return true;
        }
        return false;
    }

}
