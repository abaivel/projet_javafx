package Classes.World.DecorItem.WalkThroughDecorItem;

import Classes.Item.ConsumableItem.Key;
import Classes.World.Position;
import Classes.World.World;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class Door extends WalkThroughDecorItem{

    private Key key;
    private String color;
    private boolean open;
    public Door(World w, int x, int y, String color, String urlImage) {
        super(w,x, y);
        this.color=color;
        open=false;
        this.node = new ImageView(urlImage);
        ((ImageView)node).setFitHeight((double) Position.HEIGHT /Position.ROWS);
        ((ImageView)node).setFitWidth((double) Position.WIDTH/Position.COLUMNS);


    }
    public boolean isOpen() {
        return open;
    }
    public void setOpen(boolean open) {
        this.open = open;
    }
    public Key getKey() {
        return key;
    }

    public String getColor(){return this.color;}


}