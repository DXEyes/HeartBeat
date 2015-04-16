/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heartbeat;

import gamebase.Sprite;

/**
 *
 * @author pcowal15
 */
public class BradyIV extends Draggable{
    Cable cable;
    int count;
    public BradyIV(HeartGame game) {
        super(game, game.iv, 120+(int)(Math.random()*50), -50+(int)(Math.random()*50));
        cable=new Cable(game, x, y, 200, 0xFF202020);
        cable.setStart(x, y, -200);
        cable.setEnd(x+w, y-h, 0);
    }

    @Override
    public void Update() {
        cable.update(4);
        int offset=2;
        cable.setEnd(x+w-offset, y-h+offset, 0);
        
        if(!active){
            int c=game.p.getHitbox((int)x-w, (int)y+h);
            if(c==Patient.VEIN_HITBOX){
                count-=2;
            }
            else if(c==Patient.ARM_HITBOX){
                count--;
            }
            if(count<0){
                count=600;
                if(game.p.heartStatus==Patient.STATUS_BRADYCARDIA){
                    game.scorekeeper.addPoints(10);
                    if(Math.random()<0.2){
                        game.p.changeStatus(Patient.STATUS_TACHYCARDIA,5);
                    }
                    else{
                        game.p.changeStatus(Patient.STATUS_NORMAL,5);
                        if(Math.random()<0.2){
                            game.p.arrhythmia=true;
                        }
                    }
                }
                else{
                    game.scorekeeper.addPoints(-10);
                    if(Math.random()<0.5){
                        game.p.changeStatus(Patient.STATUS_TACHYCARDIA,5);
                    }
                    else{
                        game.p.arrhythmia=true;
                    }
                }
            }
            
        }
        else{
            count=600;
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Render() {
        cable.render();
        game.drawText(count+"", game.font, (int)x+30, (int)y);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Use() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
