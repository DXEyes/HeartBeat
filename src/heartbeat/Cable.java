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
    public Cable(HeartGame game, double x, double y, double length, int color){
        this.game=game;
        points=new ArrayList<CablePoint>();
        for(int i=0; i<20; ++i){
            CablePoint p=new CablePoint(x,y,0,length/20);
            if(i>0)p.attach(points.get(i-1));
            points.add(p);
            
        }
        start=points.get(0);
        end1=points.get(19);
        this.color=color;
    }
    public Cable(HeartGame game, double x, double y, double length, int split, int color){
        this.game=game;
        points=new ArrayList<CablePoint>();
        for(int i=0; i<20; ++i){
            CablePoint p=new CablePoint(x,y,0,length/20);
            if(i>0)p.attach(points.get(i-1));
            points.add(p);
            
        }
        for(int i=split; i<20; ++i){
            CablePoint p=new CablePoint(x,y,0,length/20);
            if(i>split)p.attach(points.get(points.size()-1));
            else p.attach(points.get(split));
            points.add(p);
        }
        start=points.get(0);
        end1=points.get(19);
        end2=points.get(points.size()-1);
        this.color=color;
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
    public void setEnd2(double x, double y, double z){
        end2.x=x;
        end2.y=y;
        end2.z=z;
        end2.fix(true);
    }
    public void update(int steps){
        for(int n=0; n<steps; ++n){
            for(CablePoint p:points){
                p.update();
            }
        }
    }
    public void render(){
        double w=game.width/2;
        double h=game.height/2;
        double sf=1000;
        
        for(CablePoint p : points){
            for(CablePoint p2 : p.attachedPoints){
                double p1z=sf/(sf+p.z);
                double p2z=sf/(sf+p2.z);
                game.drawLine((int)((p.x-w)*p1z+w), (int)((p.y-h)*p1z+h), (int)((p2.x-w)*p2z+w), (int)((p2.y-h)*p2z+h), color);
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
        res=.04;
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
