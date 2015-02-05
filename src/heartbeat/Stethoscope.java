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
public class Stethoscope {
    HeartGame game;
    double x, y;
    public Stethoscope(HeartGame game){
        this.game=game;
    }
    public void update(){
        x+=(game.mouseX-x)*0.1;
        y+=(game.mouseY-y)*0.1;
        double distsq=(x-60)*(x-60)+(y-60)*(y-60);
        game.beat.song.setCutoff((float)Math.min(1, (distsq)*0.0005+0.1));
        game.heartbeat.setVolume((float)Math.max(0, 1-(distsq)*0.0005));
    }
    public void render(){
        game.drawRectangle((int)x-4, (int)y-4, (int)x+4, (int)y+4, 0xFF808080, false);
        game.drawLine((int)x, (int)y, 320, 180, 0xFF808080);
    }
}
