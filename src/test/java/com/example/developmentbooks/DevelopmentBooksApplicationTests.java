package com.example.developmentbooks;

import com.example.developmentbooks.domain.Book;
import com.example.developmentbooks.domain.DiscountStack;
import com.example.developmentbooks.domain.DiscountType;
import com.example.developmentbooks.logic.BookDiscountMaker;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DevelopmentBooksApplicationTests {

    private static final Book A = new Book("A", 50.0);
    private static final Book B = new Book("B", 50.0);
    private static final Book C = new Book("C", 50.0);
    private static final Book D = new Book("D", 50.0);
    private static final Book E = new Book("E", 50.0);

    @Test
    void twoDifferentBooksProduceOneSetOfTwo() {
        BookDiscountMaker maker = new BookDiscountMaker();

        Map<Book, Integer> books = new HashMap<>();
        books.put(A, 1);
        books.put(B, 1);

        List<DiscountStack> stacks = maker.buildDiscountStacks(books);

        assertEquals(1, stacks.size());
        assertEquals(
                DiscountType.TWO_DIFFERENT_BOOKS,
                stacks.get(0).discountType()
        );
    }

    @Test
    void threeDifferentBooksProduceOneSetOfThree() {
        BookDiscountMaker maker = new BookDiscountMaker();

        Map<Book, Integer> books = new HashMap<>();
        books.put(A, 1);
        books.put(B, 1);
        books.put(C, 1);

        List<DiscountStack> stacks = maker.buildDiscountStacks(books);

        assertEquals(1, stacks.size());
        assertEquals(
                DiscountType.THREE_DIFFERENT_BOOKS,
                stacks.get(0).discountType()
        );
    }

    @Test
    void fourDifferentBooksProduceOneSetOfFour() {
        BookDiscountMaker maker = new BookDiscountMaker();

        Map<Book, Integer> books = new HashMap<>();
        books.put(A, 1);
        books.put(B, 1);
        books.put(C, 1);
        books.put(D, 1);

        List<DiscountStack> stacks = maker.buildDiscountStacks(books);

        assertEquals(1, stacks.size());
        assertEquals(
                DiscountType.FOUR_DIFFERENT_BOOKS,
                stacks.get(0).discountType()
        );
    }

    @Test
    void fiveDifferentBooksProduceOneSetOfFive() {
        BookDiscountMaker maker = new BookDiscountMaker();

        Map<Book, Integer> books = new HashMap<>();
        books.put(A, 1);
        books.put(B, 1);
        books.put(C, 1);
        books.put(D, 1);
        books.put(E, 1);

        List<DiscountStack> stacks = maker.buildDiscountStacks(books);

        assertEquals(1, stacks.size());
        assertEquals(
                DiscountType.FIVE_DIFFERENT_BOOKS,
                stacks.get(0).discountType()
        );
    }

    @Test
    void prefersFourPlusFourOverFivePlusThree() {
        BookDiscountMaker maker = new BookDiscountMaker();

        Map<Book, Integer> books = new HashMap<>();
        books.put(A, 2);
        books.put(B, 2);
        books.put(C, 2);
        books.put(D, 1);
        books.put(E, 1);

        List<DiscountStack> stacks = maker.buildDiscountStacks(books);

        assertEquals(2, stacks.size());
        assertTrue(stacks.stream()
                .allMatch(stack ->
                        stack.discountType() == DiscountType.FOUR_DIFFERENT_BOOKS
                ));
    }

    @Test
    void totalDiscountedPriceIsCorrectForFourPlusFourCase() {
        BookDiscountMaker maker = new BookDiscountMaker();

        Map<Book, Integer> books = new HashMap<>();
        books.put(A, 2);
        books.put(B, 2);
        books.put(C, 2);
        books.put(D, 1);
        books.put(E, 1);

        List<DiscountStack> stacks = maker.buildDiscountStacks(books);

        double totalDiscountedPrice = stacks.stream()
                .mapToDouble(DiscountStack::discountedPrice)
                .sum();

        assertEquals(320.0, totalDiscountedPrice);
    }

    @Test
    void sixDifferentBooksShouldThrowException() {
        BookDiscountMaker maker = new BookDiscountMaker();

        Map<Book, Integer> books = new HashMap<>();
        books.put(A, 1);
        books.put(B, 1);
        books.put(C, 1);
        books.put(D, 1);
        books.put(E, 1);
        books.put(new Book("F", 50.0), 1);

        assertThrows(
                IllegalArgumentException.class,
                () -> maker.buildDiscountStacks(books)
        );
    }

}
