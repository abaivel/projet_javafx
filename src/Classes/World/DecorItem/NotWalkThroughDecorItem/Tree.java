package Classes.World.DecorItem.NotWalkThroughDecorItem;


import Classes.World.Position;
import Classes.World.World;
import javafx.scene.image.ImageView;

public class Tree extends NotWalkThroughDecorItem {
    public Tree(World w, int x, int y, String urlImage) {
        super(w, x, y);
        this.node = new ImageView(urlImage);
        ((ImageView)node).setFitHeight((double) Position.HEIGHT /Position.ROWS);
        ((ImageView)node).setFitWidth((double) Position.WIDTH/Position.COLUMNS);
    }
}
