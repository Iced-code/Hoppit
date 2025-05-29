import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.util.*;

public class game extends JPanel implements KeyListener
{
    frog Leaf;
    fly Nut;

    Color backgroundColor;
    boolean gameOver = false;
    int score = 0;
    int miss = 0;
    int flyMoveAmount = 20;
    boolean paused = false;
    int highScore = 0;
    boolean isOpening = true;

    String[] gems = {"assets/images/green.png", "assets/images/blue.png", "assets/images/pink.png"};
    ArrayList<hearts> lives = new ArrayList<hearts>();
    hearts live1 = new hearts(285, 10);
    hearts live2 = new hearts(365, 10);
    hearts live3 = new hearts(445, 10);
    
    File background;
    BufferedImage backimage;
    File instructions;
    BufferedImage instructionsimage;
    private static String errMessage = "Important files not found. Please manually restore or redownload the missing program files.";

    //CONSTRUCTOR
    public game(){
        gameOver = true;
        backgroundColor = new Color(243, 255, 240);
        Leaf = new frog(250, 600);
        Nut = new fly((int)Math.random() * 800, 200);
        
        lives.add(live1);
        lives.add(live2);
        lives.add(live3);

        try {
            background = new File("assets/images/background.png");
            backimage = ImageIO.read(background);
            instructions = new File("assets/images/instructions.png");
            instructionsimage = ImageIO.read(instructions);
        } catch (Exception e) {
            System.err.println(errMessage);
            System.exit(-1);
        }
    }

    //RESTART FUNCTION
    public void restart(){
        gameOver = false;
        miss = 0;
        score = 0;
        Nut.setGoldStatus(false);
        Leaf.show();
        for(hearts life : lives){
            life.show();
        }

        paused = false;
    }

    //FLY'S RIGHT TO LEFT MOVEMENT AND OFFSCREEN COLLISION LOGIC
    public void collide(){
        if(Nut.getX() <= -250){
            if(Nut.life()){
                miss++;
                lives.get(lives.size() - miss).hide();
            }
            flyMoveAmount = (int)Math.random() * 30 + 30;

            if(!paused){
                Nut.aliveYes();
            }
            Nut.moveHeight();
            Nut.transform();
        }
        if(Nut.getX() + 140 >= 1150){
            if(Nut.life()){
                miss++;
                lives.get(lives.size() - miss).hide();
            }
            flyMoveAmount = -1*((int)Math.random() * 30 + 30);

            if(!paused){
                Nut.aliveYes();
            }
            Nut.moveHeight();
            Nut.transform();
        }
        repaint();
    }

    //UNUSED
    public void keyPressed(KeyEvent e) {
        char c = e.getKeyChar();

        repaint();
    }

    //UNUSED
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();

