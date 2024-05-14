package Classes.Monster;

import Classes.Item.Item;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class Slime extends Monster{
    public Slime(GridPane g, String name, int lifePoints, int force, int defense, ArrayList<Item> inventory, int x, int y) {
        super(g, name, lifePoints, force, defense, inventory, x, y);
    }

    @Override
    public void chooseAttack(int numRound) {
        //TODO Determiner quelle stratégie ce type de monstres va adopter
    }
}