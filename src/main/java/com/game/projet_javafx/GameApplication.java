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
import Classes.World.DecorItem.NotWalkThroughDecorItem.Door;
import Classes.World.DecorItem.NotWalkThroughDecorItem.Hedge;
import Classes.World.DecorItem.WalkThroughDecorItem.River;
import Classes.World.Position;
import Classes.World.World;
import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        p = new Player(world,7,9,"Bearu",10,4,2,25,"penguin.png");
        world.addToWorld(p);
        infosBottom = new FlowPane(Orientation.HORIZONTAL);
        infosBottom.setHgap(10);
        infosBottom.setPrefWidth(Position.WIDTH);
        infosBottom.setAlignment(Pos.TOP_LEFT);
        FlowPane infosPerso = new FlowPane(Orientation.HORIZONTAL);
        //endregion
        //region Front : infosPlayer
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
        lifes.setWidth(p.getLifePoints()*15);
        lifes.setFill(Color.GREEN);
        lifeBar.add(lifes, 0,0,10,1);
        infosPerso.setPrefHeight(20);
        infosPerso.setPrefWidth(220);
        infosPerso.getChildren().add(lifeBar);
        //endregion
        //region Front : Player's money
        Text moneyLabel = new Text();                                     //creating the label for the front
        moneyLabel.textProperty().bind(p.getMoneyProperty().asString());    //binding
        moneyLabel.setStyle("-fx-font-size: 15px");
        ImageView imageMoney = new ImageView(new Image("coin.png"));
        imageMoney.setFitWidth(25);
        imageMoney.setFitHeight(25);
        HBox money = new HBox();
        money.getChildren().add(imageMoney);
        money.getChildren().add(moneyLabel);
        money.setPrefHeight(25);
        money.setFillHeight(true);
        money.setAlignment(Pos.CENTER);
        infosPerso.getChildren().add(money);
        //endregion
        //endregion
        //region Front : Player's inventory
        GridPane inventory = new GridPane();
        inventory.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 3px;-fx-max-width: 230px;-fx-max-height: 95px");
        inventory.setHgap(5);
        inventory.setVgap(5);
        inventory.setAlignment(Pos.TOP_LEFT);
        for (int i=0;i<2;i++){
            for (int j=0;j<5;j++) {
                Region r = new Region();
                r.setPrefHeight(40);
                r.setPrefWidth(40);
                r.setStyle("-fx-border-color: black;-fx-border-width: 1px;");
                inventory.add(r, j, i);
                int index = i*5+j;
                if (index<p.getSizeInventory()) {
                    inventory.add(p.getInventory().get(index).getNode(), j, i);
                }
            }
        }
        //endregion

        //region Front : Filling infosBottom FlowPane
        infosBottom.getChildren().add(infosPerso);
        infosBottom.getChildren().add(inventory);
        flowPane.getChildren().add(infosBottom);
        //endregion

        File file = new File("src\\main\\resources\\game_music.mp3");
        System.out.println("here");
        final String MEDIA_URL = file.toURI().toString();
        System.out.println("here");
        Media media = new Media(MEDIA_URL);
        System.out.println("here");
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        System.out.println("here");
        mediaPlayer.volumeProperty().set(0.1);
        //mediaPlayer.setStopTime(Duration.seconds(212));
        mediaPlayer.play();

        //region Front : Scene creation
        Scene scene = new Scene(flowPane);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
        //endregion

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
        spaceAndRightPressed.addListener((observableValue, aBoolean, t1) -> {
            if (spacePressed.getValue()) {
                p.jump(GridPane.getColumnIndex(p.node) + 2, GridPane.getRowIndex(p.node));
                spacePressed.set(false);
                jumpEffectued.set(true);
            }
        });
        spaceAndLeftPressed.addListener((observableValue, aBoolean, t1) -> {
            if (spacePressed.getValue()) {
                p.jump(GridPane.getColumnIndex(p.node) - 2, GridPane.getRowIndex(p.node));
                spacePressed.set(false);
                jumpEffectued.set(true);
            }
        });

        spaceAndUpPressed.addListener((observableValue, aBoolean, t1) -> {
            if (spacePressed.getValue()) {
                p.jump(GridPane.getColumnIndex(p.node), GridPane.getRowIndex(p.node)-2);
                spacePressed.set(false);
                jumpEffectued.set(true);
            }
        });

        spaceAndDownPressed.addListener((observableValue, aBoolean, t1) -> {
            if (spacePressed.getValue()) {
                p.jump(GridPane.getColumnIndex(p.node), GridPane.getRowIndex(p.node)+2);
                spacePressed.set(false);
                jumpEffectued.set(true);
            }
        });

        rightPressed.addListener((observableValue, aBoolean, t1) -> {
            if (!jumpEffectued.getValue() && rightPressed.getValue()){
                p.move(GridPane.getColumnIndex(p.node) + 1, GridPane.getRowIndex(p.node));
                rightPressed.set(false);
            }
        });

        leftPressed.addListener((observableValue, aBoolean, t1) -> {
            if (!jumpEffectued.getValue() && leftPressed.getValue()){
                p.move(GridPane.getColumnIndex(p.node) - 1, GridPane.getRowIndex(p.node));
                leftPressed.set(false);
            }
        });

        upPressed.addListener((observableValue, aBoolean, t1) -> {
            if (!jumpEffectued.getValue() && upPressed.getValue()){
                p.move(GridPane.getColumnIndex(p.node), GridPane.getRowIndex(p.node)-1);
                upPressed.set(false);
            }
        });

        downPressed.addListener((observableValue, aBoolean, t1) -> {
            if (!jumpEffectued.getValue() && downPressed.getValue()){
                p.move(GridPane.getColumnIndex(p.node), GridPane.getRowIndex(p.node)+1);
                downPressed.set(false);
            }
        });


        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.RIGHT) {
                rightPressed.set(true);
            }else if (e.getCode() == KeyCode.LEFT) {
                leftPressed.set(true);
            }else if (e.getCode() == KeyCode.UP) {
                upPressed.set(true);
            }else if (e.getCode() == KeyCode.DOWN) {
                downPressed.set(true);
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
        //listener of the value of life points of the player
        p.getLifePointsProperty().addListener((observableValue, number, t1) -> {  //function called when Player's LP change
            if (p.getLifePoints()<=0){                                                                              //verification failure condition
                DefeatApplication defeat = new DefeatApplication();
                try {
                    defeat.start(new Stage());                                                              //launch defeat stage
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                stage.close();
            }                                                                                               //15 is the size of one "life"
            lifes.setWidth(p.getLifePoints()*15);                                                                   //for the front resizes life bar with LP left
        });

        //listener of the size of the player's inventory
        p.getSizeInventoryProperty().addListener((observableValue, number, t1) -> {      //function called when the size of the inventory changes
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
                    if (index<p.getSizeInventoryProperty().getValue()) {
                        inventory.add(p.getInventory().get(index).getNode(), j, i);     //add items one by one to the gridpane
                    }
                }
            }
        });

        //listener of the value of money of the player
        p.getMoneyProperty().addListener((observableValue, number, t1) -> System.out.println(p.getMoneyProperty()));

        //listener of the value of indexWorld of the player
        p.getIndexWorldProperty().addListener((observableValue, number, t1) -> {
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

        });
        //endregion

        //region Listener : Player near an NPC
        p.nearByNPCProperty().addListener((observableValue, npc, t1) -> {
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
        });
        //endregion

        //region Listener : Player near a Monster
        p.nearByMonsterProperty().addListener((observableValue, monster, t1) -> {
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
        });
        //endregion
    }
    //endregion

    //region World Creation
    public World createWorld1(){
        World w = new World(this,"#639620","#507a1a");


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
            Tree tree3 = new Tree(w,3,i,"tree.png");
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
            Tree tree6 = new Tree(w,6,i,"tree.png");
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
            Tree tree282 = new Tree(w,28,i,"tree.png");
            w.addToWorld(tree282);
        }
        for (int i=3;i<=6;i++) {
            Tree tree283 = new Tree(w,28,i,"tree.png");
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
            Tree tree30 = new Tree(w,30,i,"tree.png");
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
            Tree tree38 = new Tree(w,38,i,"tree.png");
            w.addToWorld(tree38);
        }
        Tree tree3810 = new Tree(w,38,10,"tree.png");
        w.addToWorld(tree3810);

        //column39
        Tree tree390 = new Tree(w,39,0,"tree.png");
        w.addToWorld(tree390);
        Tree tree3910 = new Tree(w,39,10,"tree.png");
        w.addToWorld(tree3910);
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
        for (int i=12;i<=14;i++) {
            River river1 = new River(w,i,1);
            w.addToWorld(river1);
        }
        for (int i=17;i<=21;i++) {
            River river11 = new River(w,i,1);
            w.addToWorld(river11);
        }

        //row2
        for (int i=13;i<=20;i++) {
            River river2 = new River(w,i,2);
            w.addToWorld(river2);
        }

        //row9
        for (int i=0;i<=2;i++) {
            River river9 = new River(w,i,9);
            w.addToWorld(river9);
        }
        //row10
        for (int i=0;i<=4;i++) {
            River river10 = new River(w,i,10);
            w.addToWorld(river10);
        }
        //row11
        for (int i=2;i<=4;i++) {
            River river11 = new River(w,i,11);
            w.addToWorld(river11);
        }
        for (int i=19;i<=21;i++) {
            River river111 = new River(w,i,11);
            w.addToWorld(river111);
        }
        //row12
        for (int i=3;i<=22;i++) {
            River river12 = new River(w,i,12);
            w.addToWorld(river12);
        }
        //row13
        for (int i=5;i<=18;i++) {
            River river13 = new River(w,i,13);
            w.addToWorld(river13);
        }
        for (int i=21;i<=23;i++) {
            River river131 = new River(w,i,13);
            w.addToWorld(river131);
        }
        //row14
        for (int i=23;i<=25;i++) {
            River river14 = new River(w,i,14);
            w.addToWorld(river14);
        }
        //row15
        for (int i=23;i<=39;i++) {
            River river15 = new River(w,i,15);
            w.addToWorld(river15);
        }
        //row16
        for (int i=25;i<=39;i++) {
            River river16 = new River(w,i,16);
            w.addToWorld(river16);
        }
        //endregion

        //region Walls
        //market
        //row
        for (int i=10;i<=19;i++) {
            Wall wall0 = new Wall(w,i,4);
            w.addToWorld(wall0);
        }

        //columns
        for (int i=5;i<=8;i++){
            Wall wall01 = new Wall(w,10,i);
            w.addToWorld(wall01);
        }
        for (int i=5;i<=8;i++) {
            Wall wall02 = new Wall(w,13,i);
            w.addToWorld(wall02);
        }
        for (int i=5;i<=8;i++) {
            Wall wall03 = new Wall(w,16,i);
            w.addToWorld(wall03);
        }
        for (int i=5;i<=8;i++) {
            Wall wall04 = new Wall(w,19,i);
            w.addToWorld(wall04);
        }

        //Looter's warehouse
        //rows
        for (int i=1;i<=6;i++) {
            Wall wall1 = new Wall(w,i,14);
            w.addToWorld(wall1);
        }
        for (int i=1;i<=6;i++) {
            Wall wall11 = new Wall(w,i,17);
            w.addToWorld(wall11);
        }

        //column
        for (int i=15;i<=16;i++) {
            Wall wall12 = new Wall(w,1,i);
            w.addToWorld(wall12);
        }

        //Trappppp, I like trap, trap cute
        //rows
        for (int i=36;i<=39;i++) {
            Wall wall2 = new Wall(w,i,11);
            w.addToWorld(wall2);
        }
        for (int i=36;i<=39;i++) {
            Wall wall21 = new Wall(w,i,14);
            w.addToWorld(wall21);
        }

        //column
        for (int i=12;i<=13;i++) {
            Wall wall22 = new Wall(w,39,i);
            w.addToWorld(wall22);
        }
        Wall wall23 = new Wall(w,36,12);
        w.addToWorld(wall23);

        //endregion

        //region hedges
        for (int i=20;i<=28;i++) {
            Hedge hedge0 = new Hedge(w,i,6);
            w.addToWorld(hedge0);
        }
        for (int i=8;i<=9;i++){
            Hedge hedge1 = new Hedge(w,36,i);
            w.addToWorld(hedge1);
        }
        //endregion

        //region items
        Key key_green = new Key(w,1,2,"green key",5,true,"key_green.png","green");
        w.addToWorld(key_green);



        Bomb bomb1 = new Bomb(w,4,0,"boum",10,true,"bomb.png");
        w.addToWorld(bomb1);
        Bomb bomb2 = new Bomb(w,6,16,"BOUUUUM eheh",10,true,"bomb.png");
        w.addToWorld(bomb2);
        Bomb bomb3 = new Bomb(w,17,5,"boum",10,true,"bomb.png");
        w.addToWorld(bomb3);



        InstantMoney instMoney1 = new InstantMoney(w,11,6,10,"coin.png");
        w.addToWorld(instMoney1);
        InstantMoney instMoney2 = new InstantMoney(w,14,5,10,"coin.png");
        w.addToWorld(instMoney2);
        InstantMoney instMoney3 = new InstantMoney(w,15,5,10,"coin.png");
        w.addToWorld(instMoney3);
        for (int i=2;i<=3;i++) {
            InstantMoney instMoney4 = new InstantMoney(w,i,15,10,"coin.png");
            w.addToWorld(instMoney4);
        }
        InstantMoney instMoney5 = new InstantMoney(w,2,16,10,"coin.png");
        w.addToWorld(instMoney5);

        InstantHealth instHealth1 = new InstantHealth(w,19,14,5,"heart.png");
        w.addToWorld(instHealth1);
        InstantHealth instHealth2 = new InstantHealth(w,38,12,5,"heart.png");
        w.addToWorld(instHealth2);

        Trap trap0 = new Trap(w,4,1,"trap.png");
        w.addToWorld(trap0);
        Trap trap1 = new Trap(w,18,7,"trap.png");
        w.addToWorld(trap1);
        Trap trap2 = new Trap(w,36,13,"trap.png");
        w.addToWorld(trap2);
        for (int i=3;i<=4;i++) {
            Trap trap3 = new Trap(w,i,16,"trap.png");
            w.addToWorld(trap3);
        }

        Buoy buoy = new Buoy(w,14,6,"Buoyyyyyy",8,true,"buoy.png");
        w.addToWorld(buoy);

        Book book = new Book(w,37,12,"Nice book",7,true,"book.png","I am a nice book eheh");
        w.addToWorld(book);

        Trinket vase = new Trinket(w,38,13,"Cute vase",10,true,"vase.png");
        w.addToWorld(vase);

        Potion potion1 = new Potion(w,12,5,"Life potion",10,true,"potion1.png","LIFE+5",0);
        w.addToWorld(potion1);

        Sword sword = new Sword(w,37,2,"Sword",25,true,"sword.png",4);
        w.addToWorld(sword);

        Potion potion2 = new Potion(w,15,6,"Defense potion",10,true,"potion2.png","DE+20",3);

        Trinket roller = new Trinket(w, 11,7,"Roller",20,false,"roller.png");
        Trinket camera = new Trinket(w, 11,7,"Camera",20,false,"camera.png");




        //endregion

        //region NPC
        Fouras fouras1 = new Fouras(w,5,4,"Grand Fouras Suprême",50,"fouras.png");
        w.addToWorld(fouras1);

        Fouras fouras2 = new Fouras(w,0,8,"Magicien",30,"fouras2.png");
        w.addToWorld(fouras2);

        Merchant merchant1 = new Merchant(w,11,7,"Bob the merchant",40,"merchant.png");
        merchant1.addToInventory(roller);
        merchant1.addToInventory(camera);
        w.addToWorld(merchant1);

        Merchant merchant2 = new Merchant(w,15,6,"Patrick the merchant",30,"merchant.png");
        merchant2.addToInventory(potion2);
        w.addToWorld(merchant2);
        //endregion

        //region Monsters
        Looter looter = new Looter(w,4,15,"Mwahaha I loot",new ArrayList<Item>(),10,5,2,0,"looter.png");
        w.addToWorld(looter);

        Wolf wolf1 = new Wolf(w,2,0,"agrougrou",new ArrayList<Item>(),10,4,3,0,"wolf.png");
        w.addToWorld(wolf1);

        Wolf wolf2 = new Wolf(w,1,1,"ahouuuuuuu",new ArrayList<Item>(),10,4,3,0,"wolf.png");
        w.addToWorld(wolf2);

        Slime slime1 = new Slime(w,15,0,"slimeyyy",new ArrayList<Item>(),10,3,2,0,"slime.png");
        w.addToWorld(slime1);

        Slime slime2 = new Slime(w,17,0,"slimeuuuh",new ArrayList<Item>(),10,3,2,0,"slime2.png");
        w.addToWorld(slime2);
        //endregion

        Door door = new Door(w,18,5,"green","door_closed_green.png",1);
        w.addToWorld(door);
        return w;
    }

    public World createWorld2(){
        World w = new World(this,"#2e9b48","#1e6730");
        //region Trees
        //region forest
        for (int i=0;i<7;i++){
            w.addToWorld(new Tree(w,0,i,"tree.png"));
        }
        w.addToWorld(new Tree(w,1,6,"tree.png"));
        for (int i=1;i<7;i++){
            w.addToWorld(new Tree(w,2,i,"tree.png"));
        }
        for (int i=1;i<7;i++){
            w.addToWorld(new Tree(w,3,i,"tree.png"));
        }
        w.addToWorld(new Tree(w,4,2,"tree.png"));
        w.addToWorld(new Tree(w,4,4,"tree.png"));
        w.addToWorld(new Tree(w,4,6,"tree.png"));
        w.addToWorld(new Tree(w,5,6,"tree.png"));
        for (int i=1;i<7;i++){
            w.addToWorld(new Tree(w,6,i,"tree.png"));
        }
        //endregion
        w.addToWorld(new Tree(w,8,14,"tree.png"));
        w.addToWorld(new Tree(w,8,16,"tree.png"));
        w.addToWorld(new Tree(w,9,15,"tree.png"));
        w.addToWorld(new Tree(w,10,14,"tree.png"));
        w.addToWorld(new Tree(w,10,16,"tree.png"));
        for (int i=14;i<18;i++){
            w.addToWorld(new Tree(w,35,i,"tree.png"));
        }
        w.addToWorld(new Tree(w,19,5,"tree.png"));
        w.addToWorld(new Tree(w,10,7,"tree.png"));
        w.addToWorld(new Tree(w,12,12,"tree.png"));
        w.addToWorld(new Tree(w,21,15,"tree.png"));
        w.addToWorld(new Tree(w,28,9,"tree.png"));
        w.addToWorld(new Tree(w,15,1,"tree.png"));
        //endregion
        //region Walls
        for (int i=0;i<3;i++){
            w.addToWorld(new Wall(w,i,7));
        }
        for (int i=8;i<12;i++){
            w.addToWorld(new Wall(w,0,i));
        }
        for (int i=0;i<11;i++){
            w.addToWorld(new Wall(w,i,11));
        }
        for (int i=0;i<6;i++){
            w.addToWorld(new Wall(w,11+i,11+i));
            w.addToWorld(new Wall(w,11+i,12+i));
        }
        for (int i=0;i<4;i++){
            w.addToWorld(new Wall(w,i,14));
        }
        for (int i=15;i<18;i++){
            w.addToWorld(new Wall(w,3,i));
        }
        for (int i=10;i<18;i++){
            w.addToWorld(new Wall(w,36,i));
        }
        for (int i=18;i<30;i++){
            w.addToWorld(new Wall(w,i,3));
        }
        for (int i=0;i<3;i++){
            w.addToWorld(new Wall(w,30+i,3+i));
            w.addToWorld(new Wall(w,30+i,4+i));
        }
        for (int i=33;i<40;i++){
            w.addToWorld(new Wall(w,i,6));
        }
        //endregion
        //region River
        for (int i=0;i<5;i++){
            w.addToWorld(new River(w,29-i,13+i));
            w.addToWorld(new River(w,30-i,13+i));
            w.addToWorld(new River(w,31-i,13+i));
        }
        for (int i=32;i<36;i++){
            w.addToWorld(new River(w,i,13));
        }
        for (int i=31;i<35;i++){
            w.addToWorld(new River(w,i,14));
        }
        for (int i=0;i<3;i++){
            w.addToWorld(new River(w,36-i,7+i));
            w.addToWorld(new River(w,37-i,7+i));
            w.addToWorld(new River(w,38-i,7+i));
        }
        for (int i=34;i<36;i++){
            for (int j=10;j<13;j++){
                w.addToWorld(new River(w,i,j));
            }
        }
        w.addToWorld(new River(w,39,7));
        //endregion
        //region Hedge
        for (int i=18;i<30;i++){
            w.addToWorld(new Hedge(w,i,4));
        }
        w.addToWorld(new Hedge(w,8,15));
        w.addToWorld(new Hedge(w,9,14));
        w.addToWorld(new Hedge(w,9,16));
        w.addToWorld(new Hedge(w,10,15));
        //endregion
        //region Money
        for (int i=33;i<36;i++){
            w.addToWorld(new InstantMoney(w,i,1,1,"coin.png"));
        }
        for (int i=2;i<5;i++){
            w.addToWorld(new InstantMoney(w,34,i,1,"coin.png"));
        }
        w.addToWorld(new InstantMoney(w,38,1,1,"coin.png"));
        w.addToWorld(new InstantMoney(w,37,2,1,"coin.png"));
        w.addToWorld(new InstantMoney(w,38,3,1,"coin.png"));
        w.addToWorld(new InstantMoney(w,37,4,1,"coin.png"));
        w.addToWorld(new InstantMoney(w,4,3,1,"coin.png"));
        w.addToWorld(new InstantMoney(w,0,15,1,"coin.png"));
        w.addToWorld(new InstantMoney(w,2,15,1,"coin.png"));
        w.addToWorld(new InstantMoney(w,2,17,1,"coin.png"));
        //endregion
        //region Heart
        w.addToWorld(new InstantHealth(w,0,17,1,"heart.png"));
        //endregion
        //region Item
        w.addToWorld(new Bomb(w,1,9,"Bomb",20,true,"bomb.png"));
        w.addToWorld(new Key(w,39,5,"Green Key",20,true,"key_blue.png","blue"));
        w.addToWorld(new Book(w,33,5,"Book",7, true,"book.png","You're searching for a sword or a buoy ? Go see the merchant behind the walls"));
        w.addToWorld(new Potion(w,4,5,"Strenght Potion",20,true,"potion1.png","ST+40",3));
        w.addToWorld(new Potion(w,0,12,"Defense Potion",15,true,"potion2.png","DE+20",3));
        w.addToWorld(new Trinket(w,1,16,"22's hat",40,true,"hat.png"));
        w.addToWorld(new Trinket(w,15,17,"Heart glasses",20,true,"glasses.png"));
        //region Item in inventory
        Trinket scarf = new Trinket(w,1,5,"Red Scarf",7,false,"scarf.png");
        Trinket guitar = new Trinket(w,1,5,"Guitar",15,false,"guitar.png");
        Trinket micro = new Trinket(w,29,1,"Micro",15,false,"micro.png");
        Trinket painAuChocolat = new Trinket(w,38,11,"Pain au chocolat",10,false,"pain_au_chocolat.png");
        Sword sword = new Sword(w,5,16,"Sword",20,false,"sword.png",2);
        Buoy buoy = new Buoy(w,5,16,"Buoy",15,false,"buoy.png");
        buoy.getNode().setOnMouseClicked(new ClickItemHandler(buoy));
        //endregion
        //endregion
        //region Monster
        //region Slime
        w.addToWorld(new Slime(w,5,5,"Cutie Slime",new ArrayList<>(),8,3,2,0,"slime2.png"));
        w.addToWorld(new Slime(w,3,9,"Speaky Slime",new ArrayList<>(),9,5,3,0,"slime3.png"));
        w.addToWorld(new Slime(w,38,11,"Red Slime",new ArrayList<>(List.of(painAuChocolat)),10,7,4,0,"slime.png"));
        //endregion
        //region Looter
        w.addToWorld(new Looter(w,29,1,"Scooter",new ArrayList<>(List.of(micro)),7,4,1,0,"looter.png"));
        w.addToWorld(new Looter(w,14,17,"Kanye",new ArrayList<>(),5,3,2,0,"looter2.png"));
        //endregion
        //region Wolf
        w.addToWorld(new Wolf(w,2,12,"Meredith the Wolf",new ArrayList<>(), 8,4,3,0,"wolf.png"));
        w.addToWorld(new Wolf(w,24,1,"Olivia the Wolf",new ArrayList<>(), 8,6,4,0,"wolf.png"));
        //endregion
        //endregion
        //region NPC
        //region Fouras
        w.addToWorld(new Fouras(w,7,3,"Willow",10,"fouras2.png"));
        w.addToWorld(new Fouras(w,33,16,"Dumbledore",20,"fouras.png"));
        //endregion
        //region Merchant
        Merchant forestMerchant = new Merchant(w,1,5,"Forest Merchant",30,"merchant.png");
        forestMerchant.addToInventory(scarf);
        forestMerchant.addToInventory(guitar);
        w.addToWorld(forestMerchant);
        Merchant hiddenMerchant = new Merchant(w,5,16,"Hidden Merchant",25,"merchant.png");
        hiddenMerchant.addToInventory(sword);
        hiddenMerchant.addToInventory(buoy);
        w.addToWorld(hiddenMerchant);
        //endregion
        //region Useless
        w.addToWorld(new UselessPerson(w,36,2, "TayTay",100,"useless1.png"));
        w.addToWorld(new UselessPerson(w,8,10, "Knight",10,"useless2.png"));
        //endregion
        //endregion
        //region Trap
        w.addToWorld(new Trap(w,4,1,"trap.png"));
        w.addToWorld(new Trap(w,38,13,"trap.png"));
        //endregion
        //region Door
        Door doorPreviousWorld = new Door(w,18,5,"green","door_open_green.png",0);
        doorPreviousWorld.setOpen(true);
        w.addToWorld(doorPreviousWorld);
        Door doorNextWorld = new Door(w,38,15,"blue","door_closed_blue.png",2);
        w.addToWorld(doorNextWorld);

        //endregion


        return w;
    }

    public World createWorld3(){
        World w = new World(this,"#244200","#1b3100");
        Trinket trinket = new Trinket(w,20,15,"Hedgehog",10,false,"hedgehog.png");
        Slime slime = new Slime(w,20,15,"Slime",new ArrayList<>(List.of(trinket)),5,4,2,0,"slime.png");
        w.addToWorld(slime);
        Door doorPreviousWorld = new Door(w,38,15,"blue","door_open_blue.png",1);
        doorPreviousWorld.setOpen(true);
        w.addToWorld(doorPreviousWorld);
        return w;
    }
    //endregion

    public static void main(String[] args) {launch();}

    public class ClickItemHandler implements EventHandler<MouseEvent> {
        Item item;

        public ClickItemHandler(Item item) {
            this.item=item;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
            if (!item.isDropped() && mouseEvent.getButton() == MouseButton.SECONDARY) {
                item.setDropped(true);
                p.removeFromInventory(item);
                item.setPosition(p.getPosition().getX(), p.getPosition().getY());
            }
            if(!item.isDropped() && mouseEvent.getButton() == MouseButton.PRIMARY){
                if(item instanceof Book){
                    BookApplication bookApp = new BookApplication((Book) item);
                    try {
                        bookApp.start(new Stage());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            if(item instanceof Bomb && mouseEvent.getButton() == MouseButton.PRIMARY && item.isDropped()){
                world.destroysRadiusBomb(item.getPosition().getX(), item.getPosition().getY());
            }
        }
    }
    public class ClickDoorHandler implements EventHandler<MouseEvent> {
        Door door;

        public ClickDoorHandler(Door door) {
            this.door=door;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
            if(mouseEvent.getButton() == MouseButton.PRIMARY && !door.isOpen()) {
                ArrayList<Key> keys = p.containsKey();
                if (keys!=null && !keys.isEmpty()) {
                    for (Key k : keys) {
                        if (door.getColor().equals(k.getColor())) {
                            door.setOpen(true);
                            ((ImageView) door.getNode()).setImage(new Image("door_open_"+door.getColor()+".png"));
                            p.removeFromInventory(k);
                        }
                    }
                }
            }
        }
    }
}
