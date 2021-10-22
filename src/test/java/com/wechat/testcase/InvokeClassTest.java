package com.wechat.testcase;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 参数类型为基本类型、包装类型、String类型
 * 重载方法暂时没有考虑
 * 更复杂类型参数以后会补充进来
 * Created by zkn on 2016/5/9.
 */
public class InvokeClassTest {

    public static void main(String[] args){
        String param = "The first demo";
        ArrayList<LinkedHashMap<String, Object>> linkedHashMaps = new ArrayList<>();
        LinkedHashMap<String, Object> stringObjectLinkedHashMap = new LinkedHashMap<>();
        stringObjectLinkedHashMap.put("a","b");
        stringObjectLinkedHashMap.put("b","b");
        stringObjectLinkedHashMap.put("c","b");
        stringObjectLinkedHashMap.put("d","b");
        LinkedHashMap<String, Object> stringObjectLinkedHashMap2 = new LinkedHashMap<>();
        stringObjectLinkedHashMap2.put("1","b");
        stringObjectLinkedHashMap2.put("2","b");
        stringObjectLinkedHashMap2.put("3","b");
        stringObjectLinkedHashMap2.put("4","b");
        linkedHashMaps.add(stringObjectLinkedHashMap);
        linkedHashMaps.add(stringObjectLinkedHashMap2);
        //全部当做String值来传递
        executeReflectMethod("com.wechat.testcase.InvokeMethodTest","test",linkedHashMaps);
    }

    private static void executeReflectMethod(String className,String methodName,ArrayList<LinkedHashMap<String, Object>> linkedHashMaps) {
        try {
            Object clazz = Class.forName(className).newInstance();
            Method[] obj = clazz.getClass().getMethods();
            for(Method method : obj){
                if(methodName!=null && methodName.equals(method.getName())){
                    //如果是private修饰符的，则把可访问性设置为true
                    if(!method.isAccessible()){
                        method.setAccessible(true);
                    }
                    //得到方法中的所有参数信息
                    Class<?>[] parameterClazz = method.getParameterTypes();
                    List<Object> listValue = new ArrayList<Object>();
                    //循环参数类型
                    for(int i=0; i<parameterClazz.length; i++){
                        fillList(listValue, parameterClazz[i],linkedHashMaps);
                    }
                    Object invoke = method.invoke(clazz, listValue.toArray());
                    System.out.println("Object invoke="+invoke);
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static void fillList(List<Object> list, Class<?> parameter,Object value) {
        System.out.println(parameter.getTypeName());
        if("java.lang.String".equals(parameter.getTypeName())){
            list.add(value);
        }else if("java.lang.Character".equals(parameter.getTypeName())){
            char[] ch = ((String)value).toCharArray();
            list.add(ch[0]);
        }else if("char".equals(parameter.getTypeName())){
            char[] ch = ((String)value).toCharArray();
            list.add(ch[0]);
        }else if("java.lang.Double".equals(parameter.getTypeName())){
            list.add(Double.parseDouble((String) value));
        }else if("double".equals(parameter.getTypeName())){
            list.add(Double.parseDouble((String) value));
        }else if("java.lang.Integer".equals(parameter.getTypeName())){
            list.add(Integer.parseInt((String) value));
        }else if("int".equals(parameter.getTypeName())){
            list.add(Integer.parseInt((String) value));
        }else if("java.lang.Long".equals(parameter.getTypeName())){
            list.add(Long.parseLong((String) value));
        }else if("long".equals(parameter.getTypeName())){
            list.add(Long.parseLong((String) value));
        }else if("java.lang.Float".equals(parameter.getTypeName())){
            list.add(Float.parseFloat((String) value));
        }else if("float".equals(parameter.getTypeName())){
            list.add(Float.parseFloat((String) value));
        }else if("java.lang.Short".equals(parameter.getTypeName())){
            list.add(Short.parseShort((String) value));
        }else if("shrot".equals(parameter.getTypeName())){
            list.add(Short.parseShort((String) value));
        }else if("java.lang.Byte".equals(parameter.getTypeName())){
            list.add(Byte.parseByte((String) value));
        }else if("byte".equals(parameter.getTypeName())){
            list.add(Byte.parseByte((String) value));
        }else if("java.lang.Boolean".equals(parameter.getTypeName())){
            if("false".equals(value) || "0".equals(value)){
                list.add(false);
            }else if("true".equals(value) || "1".equals(value)){
                list.add(true);
            }
        }else if("boolean".equals(parameter.getTypeName())){
            if("false".equals(value) || "0".equals(value)){
                list.add(false);
            }else if("true".equals(value) || "1".equals(value)){
                list.add(true);
            }
        }else if("java.util.ArrayList".equals(parameter.getTypeName())){
            list.add((ArrayList<LinkedHashMap<String, Object>>)value);
        }
    }
}