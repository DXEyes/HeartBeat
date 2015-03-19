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
public class Cable {
    HeartGame game;
    CablePoint[] points;
    int width, color;
    public Cable(HeartGame game, double x, double y, double length){
        this.game=game;
        points=new CablePoint[20];
        for(int i=0; i<20; ++i){
            points[i]=new CablePoint(x,y,0,length/20);
        }
        color=0xFFFF0000;
    }
    public void setStart(double x, double y, double z){
        points[0].x=x;
        points[0].y=y;
        points[0].z=z;
        points[0].fix(true);
    }
    public void setEnd(double x, double y, double z){
        points[19].x=x;
        points[19].y=y;
        points[19].z=z;
        points[19].fix(true);
    }
    public void update(){
        for(int n=0; n<20; ++n){
            for(int i=0; i<20; ++i){

                if(i<19)points[i].handleCable(points[i+1]);
                points[i].update();
            }
        }
    }
    public void render(){
        for(int i=0; i<19; ++i){
            game.drawLine((int)points[i].x, (int)(points[i].y+points[i].z/10), (int)points[i+1].x, (int)(points[i+1].y+points[i+1].z/10), color);
        }
    }
}
class CablePoint{
    double x, y, z, l;
    double xv, yv, zv;
    double grav, res, sp;
    boolean fixed;
    public CablePoint(double x, double y, double z, double l){
        this.x=x;
        this.y=y;
        this.z=z;
        this.l=l;
        xv=yv=zv=0;
        grav=.1;
        res=.01;
        sp=.01;
        fixed=false;
    }
    public void fix(boolean fix){
        fixed=fix;
    }
    public void handleCable(CablePoint other){
        double dx=other.x-x;
        double dy=other.y-y;
        double dz=other.z-z;
        double dist=Math.sqrt(dx*dx+dy*dy+dz*dz);
        
        if(dist>l){
            xv+=sp*dx*(dist-l)/(dist+1);
            yv+=sp*dy*(dist-l)/(dist+1);
            zv+=sp*dz*(dist-l)/(dist+1);
            
            other.xv-=sp*dx*(dist-l)/(dist+1);
            other.yv-=sp*dy*(dist-l)/(dist+1);
            other.zv-=sp*dz*(dist-l)/(dist+1);
        }
    }
    public void update(){
        if(fixed)return;
        x+=xv;
        y+=yv;
        z+=zv;
        zv+=grav;
        xv*=(1-res);
        yv*=(1-res);
        zv*=(1-res);
    }
    
}
