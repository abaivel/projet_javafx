package Classes.InstantUse;

import Classes.GameObject;
import Classes.World.World;

//TODO : front + image ?
public abstract class Instant extends GameObject {
    //region Attributes
    private int value;
    //endregion

    //region Constructor
    public Instant(World w, int x, int y, int value) {
        super(w, x, y);
        this.value = value;
    }
    //endregion

    //region Getters
    public int getValue() {return value;}
    //endregion

    //region Setters
    public void setValue(int value) {this.value = value;}
    //endregion
}
