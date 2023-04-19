package com.example.market;

import com.example.annotation.Merchant;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class FruitStore {

    private final Set<Class<?>> merchants = new HashSet<>();
    private final Map<Class<?>, Object> merchantList = new HashMap<>();

    public FruitStore() {
        Reflections reflections = new Reflections("com.example.market.merchants");
        merchants.addAll(reflections.getTypesAnnotatedWith(Merchant.class));
        initializeMerchants();
    }

    private void initializeMerchants() {
        // merchant 정보를 읽어 인스턴스화를 한 후 저장한다.
        for (Class<?> merchant : merchants) {
            try {
                Object instance = merchant.getConstructor().newInstance(); // 인스턴스화
                merchantList.put(merchant, instance);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    public String store(String fruitName, int amount, Class<?> clazz) {
        Object merchant = merchantList.get(clazz); // Robbie 객체
        if (Objects.nonNull(merchant)) {
            try {
                Method method = merchant.getClass().getMethod("sellFruit", String.class, int.class); // Robbie.class.getMethod();
                return (String) method.invoke(merchant, fruitName, amount);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
                throw new RuntimeException("Method Error");
            }
        } else {
            return "해당 판매자는 영업을 하지 않습니다.";
        }
    }


}
