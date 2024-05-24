package Classes.World.DecorItem.NotWalkThroughDecorItem;


import Classes.World.Position;
import Classes.World.World;
import javafx.scene.image.ImageView;

//Decor that is pretty, who doesn't love trees ?
//Player can't jump over trees
//Can be exploded by a bomb
public class Tree extends NotWalkThroughDecorItem {

    //region Constructor
    public Tree(World w, int x, int y, String urlImage) {
        super(w, x, y);
        this.node = new ImageView(urlImage);
        ((ImageView)node).setFitHeight((double) Position.HEIGHT /Position.ROWS);
        ((ImageView)node).setFitWidth((double) Position.WIDTH/Position.COLUMNS);
    }
    //endregion
}
