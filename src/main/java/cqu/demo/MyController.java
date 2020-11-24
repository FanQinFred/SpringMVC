package cqu.demo;


import cqu.ioc.ApplicationContext;
import cqu.ioc.annotation.Autowire;
import cqu.ioc.annotation.Controller;
import cqu.ioc.support.AnnotationApplicationContext;


@Controller
public class MyController {
    @Autowire("myservice")
    private MyService myService;

    public void test(){
        myService.say("hello world");
    }

    public static void main(String[] args) {
        ApplicationContext context = (ApplicationContext) new AnnotationApplicationContext("WEB-INF/applicationContext.properties");
        MyController controller = context.getBean("cqu.demo.MyController",MyController.class);
        controller.test();
    }
}
