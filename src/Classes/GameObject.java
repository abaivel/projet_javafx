package Classes;

import Classes.World.Position;
import javafx.scene.layout.GridPane;

public abstract class GameObject {
    public Position position;
    public GridPane gridPane;

    public GameObject(GridPane g,int x, int y) {
        this.gridPane = g;
        this.position = new Position(x,y);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(int x, int y) {
        this.position.setX(x);
        this.position.setY(y);
    }
}
