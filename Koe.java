public class Koe<T> extends Stabel<T>{
  public Koe(){
    super();
  }
  @Override
  public void settInn(T element){
    Node nyNode = new Node();
    nyNode.data = element;
    if (storrelse == 0){
      first = nyNode;
      storrelse++;

    } else{
      Node temp = first;
    while (temp.neste != null){
      temp = temp.neste;
    }
    temp.neste = nyNode;
    storrelse++;
    }
  }
}
