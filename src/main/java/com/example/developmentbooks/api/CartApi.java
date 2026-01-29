package com.example.developmentbooks.api;

import com.example.developmentbooks.domain.Book;
import com.example.developmentbooks.domain.DiscountStack;
import com.example.developmentbooks.logic.BookDiscountMaker;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class CartApi {

    private BookDiscountMaker bookDiscountMaker;

    @PostMapping("/calculateCart")
    public ResponseEntity<String> calculateCart(@RequestBody List<BooksInput> inputs) {
        FormattedOutput formattedOutput = new FormattedOutput();
        bookDiscountMaker = new BookDiscountMaker();
        Map<Book, Integer> books = parseBooks(inputs);
        List<DiscountStack> discountStacks = bookDiscountMaker.buildDiscountStacks(books);

        return ResponseEntity.accepted()
                .body(formattedOutput.formatOutput(discountStacks, books));
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
}
