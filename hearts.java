import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class hearts
{
    private int x, y;
    private boolean visible = true;
    private boolean change = false;
    BufferedImage image = null;
    BufferedImage image2 = null;
    File input = null;
    File input2 = null;

    public hearts(int x, int y){
        this.x = x;
        this.y = y;
        try {
            input = new File("images/lily.png");
            input2 = new File("images/lily-dark.png");
        } catch (Exception e) {}
    } 
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public void show(){
        visible = true;
    }

    public void hide(){
        visible = false;
    }

    public void sprig(){
        change = !change;
    }

    public boolean getSprig(){
        return change;
    }

    public void paint(Graphics g){
        if(visible){
            try {
                image = ImageIO.read(input);
            } catch (Exception e){}
        } else {
            try {
                image = ImageIO.read(input2);
            } catch (Exception e){}
        }

        g.drawImage(image, x, y, null);
    }
}
