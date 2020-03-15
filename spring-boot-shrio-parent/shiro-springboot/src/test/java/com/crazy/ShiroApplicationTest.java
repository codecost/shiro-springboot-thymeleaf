package com.crazy;

import com.crazy.service.UserService;
import com.crazy.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ShiroApplicationTest {
    @Autowired
    UserServiceImpl userService;
    @Test
    public void Test1(){
        System.out.println(userService.queryUserByName("王五"));
    }
}
