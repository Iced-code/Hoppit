import java.awt.*;
import java.awt.Graphics;

public class frog
{
    //private BufferedImage imageFace;
    //private BufferedImage image1;
    private int x, y;
    private boolean tongue = false;
    private boolean visible = true;
    private boolean change = false;

    public frog(int x, int y){
        this.x = x;
        this.y = y;
    } 

    public void moveRight(int distance){
        x = x + distance;
    }

    public void moveLeft(int distance){
        x = x - distance;
    }

    public void tongueOut(){
        tongue = true;
    }
    
    public void tongueIn(){
        tongue = false;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public int getTongueX(){
        return x + 125;
    }
    
    public int getTongueY(){
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

    public void paint(Graphics g){
        if(visible){
            //TOUNGE
            if (tongue ==  true){
                g.setColor(Color.PINK);
                if(change){ g.setColor(new Color(237, 241, 66));}
                // try {
                    // Thread.sleep(500);
                // }catch(InterruptedException e) {}
                // for(int i = 0; i < 100; i += 10){
                    // g.fillRect(x+125, a-(i*5), 50, b+(i*5));
                // } 
                
                g.fillRect(x+125, y-550, 50, 700);
                g.fillRect(x+100, y-525, 100, 80);
                
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
