/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heartbeat;

/**
 *
 * @author Sonar
 */
public class Achievement {
    HeartGame game;
    String title;
    int t, y;
    public Achievement(HeartGame game){
        this.game=game;
        title="";
        t=0;
        y=180;
    }
    public void display(String achievement){
        if(t==0){
            this.title=achievement;
            t=300;
            y=180;
        }
    }
    public void render(){
        if(t>0){
            t--;
            if(t>200 && y>160){
                y--;
            }
            else if(t<30)y++;
            game.drawRectangle(0, y, 320, 181, 0xFF303030, false);
            game.drawText("ACHIEVEMENT: "+title, game.fontWhite, 2, y+2);
        }
        
        
    }
}
