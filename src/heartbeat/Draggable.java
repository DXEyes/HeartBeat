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
    int useTimer, retTimer;
    int xStart, yStart, xOffset, yOffset, w, h;
    double x, y, dx, dy;
    boolean active, essential;
    
    public Draggable(HeartGame game, Sprite spr, int xStart, int yStart){
        this.game=game;
        this.spr=spr;
        x=xStart;
        y=yStart;
        dx=dy=0;
        w=spr.width/2;
        h=spr.height/2;
        this.xStart=xStart;
        this.yStart=yStart;
        active=false;
        essential=false;
        subimg=0;
    }
    
    public void update(){
        double dragSpeed=.5;
        if(useTimer>0)useTimer--;
        else if(!active){
            if(essential){
                if(retTimer>300){
                    x+=(xStart-x)*dragSpeed;
                    y+=(yStart-y)*dragSpeed;
                }
                else{
                    retTimer++;
                }
            }
            double s=Math.sqrt(dx*dx+dy*dy);
            double friction=.5;
            if(s<friction){
                dx=dy=0;
            }
            else{
                dx*=(s-friction)/s;
                dy*=(s-friction)/s;
            }
            x+=dx;
            y+=dy;
            if(game.click&&!game.dragging
                    &&game.mouseX>x-w&&game.mouseX<x+w
                    &&game.mouseY>y-h&&game.mouseY<y+h){
                active=true;
                game.dragging=true;
                
                //xOffset=game.mouseX-xStart;
                //yOffset=game.mouseY-yStart;
            }
        }
        else{
            retTimer=0;
            dx=(game.mouseX-xOffset-x)*dragSpeed;
            dy=(game.mouseY-yOffset-y)*dragSpeed;
            x+=dx;
            y+=dy;
            
            if(game.click){
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
