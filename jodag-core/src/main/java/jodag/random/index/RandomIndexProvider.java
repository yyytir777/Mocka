package jodag.random.index;

public interface RandomIndexProvider {

    <T> T getNextIdx(T size);
}
