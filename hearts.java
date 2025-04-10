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
    static String errMessage = "Important files not found. Please manually restore or redownload the missing program files.";

    //CONSTRUCTOR
    public hearts(int x, int y){
        this.x = x;
        this.y = y;
        try {
            lily = new File("assets/images/lily2.png");
            lily_dark = new File("assets/images/lily2-dark.png");
        } catch (Exception e) {
            System.err.println(errMessage);
            System.exit(-1);
        }
    }

    //DISPLAYS IMAGES
    public void show(){
        visible = true;
    }

    //HIDES IMAGES
    public void hide(){
        visible = false;
    }

    //SETS LIVES ICONS TO LILY PADS
    public void change_to_lily(){
        try {
            lily = new File("assets/images/lily2.png");
            lily_dark = new File("assets/images/lily2-dark.png");
        } catch (Exception e) {
            System.err.println(errMessage);
            System.exit(-1);
        }
        change = false;
    }

    //SETS LIVES ICONS TO GEMS
    public void change_to_calamity(String color_image, String dark_image){
        try {
            lily = new File(color_image);
            lily_dark = new File(dark_image);
        } catch (Exception e) {
            System.err.println(errMessage);
        }
        change = true;
    }

    //RETURNS "CHANGE" ATTRIBUTE
    public boolean getChange(){
        return change;
    }

    //PAINTS LIVES ICONS
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
