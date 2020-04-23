package br.gov.bcb.sipoa.negocio.utils;

import br.gov.bcb.sipoa.negocio.utils.booleanevaluator.BooleanEvaluator;
import br.gov.bcb.sipoa.negocio.utils.booleanevaluator.BooleanEvaluatorService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SqlResourceReader {


    @Autowired
    private BooleanEvaluatorService booleanEvaluator;

    private Pattern pattern;

    public SqlResourceReader() {
        pattern = Pattern.compile("\\$\\{([^=]+)=([^}$]+?)((\\$\\$)([^}$]+))?\\}");
    }

    @Cacheable(cacheNames = "sqlFile", key = "#resource.path")
    public String readSql(Resource resource) {
        try {
            return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }


    public String processConditional(String string, String key, Boolean shoudHave) {
        if (shoudHave) {
            return string.replaceAll("\\$\\{\\s*?" + key + "\\s*?\\=([^}]+?)((\\$\\$)([^}]+))?\\}", "$1");
        } else {
            return string.replaceAll("\\$\\{\\s*?" + key + "\\s*?\\=([^}]+?)((\\$\\$)([^}]+))?\\}", "$4");
        }
    }

    public String processAllConditional(String string, Map<String, Boolean> keysMap) {
        boolean found;
        do {
            Matcher m = pattern.matcher(string);
            StringBuffer sb = new StringBuffer(string.length());
            found = false;
            while (m.find()) {
                found = true;
                String key = m.group(1).trim();
                String trueValue = m.group(2);
                String falseValue = StringUtils.defaultIfBlank(m.group(5), "");
                if (booleanEvaluator.interpret(key, keysMap)) {
                    m.appendReplacement(sb, Matcher.quoteReplacement(trueValue));
                } else {
                    m.appendReplacement(sb, Matcher.quoteReplacement(falseValue));
                }
            }
            m.appendTail(sb);
            string = sb.toString();
        } while (found);
        return string;

    }

}
