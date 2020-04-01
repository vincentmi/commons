package com.vnzmi.commons;

import lombok.SneakyThrows;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.util.*;

public class Utils {

    @SneakyThrows
    public static <K,V> HashMap<K,V> collectToMap(List<V> list , String fieldName)  {
        HashMap<K,V> result = new HashMap<>();
        V item;
        K keyFiled;
        Class rawType = null;
        for(int i = 0;i<list.size();i++)
        {
            item = list.get(i);
            rawType = getRawType(item.getClass());
            BeanInfo beanInfo = Introspector.getBeanInfo(rawType);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for(int j = 0;j<propertyDescriptors.length;j++)
            {
                if(propertyDescriptors[j].getName().equalsIgnoreCase(fieldName))
                {
                    keyFiled = (K)propertyDescriptors[j].getReadMethod().invoke(item);
                    result.put(keyFiled,item);
                    break;
                }
            }
        }
        return result;
    }

    @SneakyThrows
    public static Object mapToObject(Map<String,Object> map , Class<?> beanClass) {
        if (map == null)
            return null;

        BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
        Object obj = beanClass.newInstance();
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor descriptor : propertyDescriptors) {
            String propertyName = descriptor.getName();
            if (propertyName.compareToIgnoreCase("class") == 0) {
                continue;
            }
            if (map.containsKey(propertyName)) {
                Object value = map.get(propertyName);
                descriptor.getWriteMethod().invoke(obj, value);
            }
        }
        return obj;
    }



    @SneakyThrows
    public static Map<String,Object> objectToMap(Object srcObject) {
        Map<String,Object> map = new HashMap<>() ;
        if (srcObject == null) {
            return map;
        }
        Class objectRawClass = getRawType(srcObject.getClass());
        BeanInfo beanInfo = Introspector.getBeanInfo(objectRawClass);
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor descriptor : propertyDescriptors) {
            String propertyName = descriptor.getName();
            if (propertyName.compareToIgnoreCase("class") == 0) {
                continue;
            }
            Object value  = descriptor.getReadMethod().invoke(srcObject);
            map.put(propertyName,value);
        }
        return map;
    }

    public static Class<?> getRawType(Type type) {
        if (type instanceof Class) {
            return (Class)type;
        } else if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType)type;
            Type rawType = parameterizedType.getRawType();
            if (!(rawType instanceof Class)) {
                throw new IllegalArgumentException();
            } else {
                return (Class)rawType;
            }
        } else if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType)type).getGenericComponentType();
            return Array.newInstance(getRawType(componentType), 0).getClass();
        } else if (type instanceof TypeVariable) {
            return Object.class;
        } else if (type instanceof WildcardType) {
            return getRawType(((WildcardType)type).getUpperBounds()[0]);
        } else {
            String className = type == null ? "null" : type.getClass().getName();
            throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + className);
        }
    }

}
