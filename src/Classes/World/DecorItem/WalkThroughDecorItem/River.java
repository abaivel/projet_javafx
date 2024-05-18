package Classes.World.DecorItem.WalkThroughDecorItem;


import Classes.World.Position;
import Classes.World.World;
import javafx.scene.shape.Rectangle;

public class River extends WalkThroughDecorItem {
    public River(World w, int x, int y) {
        super(w, x, y);
        this.node=new Rectangle((double) Position.WIDTH/Position.COLUMNS, (double) Position.HEIGHT /Position.ROWS);
        this.node.setStyle("-fx-stroke: #046;-fx-fill: #007dbb");
    }
}
