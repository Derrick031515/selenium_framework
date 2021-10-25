package com.framework.basepo;

import lombok.Data;
import java.util.LinkedHashMap;
import java.util.List;

@Data
public class POBaseData {
    List<LinkedHashMap<String,Object>> data;
}
