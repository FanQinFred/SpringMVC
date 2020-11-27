package cqu.ioc;

import java.util.Map;

//获取实例的接口
public interface ApplicationContext {
    Object getBean(String id);
    <T>T getBean(String id, Class<T> clazz);
    Map<String,Object> getBeans();
}
