package cqu.ioc.support;

import cqu.ioc.ApplicationContext;
import cqu.ioc.BeanRegister;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class AnnotationApplicationContext implements ApplicationContext, BeanRegister {

    private Map<String,Object> instanceMapping = new ConcurrentHashMap<String, Object>();

    private List<BeanDefinition> beanDefinitions = new ArrayList<BeanDefinition>();
    private Properties config = new Properties();

    public AnnotationApplicationContext(String location){
        try{

            String classpath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("/")).getPath();
            String end = "WEB-INF/classes/";
            BufferedReader bufferedReader = new BufferedReader(new FileReader(classpath.replace(end, location)));

            config.load(bufferedReader);

            register();

            createBean();

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

    public Object getBean(String id) {
        return instanceMapping.get(id);
    }

    public <T> T getBean(String id, Class<T> clazz) {
        return (T)instanceMapping.get(id);
    }

    public Map<String, Object> getBeans() {
        return instanceMapping;
    }


    public void registBeanDefinition(List<BeanDefinition> bds) {
        beanDefinitions.addAll(bds);
    }

    public void registInstanceMapping(String id, Object instance) {
        instanceMapping.put(id,instance);
    }

}