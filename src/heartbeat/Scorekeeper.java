/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heartbeat;

/**
 *
 * @author pcowal15
 */
public class Scorekeeper {
    HeartGame game;
    int score, combo;
    int scoreDisplay;
    public Scorekeeper(HeartGame game){
        this.game=game;
        score=scoreDisplay=0;
        combo=1;
    }
    public void addPoints(int points){
        score+=points*combo;
        if(score<0)score=0;
    }
    public void resetCombo(){
        combo=1;
    }
    public void increaseCombo(){
        ++combo;
    }
    public void render(){
        
        for(int i=100000; i>10; i/=10){
            if(scoreDisplay<score-i){
                scoreDisplay+=i/5;
                break;
            }
        }
        if(scoreDisplay<score)scoreDisplay++;
        
        int s2=scoreDisplay;
        int offset=0;
        while (s2>=10){
            s2/=10;
            offset+=game.font.width;
        }
        game.drawText(scoreDisplay+"", game.font, 310-offset, 5);
        s2=combo;
        offset=game.font.width;
        while (s2>=10){
            s2/=10;
            offset+=game.font.width;
        }
        game.drawText("x"+combo, game.font, 310-offset, 25);
    }
}
