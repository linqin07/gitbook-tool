package com.dao;

import com.GitbookApplication;
import com.controller.TestController;
import com.entity.Info;
import com.service.CoreService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Description:
 * InjectMocks字段是无法注入其他InjectMocks字段的,如下的testController是无法注入coreService的。可以手动注入
 * ReflectionTestUtils.setFiReld(testController, "coreService", coreService);
 * author: 林钦
 * date: 2019/06/12
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = GitbookApplication.class)
public class InfoMapperTest {
    @Mock
    // 创建一个Mock
    private InfoMapper infoMapper;

    @InjectMocks
    // 创建一个实例，其余用@Mock（或@Spy）注解创建的mock将被注入到用该实例中。
    private CoreService coreService;

    @InjectMocks
    private TestController testController;

    @Test
    public void test() {
        // 打桩,与参数无关。
        Info info = new Info();
        info.setPicUrl("picUrl")
            .setPicLocalPath("picLocalPath")
            .setPicName("picName")
            .setId(0L);
        Mockito.when(infoMapper.selectByPicUrl("pic")).thenReturn(info);
        Info info1 = infoMapper.selectByPicUrl("pic1ddd");
        System.out.println(info.toString());

    }

    @Test
    public void test1() throws UnsupportedEncodingException {
        System.out.println(URLEncoder.encode("ivory*#06#@_2019", "UTF-8"));
    }

}