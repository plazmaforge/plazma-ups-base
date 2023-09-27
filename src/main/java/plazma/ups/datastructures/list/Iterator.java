package plazma.ups.datastructures.list;

public interface Iterator<T> {

    boolean hasNext();

    T next();

    void remove();

}
