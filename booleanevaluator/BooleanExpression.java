package br.gov.bcb.sipoa.negocio.utils.booleanevaluator;

import java.util.Map;

public interface BooleanExpression {
    boolean interpret(Map<String, Boolean> variables);
}
