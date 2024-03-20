import javax.swing.*;

public class Hoppit extends game
{
    public static void main(String [] arg){

        //makes the game window
        JFrame frame = new JFrame("Hoppit");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(800, 800);
        frame.setLocation(0, 0);
        frame.setLayout(null);

        //makes the game
        game Hoppit = new game();
        
        Hoppit.setSize(800, 800);
        Hoppit.setLocation(0, 0);
        frame.getContentPane().add(Hoppit);

        frame.setVisible(true);

        frame.addKeyListener(Hoppit);
        Hoppit.run();
    }
}
