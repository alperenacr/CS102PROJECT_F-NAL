package main.spacegame.ui;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.view.KeyView;
import com.almasb.fxgl.input.view.MouseButtonView;
import com.almasb.fxgl.logging.Logger;
import com.almasb.fxgl.scene.Scene;
import com.almasb.fxgl.texture.Texture;

import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
//import main.spacegame.service.HighScoreService;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.*;
import static javafx.scene.input.KeyCode.*;

public class SpaceGameMainMenu extends FXGLMenu {


    private VBox scoresRoot = new VBox(10);
    private Node highScores;

    public SpaceGameMainMenu() {
        super(MenuType.MAIN_MENU);
        Image mainImage =getAssetLoader().loadImage(("mainmenu.png"));
      
        

        ImageView myıImageView = new ImageView(mainImage);
       
        myıImageView.setTranslateX(0);
        myıImageView.setTranslateY(0);
        getContentRoot().getChildren().add(myıImageView);
        
        

        var title = getUIFactoryService().newText(getSettings().getTitle(), Color.PURPLE, 65.0);
        title.setStroke(Color.AQUA);
        title.setStrokeWidth(1.5);
        
       

        
        
        
        centerTextBind(title, getAppWidth() / 2.0, 200);

        var version = getUIFactoryService().newText(getSettings().getVersion(), Color.WHITE, 60.0);
        centerTextBind(version, getAppWidth() / 2.0, 220);

        getContentRoot().getChildren().addAll( version);

        var color = Color.DARKRED;

        var blocks = new ArrayList<ColorBlock>();

        var blockStartX = getAppWidth() / 2.0 - 380;

        for (int i = 0; i < 40; i++) {
            var block = new ColorBlock(40, color);
            block.setTranslateX(0+ i*50);
            block.setTranslateY(20);

            blocks.add(block);
            getContentRoot().getChildren().add(block);
        }

        for (int i = 0; i < 40; i++) {
            var block = new ColorBlock(40, color);
            block.setTranslateX(0 + i*50);
            block.setTranslateY(1000);

            blocks.add(block);
            getContentRoot().getChildren().add(block);


            
        }

        for (int i = 0; i < blocks.size(); i++) {
            var block = blocks.get(i);

            animationBuilder()
                    .delay(Duration.seconds(i * 0.05))
                    .duration(Duration.seconds(0.5))
                    .repeatInfinitely()
                    .autoReverse(true)
                    .animate(block.fillProperty())
                    .from(color)
                    .to(color.brighter().brighter())
                    .buildAndPlay(this);
        }

        var menuBox = new VBox(
                2,
                new MenuButton("New Game", () -> fireNewGame()),
               // new MenuButton("HighScore Table"), Buraya Service Oluşturulduktan Sonra High Score Table gelecek
                new MenuButton("Exit", () -> fireExit())
        );
        menuBox.setAlignment(Pos.TOP_CENTER);

        menuBox.setTranslateX(getAppWidth() / 2.0 - 125);
        menuBox.setTranslateY(getAppHeight() / 2.0 + 125);

        
        var centeringLine = new Line(getAppWidth() / 2.0, 0, getAppWidth() / 2.0, getAppHeight());
        centeringLine.setStroke(Color.WHITE);

        scoresRoot.setPadding(new Insets(10));
        scoresRoot.setAlignment(Pos.TOP_LEFT);

        StackPane hsRoot = new StackPane(new Rectangle(450, 250, Color.color(0, 0, 0.2, 0.8)), scoresRoot);
        hsRoot.setAlignment(Pos.TOP_CENTER);
        hsRoot.setCache(true);
        hsRoot.setCacheHint(CacheHint.SPEED);
        hsRoot.setTranslateX(getAppWidth());
        hsRoot.setTranslateY(menuBox.getTranslateY());

        highScores = hsRoot;

        getContentRoot().getChildren().addAll(menuBox, hsRoot);

        
       


        
        
        
       
        
    

    }
}
class MenuButton extends Parent {
    MenuButton(String name, Runnable action) {
        var text = getUIFactoryService().newText(name, Color.PURPLE, 60.0);
        text.setStrokeWidth(1.5);
        text.strokeProperty().bind(text.fillProperty());

        text.fillProperty().bind(
                Bindings.when(hoverProperty())
                        .then(Color.PURPLE)
                        .otherwise(Color.RED)
        );
    
        setOnMouseClicked(e -> action.run());

        setPickOnBounds(true);

        getChildren().add(text);
    }
}
