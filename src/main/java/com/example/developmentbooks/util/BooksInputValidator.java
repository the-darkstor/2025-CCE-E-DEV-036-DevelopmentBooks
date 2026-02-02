package com.example.developmentbooks.util;


import com.example.developmentbooks.api.BooksInput;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BooksInputValidator {

    private static final int MAX_DISTINCT_BOOKS = 5;

    public static void validate(List<BooksInput> inputs) {

        if (inputs == null || inputs.isEmpty()) {
            throw new IllegalArgumentException(
                    "Input must be a non-empty list of books"
            );
        }

        Set<String> distinctTitles = new HashSet<>();

        for (int i = 0; i < inputs.size(); i++) {
            BooksInput input = inputs.get(i);

            if (input == null) {
                throw new IllegalArgumentException(
                        "Book entry at index " + i + " is null"
                );
            }

            if (input.title() == null || input.title().isBlank()) {
                throw new IllegalArgumentException(
                        "Book title at index " + i + " must not be null or empty"
                );
            }

            if (input.quantity() <= 0) {
                throw new IllegalArgumentException(
                        "Quantity for book '" + input.title() + "' must be greater than 0"
                );
            }

            distinctTitles.add(input.title());
        }

        if (distinctTitles.size() > MAX_DISTINCT_BOOKS) {
            throw new IllegalArgumentException(
                    "Maximum number of distinct books allowed is "
                            + MAX_DISTINCT_BOOKS
                            + " (found " + distinctTitles.size() + ")"
            );
        }
    }
}
