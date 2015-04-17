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
public class Feedback {
    HeartGame game;
    int life, x, y;
    String text;
    public Feedback(HeartGame game, String text, int x, int y){
        this.game=game;
        this.text=text;
        this.x=x;
        this.y=y;
        life=20;
    }
    public void render(){
        game.blendMode=GameLoop.BM_DIFFERENCE;
        game.drawText(text, game.fontWhite, x, y);
        game.blendMode=GameLoop.BM_NORMAL;
        life--;
        y-=2;
    }
}
