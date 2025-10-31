package Basharmal.Data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ReadData {
    public List<HashMap<String, Object>> getDataFromJson() throws IOException {
        String jsonData = FileUtils.readFileToString(new File(System.getProperty("user.dir") + "//src//test//java//Basharmal//Data//ReadData.java"), "UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        List<HashMap<String,Object>> data=objectMapper.readValue(jsonData, new TypeReference<List<HashMap<String, Object>>>() {
        });
        return data;
    }
}
