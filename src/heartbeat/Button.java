/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heartbeat;

import java.awt.event.MouseEvent;

/**
 *
 * @author pcowal15
 */
public class Button {
    HeartGame game;
    int x1, y1, x2, y2, xOffset, yOffset;
    int color, colorHover, colorClicked, colorBorder, id;
    String text;
    public Button(HeartGame game, String text, int x1, int y1, int w, int h, int id){
        this.game=game;
        this.text=text;
        this.x1=x1;
        this.y1=y1;
        this.x2=x1+w;
        this.y2=y1+20;
        this.id=id;
        color=0x80C0C0C0;
        colorHover=0x80808080;
        colorClicked=0x80404040;
        colorBorder=0xFF202020;
    }
    public boolean clicked(){
        if(game.mbPressed[MouseEvent.BUTTON1]){
            if(hover()&&game.click){
                return true;
            }
        }
        return false;
    }
    public boolean hover(){
        return (game.mouseX>x1 && game.mouseX<x2 && game.mouseY>y1 && game.mouseY<y2);
        
    }
    public void render(){
        int c=color;
        if(hover()){
            if(game.mbPressed[MouseEvent.BUTTON1]){
                c=colorClicked;
            }
            else{
                c=colorHover;
            }
        }
        game.drawRectangle(x1+xOffset, y1+yOffset, x2+xOffset, y2+yOffset, c, false);
        game.drawRectangle(x1+xOffset, y1+yOffset, x2+xOffset, y2+yOffset, colorBorder, true);
        game.drawText(text, game.fontBlack, x1+2+xOffset, y1+2+yOffset);
    }
    
}
