package br.gov.bcb.sipoa.negocio.utils.booleanevaluator.expressions;

import br.gov.bcb.sipoa.negocio.utils.booleanevaluator.BooleanExpression;

import java.util.Map;

public class Variable implements BooleanExpression {
    private String variableName;
    public Variable(String variableName) {
        this.variableName = variableName;
    }

    public String toString() {
        return String.format("%s", variableName);
    }

    public boolean interpret(Map<String, Boolean> variables) {
        if(variables.containsKey(variableName)){
            return variables.get(variableName);
        }
        return false;
    }
}
