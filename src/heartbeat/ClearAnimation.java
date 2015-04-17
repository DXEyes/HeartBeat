/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heartbeat;

import java.awt.Color;

/**
 *
 * @author pcowal15
 */
public class ClearAnimation {
    HeartGame game;
    int time;
    public ClearAnimation(HeartGame game){
        time=0;
        this.game=game;
    }
    public void render(){
        time++;
        
        game.blur(Math.min(time*4, 500));
        int y;
        if(time<100){
            double yy=(100-time);
            y=90+(int)(yy*yy/16);
        }
        else if(time<160){
            y=90;
        }
        else{
            double yy=(time-160);
            y=90-(int)(yy*yy/16);
        }
        game.drawSprite(game.clear, 0, 160-game.clear.width/2, y-game.clear.height/2);
        
        if(time>180){
            int a=(time-180)*4;
            if(a>255)a=255;
            game.drawRectangle(0, 0, 320, 240, new Color(255,255,255,a).getRGB(), false);
        }
    }
}
