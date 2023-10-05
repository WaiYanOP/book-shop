package com.example.bookshop;

import com.example.bookshop.dao.BookDao;
import com.example.bookshop.entity.Book;
import com.example.bookshop.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class BookShopApplication {

    private final BookDao bookDao;

    public static void main(String[] args) {
        SpringApplication.run(BookShopApplication.class, args);
    }

    @Bean @Transactional @Profile("test")
    public ApplicationRunner runner(){
        return r -> {
            List.of(
                    new Book("England History", 35.5, "Macaulay",
                            "https://source.unsplash.com/366x200/?history",
                            LocalDate.of(1587, 3,21),
                            Category.HISTORICAL),
                    new Book("World History", 40.5, "H.G Wells",
                            "https://source.unsplash.com/366x200/?world",
                            LocalDate.of(1987, 2,1),
                            Category.NOVEL),
                    new Book("Return of Native", 45.5, "Thoams Hardy",
                            "https://source.unsplash.com/366x200/?native",
                            LocalDate.of(1487, 9,11),
                            Category.ROMANCE),
                    new Book("World War", 30.5, "John Doe",
                            "https://source.unsplash.com/366x200/?war",
                            LocalDate.of(1287, 4,15),
                            Category.TRAGEDY),
                    new Book("The Nun", 50.5, "Willian",
                            "https://source.unsplash.com/366x200/?ghost",
                            LocalDate.of(1987, 8,22),
                            Category.HORROR),
                    new Book("City Stairs", 55.5, "Shar Shaw",
                            "https://source.unsplash.com/366x200/?science",
                            LocalDate.of(1887, 9,14),
                            Category.SIFI)
            ).forEach(bookDao::save);
        };
    }

}
