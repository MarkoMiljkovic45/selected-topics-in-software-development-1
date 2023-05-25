package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.scripting.elems.*;
import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.nodes.EchoNode;
import hr.fer.oprpp1.custom.scripting.nodes.ForLoopNode;
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SmartScriptParserTest {

    @Test
    public void testSmartScriptParserExample1() {
        String text = readExample(1);
        DocumentNode correctData = new DocumentNode();

        correctData.addChildNode(new TextNode("Ovo je \nsve jedan text node\n"));

        assertEquals(new SmartScriptParser(text).getDocumentNode(), correctData);
    }


    @Test
    public void testSmartScriptLexerExample2() {
        String text = readExample(2);
        DocumentNode correctData = new DocumentNode();

        correctData.addChildNode(new TextNode("Ovo je \nsve jedan \\{$ text node\n"));

        assertEquals(new SmartScriptParser(text).getDocumentNode(), correctData);
    }


    @Test
    public void testSmartScriptLexerExample3() {
        String text = readExample(3);
        DocumentNode correctData = new DocumentNode();

        correctData.addChildNode(new TextNode("Ovo je \nsve jedan \\\\\\{$text node\n"));

        assertEquals(new SmartScriptParser(text).getDocumentNode(), correctData);
    }


    @Test
    public void testSmartScriptLexerExample4() {
        String text = readExample(4);
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
    }


    @Test
    public void testSmartScriptLexerExample5() {
        String text = readExample(5);
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
    }


    @Test
    public void testSmartScriptLexerExample6() {
        String text = readExample(6);
        DocumentNode correctData = new DocumentNode();

        correctData.addChildNode(new TextNode("Ovo je OK "));
        correctData.addChildNode(new EchoNode(new ElementString("\"String ide\nu više redaka\nčak tri\"")));

        assertEquals(new SmartScriptParser(text).getDocumentNode(), correctData);
    }

    @Test
    public void testSmartScriptLexerExample7() {
        String text = readExample(7);
        DocumentNode correctData = new DocumentNode();

        correctData.addChildNode(new TextNode("Ovo je isto OK "));
        correctData.addChildNode(new EchoNode(new ElementString("\"String ide\nu \\\"više\\\" \\nredaka\novdje a stvarno četiri\"")));

        assertEquals(new SmartScriptParser(text).getDocumentNode(), correctData);
    }

    @Test
    public void testSmartScriptLexerExample8() {
        String text = readExample(8);
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
    }

    @Test
    public void testSmartScriptLexerExample9() {
        String text = readExample(9);
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
    }

    @Test
    public void testSmartScriptLexerExample10() {
        String text = readExample(10);
        DocumentNode correctData = new DocumentNode();

        correctData.addChildNode(new TextNode("This is sample text.\r\n"));

        ForLoopNode forNode1 = new ForLoopNode(
                new ElementVariable("i"),
                new ElementConstantInteger(1),
                new ElementConstantInteger(10),
                new ElementConstantInteger(1)
        );

        forNode1.addChildNode(new TextNode("This is "));
        forNode1.addChildNode(new EchoNode(new ElementVariable("i")));
        forNode1.addChildNode(new TextNode("-th time this message is generated.\r\n"));

        correctData.addChildNode(forNode1);

        ForLoopNode forNode2 = new ForLoopNode(
                new ElementVariable("i"),
                new ElementConstantInteger(0),
                new ElementConstantInteger(10),
                new ElementConstantInteger(2)
        );

        forNode2.addChildNode(new TextNode("sin("));
        forNode2.addChildNode(new EchoNode(new ElementVariable("i")));
        forNode2.addChildNode(new TextNode("^2) = "));
        forNode2.addChildNode(new EchoNode(
                new ElementVariable("i"),
                new ElementVariable("i"),
                new ElementOperator("*"),
                new ElementFunction("sin"),
                new ElementString("\"0.000\""),
                new ElementFunction("decfmt")
        ));

        correctData.addChildNode(forNode2);

        assertEquals(new SmartScriptParser(text).getDocumentNode(), correctData);
    }

    @Test
    public void testSmartScriptLexerExample11() {
        String text = readExample(11);
        DocumentNode correctData = new DocumentNode();

        correctData.addChildNode(new ForLoopNode(
                new ElementVariable("sco_re"),
                new ElementConstantInteger(-1),
                new ElementConstantInteger(10),
                new ElementConstantInteger(1)
        ));

        assertEquals(new SmartScriptParser(text).getDocumentNode(), correctData);
    }

    @Test
    public void testSmartScriptLexerExample12() {
        String text = readExample(12);
        DocumentNode correctData = new DocumentNode();

        correctData.addChildNode(new ForLoopNode(
                new ElementVariable("year"),
                new ElementConstantInteger(1),
                new ElementVariable("last_year"),
                null
        ));

        assertEquals(new SmartScriptParser(text).getDocumentNode(), correctData);
    }

    @Test
    public void testSmartScriptLexerExample13() {
        String text = readExample(13);
        DocumentNode correctData = new DocumentNode();

        correctData.addChildNode(new ForLoopNode(
                new ElementVariable("i"),
                new ElementConstantDouble(-1.35),
                new ElementVariable("bbb"),
                new ElementConstantInteger(1)
        ));

        assertEquals(new SmartScriptParser(text).getDocumentNode(), correctData);
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
}
