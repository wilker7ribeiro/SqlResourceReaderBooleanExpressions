package br.gov.bcb.sipoa.negocio.utils.booleanevaluator.expressions;

import br.gov.bcb.sipoa.negocio.utils.booleanevaluator.BooleanExpression;

import java.util.Map;

public class False implements BooleanExpression {
    public String toString() {
        return "false";
    }

    public boolean interpret(Map<String, Boolean> variables) {
        return false;
    }
}
