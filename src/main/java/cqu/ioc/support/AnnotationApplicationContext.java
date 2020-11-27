package cqu.ioc.support;

import cqu.ioc.ApplicationContext;
import cqu.ioc.BeanRegister;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class AnnotationApplicationContext implements ApplicationContext, BeanRegister {

    //储存create创建的实例
    private Map<String,Object> instanceMapping = new ConcurrentHashMap<String, Object>();

    //保存所有bean的信息,主要包含bean的类型  id等信息
    private List<BeanDefinition> beanDefinitions = new ArrayList<BeanDefinition>();
    //配置文件的config,这里为了简单我们使用properties文件
    private Properties config = new Properties();

    public AnnotationApplicationContext(String location){
        try{
            //1、定位
            String classpath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("/")).getPath();
            String end = "WEB-INF/classes/";
            BufferedReader bufferedReader = new BufferedReader(new FileReader(classpath.replace(end, location)));
            //2、载入配置文件
            config.load(bufferedReader);

            //3、注册 将注册信息放入this.beanDefinitions，包括Controller和AutoWire注解
            register();

            //4、实例化 将实例放入this.instanceMapping
            createBean();

            //5、注入 根据AutoWire注释获取需要注入的类，根据instanceMapping的kv注入
            populate();

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    private void populate() {
        Populator populator = new Populator();
        populator.populate(instanceMapping);
    }


    private void createBean() {
        BeanCreater creater = new BeanCreater(this);
        creater.create(beanDefinitions);
    }

    private void register() {
        BeanDefinitionParser parser = new BeanDefinitionParser(this);
        parser.parse(config);
    }

    public Properties getConfig() {
        return this.config;
    }
//    ApplicationContext
    public Object getBean(String id) {
        return instanceMapping.get(id);
    }

    public <T> T getBean(String id, Class<T> clazz) {
        return (T)instanceMapping.get(id);
    }

    public Map<String, Object> getBeans() {
        return instanceMapping;
    }

//      BeanRegister
    public void registBeanDefinition(List<BeanDefinition> bds) {
        beanDefinitions.addAll(bds);
    }

    public void registInstanceMapping(String id, Object instance) {
        instanceMapping.put(id,instance);
    }

}