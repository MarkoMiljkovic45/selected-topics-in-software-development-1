package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class QueryLexerTest {

    @Test
    public void testQueryLexer1() {
        QueryLexer lex = new QueryLexer("query jmbag=\"0000000003\"");

        QueryToken[] expected = {
                new QueryToken(QueryTokenType.KEYWORD, "query"),
                new QueryToken(QueryTokenType.FIELD, "jmbag"),
                new QueryToken(QueryTokenType.COMPARISON_OPERATOR, "="),
                new QueryToken(QueryTokenType.STRING_LITERAL, "0000000003")
        };

        QueryToken[] actual = getTokenArrayFromLexer(lex);

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testQueryLexer2() {
        QueryLexer lex = new QueryLexer("query \tlastName = \"Blažić\"");

        QueryToken[] expected = {
                new QueryToken(QueryTokenType.KEYWORD, "query"),
                new QueryToken(QueryTokenType.FIELD, "lastName"),
                new QueryToken(QueryTokenType.COMPARISON_OPERATOR, "="),
                new QueryToken(QueryTokenType.STRING_LITERAL, "Blažić")
        };

        QueryToken[] actual = getTokenArrayFromLexer(lex);

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testQueryLexer3() {
        QueryLexer lex = new QueryLexer("query firstName>\"A\" and  lastName LIKE \"B*ć\"");

        QueryToken[] expected = {
                new QueryToken(QueryTokenType.KEYWORD, "query"),
                new QueryToken(QueryTokenType.FIELD, "firstName"),
                new QueryToken(QueryTokenType.COMPARISON_OPERATOR, ">"),
                new QueryToken(QueryTokenType.STRING_LITERAL, "A"),
                new QueryToken(QueryTokenType.LOGICAL_OPERATOR, "and"),
                new QueryToken(QueryTokenType.FIELD, "lastName"),
                new QueryToken(QueryTokenType.COMPARISON_OPERATOR, "LIKE"),
                new QueryToken(QueryTokenType.STRING_LITERAL, "B*ć")
        };

        QueryToken[] actual = getTokenArrayFromLexer(lex);

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testQueryLexer4() {
        QueryLexer lex = new QueryLexer("query firstName>\"A\" And firstName<\"C\" AND lastName LIKE \"B*ć\" aNd jmbag>\"0000000002\"\n");

        QueryToken[] expected = {
                new QueryToken(QueryTokenType.KEYWORD, "query"),
                new QueryToken(QueryTokenType.FIELD, "firstName"),
                new QueryToken(QueryTokenType.COMPARISON_OPERATOR, ">"),
                new QueryToken(QueryTokenType.STRING_LITERAL, "A"),
                new QueryToken(QueryTokenType.LOGICAL_OPERATOR, "And"),
                new QueryToken(QueryTokenType.FIELD, "firstName"),
                new QueryToken(QueryTokenType.COMPARISON_OPERATOR, "<"),
                new QueryToken(QueryTokenType.STRING_LITERAL, "C"),
                new QueryToken(QueryTokenType.LOGICAL_OPERATOR, "AND"),
                new QueryToken(QueryTokenType.FIELD, "lastName"),
                new QueryToken(QueryTokenType.COMPARISON_OPERATOR, "LIKE"),
                new QueryToken(QueryTokenType.STRING_LITERAL, "B*ć"),
                new QueryToken(QueryTokenType.LOGICAL_OPERATOR, "aNd"),
                new QueryToken(QueryTokenType.FIELD, "jmbag"),
                new QueryToken(QueryTokenType.COMPARISON_OPERATOR, ">"),
                new QueryToken(QueryTokenType.STRING_LITERAL, "0000000002")
        };

        QueryToken[] actual = getTokenArrayFromLexer(lex);

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testQueryLexer5() {
        QueryLexer lex = new QueryLexer("\t    \n\t  exit  \t \n");

        assertEquals(new QueryToken(QueryTokenType.KEYWORD, "exit"), lex.nextToken());
        assertEquals(new QueryToken(QueryTokenType.EOF, null), lex.nextToken());
        assertThrows(NoSuchElementException.class, lex::nextToken);
    }

    @Test
    public void testQueryLexerOpenedStringLiteral() {
        QueryLexer lex = new QueryLexer("\t    \n\t  \"Opened string literal < firstName  \t \n");

        assertThrows(IndexOutOfBoundsException.class, lex::nextToken);
    }

    /**
     * Helper method to collect all tokens from lexer and store them in an array
     * @param lexer to be used in generating tokens
     * @return all tokens from lexer stored in array
     */
    private QueryToken[] getTokenArrayFromLexer(QueryLexer lexer) {
        List<QueryToken> tokenList = new ArrayList<>();

        while (lexer.nextToken().getType() != QueryTokenType.EOF) {
            tokenList.add(lexer.getCurrentToken());
        }

        return tokenList.toArray(new QueryToken[1]);
    }
}
