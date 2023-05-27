package main.spacegame.factory;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.*;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.particle.ParticleEmitters;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import com.almasb.fxgl.texture.ImagesKt;
import com.almasb.fxgl.texture.Pixel;
import com.almasb.fxgl.texture.Texture;

import main.spacegame.SpaceGameApp;
import main.spacegame.component.*;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.CacheHint;
import javafx.scene.effect.Bloom;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.stream.Collectors;

import static com.almasb.fxgl.dsl.FXGL.*;
import static main.spacegame.Config.*;
import static main.spacegame.SpaceGameType.*;

public class SpaceGameFactory implements EntityFactory {

    private static final Texture BOMB_TEXTURE = texture("Bomb.png", 15, 15);
    private static final Texture BULLET_TEXTURE = texture("Bullet.png");

    @Spawns("Background")
    public Entity spawnBackground(SpawnData data) {
        return entityBuilder(data)
                .type(GRID)
                .with(IS_BACKGROUND ? new BackgroundMapComponent() : new CollidableComponent(false))
                .with(new GridComponent())
                .zIndex(BACKGROUND_Z_INDEX)
                .build();
    }

    @Spawns("ParticleLayer")
    public Entity spawnParticleLayer(SpawnData data) {
        return entityBuilder(data)
                .type(PARTICLE_LAYER)
                .with("Particle", new ParticleCanvasComponent()) // lookAfter
                .zIndex(PARTICLES_Z_INDEX)
                .build();
    }

    @Spawns("Player")
    public Entity spawnPlayer(SpawnData data) {
        var texture = texture("PlayerNew.png", 80, 80);

        var darkerTexture = texture.darker().darker().darker();

        var e = entityBuilder()
                .type(PLAYER)
                .at(getAppWidth() / 2.0 - texture.getWidth() / 2, getAppHeight() / 2.0 - texture.getHeight() / 2)
                .viewWithBBox(texture)
                .collidable()
                .zIndex(1000)
                .with("Player", new PlayerComponent(PLAYER_SPEED, FXGL.<SpaceGameApp>getAppCast().getPlayer()))  // write getPlayer() method
                .with("PlayerView", new PlayerViewComponent())
                .with(new EffectComponent())
                .with(new KeepInBoundsComponent(new Rectangle2D(0, 0, getAppWidth(), getAppHeight())))
                .zIndex(PLAYER_Z_INDEX)
                .build();

        onIntChangeTo("lives", 0, () -> {
            e.getViewComponent().addChild(darkerTexture);
        });

        return e;
    }

    @Spawns("Bullet")
    public Entity spawnBullet(SpawnData data) {
        // bullet texture is 54x13, hence 6.5

        var bulletTexture = texture("Bullet.png");
        int bulletX = (int) bulletTexture.getWidth(); // bulletX is long side of bullet
        int bulletY = (int) bulletTexture.getHeight(); // bulletX is short side of bullet

        var expireClean = new ExpireCleanComponent(Duration.seconds(0.5)).animateOpacity();
        expireClean.pause();

        var t = ImagesKt.fromPixels(bulletX, bulletY,
                bulletTexture
                        .pixels()
                        .stream()
                        .map(p -> {

                            double alphaMod = p.getX() / bulletTexture.getWidth(); // denominator should be double

                            return new Pixel(p.getX(), p.getY(),
                                    Color.color(p.getR(), p.getG(), p.getB(), p.getA() * alphaMod), p.getParent());
                        })
                        .collect(Collectors.toList()));

        var e = entityBuilder(data)
                .at(data.getX(), data.getY() - 6.5)  //change 6.5
                .type(BULLET)
                .viewWithBBox(new Texture(t))
                .with(new CollidableComponent(true))
                .with(new ProjectileComponent(data.get("direction"), BULLET_MOVE_SPEED))
                .with("Bullet", new BulletComponent())
                .with(expireClean)
                .zIndex(BULLET_Z_INDEX)
                .rotationOrigin(0, 6.5)    // change 6.5
                .build();

        // creating entities can be expensive on mobile, so pool bullets
        e.setReusable(true);

        return e;
    }

    // this allows to "reset" the bullet after it is returned from the pool
    public static void respawnBullet(Entity entity, SpawnData data) {
        play("shoot" + (int) (Math.random() * 4 + 1) + ".wav");       //for bulletAudio

        entity.setPosition(data.getX(), data.getY() - 6.5);   // change 6.5
        entity.setOpacity(1);
        entity.setVisible(true);

        //entity.removeComponent(RicochetComponent.class);  // for bouncing
        entity.removeComponent(ExpireCleanComponent.class);

        var expireClean = new ExpireCleanComponent(Duration.seconds(0.5)).animateOpacity();
        expireClean.pause();

        entity.addComponent(expireClean);

        Point2D dir = data.get("direction");

        entity.getComponent(ProjectileComponent.class).setDirection(dir);
    }

    @Spawns("Bomb")
    public Entity spawnBomb(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setFixtureDef(new FixtureDef().density(0.05f));
        physics.setBodyType(BodyType.DYNAMIC);

        physics.setOnPhysicsInitialized(() -> {
            Point2D playerPosition = FXGL.<SpaceGameApp>getAppCast().getPlayer().getPosition();   // should be written getPlayer() and getPosition() methods
            //Point2D mousePosition = FXGL.getInput().getMousePositionWorld();

            physics.setLinearVelocity(playerPosition.subtract(data.getX(), data.getY()).normalize().multiply(800));
        });


        var e = entityBuilder(data)
                .type(BOMB)
                .view(BOMB_TEXTURE)
                .collidable()
                .with(physics)
                .with(new ExpireCleanComponent(Duration.seconds(4)))
                .build();

        e.setReusable(true);

        return e;
    }


    @Spawns("Explosion")
    public Entity spawnExplosion(SpawnData data) {
        play("explosion.wav");

        var texture = texture("Explosion.png", 80 * 16, 80).toAnimatedTexture(16, Duration.seconds(0.5));


        var e = entityBuilder()
                .at(data.getX() - 40, data.getY() - 40)
                // we want a smaller texture, 80x80
                // it has 16 frames, hence 80 * 16
                .view(texture.loop())
                .build();

        texture.setOnCycleFinished(() -> e.removeFromWorld());

        return e;
    }
    
/* 
    public static void respawnExplosion(Entity entity, SpawnData data) {
        entity.setPosition(data.getX() - 40, data.getY() - 40);

        int numParticles = data.hasKey("numParticles") ? data.get("numParticles") : 200;

        //play("explosion-0" + (int) (Math.random() * 8 + 1) + ".wav");     //for audio

        entity.getComponent(ExplosionParticleComponent.class).setNumParticles(numParticles);       // gives error
    }
*/

    @Spawns("Shockwave")
    public Entity spawnShockwave(SpawnData data) {
        var view = new Rectangle(40, 40, null);
        view.setStrokeWidth(2);
        view.setStroke(Color.GOLD);
        view.setCache(true);
        view.setCacheHint(CacheHint.SCALE);

        var e = entityBuilder()
                .at(data.getX() - 40, data.getY() - 40)
                .type(SHOCKWAVE)
                .viewWithBBox(view)
                .collidable()
                .build();

        animationBuilder()
                .interpolator(Interpolators.EXPONENTIAL.EASE_OUT())
                .scale(e)
                .from(new Point2D(1, 1))
                .to(new Point2D(15, 15))
                .buildAndPlay();

        animationBuilder()
                .onFinished(() -> e.removeFromWorld())
                .fadeOut(e)
                .buildAndPlay();

        return e;
    }


}
