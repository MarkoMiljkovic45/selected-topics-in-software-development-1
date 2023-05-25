package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.ObjectStack;
import hr.fer.oprpp1.custom.scripting.elems.*;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexerState;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptTokenType;
import hr.fer.oprpp1.custom.scripting.nodes.*;

/**
 * An implementation of a parser for the <i>Smart Script</i> language
 */
public class SmartScriptParser {

    /**
     * Source text lexer
     */
    private final SmartScriptLexer lexer;
    /**
     * The root of the syntax tree
     */
    private final DocumentNode documentNode;

    /**
     * The main constructor used to initialize the parser
     * @param documentBody text of the document to be parsed
     * @throws SmartScriptParserException if a parsing error occurs
     */
    public SmartScriptParser(String documentBody) {
        this.lexer = new SmartScriptLexer(documentBody);

        try {
            lexer.nextToken();
        } catch (Exception e) {
            throw new SmartScriptParserException(e.getMessage());
        }

        this.documentNode = parse();
    }

    /**
     * Getter method for documentNode
     * @return documentNode
     */
    public DocumentNode getDocumentNode() {
        return documentNode;
    }

    /**
     * Helper method used for parsing the source text
     * @return syntax tree of document
     */
    private DocumentNode parse() {
        DocumentNode doc = new DocumentNode();
        ObjectStack stack = new ObjectStack();
        stack.push(doc);

        try {
            while (!isTokenOfType(SmartScriptTokenType.EOF)) {

                if (isTokenOfType(SmartScriptTokenType.TEXT)) {
                    Node n = (Node) stack.peek();
                    n.addChildNode(parseText());
                    lexer.nextToken();
                    continue;
                }

                if (isTokenOfType(SmartScriptTokenType.OPENED_TAG)) {
                    lexer.setState(SmartScriptLexerState.TAG);
                    lexer.nextToken();

                    if (!isTokenOfType(SmartScriptTokenType.KEYWORD)) {
                        throw new SmartScriptParserException("Keyword expected.");
                    }

                    if ("=".equals(lexer.getCurrentToken().getValue())) {
                        lexer.nextToken();
                        Node n = (Node) stack.peek();
                        n.addChildNode(parseEcho());
                        continue;
                    }

                    if ("FOR".equalsIgnoreCase(lexer.getCurrentToken().getValue().toString())) {
                        lexer.nextToken();
                        Node n = (Node) stack.peek();

                        ForLoopNode forLoopNode = parseFor();

                        n.addChildNode(forLoopNode);
                        stack.push(forLoopNode);
                        continue;
                    }

                    if ("END".equalsIgnoreCase(lexer.getCurrentToken().getValue().toString())) {
                        lexer.nextToken();

                        if (!isTokenOfType(SmartScriptTokenType.CLOSED_TAG)) {
                            throw new SmartScriptParserException("Closing tag $} expected");
                        }

                        lexer.setState(SmartScriptLexerState.BASIC);
                        lexer.nextToken();
                        stack.pop();
                        continue;
                    }

                    continue;
                }

                throw new SmartScriptParserException("Unexpected token of type " + lexer.getCurrentToken().getType() +
                        " found: " + lexer.getCurrentToken().getValue());
            }
        } catch (Exception e) {
            throw new SmartScriptParserException(e.getMessage());
        }

        if (stack.size() != 1) throw new SmartScriptParserException("Uneven number of {$END$} tags.");

        return doc;
    }

    /**
     * Helper method used for parsing text outside of tags
     * @return parsed text node
     */
    private TextNode parseText() {
        if (!isTokenOfType(SmartScriptTokenType.TEXT)) {
            throw new SmartScriptParserException("Text was expected!");
        }
        return new TextNode(lexer.getCurrentToken().getValue().toString());
    }

    /**
     * Helper method used for parsing the echo tag
     * @return parsed echo tag
     */
    private EchoNode parseEcho() {
        ArrayIndexedCollection elements = new ArrayIndexedCollection();
        while(!isTokenOfType(SmartScriptTokenType.CLOSED_TAG)) {
            if (isTokenOfType(SmartScriptTokenType.VARIABLE)) {
                elements.add(new ElementVariable(lexer.getCurrentToken().getValue().toString()));
                lexer.nextToken();
                continue;
            }

            if (isTokenOfType(SmartScriptTokenType.STRING)) {
                elements.add(new ElementString(lexer.getCurrentToken().getValue().toString()));
                lexer.nextToken();
                continue;
            }

            if (isTokenOfType(SmartScriptTokenType.INTEGER_CONSTANT)) {
                elements.add(new ElementConstantInteger(Integer.parseInt(lexer.getCurrentToken().getValue().toString())));
                lexer.nextToken();
                continue;
            }

            if (isTokenOfType(SmartScriptTokenType.DOUBLE_CONSTANT)) {
                elements.add(new ElementConstantDouble(Double.parseDouble(lexer.getCurrentToken().getValue().toString())));
                lexer.nextToken();
                continue;
            }

            if (isTokenOfType(SmartScriptTokenType.FUNCTION)) {
                elements.add(new ElementFunction(lexer.getCurrentToken().getValue().toString()));
                lexer.nextToken();
                continue;
            }

            if (isTokenOfType(SmartScriptTokenType.OPERATOR)) {
                elements.add(new ElementOperator(lexer.getCurrentToken().getValue().toString()));
                lexer.nextToken();
                continue;
            }

            throw new SmartScriptParserException("Unexpected token of type: " + lexer.getCurrentToken().getType());
        }

        lexer.setState(SmartScriptLexerState.BASIC);
        lexer.nextToken();

        int length = elements.size();
        Element[] e = new Element[length];

        for (int i = 0; i < length; i++) {
            e[i] = (Element) elements.get(i);
        }

        return new EchoNode(e);
    }

