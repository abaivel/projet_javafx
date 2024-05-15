package Classes.World.DecorItem.NotWalkThroughDecorItem;

import Classes.World.World;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Wall extends NotWalkThroughDecorItem{
    private static final int HEIGHT = 675;
    private static final int WIDTH = 1500;
    private static final int ROWS = 18;
    private static final int COLUMNS=40;
    public Wall(World w, int x, int y) {
        super(w,x, y);
        /*Rectangle r = new Rectangle((double) WIDTH/COLUMNS, (double) HEIGHT /ROWS);
        g.add(r,x,y);*/
    }
}