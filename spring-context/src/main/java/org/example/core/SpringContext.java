package org.example.core;

import org.example.annotation.Autowired;
import org.example.annotation.Bean;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SpringContext {
    //bean store
    private final static Map<Class<?>, Class<?>> BEAN_STORE = new HashMap<>();

    static {
        System.out.println("SpringContext.static initializer");
        //get all the types from class path with @Bean
        Set<Class<?>> beans = new Reflections("org.example")
                .getTypesAnnotatedWith(Bean.class);
        beans.forEach(aClass -> {
            //collecting beans
            BEAN_STORE.put(aClass, aClass);
        });
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<?> aClass) {
        if (!BEAN_STORE.containsKey(aClass)) {
            throw new RuntimeException("Bean not found");
        }
        T t = null;
        Class<?> realBean = BEAN_STORE.get(aClass);
        for (Constructor<?> constructor : realBean.getConstructors()) {
            try {
                t = (T) constructor.newInstance();
                //fill the instance variables
                for (Field declaredField : realBean.getDeclaredFields()) {
                    Class<?> type = declaredField.getType();
                    if (!BEAN_STORE.containsKey(type)) {
                        throw new RuntimeException("The been has not been annotated by @Bean");
                    }
                    if (declaredField.isAnnotationPresent(Autowired.class)) {
//                        Autowired annotation = declaredField.getAnnotation(Autowired.class);
                        Class<?> instanceVar = BEAN_STORE.get(declaredField.getType());
                        declaredField.setAccessible(true);
                        declaredField.set(t,instanceVar.newInstance() );
                        declaredField.setAccessible(false);
                    }
                }
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
        return t;
    }

}
