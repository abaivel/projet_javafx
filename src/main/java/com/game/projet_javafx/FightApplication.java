package com.game.projet_javafx;

import Classes.Item.ConsumableItem.Potion;
import Classes.Item.Item;
import Classes.Monster.Monster;
import Classes.Player.Player;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

//Front for fight sequences -> triggers when going near a monster
public class FightApplication extends Application {

    //region Attributes
    private final Player player;
    private final Monster monster;
    private final BooleanProperty playerTurn;
    private final Stage primaryStage;
    //endregion

    //region Constructor
    public FightApplication(Player player, Monster monster, Stage primaryStage) {
        this.player = player;
        this.monster = monster;
        this.playerTurn=new SimpleBooleanProperty(true);
        this.primaryStage=primaryStage;
    }
    //endregion

    //region start function
    @Override
    public void start(Stage stage) throws Exception {
        FlowPane pane = new FlowPane(Orientation.VERTICAL);
        //region paneLifeBars
        AnchorPane paneLifeBars = new AnchorPane();
        GridPane lifeBarPlayer = createLifeBar();
        Rectangle lifesPlayer = new Rectangle();
        lifesPlayer.setHeight(20);
        lifesPlayer.setWidth(player.getLifePoints()*15);
        lifesPlayer.setFill(Color.GREEN);
        lifeBarPlayer.add(lifesPlayer, 0,0,10,1);
        AnchorPane.setTopAnchor(lifeBarPlayer, 0.0);
        AnchorPane.setLeftAnchor(lifeBarPlayer, 0.0);

        GridPane lifeBarMonster = createLifeBar();
        Rectangle lifesMonster = new Rectangle();
        lifesMonster.setHeight(20);
        lifesMonster.setWidth(monster.getLifePoints()*15);
        lifesMonster.setFill(Color.GREEN);
        lifeBarMonster.add(lifesMonster, 0,0,10,1);
        AnchorPane.setTopAnchor(lifeBarMonster, 0.0);
        AnchorPane.setRightAnchor(lifeBarMonster, 50.0);

        paneLifeBars.getChildren().addAll(lifeBarPlayer,lifeBarMonster);
        paneLifeBars.setPrefHeight(pane.getHeight());
        paneLifeBars.setPrefWidth(pane.getWidth());
        pane.getChildren().add(paneLifeBars);
        //endregion
        //region listStatus
        AnchorPane listStatus= new AnchorPane();
        FlowPane listStatusPlayer = new FlowPane(Orientation.HORIZONTAL);
        listStatusPlayer.setStyle("-fx-font-size: 20px;-fx-font-family: 'Brush Script MT'");
        for (String s : player.getStatus().keySet()){
            Text status = new Text();
            if (s.startsWith("ST")){
                status.setText("Strenght "+s.substring(2)+" : "+player.getStatus().get(s)+" rounds left");
            }else if (s.startsWith("DE")){
                status.setText("Defense "+s.substring(2)+" : "+player.getStatus().get(s)+" rounds left");
            }else if (s.equals("poisoned")){
                status.setText("Poisoned : "+player.getStatus().get(s)+" rounds left");
            }
            listStatusPlayer.getChildren().add(status);
        }
        AnchorPane.setTopAnchor(listStatusPlayer, 0.0);
        AnchorPane.setLeftAnchor(listStatusPlayer, 0.0);
        listStatus.getChildren().add(listStatusPlayer);
        FlowPane listStatusMonster = new FlowPane(Orientation.HORIZONTAL);
        for (String s : monster.getStatus().keySet()){
            Text status = new Text();
            if (s.startsWith("ST")){
                status.setText("Strenght "+s.substring(2)+" : "+monster.getStatus().get(s)+" rounds left");
            }else if (s.startsWith("DE")){
                status.setText("Defense "+s.substring(2)+" : "+monster.getStatus().get(s)+" rounds left");
            }else if (s.equals("poisoned")){
                status.setText("Poisoned : "+monster.getStatus().get(s)+" rounds left");
            }
            listStatusMonster.getChildren().add(status);
        }
        AnchorPane.setTopAnchor(listStatusMonster, 0.0);
        AnchorPane.setRightAnchor(listStatusMonster, 0.0);
        listStatus.getChildren().add(listStatusMonster);
        pane.getChildren().add(listStatus);
        //endregion
        //region paneFighters
        AnchorPane paneFighters = new AnchorPane();
        Image imagePlayer = new Image(((ImageView)(player.getNode())).getImage().getUrl());
        ImageView imageViewPlayer = new ImageView(imagePlayer);
        AnchorPane.setTopAnchor(imageViewPlayer, 0.0);
        AnchorPane.setLeftAnchor(imageViewPlayer, 0.0);
        paneFighters.getChildren().add(imageViewPlayer);
        Image imageMonster = new Image(((ImageView)(monster.getNode())).getImage().getUrl());
        ImageView imageViewMonster = new ImageView(imageMonster);
        AnchorPane.setTopAnchor(imageViewMonster, 0.0);
        AnchorPane.setRightAnchor(imageViewMonster, 50.0);
        paneFighters.getChildren().add(imageViewMonster);
        paneFighters.setPrefHeight(pane.getHeight());
        paneFighters.setPrefWidth(pane.getWidth());
        System.out.println(pane.getWidth());
        pane.getChildren().add(paneFighters);
        //endregion
        //region paneTurn
        StackPane paneTurn = new StackPane();
        Text textwhoattacks = new Text("Your turn");
        textwhoattacks.setStyle("-fx-font-size: 40px;-fx-font-family: 'Brush Script MT'");
        paneTurn.setAlignment(Pos.CENTER);
        paneTurn.getChildren().add(textwhoattacks);
        pane.getChildren().add(paneTurn);
        //endregion
        //region paneDodge
        StackPane paneDodge = new StackPane();
        Text textDodgeSuccessfull = new Text("");
        textDodgeSuccessfull.setStyle("-fx-font-size: 40px;-fx-font-family: 'Brush Script MT'");
        paneDodge.setAlignment(Pos.CENTER);
        paneDodge.getChildren().add(textDodgeSuccessfull);
        pane.getChildren().add(paneDodge);
        //endregion
        //region gridButtonsChoiceAction
        GridPane gridButtonsChoiceAction = new GridPane();
        Button buttonUsePotion = new Button("Use potion");
        Button buttonDodge = new Button("Dodge");
        Button buttonAttack = new Button("Attack");
        //TODO To use item in fight
        //Button buttonUseOtherItems = new Button("Use other items");
        buttonUsePotion.setStyle("-fx-font-size: 30px;-fx-font-family: 'Brush Script MT'");
        buttonDodge.setStyle("-fx-font-size: 30px;-fx-font-family: 'Brush Script MT'");
        buttonAttack.setStyle("-fx-font-size: 30px;-fx-font-family: 'Brush Script MT'");
        //TODO To use item in fight
        //buttonUseOtherItems.setStyle("-fx-font-size: 30px;-fx-font-family: 'Brush Script MT'");
        gridButtonsChoiceAction.add(buttonUsePotion,1,0);
        gridButtonsChoiceAction.add(buttonDodge,2,0);
        gridButtonsChoiceAction.add(buttonAttack,3,0);
        //TODO To use item in fight
        //gridButtonsChoiceAction.add(buttonUseOtherItems,4,0);
        pane.getChildren().add(gridButtonsChoiceAction);
        //endregion
        //region gridPotions
        GridPane gridPotions = new GridPane();
        ScrollPane scrollGridPotions = new ScrollPane(gridPotions);
        int i=0;
        for (Item item : player.getInventory()){
            System.out.println(player.getInventory());
            if (item instanceof Potion){
                Button potion = new Button(item.getName());
                potion.setStyle("-fx-font-size: 20px;-fx-font-family: 'Brush Script MT'");
                ImageView imagePotion = new ImageView(((ImageView)(item.getNode())).getImage().getUrl());
                imagePotion.setFitHeight(50);
                imagePotion.setFitWidth(50);
                potion.setGraphic(imagePotion);
                gridPotions.add(potion,i,0);
                potion.setOnAction(actionEvent -> {
                    double playerAttack = player.attack(0,monster, item);
                    monster.defend(playerAttack);
                    scrollGridPotions.setVisible(false);
                    textDodgeSuccessfull.setText("");
                    delay(1000, () -> playerTurn.set(false));
                });
            }
            i++;
        }
        scrollGridPotions.setVisible(false);
        pane.getChildren().add(scrollGridPotions);
        //endregion

        //region gridItems
        //TODO To use item in fight
        /*GridPane gridItems = new GridPane();
        ScrollPane scrollGridItems = new ScrollPane(gridItems);
        int j=0;
        for (Item item : player.getInventory()){
            if (item instanceof Item){
                Button buttonItem = new Button(item.getName());
                buttonItem.setStyle("-fx-font-size: 20px;-fx-font-family: 'Brush Script MT'");
                ImageView imageItem = new ImageView(((ImageView)(item.getNode())).getImage().getUrl());
                imageItem.setFitHeight(50);
                imageItem.setFitWidth(50);
                buttonItem.setGraphic(imageItem);
                gridItems.add(buttonItem,j,0);
                buttonItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        double playerAttack = player.attack(3,monster, item);
                        monster.defend(playerAttack);
                        scrollGridItems.setVisible(false);
                        textDodgeSuccessfull.setText("");
                        delay(1000, () -> playerTurn.set(false));
                    }
                });
            }
            j++;
        }
        ScrollPane scrollGridItems = new ScrollPane(gridItems);
        scrollGridItems.setVisible(false);
        pane.getChildren().add(scrollGridItems);*/
        //endregion

        Scene scene = new Scene(new ScrollPane(pane));
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.initOwner(primaryStage);
        stage.widthProperty().addListener((observableValue, number, t1) -> {
            double width = stage.getWidth();
            System.out.println(width);
            pane.setPrefWidth(width);
            paneFighters.setPrefWidth(width);
            buttonUsePotion.setPrefWidth(width/3);
            buttonDodge.setPrefWidth(width/3);
            buttonAttack.setPrefWidth(width/3);
            //TODO To use item in fight - put /4 instead of /3
            //buttonUseOtherItems.setPrefWidth(width/4);
        });
        stage.heightProperty().addListener((observableValue, number, t1) -> pane.setPrefHeight(stage.getHeight()));

        buttonUsePotion.setOnAction(actionEvent -> {
            scrollGridPotions.setVisible(true);
        });
        buttonAttack.setOnAction(actionEvent -> {
            scrollGridPotions.setVisible(false);
            double playerAttack = player.attack(1,monster,null);
            monster.defend(playerAttack);
            gridButtonsChoiceAction.setVisible(false);
            textDodgeSuccessfull.setText("");
            delay(2000, () -> playerTurn.set(false));

        });
        buttonDodge.setOnAction(actionEvent -> {
            scrollGridPotions.setVisible(false);
            double playerAttack = player.attack(2,monster,null);
            monster.defend(playerAttack);
            if (player.isDodge()){
                textDodgeSuccessfull.setText("You succeeded to dodge the monster 's attack");
            }else{
                textDodgeSuccessfull.setText("You didn't succeed to dodge the monster 's attack");
            }
            gridButtonsChoiceAction.setVisible(false);
            delay(2000, () -> playerTurn.set(false));
        });
        //TODO to use items in fight
        /*buttonUseOtherItems.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                scrollGridItems.setVisible(true);
            }
        });*/
        playerTurn.addListener((observableValue, aBoolean, t1) -> {
            textwhoattacks.setText(playerTurn.get()?"Your turn":"Monster's turn");
            gridButtonsChoiceAction.setVisible(playerTurn.get());
            scrollGridPotions.setVisible(false);
            //gridItems.setVisible(false); //TODO to use items in fight
            if (!playerTurn.get() && monster.getLifePoints()>0){
                if (!player.isDodge()) {
                    double attackMonster = monster.attack(player);
                    if (attackMonster>0) {
                        player.defend(attackMonster);
                    }
                }
                delay(2000, () -> playerTurn.set(true));
            }
        });

        player.getLifePointsProperty().addListener((observableValue, number, t1) -> lifesPlayer.setWidth(player.getLifePoints()*15));
        monster.getLifePointsProperty().addListener((observableValue, number, t1) -> {
            lifesMonster.setWidth(monster.getLifePoints()*15);
            if (monster.getLifePoints()<=0) {
                System.out.println("Gagné");
                stage.close();
            }
        });
        player.getNumberStatusProperty().addListener((observableValue, number, t1) -> {
            listStatusPlayer.getChildren().clear();
            for (String s : player.getStatus().keySet()){
                Text status = new Text();
                if (s.startsWith("ST")){
                    status.setText("Strenght "+s.substring(2)+" : "+player.getStatus().get(s)+" rounds left");
                }else if (s.startsWith("DE")){
                    status.setText("Defense "+s.substring(2)+" : "+player.getStatus().get(s)+" rounds left");
                }else if (s.equals("poisoned")){
                    status.setText("Poisoned : "+player.getStatus().get(s)+" rounds left");
                }
                listStatusPlayer.getChildren().add(status);
            }
        });
        player.getSizeInventoryProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                gridPotions.getChildren().clear();
                int i=0;
                for (Item item : player.getInventory()){
                    System.out.println(player.getInventory());
                    if (item instanceof Potion){
                        Button potion = new Button(item.getName());
                        potion.setStyle("-fx-font-size: 20px;-fx-font-family: 'Brush Script MT'");
                        ImageView imagePotion = new ImageView(((ImageView)(item.getNode())).getImage().getUrl());
                        imagePotion.setFitHeight(50);
                        imagePotion.setFitWidth(50);
                        potion.setGraphic(imagePotion);
                        gridPotions.add(potion,i,0);
                        potion.setOnAction(actionEvent -> {
                            double playerAttack = player.attack(0,monster, item);
                            monster.defend(playerAttack);
                            scrollGridPotions.setVisible(false);
                            textDodgeSuccessfull.setText("");
                            delay(1000, () -> playerTurn.set(false));
                        });
                    }
                    i++;
                }
                //TODO To use item in fight
                /*gridItems.getChildren().clear();
                int j=0;
                for (Item item : player.getInventory()){
                    if (item instanceof Item){
                        Button buttonItem = new Button(item.getName());
                        buttonItem.setStyle("-fx-font-size: 20px;-fx-font-family: 'Brush Script MT'");
                        ImageView imageItem = new ImageView(((ImageView)(item.getNode())).getImage().getUrl());
                        imageItem.setFitHeight(50);
                        imageItem.setFitWidth(50);
                        buttonItem.setGraphic(imageItem);
                        gridItems.add(buttonItem,j,0);
                        buttonItem.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                double playerAttack = player.attack(3,monster, item);
                                monster.defend(playerAttack);
                                scrollGridItems.setVisible(false);
                                textDodgeSuccessfull.setText("");
                                delay(1000, () -> playerTurn.set(false));
                            }
                        });
                    }
                    j++;
                }*/
            }
        });
        monster.getNumberStatusProperty().addListener((observableValue, number, t1) -> {
            listStatusMonster.getChildren().clear();
            for (String s : monster.getStatus().keySet()){
                Text status = new Text();
                if (s.startsWith("ST")){
                    status.setText("Strenght : "+s.substring(2));
                }else if (s.startsWith("DE")){
                    status.setText("Defense : "+s.substring(2));
                }else if (s.equals("poisoned")){
                    status.setText("Poisoned");
                }
                listStatusMonster.getChildren().add(status);
            }
        });
        monster.messageAttackProperty().addListener((observableValue, s, t1) -> {
            System.out.println(monster.getMessageAttack());
            if (!monster.getMessageAttack().isEmpty()) {
                System.out.println("hello");
                textDodgeSuccessfull.setVisible(true);
                textDodgeSuccessfull.setText(monster.getMessageAttack());
                monster.setMessageAttack("");
            }
        });
        textDodgeSuccessfull.textProperty().addListener((observableValue, s, t1) -> {
            System.out.println("TEXT="+t1);
            System.out.println("TEXT CHANGE");
        });

        stage.setOnCloseRequest(event -> {
            // Empêcher la fermeture en consommant l'événement
            if (monster.getLifePoints()>0 && player.getLifePoints()>0) {
                event.consume();
            }
        });
        stage.showAndWait();
    }
    private void closeWindowEvent(WindowEvent event) {
        System.out.println("Window close request ...");

    }
    //endregion

    //region createLifeBar ; delay
    public GridPane createLifeBar(){
        GridPane lifeBar = new GridPane();
        lifeBar.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 3px; -fx-max-height: 20");
        for (int i=0;i<10;i++){
            Rectangle r = new Rectangle();
            r.setHeight(20);
            r.setWidth(15);
            r.setFill(Color.TRANSPARENT);
            lifeBar.add(r,i,0);
        }
        return lifeBar;
    }

    public static void delay(long millis, Runnable continuation) {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try { Thread.sleep(millis); }
                catch (InterruptedException e) { }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> continuation.run());
        new Thread(sleeper).start();
    }
    //endregion

    public static void main(String[] args) {
        launch();
    }
}
