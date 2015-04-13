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
    public TachySyringe(HeartGame g, int x, int y){
        super(g, g.syringe, x, y);
        filled=true;
    }
    @Override
    public void Update() {
        
    }

    @Override
    public void Render() {
        
    }

    @Override
    public void Use() {
        
        if(filled){
            int c=game.p.getHitbox((int)x-w, (int)y-h);
            if(c!=0){
                game.feedback.add(new Feedback(game, c+"", (int)x-w, (int)y-h));
                filled=false;
                essential=false;
            }
            dx=dy=0;
        }
    }
    
}
