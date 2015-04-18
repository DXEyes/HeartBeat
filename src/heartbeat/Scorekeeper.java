/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heartbeat;

import gamebase.GameLoop;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pcowal15
 */
public class Scorekeeper {
    HeartGame game;
    int score;
    double combo, maxCombo;
    int scoreDisplay;
    int patientCount, patientsTreated;
    
    double prevX, prevY;
    Signature signature;
    Button menuButton;
    int t;
    public Scorekeeper(HeartGame game){
        this.game=game;
        score=scoreDisplay=0;
        combo=maxCombo=1;
        signature=new Signature();
        menuButton=new Button(game, "Okay!", 220, 140, 80, 16, 2);
    }
    public void addPoints(int points){
        score+=(int)((double)points*combo);
        if(score<0)score=0;
    }
    public void resetCombo(){
        combo=1;
    }
    public void increaseCombo(double amt){
        combo+=amt;
        if(combo>maxCombo)maxCombo=combo;
    }
    public void decreaseCombo(double amt){
        combo-=amt;
        if(combo<1)combo=1;
    }
    public void halveCombo(){
        combo/=2;
        if(combo<1)combo=1;
    }
    
    public void renderFinal(){
        
        t++;
        //draw a sheet of paper
        game.drawRectangle(40, 2, 280, 178, 0xFFF0F0F0, false);
        game.drawRectangle(40, 2, 280, 178, 0xFF202020, true);
        
        String rank;
        double percent=0;
        if(patientCount>0)percent=(double)patientsTreated/(double)patientCount;
        if(percent>.999)rank="S";
        else if(percent>.93)rank="A";
        else if(percent>.85)rank="B";
        else if(percent>.70)rank="C";
        else if(percent>.60)rank="D";
        else rank="F";
        
        game.drawText2("Medical Report\nScore: "+score+"\n"
                + "Max Combo: "+(int)maxCombo+"\n"
                + "% Treated: "+patientsTreated+"/"+patientCount + "\n"
                + "Rank: "+rank, game.fontBlack, 42, 4);
        
        game.drawText("Sign below", game.fontBlack, 42, 120);
        if(game.mbPressed[MouseEvent.BUTTON1]){
            if(game.click){
                prevX=game.mouseX;
                prevY=game.mouseY;
            }
            for(double i=0; i<=1; i+=.125){
                double xx=prevX*i+(double)game.mouseX*(1-i);
                double yy=prevY*i+(double)game.mouseY*(1-i);
                signature.edit((int)xx-42, (int)yy-140);
                
            }
            prevX=game.mouseX;
            prevY=game.mouseY;
            
        }
        signature.render(game, 42, 140);
        if(signature.edited){
            menuButton.render();
            if(menuButton.clicked()){
                if(signature.fullyBlack()){
                    game.achievement.display("[redacted]");
                }
                game.highscores.addEntry(signature, score, rank);
                try {
                    SerializationUtil.serialize(game.highscores, "highscores.hbh");
                } catch (IOException ex) {
                    //Logger.getLogger(Scorekeeper.class.getName()).log(Level.SEVERE, null, ex);
                }
                game.resetGame();
            }
        }
    }
    
    public void render(){
        animateScore();
        
        int s2=scoreDisplay;
        int offset=0;
        while (s2>=10){
            s2/=10;
            offset+=game.fontBlack.width;
        }
        game.blendMode=GameLoop.BM_DIFFERENCE;
        game.drawText(scoreDisplay+"", game.fontWhite, 310-offset, 5);
        double s3=combo+0.000001;
        offset=game.fontBlack.width*3;
        while (s3>=10){
            s3/=10;
            offset+=game.fontBlack.width;
        }
        game.drawText("x"+combo, game.fontWhite, 310-offset, 25);
        game.blendMode=GameLoop.BM_NORMAL;
    }
    public void animateScore(){
        for(int i=100000; i>10; i/=10){
            if(scoreDisplay<score-i){
                scoreDisplay+=i/5;
                break;
            }
            if(scoreDisplay>score+i){
                scoreDisplay-=i/5;
                break;
            }
        }
        if(scoreDisplay<score)scoreDisplay++;
        if(scoreDisplay>score)scoreDisplay--;
    }
}
