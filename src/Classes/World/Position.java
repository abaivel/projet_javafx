package Classes.World;

public class Position {
    //Position on the map is a grid from y:0 to 20 * x : 0 to 50
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //Getters and Setters
    public int getX() {return x;}
    public int getY() {return y;}
    public void setX(int x) {this.x = x;}
    public void setY(int y) {this.y = y;}

    //ToString function to print
    public String toString(){
        return "(" + this.getX() + ", " + this.getY() + ")";
    }

    //Equals function
    public boolean equals(Object o){
        if(o instanceof Position){
            Position p = (Position)o;
            return p.getX() == this.getX() && p.getY() == getY();
        }
        return false;
    }

}