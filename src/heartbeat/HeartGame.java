/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heartbeat;

import gamebase.GameLoop;
import gamebase.Sound;
import gamebase.Sprite;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pcowal15
 */
public class HeartGame extends GameLoop{
    Sprite body, heart, font;
    BeatController beat;
    Sound music, heartbeat;
    Patient p;
    Stethoscope scope;
    boolean dragging;
    
    public HeartGame(){
        super(320,180);
    }
    
    @Override
    public void Initialize() {
        try {
            body=new Sprite("patient_hitbox_temp.png",-1);
            heart=new Sprite("heartbeat_strip8.png",-1);
            font=new Sprite("12x8test2t_strip96.png",-1);
            music=new Sound("Purple I.wav");
            heartbeat=new Sound("normal_01.wav");
            beat=new BeatController(music, 125./2);
            p=new Patient(this,1);
            scope=new Stethoscope(this);
            beat.start();
            dragging=false;
            
        } catch (IOException ex) {
            Logger.getLogger(HeartGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void Update() {
        p.update();
        scope.update();
    }

    @Override
    public void Render() {
        p.render();
        scope.render();
        this.drawText("Hello World!", font, 100, 20);
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        
    }
    
    public void drawLine(int x1, int y1, int x2, int y2, int color){
        if(x1==x2){
            for(int j=y1; j<y2; ++j){
                setPixel(x1,j,color);
            }
            for(int j=y2; j<y1; ++j){
                setPixel(x1,j,color);
            }
            return;
        }
        if(x1>x2){
            drawLine(x2,y2,x1,y1,color);
            return;
        }
        int xx=x2-x1;
        int yy=y2-y1;
        int offset=xx/2;
        int j=y1;
        for(int i=x1; i<x2; ++i){
            setPixel(i,j,color);
            offset+=Math.abs(yy);
            while(offset>xx){
                offset-=xx;
                j+=(yy>0)?1:-1;
                setPixel(i,j,color);
            }
        }
        
    }

    @Override
    public void mouseWheelRotated(int i) {
        
    }
}
