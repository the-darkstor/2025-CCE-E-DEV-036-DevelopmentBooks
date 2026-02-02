package com.example.developmentbooks.api;

import com.example.developmentbooks.domain.Book;
import com.example.developmentbooks.domain.DiscountStack;
import com.example.developmentbooks.logic.BookDiscountMaker;
import com.example.developmentbooks.util.BooksInputValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class CartApi {

    private BookDiscountMaker bookDiscountMaker;

    private static Logger log = LoggerFactory.getLogger(CartApi.class.getName());

    @PostMapping("/calculateCart")
    public ResponseEntity<String> calculateCart(@RequestBody List<BooksInput> inputs) {
        ResponseEntity<String> response;
        FormattedOutput formattedOutput = new FormattedOutput();
        bookDiscountMaker = new BookDiscountMaker();
        List<DiscountStack> discountStacks;
        Map<Book, Integer> books;

        try {
            BooksInputValidator.validate(inputs);
            books = parseBooks(inputs);
            discountStacks = bookDiscountMaker.buildDiscountStacks(books);
            response = ResponseEntity.accepted()
                    .body(formattedOutput.formatOutput(discountStacks, books));
            log.info("Books processed successfully");
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("API error: ");
            sb.append(e.getMessage());

            log.error(e.getMessage());
            response = ResponseEntity.internalServerError()
                    .body(sb.toString());
        }

        return response;
    }

    private Map<Book, Integer> parseBooks(List<BooksInput> inputs) {
        Map<Book, Integer> bookIntegerMap = new HashMap<>();
        if (inputs != null && !inputs.isEmpty()) {
            for(BooksInput input : inputs) {
                bookIntegerMap.put(new Book(input.title(), 50.0), input.quantity());
            }
        }
        return bookIntegerMap;
    }

    private boolean validateDistinctBooks(List<BooksInput> inputs) {
        return inputs.size() <= 5;
    }
}
