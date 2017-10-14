public class Aapning extends HvitRute{

  public Aapning(int x, int y){
    super(x, y);
  }

  @Override
  public void gaa(Rute komFra, String utvei){
    utvei += this.getPos();
    labyrint.losninger.settInn(utvei);
  }
}
