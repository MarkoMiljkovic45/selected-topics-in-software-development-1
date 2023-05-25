package hr.fer.oprpp1.custom.collections;

/**
 * Used fo processing objects using the process() method
 */
@FunctionalInterface
public interface Processor<T> {

    /**
     * Used to execute an operation on value
     * @param value on which the operation will be executed
     */
    void process(T value);
}
