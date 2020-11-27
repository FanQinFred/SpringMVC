package cqu.ioc;

import java.util.Map;

public interface ApplicationContext {
    Object getBean(String id);
    <T>T getBean(String id, Class<T> clazz);
    Map<String,Object> getBeans();
}
