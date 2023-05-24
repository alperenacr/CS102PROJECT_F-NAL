package main.spacegame.component;


import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import main.spacegame.SpaceGameApp;

public class PickUpComponent extends Component {
    private Runnable onPickup;

    public void PickupComponent(Runnable onPickup) {
        this.onPickup = onPickup;

    }

    public void pickUp() {

        onPickup.run();
    }

    


}