        //repaint();    
    }

    //KEYBOARD INPUT FUNCTIONS
    public void keyReleased(KeyEvent e) {
        char c = e.getKeyChar();
        
        //STICKS OUT TONGUE
        if(c == ' '){
            if(!isOpening){
                Leaf.tongueOut();
                try {
                    Thread.sleep(220);
                }catch(InterruptedException x) {}
                Leaf.tongueIn();
            }

            if(isOpening){
                isOpening = false;
                restart();
            }

            //INCREASES SCORE IF FLY IS CAUGHT
            if((!(Leaf.getX() > Nut.getX() + 35) && !(Leaf.getX() + 185 < Nut.getX())) && Nut.life()){
                playToungeSound();
                Nut.aliveNo();
                
                if(Nut.getGoldStatus() == true){
                    score += 2;
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

        //RESTART
        if((c == 'r' || c == 'R') && !isOpening){
            restart();  
        }
        
        //EASTER EGGS
        if(c == 'S'){
            Nut.aliveNo();
            paused = true;
            repaint();
            String s = "";
            try {
                s = (JOptionPane.showInputDialog("")).toLowerCase();
            } catch (NullPointerException error){}

            //SPRIG
            if(s.equals("sprig")){
                Leaf.sprig();
                if(Leaf.getSprig() == true){
                    JOptionPane.showMessageDialog(this, "Sprig, the best frog friend - in this world, and every other world.");
                    playTrueColorsSound();
                }
            }
            //CALAMITY GEMS
            else if(s.equals("calamity")){
                if(lives.get(0).getChange() == false){
                    int i = 0;
                    for(hearts life : lives){
                        life.change_to_calamity(gems[i], "assets/images/dark.png");
                        i++;
                    }
                    JOptionPane.showMessageDialog(this, "Gems of witt, heart, and strength can bring or stop calamity.");
                } 
                else {
                    for(hearts life : lives){
                        life.change_to_lily();
                    }
                }
            }
            //LEAF THE FROG
            else if(s.equals("leaf")){
                Leaf.makeOriginal();
                if(Leaf.getOriginal() == true){
                    JOptionPane.showMessageDialog(this, "Leaf is the original and official nickname of the Hoppit Frog.");
                }
            }
            
            paused = false;
        }
        else if(c == 'i' || c == 'I'){
            if(!isOpening){
                Nut.aliveNo();
                paused = true;
                isOpening = true;
                repaint();
            }
        }

        repaint();
    }

    ////////////////////////////////////////////////////

    //RUNS GAME LOGIC AND PLAYS THE GAME
    public void run(){
        Leaf.show();
        for(hearts life : lives){
            life.show();
        }
        while(miss < 4){
            try {
                Thread.sleep(70);
            }catch(InterruptedException e) {}
            Nut.move(flyMoveAmount);
            paintImmediately(0, 0, 1000, 1000);
            collide();
            end();
            if(gameOver){
                Nut.aliveNo();
                Leaf.hide();
                for(hearts life : lives){
                    life.hide();
                }
            }
            repaint();
        }
    }

    //PLAYS TRUE COLORS SOUND EFFECT
    public static synchronized void playToungeSound() {
        new Thread(new Runnable() {
        public void run() {
            try {
                Clip clip = AudioSystem.getClip();
                String URL = "assets/audio/tongue_out.wav";
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResource(URL));
                clip.open(inputStream);
                clip.start(); 
            } catch (Exception e) {
                System.err.println(errMessage);
                e.printStackTrace();
            }
          }
        }).start();      
    }

    //PLAYS TRUE COLORS SOUND EFFECT
    public static synchronized void playTrueColorsSound() {
        new Thread(new Runnable() {
        public void run() {
            try {
                Clip clip = AudioSystem.getClip();
                String URL = "assets/audio/Amphibia_TrueColorsCredits.wav";
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResource(URL));
                clip.open(inputStream);
                clip.start(); 
            } catch (Exception e) {
                System.err.println(errMessage);
                e.printStackTrace();
            }
          }
        }).start();
    }
    
    //ENDS THE GAME
    public void end(){
        if(miss >= 3){
            gameOver = true;
        }
    }

    //DRAWS ALL OBJECTS ONTO SCREEN (FROG, FLY BACKGROUND, TEXT)
    public void paint(Graphics g){
        g.drawImage(backimage, 0, 0, null);
        
        //PAINTS FROG, FLY, AND LIVES
        if(!isOpening){
            Leaf.paint(g);
            Nut.paint(g);
            for(hearts life : lives){
                life.paint(g);
            }
        }
        if(gameOver){
            Leaf.hide();
            Nut.aliveNo();
        }

        //TITLE, SCORE, AND NAME CREDITS
        String highScoreText = "High Score: " + highScore;
        String scoreText = "Score: " + score;
        String title = "Hoppit";
        String name1 = "Ayaan Modak";
        String name2 = "Github: @Iced-code";

        //PAINTS ON-SCREEN TEXT
        g.setColor(Color.BLACK);
        g.setFont(new Font("Verdana", Font.BOLD, 17));
        g.drawString(name1, 5, 737);
        g.drawString(name2, 5, 757);

        g.setFont(new Font("Verdana", Font.BOLD, 50));
        g.drawString(title, 20, 60);

		//DISPLAYS GAME HUD
        if(!gameOver){
            g.setFont(new Font("Verdana", Font.BOLD, 40));
            g.drawString(scoreText, 565, 55);
            g.setFont(new Font("Verdana", Font.BOLD, 25));
            g.drawString(highScoreText, 565, 100);
        }

        //DISPLAYS OPENING INSTRUCTIONS SCREEN
        if(isOpening){
            g.drawImage(instructionsimage, 100, 100, null);
        }

        //PAINTS GAME OVER SCREEN
        if(gameOver && !isOpening){
            g.setColor(Color.RED);
            g.setFont(new Font("Verdana", Font.BOLD, 50));
            String gameEnd = "Game Over";
            g.drawString(gameEnd, 255, 320);

            g.setColor(Color.BLACK);
            g.drawString(highScoreText, 230, 415);
            g.setFont(new Font("Verdana", Font.PLAIN, 35));
            String r = "Press 'R' to restart";
            g.drawString(r, 250, 465);
        }
    }
}
