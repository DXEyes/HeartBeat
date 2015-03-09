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
public class Stethoscope extends Draggable{
    
    public Stethoscope(HeartGame game){
        super(game,game.heart,300,150);
    }
    @Override
    public void Update(){
        double distsq=(x-60)*(x-60)+(y-60)*(y-60);
        game.beat.song.setCutoff((float)Math.min(1, (distsq)*0.0005+0.1));
        game.heartbeat.setVolume((float)Math.max(0, 1-(distsq)*0.0005));
    }
    @Override
    public void Render(){
        //game.drawRectangle((int)x-4, (int)y-4, (int)x+4, (int)y+4, 0xff808080, false);
        game.drawLine((int)x, (int)y, 320, 180, 0x99000000);
    }

    @Override
    public void Use() {
        
    }
}
