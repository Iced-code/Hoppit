import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JOptionPane; 
import java.util.*;
import java.util.Scanner;

public class game extends JPanel implements KeyListener
{
    Color backgroundColor;

    frog Leaf;
    fly Nut;
    boolean gameOver = false;
    int score = 0;
    int miss = 0;
    int amount = 20;
    boolean paused = false;
    int highScore = 0;
    boolean isOpening = true;

    public game(){
        gameOver = true;
        backgroundColor = new Color(243, 255, 240);
        Leaf = new frog(250, 600);
        Nut = new fly((int)Math.random() * 800, 200);
    }

    public void restart(){
        gameOver = false;
        miss = 0;
        score = 0;
        Nut.setGoldStatus(false);
        //Nut.aliveYes();
        Leaf.show();
    }

    public void collide(){
        if(Nut.getX() <= -250){
            if(Nut.life()){
                miss++;
            }
            amount = 20;
            if(!paused){
                Nut.aliveYes();
            }
            Nut.moveHeight();
            Nut.transform();
        }
        if(Nut.getX() + 140 >= 1150){
            
            if(Nut.life()){
                miss++;
            }
            amount = -20;
            if(!paused){
                Nut.aliveYes();
            }
            Nut.moveHeight();
            Nut.transform();
        }
        repaint();
    }

    public void keyPressed(KeyEvent e) {
        char c = e.getKeyChar();

        //A & D for big movements
        if(c == 'd' || c == 'D'){
            Leaf.moveRight(35);        
        }
        if(c == 'a' || c == 'A'){
            Leaf.moveLeft(35);        
        }

        /*For the fly's movement
        if(c == 'l' || c == 'L'){
            Nut.moveRight(15);        
        }
        if(c == 'j' || c == 'J'){
            Nut.moveLeft(15);        
        }*/

        //dev commands

        //Appears/Disappears fly
        if(c == 'x' || c == 'X'){
            Nut.aliveNo();        
        }
        if(c == 'z' || c == 'Z'){
            Nut.aliveYes();        
        }
        //ends game
        if((c == 'r' || c == 'R') && !isOpening){
            restart();  
        }
        
        //sticks tongue out when space bar pressed
        if(c == ' '){

            if(isOpening){
                isOpening = false;
                restart();
            }
            Leaf.tongueOut();
            try {
                Thread.sleep(220);
            }catch(InterruptedException x) {}
            Leaf.tongueIn();

            //adds to score if fly is hit
            if((!(Leaf.getX() > Nut.getX() + 35) && !(Leaf.getX() + 185 < Nut.getX())) && Nut.life()){
                Nut.aliveNo();
                
                if(Nut.getGoldStatus() == true){
                    score += 3;
                } else{
                    score++;
                }

                if(highScore < score){
                    highScore = score;
                }
                collide();
            }
            try {
                Thread.sleep(400);
            }catch(InterruptedException x) {}
        }

        if(c == 'S'){
            Nut.aliveNo();
            paused = true;
            repaint();
            String s = (JOptionPane.showInputDialog("Enter the egg")).toLowerCase();
            
            /*Scanner sc = new Scanner(System.in);
			System.out.println("Enter the egg: ");
			String s = sc.nextLine();
            System.out.println("");
            sc.close();*/

			//repaint();
            //paused = false;
            if(s.equals("greatest treasure")){
                Leaf.sprig();
                JOptionPane.showMessageDialog(null, "Never forget, the friends you make are the greatest of treasures.");
                //System.out.println("Never forget, the friends you make are the greatest of treasures.");
            }
            
            paused = false;
            repaint();
        }

        repaint();
    }

    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();

