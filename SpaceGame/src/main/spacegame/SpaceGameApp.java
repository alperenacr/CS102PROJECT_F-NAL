package main.spacegame;
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
import javafx.scene.paint.Color;
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


public class SpaceGameApp extends GameApplication{

    private Entity player;
    private PlayerComponent playerComponent;
    
    boolean flag = false;
    public int dashes = PLAYER_DASH_COUNT;

    private VirtualJoystick moveJoystick;
    private VirtualJoystick shootJoystick;

    public Entity getPlayer() {
        return player;
    }

    public static void main(String[] args) {
        launch( args);
    }

    

    @Override
    protected void initSettings(GameSettings settings) {
        

        settings.setWidth(1920);
        settings.setHeight(1080);
        settings.setTitle("Space_Game");
        settings.setVersion("1.0");
        settings.setProfilingEnabled(false);
        settings.setMainMenuEnabled(true);
        settings.setGameMenuEnabled(true);
        

        settings.setSceneFactory(new SceneFactory() {
            
            @Override
           public FXGLMenu newGameMenu(){
            return new SpaceGamePauseMenu();
           }

            @Override
            public FXGLMenu newMainMenu() {
                return new SpaceGameMainMenu();
            }
        });}
         
         @Override
    protected void onPreInit() {
       // sound
    }
    protected void initInput() {
        onKeyDown(KeyCode.V, () -> {
                if (dashes > 0) {
                    flag = true;
                    dashes--;
                }
            });
            

            getInput().addAction(new UserAction("Up") {
                @Override
                protected void onAction() {
                    playerComponent.up(flag);
                    flag = false;
                }
            }, KeyCode.W, VirtualButton.UP);

            getInput().addAction(new UserAction("Down") {
                @Override
                protected void onAction() {
                    playerComponent.down(flag);
                    flag = false;
                }
            }, KeyCode.S, VirtualButton.DOWN);

            getInput().addAction(new UserAction("Left") {
                @Override
                protected void onAction() {
                    playerComponent.left(flag);
                    flag = false;
                }
            }, KeyCode.A, VirtualButton.LEFT);

            getInput().addAction(new UserAction("Right") {
                @Override
                protected void onAction() {
                    playerComponent.right(flag);
                    flag = false;
                }
            }, KeyCode.D, VirtualButton.RIGHT);

            // TODO: use sticks to aim?
            // TODO: allow virtual button + sticks
            getInput().addAction(new UserAction("Shoot Mouse") {
                @Override
                protected void onAction() {
                    playerComponent.shoot(getInput().getMousePositionWorld());
                }
            }, MouseButton.PRIMARY);
        }


        
    
        protected void initGameVars(Map<String, Object> vars) {
            vars.put("score", 0);
            vars.put("multiplier", 1);
            vars.put("kills", 0);
            vars.put("lives", 3);
            vars.put("isRicochet", false);
            vars.put("secondaryCharge", 0);
            vars.put("weaponType", WeaponType.SINGLE);
            vars.put("lastHitTime", 0);
            vars.put("time", 0.0);
        }

       
        protected void initGame(){
            
        }

        protected void initEnemySpawns(){

        }

        protected void initPhysics(){

        }

        protected void onCollisionBegin(Entity a, Entity b){
            
        }
        //collision handler physics.add atılacak her düşman için

        protected void killPlayer(){

        }

        protected void initUI(){
           
            
            
        
            
        }

        protected void onUpdate(double tpf){

        }

        protected void killEnemy(Entity enemy){

        }
        
        protected void addScoreKill(Point2D enemyPosition){

        }

        private void gameOver(){

        }
        
}
