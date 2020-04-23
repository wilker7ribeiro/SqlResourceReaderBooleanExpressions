package br.gov.bcb.sipoa.negocio.utils.booleanevaluator;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class BooleanEvaluatorService {

    public BooleanExpression build(String expressionString) {
        return new BooleanEvaluator(expressionString).build();
    }

    public Boolean interpret(String expressionString) {
        return new BooleanEvaluator(expressionString).interpret();
    }

    public Boolean interpret(String expressionString, Map<String, Boolean> variables) {
        return new BooleanEvaluator(expressionString).interpret(variables);
    }
}
