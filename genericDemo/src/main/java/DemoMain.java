import io.github.yedaxia.apidocs.Docs;
import io.github.yedaxia.apidocs.IResponseWrapper;
import io.github.yedaxia.apidocs.parser.ResponseNode;

import java.util.HashMap;
import java.util.Map;

public class DemoMain {

    public static void main(String[] args) {


        Docs.DocsConfig config = new Docs.DocsConfig();
        //config.setProjectPath("/Users/kevin/Documents/code/github/JApiDocs/genericDemo");
        config.setProjectPath("/Users/kevin/Documents/code/itacasa-gitlab/group-itacasa-cloud-service/cloud-service-mall/cloud-service-mall-order");


        config.setRapProjectId("3");
        config.setRapHost("http://localhost:8081");
        config.setRapAccount("kevin_huang");
        config.setRapPassword("123456");
        config.setRapLoginCookie("Idea-d861c587=0409e26b-f465-4f66-b5e1-d0d6fc38a1e6; UM_distinctid=167f946a52d177-0cedae56eb4229-10326653-384000-167f946a52e596; JSESSIONID=CC0B6CD14D3EFB6AC81BC37729EC8289; CNZZDATA5879641=cnzz_eid%3D101674269-1546070824-http%253A%252F%252Flocalhost%253A8080%252F%26ntime%3D1546076237");

        Docs.setResponseWrapper(new IResponseWrapper() {
            @Override
            public Map<String,Object> wrapResponse(ResponseNode responseNode) {
                Map<String,Object> resultMap = new HashMap<>();
                resultMap.put("code", 0);
                resultMap.put("errorMessage","string //错误信息");
                resultMap.put("content", responseNode);
                return resultMap;
            }
        });

        Docs.buildHtmlDocs(config);
    }
}
