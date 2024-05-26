package Classes.Item.NotConsumableItem.Weapon;
import Classes.World.World;

//A type of weapon
public class Sword extends Weapon{

    //region Constructor
    public Sword(World w, int x, int y, String name, int price, boolean dropped, String urlImage, double damage) {
        super(w,x, y, name, price, dropped,urlImage,damage);
    }
    //endregion
}
