package br.gov.bcb.sipoa.negocio.utils.booleanevaluator.expressions;

import br.gov.bcb.sipoa.negocio.utils.booleanevaluator.BooleanExpression;

import java.util.Map;

public class Not extends NonTerminal {
    public void setChild(BooleanExpression child) {
        setLeft(child);
    }

    public void setRight(BooleanExpression right) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean interpret(Map<String, Boolean> vars) {
        return !left.interpret(vars);
    }

    public String toString() {
        return String.format("!%s", left);
    }
}
