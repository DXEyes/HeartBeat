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
    Sprite heart, body;
    public Patient(HeartGame game, double heartRate){
        this.game=game;
        heart=game.heart;
        body=game.body;
        this.heartRate=heartRate;
        heartFrame=7;
    }
    public void update(){
        heartFrame=game.beat.getBeat()%8;
    }
    public void render(){
        game.drawSprite(body, 0,0,0);
        game.drawSprite(heart, heartFrame, 45, 45);
    }
}
