package ca.jrvs.apps.trading.Util;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

public class JasonUtil {

    public static String toJson(Object object /*,boolean prettyJson,boolean includeNullValues*/) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        // if (prettyJson)
        // {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        //  }

        // if (includeNullValues)
        // {
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        // }
        return mapper.writeValueAsString(object);


    }


    public static <T> T toObjectFromJson(String json, Class clazz) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return (T) mapper.readValue(json, clazz);


    }


}  // end of JsonParser Class






