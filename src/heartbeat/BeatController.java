/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heartbeat;

import gamebase.Sound;

/**
 *
 * @author pcowal15
 */
public class BeatController {
    long startTime;
    double bpm;
    Sound song;
    boolean playing;
    public BeatController(Sound song, double bpm){
        this.song=song;
        this.bpm=bpm;
        playing=false;
    }
    public int getBeat(){
        if(!playing)return -1;
        long time=System.currentTimeMillis()-startTime;
        return (int)((time*bpm*8)/(1000*60));
    }
    
    public void start(){
        song.play();
        startTime=System.currentTimeMillis();
        playing=true;
    }
}
