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

    public hearts(int x, int y){
        this.x = x;
        this.y = y;
        try {
            File input = new File("images/lily.png");
            image = ImageIO.read(input);
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
            g.drawImage(image, x, y, null);
        }
    }
}
