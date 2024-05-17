package Fight;

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
        while(this.getPlayer().getLP() > 0 && this.getMonster().getLifePoints() > 0){
            System.out.println("round" + i + "\n");
            i++;
            System.out.println(this.getPlayer() +""+ this.getMonster());
            //Player acts
            //TODO : want to do if 0 and don't have potion -> do while ????
            Scanner sc = new Scanner(System.in);
            System.out.println("Select your action between 0 and 2 :\n0->uses potion\n1->dodges\n2->attacks\n");
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

    //TODO : pv monstre baisse pas ; sort pas du while ; revoir cooldown monstre
    //TODO : passer potion en used quand used
    public static void main(String[] args) {
        ArrayList<Item> items = new ArrayList<>();
        ArrayList<Item> itemsMT = new ArrayList<>();
        Potion potion = new Potion(null,0,0,"WuawPotion",false,"ST+20",10,3);
        Potion potion2 = new Potion(null,0,0,"DEFFFFPotion",false,"DE+20",10,3);
        Potion potion3 = new Potion(null,0,0,"DEF----Potion",false,"DE-20",10,3);
        Potion potion4 = new Potion(null,0,0,"MEGADEFFFPotion",false,"DE+40",10,3);
        Key key = new Key(null,0,0,"Keyyy:0000",false,"GREEN",4);
        items.add(potion);
        items.add(potion2);
        items.add(potion3);
        items.add(key);
        items.add(potion4);
        Player player = new Player(null, 5,"Mighty fighter",10,3,2,0,0);
        player.setInventory(items);
        Slime slime = new Slime(null, "slimy", 5,3,2,itemsMT,0,0,0);

        Fight fight = new Fight(player, slime);
        fight.launchFight();
    }
    //TODO : debugg use potion --> doesn't apply any status to player or monster
}
