import java.awt.*;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class frog
{
    private int x, y;
    private boolean tongue = false;
    private boolean visible = true;
    private boolean change = false;
    private boolean original = false;

    private File file;
    private BufferedImage image;

    //CONSTRUCTOR
    public frog(int x, int y){
        this.x = x;
        this.y = y;

        try {
            file = new File("assets/images/frog.png");
            image = ImageIO.read(file);
        } catch (Exception e) {
            System.err.println("Important files not found. Please manually restore the files or redownload the program files.");
            System.exit(-1);
        }
    } 

    //STICKS OUT FROG TONGUE
    public void tongueOut(){
        tongue = true;
    }
    
    //STICKS IN FROG TONGUE
    public void tongueIn(){
        tongue = false;
    }
    
    //RETURNS X POSTION
    public int getX(){
        return x;
    }
    
    //RETURNS Y POSTION
    public int getY(){
        return y;
    }
    
    //RETURNS TONGUE X POSITION
    public int getTongueX(){
        return x + 125;
    }
    
    //RETURNS TONGUE Y POSITION
    public int getTongueY(){
        return y;
    }

    //DISPLAYS FROG
    public void show(){
        visible = true;
    }

    //HIDES FROG
    public void hide(){
        visible = false;
    }

    //RETURNS VISIBLE ATTRIBUTE
    public boolean getVisible(){
        return visible;
    }

    //FLIPS CHANGE ATTRIBUTE
    public void sprig(){
        change = !change;
    }

    //RETURNS CHANGE ATTRIBUTE
    public boolean getSprig(){
        return change;
    }

    //FLIPS ORIGINAL ATTRIBUTE
    public void makeOriginal(){
        original = !original;
    }

    //RETURNS ORIGINAL ATTRIBUTE
    public boolean getOriginal(){
        return original;
    }

    //TONGUE ANIMATION TEST
    /* public void tongueAnimate(Graphics g){
        for(int i = 0; i <= 70; i += 10){
            try {
                Thread.sleep(250);
            }catch(InterruptedException x) {}
            g.fillRect(x+125, y-550, 50, (i*10));
            System.out.println(i + " " + i*10);
        }
    } */

    //PAINTS FROG
    public void paint(Graphics g){
        if(visible){
            //tongue is always painted
            if (tongue == true){
                g.setColor(Color.PINK);
                if(change){ 
                    g.setColor(new Color(237, 241, 66));
                }
                g.fillRect(x+125, y-520, 50, 700);
                g.fillRect(x+100, y-495, 100, 80);
            }

            //frog sprite image
            if(original == false){ 
                
                try {
                    if(change == false){
                        file = new File("assets/images/frog.png");
                    }
                    else {
                        file = new File("assets/images/frog_sprig.png");
                    }
                    image = ImageIO.read(file);
                } catch (Exception e){
                    System.err.println("Important files not found. Please manually restore the files or redownload the program files.");
                }

                g.drawImage(image, 155, 520, null);
            }
            //original frog graphics image
            else if(original == true){
                //TONGUE
                if (tongue == true){
                    g.setColor(Color.PINK);
                    if(change){ 
                        g.setColor(new Color(237, 241, 66));
                    }
                    g.fillRect(x+125, y-520, 50, 700);
                    g.fillRect(x+100, y-495, 100, 80);
                }

                //BODY
                g.setColor(new Color(66, 126, 245));
                if(change){
                    g.setColor(new Color(240, 57, 119));
                }
                g.fillOval(x, y, 300, 300);
                
                //BELLY
                g.setColor(new Color(227, 240, 255));
                if(change){
                    g.setColor(new Color(249, 166, 187));
                }
                g.fillOval(x+25, y+75, 250, 450);
                
                //EYELIDS
                g.setColor(new Color(0, 45, 102));
                if(change){
                    g.setColor(new Color(249, 166, 187));
                }
                g.fillOval(x - 13, y-15, 90, 90);
                g.fillOval(x + 215, y-15, 90, 90);

                //EYES
                g.setColor(new Color(205, 225, 250));
                if(change){
                    g.setColor(new Color(255, 255, 227));
                }
                g.fillOval(x-17, y-15, 85, 85);
                g.fillOval(x+223, y-15, 85, 85);
                g.setColor(Color.BLACK);
            }
        }
    }
}
