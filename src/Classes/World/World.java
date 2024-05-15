package Classes.World;

import Classes.GameObject;
import Classes.World.World;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

public class World {
    GameObject[][] gridObjects = new GameObject[40][18];
    String color;
    GridPane pane;
    private static final int HEIGHT = 675;
    private static final int WIDTH = 1500;
    private static final int ROWS = 18;
    private static final int COLUMNS=40;
    public GridPane getPane() {
        return pane;
    }

    public World(String color) {
        this.color=color;
        pane = new GridPane();
        pane.setPrefHeight(HEIGHT);
        pane.setPrefWidth(WIDTH);
        pane.setStyle("-fx-background-color: white;");
        pane.setVgap(-1);
        pane.setHgap(-1);
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                Rectangle cell = new Rectangle((double) WIDTH/COLUMNS, (double) HEIGHT /ROWS );
                cell.setStyle("-fx-stroke: black;-fx-fill: green;");
                pane.add(cell, col, row);
            }
        }
    }
    public void addToWorld(GameObject gameObject){
        gridObjects[gameObject.getPosition().getX()][gameObject.getPosition().getY()] = gameObject;
        pane.add(gameObject.getNode(),gameObject.getPosition().getX(),gameObject.getPosition().getY());
    }
    public void removeFromWorld(GameObject gameObject){
        gridObjects[gameObject.getPosition().getX()][gameObject.getPosition().getY()] = null;
        pane.getChildren().remove(gameObject.getNode());
    }
    public void moveGameObject(GameObject gameObject, int x, int y){
        gridObjects[gameObject.getPosition().getX()][gameObject.getPosition().getY()] = null;
        gridObjects[x][y]=gameObject;
        gameObject.setPosition(x,y);
        GridPane.setColumnIndex(gameObject.getNode(),x);
        GridPane.setRowIndex(gameObject.getNode(),y);
    }
}