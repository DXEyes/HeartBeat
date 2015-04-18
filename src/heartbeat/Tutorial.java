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
 * @author Sonar
 */
public class Tutorial {
    HeartGame game;
    boolean[] triggered;
    int currentTutorial, duration;
    public Tutorial(HeartGame game){
        this.game=game;
        triggered=new boolean[10];
        triggered[0]=true;
        currentTutorial=0;
        duration=0;
    }
    public boolean displayTutorial(int t){
        if(t<0){
            currentTutorial=t;
        }
        else if(!triggered[t]){
            triggered[t]=true;
            duration=0;
            currentTutorial=t;
            if(t<5)game.p.arrhythmia=false;
            return true;
        }
        return false;
    }
    public void render(){
        duration++;
        if(duration>400)currentTutorial=-1;
        
        //game.blendMode=GameLoop.BM_DIFFERENCE;
        Sprite font=game.fontTutorial;
        switch(currentTutorial){
            case 0: 
                game.drawText("Click Here>", font, 40, 145);
                //game.p.changeStatus(Patient.STATUS_NORMAL, 2);
                if(game.dragging)displayTutorial(1);
                break;
            case 1:
                game.drawText("<Move Here", font, 70, 55);
                if(!game.dragging)currentTutorial=-1;
                break;
            case 2:
                game.drawText2("This patient's heart\nsounds like it's in a\nSTABLE condition.", font, 70, 15);
                break;
            case 3:
                game.drawText2("An abnormally fast\nheart rate is called\nTACHYCARDIA.", font, 70, 15);
                break;
            case 4:
                game.drawText2("An abnormally slow\nheart rate is called\nBRADYCARDIA.", font, 70, 15);
                break;
            case 5:
                game.drawText2("Heart rates that sound\noff-beat are caused by\nATRIAL FIBRILLATION.", font, 70, 15);
                break;
            case 6:
                game.drawText2("Oh no! This patient's\nheart is STOPPED!\nPerform CPR to the BEAT!", font, 70, 15);
                break;
            case 7:
                game.drawText2("<Apply Here", font, 85, 75);
                break;
            case 8:
                game.drawText2("<Apply here, and\naim for the VEIN", font, 88, 90);
                break;
        }
        //game.blendMode=GameLoop.BM_NORMAL;
    }
    
}
