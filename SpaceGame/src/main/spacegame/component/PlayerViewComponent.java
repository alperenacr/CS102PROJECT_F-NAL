package main.spacegame.component;

public class PlayerViewComponent extends ChildViewComponent {
    
    public PlayerViewComponent()      
    {
        super(35, 35, false);

        var playerHealth = new Arc(0, 0, 60, 60, -100, 0);
        
        playerHealth.setStroke(Color.BLACK.brighter());
        playerHealth.setStrokeWidth(3.0);
        playerHealth.setFill(null);
        playerHealth.lengthProperty().bind(getip("hp").multiply(-360.0).divide(PLAYER_HP));
        
        getViewRoot().getChildren().addAll(playerHealth);
     }

}
