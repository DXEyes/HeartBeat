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
public class TachySyringe extends Draggable{
    boolean filled;
    public TachySyringe(HeartGame g){
        super(g, g.syringe, 350+(int)(Math.random()*50), 5+(int)(Math.random()*50));
        filled=true;
    }
    @Override
    public void Update() {
        if(!filled && subimg<6)subimg++;
    }

    @Override
    public void Render() {
        
    }

    @Override
    public void Use() {
        if(filled){
            int c=game.p.getHitbox((int)x-w, (int)y);
            if(c==Patient.ARM_HITBOX || c==Patient.VEIN_HITBOX){
                //game.feedback.add(new Feedback(game, c+"", (int)x-w, (int)y-h));
                if(game.p.nextStatus==Patient.STATUS_TACHYCARDIA){
                    game.p.changeStatus(Patient.STATUS_NORMAL, 200);
                    game.scorekeeper.addPoints(10);
                }
                else if(Math.random()<0.3){
                    game.p.changeStatus(Patient.STATUS_BRADYCARDIA, 200);
                    game.scorekeeper.addPoints(-10);
                }
                filled=false;
                essential=false;
            }
            dx=dy=0;
        }
    }
    
}
