package Classes.World;

import Classes.GameObject;
import Classes.Item.ConsumableItem.Bomb;
import Classes.Item.Item;
import Classes.Monster.Monster;
import Classes.NPC.NPC;
import Classes.Player.Player;
import Classes.World.DecorItem.NotWalkThroughDecorItem.Trap;
import Classes.World.DecorItem.NotWalkThroughDecorItem.Tree;
import Classes.World.DecorItem.NotWalkThroughDecorItem.Wall;
import Classes.World.DecorItem.WalkThroughDecorItem.Door;
import Classes.World.DecorItem.WalkThroughDecorItem.Hedge;
import Classes.World.DecorItem.WalkThroughDecorItem.River;
import Classes.World.World;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class World {
    //region Attributes
    public ArrayList<GameObject>[][] gridObjects = new ArrayList[40][18];
    String color;
    GridPane pane;
    //endregion

    //region Constants
    private static final int HEIGHT = 675;
    private static final int WIDTH = 1500;
    private static final int ROWS = 18;
    private static final int COLUMNS=40;

    //endregion

    //region Getters
    public GridPane getPane() {
        return pane;
    }
    //endregion

    //region Constructor
    public World(String color) {
        this.color=color;
        pane = new GridPane();
        pane.setPrefHeight(HEIGHT);
        pane.setPrefWidth(WIDTH);
        pane.setStyle("-fx-background-color: "+color+";");
        pane.setVgap(-1);
        pane.setHgap(-1);
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                Rectangle cell = new Rectangle((double) WIDTH/COLUMNS, (double) HEIGHT /ROWS );
                cell.setStyle("-fx-stroke: #507a1a;-fx-fill: none");
                //cell.setViewOrder(-1.0);
                pane.add(cell, col, row);
            }
        }
        for (int i=0;i<40;i++){
            for (int j=0;j<18;j++){
                gridObjects[i][j] = new ArrayList<>();
            }
        }
    }
    //endregion

    //region add, remove, move
    public void addToWorld(GameObject gameObject){
        gridObjects[gameObject.getPosition().getX()][gameObject.getPosition().getY()].add(gameObject);
        pane.add(gameObject.getNode(),gameObject.getPosition().getX(),gameObject.getPosition().getY());
    }

    public void removeFromWorld(GameObject gameObject){
        gridObjects[gameObject.getPosition().getX()][gameObject.getPosition().getY()].remove(gameObject);
        pane.getChildren().remove(gameObject.getNode());
    }

    public void moveGameObject(GameObject gameObject, int x, int y){
        gridObjects[gameObject.getPosition().getX()][gameObject.getPosition().getY()].remove(gameObject);
        gridObjects[x][y].add(gameObject);
        GridPane.setColumnIndex(gameObject.getNode(),x);
        GridPane.setRowIndex(gameObject.getNode(),y);
        if (!pane.getChildren().contains(gameObject.getNode())){
            pane.add(gameObject.getNode(),x,y);
        }
    }
    //endregion

    //region For movement
    public boolean CanGoThere(int x, int y){
        Class<?>[] listClasses = new Class[6];
        for (GameObject g : gridObjects[x][y]){
            if (g instanceof Wall || g instanceof Tree || g instanceof Hedge || g instanceof NPC ||g instanceof Monster || g instanceof Door){
                return false;
            }
        }
        return true;
        //return !(gridObjects[x][y] instanceof Wall || gridObjects[x][y] instanceof Tree || gridObjects[x][y] instanceof Hedge || gridObjects[x][y] instanceof NPC ||gridObjects[x][y] instanceof Monster ||(gridObjects[x][y] instanceof Door && !((Door)gridObjects[x][y]).isOpen()));
    }

    public boolean CanJumpThere(int x_start, int y_start, int x, int y){
        Class<?>[] listClasses = new Class[3];
        listClasses[0]=Hedge.class;
        listClasses[1]=Item.class;
        listClasses[2]=Trap.class;
        return instanceOf(gridObjects[x+(x_start-x)/2][y+(y_start-y)/2],listClasses) || gridObjects[x+(x_start-x)/2][y+(y_start-y)/2].isEmpty();
        /*if (x_start==x){
            return instanceOf(gridObjects[x][y+(y_start-y)/2],listClasses) || gridObjects[x][y+(y_start-y)/2].isEmpty();
        }else{
            return instanceOf(gridObjects[x+(x_start-x)/2][y],listClasses) || gridObjects[x+(x_start-x)/2][y].isEmpty();
        }*/
    }
    //endregion

    //region Item
    public ArrayList<Item> IsThereItem(int x, int y){
        ArrayList<Item> listItems = new ArrayList<>();
        System.out.println("gridObjects[x][y]="+gridObjects[x][y]);
        for (GameObject g : gridObjects[x][y]){
            if (g instanceof Item){
                listItems.add((Item)g);
            }
        }
        System.out.println("listItems="+listItems);
        return listItems;
    }

    public void destroysRadiusBomb(int x, int y){  //for the use of bombs -> destroys decors in a 9 block radius around the bomb and kill the player
        for (int i=x-1;i<=x+1;i++){                 //parcours matrice des 9 cases autour de notre case x,y
            for (int j=y-1;j<=y+1;j++){             //parcours matrice des 9 cases autour de notre case x,y
                if (i>=0 && i<=39 && j>=0 && j<=17) {   //verify if we're in the grid
                    for(int k=0; k<gridObjects[i][j].size();k++){   //parcours des ArrayList de GameObjects pour chaque case i,j, get(k) pour récup le GameObject
                        if(gridObjects[i][j].get(k) instanceof Trap || gridObjects[i][j].get(k) instanceof Hedge || gridObjects[i][j].get(k) instanceof Wall || gridObjects[i][j].get(k) instanceof Tree || gridObjects[i][j].get(k) instanceof Bomb){
                            this.removeFromWorld(gridObjects[i][j].get(k));         //"detroys" -> removes particular decor items from the world if present in the radius of the bomb's explosion
                        }
                        else if(gridObjects[i][j].get(k) instanceof Player player){
                            player.setLP(0);                                        //kills the player if they're in the radius of the bomb's explosion
                        }
                    }
                }

            }
        }
    }
    //endregion

    //region IsThere...
    public boolean IsThereRiver(int x, int y){
        Class<?>[] listClasses = new Class[1];
        listClasses[0]=River.class;
        return instanceOf(gridObjects[x][y], listClasses);
    }

    public boolean IsThereTrap(int x, int y){
        Class<?>[] listClasses = new Class[1];
        listClasses[0]=Trap.class;
        return instanceOf(gridObjects[x][y], listClasses);
    }
    public NPC IsThereNPC(int x, int y){
        Class<?>[] listClasses = new Class[1];
        listClasses[0]=NPC.class;
        for (int i=x-1;i<=x+1;i++){
            for (int j=y-1;j<=y+1;j++){
                if (i>=0 && i<=39 && j>=0 && j<=17) {
                    if (instanceOf(gridObjects[i][j],listClasses)){
                        return (NPC)gridObjects[i][j].get(0);
                    }
                }
            }
        }
        return null;
    }
    public Monster IsThereMonster(int x, int y){
        Class<?>[] listClasses = new Class[1];
        listClasses[0]=Monster.class;
        for (int i=x-1;i<=x+1;i++){
            for (int j=y-1;j<=y+1;j++){
                if (i>=0 && i<=39 && j>=0 && j<=17) {
                    if (instanceOf(gridObjects[i][j],listClasses)){
                        return (Monster) gridObjects[i][j].get(0);
                    }
                }
            }
        }
        return null;
    }

    public int IsThereDoorOpen(int x, int y){
        for (GameObject g : gridObjects[x][y]){                 //parcours les GameObject de la case d'indice x,y de la matrice gridObjects
            if ((g instanceof Door && ((Door)g).isOpen())){     //chaque case de gridObject est une ArrayList de GameObjects
                return (((Door) g).getNextWorld());
            }
        }
        return -1;
    }
    //endregion

    //region InstanceOf function
    public boolean instanceOf(ArrayList<GameObject> grid, Class<?>[] c){
        for (GameObject g : grid){
            for (Class<?> c0 : c) {
                if (g.getClass() == c0 || g.getClass().getSuperclass() == c0 || g.getClass().getSuperclass().getSuperclass() == c0) {
                    return true;
                }
            }
        }
            return false;

    }
    //endregion



}