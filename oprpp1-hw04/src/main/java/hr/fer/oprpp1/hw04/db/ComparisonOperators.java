package hr.fer.oprpp1.hw04.db;

/**
 * Class that defines static IComparisonOperators
 */
public class ComparisonOperators {

    public static final IComparisonOperator LESS;
    public static final IComparisonOperator LESS_OR_EQUALS;
    public static final IComparisonOperator GREATER;
    public static final IComparisonOperator GREATER_OR_EQUALS;
    public static final IComparisonOperator EQUALS;
    public static final IComparisonOperator NOT_EQUALS;
    public static final IComparisonOperator LIKE;

    static {
        LESS              = (v1, v2) -> v1.compareTo(v2) < 0;
        LESS_OR_EQUALS    = (v1, v2) -> v1.compareTo(v2) <= 0;
        GREATER           = (v1, v2) -> v1.compareTo(v2) > 0;
        GREATER_OR_EQUALS = (v1, v2) -> v1.compareTo(v2) >= 0;
        NOT_EQUALS        = (v1, v2) -> !v1.equals(v2);
        EQUALS            = String::equals;

        LIKE = (v1, v2) -> {
            String[] prefixSuffix = v2.split("\\*");
            String prefix;
            String suffix;

            //If v2 = "*" | "**" | "***"...
            if (prefixSuffix.length == 0) {
                if (v2.length() != 1) {
                    throw new IllegalArgumentException("Pattern can only have one wildcard *.");
                } else {
                    return true;
                }
            }

            //If v2 = text* | text
            if (prefixSuffix.length == 1) {
                if (v2.indexOf('*') != -1) {
                    prefix = prefixSuffix[0];
                    return v1.startsWith(prefix);
                } else {
                    return v1.equals(v2);
                }
            }

            //If v2 = text*text | *text
            if (prefixSuffix.length == 2) {
                prefix = prefixSuffix[0];
                suffix = prefixSuffix[1];

                return v1.startsWith(prefix) && v1.endsWith(suffix) && v1.length() >= v2.length() - 1;
            }

            throw new IllegalArgumentException("Pattern can only have one wildcard *.");
        };
    }
}
