package br.gov.bcb.sipoa.negocio.utils.booleanevaluator.expressions;

import java.util.Map;

public class And extends NonTerminal {
    @Override
    public boolean interpret(Map<String, Boolean> vars) {
        return left.interpret(vars) && right.interpret(vars);
    }

    public String toString() {
        return String.format("(%s & %s)", left, right);
    }
}
