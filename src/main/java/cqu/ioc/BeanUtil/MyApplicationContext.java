package cqu.ioc.BeanUtil;

import cqu.ioc.ApplicationContext;
import cqu.ioc.Register;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * IOC容器，bean的注册器
 */
public class MyApplicationContext implements ApplicationContext, Register {

    private Map<String,Object> instanceMapping = new ConcurrentHashMap<String, Object>();

    private List<Bean> beans = new ArrayList<Bean>();
    private Properties config = new Properties();

    public MyApplicationContext(String location){
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
        Injector injector = new Injector();
        injector.inject(instanceMapping);
    }


    private void createBean() {
        Creator creater = new Creator(this);
        creater.create(beans);
    }

    private void register() {
        Parser parser = new Parser(this);
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


    public void registBeanDefinition(List<Bean> bds) {
        beans.addAll(bds);
    }

    public void registInstanceMapping(String id, Object instance) {
        instanceMapping.put(id,instance);
    }

}