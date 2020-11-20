package cn.edu.cqu.demo;


import cn.edu.cqu.ioc.ApplicationContext;
import cn.edu.cqu.ioc.annotation.Autowire;
import cn.edu.cqu.ioc.annotation.Controller;
import cn.edu.cqu.ioc.support.AnnotationApplicationContext;

@Controller
public class MyController {
    @Autowire("myservice")
    private MyService myService;

    public void test(){
        myService.say("hello world");
    }

    public static void main(String[] args) {
        ApplicationContext context = (ApplicationContext) new AnnotationApplicationContext("applicationContext.properties");
        MyController controller = context.getBean("com.mars.demo.MyController",MyController.class);
        controller.test();
    }
}
