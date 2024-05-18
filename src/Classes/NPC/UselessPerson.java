package Classes.NPC;

import Classes.World.Position;
import Classes.World.World;

public class UselessPerson extends NPC{
    //Constructor with all parameters
    public UselessPerson(World w,String name, double money, int x, int y, String urlImage) {
        super(w,name, money, x,y, urlImage);
    }

    //Default constructor
    public UselessPerson() {
        this(null,"Aurélien",100,0,0,"");      //Because Aurelien is useless :p
    }

    //He welcomes, he doesn't do much more since he's useless
    public String welcome(){
        return "Welcome eheh !";
    }

    //He can also say goodbye ! Average man experience
    public String goodbye(){
        return "Goodbye eheh !";
    }
}