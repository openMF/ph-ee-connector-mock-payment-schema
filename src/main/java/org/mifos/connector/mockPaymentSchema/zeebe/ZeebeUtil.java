package org.mifos.connector.mockPaymentSchema.zeebe;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ZeebeUtil {

    private static Logger logger = LoggerFactory.getLogger(ZeebeUtil.class);
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void zeebeVariablesToCamelProperties(Map<String, Object> variables, Exchange exchange, String... names) {
        exchange.setProperty("zeebeVariables", variables);

        for (String name : names) {
            Object value = variables.get(name);
            if (value == null) {
                logger.error("failed to find Zeebe variable name {}", name);
            } else {
                exchange.setProperty(name, value);
            }
        }
    }

    public static Map<String, Object> zeebeVariablesFrom(Exchange exchange) {
        return exchange.getProperty("zeebeVariables", Map.class);
    }

    public static <T> T zeebeVariable(Exchange exchange, String name, Class<T> clazz) throws Exception {
        Object content = zeebeVariablesFrom(exchange).get(name);
        if (content instanceof Map) {
            return objectMapper.readValue(objectMapper.writeValueAsString(content), clazz);
        }
        return (T) content;
    }
}
