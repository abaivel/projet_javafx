package Classes.World;

import Classes.GameObject;
import Classes.InstantUse.Instant;
import Classes.Item.ConsumableItem.Bomb;
import Classes.Item.Item;
import Classes.Monster.Monster;
import Classes.NPC.NPC;
import Classes.Player.Player;
import Classes.World.DecorItem.NotWalkThroughDecorItem.*;
import Classes.World.DecorItem.WalkThroughDecorItem.Trap;
import Classes.World.DecorItem.WalkThroughDecorItem.River;
import com.game.projet_javafx.GameApplication;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

//World contains is a matrix where in each square there's an Array of GameObjects
//We can have multiple GamoObjects on a square exemple : PLayer and river ; PLayer and item (when player discard an object on the floor)
public class World {
    //region Attributes
    private final ArrayList<GameObject>[][] gridObjects = new ArrayList[40][18];
    private final String color;
    private final GridPane pane;

    private final GameApplication game;
    //endregion

    //region Getters
    public GridPane getPane() {
        return pane;
    }
    public GameApplication getGame() {
        return game;
    }
    //endregion

    //region Constructor
    public World(GameApplication game, String color) {
        this.color=color;
        this.game = game;
        pane = new GridPane();
        pane.setPrefHeight(Position.HEIGHT);
        pane.setPrefWidth(Position.WIDTH);
        pane.setStyle("-fx-background-color: "+color+";");
        pane.setVgap(-1);
        pane.setHgap(-1);
        for (int row = 0; row < Position.ROWS; row++) {
            for (int col = 0; col < Position.COLUMNS; col++) {
                Rectangle cell = new Rectangle((double) Position.WIDTH/Position.COLUMNS, (double) Position.HEIGHT /Position.ROWS );
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
        Class<?>[] listClasses = new Class[2];
        listClasses[0] = NotWalkThroughDecorItem.class;
        listClasses[1] = Character.class;
        return instanceOf(gridObjects[x][y],listClasses)==null;
    }

    public boolean CanJumpThere(int x_start, int y_start, int x, int y){
        Class<?>[] listClasses = new Class[3];
        listClasses[0]=Hedge.class;
        listClasses[1]=Item.class;
        listClasses[2]=Trap.class;
        return instanceOf(gridObjects[x+(x_start-x)/2][y+(y_start-y)/2],listClasses)!=null || gridObjects[x+(x_start-x)/2][y+(y_start-y)/2].isEmpty();
    }
    //endregion

    //region Item : IsThereItem ; destroysRadiusBomb
    public ArrayList<Item> IsThereItem(int x, int y){
        ArrayList<Item> listItems = new ArrayList<>();
        for (GameObject g : gridObjects[x][y]){
            if (g instanceof Item){
                listItems.add((Item)g);
            }
        }
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
                            player.setLifePoints(0);                                        //kills the player if they're in the radius of the bomb's explosion
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
        return instanceOf(gridObjects[x][y], listClasses)!=null;
    }

    public boolean IsThereTrap(int x, int y){
        Class<?>[] listClasses = new Class[1];
        listClasses[0]=Trap.class;
        return instanceOf(gridObjects[x][y], listClasses)!=null;
    }
    public NPC IsThereNPC(int x, int y){
        Class<?>[] listClasses = new Class[1];
        listClasses[0]=NPC.class;
        for (int i=x-1;i<=x+1;i++){
            for (int j=y-1;j<=y+1;j++){
                if (i>=0 && i<=39 && j>=0 && j<=17) {
                    NPC npc = (NPC) instanceOf(gridObjects[i][j],listClasses);
                    if (npc!=null){
                        return npc;
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
                    Monster monster = (Monster) instanceOf(gridObjects[i][j],listClasses);
                    if (monster!=null){
                        return monster;
                    }
                }
            }
        }
        return null;
    }

    /*public int IsThereDoorOpen(int x, int y){
        for (GameObject g : gridObjects[x][y]){                 //parcours les GameObject de la case d'indice x,y de la matrice gridObjects
            if ((g instanceof Door && ((Door)g).isOpen())){     //chaque case de gridObject est une ArrayList de GameObjects
                return (((Door) g).getNextWorld());
            }
        }
        return -1;
    }*/

    public int IsThereDoorOpen(int x, int y){
        Class<?>[] listClasses = new Class[1];
        listClasses[0]=Door.class;
        Door door = (Door) instanceOf(gridObjects[x][y],listClasses);
        if (door!=null && door.isOpen()){
            return door.getNextWorld();
        }
        return -1;
    }

    public ArrayList<Instant> IsThereInstant(int x, int y){
        ArrayList<Instant> listInstants = new ArrayList<>();
        for (GameObject g : gridObjects[x][y]){
            if (g instanceof Instant){
                listInstants.add((Instant)g);
            }
        }
        return listInstants;
    }
    //endregion

    //region InstanceOf function
    public GameObject instanceOf(ArrayList<GameObject> grid, Class<?>[] c){
        for (GameObject g : grid){
            for (Class<?> c0 : c) {
                if (g.getClass() == c0 || g.getClass().getSuperclass() == c0 || g.getClass().getSuperclass().getSuperclass() == c0) {
                    return g;
                }
            }
        }
        return null;

    }
    //endregion



}