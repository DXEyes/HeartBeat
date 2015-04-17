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
    Button start, exit, credits, options;
    public Menu(HeartGame game){
        this.game=game;
        menuTimer=60;
        active=true;
        start=new Button(game, "START!!!", 120, 120, 80, 16, 1);
        exit=new Button(game, "exit :(", 120, 140, 80, 16, 2);
        game.pulse.y=82;
    }
    public void update(){
        ++t;
        if(active && menuTimer>0)--menuTimer;
        else ++menuTimer;
        
        if(start.clicked())active=false;
        if(exit.clicked())System.exit(0);
    }
    public boolean done(){
        return menuTimer>40;
    }
    public int quad(int offset){
        int q=Math.max(menuTimer-offset, 0);
        return (q*q)/9;
    }
    
    public void render(){
        
        game.drawSprite(game.background, 0, t%320, 0);
        game.drawSprite(game.background, 0, t%320-320, 0);
        game.pulse.render();
        
        int to=quad(30);
        start.yOffset=quad(20);
        exit.yOffset=quad(10);
        
        
        game.drawSpriteScaled(game.title, (game.beat.getBeat()%8==0)?0:1, 160-78, 60-44-to, 160+78, 60+44-to);
        start.render();
        exit.render();
    }
}
