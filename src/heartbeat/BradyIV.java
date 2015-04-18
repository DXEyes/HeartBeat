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
    int patientID;
    public BradyIV(HeartGame game) {
        super(game, game.iv, 120+(int)(Math.random()*50), -50+(int)(Math.random()*50));
        cable=new Cable(game, x, y, 200, 0xFF202020);
        cable.setStart(x, y, -200);
        cable.setEnd(x+w, y-h, 0);
        patientID=-1;
    }

    @Override
    public void Update() {
        if(this.active)game.tutorial.displayTutorial(8);
        
        cable.update(4);
        int offset=2;
        cable.setEnd(x+w-offset, y-h+offset, 0);
        
        if(!active){
            int c=game.p.getHitbox((int)x-w, (int)y+h);
            if(c==Patient.VEIN_HITBOX){
                if(subimg<3)subimg++;
                count-=3;
                if(count%100<3)game.scorekeeper.addPoints(5);
                
                if(patientID<0)patientID=game.p.id;
                else if(patientID!=game.p.id){
                    patientID=game.p.id;
                    game.scorekeeper.halveCombo();
                    game.feedback.add(new Feedback(game, "Shared Needle :(", (int)x, (int)y));
                }
            }
            else if(c==Patient.ARM_HITBOX){
                if(subimg<3)subimg++;
                count--;
                if(patientID<0)patientID=game.p.id;
            }
            else{
                subimg=0;
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
                    if(Math.random()<0.3){
                        game.p.changeStatus(Patient.STATUS_STOPPED, 5);
                    }
                }
            }
            
        }
        else{
            count=600;
            subimg=0;
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Render() {
        cable.render();
        if(game.keysPressed['B'] && game.keysPressed['D']){
            game.drawText(""+count, game.fontBlack, (int)x+10, (int)y-6);
        }
        else if(active){
            game.drawText("Atropine", game.fontBlack, (int)x+10, (int)y-6);
        }
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Use() {
        if(x>180 && x<230 && y<35){
            alive=false;
            game.destroy.play();
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
