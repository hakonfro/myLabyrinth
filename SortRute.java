public class SortRute extends Rute{

  public SortRute(int x, int y){
    super(x,y);
    farge = "Svart";
    tegn = '#';
  }
  public char tilTegn(){
    return tegn;
  }
  @Override
  public String toString(){
    return farge;
  }

  @Override
  public void gaa(Rute komFra, String utvei){
    return;
  }
}
