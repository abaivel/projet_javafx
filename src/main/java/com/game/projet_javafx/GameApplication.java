package com.game.projet_javafx;

import Classes.GameObject;
import Classes.Item.ConsumableItem.Key;
import Classes.Item.ConsumableItem.Potion;
import Classes.Item.Item;
import Classes.Item.NotConsumableItem.Book;
import Classes.Item.NotConsumableItem.Buoy;
import Classes.Item.NotConsumableItem.Trinket;
import Classes.Item.NotConsumableItem.Weapon.Sword;
import Classes.Monster.Slime;
import Classes.NPC.Fouras;
import Classes.NPC.NPC;
import Classes.Player.Player;
import Classes.World.DecorItem.NotWalkThroughDecorItem.Trap;
import Classes.World.DecorItem.NotWalkThroughDecorItem.Tree;
import Classes.World.DecorItem.NotWalkThroughDecorItem.Wall;
import Classes.World.DecorItem.WalkThroughDecorItem.Hedge;
import Classes.World.DecorItem.WalkThroughDecorItem.River;
import Classes.World.World;
import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import Classes.World.World;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class GameApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        /*ArrayList<ArrayList<GameObject>> gridObject = new ArrayList<>();
        gridObject.set(2,new ArrayList<GameObject>())*/
        World world = createWorld1();
        FlowPane flowPane = new FlowPane(Orientation.HORIZONTAL);
        flowPane.setStyle("-fx-background-color: white");
        GridPane pane = world.getPane();
        flowPane.getChildren().add(pane);
        Player p = new Player(world,10,"Truc",25,8,2,5,5);
        world.addToWorld(p);
        FlowPane infosBottom = new FlowPane(Orientation.VERTICAL);
        infosBottom.setHgap(10);
        FlowPane infosPerso = new FlowPane(Orientation.VERTICAL);
        infosBottom.getChildren().add(infosPerso);
        /*ImageView avatar = new ImageView("image_pinguin.png");
        avatar.setFitWidth(70);
        avatar.setFitHeight(70);
        infosPerso.getChildren().add(avatar);*/
        GridPane lifeBar = new GridPane();
        lifeBar.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 3px; -fx-max-height: 20");
        for (int i=0;i<10;i++){
            Rectangle r = new Rectangle();
            r.setHeight(20);
            r.setWidth(15);
            r.setFill(Color.TRANSPARENT);
            lifeBar.add(r,i,0);
        }
        Rectangle lifes = new Rectangle();
        lifes.setHeight(20);
        lifes.setWidth(p.getLP()*15);
        lifes.setFill(Color.GREEN);
        lifeBar.add(lifes, 0,0,10,1);
        infosPerso.getChildren().add(lifeBar);
        GridPane inventory = new GridPane();
        inventory.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 3px;");
        inventory.setHgap(5);
        inventory.setVgap(5);
        for (int i=0;i<5;i++){
            for (int j=0;j<2;j++) {
                Region r = new Region();
                r.setPrefHeight(40);
                r.setPrefWidth(40);
                r.setStyle("-fx-border-color: black;-fx-border-width: 1px;");
                inventory.add(r, i, j);
            }
        }
        infosBottom.getChildren().add(inventory);
        flowPane.getChildren().add(infosBottom);



        Scene scene = new Scene(flowPane);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
        /*scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.RIGHT) {
                if (GridPane.getColumnIndex(p.node)<COLUMNS-1 && IsThereWall(world.gridObjects, GridPane.getColumnIndex(p.node) + 1, GridPane.getRowIndex(p.node))) {
                    GridPane.setColumnIndex(p.node, GridPane.getColumnIndex(p.node) + 1);
                }
            }else if (e.getCode() == KeyCode.LEFT) {
                if (GridPane.getColumnIndex(p.node)>0 && IsThereWall(world.gridObjects, GridPane.getColumnIndex(p.node) - 1, GridPane.getRowIndex(p.node))) {
                    GridPane.setColumnIndex(p.node, GridPane.getColumnIndex(p.node) - 1);
                }
            }else if (e.getCode() == KeyCode.UP) {
                if (GridPane.getRowIndex(p.node)>0 && IsThereWall(world.gridObjects, GridPane.getColumnIndex(p.node), GridPane.getRowIndex(p.node) - 1)) {
                    GridPane.setRowIndex(p.node, GridPane.getRowIndex(p.node) - 1);
                }
            }else if (e.getCode() == KeyCode.DOWN) {
                if (GridPane.getRowIndex(p.node)<ROWS-1 && IsThereWall(world.gridObjects, GridPane.getColumnIndex(p.node), GridPane.getRowIndex(p.node) + 1)) {
                    GridPane.setRowIndex(p.node, GridPane.getRowIndex(p.node) + 1);
                }
            }
        });*/
        //region movement
        final BooleanProperty spacePressed = new SimpleBooleanProperty(false);
        final BooleanProperty rightPressed = new SimpleBooleanProperty(false);
        final BooleanBinding spaceAndRightPressed = spacePressed.and(rightPressed);
        final BooleanProperty a = new SimpleBooleanProperty(true);
        final BooleanProperty b = new SimpleBooleanProperty(true);
        spaceAndRightPressed.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (spacePressed.getValue()) {
                    System.out.println("jump");
                    p.jump(GridPane.getColumnIndex(p.node) + 2, GridPane.getRowIndex(p.node));
                    a.set(false);
                    spacePressed.set(false);
                }
            }

        });

        rightPressed.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (!spacePressed.getValue() && rightPressed.getValue()){
                    System.out.println("avance");
                    p.move(GridPane.getColumnIndex(p.node) + 1, GridPane.getRowIndex(p.node));
                    rightPressed.set(false);
                }else{
                    System.out.println("avance pas");
                }
            }
        });
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.RIGHT) {
                rightPressed.set(true);
                //p.move(GridPane.getColumnIndex(p.node) + 1, GridPane.getRowIndex(p.node));
            }else if (e.getCode() == KeyCode.LEFT) {
                p.move(GridPane.getColumnIndex(p.node) - 1,GridPane.getRowIndex(p.node));
            }else if (e.getCode() == KeyCode.UP) {
                p.move(GridPane.getColumnIndex(p.node),GridPane.getRowIndex(p.node)-1);
            }else if (e.getCode() == KeyCode.DOWN) {
                p.move(GridPane.getColumnIndex(p.node), GridPane.getRowIndex(p.node) + 1);
            }
            if (e.getCode() == KeyCode.SPACE){
                spacePressed.set(true);
            }
        });
        scene.setOnKeyReleased(e->{
            if (e.getCode() == KeyCode.SPACE){
                spacePressed.set(false);
                //a.set(true);
            }else if (e.getCode() == KeyCode.RIGHT){
                rightPressed.set(false);
            }
        });
        //endregion
        p.getLPProperty().addListener(new ChangeListener<Number>() { //listener of the value of life points of the player
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if (p.getLP()<=0){
                    DefeatApplication defeat = new DefeatApplication();
                    try {
                        defeat.start(new Stage());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    stage.close();
                }
                lifes.setWidth(p.getLP()*15);
            }
        });
        p.sizeInventoryProperty().addListener(new ChangeListener<Number>() { //listener of the size of the player's inventory
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if (p.contains("Hedgehog")){
                    stage.close();
                    VictoryApplication victory = new VictoryApplication();
                    try {
                        victory.start(new Stage());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println("CHANGE");
                System.out.println(p.getInventory());
                inventory.getChildren().removeIf(n -> n instanceof ImageView);
                for (int i=0;i<2;i++){
                    for (int j=0;j<5;j++) {
                        int index = i*5+j;
                        if (index<p.sizeInventoryProperty().getValue()) {
                            inventory.add(p.getInventory().get(index).getNode(), j, i);
                        }
                    }
                }
            }
        });
        p.nearByNPCProperty().addListener(new ChangeListener<NPC>() {
            @Override
            public void changed(ObservableValue<? extends NPC> observableValue, NPC npc, NPC t1) {
                System.out.println("I am near a NPC");
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }

    public World createWorld1(){
        World w = new World("#639620");
        Wall wall = new Wall(w,2,2);
        w.addToWorld(wall);
        Sword sword = new Sword(w,"Sword",true,10,10,4,25,"sword.png");
        w.addToWorld(sword);
        Potion potion = new Potion(w,30,2,"Sword",true,"ST+20",10,3,"potion1.png");
        w.addToWorld(potion);
        Buoy buoy = new Buoy(w,28,15,"Sword",true,10,"buoy.png");
        w.addToWorld(buoy);
        Trinket trinket = new Trinket(w,10,6,5,"Hedgehog",true,"hedgehog.png");
        w.addToWorld(trinket);
        Key key = new Key(w,10,15,"Key",true,"#ffffff",12,"key.png");
        w.addToWorld(key);
        Book book = new Book(w,"Book",true,"This is a book",1,1,14,"book.png");
        w.addToWorld(book);
        Fouras fouras1 = new Fouras(w, "Wizard",10,15,2,"fouras.png");
        w.addToWorld(fouras1);
        Slime slime = new Slime(w, "Slime", 5, 2, 1, new ArrayList<>(),25,13,2,"slime.png");
        w.addToWorld(slime);
        Tree tree = new Tree(w,5,17,"tree.png");
        w.addToWorld(tree);
        Hedge hedge = new Hedge(w,12,12);
        w.addToWorld(hedge);
        Trap trap = new Trap(w,15,15,"trap2.png");
        w.addToWorld(trap);
        for (int i=0;i<5;i++){
            River r1 = new River(w,i,14);
            w.addToWorld(r1);
        }
        for (int i=15;i<18;i++){
            River r2 = new River(w,4,i);
            w.addToWorld(r2);
        }
        return w;
    }
}
