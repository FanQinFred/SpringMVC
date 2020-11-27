package cqu.utils;

import java.util.HashMap;
import java.util.Map;


public class RequestMapingMap {


    public static Map<RequestMappingTypeSet, Class<?>> requesetMap = new HashMap<>();

    public static void setRequesetMap(Map<RequestMappingTypeSet, Class<?>> requesetMap) {
        RequestMapingMap.requesetMap = requesetMap;
    }

    public static Class<?> getClassName(RequestMappingTypeSet requestMappingTypeSet) {
        System.out.println(requestMappingTypeSet.value+requestMappingTypeSet.method.toString());
        return requesetMap.get(requestMappingTypeSet);
    }

    public static void put(RequestMappingTypeSet type, Class<?> className) {
        requesetMap.put(type, className);
    }

    public static Map<RequestMappingTypeSet, Class<?>> getRequesetMap() {
        return requesetMap;
    }
}
