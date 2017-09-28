package com.xhj.bms.util;


import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Projack
 */
public class BeanUtils {

    private static Logger logger = Logger.getLogger(BeanUtils.class);


    /**
     * 合并obj对象的值到_obj里
     * @param _obj
     * @param obj
     * @return
     */
    public static  Object getTransObj(Object _obj, Object obj){
        Class _clasz = _obj.getClass();
        Class clasz = obj.getClass();
        try{
            if (obj == null) {
                return _obj;
            }
            Field[] fields = clasz.getDeclaredFields();
            for (Field field : fields){
                String fieldName = field.getName();
                Object fieldValue = getClassValue(obj,fieldName);
                if(fieldValue != null){
                    Field _field = _clasz.getDeclaredField(fieldName);
                    _field.setAccessible(true);
                    _field.set(_obj,fieldValue);
                }
            }
            return _obj;
        }catch (Exception e){
            logger.error("取方法出错！" + e.getStackTrace());
            return _obj;
        }
    }

    /**
     * 根据字段名称取值
     * @param obj
     * @param fieldName
     * @return
     */
    public static Object getClassValue(Object obj, String fieldName) {
        if (obj == null) {
            return null;
        }
        try {
            Class beanClass = obj.getClass();
            Method[] ms = beanClass.getMethods();
            for (int i = 0; i < ms.length; i++) {
                // 只取get方法
                if (!ms[i].getName().startsWith("get")) {
                    continue;
                }
                Object objValue = null;
                try {
                    objValue = ms[i].invoke(obj, new Object[] {});
                } catch (Exception e) {
                    logger.error("反射取值出错：" + e.getStackTrace());
                    continue;
                }
                if (objValue == null) {
                    continue;
                }
                if (ms[i].getName().toUpperCase().equals(fieldName.toUpperCase()) || ms[i].getName().substring(3).toUpperCase().equals(fieldName.toUpperCase())) {
                    return objValue;
                } else if (ms[i].getName().substring(3).toUpperCase().equals("ID")) {
                    return objValue;
                }
            }
        } catch (Exception e) {
            logger.error("取方法出错！" + e.getStackTrace());
        }
        return null;
    }
}