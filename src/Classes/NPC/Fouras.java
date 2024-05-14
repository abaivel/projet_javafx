package Classes.NPC;

import Classes.NPC.NPC;
import Classes.World.Position;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Fouras extends NPC {
    private Map<String, String> enigma;

    //Constructor with all parameters
    public Fouras(GridPane g, String name, double money,int x, int y) {
        super(g,name, money, x, y);
        this.enigma = new HashMap<String, String>();
        this.enigma.put("I travel the world without moving an inch,\nConnecting continents with a mere blink.\nI'm not alive, but I can grow,\nWho am I, do you know?\n","Internet");
        this.enigma.put("I have a tail and two flat ears. I move with no feet. What am I ?","mouse");
        this.enigma.put("A box to anywhere. Just watch for my glare. What am I ?","monitor");
        this.enigma.put("25 years old, but only turned 10. What am I ?","windows");
    }

    //Default constructor
    public Fouras(String name) {
        this(null, name,100,0,0);
    }

    //Getters and Setters
    public Map<String, String> getEnigma() {return enigma;}
    public void setEnigma(Map<String, String> enigma) {this.enigma = enigma;}

    //TO DO : add a reward ? Give some of his money ? Example he gives off 10% of his total money
    //Function to tell an enigma to the player
    public boolean tellEnigma(){
        String[] keys = this.getEnigma().keySet().toArray(new String[0]);   //Puts the keySet into a List of Strings
        int i = (int) (Math.random() * keys.length);                        //Generates a random int between 0 and the list's length
        String randomKey = keys[i];                                         //Random key in the keySet
        System.out.println(randomKey);
        String answer = this.answerEnigma().toLowerCase();                  //Calls other function to get the player's answer
        //Use of toLowerCase to get same case
        if(answer.equals(this.getEnigma().get(randomKey))){
            System.out.println("Good answer");
            return true;
        }else{
            System.out.println("Wrong answer");
            return false;
        }

    }

    //Function called in TellEnigma() to get the answer via input
    public String answerEnigma(){
        Scanner input = new Scanner(System.in);                         //Create a Scanner object for the player's input
        System.out.println("Enter your answer to the enigma");
        String answer = input.nextLine();                               //Read user input
        System.out.println("Your answer is: " + answer);                //Output user input
        return answer;
    }

    public static void main(String[] args) {
        Fouras f=new Fouras(null,"Fouras",100,0,0);
        System.out.println(f);
        f.tellEnigma();

    }


}