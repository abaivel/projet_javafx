package Classes.Item.ConsumableItem;

import Classes.Item.Item;
import Classes.World.World;
import javafx.scene.input.MouseButton;

//Bomb explodes when primary clicked on after being left on the ground --> destroy decor nearby (except door and river), kill Player
public class Bomb extends Item {

    //region Constructor
    public Bomb(World w, int x, int y, String name, boolean dropped, int price, String urlImage) {
        super(w, x, y, name, dropped, price, urlImage);

        this.getNode().setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton() == MouseButton.PRIMARY && this.isDropped()){
                w.destroysRadiusBomb(this.getPosition().getX(), this.getPosition().getY());
            }
        });
    }
    //endregion


}
