import java.util.NoSuchElementException;
import java.util.Iterator;
/* Denne klassen er ansvarlig for to underliggende klasser (Koe og
 OrdnetLenkeliste). Endringer kan paavirke andre klassers funksjonalitet */
public class Stabel<T> implements Liste<T>{
  protected Node first;
  protected int storrelse = 0;

  public Stabel(){
    this.first = null;
  }
  public void settInn(T element){
    Node nyNode = new Node();
    nyNode.data = element;
    nyNode.neste = first;
    first = nyNode;
    storrelse++;
  }
  public boolean erTom(){
    return storrelse == 0;
  }
  public int storrelse(){
    return storrelse;
    }
    public T fjern(){
      if (first == null){
        throw new NoSuchElementException();
      }
      T element = first.data;
      first = first.neste;
      storrelse = storrelse - 1;
      return element;
    }
  class Node {
    T data;
    Node neste;
  }
  public Iterator<T> iterator(){
    return new ListeIterator();
  }
  public class ListeIterator implements Iterator<T>{
    Node her;
    ListeIterator(){
      her= first;
    }
    public boolean hasNext(){
      if (her == null) return false;
      //else if (her.neste == null) return false;
      else return true;
    }
    public T next(){
      T info = her.data;
      her = her.neste;
      return info;
    }
  }
}
