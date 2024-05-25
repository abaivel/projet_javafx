package Classes.World.DecorItem.WalkThroughDecorItem;

import Classes.World.Position;
import Classes.World.World;
import javafx.scene.image.ImageView;

//Doors have 2 states :
//  - closed -> the player can't walk through but can open them if they have the corresponding key in their inventory
//  - opened -> if player walk through, they'll be teleported to the next world
public class Door extends WalkThroughDecorItem{

    //region Attributes
    private final String color;
    private boolean open;
    private int nextWorld;
    //endregion

    //region Constructor
    public Door(World w, int x, int y, String color, String urlImage, int nextWorld) {
        super(w,x, y);
        this.color=color;
        open=false;
        this.node = new ImageView(urlImage);
        this.nextWorld = nextWorld;
        ((ImageView)node).setFitHeight((double) Position.HEIGHT /Position.ROWS);
        ((ImageView)node).setFitWidth((double) Position.WIDTH/Position.COLUMNS);
    }
    //endregion

    //region Getters
    public String getColor(){return this.color;}
    public int getNextWorld(){return nextWorld;}
    public boolean isOpen() {return open;}
    //endregion

    //region Setters
    public void setOpen(boolean open) {this.open = open;}
    public void setNextWorld(int nextWorld){this.nextWorld = nextWorld;}
    //endregion
}