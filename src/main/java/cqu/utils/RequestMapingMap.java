package cqu.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 存储方法的访问路径
 *
 * @author itguang
 * @create 2018-04-05 22:19
 **/
public class RequestMapingMap {

    /**
     * @Field: requesetMap
     *          用于存储方法的访问路径
     */
    public static Map<RequestMappingTypeSet, Class<?>> requesetMap = new HashMap<>();

    public static void setRequesetMap(Map<RequestMappingTypeSet, Class<?>> requesetMap) {
        RequestMapingMap.requesetMap = requesetMap;
    }

    public static Class<?> getClassName(RequestMappingTypeSet requestMappingTypeSet) {
        System.out.println(requestMappingTypeSet.value+requestMappingTypeSet.method.toString());
        if(requesetMap.containsKey(requestMappingTypeSet))
            System.out.println("11111111");
        return requesetMap.get(requestMappingTypeSet);
    }

    public static void put(RequestMappingTypeSet type, Class<?> className) {
        requesetMap.put(type, className);
    }

    public static Map<RequestMappingTypeSet, Class<?>> getRequesetMap() {
        return requesetMap;
    }
}
