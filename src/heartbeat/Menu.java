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
public class Menu {
    HeartGame game;
    int menuTimer, t;
    boolean active;
    Button start, exit, scores, credits;
    int nextState;
    
    public Menu(HeartGame game){
        this.game=game;
        menuTimer=80;
        active=true;
        start=new Button(game, "START!!!", 79, 122, 80, 16, 1);
        scores=new Button(game, "Scores!", 161, 122, 80, 16, 2);
        credits=new Button(game, "Credits", 79, 144, 80, 16, 2);
        exit=new Button(game, "exit :(", 161, 144, 80, 16, 2);
        game.pulse.y=82;
    }
    public void update(){
        ++t;
        if(active && menuTimer>0)--menuTimer;
        else ++menuTimer;
        
        if(start.clicked()){
            nextState=HeartGame.STATE_GAME;
            active=false;
            game.music.stop();
        }
        if(scores.clicked()){
            nextState=HeartGame.STATE_SCORES;
            active=false;
        }
        if(credits.clicked()){
            nextState=HeartGame.STATE_CREDITS;
            active=false;
        }
        if(exit.clicked())System.exit(0);
    }
    public boolean done(){
        return menuTimer>80;
    }
    public int quad(int offset){
        int q=Math.max(menuTimer-offset, 0);
        return (q*q)/9;
    }
    
    public void render(){
        
        game.pulse.render();
        
        int to=quad(40);
        start.yOffset=quad(30);
        scores.yOffset=quad(20);
        credits.yOffset=quad(10);
        exit.yOffset=quad(0);
        
        
        game.drawSpriteScaled(game.title, (game.beat.getBeat()%8==0)?0:1, 160-78, 60-44-to, 160+78, 60+44-to);
        start.render();
        scores.render();
        credits.render();
        exit.render();
    }
}
