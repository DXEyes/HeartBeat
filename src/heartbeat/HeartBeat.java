/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heartbeat;

import gamebase.GameFrame;

/**
 *
 * @author pcowal15
 */
public class HeartBeat {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new GameFrame(new HeartGame(),"HeartBeat v1.0",60).Run();
    }
    
}
