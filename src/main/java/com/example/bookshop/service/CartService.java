package com.example.bookshop.service;

import com.example.bookshop.ds.CartItem;
import com.example.bookshop.entity.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartBean cartBean;

    public int cartSize(){
        return cartBean.getCardItems().size();
    }

    public void addToCart(Book book){
        this.cartBean.addcartItem(
                CartItem.builder()
                        .id(book.getId())
                        .title(book.getTitle())
                        .author(book.getAuthor())
                        .price(book.getPrice())
                        .build()
        );
    }
    public Set<CartItem> listCartItem(){
        return cartBean.getCardItems();
    }

    public void deleteById(int id){
        cartBean.getCardItems().removeIf(
                c -> c.getId() == id
        );
    }

    public void clearCart(){
        cartBean.getCardItems().clear();
    }

    public  void setValueRenderer(boolean value){
        cartBean.setCardItems(
                cartBean.getCardItems()
                        .stream().map(c->{
                            c.setRenderer(value);
                            return c;
                        }).collect(Collectors.toSet())
        );
    }

    /*public void addToCartItemQuatity(List<Integer> list){
        int i = 0;
        for (CartItem cartItem : cartBean.getCardItems()){
            cartItem.getQuantityList().add(list.get(i));
            i++;
        }
    }*/
}
