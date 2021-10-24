package com.framework.basepo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.List;

public class Derrick {
    static List<POBaseData> hello() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        TypeReference<List<POBaseData>> typeReference = new TypeReference<List<POBaseData>>() {
        };
        List<POBaseData> data = mapper.readValue(POBaseData.class.getResourceAsStream("src/test/resources/data/hello.yml"), typeReference);
        return  data;
    }

    @ParameterizedTest
    @MethodSource("hello")
    public void first_title(POBaseData poBaseData) {
        System.out.println(poBaseData.getName());
        System.out.println(poBaseData.getAge());
    }
}
