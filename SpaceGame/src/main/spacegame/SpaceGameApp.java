package main.spacegame;
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
import main.spacegame.ui.SpaceGameMainMenu;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getInput;

public class SpaceGameApp extends GameApplication{
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
            public FXGLMenu newMainMenu() {
                return new SpaceGameMainMenu();
            }
        });}
         
         @Override
    protected void onPreInit() {
       // sound
    }
    protected void initInput() {

        
        }
        protected void initGameVars(Map<String, Object> vars) {
            vars.put("score", 0);
            vars.put("multiplier", 1);
            vars.put("kills", 0);
            vars.put("lives", 3);
            vars.put("isRicochet", false);
            vars.put("secondaryCharge", 0);
            vars.put("weaponType", WeaponType.GUN);
           // vars.put("hp", PLAYER_HP);
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
