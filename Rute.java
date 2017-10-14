abstract class Rute{
  protected String farge;
  protected char tegn;
  abstract char tilTegn();
  protected final int x, y;
  protected Labyrint labyrint;
  boolean harVartInnom = false;

  public Rute(int x, int y){
    this.x = x;
    this.y = y;
  }

  public void settRuter(Labyrint labyrint){
    this.labyrint = labyrint;
    nord = labyrint.getRute(x-1, y);
    syd = labyrint.getRute(x + 1, y);
    vest = labyrint.getRute(x, y-1);
    oest = labyrint.getRute(x, y+1);
  }

  Rute nord;
  Rute syd;
  Rute vest;
  Rute oest;

  public void gaa(Rute komFra, String utvei){
    if(harVartInnom) return;

    harVartInnom = true;

    utvei += this.getPos() + " -->";


      if (komFra != nord){
        nord.gaa(this, utvei);
      }
      if (komFra != syd){
        syd.gaa(this, utvei);
      }
      if (komFra != oest){
        oest.gaa(this, utvei);
      }
      if (komFra != vest){
        vest.gaa(this, utvei);
      }
      harVartInnom = false;
  }

  public void finnUtvei(){
    this.gaa(this, null);
  }

  public String getPos(){
    String tmpX = x + 1+"";
    String tmpY = y+ 1 +"";
    String posisjon = "(" + tmpY +"," + tmpX +")";
    return posisjon;
  }
}
