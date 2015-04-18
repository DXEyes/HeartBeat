/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heartbeat;

/**
 *
 * @author pcowal15
 */
public class ScrollingBackground {
    static int t;
    public static void render(HeartGame game){
        ++t;
        game.drawSprite(game.background, 0, t%320, 0);
        game.drawSprite(game.background, 0, t%320-320, 0);
    }
}
