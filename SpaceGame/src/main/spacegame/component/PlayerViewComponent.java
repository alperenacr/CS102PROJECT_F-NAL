package main.spacegame.component;



import static com.almasb.fxgl.dsl.FXGL.addUINode;
import static com.almasb.fxgl.dsl.FXGL.animationBuilder;
import static com.almasb.fxgl.dsl.FXGL.byType;
import static com.almasb.fxgl.dsl.FXGL.centerText;
import static com.almasb.fxgl.dsl.FXGL.entityBuilder;
import static com.almasb.fxgl.dsl.FXGL.eventBuilder;
import static com.almasb.fxgl.dsl.FXGL.getAppCenter;
import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static main.spacegame.Config.*;
import static main.spacegame.SpaceGameType.*;
import java.awt.Component;
import static com.almasb.fxgl.dsl.FXGL.getAppWidth;
import static com.almasb.fxgl.dsl.FXGL.getDialogService;
import static com.almasb.fxgl.dsl.FXGL.getGameController;
import static com.almasb.fxgl.dsl.FXGL.getGameScene;
import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGL.getPhysicsWorld;
import static com.almasb.fxgl.dsl.FXGL.getSaveLoadService;
import static com.almasb.fxgl.dsl.FXGL.getService;
import static com.almasb.fxgl.dsl.FXGL.getSettings;
import static com.almasb.fxgl.dsl.FXGL.getUIFactoryService;
import static com.almasb.fxgl.dsl.FXGL.getWorldProperties;
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
import com.almasb.fxgl.dsl.components.view.ChildViewComponent;
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
import javafx.scene.paint.Paint;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;
import main.spacegame.component.PlayerComponent;
import main.spacegame.ui.SpaceGameMainMenu;
import main.spacegame.ui.SpaceGamePauseMenu;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getInput;
import static com.almasb.fxgl.dsl.FXGL.getd;
import static com.almasb.fxgl.dsl.FXGL.geti;
import static com.almasb.fxgl.dsl.FXGL.geto;
import static com.almasb.fxgl.dsl.FXGL.inc;
import static com.almasb.fxgl.dsl.FXGL.isReleaseMode;
import static com.almasb.fxgl.dsl.FXGL.loopBGM;
import static com.almasb.fxgl.dsl.FXGL.onCollisionCollectible;
import static com.almasb.fxgl.dsl.FXGL.onKey;
import static com.almasb.fxgl.dsl.FXGL.onKeyDown;
import static com.almasb.fxgl.dsl.FXGL.run;
import static com.almasb.fxgl.dsl.FXGL.runOnce;
import static com.almasb.fxgl.dsl.FXGL.set;
import static com.almasb.fxgl.dsl.FXGL.spawn;
import static com.almasb.fxgl.dsl.FXGL.spawnFadeIn;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getInput;
import static main.spacegame.Config.COLLISION_PENALTY;
import static main.spacegame.Config.GAME_OVER_SCORE;
import static main.spacegame.Config.IS_MENU;
import static main.spacegame.Config.IS_NO_ENEMIES;
import static main.spacegame.Config.IS_RELEASE;
import static main.spacegame.Config.IS_SOUND_ENABLED;
import static main.spacegame.Config.MAX_MULTIPLIER;
import static main.spacegame.Config.OUTSIDE_DISTANCE;
import static main.spacegame.Config.PENALTY_INTERVAL;
import static main.spacegame.Config.PLAYER_HP;
import static main.spacegame.Config.SAVE_FILE_NAME;
import static main.spacegame.Config.TIME_PENALTY;
import static main.spacegame.Config.WAVE_SPAWN_INTERVAL;
import static main.spacegame.SpaceGameType.BOMBER;
import static main.spacegame.SpaceGameType.BOSS;
import static main.spacegame.SpaceGameType.BULLET;
import static main.spacegame.SpaceGameType.GRID;
import static main.spacegame.SpaceGameType.PARTICLE_LAYER;
import static main.spacegame.SpaceGameType.PLAYER;
import static main.spacegame.SpaceGameType.SHOCKWAVE;

import java.util.Map;
import javafx.scene.paint.Color;
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

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.CacheHint;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import com.almasb.fxgl.entity.components.ViewComponent;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;
import main.spacegame.collision.BombHandler;
import main.spacegame.component.GridComponent;
import main.spacegame.component.PlayerComponent;
import main.spacegame.factory.EnemyFactory;
import main.spacegame.factory.SpaceGameFactory;
import main.spacegame.level.LevelService;
import main.spacegame.ui.MainUI;
import main.spacegame.ui.SpaceGameMainMenu;

public class PlayerViewComponent extends ChildViewComponent {
    
    public PlayerViewComponent()      
    {
        super(35, 35, false);

        
        var playerHealth = new Arc(0, 0, 60, 60, -100, 0);
        
        playerHealth.setStroke(Color.GRAY.brighter());
        playerHealth.setStrokeWidth(3.0);
        playerHealth.setFill(null);
        playerHealth.lengthProperty().bind(getip("hp").multiply(-360.0).divide(PLAYER_HP));
        
        getViewRoot().getChildren().addAll(playerHealth);
     }

}
