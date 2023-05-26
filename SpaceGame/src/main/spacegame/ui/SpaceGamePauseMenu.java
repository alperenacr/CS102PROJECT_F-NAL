package main.spacegame.ui;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.EventBuilder;
import com.almasb.fxgl.dsl.EventBuilderKt;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.view.KeyView;
import com.almasb.fxgl.input.view.MouseButtonView;
import com.almasb.fxgl.logging.Logger;
import com.almasb.fxgl.scene.Scene;
import com.almasb.fxgl.texture.Texture;

import javafx.beans.binding.Bindings;
import javafx.event.Event;
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

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.Action;

import static com.almasb.fxgl.dsl.FXGL.*;
import static javafx.scene.input.KeyCode.*;

public class SpaceGamePauseMenu extends FXGLMenu {

    Image creditImage =getAssetLoader().loadImage(("credits.png"));
    ImageView myıImageView2 = new ImageView(creditImage);
    MenuButton closeMenuButton = new MenuButton("Close Credits",() -> closeCredits());
   
    public SpaceGamePauseMenu(){
        super(MenuType.GAME_MENU);


        Image mainImage =getAssetLoader().loadImage(("gameMenu.png"));
      
        

        ImageView myıImageView = new ImageView(mainImage);
       
        myıImageView.setTranslateX(0);
        myıImageView.setTranslateY(0);
        getContentRoot().getChildren().add(myıImageView);


        var title = getUIFactoryService().newText("PAUSED", Color.RED, 90.0);
        title.setStroke(Color.AQUA);
        title.setStrokeWidth(4.0);

        centerTextBind(title, getAppWidth() / 2.0, 600);
        getContentRoot().getChildren().add(title);

        var contunieText = getUIFactoryService().newText("TO CONTUNİE PLEASE PRESS ESC", Color.GOLD,45.0);

        contunieText.setStroke(Color.WHITESMOKE);
        contunieText.setStrokeWidth(2.0);
        centerTextBind(contunieText, 380, 200);

        getContentRoot().getChildren().add(contunieText);

        
      
        
        
        
       
      
        
       

        var menuBox = new VBox(
            2,
            new MenuButton("New Game", () -> fireNewGame()),
            new MenuButton("Credits",() -> showCredits()),

            new MenuButton("Exit", () -> fireExit())
    );
    menuBox.setAlignment(Pos.TOP_CENTER);

    menuBox.setTranslateX(getAppWidth() / 2.0 - 160);
    menuBox.setTranslateY(getAppHeight() / 2.0 + 125);
    
    getContentRoot().getChildren().add(menuBox);

    closeMenuButton.setTranslateX(800);
    closeMenuButton.setTranslateY(1000);
    }

    public void showCredits(){
        
        getContentRoot().getChildren().add( myıImageView2);
        myıImageView2.setTranslateX(0);
        myıImageView2.setTranslateY(0);

        getContentRoot().getChildren().add( closeMenuButton);
        
    }

    public void closeCredits() {
        getContentRoot().getChildren().removeAll(myıImageView2, closeMenuButton);
        
    }

    // commit deneme yoksa deli olcam
    
}
