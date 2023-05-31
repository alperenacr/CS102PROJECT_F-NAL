package main.spacegame.component.enemy;


import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;

public class WingedAlienComponent extends Component {
    private int moveSpeed;
    private Vec2 velocity = new Vec2();
    private Entity wingedAlien;
    Vec2 directionVector;
    private Entity player;

   public WingedAlienComponent(int moveSpeed, Entity player) {
        this.moveSpeed = moveSpeed;
        this.player = player;
    }

    @Override
    public void onAdded() {
        wingedAlien = entity;
    }

    public void onUpdate(double tpf) {
        
        move(moveSpeed);
    }

    private void move(double tpf) {
        
        directionVector = velocitySetter();

        velocity.addLocal(directionVector).mulLocal((float)tpf);

        wingedAlien.translate(new Point2D(velocity.x, velocity.y));;
    }


    private Vec2 velocitySetter() {
        if (wingedAlien.getY() > player.getY()) {
            if (wingedAlien.getX() == player.getX()) {
                return new Vec2(0,1);
            }
            else if (wingedAlien.getX() < player.getX()){
                return new Vec2(1,0);
            }
            else {
                return new Vec2(-1,0);
            }
        }

        else if (wingedAlien.getY() < player.getY()) {
            if (wingedAlien.getX() == player.getX()) {
                return new Vec2(0,-1);
            }
            else if (wingedAlien.getX() < player.getX()){
                return new Vec2(1,0);
            }
            else {
                return new Vec2(-1,0);
            }
        }

        else if (wingedAlien.getX() > player.getX()) {
            if (wingedAlien.getY() == player.getY()) {
                return new Vec2(-1, 0);
            }
            else if (wingedAlien.getY() < player.getY()){
                return new Vec2(0, -1);
            }
            else {
                return new Vec2(0, 1);
            }
        }

        else if (wingedAlien.getX() < player.getX()) {
            if (wingedAlien.getY() == player.getY()) {
                return new Vec2(1, 0);
            }
            else if (wingedAlien.getY() < player.getY()){
                return new Vec2(0, -1);
            }
            else {
                return new Vec2(0, 1);
            }
        }
        
        return new Vec2(0,0);
    }

}
