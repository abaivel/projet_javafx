package com.game.projet_javafx;

import Classes.GameObject;
import Classes.Player.Player;
import Classes.World.DecorItem.NotWalkThroughDecorItem.Wall;
import Classes.World.World;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import Classes.World.World;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class GameApplication extends Application {
    private static final int HEIGHT = 675;
    private static final int WIDTH = 1500;
    private static final int ROWS = 18;
    private static final int COLUMNS=40;

    @Override
    public void start(Stage stage) throws IOException {
        /*ArrayList<ArrayList<GameObject>> gridObject = new ArrayList<>();
        gridObject.set(2,new ArrayList<GameObject>())*/
        GameObject[][] gridObjects = new GameObject[40][18];
        World world = new World("#444444");
        GridPane pane = world.getPane();
        Wall w = new Wall(world,2,2);
        Player p = new Player(world,10,"Truc",25,8,2,5,5);
        /*Rectangle p.image = new Rectangle((double) WIDTH/COLUMNS, (double) HEIGHT /ROWS);
        p.image.setStyle("-fx-fill: red;");
        pane.add(p.image,5,5);*/
        ArrayList<Rectangle> listWalls = new ArrayList<Rectangle>();
        /*listWalls.add(new Rectangle((double) WIDTH/COLUMNS, (double) HEIGHT /ROWS));
        listWalls.add(new Rectangle((double) WIDTH/COLUMNS, (double) HEIGHT /ROWS));
        pane.add(listWalls.get(0),2,2);
        pane.add(listWalls.get(1),3,2);*/
        Scene scene = new Scene(pane);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.RIGHT) {
                if (GridPane.getColumnIndex(p.image)<COLUMNS-1 && IsThereWall(gridObjects, GridPane.getColumnIndex(p.image) + 1, GridPane.getRowIndex(p.image))) {
                    GridPane.setColumnIndex(p.image, GridPane.getColumnIndex(p.image) + 1);
                }
            }else if (e.getCode() == KeyCode.LEFT) {
                if (GridPane.getColumnIndex(p.image)>0 && IsThereWall(gridObjects, GridPane.getColumnIndex(p.image) - 1, GridPane.getRowIndex(p.image))) {
                    GridPane.setColumnIndex(p.image, GridPane.getColumnIndex(p.image) - 1);
                }
            }else if (e.getCode() == KeyCode.UP) {
                if (GridPane.getRowIndex(p.image)>0 && IsThereWall(gridObjects, GridPane.getColumnIndex(p.image), GridPane.getRowIndex(p.image) - 1)) {
                    GridPane.setRowIndex(p.image, GridPane.getRowIndex(p.image) - 1);
                }
            }else if (e.getCode() == KeyCode.DOWN) {
                if (GridPane.getRowIndex(p.image)<ROWS-1 && IsThereWall(gridObjects, GridPane.getColumnIndex(p.image), GridPane.getRowIndex(p.image) + 1)) {
                    GridPane.setRowIndex(p.image, GridPane.getRowIndex(p.image) + 1);
                }
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }

    public static boolean IsThereWall2(ArrayList<Rectangle> list, int x, int y){
        for (Rectangle rectangle : list) {
            int xRec = GridPane.getColumnIndex(rectangle);
            int yRec = GridPane.getRowIndex(rectangle);
            if (xRec == x && yRec == y) {
                return true;
            }
        }
        return false;
    }

    public static boolean IsThereWall(GameObject[][] grid, int x, int y){
        return !(grid[x][y] instanceof Wall);
    }
}
