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
public class Patient {
    HeartGame game;
    int heartFrame;
    double heartRate;
    
    double temp;
    int heartStatus, nextStatus, statusCounter;
    
    int skin, clothes, hair, eyes;
    
    int blink, bc;
    boolean arrhythmia;
    public static final int STATUS_NORMAL=0;
    public static final int STATUS_TACHYCARDIA=1;
    public static final int STATUS_BRADYCARDIA=-1;
    public static final int STATUS_STOPPED=2;
    
    
    
    
    boolean exiting;
    
    double heartOpacity;
    int sc;
    Sprite heart, body;
    int x;
    int cprCount;
    int id;
    public Patient(HeartGame game){
        
        this.game=game;
        heart=game.heart;
        body=game.bodyHitbox;
        statusCounter=0;
        temp=98;
        heartFrame=7;
        sc=0;
        create();
    }
    
    public void create(){
        skin=(int)(Math.random()*4);
        hair=(int)(Math.random()*4);
        clothes=(int)(Math.random()*4);
        eyes=1+(int)(Math.random()*3);
        
        arrhythmia=false;
        if(Math.random()<0.7){
            if(Math.random()<0.6){
                heartStatus=STATUS_TACHYCARDIA;
            }
            else{
                heartStatus=STATUS_BRADYCARDIA;
            }
        }
        else{
            heartStatus=STATUS_NORMAL;
            arrhythmia=true;
        }
        if(Math.random()<0.3)arrhythmia=true;
        if(Math.random()<0.2)heartStatus=STATUS_STOPPED;
        nextStatus=heartStatus;
        id++;
        
    }
    public void changeStatus(int status, int time){
        nextStatus=status;
        statusCounter=time;
    }
    
