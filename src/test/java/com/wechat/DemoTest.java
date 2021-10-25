package com.wechat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.framework.basepo.POBaseData;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.List;

public class DemoTest {
    static List<POBaseData> getData() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        TypeReference<List<POBaseData>> typeReference = new TypeReference<List<POBaseData>>() {
        };
        List<POBaseData> data = mapper.readValue(DemoTest.class.getResourceAsStream("/data/CeshirenPOCodeSearchPageData.yaml"), typeReference);

        return data;
    }

    @ParameterizedTest
    @MethodSource
    void getData(POBaseData data){
        data.getData().forEach(ele->{
            System.out.println(ele.get("get_first_title"));
        });
//        System.out.println(data.getName());
    }
}
