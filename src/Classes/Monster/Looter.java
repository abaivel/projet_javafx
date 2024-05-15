package Classes.Monster;

import Classes.Item.Item;
import Classes.World.World;

import java.util.ArrayList;
import java.util.List;

public class Looter extends Monster{
    public Looter(World w, String name, int lifePoints, int force, int defense, ArrayList<Item> inventory, int x, int y) {
        super(w, name, lifePoints, force, defense, inventory, x, y);
    }

    @Override
    public void chooseAttack(int numRound) {
        //TODO Determiner quelle stratégie ce type de monstres va adopter
    }
}