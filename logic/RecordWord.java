package logic;
public class RecordWord{
  public AnchorSquare koordinat;
  public String value;
  public int bonus;
  public RecordWord(AnchorSquare k,String v,int b){
    koordinat = k;
    value = v;
    bonus = b;
  }

  public String toString(){
    return koordinat + "->" + value + " = " + bonus;
  }
}
