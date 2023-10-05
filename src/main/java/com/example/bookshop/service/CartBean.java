package com.example.bookshop.service;

import com.example.bookshop.ds.CartItem;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashSet;
import java.util.Set;

@SessionScope
@Component
@Data
public class CartBean {
    private Set<CartItem> cardItems = new HashSet<>();
    public void addcartItem(CartItem cartItem){
        cardItems.add(cartItem);
    }
}
