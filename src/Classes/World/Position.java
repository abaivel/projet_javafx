package Classes.World;

//Position on the grid for all GameObjects
public class Position {

    //TODO comment the size of the grid
    //region Constants
    public static final Integer HEIGHT = 675;
    public static final Integer WIDTH = 1500;
    public static final Integer ROWS = 18;
    public static final Integer COLUMNS=40;
    //endregion

    //region Attributes
    private int x;
    private int y;
    //endregion

    //region Constructor
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    //endregion

    //region Getters and Setters
    public int getX() {return x;}
    public int getY() {return y;}
    public void setX(int x) {this.x = x;}
    public void setY(int y) {this.y = y;}
    //endregion

    //region ToString function to print
    public String toString(){
        return "(" + this.getX() + ", " + this.getY() + ")";
    }
    //endregion

    //region Equals function
    public boolean equals(Object o){
        if(o instanceof Position){
            Position p = (Position)o;
            return p.getX() == this.getX() && p.getY() == getY();
        }
        return false;
    }
    //endregion

}