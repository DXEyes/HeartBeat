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
    Cable cable;
    public Stethoscope(HeartGame game){
        super(game,game.scope,160,150);
        cable=new Cable(game, x, y, 200, 10, 0xFF303030);
        cable.setEnd(220, 180, -100);
        cable.setEnd2(100, 180, -100);
        cable.setStart(x, y, 0);
        cable.update(100);
        essential=true;
    }
    @Override
    public void Update(){
        double distsq=(x-55)*(x-55)+(y-65)*(y-65);
        game.beat.song.setCutoff((float)Math.min(1, (distsq)*0.0005+0.1));
        float v=(float)Math.max(0, 1-(distsq)*0.0005);
        
        game.p.heartOpacity=v;
        game.normal.setVolume(v);
        game.fast.setVolume(v);
        game.slow.setVolume(v);
        cable.setStart(x, y, 0);
        cable.update(4);
        
        if(!active){
            dx=(xStart-xOffset-x)*.2;
            dy=(yStart-yOffset-y)*.2;
        }
    }
    @Override
    public void Render(){
        cable.render();
        //game.drawRectangle((int)x-4, (int)y-4, (int)x+4, (int)y+4, 0xff808080, false);
        //game.drawLine((int)x, (int)y, 320, 180, 0x99000000);
    }

    @Override
    public void Use() {
        
    }
}
