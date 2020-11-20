package cn.edu.cqu.demo;


import cn.edu.cqu.ioc.annotation.Autowire;
import cn.edu.cqu.ioc.annotation.Controller;
import cn.edu.cqu.ioc.annotation.RequestMapping;
import cn.edu.cqu.ioc.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/web")
public class MvcController {
    @Autowire("myservice")
    private MyService myService;

    @RequestMapping("/test")
    public void test(HttpServletRequest req, HttpServletResponse res,@RequestParam("word") String word){
        myService.say(word);
        try {
            res.getWriter().print(word);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @RequestMapping("/view")
    public String view(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        return "view";
    }

}
