package Fight;

import Classes.Item.ConsumableItem.Potion;
import Classes.Item.Item;
import Classes.Monster.Looter;
import Classes.Monster.Monster;
import Classes.Monster.Slime;
import Classes.Player.Player;
import javafx.scene.effect.ImageInput;

import java.util.ArrayList;
import java.util.Scanner;

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

    public void launchFight(){
        System.out.println("Beginning of the fight !");
        int i=1;
        while(this.getPlayer().getLP() > 0 || this.getMonster().getLifePoints() > 0){
            System.out.println("round" + i + "\n");
            i++;
            System.out.println(this.getPlayer() +""+ this.getMonster());
            //Player acts
            Scanner sc = new Scanner(System.in);
            System.out.println("Select your action between 0 and 2 :");
            int action = sc.nextInt();
            this.setPlayerAttack(this.getPlayer().attack(action,this.getMonster()));    //stocking the attack done by the player

            //Monster defends himself
            this.getMonster().defend(this.getPlayerAttack());

            //Monster acts
            this.setMonsterAttack(this.getMonster().attack(this.getPlayer()));

            //Player defends himself
            this.getPlayer().defend(this.getMonsterAttack());
        }
    }

    public static void main(String[] args) {
        ArrayList<Item> items = new ArrayList<>();
        Player player = new Player(null, 5,"Mighty fighter",10,3,2,0,0);
        Slime slime = new Slime(null, "slimy", 5,3,2,items,0,0,0);

        Fight fight = new Fight(player, slime);
        fight.launchFight();
    }
}
