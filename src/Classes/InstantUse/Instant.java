package Classes.InstantUse;

import Classes.GameObject;
import Classes.World.Position;
import Classes.World.World;
import javafx.scene.image.ImageView;

//TODO : front + image ?
public abstract class Instant extends GameObject {
    //region Attributes
    private int value;
    //endregion

    //region Constructor
    public Instant(World w, int x, int y, int value, String urlImage) {
        super(w, x, y);
        this.value = value;
        this.node = new ImageView(urlImage);
        ((ImageView)node).setFitHeight((double) Position.HEIGHT /Position.ROWS);
        ((ImageView)node).setFitWidth((double) Position.WIDTH/Position.COLUMNS);
    }
    //endregion

    //region Getters
    public int getValue() {return value;}
    //endregion

    //region Setters
    public void setValue(int value) {this.value = value;}
    //endregion
}
