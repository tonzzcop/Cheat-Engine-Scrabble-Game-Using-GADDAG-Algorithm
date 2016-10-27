package logic;
public class AnchorSquare{
  public int x,y;
  public int xParent,yParent;
  public boolean horisontal;
  public AnchorSquare(int yy,int xx,int yp,int xp,boolean h){
    x = xx;
    y = yy;
    yParent = yp;
    xParent = xp;
    horisontal = h;
  }

  public String toString(){
    return "[X:"+x+",Y:"+y+",yParent:"+yParent+",xParent:"+xParent+",H:"+horisontal+"]";
  }
}
