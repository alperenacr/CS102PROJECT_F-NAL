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
import com.almasb.fxgl.entity.level.Level;
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
import javafx.scene.layout.Background;
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
import main.spacegame.service.HighScoreService;
import main.spacegame.service.LevelPressureService;
import main.spacegame.ui.MainUI;
import main.spacegame.ui.SpaceGameMainMenu;

import main.spacegame.service.HighScoreService;


public class SpaceGameApp extends GameApplication{

    private Entity player;
    private PlayerComponent playerComponent;

    private LevelPressureService pressureService;
    
    boolean flag = false;
    public int dashes = PLAYER_DASH_COUNT;

 

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
        settings.setProfilingEnabled(true); // ??
        settings.setFullScreenAllowed(true);
        settings.setFullScreenFromStart(true);

        settings.setCollisionDetectionStrategy(CollisionDetectionStrategy.GRID_INDEXING);
        settings.addEngineService(HighScoreService.class);
        settings.addEngineService(LevelPressureService.class);

        settings.addEngineService(LevelService.class);



        

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
        getSettings().setGlobalSoundVolume(0.7);
        getSettings().setGlobalMusicVolume(0.7);

        loopBGM("dans eden sincap ama epilepsi garantili (100).mp3");
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

                    getInput().addAction(new UserAction("Shoot Mouse") {
                @Override
                protected void onAction() {
                    playerComponent.shoot(getInput().getMousePositionWorld());
                }
            }, MouseButton.PRIMARY);
            getInput().addAction(new UserAction("MouseRight") {
                @Override
                protected void onAction(){
                    playerComponent.shoot(getInput().getMousePositionWorld());
                    playerComponent.shoot(getInput().getMousePositionWorld());
                }
            }, MouseButton.SECONDARY);
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
            vars.put("dash",dashes);
            vars.put("hp", 6);
        }

       
        protected void initGame(){
            getGameWorld().addEntityFactory(new SpaceGameFactory());
            getGameWorld().addEntityFactory(new EnemyFactory());
            spawn("Background");

            player = spawn("Player");
            playerComponent = player.getComponent(PlayerComponent.class);

            pressureService = getService(LevelPressureService.class);

            int dist = OUTSIDE_DISTANCE;
            
            getGameScene().getViewport().setLazy(true);
            getGameScene().getViewport().setBounds(-dist, -dist, getAppWidth()+dist,getAppHeight() +dist);
            getGameScene().getViewport().bindToEntity(player, getAppWidth() /2.0 - player.getWidth() / 2.0,getAppHeight() / 2.0 - player.getHeight() / 2.0 );



            getWorldProperties().<Integer>addListener("score", (prev, now) -> {
                getService(HighScoreService.class).updatePlayerScore("alperen",now , 1);
    
                if (now >= GAME_OVER_SCORE)
                    gameOver();
            });
    
            getWorldProperties().<Integer>addListener("lives", (prev, now) -> {
                if (now == 0)
                    gameOver();
            });
    
            getWorldProperties().<Integer>addListener("hp", (prev, now) -> {
                if (now > PLAYER_HP)
                    set("hp", PLAYER_HP);
    
                if (now <= 0)
                    killPlayer();
            });
    
           if(IS_NO_ENEMIES){
            initEnemySpawns();
           }
            
        }

        protected void initEnemySpawns(){
             getWorldProperties().<Integer>addListener("score", (prev, now) -> {
                getService(HighScoreService.class).updatePlayerScore("Alperen", now , 1);
    
                if (now >= GAME_OVER_SCORE)
                    gameOver();
            });
    
            getWorldProperties().<Integer>addListener("lives", (prev, now) -> {
                if (now == 0)
                    gameOver();
            });
    
            getWorldProperties().<Integer>addListener("hp", (prev, now) -> {
                if (now > PLAYER_HP)
                    set("hp", PLAYER_HP);
    
                if (now <= 0)
                    killPlayer();
            });
    
           
             run(() -> {
                // spawn waves regardless of pressure level
                getService(LevelService.class).spawnLevel();
            }, WAVE_SPAWN_INTERVAL);
    
           
    
    
            run(() -> {
                if (true) {
                    var numToSpawn = Math.min(geti("multiplier") / 25 + 2, 8);
    
                    for (int i = 0; i < numToSpawn; i++) {
                        spawn("Winged Alien");
                    }
                }
            }, WINGEDALIEN_SPAWN_INTERVAL);
    
            run(() -> {
                if (true) {
                    for (int i = 0; i < 4; i++) {
                        var e = spawn("Bomber");
                        EnemyFactory.respawnBomber(e);
                    }
                }
            }, BOMBER_SPAWN_INTERVAL);
    
            run(() -> {
                spawnFadeIn(
                        "Bomb",
                        new SpawnData(FXGLMath.randomPoint(new Rectangle2D(0, 0, getAppWidth() - 80, getAppHeight() - 80))),
                        Duration.seconds(1)
                );
            }, BOMBER_SPAWN_INTERVAL);

            run(() -> {
                spawnFadeIn(
                        "Boss",
                        new SpawnData(FXGLMath.randomPoint(new Rectangle2D(0, 0, getAppWidth() - 80, getAppHeight() - 80))),
                        Duration.seconds(1)
                );
            }, BOSS_SPAWN_INTERVAL);
            run(() -> {
                spawnFadeIn(
                        "AmongUs",
                        new SpawnData(FXGLMath.randomPoint(new Rectangle2D(0, 0, getAppWidth() - 80, getAppHeight() - 80))),
                        Duration.seconds(1)
                );
            }, AMONGUS_SPAWN_INTERVAL);
    
           
        }

        protected void initPhysics(){
            PhysicsWorld physics = getPhysicsWorld();
           
            CollisionHandler bulletEnemy = new CollisionHandler(BULLET, AMONGUS) {
                @Override
                protected void onCollisionBegin(Entity bullet, Entity enemy) {
                    bullet.removeFromWorld();
                    HealthIntComponent hp = enemy.getComponent(HealthIntComponent.class);
                    hp.setValue(hp.getValue() - 1000);
                    killEnemy(enemy);
                    
                }
            };
    
            physics.addCollisionHandler(bulletEnemy);
            physics.addCollisionHandler(bulletEnemy.copyFor(BULLET, WİNGEDALİEN));
            physics.addCollisionHandler(bulletEnemy.copyFor(BULLET, BOMBER));
            physics.addCollisionHandler(bulletEnemy.copyFor(BULLET, BOSS));
    
            
            physics.addCollisionHandler(new BombHandler());
    
           
            
    
        
    
            CollisionHandler playerEnemy = new CollisionHandler(PLAYER, AMONGUS) {
                @Override
                protected void onCollisionBegin(Entity a, Entity b) {
                    
                    getGameScene().getViewport().shakeTranslational(8);
                    if (System.nanoTime() > geti("lastHitTime") + 100000000) {
                        set("lastHitTime", (int)System.nanoTime());
    
                        inc("hp", COLLISION_PENALTY);
    
                        killEnemy(b);
                    }
                }
            };
    
            physics.addCollisionHandler(playerEnemy);
            physics.addCollisionHandler(playerEnemy.copyFor(PLAYER, WİNGEDALİEN));
           

            physics.addCollisionHandler(playerEnemy.copyFor(PLAYER, BOMBER));
            physics.addCollisionHandler(playerEnemy.copyFor(PLAYER, BOMB));
    
           
        }  
    


        protected void killPlayer(){
            byType(WİNGEDALİEN,AMONGUS,BOMBER,BOSS,BULLET).forEach(Entity::removeFromWorld);
            player.setPosition(getAppWidth() / 2, getAppHeight() / 2);
    

        inc("lives", -1);
        set("kills", 0);
        set("secondaryCharge", 0);
        set("multiplier", 1);
        set("hp", PLAYER_HP);
        set("time", 0.0);
        set("weaponType", WeaponType.SINGLE);
        }

        protected void initUI(){
            var pressureText = getUIFactoryService().newText("", Color.WHITE, 24.0);
        pressureText.textProperty().bind(getService(LevelPressureService.class).pressurePropProperty().asString("Pressure: %.2f"));

           var ui = new MainUI();

           addUINode(ui, 30, 50);

        var centerLine = new Line(getAppWidth() / 2.0, 0, getAppWidth() / 2.0, getAppHeight());
        centerLine.setStroke(Color.RED);

        Text goodLuck = getUIFactoryService().newText("Kill enemies to survive!", Color.GREEN, 38);

        addUINode(goodLuck);

        centerText(goodLuck);

        animationBuilder()
                .duration(Duration.seconds(2))
                .autoReverse(true)
                .repeat(2)
                .fadeIn(goodLuck)
                .buildAndPlay();
           
            
            
        
            
        }

        protected void onUpdate(double tpf){

        }

        

        public void killEnemy(Entity enemy) {
            SpawnData data;
    
            if (enemy.isType(BOMBER)) {
                data = new SpawnData(enemy.getCenter()).put("numParticles", 10);
            } else {
                data = new SpawnData(enemy.getCenter()).put("numParticles", 200);
            }
    
            
    
         
    
            
    
            inc("hp", +1);
            inc("secondaryCharge", +1);
    
            addScoreKill(enemy.getCenter());
    
            enemy.removeFromWorld();
        }
        
        private void addScoreKill(Point2D enemyPosition) {
            inc("kills", +1);
    
            if (geti("kills") == 15) {
                set("kills", 0);
                inc("multiplier", +1);
            }
    
            final int multiplier = geti("multiplier");
            int score = 10 * multiplier;
    
            inc("score", score);
    
            Text bonusText = getUIFactoryService().newText(
                    "" + score,
                    Color.color(1, 1, 1, 0.8),
                    Math.max(multiplier / 14, 12)
            );
            bonusText.setStroke(Color.GOLD);
            bonusText.setCache(true);
            bonusText.setCacheHint(CacheHint.SPEED);
    
            var e = entityBuilder()
                    .at(enemyPosition)
                    .view(bonusText)
                    .zIndex(2000)
                    .buildAndAttach();
    
            animationBuilder()
                    .onFinished(() -> e.removeFromWorld())
                    .interpolator(Interpolators.EXPONENTIAL.EASE_OUT())
                    .translate(e)
                    .from(enemyPosition)
                    .to(enemyPosition.subtract(0, 65))
                    .buildAndPlay();
    
            animationBuilder()
                    .duration(Duration.seconds(0.35))
                    .autoReverse(true)
                    .repeat(2)
                    .interpolator(Interpolators.BOUNCE.EASE_IN())
                    .scale(e)
                    .from(new Point2D(1, 1))
                    .to(new Point2D(1.2, 0.85))
                    .buildAndPlay();
        }
    
        private void gameOver() {
            getDialogService().showInputBox("Your score:" + geti("score") + "\nEnter your name", s -> s.matches("[a-zA-Z]*"), name -> {
               // servicee göre
    
            
            });
        }
        
}
