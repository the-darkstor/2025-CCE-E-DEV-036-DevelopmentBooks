package com.example.developmentbooks.api;

import com.example.developmentbooks.domain.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class CartApi {

    @PostMapping("/calculateCart")
    public ResponseEntity<String> calculateCart(@RequestBody List<BooksInput> inputs) {
        StringBuilder result = new StringBuilder();
        Map<Book, Integer> books = parseBooks(inputs);

        return ResponseEntity.accepted().build();
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
