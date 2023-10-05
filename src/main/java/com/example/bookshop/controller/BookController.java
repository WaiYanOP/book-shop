package com.example.bookshop.controller;

import com.example.bookshop.ds.CartItem;
import com.example.bookshop.entity.Book;
import com.example.bookshop.entity.Customer;
import com.example.bookshop.service.BookService;
import com.example.bookshop.service.CartService;
import com.example.bookshop.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
    private final CartService cartService;

    private final BookService bookService;
    private final CustomerService customerService;
    @GetMapping("/books")
    public String listAllBooks(Model model){
        model.addAttribute("books", bookService.findAllBooks());
        return "books";
    }

    @GetMapping("/books/detail")
    public String BookDetails(@RequestParam("id") int id, Model model){
        model.addAttribute("book",bookService.findBookById(id));
        return "bookdetails";
    }

    @ModelAttribute("cartSize")
    public int cartSize(){
        return cartService.cartSize();
    }

    @GetMapping("/books/add")
    public String addToCart(@RequestParam("id") int id){
        cartService.addToCart(bookService.findBookById(id));
        return "redirect:/book/books/detail?id=" + id;
    }

    public boolean action = false;
    @GetMapping("/books/view/cart")
    public String viewCart(Model model){
        model.addAttribute("action", false);
        model.addAttribute("book", new Book());
        //cartService.setValueRenderer(false);
        model.addAttribute("cardItems", cartService.listCartItem());
        return "viewCart";
    }

    @GetMapping("/delete")
    public String removeCartItem(@RequestParam("id")int id){
        cartService.deleteById(id);
        return "redirect:/book/books/view/cart";
    }

    @GetMapping("/remove/all")
    public String removeAll(){
        cartService.clearCart();
        return "redirect:/book/books/view/cart";
    }

    @PostMapping("/checkout")
    public String checkout(Book book, Model model){
        int i = 0;
        for (CartItem cartItem:cartService.listCartItem()){
            cartItem.setQuantity(book.getQuantityList().get(i));
            i++;
        }
        System.out.println(cartService.listCartItem() + "===================");
        /*model.addAttribute("action", true);
        cartService.setValueRenderer(true);
        cartService.clearCart();*/
        return "redirect:/book/register";
    }

    @GetMapping("/register")
    public String registerCustomer(Model model){
        model.addAttribute("customer", new Customer());
        return "register";
    }

    @PostMapping("/save-customer")
    public String saveCustomer(Customer customer, BindingResult result){
        if (result.hasErrors()){
            return "register";
        }
        customerService.saveCustomer(customer);
        cartService.clearCart();
        return "redirect:/login";
    }

    @ModelAttribute("totalPrice")
    public double totalPrice(){
        return cartService.listCartItem()
                .stream()
                .map(c -> {
                    c.setSum(c.getPrice() * c.getQuantity());
                    return c.getSum();
                })
                .mapToDouble(i -> i)
                .sum();
    }

    @GetMapping("/info")
    public String showInfo(Model model){
        model.addAttribute("cartItems", cartService.listCartItem());
        return "info";
    }
}
