/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heartbeat;

import java.io.Serializable;

/**
 *
 * @author pcowal15
 */
public class Signature implements Serializable{
    boolean[][] pixels;
    boolean edited;
    public Signature(){
        pixels=new boolean[100][20];
        edited=false;
    }
    public void edit(int x, int y){
        if(x>=0 && x<100 && y>=0 && y<20){
            edited=true;
            pixels[x][y]=true;
        }
    }
    public void clear(){
        pixels=new boolean[100][20];
        edited=false;
    }
    public void render(HeartGame game, int x, int y){
        game.drawRectangle(x, y, x+100, y+20, 0xFF202020, true);
        for(int i=0; i<100; ++i){
            for(int j=0; j<20; ++j){
                if(pixels[i][j])game.setPixel(i+x, j+y, 0xFF202020);
            }
        }
    }
}
