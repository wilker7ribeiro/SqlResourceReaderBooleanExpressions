package br.gov.bcb.sipoa.negocio.utils.booleanevaluator;

import br.gov.bcb.sipoa.negocio.utils.SqlResourceReader;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class BooleanEvaluatorTest {

    private Exception ex;
    private Map<String, Boolean> variables;

    @Before
    public void setUp() {
        ex = null;
        variables = new HashMap<>();
        variables.put("verdadeiro", true);
        variables.put("falso", false);
    }

    @Test
    public void variables(){
        assertTrue(new BooleanEvaluator("verdadeiro").interpret(variables));
        assertFalse(new BooleanEvaluator("falso").interpret(variables));
        assertFalse(new BooleanEvaluator("inexistente").interpret(variables));
    }

    @Test
    public void and(){
        assertTrue(new BooleanEvaluator("true & true").interpret());
        assertFalse(new BooleanEvaluator("true & false").interpret());

        assertTrue(new BooleanEvaluator("verdadeiro & verdadeiro").interpret(variables));
        assertFalse(new BooleanEvaluator("verdadeiro & falso").interpret(variables));
    }


    @Test
    public void or(){
        assertTrue(new BooleanEvaluator("true | true").interpret());
        assertTrue(new BooleanEvaluator("true | false").interpret());
        assertFalse(new BooleanEvaluator("false | false").interpret());

        assertTrue(new BooleanEvaluator("verdadeiro | verdadeiro").interpret(variables));
        assertTrue(new BooleanEvaluator("verdadeiro | falso").interpret(variables));
        assertFalse(new BooleanEvaluator("falso | falso").interpret(variables));
    }

    @Test
    public void not(){
        assertTrue(new BooleanEvaluator("!false").interpret());
        assertFalse(new BooleanEvaluator("!true").interpret());

        assertTrue(new BooleanEvaluator("!falso").interpret(variables));
        assertFalse(new BooleanEvaluator("!verdadeiro").interpret(variables));
    }

    @Test(expected = RuntimeException.class)
    public void malformadoSimbulosJuntos(){
        new BooleanEvaluator("asda |&|& asd").build();
    }

    @Test(expected = RuntimeException.class)
    public void malformadoVariaveisSoltas(){
        new BooleanEvaluator("asda asd as").build();
    }
}