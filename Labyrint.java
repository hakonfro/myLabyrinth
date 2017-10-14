import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

public class Labyrint{

  public int antKol;
  public int antRad;

  Rute[] [] ruteArray;

  public Koe<String> losninger;

  public static Labyrint lesFraFil(File fil) throws Exception {
    Labyrint ferdigLabyrint = null;

    ferdigLabyrint = new Labyrint(fil);
    return ferdigLabyrint;
  }
  private Labyrint(File fil)throws Exception {

      lagArray(fil);

      for (int i = 0; i < ruteArray.length; i++){
        for (int j = 0; j < antKol; j++){
          ruteArray[i][j].settRuter(this);
        }
      }

  }

  public Rute getRute(int x, int y){
    try{
      return ruteArray[x][y];
    } catch(Exception e){
      return null;
    }
  }

  public Koe  <String> finnUtveiFra(int kol, int rad){
    losninger = new Koe<String>();
    ruteArray[rad][kol].finnUtvei();
    return losninger;
  }

  @Override
  public String toString(){
    String tekstLabyrint = "";
    String linje = "";
    for(Rute[] kolonne : ruteArray){
      linje = "";

      for(Rute rute : kolonne){
        linje += rute.tilTegn();
      }
      tekstLabyrint += linje + "\n";
    }
    return tekstLabyrint;
  }

  public boolean erAapning(int x, int y){
    return x == antRad -1 || x == 0 || y == antKol - 1  || y == 0;

  }

  private void lagArray(File fil)throws Exception{

    Scanner in = new Scanner(fil);
    antRad = in.nextInt();
    antKol = in.nextInt();
    in.nextLine();

    //sjekker at alt blir lest inn riktig
    //System.out.println(antRad);
    //System.out.println(antKol);

    ruteArray = new Rute[antRad][antKol];

    String linje;

    int teller = 0;

    while(in.hasNextLine()){
      linje = in.nextLine();

      for(int i = 0; i < antKol; i++){
        char ch = linje.charAt(i);

        if(ch == '.'){
          if(erAapning(teller, i)){
            ruteArray[teller][i] = new Aapning(teller, i);
            //sjekker at if testene gaar som det skal
            //System.out.println("åpning laget [" + teller + "] [" + i +"]");
          } else{
            ruteArray[teller][i] = new HvitRute(teller, i);
          }
        }
        else if (ch == '#'){
          ruteArray[teller][i] = new SortRute(teller, i);
        } else{
          System.out.println("noe gikk galt");
        }
      }
      teller++;
    }

  }
}
