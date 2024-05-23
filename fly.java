import java.awt.*;
import java.awt.Graphics;

public class fly
{
    private int x, y;
    private boolean alive;
    private boolean isGolden;
    public fly(int x, int y){
        this.x = x;
        this.y = y;
        alive = false;
        isGolden = false;
    }

    public void moveRight(int distance){
        x = x + distance;
    }

    public void moveLeft(int distance){
        x = x - distance;
    }

    public void move(int amount){
        x = x + amount;
    }

    public void moveHeight(){
        y = (int)(Math.random() * 200) + 100;
    }
    
    public void aliveYes(){
        alive = true;
    }
    
    public void aliveNo(){
        alive = false;
    }

    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public boolean life(){
        return alive;
    }

    public void setGoldStatus(boolean isGold){
        isGolden = isGold;
    }
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
    
    public void paint(Graphics g){
        if (alive ==  true){

            //BODY
            if(!isGolden){
                g.setColor(Color.GRAY);
            } else{
                g.setColor(new Color(252, 201, 61));
            }
            g.fillRect(x, y, 85, 85);

            //WINGS
            if(!isGolden){
                g.setColor(Color.LIGHT_GRAY);
            } else{
                g.setColor(new Color(163, 132, 3));
            }
            g.fillRect(x+50, y-10, 90, 65);
            g.fillRect(x-50, y-10, 90, 65);
        }
    }
}
