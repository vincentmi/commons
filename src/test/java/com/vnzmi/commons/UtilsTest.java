package com.vnzmi.commons;

import junit.framework.TestCase;
import lombok.Data;
import org.apache.commons.collections.map.HashedMap;
import org.junit.jupiter.api.Test;
import org.junit.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@Data
class Bean {
    private  String name;
    private  int age;
    public Bean(){}
    public Bean(String name,int age )
    {
        this.name = name ;
        this.age = age;
    }
}

class UtilsTest extends TestCase {

    @Test
    void mapToObject() {
        Map<String,Object> map = new HashMap<String,Object>(){{
            put("name","vincent");
            put("age",25);
            put("gender","female");
        }};

        Map<String,Object> map2 = new HashMap<String,Object>(){{
            put("name","vincent");
            put("age",25);
        }};

        Bean bean = new Bean("vincent",25);
        assertEquals(bean,Utils.mapToObject(map,Bean.class));
        assertNotEquals(map,Utils.objectToMap(bean));
        assertEquals(map2,Utils.objectToMap(bean));

    }

    @Test
    void testCollectToMap()
    {
        List<Bean> beanList = new ArrayList<Bean>();
        Bean a = new Bean("a",1);
        Bean b = new Bean("b",2);
        Bean c = new Bean("c",3);

        beanList.add(a);
        beanList.add(b);
        beanList.add(c);

        Map<String,Bean> result = Utils.collectToMap(beanList,"name");

        assertEquals(a , result.get("a"));
        assertEquals(b , result.get("b"));
        assertEquals(c , result.get("c"));

    }


}