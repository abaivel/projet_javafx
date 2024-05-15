package Classes.Item.NotConsumableItem.Weapon;
import Classes.World.World;

public class Sword extends Weapon{
    public Sword(World w, String name, boolean dropped, int x, int y, double damage, int price) {
        super(w, name, dropped,x, y,damage, price);
    }
}
