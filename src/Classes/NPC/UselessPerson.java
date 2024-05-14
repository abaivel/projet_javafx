package Classes.NPC;

import Classes.World.Position;

public class UselessPerson extends NPC{
    //Constructor with all parameters
    public UselessPerson(String name, double money, Position position) {
        super(name, money, position);
    }

    //Default constructor
    public UselessPerson() {
        this("Aurélien",100,new Position(0,0));      //Because Aurelien is useless :p
    }

    //He welcomes, he doesn't do much more since he's useless
    public void welcome(){
        System.out.println("Welcome eheh !");
    }

    //He can also say goodbye ! Average man experience
    public void goodbye(){
        System.out.println("Goodbye eheh !");
    }
}