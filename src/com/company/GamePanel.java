package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.awt.event.*;


//action in Game
public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH=600;
    static final int SCREEN_HEIGHT=600;
    static final int UNITE_SIZE=25;
    static final int GAME_UNITS=(SCREEN_WIDTH*SCREEN_HEIGHT)/UNITE_SIZE;
    static final int DELAY=75;
    final int x[]=new int[GAME_UNITS];
    final int y[]=new int[GAME_UNITS];
    int bodyParts=6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction='R';
    boolean running=false;
    Timer timer;
    Random random;


    GamePanel(){
            random=new Random();
            this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
            this.setBackground(Color.black);
            this.setFocusable(true);
            this.addKeyListener(new MyKeyAdapter());
            startGame();
   }
   public void  startGame(){
        newApple();
        running=true;
        timer=new Timer(DELAY,this);
       timer.start();

   }
 @Override
 public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
 }
   public void draw(Graphics g) {
       if (running) {
           /*
           for (int i = 0; i < SCREEN_HEIGHT / UNITE_SIZE; i++) {
               g.drawLine(i * UNITE_SIZE, 0, i * UNITE_SIZE, SCREEN_HEIGHT);
               g.drawLine(0, i * UNITE_SIZE, SCREEN_WIDTH, i * UNITE_SIZE);
           }

            */
           //apple
              g.setColor(Color.red);
               g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
               g.fillOval(appleX, appleY, UNITE_SIZE, UNITE_SIZE);
           //snaker
           for (int i = 0; i < bodyParts; i++) {
               if (i == 0) {
                   g.setColor(Color.green);
                   g.fillRect(x[i], y[i], UNITE_SIZE, UNITE_SIZE);
               } else {
                   g.setColor(new Color(45, 180, 0));
                   g.fillRect(x[i], y[i], UNITE_SIZE, UNITE_SIZE);
               }
           }
           g.setColor(Color.red);
           g.setFont(new Font("Ink free",Font.BOLD,40));
           FontMetrics metrics=getFontMetrics(getFont());
           g.drawString("Score:"+applesEaten,(SCREEN_WIDTH-metrics.stringWidth("Score:"+applesEaten))/2,g.getFont().getSize());
       }
       else {
           GameOver(g);
       }
   }
   public void newApple(){
        appleX=random.nextInt((int)(SCREEN_HEIGHT/UNITE_SIZE))*UNITE_SIZE;
       appleY=random.nextInt((int)(SCREEN_HEIGHT/UNITE_SIZE))*UNITE_SIZE;

   }
    public void move(){
        for (int i=bodyParts;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        switch (direction){
            case 'u':
                y[0]=y[0]-UNITE_SIZE;
                break;
            case 'D':
                y[0]=y[0]+UNITE_SIZE;
                break;
            case 'L':
                x[0]=x[0]-UNITE_SIZE;
                break;
            case 'R':
                x[0]=x[0]+UNITE_SIZE;
                break;
        }
    }
    public void checkApple(){
        if((x[0]==appleX)&& (y[0]==appleY)){
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }
    public void checkCollisions(){
        //checks if head collides with body
        for (int i=bodyParts;i>0;i--){
            if((x[0]== x[i]) && (y[0]==y[i])){
                running=false;
            }
            //checks if head touches left border
            if(x[0]<0){
                running=false;
            }
            //checks if head touches Right border
            if(x[0]>SCREEN_WIDTH){
                running=false;
            }
            //checks if head touches top border
            if(y[0]<0){
                running=false;
            }
            //checks if head touches bottom border
            if(y[0]>SCREEN_HEIGHT){
                running=false;
            }
            if (!running){
                timer.stop();
            }

        }
    }
    public void GameOver(Graphics g){
        //score
        g.setColor(Color.red);
        g.setFont(new Font("Ink free",Font.BOLD,40));
        FontMetrics metrics1=getFontMetrics(getFont());
        g.drawString("Score:"+applesEaten,(SCREEN_WIDTH-metrics1.stringWidth("Score:"+applesEaten))/2,g.getFont().getSize());
        //Game over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink free",Font.BOLD,75));
        FontMetrics metrics2=getFontMetrics(getFont());
        g.drawString("Game Over",(SCREEN_WIDTH-metrics2.stringWidth("Game Over"))/2,SCREEN_HEIGHT/2);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();

    }
   public class MyKeyAdapter extends KeyAdapter{
            //الاسهم ولاتجاهات التحكم

       @Override
       public void keyPressed(KeyEvent e) {
           super.keyPressed(e);
           switch (e.getKeyCode()){
               case KeyEvent.VK_LEFT:
                   if(direction!='R'){
                       direction='L';
                   }
                   break;
               case KeyEvent.VK_RIGHT:
                   if(direction!='L'){
                       direction='R';
                   }
                   break;
               case KeyEvent.VK_UP:
                   if(direction!='D'){
                       direction='U';
                   }
                   break;
               case KeyEvent.VK_DOWN:
                   if(direction!='U'){
                       direction='D';
                   }
                   break;
           }
       }

    }
}
