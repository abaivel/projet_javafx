package Classes.World.DecorItem.WalkThroughDecorItem;

import Classes.World.DecorItem.NotWalkThroughDecorItem.NotWalkThroughDecorItem;
import Classes.World.Position;
import Classes.World.World;
import javafx.scene.image.ImageView;

//Player looses LP when walking on it : yes the player can be stupid
//Player can jump over it
//Can be exploded by a bomb
public class Trap extends WalkThroughDecorItem {

    //region Constructor
    public Trap(World w, int x, int y, String urlImage) {
        super(w,x, y);
        this.node = new ImageView(urlImage);
        ((ImageView)node).setFitHeight((double) Position.HEIGHT /Position.ROWS);
        ((ImageView)node).setFitWidth((double) Position.WIDTH/Position.COLUMNS);
    }
    //endregion
}