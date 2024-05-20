package com.game.projet_javafx;

import Classes.Item.ConsumableItem.Potion;
import Classes.Item.Item;
import Classes.Monster.Monster;
import Classes.Player.Player;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FightApplication extends Application {
    private Player player;
    private Monster monster;
    private BooleanProperty playerTurn;
    private Stage primaryStage;

    public FightApplication(Player player, Monster monster, Stage primaryStage) {
        this.player = player;
        this.monster = monster;
        this.playerTurn=new SimpleBooleanProperty(true);
        this.primaryStage=primaryStage;
    }

    @Override
    public void start(Stage stage) throws Exception {
        FlowPane pane = new FlowPane(Orientation.VERTICAL);
        //region paneLifeBars
        AnchorPane paneLifeBars = new AnchorPane();
        GridPane lifeBarPlayer = createLifeBar();
        Rectangle lifesPlayer = new Rectangle();
        lifesPlayer.setHeight(20);
        lifesPlayer.setWidth(player.getLP()*15);
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
                status.setText("Strenght : "+s.substring(2));
            }else if (s.startsWith("DE")){
                status.setText("Defense : "+s.substring(2));
            }else if (s.equals("poisoned")){
                status.setText("Poisoned");
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
                status.setText("Strenght : "+s.substring(2));
            }else if (s.startsWith("DE")){
                status.setText("Defense : "+s.substring(2));
            }else if (s.equals("poisoned")){
                status.setText("Poisoned");
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
        Text textDodgeSuccessfull = new Text("You didn't succeed to dodge the monster 's attack");
        textDodgeSuccessfull.setStyle("-fx-font-size: 40px;-fx-font-family: 'Brush Script MT'");
        textDodgeSuccessfull.setVisible(false);
        paneDodge.setAlignment(Pos.CENTER);
        paneDodge.getChildren().add(textDodgeSuccessfull);
        pane.getChildren().add(paneDodge);
        //endregion
        //region gridButtonsChoiceAction
        GridPane gridButtonsChoiceAction = new GridPane();
        Button buttonUsePotion = new Button("Use potion");
        Button buttonDodge = new Button("Dodge");
        Button buttonAttack = new Button("Attack");
        buttonUsePotion.setStyle("-fx-font-size: 30px;-fx-font-family: 'Brush Script MT'");
        buttonDodge.setStyle("-fx-font-size: 30px;-fx-font-family: 'Brush Script MT'");
        buttonAttack.setStyle("-fx-font-size: 30px;-fx-font-family: 'Brush Script MT'");
        gridButtonsChoiceAction.add(buttonUsePotion,1,0);
        gridButtonsChoiceAction.add(buttonDodge,2,0);
        gridButtonsChoiceAction.add(buttonAttack,3,0);
        pane.getChildren().add(gridButtonsChoiceAction);
        //endregion
        //region gridPotions
        GridPane gridPotions = new GridPane();
        int i=0;
        for (Item item : player.getInventory()){
            if (item instanceof Potion){
                Button potion = new Button(item.getName());
                potion.setStyle("-fx-font-size: 20px;-fx-font-family: 'Brush Script MT'");
                ImageView imagePotion = new ImageView(((ImageView)(item.getNode())).getImage().getUrl());
                imagePotion.setFitHeight(50);
                imagePotion.setFitWidth(50);
                potion.setGraphic(imagePotion);
                gridPotions.add(potion,i,0);
                potion.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        double playerAttack = player.attack(0,monster, (Potion) item);
                        monster.defend(playerAttack);
                        delay(1000, () -> playerTurn.set(false));
                    }
                });
            }
            i++;
        }
        ScrollPane scrollGridPotions = new ScrollPane(gridPotions);
        scrollGridPotions.setVisible(false);
        pane.getChildren().add(scrollGridPotions);
        //endregion

        Scene scene = new Scene(new ScrollPane(pane));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.initOwner(primaryStage);
        stage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                double width = stage.getWidth();
                System.out.println(width);
                pane.setPrefWidth(width);
                paneFighters.setPrefWidth(width);
                buttonUsePotion.setPrefWidth(width/3);
                buttonDodge.setPrefWidth(width/3);
                buttonAttack.setPrefWidth(width/3);
            }
        });
        stage.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                pane.setPrefHeight(stage.getHeight());
            }
        });
        /*System.out.println(stage.getWidth());

        //region setHeight and setWidth
        pane.setPrefWidth(stage.getWidth());
        pane.setPrefHeight(stage.getHeight());
        //paneFighters.setPrefWidth(pane.getWidth());
        buttonUsePotion.setPrefWidth(stage.getWidth()/3);
        buttonDodge.setPrefWidth(stage.getWidth()/3);
        buttonAttack.setPrefWidth(stage.getWidth()/3);*/
        //endregion

        buttonUsePotion.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                scrollGridPotions.setVisible(true);
                textDodgeSuccessfull.setVisible(false);
            }
        });
        buttonAttack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                textDodgeSuccessfull.setVisible(false);
                System.out.println("ici");
                double playerAttack = player.attack(1,monster,null);
                monster.defend(playerAttack);
                delay(2000, () -> playerTurn.set(false));

            }
        });
        buttonDodge.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                double playerAttack = player.attack(2,monster,null);
                monster.defend(playerAttack);
                if (player.isDodge()){
                    textDodgeSuccessfull.setText("You succeeded to dodge the monster 's attack");
                }else{
                    textDodgeSuccessfull.setText("You didn't succeed to dodge the monster 's attack");
                }
                textDodgeSuccessfull.setVisible(true);
                delay(2000, () -> playerTurn.set(false));
            }
        });
        playerTurn.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                textwhoattacks.setText(playerTurn.get()?"Your turn":"Monster's turn");
                gridButtonsChoiceAction.setVisible(playerTurn.get());
                gridPotions.setVisible(false);
                if (!playerTurn.get() && monster.getLifePoints()>0 && !player.isDodge()){
                    System.out.println("Time for the monster to fight");
                    double attackMonster = monster.attack(player);
                    player.defend(attackMonster);
                    playerTurn.set(true);
                }
            }
        });

        player.getLPProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                lifesPlayer.setWidth(player.getLP()*15);
            }
        });
        monster.lifePointsProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                lifesMonster.setWidth(monster.getLifePoints()*15);
                if (monster.getLifePoints()<=0) {
                    System.out.println("Gagné");
                    stage.close();
                }
            }
        });
        player.numberStatusProperty().addListener((observableValue, number, t1) -> {
            listStatusPlayer.getChildren().clear();
            for (String s : player.getStatus().keySet()){
                Text status = new Text();
                if (s.startsWith("ST")){
                    status.setText("Strenght : "+s.substring(2));
                }else if (s.startsWith("DE")){
                    status.setText("Defense : "+s.substring(2));
                }else if (s.equals("poisoned")){
                    status.setText("Poisoned");
                }
                listStatusPlayer.getChildren().add(status);
            }
        });
        monster.numberStatusProperty().addListener((observableValue, number, t1) -> {
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
        stage.showAndWait();
    }
    public static void main(String[] args) {
        launch();
    }

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

}
