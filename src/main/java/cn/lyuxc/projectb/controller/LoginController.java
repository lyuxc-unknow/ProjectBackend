package cn.lyuxc.projectb.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
public class LoginController {
    @PostMapping("/api/login")
    public Map<String, Object> login(@RequestBody Map<String, String> payload) {
        String username = payload.get("username");
        String password = payload.get("password");

        Map<String, Object> result = new HashMap<>();
        if ("admin".equals(username) && "123456".equals(password)) {
            result.put("code", 200);
            result.put("message", "登录成功");
            result.put("token", UUID.randomUUID().toString()); // 模拟 token
        } else {
            result.put("code", 401);
            result.put("message", "用户名或密码错误");
        }
        log.info(username);
        log.info(password);
        return result;
    }

}
