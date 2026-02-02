package com.example.developmentbooks;

import com.example.developmentbooks.api.BooksInput;
import com.example.developmentbooks.util.BooksInputValidator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BooksInputValidatorTest {

    @Test
    void validInputWithFiveDistinctBooksDoesNotThrow() {
        List<BooksInput> inputs = List.of(
                new BooksInput("A", 1),
                new BooksInput("B", 2),
                new BooksInput("C", 3),
                new BooksInput("D", 1),
                new BooksInput("E", 1)
        );

        assertDoesNotThrow(() ->
                BooksInputValidator.validate(inputs)
        );
    }

    @Test
    void emptyInputListThrowsException() {
        List<BooksInput> inputs = new ArrayList<>();

        assertThrows(
                IllegalArgumentException.class,
                () -> BooksInputValidator.validate(inputs)
        );
    }

    @Test
    void nullInputListThrowsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> BooksInputValidator.validate(null)
        );
    }

    @Test
    void nullBookEntryThrowsException() {
        List<BooksInput> inputs = new ArrayList<>();
        inputs.add(null);

        assertThrows(
                IllegalArgumentException.class,
                () -> BooksInputValidator.validate(inputs)
        );
    }

    @Test
    void blankTitleThrowsException() {
        List<BooksInput> inputs = List.of(
                new BooksInput(" ", 1)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> BooksInputValidator.validate(inputs)
        );
    }

    @Test
    void nullTitleThrowsException() {
        List<BooksInput> inputs = List.of(
                new BooksInput(null, 1)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> BooksInputValidator.validate(inputs)
        );
    }

    @Test
    void quantityZeroThrowsException() {
        List<BooksInput> inputs = List.of(
                new BooksInput("A", 0)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> BooksInputValidator.validate(inputs)
        );
    }

    @Test
    void negativeQuantityThrowsException() {
        List<BooksInput> inputs = List.of(
                new BooksInput("A", -1)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> BooksInputValidator.validate(inputs)
        );
    }

    @Test
    void moreThanFiveDistinctBooksThrowsException() {
        List<BooksInput> inputs = List.of(
                new BooksInput("A", 1),
                new BooksInput("B", 1),
                new BooksInput("C", 1),
                new BooksInput("D", 1),
                new BooksInput("E", 1),
                new BooksInput("F", 1)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> BooksInputValidator.validate(inputs)
        );
    }

    @Test
    void duplicateTitlesAreAllowedAndCountAsOneDistinctBook() {
        List<BooksInput> inputs = List.of(
                new BooksInput("A", 1),
                new BooksInput("A", 2),
                new BooksInput("B", 1),
                new BooksInput("C", 1),
                new BooksInput("D", 1)
        );

        assertDoesNotThrow(() ->
                BooksInputValidator.validate(inputs)
        );
    }
}
