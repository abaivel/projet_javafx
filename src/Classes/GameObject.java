package Classes;

import Classes.World.Position;
import Classes.World.World;
import javafx.scene.Node;
import Classes.World.World;
import javafx.scene.layout.GridPane;

public abstract class GameObject {
    public Position position;

    public World world;

    public Node node;

    public GameObject(World w,int x, int y) {
        this.world = w;
        this.position = new Position(x,y);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(int x, int y) {
        this.position.setX(x);
        this.position.setY(y);
        world.moveGameObject(this,x,y);
    }
    public Node getNode() {
        return node;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
