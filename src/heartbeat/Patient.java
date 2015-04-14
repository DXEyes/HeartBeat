/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heartbeat;

import gamebase.Sprite;

/**
 *
 * @author pcowal15
 */
public class Patient {
    HeartGame game;
    int heartFrame;
    double heartRate;
    
    double temp;
    int heartStatus, statusCounter;
    
    boolean arrhythmia;
    public static final int STATUS_NORMAL=0;
    public static final int STATUS_TACHYCARDIA=1;
    public static final int STATUS_BRADYCARDIA=-1;
    public static final int STATUS_STOPPED=2;
    
    Sprite heart, body;
    public Patient(HeartGame game, int heartStatus, boolean arrhythmia){
        
        this.game=game;
        heart=game.heart;
        body=game.body;
        this.heartStatus=heartStatus;
        statusCounter=0;
        temp=98;
        heartFrame=7;
    }
    public void update(){
        
        boolean beat=(heartFrame==0);
        
        double tempo=1;
        
        if(heartStatus==STATUS_TACHYCARDIA || temp>98){
            tempo=2;
        }
        if(heartStatus==STATUS_BRADYCARDIA){
            tempo=0.5;
        }
        
        
        heartFrame=(int)((double)game.beat.getBeat()*1)%8;
        if(!beat && heartFrame==0){
            game.heartbeat.stop();
            game.heartbeat.play();
        }
    }
    public int getHitbox(int x, int y){
        if(x>=0 && x<body.width && y>=0 && y<body.height)return body.getPixel(0, x, y);
        return 0;
    }
    public void render(){
        game.drawSprite(body, 0,0,0);
        game.drawSprite(heart, heartFrame, 40, 50);
        
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
        case HEART_HITBOX:
            s="Heart";
            break;
        case DEFIB_HITBOX:
            s="Defib";
            break;
        }
        //game.drawText(Integer.toHexString(c)+": "+s, game.font, 100, 40);
        
    }
    public static final int BODY_HITBOX=0xFF000000;
    public static final int ARM_HITBOX=0xFFFF0000;
    public static final int VEIN_HITBOX=0xFF00FF00;
    public static final int HEART_HITBOX=0xFFFF00FF;
    public static final int DEFIB_HITBOX=0xFF0000FF;
}
