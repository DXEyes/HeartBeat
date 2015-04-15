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
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pcowal15
 */
public class HeartGame extends GameLoop{
    Sprite bodyHitbox, patient, heart, font, syringe, iv, scope, table;
    BeatController beat;
    Sound music, normal, fast, slow;
    ArrayList<Button> buttons;
    Patient p;
    ArrayList<Draggable> items;
    ArrayList<Feedback> feedback;
    boolean dragging, canClick, click;
    int prevFPS;
    
    int state;
    public static final int STATE_GAME=1;
    public static final int STATE_FEEDBACK=2;
    public static final int STATE_MENU=0;
    Cable c;
    public HeartGame(){
        super(320,180);
    }
    
    @Override
    public void Initialize() {
        try {
            items=new ArrayList<Draggable>();
            buttons=new ArrayList<Button>();
            feedback=new ArrayList<Feedback>();
            
            scope=new Sprite("artwork/scope.png",-1);
            table=new Sprite("table_temp.png", -1);
            patient=new Sprite("artwork/patients/patient colored.png", -1);
            bodyHitbox=new Sprite("hitbox.png",-1);
            heart=new Sprite("heartbeat_strip8.png",-1);
            font=new Sprite("12x8FontFixed.png",94);
            syringe=new Sprite("Syringe_strip4.png",-1);
            iv=new Sprite("artwork/IV.png", -1);
            
            
            normal=new Sound("normal2.wav");
            fast=new Sound("fast.wav");
            slow=new Sound("slow2.wav");
            
            
            buttons.add(new Button(this, "Stable!", 200,80,113,18, 0));
            buttons.add(new Button(this, "Tachycardia", 200,100,113,18, 1));
            buttons.add(new Button(this, "Bradycardia", 200,120,113,18, 2));
            buttons.add(new Button(this, "Atrial Fib.", 200,140,113,18, 3));
            p=new Patient(this,1,true);
            
            items.add(new Stethoscope(this));
            
            
            dragging=false;
            
            playTune(4);
            
        } catch (IOException ex) {
            Logger.getLogger(HeartGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void Update() {
        if(prevFPS!=fps){
            prevFPS=fps;
            System.out.println("FPS: "+fps);
        }
        if(canClick){
            if(mbPressed[MouseEvent.BUTTON1]){
                click=true;
                canClick=false;
            }
        }
        else{
            click=false;
            if(!mbPressed[MouseEvent.BUTTON1]){
                canClick=true;
            }
        }
        p.update();
        for(Draggable d:items){
            d.update();
        }
        for(Button b:buttons){
            if(b.clicked()){
                switch(b.id){
                    case 0:
                        p.exit();
                        break;
                    case 1:
                        items.add(new TachySyringe(this));
                        break;
                    case 2:
                        items.add(new BradyIV(this));
                        break;
                    case 3:
                        items.add(new ArrhSyringe(this));
                        break;
                }
            }
        }
        
    }

    @Override
    public void Render() {
        p.render();
        blur((int)(600*p.heartOpacity));
        p.renderHeart();
        for(Draggable d:items){
            d.render();
        }
        for(Button b:buttons){
            b.render();
        }
        ListIterator l=feedback.listIterator();
        while(l.hasNext()){
            Feedback f=(Feedback)l.next();
            f.render();
            if(f.life<=0)l.remove();
        }
        
        
        
        //this.drawText("Hello World!", font, 100, 20);
        //this.drawText("The quick brown fox", font, 100, 80);
        
    }
    
    public void blur(int amt){
        if(amt>127){
            for(int i=0; i<screen.length-1; ++i){
                screen[i]=blendColor(blendColor(screen[i+1],screen[i],127),0,20);
            }
            for(int i=screen.length-1; i>0; --i){
                screen[i]=blendColor(screen[i-1],screen[i],127);
            }
            blur(amt-127);
        }
        else{
            for(int i=0; i<screen.length-1; ++i){
                screen[i]=blendColor(blendColor(screen[i+1],screen[i],amt),0,amt/6);
            }
            for(int i=screen.length-1; i>0; --i){
                screen[i]=blendColor(screen[i-1],screen[i],amt);
            }
        }
    }
    
    public void playTune(int i){
        switch(i){
            case 1:
                music=new Sound("heartbeat soundtrack/particles_70.wav");
                beat=new BeatController(music, 70.);
                beat.start();
                break;
            case 2:
                music=new Sound("heartbeat soundtrack/johnson glitch mix_75.wav");
                beat=new BeatController(music, 75.);
                beat.start();
                break;
            case 3:
                music=new Sound("heartbeat soundtrack/silvertone_2_100.wav");
                beat=new BeatController(music, 100);
                beat.start();
                break;
            case 4:
                music=new Sound("heartbeat soundtrack/lo_instrumental_100p4.wav");
                beat=new BeatController(music, 100.4);
                beat.start();
                break;
        }
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
