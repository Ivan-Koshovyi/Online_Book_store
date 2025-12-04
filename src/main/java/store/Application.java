package store;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import store.model.Book;
import store.service.BookService;

@SpringBootApplication
public class Application {
    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Book book = new Book();
                book.setAuthor("Author");
                book.setIsbn("1234");
                book.setTitle("My Book Title");
                book.setPrice(new BigDecimal("199.99"));

                bookService.save(book);

                System.out.println(bookService.getAll());
            }
        };
    }

}
