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
public class ArrhSyringe extends Draggable{
    boolean filled;
    public ArrhSyringe(HeartGame g){
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
                if(game.p.arrhythmia){
                    game.scorekeeper.addPoints(10);
                    game.p.arrhythmia=false;
                }
                else{
                    game.scorekeeper.addPoints(-10);
                }
                filled=false;
                essential=false;
            }
            dx=dy=0;
        }
    }
    
}
