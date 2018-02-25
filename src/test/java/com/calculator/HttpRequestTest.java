package com.calculator;

/**
 * Created by ian on 2/9/18.
 */

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    @Autowired
    Environment environment;

//    String port = environment.getProperty("local.server.port");
    private int port = 8080;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void addShoulddReturnInteger() throws Exception{
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "calculator/add?val=5", Integer.class)).isEqualTo(5);
    }

    @Test
    public void addShoulddReturnTotal() throws Exception{
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "calculator/total", Integer.class)).isNotEqualTo(0);
    }
}