package br.gov.bcb.sipoa.negocio.utils.booleanevaluator.expressions;

import br.gov.bcb.sipoa.negocio.utils.booleanevaluator.BooleanExpression;

import java.util.Map;

public class True implements BooleanExpression {
    public String toString() {
        return "true";
    }

    public boolean interpret(Map<String, Boolean> variables) {
        return true;
    }
}
