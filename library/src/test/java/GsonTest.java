import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class GsonTest {

    @Test
    public void test(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        Gson gson = gsonBuilder.create();

        User user = new User();
        user.setNameInfo("coder");
        user.setAgeInfo("28");

        String json = gson.toJson(user);

        Map<String,Object> map = new HashMap<>();
        map.put("dataA",user);
        String json2 = gson.toJson(map);

        Object o = gson.fromJson(json2, Object.class);
        String json3 = gson.toJson(o);

        //Assert.assertEquals("{\"age_info\":\"28\",\"name_info\":\"coder\"}", json);

        /*ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(com.fasterxml.jackson.databind.PropertyNamingStrategy.SNAKE_CASE);
        String json4 = mapper.writeValueAsString(o);*/


        SerializeConfig config = new SerializeConfig();
        config.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
        String json5 = JSON.toJSONString(map, config);

        String json4 = snakeCase(json2,"_");
        System.out.println(json4);

    }

    class User {

        private String nameInfo;

        private String ageInfo;

        public String getNameInfo() {
            return nameInfo;
        }

        public void setNameInfo(String nameInfo) {
            this.nameInfo = nameInfo;
        }

        public String getAgeInfo() {
            return ageInfo;
        }

        public void setAgeInfo(String ageInfo) {
            this.ageInfo = ageInfo;
        }
    }

    @Test
    public void snakeCaseTest(){
        String key = snakeCase("aBcD","_");
        System.out.println(key);
    }



    static String snakeCase(String name, String separator) {
        StringBuilder translation = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            char character = name.charAt(i);
            if (Character.isUpperCase(character) && translation.length() != 0) {
                translation.append(separator);
            }

            translation.append(Character.toLowerCase(character));
        }
        return translation.toString();
    }
}

