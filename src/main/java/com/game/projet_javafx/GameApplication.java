package com.game.projet_javafx;

import Classes.GameObject;
import Classes.World.DecorItem.NotWalkThroughDecorItem.Wall;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
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
        GameObject[][] gridObjects = new GameObject[40][20];
        GridPane pane = new GridPane();
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
        Wall w = new Wall(pane,2,2);
        gridObjects[2][2]=w;
        Rectangle perso = new Rectangle((double) WIDTH/COLUMNS, (double) HEIGHT /ROWS);
        perso.setStyle("-fx-fill: red;");
        pane.add(perso,5,5);
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
                if (GridPane.getColumnIndex(perso)<COLUMNS-1 && IsThereWall(gridObjects, GridPane.getColumnIndex(perso) + 1, GridPane.getRowIndex(perso))) {
                    GridPane.setColumnIndex(perso, GridPane.getColumnIndex(perso) + 1);
                }
            }else if (e.getCode() == KeyCode.LEFT) {
                if (GridPane.getColumnIndex(perso)>0 && IsThereWall(gridObjects, GridPane.getColumnIndex(perso) - 1, GridPane.getRowIndex(perso))) {
                    GridPane.setColumnIndex(perso, GridPane.getColumnIndex(perso) - 1);
                }
            }else if (e.getCode() == KeyCode.UP) {
                if (GridPane.getRowIndex(perso)>0 && IsThereWall(gridObjects, GridPane.getColumnIndex(perso), GridPane.getRowIndex(perso) - 1)) {
                    GridPane.setRowIndex(perso, GridPane.getRowIndex(perso) - 1);
                }
            }else if (e.getCode() == KeyCode.DOWN) {
                if (GridPane.getRowIndex(perso)<ROWS-1 && IsThereWall(gridObjects, GridPane.getColumnIndex(perso), GridPane.getRowIndex(perso) + 1)) {
                    GridPane.setRowIndex(perso, GridPane.getRowIndex(perso) + 1);
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
