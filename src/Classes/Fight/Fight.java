package Classes.Fight;

import Classes.Item.ConsumableItem.Key;
import Classes.Item.ConsumableItem.Potion;
import Classes.Item.Item;
import Classes.Monster.Looter;
import Classes.Monster.Monster;
import Classes.Monster.Slime;
import Classes.Player.Player;
import javafx.scene.effect.ImageInput;

import java.util.ArrayList;
import java.util.Scanner;

//For monsters and Players to fight
//Player can use items, use potions, try to dodge and attack
//Monsters can do special attacks and normal attacks
public class Fight {

    //region Fight's attributes
    Player player;
    Monster monster;
    double playerAttack;
    double monsterAttack;
    //endregion

    //region Constructor
    public Fight(Player player, Monster monster) {
        this.player = player;
        this.monster = monster;
        this.playerAttack = 0;
        this.monsterAttack = 0;
    }
    //endregion

    //region Getters and Setters
    public Player getPlayer() {return player;}
    public Monster getMonster() {return monster;}
    public double getPlayerAttack(){return playerAttack;}
    public double getMonsterAttack() {return monsterAttack;}

    public void setPlayerAttack(double attack){this.playerAttack = attack;}
    public void setMonsterAttack(double attack){this.monsterAttack = attack;}
    //endregion

    //region Functions that does all the fight sequence
    public void launchFight(){
        System.out.println("Beginning of the fight !");
        int i=1;
        while(this.getPlayer().getLP() > 0 && this.getMonster().getLifePoints() > 0){
            System.out.println("round" + i + "\n");
            i++;
            System.out.println(this.getPlayer() +""+ this.getMonster());
            //Player acts
            //TODO : want to do if 0 and don't have potion -> do while ????
            Scanner sc = new Scanner(System.in);
            System.out.println("Select your action between 0 and 2 :\n0->uses potion\n1->attack\n2->dodge\n");
            int action = sc.nextInt();
            this.setPlayerAttack(this.getPlayer().attack(action,this.getMonster(),null));    //stocking the attack done by the player

            //Monster defends himself
            this.getMonster().defend(this.getPlayerAttack());

            //Monster acts
            this.setMonsterAttack(this.getMonster().attack(this.getPlayer()));

            //Player defends himself
            this.getPlayer().defend(this.getMonsterAttack());
        }
        if (this.getPlayer().getLP()<=0){
            System.out.println("Player has lost");
        }
        if (this.getMonster().getLifePoints()<=0){
            System.out.println("Monster has lost");
        }
    }
    //endregion

}
