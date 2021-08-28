package com.jcoder.request.execute.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class JsonConvertTest {

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);

        String jsonStr = "[\n" +
                "    {\n" +
                "\t  \"firstname\":\"Anna\",\n" +
                "\t  \"lastname\":\"Smith\",\n" +
                "\t  \"exams\":[\n" +
                "\t    {\"subject\":\"英语\" , \"score\":\"80\"},\n" +
                "\t\t{\"subject\":\"语文\" , \"score\":\"90\"}\n" +
                "\t  ]\n" +
                "\t},\n" +
                "\t{\n" +
                "\t  \"firstname\":\"James\",\n" +
                "\t  \"lastname\":\"Gery\",\n" +
                "\t  \"exams\":[\n" +
                "\t    {\"subject\":\"数学\" , \"score\":\"95\"},\n" +
                "\t\t{\"subject\":\"语文\" , \"score\":\"90\"}\n" +
                "\t  ]\n" +
                "\t}\n" +
                "]";
        List jsonMap = objectMapper.readValue(jsonStr, List.class);

        System.out.println("---------------end---------------");

    }
}
