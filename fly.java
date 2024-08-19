import java.awt.*;

public class fly
{
    private int x, y;
    private boolean alive;
    private boolean isGolden;

    //CONSTRUCTOR
    public fly(int x, int y){
        this.x = x;
        this.y = y;
        alive = false;
        isGolden = false;
    }

    //MOVES FLY
    public void move(int amount){
        x = x + amount;
    }

    //CHANGES VERTICAL POSITION
    public void moveHeight(){
        y = (int)(Math.random() * 200) + 110;
    }
    
    //SETS ALIVE ATTRIBUTE TO TRUE
    public void aliveYes(){
        alive = true;
    }
    
    //SETS ALIVE ATTRIBUTE TO FALSE
    public void aliveNo(){
        alive = false;
    }

    //RETURNS X POSITION
    public int getX(){
        return x;
    }
    
    //RETURNS Y POSITION
    public int getY(){
        return y;
    }
    
    //RETURNS ALIVE ATTRIBUTE
    public boolean life(){
        return alive;
    }

    //SETS ISGOLDEN ATTRIBUTE BASED ON PARAMETER
    public void setGoldStatus(boolean isGold){
        isGolden = isGold;
    }

    //RETURNS WHETHER OR NOT FLY IS GOLDEN
    public boolean getGoldStatus(){
        return isGolden;
    }

    //BECOMES GOLD
    public void transform(){
        int x = (int)(Math.random() * 10);
        if(isGolden){
            isGolden = !isGolden;
        } else if(x == 9){
            isGolden = true;
        }
    }
    
    //PAINTS FLY
    public void paint(Graphics g){
        if (alive ==  true){

            //BODY
            if(!isGolden){
                g.setColor(new Color(57, 59, 63));
            } else{
                g.setColor(new Color(252, 201, 61));
            }
            g.fillRect(x, y, 85, 85);

            //WINGS
            if(!isGolden){
                g.setColor(Color.GRAY);
            } else{
                g.setColor(new Color(163, 132, 3));
            }
            g.fillRect(x+50, y-10, 90, 65);
            g.fillRect(x-50, y-10, 90, 65);
        }
    }
}
