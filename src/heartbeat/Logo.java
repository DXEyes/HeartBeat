/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heartbeat;

import gamebase.GameLoop;

/**
 *
 * @author pcowal15
 */
public class Logo {
    HeartGame game;
    int t;
    double x;
    public Logo(HeartGame game){
        this.game=game;
        x=-320;
    }
    public boolean done(){
        return t>300;
    }
    public void render(){
        
        double r3=Math.sqrt(3)/2;
        
        t++;
        if(t<200 && x<0)x+=5;
        if(t>200)x+=5;
        game.blendMode=GameLoop.BM_ADD;
        
        int x1=25-(int)(x);
        int y1=27;
        game.drawSpriteScaled(game.logo, 1, x1, y1, x1+270, y1+125);
        x1=25+(int)(x/2);
        y1=27+(int)(x*r3);
        game.drawSpriteScaled(game.logo, 2, x1, y1, x1+270, y1+125);
        y1=27-(int)(x*r3);
        game.drawSpriteScaled(game.logo, 3, x1, y1, x1+270, y1+125);
        game.blendMode=GameLoop.BM_NORMAL;
    }
}
