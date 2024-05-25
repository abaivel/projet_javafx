package Classes;

import Classes.World.Position;
import Classes.World.World;
import javafx.scene.Node;

//Everything is a GameObject to be placed on the grid
public abstract class GameObject {

    //region Attributes
    public Position position;
    public World world;
    public Node node;
    //endregion

    //region Constructor
    public GameObject(World w,int x, int y) {
        this.world = w;
        this.position = new Position(x,y);
    }
    //endregion

    //region Getters
    public Position getPosition() {
        return position;
    }
    public Node getNode() {
        return node;
    }
    //endregion

    //region Setters
    public void setPosition(int x, int y) {
        world.moveGameObject(this,x,y);
        this.position.setX(x);
        this.position.setY(y);
    }

    public void setWorld(World world) {
        this.world = world;
    }
    //endregion
}
