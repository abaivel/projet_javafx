package com.game.projet_javafx;

import Classes.GameObject;
import Classes.Item.ConsumableItem.Key;
import Classes.Item.ConsumableItem.Potion;
import Classes.Item.Item;
import Classes.Item.NotConsumableItem.Book;
import Classes.Item.NotConsumableItem.Buoy;
import Classes.Item.NotConsumableItem.Trinket;
import Classes.Item.NotConsumableItem.Weapon.Sword;
import Classes.Monster.Looter;
import Classes.Monster.Monster;
import Classes.Monster.Slime;
import Classes.Monster.Wolf;
import Classes.NPC.Fouras;
import Classes.NPC.Merchant;
import Classes.NPC.NPC;
import Classes.NPC.UselessPerson;
import Classes.Player.Player;
import Classes.World.DecorItem.NotWalkThroughDecorItem.Trap;
import Classes.World.DecorItem.NotWalkThroughDecorItem.Tree;
import Classes.World.DecorItem.NotWalkThroughDecorItem.Wall;
import Classes.World.DecorItem.WalkThroughDecorItem.Door;
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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import Classes.World.World;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class GameApplication extends Application {
    private Player p;
    private World world;
    GridPane pane;
    FlowPane flowPane;
    FlowPane infosBottom;

    @Override
    public void start(Stage stage) throws IOException {
        /*ArrayList<ArrayList<GameObject>> gridObject = new ArrayList<>();
        gridObject.set(2,new ArrayList<GameObject>())*/
        World world1 = createWorld1();
        World world2 = createWorld2();
        World world3 = createWorld3();

        World[] listWorld = new World[3];
        listWorld[0] = world1;
        listWorld[1] = world2;
        listWorld[2] = world3;
        world=listWorld[0];
        flowPane = new FlowPane(Orientation.HORIZONTAL);
        flowPane.setStyle("-fx-background-color: white");
        pane = world.getPane();
        flowPane.getChildren().add(pane);
        p = new Player(world,10,"Truc",25,4,2,5,5);
        world.addToWorld(p);
        infosBottom = new FlowPane(Orientation.VERTICAL);
        infosBottom.setHgap(10);
        FlowPane infosPerso = new FlowPane(Orientation.VERTICAL);
        infosBottom.getChildren().add(infosPerso);
        /*ImageView avatar = new ImageView("image_pinguin.png");
        avatar.setFitWidth(70);
        avatar.setFitHeight(70);
        infosPerso.getChildren().add(avatar);*/

        //TODO : create seperate functions to make code more clear ?
        //PLayer's lifebar
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

        //Player's inventory
        GridPane inventory = new GridPane();
        inventory.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 3px;");
        inventory.setHgap(5);
        inventory.setVgap(5);
        for (int i=0;i<2;i++){
            for (int j=0;j<5;j++) {
                Region r = new Region();
                r.setPrefHeight(40);
                r.setPrefWidth(40);
                r.setStyle("-fx-border-color: black;-fx-border-width: 1px;");
                inventory.add(r, j, i);
                int index = i*5+j;
                if (index<p.sizeInventoryProperty().getValue()) {
                    inventory.add(p.getInventory().get(index).getNode(), j, i);
                }
            }
        }

        //Player's money
        Label moneyLabel = new Label();                                     //creating the label for the front
        moneyLabel.textProperty().bind(p.getMoneyProperty().asString());    //binding
        Pane money = new Pane(moneyLabel);                                  //pane creation

        //Filling infosBottom FlowPane
        infosBottom.getChildren().add(inventory);
        infosBottom.getChildren().add(money);
        flowPane.getChildren().add(infosBottom);

        /*File file = new File("src\\main\\resources\\game_music.mp3");
        System.out.println("here");
        final String MEDIA_URL = file.toURI().toString();
        System.out.println("here");
        Media media = new Media(MEDIA_URL);
        System.out.println("here");
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        System.out.println("here");
        mediaPlayer.volumeProperty().set(0.1);
        //mediaPlayer.setStopTime(Duration.seconds(212));
        mediaPlayer.play();*/

        //Scene creation
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
        final BooleanProperty leftPressed = new SimpleBooleanProperty(false);
        final BooleanProperty upPressed = new SimpleBooleanProperty(false);
        final BooleanProperty downPressed = new SimpleBooleanProperty(false);
        final BooleanProperty jumpEffectued = new SimpleBooleanProperty(false);
        final BooleanBinding spaceAndRightPressed = spacePressed.and(rightPressed);
        final BooleanBinding spaceAndLeftPressed = spacePressed.and(leftPressed);
        final BooleanBinding spaceAndUpPressed = spacePressed.and(upPressed);
        final BooleanBinding spaceAndDownPressed = spacePressed.and(downPressed);
        spaceAndRightPressed.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (spacePressed.getValue()) {
                    p.jump(GridPane.getColumnIndex(p.node) + 2, GridPane.getRowIndex(p.node));
                    spacePressed.set(false);
                    jumpEffectued.set(true);
                }
            }
        });
        spaceAndLeftPressed.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (spacePressed.getValue()) {
                    p.jump(GridPane.getColumnIndex(p.node) - 2, GridPane.getRowIndex(p.node));
                    spacePressed.set(false);
                    jumpEffectued.set(true);
                }
            }
        });

        spaceAndUpPressed.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (spacePressed.getValue()) {
                    p.jump(GridPane.getColumnIndex(p.node), GridPane.getRowIndex(p.node)-2);
                    spacePressed.set(false);
                    jumpEffectued.set(true);
                }
            }
        });

        spaceAndDownPressed.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (spacePressed.getValue()) {
                    p.jump(GridPane.getColumnIndex(p.node), GridPane.getRowIndex(p.node)+2);
                    spacePressed.set(false);
                    jumpEffectued.set(true);
                }
            }
        });

        rightPressed.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (!jumpEffectued.getValue() && rightPressed.getValue()){
                    p.move(GridPane.getColumnIndex(p.node) + 1, GridPane.getRowIndex(p.node));
                    rightPressed.set(false);
                }
            }
        });

        leftPressed.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (!jumpEffectued.getValue() && leftPressed.getValue()){
                    p.move(GridPane.getColumnIndex(p.node) - 1, GridPane.getRowIndex(p.node));
                    leftPressed.set(false);
                }
            }
        });

        upPressed.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (!jumpEffectued.getValue() && upPressed.getValue()){
                    p.move(GridPane.getColumnIndex(p.node), GridPane.getRowIndex(p.node)-1);
                    upPressed.set(false);
                }
            }
        });

        downPressed.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (!jumpEffectued.getValue() && downPressed.getValue()){
                    p.move(GridPane.getColumnIndex(p.node), GridPane.getRowIndex(p.node)+1);
                    downPressed.set(false);
                }
            }
        });


        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.RIGHT) {
                rightPressed.set(true);
                //p.move(GridPane.getColumnIndex(p.node) + 1, GridPane.getRowIndex(p.node));
            }else if (e.getCode() == KeyCode.LEFT) {
                leftPressed.set(true);
                //p.move(GridPane.getColumnIndex(p.node) - 1,GridPane.getRowIndex(p.node));
            }else if (e.getCode() == KeyCode.UP) {
                upPressed.set(true);
                //p.move(GridPane.getColumnIndex(p.node),GridPane.getRowIndex(p.node)-1);
            }else if (e.getCode() == KeyCode.DOWN) {
                downPressed.set(true);
                //p.move(GridPane.getColumnIndex(p.node), GridPane.getRowIndex(p.node) + 1);
            }
            if (e.getCode() == KeyCode.SPACE){
                spacePressed.set(true);
            }
        });
        scene.setOnKeyReleased(e->{
            if (e.getCode() == KeyCode.SPACE){
                spacePressed.set(false);
                jumpEffectued.set(false);
            }else if (e.getCode() == KeyCode.RIGHT){
                rightPressed.set(false);
            }else if (e.getCode() == KeyCode.LEFT){
                leftPressed.set(false);
            }else if (e.getCode() == KeyCode.UP){
                upPressed.set(false);
            }else if (e.getCode() == KeyCode.DOWN){
                downPressed.set(false);
            }
        });
        //endregion

        //region Player's attributes listeners
        p.getLPProperty().addListener(new ChangeListener<Number>() { //listener of the value of life points of the player
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {  //function called when Player's LP change
                if (p.getLP()<=0){                                                                              //verification failure condition
                    DefeatApplication defeat = new DefeatApplication();
                    try {
                        defeat.start(new Stage());                                                              //launch defeat stage
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    stage.close();
                }                                                                                               //15 is the size of one "life"
                lifes.setWidth(p.getLP()*15);                                                                   //for the front resizes life bar with LP left
            }
        });

        p.sizeInventoryProperty().addListener(new ChangeListener<Number>() { //listener of the size of the player's inventory
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {      //function called when the size of the inventory changes
                if (p.contains("Hedgehog")){                                                                        //verification of win condition
                    stage.close();
                    VictoryApplication victory = new VictoryApplication();
                    try {
                        victory.start(new Stage());                                                                 //launch victory stage
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println("CHANGE");
                System.out.println(p.getInventory());
                inventory.getChildren().removeIf(n -> n instanceof ImageView);              //removes everything from the gridpane
                for (int i=0;i<2;i++){
                    for (int j=0;j<5;j++) {
                        int index = i*5+j;
                        if (index<p.sizeInventoryProperty().getValue()) {
                            inventory.add(p.getInventory().get(index).getNode(), j, i);     //add items one by one to the gridpane
                        }
                    }
                }
            }
        });

        p.getMoneyProperty().addListener(new ChangeListener<Number>() { //listener of the value of money of the player
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                System.out.println(p.getMoneyProperty());

            }
        });

        p.getIndexWorldProperty().addListener(new ChangeListener<Number>() { //listener of the value of money of the player
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                world.removeFromWorld(p);
                for (Item item : p.getInventory()){
                    world.removeFromWorld(item);
                }
                world = listWorld[p.getIndexWorld()];
                flowPane.getChildren().clear();
                pane = world.getPane();
                flowPane.getChildren().add(pane);
                flowPane.getChildren().add(infosBottom);
                p.setWorld(world);
                world.addToWorld(p);
                for (Item item : p.getInventory()){
                    item.setWorld(world);
                }

            }
        });
        //endregion


        p.nearByNPCProperty().addListener(new ChangeListener<NPC>() {
            @Override
            public void changed(ObservableValue<? extends NPC> observableValue, NPC npc, NPC t1) {
                System.out.println("I am near a NPC");
                NPC npcDiag = p.getNearByNPC();
                if(npcDiag instanceof Merchant merchantDiag){                                               //If near a merchant
                    DialogMerchantApplication diagApp = new DialogMerchantApplication(merchantDiag,p);
                    try {
                        diagApp.start(new Stage());                                                         //starts dialog
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }else if(npcDiag instanceof Fouras fourasDiag){                                             //If near a Fouras
                    DialogFourasApplication diagFouApp = new DialogFourasApplication(fourasDiag,p);
                    try {
                        diagFouApp.start(new Stage());                                                      //starts dialog
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }else if(npcDiag instanceof UselessPerson upDiag){                                          //If near a UselessPerson
                    DialogUselessApplication diagUseless = new DialogUselessApplication(upDiag,p);
                    try {
                        diagUseless.start(new Stage());                                                     //starts dialog
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        p.nearByMonsterProperty().addListener(new ChangeListener<Monster>() {
            @Override
            public void changed(ObservableValue<? extends Monster> observableValue, Monster monster, Monster t1) {
                System.out.println("listener");
                if (p.getNearByMonster()!=null) {
                    FightApplication fight = new FightApplication(p,t1, stage);
                    try {
                        fight.start(new Stage());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    if (p.getNearByMonster().getLifePoints()<=0){
                        world.removeFromWorld(p.getNearByMonster());
                        p.getNearByMonster().dropItem();
                        p.setNearByMonster(null);
                    }
                    System.out.println(p.getInventory());
                }
            }
        });
    }

    //TODO : build 2 other worlds and do portals between them
    //region World Creation
    public World createWorld1(){
        World w = new World("#639620");
        Wall wall = new Wall(w,2,2);
        w.addToWorld(wall);
        Sword sword = new Sword(w,"Sword",true,10,10,4,25,"sword.png");
        w.addToWorld(sword);
        Potion potion = new Potion(w,30,2,"Potion de vie",true,"ST+20",10,3,"potion1.png");
        w.addToWorld(potion);
        Buoy buoy = new Buoy(w,28,15,"Sword",true,10,"buoy.png");
        w.addToWorld(buoy);
        Trinket trinket = new Trinket(w,10,20,15,"Hedgehog",true,"hedgehog.png");
        w.addToWorld(trinket);
        Key key = new Key(w,10,15,"Key",true,"BLUE",12,"key.png");
        //w.addToWorld(key);
        Book book = new Book(w,"Book",true,"This is a book",1,1,14,"book.png");
        w.addToWorld(book);
        Fouras fouras1 = new Fouras(w, "Wizard",10,15,2,"fouras.png");
        w.addToWorld(fouras1);
        Slime slime = new Slime(w, "Slime", 5, 3, 2, new ArrayList<>(),7,5,0,"slime.png");
        slime.addToInventory(key);
        w.addToWorld(slime);
        Tree tree = new Tree(w,5,17,"tree.png");
        w.addToWorld(tree);
        Hedge hedge = new Hedge(w,12,12);
        w.addToWorld(hedge);
        Trap trap = new Trap(w,15,15,"trap2.png");

        ArrayList<Item> looterInv = new ArrayList<>();
        Looter looter = new Looter(w,"méchaant",8,5,2,looterInv,8,8,0,"looter.png");
        w.addToWorld(looter);

        Wolf wolf = new Wolf(w,"WOUF",15,5,2,looterInv,5,7,0,"vase.png");
        w.addToWorld(wolf);

        w.addToWorld(trap);
        //Looter looter = new Looter(w,"looter",7,3,2,new ArrayList<>(),7,7,0,"looter.png");
        //w.addToWorld(looter);
        for (int i=0;i<5;i++){
            River r1 = new River(w,i,14);
            w.addToWorld(r1);
        }
        for (int i=15;i<18;i++){
            River r2 = new River(w,4,i);
            w.addToWorld(r2);
        }

        Door door = new Door(w,16,10,"BLUE","door_closed.png",1);
        w.addToWorld(door);

        door.getNode().setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton() == MouseButton.PRIMARY){
                ArrayList<Key> keys = p.containsKey();
                if(!keys.isEmpty()){
                    for(Key k : keys){
                        if(door.getColor().equals(k.getColor())){
                            door.setOpen(true);
                            ((ImageView) door.getNode()).setImage(new Image("door_open.png"));
                            p.removeFromInventory(k);
                        }
                    }
                }

            }

        });

        return w;
    }

    public World createWorld2(){
        World w = new World("#8e9700");
        Wall wall = new Wall(w,2,2);
        w.addToWorld(wall);
        Sword sword = new Sword(w,"Sword",true,10,10,4,25,"sword.png");
        w.addToWorld(sword);
        Potion potion = new Potion(w,30,2,"Potion de vie",true,"ST+20",10,3,"potion1.png");
        w.addToWorld(potion);
        Buoy buoy = new Buoy(w,28,15,"Sword",true,10,"buoy.png");
        w.addToWorld(buoy);
        Trinket trinket = new Trinket(w,10,20,15,"Hedgehog",true,"hedgehog.png");
        w.addToWorld(trinket);
        Key key = new Key(w,10,15,"Key",true,"BLUE",12,"key.png");
        w.addToWorld(key);
        Book book = new Book(w,"Book",true,"This is a book",1,1,14,"book.png");
        w.addToWorld(book);
        Fouras fouras1 = new Fouras(w, "Wizard",10,15,2,"fouras.png");
        w.addToWorld(fouras1);
        Slime slime = new Slime(w, "Slime", 5, 3, 2, new ArrayList<>(),7,5,0,"slime.png");
        w.addToWorld(slime);
        Tree tree = new Tree(w,5,17,"tree.png");
        w.addToWorld(tree);
        Hedge hedge = new Hedge(w,12,12);
        w.addToWorld(hedge);
        Trap trap = new Trap(w,15,15,"trap2.png");

        ArrayList<Item> looterInv = new ArrayList<>();
        Looter looter = new Looter(w,"méchaant",8,5,2,looterInv,8,8,0,"looter.png");
        w.addToWorld(looter);

        Wolf wolf = new Wolf(w,"WOUF",15,5,2,looterInv,5,7,0,"vase.png");
        w.addToWorld(wolf);

        w.addToWorld(trap);
        for (int i=0;i<5;i++){
            River r1 = new River(w,i,14);
            w.addToWorld(r1);
        }
        for (int i=15;i<18;i++){
            River r2 = new River(w,4,i);
            w.addToWorld(r2);
        }

        Door door = new Door(w,16,10,"BLUE","door_closed.png",2);
        w.addToWorld(door);

        door.getNode().setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton() == MouseButton.PRIMARY){
                ArrayList<Key> keys = p.containsKey();
                if(!keys.isEmpty()){
                    for(Key k : keys){
                        if(door.getColor().equals(k.getColor())){
                            door.setOpen(true);
                            ((ImageView) door.getNode()).setImage(new Image("door_open.png"));
                            p.removeFromInventory(k);
                        }
                    }
                }

            }

        });

        return w;
    }

    public World createWorld3(){
        World w = new World("#611120");
        Wall wall = new Wall(w,2,2);
        w.addToWorld(wall);
        Sword sword = new Sword(w,"Sword",true,10,10,4,25,"sword.png");
        w.addToWorld(sword);
        Potion potion = new Potion(w,30,2,"Potion de vie",true,"ST+20",10,3,"potion1.png");
        w.addToWorld(potion);
        Buoy buoy = new Buoy(w,28,15,"Sword",true,10,"buoy.png");
        w.addToWorld(buoy);
        Trinket trinket = new Trinket(w,10,20,15,"Hedgehog",true,"hedgehog.png");
        w.addToWorld(trinket);
        Key key = new Key(w,10,15,"Key",true,"BLUE",12,"key.png");
        w.addToWorld(key);
        Book book = new Book(w,"Book",true,"This is a book",1,1,14,"book.png");
        w.addToWorld(book);
        Fouras fouras1 = new Fouras(w, "Wizard",10,15,2,"fouras.png");
        w.addToWorld(fouras1);
        Slime slime = new Slime(w, "Slime", 5, 3, 2, new ArrayList<>(),7,5,0,"slime.png");
        w.addToWorld(slime);
        Tree tree = new Tree(w,5,17,"tree.png");
        w.addToWorld(tree);
        Hedge hedge = new Hedge(w,12,12);
        w.addToWorld(hedge);
        Trap trap = new Trap(w,15,15,"trap2.png");

        ArrayList<Item> looterInv = new ArrayList<>();
        Looter looter = new Looter(w,"méchaant",8,5,2,looterInv,8,8,0,"looter.png");
        w.addToWorld(looter);

        Wolf wolf = new Wolf(w,"WOUF",15,5,2,looterInv,5,7,0,"vase.png");
        w.addToWorld(wolf);

        w.addToWorld(trap);
        for (int i=0;i<5;i++){
            River r1 = new River(w,i,14);
            w.addToWorld(r1);
        }
        for (int i=15;i<18;i++){
            River r2 = new River(w,4,i);
            w.addToWorld(r2);
        }

        Door door = new Door(w,16,10,"BLUE","door_closed.png",0);
        w.addToWorld(door);

        door.getNode().setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton() == MouseButton.PRIMARY){
                ArrayList<Key> keys = p.containsKey();
                if(!keys.isEmpty()){
                    for(Key k : keys){
                        if(door.getColor().equals(k.getColor())){
                            door.setOpen(true);
                            ((ImageView) door.getNode()).setImage(new Image("door_open.png"));
                            p.removeFromInventory(k);
                        }
                    }
                }

            }

        });

        return w;
    }
    //endregion

    public static void main(String[] args) {
        launch();
    }
}
