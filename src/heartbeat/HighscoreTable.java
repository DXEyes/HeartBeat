/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heartbeat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author pcowal15
 */
public class HighscoreTable implements Serializable{
    ArrayList<HighscoreEntry> entries;
    public HighscoreTable(){
        entries=new ArrayList<HighscoreEntry>();
    }
    public void render(HeartGame game){
        int y=10;
        for(HighscoreEntry h:entries){
            h.signature.render(game, 10, y);
            game.drawText(h.grade +"   "+h.score, game.fontBlack, 100, y);
            y+=30;
            if(y>180)break;
        }
    }
    public void addEntry(Signature s, int score, String grade){
        entries.add(new HighscoreEntry(s, score, grade));
        Collections.sort(entries);
    }
    
}
class HighscoreEntry implements Comparable, Serializable{
    Signature signature;
    int score;
    String grade;
    public HighscoreEntry(Signature signature, int score, String grade){
        this.signature=signature;
        this.score=score;
        this.grade=grade;
    }
            
    @Override
    public int compareTo(Object t) {
        HighscoreEntry h=(HighscoreEntry) t;
        return (score-h.score);
    }
}