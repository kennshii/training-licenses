package endava.internship.traininglicensessharing.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseController {
    //create the json that the application will return
    public static <V> String createExpectedBody(V v) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(v);
    }

    //create the needed request
    public static <V> String createRequest(V v) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(v);
    }
}