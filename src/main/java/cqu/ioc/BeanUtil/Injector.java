package cqu.ioc.BeanUtil;


import cqu.ioc.annotation.Autowire;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * 为被标记属性自动注入
 */
public class Injector {

    public Injector(){
    }

    public void inject(Map<String,Object> instanceMapping){
        if(instanceMapping.isEmpty())return;

        for (Map.Entry<String,Object> entry:instanceMapping.entrySet()){
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            for (Field field:fields){
                if(!field.isAnnotationPresent(Autowire.class))continue;
                Autowire autowire = field.getAnnotation(Autowire.class);
                String id = autowire.value();
                if("".equals(id))id = field.getType().getName();
                field.setAccessible(true);
                try {
                    field.set(entry.getValue(),instanceMapping.get(id));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}