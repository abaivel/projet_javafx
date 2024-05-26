package Classes.NPC;

import Classes.World.World;

//NPC that is useless... He's nice tho -> Says Welcome and Goodbye
public class UselessPerson extends NPC{

    //region Constructor with all parameters
    public UselessPerson(World w, int x, int y,String name, double money, String urlImage) {
        super(w, x,y,name, money, urlImage);
    }
    //endregion

    //region Dialog
    //He welcomes, he doesn't do much more since he's useless
    public String welcome(){
        return "Welcome eheh !";
    }

    //He can also say goodbye ! Average man experience
    public String goodbye(){
        return "Goodbye eheh !";
    }
    //endregion
}