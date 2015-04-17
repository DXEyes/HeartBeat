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
public class PulseAnimation {
    HeartGame game;
    int y, height;
    int[] colors;
    
    public PulseAnimation(HeartGame game, int y, int height){
        this.game=game;
        this.y=y;
        this.height=height;
        
        colors=new int[16];
        
        int alpha=255;
        for(int i=0; i<8; ++i){
            
            colors[i]=new Color(255,0,0,alpha).getRGB();
            alpha*=3;
            alpha/=4;
        }
    }
    public void render(){
        int c=17+game.beat.getBeat();
        
        game.drawLine(0, y, 20, y, colors[(c--)%16]);
        game.drawLine(20, y, 40, y-height, colors[(c--)%16]);
        game.drawLine(40, y-height, 60, y+height/2, colors[(c--)%16]);
        game.drawLine(60, y+height/2, 80, y, colors[(c--)%16]);
        for(int i=80; i<220; i+=35){
            game.drawLine(i, y, i+35, y, colors[(c--)%16]);
        }
        
        game.drawLine(220, y, 240, y, colors[(c--)%16]);
        game.drawLine(240, y, 260, y-height, colors[(c--)%16]);
        game.drawLine(260, y-height, 280, y+height/2, colors[(c--)%16]);
        game.drawLine(280, y+height/2, 300, y, colors[(c--)%16]);
        game.drawLine(300, y, 320, y, colors[(c--)%16]);
    }
}