    /**
     * Helper method used for parsing the FOR tag
     * @return parsed FOR tag
     */
    private ForLoopNode parseFor() {
        ElementVariable variable;
        Element startExpression = null;
        Element endExpression = null;
        Element stepExpression = null;

        //Setting variable
        if (!isTokenOfType(SmartScriptTokenType.VARIABLE)) {
            throw new SmartScriptParserException("Variable token expected.");
        }

        variable = new ElementVariable(lexer.getCurrentToken().getValue().toString());
        lexer.nextToken();

        //Setting startExpression
        if (isTokenOfType(SmartScriptTokenType.VARIABLE)) {
            startExpression = new ElementVariable(lexer.getCurrentToken().getValue().toString());
        }

        if (isTokenOfType(SmartScriptTokenType.STRING)) {
            String str = lexer.getCurrentToken().getValue().toString();
            str = str.substring(str.indexOf('"') + 1, str.lastIndexOf('"'));

            if (str.lastIndexOf('.') == -1) {
                startExpression = new ElementConstantInteger(Integer.parseInt(str));
            } else {
                startExpression = new ElementConstantDouble(Double.parseDouble(str));
            }
        }

        if (isTokenOfType(SmartScriptTokenType.INTEGER_CONSTANT)) {
            startExpression = new ElementConstantInteger(Integer.parseInt(lexer.getCurrentToken().getValue().toString()));
        }

        if (isTokenOfType(SmartScriptTokenType.DOUBLE_CONSTANT)) {
            startExpression = new ElementConstantDouble(Double.parseDouble(lexer.getCurrentToken().getValue().toString()));
        }

        if (startExpression == null) {
            throw new SmartScriptParserException("Expression token expected");
        }
        lexer.nextToken();

        //Setting endExpression
        if (isTokenOfType(SmartScriptTokenType.VARIABLE)) {
            endExpression = new ElementVariable(lexer.getCurrentToken().getValue().toString());
        }

        if (isTokenOfType(SmartScriptTokenType.STRING)) {
            String str = lexer.getCurrentToken().getValue().toString();
            str = str.substring(str.indexOf('"') + 1, str.lastIndexOf('"'));

            if (str.lastIndexOf('.') == -1) {
                endExpression = new ElementConstantInteger(Integer.parseInt(str));
            } else {
                endExpression = new ElementConstantDouble(Double.parseDouble(str));
            }
        }

        if (isTokenOfType(SmartScriptTokenType.INTEGER_CONSTANT)) {
            endExpression = new ElementConstantInteger(Integer.parseInt(lexer.getCurrentToken().getValue().toString()));
        }

        if (isTokenOfType(SmartScriptTokenType.DOUBLE_CONSTANT)) {
            endExpression = new ElementConstantDouble(Double.parseDouble(lexer.getCurrentToken().getValue().toString()));
        }

        if (endExpression == null) {
            throw new SmartScriptParserException("Expression token expected");
        }
        lexer.nextToken();

        //Setting stepExpression
        if (isTokenOfType(SmartScriptTokenType.VARIABLE)) {
            stepExpression = new ElementVariable(lexer.getCurrentToken().getValue().toString());
            lexer.nextToken();
        }

        if (isTokenOfType(SmartScriptTokenType.STRING)) {
            String str = lexer.getCurrentToken().getValue().toString();
            str = str.substring(str.indexOf('"') + 1, str.lastIndexOf('"'));

            if (str.lastIndexOf('.') == -1) {
                stepExpression = new ElementConstantInteger(Integer.parseInt(str));
            } else {
                stepExpression = new ElementConstantDouble(Double.parseDouble(str));
            }

            lexer.nextToken();
        }

        if (isTokenOfType(SmartScriptTokenType.INTEGER_CONSTANT)) {
            stepExpression = new ElementConstantInteger(Integer.parseInt(lexer.getCurrentToken().getValue().toString()));
            lexer.nextToken();
        }

        if (isTokenOfType(SmartScriptTokenType.DOUBLE_CONSTANT)) {
            stepExpression = new ElementConstantDouble(Double.parseDouble(lexer.getCurrentToken().getValue().toString()));
            lexer.nextToken();
        }

        if (!isTokenOfType(SmartScriptTokenType.CLOSED_TAG)) {
            throw new SmartScriptParserException("Closing tag $} expected");
        }

        lexer.setState(SmartScriptLexerState.BASIC);
        lexer.nextToken();
        return new ForLoopNode(variable, startExpression, endExpression, stepExpression);
    }


    /**
     * Helper method to test the current token type
     * @param type to be tested against
     * @return true if current token type is equal to type, false otherwise
     */
    private boolean isTokenOfType(SmartScriptTokenType type) {
        return lexer.getCurrentToken().getType() == type;
    }
}
