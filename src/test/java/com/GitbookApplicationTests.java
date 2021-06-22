package com;

import com.entity.gitee.GetRequest;
import com.entity.gitee.GetResponse;
import com.service.impl.GiteeApiImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GitbookApplicationTests {

    @Autowired
    private GiteeApiImpl giteeApi;

    @Test
    public void contextLoads() {
        GetRequest getRequest = new GetRequest();
        getRequest.setAccess_token("8a5b58cd187031a2d5816ba3e82d7e92");
        getRequest.setPath("mul_thread.gif");
        GetResponse getResponse = giteeApi.get(getRequest);
        System.out.println(getResponse.toString());

    }

}
