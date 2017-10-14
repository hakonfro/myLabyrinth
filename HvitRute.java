public class HvitRute extends Rute{

  public HvitRute(int x, int y){
    super(x,y);
    farge = "Hvit";
    tegn = '.';
  }
  public char tilTegn(){
    return tegn;
  }
  @Override
  public String toString(){
    return "(" + x + "," + y + ")";
  }
}
