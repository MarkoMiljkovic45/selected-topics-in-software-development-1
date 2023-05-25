package hr.fer.oprpp1.hw04.db;

import java.util.List;

/**
 * Implementation of IFilter that filters StudentRecords by query condition
 */
public class QueryFilter implements IFilter {

    /**
     * Conditions that need to be satisfied to pass the filter
     */
    private final List<ConditionalExpression> query;

    /**
     * Main constructor to initialize query conditions
     * @param query list of conditional expressions
     */
    public QueryFilter(List<ConditionalExpression> query) {
        this.query = query;
    }

    @Override
    public boolean accepts(StudentRecord record) {
        for (ConditionalExpression expression: query) {
            IFieldValueGetter   fieldGetter        = expression.getFieldGetter();
            IComparisonOperator comparisonOperator = expression.getComparisonOperator();
            String              stringLiteral      = expression.getStringLiteral();

            boolean accept = comparisonOperator.satisfied(fieldGetter.get(record), stringLiteral);

            if (!accept) return false;
        }

        return true;
    }
}
