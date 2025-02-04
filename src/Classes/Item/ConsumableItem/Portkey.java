package Classes.Item.ConsumableItem;

import Classes.Item.Item;
import Classes.World.DecorItem.NotWalkThroughDecorItem.Door;
import Classes.World.Position;
import Classes.World.World;

import java.util.List;
import java.util.Random;

public class Portkey extends Item {
    public Portkey(World w, int x, int y, String name, int price, boolean dropped, String urlImage) {
        super(w, x, y, name, price, dropped, urlImage);
    }
    public Position getRandomPosition(){
        Random random = new Random();
        /*int x;
        int y;
        Random random = new Random();
        do {
            x=random.nextInt(Position.COLUMNS);
            y=random.nextInt(Position.ROWS);
        }while (!world.CanGoThere(x,y));
        return new Position(x,y);*/
        List<Door> listDoors = world.getListDoor();
        Door randomDoor = listDoors.get(random.nextInt(listDoors.size()));
        int xDoor = randomDoor.getPosition().getX();
        int yDoor = randomDoor.getPosition().getY();
        int xDoorMinus1 = xDoor-1;
        int xDoorPlus1 = xDoor+1;
        int yDoorMinus1 = yDoor-1;
        int yDoorPlus1 = yDoor+1;
        int x;
        int y;
        do{
            x=random.nextInt(xDoorPlus1+1-xDoorMinus1)+xDoorMinus1;
            y=random.nextInt(yDoorPlus1+1-yDoorMinus1)+yDoorMinus1;
        }while (!world.CanGoThere(x,y));
        return new Position(x,y);
    }
}
