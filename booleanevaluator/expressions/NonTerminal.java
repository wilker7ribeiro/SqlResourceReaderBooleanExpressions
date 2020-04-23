package br.gov.bcb.sipoa.negocio.utils.booleanevaluator.expressions;

import br.gov.bcb.sipoa.negocio.utils.booleanevaluator.BooleanExpression;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class NonTerminal implements BooleanExpression {
    protected BooleanExpression left;
    protected BooleanExpression right;
}
