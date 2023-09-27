package plazma.ups.base.util.list;

public interface Iterator<T> {

    boolean hasNext();

    T next();

    void remove();

}
