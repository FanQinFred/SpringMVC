package cqu.demo;

import cqu.ioc.annotation.Autowire;
import cqu.ioc.annotation.Controller;
import cqu.ioc.annotation.RequestMapping;
import cqu.ioc.annotation.RequestParam;
import cqu.mvc.model.UserDao;
import cqu.mvc.model.UserEntity;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/user")
public class LoginController {


    @RequestMapping(value = "/login",method = "GET")
    public String loginGET(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        return "login";
    }

    @RequestMapping(value = "/login",method = "POST")
    public String loginPOST(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String username = req.getParameter("username");
        String pwd = req.getParameter("password");

        if(UserDao.getUserByName(username).getPwd().equals(pwd)){
            System.out.println("suc");
            Cookie cookie = new Cookie("uploader",username);
            cookie.setDomain("localhost");
            cookie.setPath("/");
            cookie.setMaxAge(3600);
            res.addCookie(cookie);
            return "redirect:http://localhost:8080/disk/show";
        }else{
            return "login";
        }

    }
}