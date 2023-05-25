package hr.fer.oprpp1.custom.collections;

@FunctionalInterface
public interface Tester<T> {
    /**
     * Tests if the object obj satisfies a certain condition
     * @param obj object to be tested
     * @return true if obj satisfies a condition, false otherwise
     */
    boolean test(T obj);
}
