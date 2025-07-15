package cn.lyuxc.projectb.controller;

import cn.lyuxc.projectb.entity.User;
import cn.lyuxc.projectb.entity.UserToken;
import cn.lyuxc.projectb.repository.UserRepository;
import cn.lyuxc.projectb.repository.UserTokenRepository;
import cn.lyuxc.projectb.utils.Md5Utils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepo;
    private final UserTokenRepository tokenRepo;

    public AuthController(UserRepository userRepo, UserTokenRepository userTokenRepo) {
        this.userRepo = userRepo;
        this.tokenRepo = userTokenRepo;
    }

    // 登录接口
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = Md5Utils.encrypt(body.get("password"));

        User user = userRepo.findByUsername(username);

        Map<String, Object> response = new HashMap<>();
        if (user == null || !user.getPassword().equals(password)) {
            response.put("code", 401);
            response.put("message", "用户名或密码错误");
            return response;
        }

        String token = Md5Utils.encrypt(UUID.randomUUID().toString());
        UserToken userToken = tokenRepo.findByUsername(username);
        if (userToken == null) {
            tokenRepo.save(new UserToken(username, token));
        } else {
            userToken.setToken(token);
            userToken.setUpdateTime(LocalDateTime.now());
            tokenRepo.save(userToken);
        }

        response.put("code", 200);
        response.put("message", "登录成功");
        response.put("token", token);
        return response;
    }

    // 注册接口
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        Map<String, Object> response = new HashMap<>();
        if (userRepo.findByUsername(username) != null) {
            response.put("code", 400);
            response.put("message", "用户已存在");
            return response;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        userRepo.save(user);

        response.put("code", 200);
        response.put("message", "注册成功");
        return response;
    }

    // 初始化数据库测试数据（仅开发用）
    @PostMapping("/init")
    public Map<String, Object> init() {
        Map<String, Object> result = new HashMap<>();

        if (userRepo.findAll().size() > 1) {
            result.put("code", 400);
            result.put("message", "数据库已初始化");
            return result;
        }

        userRepo.deleteAll();
        userRepo.saveAll(List.of(
                new User("admin", Md5Utils.encrypt("123456")),
                new User("administrator", Md5Utils.encrypt("123456")),
                new User("sysadmin", Md5Utils.encrypt("123456")),
                new User("test", Md5Utils.encrypt("test123456"))
        ));

        result.put("code", 200);
        result.put("message", "初始化成功");
        return result;
    }

    @PostMapping("/verify")
    public Map<String, Object> verifyToken(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String token = body.get("token");

        Map<String, Object> result = new HashMap<>();

        UserToken record = tokenRepo.findByUsername(username);
        if (record != null && record.getToken().equals(token)) {
            result.put("code", 200);
            result.put("message", "Token 有效");
            System.out.println("验证通过");
        } else {
            result.put("code", 401);
            result.put("message", "Token 无效或已过期");
            System.out.println("验证失败");
        }

        return result;
    }
}

