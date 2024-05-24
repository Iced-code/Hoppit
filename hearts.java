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
    File lily = null;
    File lily_dark = null;

    public hearts(int x, int y){
        this.x = x;
        this.y = y;
        try {
            lily = new File("images/lily2.png");
            lily_dark = new File("images/lily2-dark.png");
        } catch (Exception e) {}
    } 

    public hearts(int x, int y, String color_image, String dark_image){
        this.x = x;
        this.y = y;
        try {
            lily = new File(color_image);
            lily_dark = new File(dark_image);
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

    public void change_to_lily(){
        try {
            lily = new File("images/lily2.png");
            lily_dark = new File("images/lily2-dark.png");
        } catch (Exception e) {}
        change = false;
    }
    public void change_to_calamity(String color_image, String dark_image){
        try {
            lily = new File(color_image);
            lily_dark = new File(dark_image);
        } catch (Exception e) {}
        change = true;
    }

    public boolean getChange(){
        return change;
    }

    public void paint(Graphics g){
        if(visible){
            try {
                image = ImageIO.read(lily);
            } catch (Exception e){}
        } else {
            try {
                image = ImageIO.read(lily_dark);
            } catch (Exception e){}
        }

        g.drawImage(image, x, y, null);
    }
}
