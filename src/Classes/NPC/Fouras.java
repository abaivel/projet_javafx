package Classes.NPC;

import Classes.Player.Player;
import Classes.World.World;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

//NPC that tells enigmas to the player and reward them with some coins for the right answer
public class Fouras extends NPC {

    //region Fouras's attributes
    private Map<String, String> enigma;
    //endregion

    //region Constructor with all parameters
    public Fouras(World w, String name, double money, int x, int y, String urlImage) {
        super(w,name, money, x, y, urlImage);
        this.enigma = new HashMap<>();
        this.enigma.put("I travel the world without moving an inch,\nConnecting continents with a mere blink.\nI'm not alive, but I can grow,\nWho am I, do you know?\n","internet");
        this.enigma.put("I have a tail and two flat ears. I move with no feet. What am I ?","mouse");
        this.enigma.put("A box to anywhere. Just watch for my glare. What am I ?","monitor");
        this.enigma.put("25 years old, but only turned 10. What am I ?","windows");
    }
    //endregion

    //region Getters and Setters
    public Map<String, String> getEnigma() {return enigma;}
    public void setEnigma(Map<String, String> enigma) {this.enigma = enigma;}
    //endregion

    //region Function to tell an enigma to the player
    public String tellEnigma(){
        String[] keys = this.getEnigma().keySet().toArray(new String[0]);   //Puts the keySet into a List of Strings
        int i = (int) (Math.random() * keys.length);                        //Generates a random int between 0 and the list's length
        return keys[i];                                                     //Random key in the keySet
    }

    //Function to check to player's answer to the question
    public boolean checkAnswer(Player p, String question, String answer){
        if(answer.equals(this.getEnigma().get(question))){
            p.setMoney(p.getMoney()+0.1*this.getMoney());
            return true;
        }else{
            return false;
        }
    }
    //endregion

    //region Function called in TellEnigma() to get the answer via input --> back not front
    public String answerEnigma(){
        Scanner input = new Scanner(System.in);                         //Create a Scanner object for the player's input
        System.out.println("Enter your answer to the enigma");
        String answer = input.nextLine();                               //Read user input
        System.out.println("Your answer is: " + answer);                //Output user input
        return answer;
    }
    //endregion

}