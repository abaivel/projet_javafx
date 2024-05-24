package Classes.World.DecorItem.NotWalkThroughDecorItem;

import Classes.World.Position;
import Classes.World.World;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

//Decor that
//Player can't jump over it
//Can be exploded by a bomb
public class Wall extends NotWalkThroughDecorItem{

    //region Constructor
    public Wall(World w, int x, int y) {
        super(w,x, y);
        node=new Rectangle((double) Position.WIDTH/Position.COLUMNS, (double) Position.HEIGHT /Position.ROWS);
        this.node.setStyle("-fx-stroke: #24180a;-fx-fill: #744e20");
    }
    //endregion
}