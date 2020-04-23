package br.gov.bcb.sipoa.negocio.utils;

import br.gov.bcb.sipoa.negocio.utils.booleanevaluator.BooleanEvaluatorService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ClassPathResource;

import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

public class SqlResourceReaderTest {

    @InjectMocks
    private SqlResourceReader sqlResourceReader;

    @Mock
    private BooleanEvaluatorService booleanEvaluatorService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        given(booleanEvaluatorService.build(anyString())).willCallRealMethod();
        given(booleanEvaluatorService.interpret(anyString())).willCallRealMethod();
        given(booleanEvaluatorService.interpret(anyString(), anyMap())).willCallRealMethod();
    }

    @Test
    public void readSql() {
        sqlResourceReader.readSql(new ClassPathResource("/queries/ultima-alteracao-cronograma-anual/registro-atual-alteracao-cca.sql"));
    }

    @Test(expected = UncheckedIOException.class)
    public void readSqlFileNotFound() {
        sqlResourceReader.readSql(new ClassPathResource("sadasdasd"));
    }

    @Test
    public void processConditionalWithTrue() {
        assertEquals("teste 1", sqlResourceReader.processConditional("teste ${boolean=1}", "boolean", true));
    }

    @Test
    public void processConditionalWithFalse() {
        assertEquals("teste ", sqlResourceReader.processConditional("teste ${boolean=1}", "boolean", false));
    }

    @Test
    public void processConditionalWithFalseElse() {
        assertEquals("teste 2", sqlResourceReader.processConditional("teste ${boolean=1$$2}", "boolean", false));
    }

    @Test
    public void processAllConditionalWithTrue() {
        Map<String, Boolean> keyMap = new HashMap<>();
        keyMap.put("booleanA", true);
        keyMap.put("booleanB", true);
        assertEquals("teste 1", sqlResourceReader.processAllConditional("teste ${booleanA&booleanB=1}", keyMap));
    }

    @Test
    public void processAllConditionalWithFalse() {

        Map<String, Boolean> keyMap = new HashMap<>();
        keyMap.put("booleanA", true);
        keyMap.put("booleanB", false);
        assertEquals("teste ", sqlResourceReader.processAllConditional("teste ${booleanA&booleanB=1}", keyMap));
    }

    @Test
    public void processAllConditionalWithFalseElse() {

        Map<String, Boolean> keyMap = new HashMap<>();
        keyMap.put("booleanA", true);
        keyMap.put("booleanB", false);
        assertEquals("teste 2", sqlResourceReader.processAllConditional("teste ${booleanA&booleanB=1$$2}", keyMap));
    }


}