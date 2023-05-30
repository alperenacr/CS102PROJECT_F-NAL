package main.spacegame.component.enemy;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.util.Duration;
import java.util.Random;

public class AmongUsComponent extends Component {

    private Point2D velocity = new Point2D(0, 0);
    private int moveSpeed;
    private Entity player;
    private Entity amongUs;
    private Random randomizer;

    protected LocalTimer timer = FXGL.newLocalTimer();
    protected Duration delay = Duration.seconds(0.15);

    public AmongUsComponent(int moveSpeed, Entity player) {
        this.moveSpeed = moveSpeed;
        this.player = player;
        randomizer = new Random();
    }

    @Override
    public void onUpdate(double tpf) {
        
        if (timer.elapsed(delay)) {
            int dice = randomizer.nextInt(7);
            if(dice > 1){
                velocityChanger(tpf);
                timer.capture();
            }
            else{
                chaosVelocityChanger(tpf);
                timer.capture();
            }
        }
        
        amongUs.translate(velocity);
    }

    @Override
    public void onAdded() {
        amongUs = entity;

    }

    private void velocityChanger(double tpf){
        Point2D wantedVelocity = player.getCenter()
                .subtract(amongUs.getCenter())
                .normalize()
                .multiply(moveSpeed);

    
        velocity = velocity.add(wantedVelocity).multiply(tpf);
    }

 
    private void chaosVelocityChanger(double tpf){
        Point2D wantedVelocity2 = player.getCenter().multiply((5 * randomizer.nextDouble(2) - 1))
                .subtract(amongUs.getCenter())
                .normalize()
                .multiply(moveSpeed);
        
                velocity = velocity.add(wantedVelocity2).multiply(tpf);
    }

}