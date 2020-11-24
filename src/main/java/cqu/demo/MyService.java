package cqu.demo;


import cqu.ioc.annotation.Component;

@Component("myservice")
public class MyService {
    public void say(String hello_world) {
        System.out.println(hello_world);
    }
}
