package Classes.Monster;

import Classes.Item.Item;
import javafx.scene.layout.GridPane;

import java.util.List;

public class Looter extends Monster{

    //region Constructor
    public Looter(GridPane g, String name, int lifePoints, int force, int defense, List<Item> inventory, int x, int y) {
        super(g, name, lifePoints, force, defense, inventory, x, y);
    }
    //endregion

    @Override
    public void chooseAttack(int numRound) {
        //TODO Determiner quelle stratégie ce type de monstres va adopter
        //every 3 rounds they can steal an item
    }
}