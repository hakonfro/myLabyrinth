import javafx.application.Application;
import javafx.scene.input.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import java.util.Optional;
import java.io.File;
import java.util.Scanner;

public class Laglabyrint extends Application{
  GUIRute [][] ruter;
  Koe<String> losninger;
  Labyrint l;
  File fil;
  int storrelse = 20;
  GridPane rutenett;
  BorderPane root;
  Stage stage;
  Label losningTeller;

  public void start(Stage stage)throws Exception{
    this.stage = stage;
    this.losninger = new Koe<String>();
    this.rutenett = new GridPane();
    this.root = new BorderPane();

    root.setTop(lagToppBoks());

    root.setCenter(rutenett);
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setTitle("Make labyrinth");
    stage.show();
  }

  private HBox lagToppBoks() throws Exception{
    TextField filFelt = new TextField();

    losningTeller = new Label("Number of solutions: "+0);

    Button velgFilKnapp = new Button("Choose file...");
    velgFilKnapp.setOnAction(new FilVelger(filFelt));

    Button lastInnKnapp = new Button("Load");
    lastInnKnapp.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event){
        try{
          String filnavn = filFelt.getText();
          fil = new File(filnavn);
          l = Labyrint.lesFraFil(fil);
          if(l !=null){
            ruter = new GUIRute[l.antRad][l.antKol];
            for(int rad = 0; rad<l.antRad; rad++){
              for(int kol = 0; kol < l.antKol; kol++){
                Rute rute = l.getRute(rad, kol);
                ruter[rad][kol] = new GUIRute(storrelse, rute, l);
                rutenett.add(ruter[rad][kol], kol, rad);
              }
            }
            root.setCenter(rutenett);
            stage.sizeToScene();
          }
          else System.out.println("Something went wrong");

        } catch(Exception e){
          System.out.println("Something went wrong" + e);
        }
      }
    });
    HBox returBoks = new HBox(50, velgFilKnapp, filFelt, lastInnKnapp, losningTeller);
    return returBoks;
  }

  private class GUIRute extends Pane{
    boolean hvit = true;
    int x;
    int y;
    //kontruktor:
    public GUIRute(int storrelse, Rute rute, Labyrint l){
      if(rute.tilTegn() == '#'){
        hvit = false;
      }
      this.x = rute.x;
      this.y = rute.y;

      setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
      null, new BorderWidths(1))));
      setMinWidth(storrelse);
      setMinHeight(storrelse);
      oppdaterFarge();


      addEventHandler(MouseEvent.MOUSE_CLICKED ,new EventHandler<MouseEvent>() {
        public void handle(MouseEvent event){
          System.out.println("click!");
          losningTeller.setText("Number of solutions: " + 0);

          losninger = l.finnUtveiFra(y,x);
          if(!losninger.erTom()){
            losningTeller.setText("Antall losninger: "+losninger.storrelse());
            String losningString = losninger.fjern();
            boolean[][] grafiskLab = losningStringTilTabell(losningString, l.antKol, l.antRad);
            visLosning(grafiskLab);
          } else{
            System.out.println("No solutions!");
          }
        }
      });
    }

    //fargelegger losningen gul
    public void visLosning(boolean[][] grafiskLab){
      for(int i = 0; i < l.antRad; i++){
        for(int j = 0; j < l.antKol; j++){
          if(ruter[i][j].hvit){
            ruter[i][j].oppdaterFarge();
          }
          if(grafiskLab[i][j]) {
            ruter[i][j].setBackground(new Background(new BackgroundFill(Color.YELLOW, null, null)));
          }
        }
      }
    }
    public void oppdaterFarge(){
      if (hvit){
        setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID,
        null, new BorderWidths(1))));
      } else{
        setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
      }
    }
    @Override
    public String toString(){
      return hvit ? "." : "#";
    }
  }
  private class FilVelger implements EventHandler<ActionEvent>{
    TextField filFelt;
    public FilVelger(TextField filFelt){
      this.filFelt = filFelt;
    }
    @Override
    public void handle(ActionEvent e){
      FileChooser fileChooser  = new FileChooser();
      fileChooser.setTitle("Choose file");
      File selectedFile = fileChooser.showOpenDialog(null);
      if(selectedFile != null){
        filFelt.setText(selectedFile.getPath());
      }
    }
  }

  public static boolean[][] losningStringTilTabell(String losningString, int bredde, int hoyde){
    boolean[][] losning = new boolean[hoyde][bredde];
    java.util.regex.Pattern p = java.util.regex.Pattern.compile("\\(([0-9]+),([0-9]+)\\)");
    java.util.regex.Matcher m = p.matcher(losningString.replaceAll("\\s",""));
    while(m.find()){
      int x = Integer.parseInt(m.group(1))-1;
      int y = Integer.parseInt(m.group(2))-1;
      losning[y][x] = true;
    }
    return losning;
  }
}
