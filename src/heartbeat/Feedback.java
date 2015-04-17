/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heartbeat;

import gamebase.GameLoop;
import gamebase.Sprite;

/**
 *
 * @author pcowal15
 */
public class Feedback {
    int type;
    HeartGame game;
    int life, x, y;
    String text;
    public Feedback(HeartGame game, String text, int x, int y){
        this.game=game;
        this.text=text;
        this.x=x;
        this.y=y;
        life=20;
        type=-1;
    }
    public Feedback(HeartGame game, boolean good){
        this.game=game;
        life=63;
        type=good?0:1;
    }
    public void render(){
        if(type<0){
            game.blendMode=GameLoop.BM_DIFFERENCE;
            game.drawText(text, game.fontWhite, x, y);
            game.blendMode=GameLoop.BM_NORMAL;
            y-=2;
        }
        else{
            game.drawSpriteFiltered(game.checkMark, type, 160-64, 90-64, life*4, 255, 255, 255);
        }
        life--;
        
    }
}
