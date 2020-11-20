package cn.edu.cqu.demo;

import cn.edu.cqu.ioc.annotation.Autowire;
import cn.edu.cqu.ioc.annotation.Controller;
import cn.edu.cqu.ioc.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/l")
public class LoginController {
    @Autowire("myservice")
    private MyService myService;

    @RequestMapping("/login")
    public void test(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.getWriter().print("This a login page");
    }
}