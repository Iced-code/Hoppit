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
        } catch (Exception e) {}
    }

    public void restart(){
        gameOver = false;
        miss = 0;
        score = 0;
        Nut.setGoldStatus(false);
        Leaf.show();
        for(hearts life : lives){
            life.show();
        }
    }

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

    public void keyPressed(KeyEvent e) {
        char c = e.getKeyChar();

        
        //A & D for big movements
        /* if(c == 'd' || c == 'D'){
            Leaf.moveRight(35);        
        }
        if(c == 'a' || c == 'A'){
            Leaf.moveLeft(35);        
        } */

        //Appears/Disappears fly
        /* if(c == 'x' || c == 'X'){
            Nut.aliveNo();        
        }
        if(c == 'z' || c == 'Z'){
            Nut.aliveYes();        
        } */

        repaint();
    }

    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();

        repaint();    
    }

    public void keyReleased(KeyEvent e) {
        char c = e.getKeyChar();
        
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

            //adds to score if fly is hit
            if((!(Leaf.getX() > Nut.getX() + 35) && !(Leaf.getX() + 185 < Nut.getX())) && Nut.life()){
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

        //ends game
        if((c == 'r' || c == 'R') && !isOpening){
            restart();  
        }
        
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
                    playSound();
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
            //LEAF the Frog
            else if(s.equals("leaf")){
                Leaf.makeOriginal();
                if(Leaf.getOriginal() == true){
                    JOptionPane.showMessageDialog(this, "Leaf is the original and official nickname of the Hoppit Frog.");
                }
            }
            
            paused = false;
            repaint();
        }

        repaint();
    }

    ////////////////////////////////////////////////////

    //plays the game
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

    //ends the game
    public void end(){
        if(miss >= 3){
            gameOver = true;
        }
    }

    public static synchronized void playSound() {
        new Thread(new Runnable() {
        public void run() {
            try {
                Clip clip = AudioSystem.getClip();
                String URL = "assets/audio/Amphibia_TrueColorsCredits.wav";
                //AudioInputStream inputStream = AudioSystem.getAudioInputStream(game.class.getResourceAsStream("assets/audio/Amphibia_TrueColorsCredits.wav"));
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResource(URL));
                clip.open(inputStream);
                clip.start(); 
            } catch (Exception e) {
                e.printStackTrace();
            }
          }
        }).start();
    }
    
    public void paint(Graphics g){
        //paints background
        /*g.setColor(backgroundColor);
        g.fillRect(0, 0, 1000, 1000);

        g.setColor(new Color(202, 255, 189));
        ((Graphics2D)g).setStroke(new BasicStroke(50));
        for(int y = 100; y <= 1800; y += 100){
            g.drawLine(0, y, 1000, y);
        }*/

        g.drawImage(backimage, 0, 0, null);
        
        //paints fly, frog, and lives
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

        //words for the top and bottom
        String highScoreText = "High Score: " + highScore;
        String scoreText = "Score: " + score;
        String title = "Hoppit";
        String name1 = "Ayaan Modak";
        String name2 = "Github: @Iced-code";

        //instructions
        /*String menu1 = "Welcome to Hoppit! In this game,"; 
        String menu2 = "you play as a frog trying to catch";
        String menu3 = "a fly. Press the SPACEBAR to stick";
        String menu4 = "out your tongue and catch the fly";
        String menu5 = "as it flies by. \nIf you miss the fly";
        String menu6 = "3 times, it's game over!";
        String menu7 = "Press the SPACEBAR to start";*/

        
        //paints name and @ at the bottom
        g.setColor(Color.BLACK);
        g.setFont(new Font("Verdana", Font.BOLD, 17));
        g.drawString(name1, 5, 737);
        g.drawString(name2, 5, 757);

        g.setFont(new Font("Verdana", Font.BOLD, 50));
        g.drawString(title, 20, 60);

		//displays game HUD
        if(!gameOver){
            g.setFont(new Font("Verdana", Font.BOLD, 40));
            g.drawString(scoreText, 565, 55);
            g.setFont(new Font("Verdana", Font.BOLD, 25));
            g.drawString(highScoreText, 565, 100);
        }

        //displays opening screen (instructions)
        if(isOpening){
            g.drawImage(instructionsimage, 100, 100, null);
            /*g.setColor(Color.WHITE);
            g.fillRect(100, 100, 600, 600);
            g.setColor(Color.BLACK);
			g.setFont(new Font("Verdana", Font.BOLD, 30));

            g.drawImage(logoimage, 100, 100, null);

			g.drawString(menu1, 110, 200);
            g.drawString(menu2, 110, 250);
            g.drawString(menu3, 110, 300);
            g.drawString(menu4, 110, 350);
            g.drawString(menu5, 110, 400);
            g.drawString(menu6, 110, 450);
            g.drawString(menu7, 150, 575);*/
        }

        //paints game over screen
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
