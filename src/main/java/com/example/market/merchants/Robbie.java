package com.example.market.merchants;

import com.example.annotation.Fruit;
import com.example.annotation.Merchant;
import com.example.market.merchants.inf.MerchantInterface;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Set;

@Merchant(item = {"사과", "참외", "딸기"})
public class Robbie implements MerchantInterface {
    @Fruit(value = "수입", name = "사과", price = 1000)
    private static int apple = 25;
    @Fruit(name = "참외", price = 1500)
    private static int melon = 17;
    @Fruit(name = "딸기", price = 500)
    private static int strawberry = 120;

    private static int cash;
    private static int price;

    private static Set<Field> fields;

    private static void initializeFieldInfo() {
        fields = ReflectionUtils.getFields(Robbie.class, ReflectionUtils.withAnnotation(Fruit.class));
    }

    @Override
    public String sellFruit(String fruitName, int amount) {
        Field field = findField(fruitName);
        if(Objects.nonNull(field)) {
            try {
                int originAmount = (int) field.get(null);

                if(originAmount >= amount) {
                    field.set(null, originAmount - amount);
                    cash += price * amount;

                    System.out.println("과일명 = " + fruitName);
                    System.out.println("가격 = " + price);
                    System.out.println("남은 수량 = " + field.get(null) + "개");
                    System.out.println("판매 수익 = " + cash + "원");

                    return "판매가 완료되었습니다.";
                } else {
                    return "수량을 다시 확인해주세요!";
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        } else {
            return "해당하는 과일은 판매하지 않습니다.";
        }
    }

    private Field findField(String fruitName) {
        if(Objects.nonNull(fields)) {
            if(!fields.isEmpty()) {
                for (Field field : fields) {
                    if(field.isAnnotationPresent(Fruit.class)) {
                        Fruit fruit = field.getAnnotation(Fruit.class);
                        if(fruit.name().equals(fruitName)) {
                            price = fruit.price();
                            return field;
                        }
                    }
                }
            }
        } else {
            initializeFieldInfo();
            return findField(fruitName);
        }
        return null;
    }


}
