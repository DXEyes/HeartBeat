/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heartbeat;

import java.util.ArrayList;

/**
 *
 * @author pcowal15
 */
public class Cable {
    HeartGame game;
    ArrayList<CablePoint> points;
    CablePoint start, end1, end2;
    int width, color;
    public Cable(HeartGame game, double x, double y, double length){
        this.game=game;
        points=new ArrayList<CablePoint>();
        for(int i=0; i<20; ++i){
            CablePoint p=new CablePoint(x,y,0,length/20);
            if(i>0)p.attach(points.get(i-1));
            points.add(p);
            
        }
        start=points.get(0);
        end1=points.get(19);
        color=0xFFFF0000;
    }
    public void setStart(double x, double y, double z){
        start.x=x;
        start.y=y;
        start.z=z;
        start.fix(true);
    }
    public void setEnd(double x, double y, double z){
        end1.x=x;
        end1.y=y;
        end1.z=z;
        end1.fix(true);
    }
    public void update(int steps){
        for(int n=0; n<steps; ++n){
            for(CablePoint p:points){
                p.update();
            }
        }
    }
    public void render(){
        for(CablePoint p : points){
            for(CablePoint p2 : p.attachedPoints){
                game.drawLine((int)p.x, (int)(p.y+p.z/5), (int)p2.x, (int)(p2.y+p2.z/5), color);
            }
        }
    }
}
class CablePoint{
    double x, y, z, l;
    double xv, yv, zv;
    double grav, res, sp;
    boolean fixed;
    ArrayList<CablePoint> attachedPoints;
    public CablePoint(double x, double y, double z, double l){
        this.x=x;
        this.y=y;
        this.z=z;
        this.l=l;
        xv=yv=zv=0;
        grav=.2;
        res=.05;
        sp=.1;
        fixed=false;
        attachedPoints=new ArrayList<CablePoint>();
    }
    public void attach(CablePoint p){
        attachedPoints.add(p);
    }
    public void fix(boolean fix){
        fixed=fix;
    }
    public void handleCable(CablePoint other){
        double dx=other.x-x;
        double dy=other.y-y;
        double dz=other.z-z;
        double dist=Math.sqrt(dx*dx+dy*dy+dz*dz);
        
        if(dist!=l){
            double mag=sp*(dist-l)/(dist+1);
            xv+=dx*mag;
            yv+=dy*mag;
            zv+=dz*mag;
            
            other.xv-=dx*mag;
            other.yv-=dy*mag;
            other.zv-=dz*mag;
        }
    }
    public void update(){
        for(CablePoint p:attachedPoints){
            handleCable(p);
        }
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
