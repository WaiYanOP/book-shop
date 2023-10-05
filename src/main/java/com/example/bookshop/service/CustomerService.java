package com.example.bookshop.service;

import com.example.bookshop.dao.CustomerDao;
import com.example.bookshop.ds.CartItem;
import com.example.bookshop.entity.BookItem;
import com.example.bookshop.entity.Customer;
import com.example.bookshop.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerDao customerDao;
    private final PasswordEncoder passwordEncoder;
    private final CartService cartService;

    @Transactional
    public void saveCustomer(Customer customer){
        Optional<Customer> customerOptional = customerDao.findByName(customer.getName());
        if (!customerOptional.isPresent()) {
            customer.setPassword(passwordEncoder.encode(customer.getPassword()));
            customer.setRole(Role.ROLE_USER);
            addBookItems(customer);
            customerDao.save(customer);
        }
        else {
            Customer customer1 = customerOptional.get();
            customer1.setId(customer1.getId());
            customer1.setPassword(customer1.getPassword());
            var customerBookItems = customer1.getBookItems();
            var bookItems = cartService.listCartItem()
                            .stream()
                                    .map(c -> tobookItemOld(c)).collect(Collectors.toSet());
            for (BookItem item : bookItems){
                item.setCustomer(customer1);
                customerBookItems.add(item);
            }
            customer1.setBookItems(customerBookItems);
            customerDao.save(customer1);
        }
    }

    private void addBookItems(Customer customer) {
        cartService.listCartItem()
                .stream()
                .forEach(c -> {
                    customer.addBook(tobookItem(c));
                });
    }

    private BookItem tobookItemOld(CartItem cartItem){
        return new BookItem(
                cartItem.getId(),
                cartItem.getTitle(),
                cartItem.getAuthor(),
                cartItem.getPrice(),
                cartItem.getQuantity(),
                cartItem.getPrice() * cartItem.getQuantity()
        );
    }

    private BookItem tobookItem(CartItem cartItem){
        return new BookItem(
                cartItem.getId(),
                cartItem.getTitle(),
                cartItem.getAuthor(),
                cartItem.getPrice(),
                cartItem.getQuantity(),
                cartItem.getPrice() * cartItem.getQuantity()
        );
    }
}