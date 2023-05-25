package hr.fer.oprpp1.custom.scripting.lexer;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SmartScriptLexerTest {

    @Test
    public void testSmartScriptLexerExample1() {
        String text = readExample(1);
        SmartScriptToken[] correctData = {
                new SmartScriptToken(SmartScriptTokenType.TEXT, "Ovo je \nsve jedan text node\n")
        };
        checkTokenStream(new SmartScriptLexer(text), correctData);
    }

    @Test
    public void testSmartScriptLexerExample2() {
        String text = readExample(2);
        SmartScriptToken[] correctData = {
                new SmartScriptToken(SmartScriptTokenType.TEXT, "Ovo je \nsve jedan \\{$ text node\n")
        };
        checkTokenStream(new SmartScriptLexer(text), correctData);
    }

    @Test
    public void testSmartScriptLexerExample3() {
        String text = readExample(3);
        SmartScriptToken[] correctData = {
                new SmartScriptToken(SmartScriptTokenType.TEXT, "Ovo je \nsve jedan \\\\\\{$text node\n")
        };
        checkTokenStream(new SmartScriptLexer(text), correctData);
    }

    @Test
    public void testSmartScriptLexerExample4() {
        String text = readExample(4);
        assertThrows(SmartScriptLexerException.class, () -> new SmartScriptLexer(text).nextToken());
    }

    @Test
    public void testSmartScriptLexerExample5() {
        String text = readExample(5);
        assertThrows(SmartScriptLexerException.class, () -> new SmartScriptLexer(text).nextToken());
    }

    @Test
    public void testSmartScriptLexerExample6() {
        String text = readExample(6);
        SmartScriptToken[] correctData = {
                new SmartScriptToken(SmartScriptTokenType.TEXT, "Ovo je OK "),
                new SmartScriptToken(SmartScriptTokenType.OPENED_TAG, null),
                new SmartScriptToken(SmartScriptTokenType.KEYWORD, "="),
                new SmartScriptToken(SmartScriptTokenType.STRING, "\"String ide\nu više redaka\nčak tri\""),
                new SmartScriptToken(SmartScriptTokenType.CLOSED_TAG, null)
        };
        checkTokenStream(new SmartScriptLexer(text), correctData);
    }

    @Test
    public void testSmartScriptLexerExample7() {
        String text = readExample(7);
        SmartScriptToken[] correctData = {
                new SmartScriptToken(SmartScriptTokenType.TEXT, "Ovo je isto OK "),
                new SmartScriptToken(SmartScriptTokenType.OPENED_TAG, null),
                new SmartScriptToken(SmartScriptTokenType.KEYWORD, "="),
                new SmartScriptToken(SmartScriptTokenType.STRING, "\"String ide\nu \\\"više\\\" \\nredaka\novdje a stvarno četiri\""),
                new SmartScriptToken(SmartScriptTokenType.CLOSED_TAG, null)
        };
        checkTokenStream(new SmartScriptLexer(text), correctData);
    }

    @Test
    public void testSmartScriptLexerExample8() {
        String text = readExample(8);
        assertThrows(SmartScriptLexerException.class, () -> {
            SmartScriptLexer lexer = new SmartScriptLexer(text);
            SmartScriptToken token = lexer.nextToken();
            while (token != null) {
                if (token.getType() == SmartScriptTokenType.OPENED_TAG) lexer.setState(SmartScriptLexerState.TAG);
                if (token.getType() == SmartScriptTokenType.CLOSED_TAG) lexer.setState(SmartScriptLexerState.BASIC);
                token = lexer.nextToken();
            }
        });
    }

    @Test
    public void testSmartScriptLexerExample9() {
        String text = readExample(9);
        assertThrows(SmartScriptLexerException.class, () -> {
            SmartScriptLexer lexer = new SmartScriptLexer(text);
            SmartScriptToken token = lexer.nextToken();
            while (token != null) {
                if (token.getType() == SmartScriptTokenType.OPENED_TAG) lexer.setState(SmartScriptLexerState.TAG);
                if (token.getType() == SmartScriptTokenType.CLOSED_TAG) lexer.setState(SmartScriptLexerState.BASIC);
                token = lexer.nextToken();
            }
        });
    }

    @Test
    public void testSmartScriptLexerExample10() {
        String text = readExample(10);
        SmartScriptToken[] correctData = {
                new SmartScriptToken(SmartScriptTokenType.TEXT, "This is sample text.\r\n"),
                new SmartScriptToken(SmartScriptTokenType.OPENED_TAG, null),
                new SmartScriptToken(SmartScriptTokenType.KEYWORD, "FOR"),
                new SmartScriptToken(SmartScriptTokenType.VARIABLE, "i"),
                new SmartScriptToken(SmartScriptTokenType.INTEGER_CONSTANT, 1),
                new SmartScriptToken(SmartScriptTokenType.INTEGER_CONSTANT, 10),
                new SmartScriptToken(SmartScriptTokenType.INTEGER_CONSTANT, 1),
                new SmartScriptToken(SmartScriptTokenType.CLOSED_TAG, null),
                new SmartScriptToken(SmartScriptTokenType.TEXT, "This is "),
                new SmartScriptToken(SmartScriptTokenType.OPENED_TAG, null),
                new SmartScriptToken(SmartScriptTokenType.KEYWORD, "="),
                new SmartScriptToken(SmartScriptTokenType.VARIABLE, "i"),
                new SmartScriptToken(SmartScriptTokenType.CLOSED_TAG, null),
                new SmartScriptToken(SmartScriptTokenType.TEXT, "-th time this message is generated.\r\n"),
                new SmartScriptToken(SmartScriptTokenType.OPENED_TAG, null),
                new SmartScriptToken(SmartScriptTokenType.KEYWORD, "END"),
                new SmartScriptToken(SmartScriptTokenType.CLOSED_TAG, null),
                new SmartScriptToken(SmartScriptTokenType.OPENED_TAG, null),
                new SmartScriptToken(SmartScriptTokenType.KEYWORD, "FOR"),
                new SmartScriptToken(SmartScriptTokenType.VARIABLE, "i"),
                new SmartScriptToken(SmartScriptTokenType.INTEGER_CONSTANT, 0),
                new SmartScriptToken(SmartScriptTokenType.INTEGER_CONSTANT, 10),
                new SmartScriptToken(SmartScriptTokenType.INTEGER_CONSTANT, 2),
                new SmartScriptToken(SmartScriptTokenType.CLOSED_TAG, null),
                new SmartScriptToken(SmartScriptTokenType.TEXT, "sin("),
                new SmartScriptToken(SmartScriptTokenType.OPENED_TAG, null),
                new SmartScriptToken(SmartScriptTokenType.KEYWORD, "="),
                new SmartScriptToken(SmartScriptTokenType.VARIABLE, "i"),
                new SmartScriptToken(SmartScriptTokenType.CLOSED_TAG, null),
                new SmartScriptToken(SmartScriptTokenType.TEXT, "^2) = "),
                new SmartScriptToken(SmartScriptTokenType.OPENED_TAG, null),
                new SmartScriptToken(SmartScriptTokenType.KEYWORD, "="),
                new SmartScriptToken(SmartScriptTokenType.VARIABLE, "i"),
                new SmartScriptToken(SmartScriptTokenType.VARIABLE, "i"),
                new SmartScriptToken(SmartScriptTokenType.OPERATOR, '*'),
                new SmartScriptToken(SmartScriptTokenType.FUNCTION, "sin"),
                new SmartScriptToken(SmartScriptTokenType.STRING, "\"0.000\""),
                new SmartScriptToken(SmartScriptTokenType.FUNCTION, "decfmt"),
                new SmartScriptToken(SmartScriptTokenType.CLOSED_TAG, null),
                new SmartScriptToken(SmartScriptTokenType.OPENED_TAG, null),
                new SmartScriptToken(SmartScriptTokenType.KEYWORD, "END"),
                new SmartScriptToken(SmartScriptTokenType.CLOSED_TAG, null)
        };
        checkTokenStream(new SmartScriptLexer(text), correctData);
    }

    @Test
    public void testSmartScriptLexerExample11() {
        String text = readExample(11);
        SmartScriptToken[] correctData = {
                new SmartScriptToken(SmartScriptTokenType.OPENED_TAG, null),
                new SmartScriptToken(SmartScriptTokenType.KEYWORD, "FOR"),
                new SmartScriptToken(SmartScriptTokenType.VARIABLE, "sco_re"),
                new SmartScriptToken(SmartScriptTokenType.STRING, "\"-1\""),
                new SmartScriptToken(SmartScriptTokenType.INTEGER_CONSTANT, 10),
                new SmartScriptToken(SmartScriptTokenType.STRING, "\"1\""),
                new SmartScriptToken(SmartScriptTokenType.CLOSED_TAG, null),
                new SmartScriptToken(SmartScriptTokenType.OPENED_TAG, null),
                new SmartScriptToken(SmartScriptTokenType.KEYWORD, "END"),
                new SmartScriptToken(SmartScriptTokenType.CLOSED_TAG, null)
        };
        checkTokenStream(new SmartScriptLexer(text), correctData);
    }

    @Test
    public void testSmartScriptLexerExample12() {
        String text = readExample(12);
        SmartScriptToken[] correctData = {
                new SmartScriptToken(SmartScriptTokenType.OPENED_TAG, null),
                new SmartScriptToken(SmartScriptTokenType.KEYWORD, "FOR"),
                new SmartScriptToken(SmartScriptTokenType.VARIABLE, "year"),
                new SmartScriptToken(SmartScriptTokenType.INTEGER_CONSTANT, 1),
                new SmartScriptToken(SmartScriptTokenType.VARIABLE, "last_year"),
                new SmartScriptToken(SmartScriptTokenType.CLOSED_TAG, null),
                new SmartScriptToken(SmartScriptTokenType.OPENED_TAG, null),
                new SmartScriptToken(SmartScriptTokenType.KEYWORD, "END"),
                new SmartScriptToken(SmartScriptTokenType.CLOSED_TAG, null)
        };
        checkTokenStream(new SmartScriptLexer(text), correctData);
    }

    @Test
    public void testSmartScriptLexerExample13() {
        String text = readExample(13);
        SmartScriptToken[] correctData = {
                new SmartScriptToken(SmartScriptTokenType.OPENED_TAG, null),
                new SmartScriptToken(SmartScriptTokenType.KEYWORD, "FOR"),
                new SmartScriptToken(SmartScriptTokenType.VARIABLE, "i"),
                new SmartScriptToken(SmartScriptTokenType.DOUBLE_CONSTANT, -1.35),
                new SmartScriptToken(SmartScriptTokenType.VARIABLE, "bbb"),
                new SmartScriptToken(SmartScriptTokenType.STRING, "\"1\""),
                new SmartScriptToken(SmartScriptTokenType.CLOSED_TAG, null),
                new SmartScriptToken(SmartScriptTokenType.OPENED_TAG, null),
                new SmartScriptToken(SmartScriptTokenType.KEYWORD, "END"),
                new SmartScriptToken(SmartScriptTokenType.CLOSED_TAG, null)
        };
        checkTokenStream(new SmartScriptLexer(text), correctData);
    }

    private String readExample(int n) {
        try(InputStream is = this.getClass().getClassLoader().getResourceAsStream("extra/primjer"+n+".txt")) {
            if(is==null) throw new RuntimeException("Datoteka extra/primjer"+n+".txt je nedostupna.");
            byte[] data = is.readAllBytes();
            return new String(data, StandardCharsets.UTF_8);
        } catch(IOException ex) {
            throw new RuntimeException("Greška pri čitanju datoteke.", ex);
        }
    }

    private void checkTokenStream(SmartScriptLexer lexer, SmartScriptToken[] correctData) {
        int counter = 0;
        for(SmartScriptToken expected : correctData) {
            SmartScriptToken actual = lexer.nextToken();
            if (actual.getType() == SmartScriptTokenType.OPENED_TAG) lexer.setState(SmartScriptLexerState.TAG);
            if (actual.getType() == SmartScriptTokenType.CLOSED_TAG) lexer.setState(SmartScriptLexerState.BASIC);
            String msg = "Checking token "+counter + ":";
            assertEquals(expected.getType(), actual.getType(), msg);
            assertEquals(expected.getValue(), actual.getValue(), msg);
            counter++;
        }
    }
}
