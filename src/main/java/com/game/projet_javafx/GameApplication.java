package com.game.projet_javafx;

import Classes.InstantUse.InstantHealth;
import Classes.InstantUse.InstantMoney;
import Classes.Item.ConsumableItem.Bomb;
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
import Classes.World.DecorItem.WalkThroughDecorItem.Trap;
import Classes.World.DecorItem.NotWalkThroughDecorItem.Tree;
import Classes.World.DecorItem.NotWalkThroughDecorItem.Wall;
import Classes.World.DecorItem.WalkThroughDecorItem.Door;
import Classes.World.DecorItem.NotWalkThroughDecorItem.Hedge;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class GameApplication extends Application {

    //region Attributes
    private Player p;
    private World world;
    GridPane pane;
    FlowPane flowPane;
    FlowPane infosBottom;
    //endregion

    //region start function
    @Override
    public void start(Stage stage) throws IOException {
        /*ArrayList<ArrayList<GameObject>> gridObject = new ArrayList<>();
        gridObject.set(2,new ArrayList<GameObject>())*/

        //region Worlds
        //Creations of the 3 worlds
        World world1 = createWorld1();
        World world2 = createWorld2();
        World world3 = createWorld3();

        //Initialisation of the world list
        World[] listWorld = new World[3];
        listWorld[0] = world1;
        listWorld[1] = world2;
        listWorld[2] = world3;
        world=listWorld[0];
        //endregion

        //region Front : main panes
        flowPane = new FlowPane(Orientation.HORIZONTAL);
        flowPane.setStyle("-fx-background-color: white");
        pane = world.getPane();
        flowPane.getChildren().add(pane);
        p = new Player(world,10,"Bearu",25,4,2,5,5);
        world.addToWorld(p);
        infosBottom = new FlowPane(Orientation.VERTICAL);
        infosBottom.setHgap(10);
        FlowPane infosPerso = new FlowPane(Orientation.VERTICAL);
        infosBottom.getChildren().add(infosPerso);
        /*ImageView avatar = new ImageView("image_pinguin.png");
        avatar.setFitWidth(70);
        avatar.setFitHeight(70);
        infosPerso.getChildren().add(avatar);*/
        //endregion

        //region Front : PLayer's lifebar
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
        //endregion

        //region Front : Player's inventory
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
        //endregion

        //region Front : Player's money
        Label moneyLabel = new Label();                                     //creating the label for the front
        moneyLabel.textProperty().bind(p.getMoneyProperty().asString());    //binding
        Pane money = new Pane(moneyLabel);                                  //pane creation
        //endregion

        //region Front : Filling infosBottom FlowPane
        infosBottom.getChildren().add(inventory);
        infosBottom.getChildren().add(money);
        flowPane.getChildren().add(infosBottom);
        //endregion

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

        //region Front : Scene creation
        Scene scene = new Scene(flowPane);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
        //endregion

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

        //region Listener : movement
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

        //region Listeners : Player's attributes
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

        p.getIndexWorldProperty().addListener(new ChangeListener<Number>() { //listener of the value of indexWorld of the player
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

        //region Listener : Player near an NPC
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
        //endregion

        //region Listener : Player near a Monster
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
                    if (p.getNearByMonster().getLifePoints()<=0){       //verify if monster dead here
                        world.removeFromWorld(p.getNearByMonster());
                        p.getNearByMonster().dropItem();
                        p.setNearByMonster(null);
                    }
                    System.out.println(p.getInventory());
                }
            }
        });
        //endregion
    }
    //endregion

    //region World Creation
    public World createWorld1(){
        World w = new World("#639620");


        //region Trees
        //column0
        for (int i=0;i<=7;i++) {
            Tree tree0 = new Tree(w,0,i,"tree.png");
            w.addToWorld(tree0);
        }
        Tree tree012 = new Tree(w,0,12,"tree.png");
        Tree tree015 = new Tree(w,0,15,"tree.png");
        Tree tree016 = new Tree(w,12,16,"tree.png");
        Tree tree017 = new Tree(w,0,17,"tree.png");
        w.addToWorld(tree012);
        w.addToWorld(tree015);
        w.addToWorld(tree016);
        w.addToWorld(tree017);

        //column1
        Tree tree14 = new Tree(w,1,4,"tree.png");
        w.addToWorld(tree14);

        //column2
        Tree tree21 = new Tree(w,2,1,"tree.png");
        Tree tree22 = new Tree(w,2,2,"tree.png");
        Tree tree212 = new Tree(w,2,12,"tree.png");
        Tree tree213 = new Tree(w,2,13,"tree.png");
        w.addToWorld(tree21);
        w.addToWorld(tree22);
        w.addToWorld(tree212);
        w.addToWorld(tree213);

        //column3
        for (int i=0;i<=5;i++) {
            Tree tree3 = new Tree(w,0,i,"tree.png");
            w.addToWorld(tree3);
        }
        Tree tree313 = new Tree(w,3,13,"tree.png");
        w.addToWorld(tree313);

        //column4
        Tree tree42 = new Tree(w,4,2,"tree.png");
        w.addToWorld(tree42);

        //column5
        Tree tree53 = new Tree(w,4,2,"tree.png");
        w.addToWorld(tree53);

        //column6
        for (int i=1;i<=6;i++) {
            Tree tree6 = new Tree(w,0,i,"tree.png");
            w.addToWorld(tree6);
        }

        //column7
        Tree tree76 = new Tree(w,7,6,"tree.png");
        Tree tree77 = new Tree(w,7,7,"tree.png");
        Tree tree716 = new Tree(w,7,16,"tree.png");
        w.addToWorld(tree76);
        w.addToWorld(tree77);
        w.addToWorld(tree716);

        //column9
        Tree tree92 = new Tree(w,9,2,"tree.png");
        Tree tree914 = new Tree(w,9,14,"tree.png");
        w.addToWorld(tree92);
        w.addToWorld(tree914);

        //column10
        Tree tree102 = new Tree(w,10,2,"tree.png");
        Tree tree1014 = new Tree(w,10,14,"tree.png");
        Tree tree1016 = new Tree(w,10,16,"tree.png");
        w.addToWorld(tree102);
        w.addToWorld(tree1014);
        w.addToWorld(tree1016);

        //column11
        Tree tree111 = new Tree(w,11,11,"tree.png");
        w.addToWorld(tree111);

        //column12
        Tree tree1215 = new Tree(w,12,15,"tree.png");
        Tree tree1217 = new Tree(w,12,17,"tree.png");
        w.addToWorld(tree1215);
        w.addToWorld(tree1217);

        //column17
        Tree tree1716 = new Tree(w,17,16,"tree.png");
        w.addToWorld(tree1716);

        //column18
        Tree tree1814 = new Tree(w,18,14,"tree.png");
        Tree tree1815 = new Tree(w,18,15,"tree.png");
        w.addToWorld(tree1814);
        w.addToWorld(tree1815);

        //column19
        Tree tree1913 = new Tree(w,19,13,"tree.png");
        w.addToWorld(tree1913);

        //column20
        Tree tree207 = new Tree(w,20,7,"tree.png");
        w.addToWorld(tree207);

        //column22
        Tree tree2216 = new Tree(w,22,16,"tree.png");
        w.addToWorld(tree2216);

        //colum23
        Tree tree231 = new Tree(w,23,1,"tree.png");
        Tree tree234 = new Tree(w,23,4,"tree.png");
        Tree tree2317 = new Tree(w,23,17,"tree.png");
        w.addToWorld(tree231);
        w.addToWorld(tree234);
        w.addToWorld(tree2317);

        //column24
        Tree tree245 = new Tree(w,24,5,"tree.png");
        Tree tree248 = new Tree(w,24,8,"tree.png");
        w.addToWorld(tree245);
        w.addToWorld(tree248);

        //colum27
        Tree tree274 = new Tree(w,27,4,"tree.png");
        w.addToWorld(tree274);

        //column28
        for (int i=0;i<=1;i++) {
            Tree tree282 = new Tree(w,0,i,"tree.png");
            w.addToWorld(tree282);
        }
        for (int i=3;i<=6;i++) {
            Tree tree283 = new Tree(w,0,i,"tree.png");
            w.addToWorld(tree283);
        }
        Tree tree2810 = new Tree(w,28,10,"tree.png");
        w.addToWorld(tree2810);

        //column29
        Tree tree293 = new Tree(w,29,3,"tree.png");
        Tree tree2910 = new Tree(w,29,10,"tree.png");
        w.addToWorld(tree293);
        w.addToWorld(tree2910);

        //colum30
        for (int i=2;i<=3;i++) {
            Tree tree30 = new Tree(w,0,i,"tree.png");
            w.addToWorld(tree30);
        }

        //column31
        Tree tree3111 = new Tree(w,31,11,"tree.png");
        w.addToWorld(tree3111);

        //column32
        Tree tree3217 = new Tree(w,32,17,"tree.png");
        w.addToWorld(tree3217);

        //column33
        Tree tree332 = new Tree(w,33,2,"tree.png");
        w.addToWorld(tree332);

        //column34
        Tree tree345 = new Tree(w,34,5,"tree.png");
        w.addToWorld(tree345);

        //column36
        Tree tree360 = new Tree(w,36,0,"tree.png");
        Tree tree367 = new Tree(w,36,7,"tree.png");
        Tree tree3610 = new Tree(w,36,10,"tree.png");
        w.addToWorld(tree360);
        w.addToWorld(tree367);
        w.addToWorld(tree3610);

        //column37
        Tree tree373 = new Tree(w,37,3,"tree.png");
        Tree tree3710 = new Tree(w,37,10,"tree.png");
        w.addToWorld(tree373);
        w.addToWorld(tree3710);

        //column38
        for (int i=2;i<=3;i++) {
            Tree tree38 = new Tree(w,0,i,"tree.png");
            w.addToWorld(tree38);
        }
        Tree tree3810 = new Tree(w,38,10,"tree.png");
        w.addToWorld(tree3810);
        //endregion

        //region River

        //row0
        for (int i=10;i<=13;i++) {
            River river0 = new River(w,i,0);
            w.addToWorld(river0);
        }
        for (int i=19;i<=21;i++) {
            River river01 = new River(w,i,0);
            w.addToWorld(river01);
        }

        //row1
        for (int i=10;i<=13;i++) {
            River river0 = new River(w,i,0);
            w.addToWorld(river0);
        }
        for (int i=19;i<=21;i++) {
            River river01 = new River(w,i,0);
            w.addToWorld(river01);
        }
        //endregion






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
        ArrayList<Item> itemSlime = new ArrayList<>();
        itemSlime.add(key);
        Slime slime = new Slime(w, "Slime", 5, 3, 2, itemSlime,7,5,0,"slime.png");
        w.addToWorld(slime);
        Hedge hedge = new Hedge(w,12,12);
        w.addToWorld(hedge);
        Trap trap = new Trap(w,15,15,"trap2.png");
        Bomb bomb = new Bomb(w,6,17,"BIGBOMB",true,10,"bomb.png");
        w.addToWorld(bomb);

        InstantMoney instantMoney = new InstantMoney(w,5,10,10,"coin.png");
        w.addToWorld(instantMoney);
        InstantHealth instantHealth = new InstantHealth(w,6,6,10,"heart.png");
        w.addToWorld(instantHealth);

        InstantHealth instant2Health = new InstantHealth(w,1,2,1,"heart.png");
        w.addToWorld(instant2Health);


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

        //Door mouse event
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

    public static void main(String[] args) {launch();}
}
