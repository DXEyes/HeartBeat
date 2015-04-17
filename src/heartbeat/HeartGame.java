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
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pcowal15
 */
public class HeartGame extends GameLoop{
    Sprite bodyHitbox, patientSkin, patientHair, patientClothes, patientEyes,
     heart, fontBlack, fontWhite, syringe, iv, scope, table, logo, background, title;
    Logo logoAnim;
    
    Menu menu;
    BeatController beat;
    Sound music, normal, fast, slow;
    ArrayList<Button> buttons;
    Patient p;
    PulseAnimation pulse;
    ArrayList<Draggable> items;
    ArrayList<Feedback> feedback;
    boolean dragging, canClick, click;
    int prevFPS;
    Scorekeeper scorekeeper;
    int state;
    public static final int STATE_GAME=2;
    public static final int STATE_FEEDBACK=3;
    public static final int STATE_MENU=1;
    public static final int STATE_INTRO=0;
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
            
            pulse=new PulseAnimation(this, 140,60);
            
            title=new Sprite("artwork/title_strip2.png",-1);
            background=new Sprite("artwork/tilebg.png",-1);
            logo=new Sprite("artwork/logo_strip4.png", -1);
            scope=new Sprite("artwork/scope.png",-1);
            table=new Sprite("artwork/table_temp.png", -1);
            patientSkin=new Sprite("artwork/patient_skin_strip4.png", -1);
            patientHair=new Sprite("artwork/hair_strip4.png", -1);
            patientClothes=new Sprite("artwork/clothes_strip4.png", -1);
            patientEyes=new Sprite("artwork/eyes_strip5.png", -1);
            bodyHitbox=new Sprite("artwork/hitbox.png",-1);
            heart=new Sprite("artwork/heartbeat_strip8.png",-1);
            fontBlack=new Sprite("artwork/12x8FontFixed.png",94);
            fontWhite=new Sprite("artwork/12x8FontWhite.png",94);
            syringe=new Sprite("artwork/Syringe_strip4.png",-1);
            iv=new Sprite("artwork/IV.png", -1);
            
            this.backgroundColor=0xFF000000;
            normal=new Sound("normal2.wav");
            fast=new Sound("fast.wav");
            slow=new Sound("slow2.wav");
            
            
            buttons.add(new Button(this, "Stable!", 200,80,113,18, 0));
            buttons.add(new Button(this, "Tachycardia", 200,100,113,18, 1));
            buttons.add(new Button(this, "Bradycardia", 200,120,113,18, 2));
            buttons.add(new Button(this, "Atrial Fib.", 200,140,113,18, 3));
            p=new Patient(this);
            
            items.add(new Stethoscope(this));
            
            scorekeeper=new Scorekeeper(this);
            dragging=false;
            
            logoAnim=new Logo(this);
            
            state=STATE_GAME;
            
            menu=new Menu(this);
            playTune(3);
            
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
        
        if(state==STATE_GAME)gameUpdate();
        
        if(state==STATE_MENU)menu.update();
    }
    
    public void gameUpdate(){
        p.update();
        ListIterator l=items.listIterator();
        while(l.hasNext()){
            Draggable d=(Draggable)l.next();
            d.update();
            if(!d.essential && (d.x<-50 || d.x>width+50 || d.y<-50 || d.y>height+50))l.remove();
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
        if(state==STATE_INTRO){
            logoAnim.render();
            if(logoAnim.done()){
                pulse.render();
                int b=beat.getBeat()/8;
                drawText(beat.getBeat()/8+" ",fontWhite,0,0);
                if(b==31){
                    blendMode=BM_DIFFERENCE;
                    this.drawRectangle(0, 0, width, height, 0xFFFFFFFF, false);
                }
                if(b==32){
                    state=STATE_MENU;
                    this.backgroundColor=0;
                }
            }
        }
        if(state==STATE_MENU){
            menu.render();
            
        }
        
        if(state==STATE_GAME){
            drawSprite(background, 0, 0, 0);
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
            scorekeeper.render();
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
    public void drawText2(String text, Sprite font, int x, int y){
        Scanner s=new Scanner(text);
        int y1=y;
        while(s.hasNextLine()){
            this.drawText(s.nextLine(), font, x, y1);
            y1+=font.height+1;
        }
        
    }
    
    @Override
    public void mouseWheelRotated(int i) {
        
    }
}
