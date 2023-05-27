package main.spacegame.level;
import java.util.ArrayList;
import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.app.scene.SimpleGameMenu;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.virtual.VirtualButton;
import com.almasb.fxgl.input.virtual.VirtualJoystick;
import com.almasb.fxgl.physics.CollisionDetectionStrategy;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsWorld;
import com.almasb.fxgl.ui.ProgressBar;
import javafx.beans.binding.Bindings;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.CacheHint;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;
import com.almasb.fxgl.animation.Animation;
import com.almasb.fxgl.core.collection.Array;
import main.spacegame.factory.SpaceGameFactory;
import com.almasb.fxgl.entity.Entity;
import main.spacegame.factory.EnemyFactory;
import com.almasb.fxgl.entity.Entity;
import static com.almasb.fxgl.dsl.FXGL.*;

public class Level {
    
    protected final Array<Entity> entities = new Array<>(100);

    protected final Array<Animation<?>> animations = new Array<>(100);

    public void start(){
        for(int i = 0; i < 100; i++)
        {
            var myEntity = spawn("Bomber");
            myEntity.setOpacity(0);
            entities.add(myEntity);
        }
    }
    public void end(){
        for(int i = 0; i < 100;i++)
        {
            animations.get(i).stop(); // ??
        }

        for(int i = 0; i < 100;i++ )
        {
            if(entities.get(i).isActive())
            {
                entities.get(i).removeFromWorld();
            }

        }

    }
    public void onStart(){

    }
    public void onEnd(){

    }
    public void onUpdate(double tpf){
        for (int i = 0; i < 100; i++) {
            Animation anim = animations.get(i);
            anim.onUpdate(tpf);
        }
    }

}