    public void exit(){
        if(heartStatus==STATUS_NORMAL && !arrhythmia){
            game.scorekeeper.patientCount++;
            game.scorekeeper.patientsTreated++;
            game.scorekeeper.addPoints(100);
            game.scorekeeper.increaseCombo(1);
            game.feedback.add(new Feedback(game, true));
        }
        else{
            game.scorekeeper.patientCount++;
            if(heartStatus==STATUS_STOPPED)game.scorekeeper.resetCombo();
            else game.scorekeeper.halveCombo();
            game.feedback.add(new Feedback(game, false));
        }
        exiting=true;
    }
    public void update(){
        if(blink<0 && blink>-95)blink=10;
        blink+=(int)(Math.random()*2);
        if(blink>100){
            blink=0;
        }
        if(game.click && getHitbox(game.mouseX, game.mouseY)==Patient.HEAD_HITBOX){
            blink=-100;
            if(bc>=0)bc+=20;
        }
        if(bc>0)bc--;
        if(bc>200){
            game.achievement.display("ARE YOU AWAKE??!?!");
            bc=-1;
        }
        if(exiting){
            int c=0;
            if(x>-200){
                for(Draggable d:game.items){
                    if(d instanceof TachySyringe || d instanceof ArrhSyringe){
                        if(this.getHitbox((int)(d.x-d.w-x), (int)d.y)!=0)d.x-=10;
                        ++c;
                    }
                    if(d instanceof BradyIV){
                        
                        if(this.getHitbox((int)(d.x-d.w-x), (int)d.y+d.h)!=0){
                            d.essential=false;
                            d.x-=10;
                            ++c;
                        }
                    }
                }
                
                x-=10;
                
                if(c>=10 && heartStatus==Patient.STATUS_STOPPED){
                    game.achievement.display("Proper Burial");
                }
            }
            else{
                exiting=false;
                create();
            }
        }
        else{
            if(x<0)x+=10;
        }
        
        if(statusCounter>0)statusCounter--;
        else heartStatus=nextStatus;
        
        boolean beat=(heartFrame==0);
        
        double tempo=1;
        
        if(heartStatus==STATUS_TACHYCARDIA || temp>98){
            tempo=2;
        }
        if(heartStatus==STATUS_BRADYCARDIA){
            tempo=0.5;
        }
        
        
        if(heartStatus==STATUS_STOPPED){
            game.normal.setVolume(0.7f);
            arrhythmia=false;
            if(game.click && this.getHitbox(game.mouseX, game.mouseY)==CHEST_HITBOX){
                int b=(int)((double)game.beat.getBeat()*tempo)%8;
                if(b==0){
                    game.scorekeeper.increaseCombo(0.1);
                    game.scorekeeper.addPoints(5);
                    game.feedback.add(new Feedback(game, "GREAT!", game.mouseX, game.mouseY));
                    cprCount+=3;
                    if(cprCount>16){
                        changeStatus(STATUS_NORMAL,5);
                    }
                    game.cpr.play();
                }
                else if(b==1||b==7){
                    game.scorekeeper.increaseCombo(0.05);
                    game.feedback.add(new Feedback(game, "Good!", game.mouseX, game.mouseY));
                    cprCount+=2;
                    if(cprCount>16){
                        changeStatus(STATUS_NORMAL,5);
                    }
                    game.cpr.play();
                }
                else if(b==2||b==6){
                    game.feedback.add(new Feedback(game, "Okay", game.mouseX, game.mouseY));
                    cprCount+=1;
                }
                else{
                    game.feedback.add(new Feedback(game, "Miss", game.mouseX, game.mouseY));
                    game.scorekeeper.addPoints(-1);
                }
                if(cprCount>16){
                    changeStatus(STATUS_NORMAL,5);
                }
            }
        }
        else{
            cprCount=0;
        }
        heartFrame=(int)((double)game.beat.getBeat()*tempo)%8;
        if(!beat && heartFrame==0){
            if(heartStatus==STATUS_STOPPED){
                game.scorekeeper.decreaseCombo(0.1);
                if(cprCount>0)cprCount--;
            }
            else{
                sc=1;
                if(arrhythmia){
                    sc=(int)(1+Math.random()*40/tempo);
                }
            }
        }
        if(sc>1)sc--;
        if(sc==1){
            sc=0;
            if(tempo==2){
                game.fast.stop();
                game.fast.play();
            }
            else if(tempo<1){
                game.slow.stop();
                game.slow.play();
            }
            else{
                game.normal.stop();
                game.normal.play();
            }
        }
    }
    public int getHitbox(int x, int y){
        if(x>=0 && x<body.width && y>=0 && y<body.height)return body.getPixel(0, x, y);
        return 0;
    }
    public void render(){
        game.drawSprite(game.table, 0, x, 0);
        game.drawSprite(game.patientClothes, clothes,x+24,40);
        game.drawSprite(game.patientSkin, skin,x,0);
        game.drawSprite(game.patientHair, hair,x+30,0);
        if(blink<4 && heartStatus!=STATUS_STOPPED){
            if(blink<0){
                game.drawSprite(game.patientEyes, 4, x+40, 20);
            }
            else{
                game.drawSprite(game.patientEyes, 0, x+40, 20);
            }
        }
        else{
            game.drawSprite(game.patientEyes, eyes, x+40, 20);
        }
        String s="Nothing";
        int c=getHitbox(game.mouseX, game.mouseY);
        
        switch(c){
        case BODY_HITBOX:
            s="Body";
            break;
        case ARM_HITBOX:
            s="Arm";
            break;
        case VEIN_HITBOX:
            s="Vein";
            break;
        case CHEST_HITBOX:
            s="Heart";
            break;
        case HEAD_HITBOX:
            s="Defib";
            break;
        }
        if(game.keysPressed['B'] && game.keysPressed['D']){
            game.drawText(heartStatus+"", game.fontBlack, 100, 40);
            game.drawText(nextStatus+"", game.fontBlack, 100, 60);
            game.drawText(statusCounter+"", game.fontBlack, 100, 80);
        }
        
    }
    public void renderHeart(){
        if(heartStatus==STATUS_STOPPED){
            game.drawSpriteFiltered(heart, 2, 40, 50, (int)(heartOpacity*255), 255, 255, 255);
        }
        else{
            game.drawSpriteFiltered(heart, heartFrame, 40, 50, (int)(heartOpacity*255), 255, 255, 255);
        }
        
    }
    public static final int BODY_HITBOX=0xFF000000;
    public static final int ARM_HITBOX=0xFFFF0000;
    public static final int VEIN_HITBOX=0xFF00FF00;
    public static final int CHEST_HITBOX=0xFFFF00FF;
    public static final int HEAD_HITBOX=0xFF0000FF;
}
