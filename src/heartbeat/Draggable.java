/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heartbeat;

import gamebase.Sprite;
import java.awt.event.MouseEvent;

/**
 *
 * @author pcowal15
 */
public abstract class Draggable {
    HeartGame game;
    Sprite spr;
    int subimg;
    int xStart, yStart, xOffset, yOffset, w, h;
    double x, y;
    boolean active;
    
    public Draggable(HeartGame game, Sprite spr, int xStart, int yStart){
        this.game=game;
        this.spr=spr;
        x=xStart;
        y=yStart;
        w=spr.width/2;
        h=spr.height/2;
        this.xStart=xStart;
        this.yStart=yStart;
        active=false;
        subimg=0;
    }
    
    public void update(){
        double dragSpeed=.1;
        if(!active){
            x+=(xStart-x)*dragSpeed;
            y+=(yStart-y)*dragSpeed;
            
            if(game.mbPressed[MouseEvent.BUTTON1]&&!game.dragging
                    &&game.mouseX>x-w&&game.mouseX<x+w
                    &&game.mouseY>y-h&&game.mouseY<y+h){
                active=true;
                game.dragging=true;
                xOffset=game.mouseX-xStart;
                yOffset=game.mouseY-yStart;
            }
        }
        else{
            x+=(game.mouseX-xOffset-x)*dragSpeed;
            y+=(game.mouseY-yOffset-y)*dragSpeed;
            
            if(!game.mbPressed[MouseEvent.BUTTON1]){
                active=false;
                game.dragging=false;
                Use();
            }
        }
        Update();
    }
    public abstract void Update();
    public void render(){
        if(spr!=null){
            game.drawSprite(spr, subimg, (int)x-w, (int)y-h);
        }
        Render();
    }
    public abstract void Render();
    public abstract void Use();
}
