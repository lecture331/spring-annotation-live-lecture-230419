package com.example.bean;

import com.example.annotation.Autowired;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class BeanFactory {

    private final Set<Class<?>> beans;
    private final HashMap<Class<?>, Object> beansStore = new HashMap<>();

    public BeanFactory(Set<Class<?>> beans) {
        this.beans = beans;
        initializeBeans();
    }

    private void initializeBeans() {
        // 클래스 정보들이 담긴 beans들을 인스턴스화 해서 beansStore에 저장
        for (Class<?> bean : beans) {
            Object object = createInstance(bean);
            if (Objects.nonNull(object)) {
                beansStore.put(bean, object);
            }
        }
    }

    private Object createInstance(Class<?> bean) {
        // 생성자 가져오기 #1
        Constructor constructor = getConstructor(bean);
        if (constructor == null) return null;

        // 매개변수 클래스 타입의 객체를 갸저오기 #2
        List<Object> parameters = new ArrayList<>(); // 매개변수 저장소
        for (Class<?> parameterType : constructor.getParameterTypes()) {
            parameters.add(getParameterClass(parameterType));
            // getParameterClass(해당 매개변수 클래스 타입) -> beansStore 에 해당 매개변수의 클래스가 저장되어있는지 확인하고 반환!
        }

        // 인스턴스화
        try {
            return constructor.newInstance(parameters.toArray()); // 해당하는 파라미터의 객체들을 전달해서 인스턴스 생성해서 반환!
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException("Create Bean Error");
        }
    }

    private Constructor getConstructor(Class<?> bean) {
        // #1
        Set<Constructor> allConstructors = ReflectionUtils.getAllConstructors(bean, ReflectionUtils.withAnnotation(Autowired.class));
        if (allConstructors.isEmpty()) {
            try {
                return bean.getConstructor();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return allConstructors.iterator().next();
        }
    }

    private Object getParameterClass(Class<?> parameterType) {
        // #2
        Object instance = getBean(parameterType);

        if (Objects.nonNull(instance)) {
            // 이미 bean 으로 등록되었다면 해당 객체 반환
            return instance;
        }

        return createInstance(parameterType); // 해당 매개변수의 클래스 타입이 아직 bean 으로 등록되어있지 않다면 등록 시키기
    }

    public <T> T getBean(Class<T> classType) {
        // beansStore 에서 해당 클래스타입이 bean 으로 등록되었는지 확인하고 있다면 해당 객체 반환!
        return (T) beansStore.get(classType);
    }
}