        repaint();    
    }

    public void keyReleased(KeyEvent e) {
        char c = e.getKeyChar();


        repaint();
    }

    ////////////////////////////////////////////////////


    public void paint(Graphics g){
        
        //paints background
        g.setColor(backgroundColor);
        g.fillRect(0, 0, 1000, 1000);

        g.setColor(new Color(202, 255, 189));
        ((Graphics2D)g).setStroke(new BasicStroke(50));
        for(int y = 100; y <= 1800; y += 100){
            g.drawLine(0, y, 1000, y);
        }
        
        //paints fly and frog
        Leaf.paint(g);
        Nut.paint(g);
        
        //paints words at the top
        String highScoreText = "High Score: " + highScore;
        String scoreText = "Score: " + score;
        String title = "Hoppit";
        String attempt = "Misses: " + miss;
        String name1 = "Ayaan Modak";
        String name2 = "Github: @Iced-code";

        String menu1 = "Welcome to Hoppit! In this game,"; //menu needs to be finished
        String menu2 = "you play as a frog trying to catch a fly.";
        String menu3 = "Press the SPACEBAR to stick out your tongue";
        String menu4 = "and catch the fly as it flies by.";
        String menu5 = "\nIf you miss the fly 3 times, it's game over!";
        String menu6 = "Try and get a high score!";
        String menu7 = "Press the SPACEBAR to start";

        g.setColor(Color.BLACK);
        //paints name and @ at the bottom
        g.setFont(new Font("Times New Roman", Font.BOLD, 17));
        g.drawString(name1, 5, 737);
        g.drawString(name2, 5, 757);
        g.setFont(new Font("Times New Roman", Font.BOLD, 50));
        g.drawString(title, 20, 60);

		//displays opening screen (instructions)
        if(isOpening){
            g.setColor(Color.WHITE);
            g.fillRect(100, 100, 600, 600);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Times New Roman", Font.BOLD, 30));
			
			g.drawString(menu1, 105, 200);
            g.drawString(menu2, 105, 250);
            g.drawString(menu3, 105, 300);
            g.drawString(menu4, 105, 350);
            g.drawString(menu5, 105, 400);
            g.drawString(menu6, 105, 450);
            g.drawString(menu7, 200, 575);
        }

		//displays main game HUD
        if(!gameOver){
            g.setFont(new Font("Times New Roman", Font.BOLD, 50));
            g.drawString(scoreText, 575, 60);
            g.drawString(attempt, 310, 60);
            g.setFont(new Font("Times New Roman", Font.BOLD, 25));
            g.drawString(highScoreText, 575, 110);
        }

        //paints game over screen
        if(gameOver && !isOpening){
            g.setColor(Color.RED);
            g.setFont(new Font("Times New Roman", Font.BOLD, 50));
            String gameEnd = "Game Over";
            g.drawString(gameEnd, 275, 320);
            g.setColor(Color.BLACK);
            g.drawString(highScoreText, 250, 415);
            g.setFont(new Font("Times New Roman", Font.PLAIN, 35));
            String r = "Press 'R' to restart";
            g.drawString(r, 270, 465);
        }
    }
        
    //ends the game
    public void end(){
        if(miss >= 3){
            gameOver = true;
        }
    }

    //plays the game
    public void run(){
        /*gameOver = true;
        miss = 0;
        score = 0;*/
        Nut.aliveYes();
        Leaf.show();
        while(miss < 4){
            try {
                Thread.sleep(70);
            }catch(InterruptedException e) {}
            Nut.move(amount);
            paintImmediately(0, 0, 1000, 1000);
            collide();
            end();
            if(gameOver){
                Nut.aliveNo();
                Leaf.hide();
            }
            repaint();
        }
    }

    public static void main(String [] arg){

        //makes the game window
        JFrame frame = new JFrame("Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(800, 800);
        frame.setLocation(0, 0);
        frame.setLayout(null);

        //makes the game
        game game = new game();
        
        game.setSize(800, 800);
        game.setLocation(0, 0);
        frame.getContentPane().add(game);

        frame.setVisible(true);

        frame.addKeyListener(game);
        game.run();
    }
}