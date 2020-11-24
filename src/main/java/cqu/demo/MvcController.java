package cqu.demo;


import cqu.ioc.annotation.Autowire;
import cqu.ioc.annotation.Controller;
import cqu.ioc.annotation.RequestMapping;
import cqu.ioc.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/web")
public class MvcController {

    @RequestMapping(value="/test",method="")
    public String test(HttpServletRequest req, HttpServletResponse res,@RequestParam("word") String word){
        return "test";
    }

    @RequestMapping("/view")
    public String view(HttpServletRequest req, HttpServletResponse res,@RequestParam("word") String word) throws ServletException, IOException {
        req.setAttribute("view2","999");
        return "view";
    }

    @RequestMapping("/index")
    public String index(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        return "index";
    }
}
