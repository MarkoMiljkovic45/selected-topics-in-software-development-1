package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;

import java.util.Arrays;
import java.util.Objects;

/**
 * A node representing a single for-loop construct.
 */
public class ForLoopNode extends Node {

    /**
     * Variable that stores current loop iteration progress
     */
    private final ElementVariable variable;
    /**
     * The starting value of variable
     */
    private final Element startExpression;
    /**
     * The end value of variable. When endExpression is reached or exceeded, loop iteration stops
     */
    private final Element endExpression;
    /**
     * The step size of each iteration.
     */
    private final Element stepExpression;

    /**
     * The main constructor used to set all the private read-only properties
     * @param variable the variable expression that stores the loop iteration progress
     * @param startExpression the starting value of variable
     * @param endExpression the end value, upper limit, of variable
     * @param stepExpression the step size for each iteration step
     */
    public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression, Element stepExpression) {
        this.variable = variable;
        this.startExpression = startExpression;
        this.endExpression = endExpression;
        this.stepExpression = stepExpression;
    }

    /**
     * Getter method for variable
     * @return variable
     */
    public ElementVariable getVariable() {
        return variable;
    }

    /**
     * Getter method for startExpression
     * @return startExpression
     */
    public Element getStartExpression() {
        return startExpression;
    }

    /**
     * Getter method for endExpression
     * @return endExpression
     */
    public Element getEndExpression() {
        return endExpression;
    }

    /**
     * Getter method for stepExpression
     * @return stepExpression
     */
    public Element getStepExpression() {
        return stepExpression;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("{$ FOR ");
        sb.append(variable.asText()).append(" ");
        sb.append(startExpression.asText()).append(" ");
        sb.append(endExpression.asText()).append(" ");

        if (stepExpression != null) {
            sb.append(stepExpression.asText()).append(" ");
        }

        sb.append("$}\n");

        if (getChildren() != null) {
            for (Node n : getChildren()) {
                sb.append(n.toString());
            }
        }

        sb.append("{$END$}\n");

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ForLoopNode that = (ForLoopNode) o;
        return variable.equals(that.variable) &&
                startExpression.equals(that.startExpression) &&
                endExpression.equals(that.endExpression) &&
                Objects.equals(stepExpression, that.stepExpression) &&
                Arrays.equals(getChildren(), that.getChildren());
    }

    @Override
    public int hashCode() {
        return Objects.hash(variable, startExpression, endExpression, stepExpression);
    }
}
