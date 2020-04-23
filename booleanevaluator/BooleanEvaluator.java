package br.gov.bcb.sipoa.negocio.utils.booleanevaluator;

import br.gov.bcb.sipoa.negocio.utils.booleanevaluator.expressions.*;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class BooleanEvaluator {

    private BooleanExpression root;

    private static final String TRUE_LITERAL = "true";
    private static final String FALSE_LITERAL = "false";

    int currentToken;
    private String expression;
    private StreamTokenizer tokenizer;

    public BooleanEvaluator(String expression) {
        this.expression = expression;
        tokenizer = new StreamTokenizer(new StringReader(expression));
        tokenizer.resetSyntax();
        tokenizer.wordChars('a', 'z');
        tokenizer.wordChars('A', 'Z');
        tokenizer.whitespaceChars('\u0000', ' ');
        tokenizer.whitespaceChars('\n', '\t');
        tokenizer.ordinaryChar('(');
        tokenizer.ordinaryChar(')');
        tokenizer.ordinaryChar('&');
        tokenizer.ordinaryChar('|');
        tokenizer.ordinaryChar('!');
    }
    public BooleanExpression build() {
        validar(expression);
        criarExpressao();
        return root;
    }

    public boolean interpret(Map<String, Boolean> variables){
        return build().interpret(variables);
    }
    public boolean interpret(){
        return build().interpret(new HashMap<>());
    }


    private void criarExpressao() {
        term();
        while (currentToken == '|') {
            Or or = new Or();
            or.setLeft(root);
            term();
            or.setRight(root);
            root = or;
        }
    }

    private void term() {
        factor();
        while (currentToken == '&') {
            And and = new And();
            and.setLeft(root);
            factor();
            and.setRight(root);
            root = and;
        }
    }

    private void factor() {
        try {
            currentToken = tokenizer.nextToken();
            if (TRUE_LITERAL.equalsIgnoreCase(tokenizer.sval)) {
                root = new True();
                currentToken = tokenizer.nextToken();
            } else if (FALSE_LITERAL.equalsIgnoreCase(tokenizer.sval)) {
                root = new False();
                currentToken = tokenizer.nextToken();
            } else if (currentToken == StreamTokenizer.TT_WORD) {
                root = new Variable(tokenizer.sval);
                currentToken = tokenizer.nextToken();
            } else if (currentToken == '!') {
                Not not = new Not();
                factor();
                not.setChild(root);
                root = not;
            } else if (currentToken == '(') {
                criarExpressao();
                currentToken = tokenizer.nextToken(); // we don't care about ')'
            } else {
                throwMalformedExpression();
            }
        } catch (IOException ignored) {
        }
    }

    private void validar(String expression) {
        if(possuiVariavesSoltas(expression)){
            throwMalformedExpression();
        }
    }

    private boolean possuiVariavesSoltas(String expression) {
        return expression.matches(".*[a-zA-Z]+\\s+[a-zA-Z]+.*");
    }

    private void throwMalformedExpression() {
        throw new RuntimeException("Expression Malformed");
    }
}



