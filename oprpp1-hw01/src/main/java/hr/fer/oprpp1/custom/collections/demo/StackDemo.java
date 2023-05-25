package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.EmptyStackException;
import hr.fer.oprpp1.custom.collections.ObjectStack;

import java.util.Objects;

public class StackDemo {

    public static void main(String[] args) {

        String expression = args[0];
        String[] expressionElements = expression.split(" ");
        ObjectStack stack = new ObjectStack();

        for(String expressionElement: expressionElements) {
            if (Objects.equals(expressionElement, "")) continue;
            try {
                int n = Integer.parseInt(expressionElement);
                stack.push(n);
            } catch (NumberFormatException numberFormatException) {
                try {
                    int b = (int) stack.pop();
                    int a = (int) stack.pop();
                    stack.push(calculate(a, b, expressionElement));
                } catch (EmptyStackException emptyStackException) {
                    System.out.println("The number of operands does not match the number of operations!");
                    return;
                } catch (UnsupportedOperationException unsupportedOperationException) {
                    System.out.println(unsupportedOperationException.getMessage());
                    return;
                }
            }
        }

        if (stack.size() != 1) System.out.println("The number of operands does not match the number of operations!");
        else System.out.println("Expression evaluates to " + (int) stack.pop());
    }

    /**
     * Performs simple mathematical binary operations on a and b and returns the result.
     * @param a first operation element
     * @param b second operation element
     * @param operation operation
     * @return result of operation
     * @throws UnsupportedOperationException if division by 0 is passed or operation is not recognised
     */
    private static int calculate(int a, int b, String operation) {
        switch (operation) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "%":
                return a % b;
            case "/":
                if (b == 0) throw new UnsupportedOperationException("Division by 0 is not allowed!");
                else return a / b;
            default:
                throw new UnsupportedOperationException("Operation not recognized!");
        }
    }
}
