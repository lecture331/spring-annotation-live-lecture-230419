package com.example.market.merchants;

import com.example.annotation.Merchant;
import com.example.market.merchants.inf.MerchantInterface;

@Merchant(value = "농협", item = {"사과", "수박", "바나나", "오렌지"})
public class NongHyup implements MerchantInterface {

    @Override
    public String sellFruit(String fruitName, int amount) {
        return null;
    }
}
