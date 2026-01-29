package com.example.developmentbooks.logic;

import com.example.developmentbooks.domain.Book;
import com.example.developmentbooks.domain.DiscountStack;
import com.example.developmentbooks.domain.DiscountType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookDiscountMaker {

    public List<DiscountStack> buildDiscountStacks(Map<Book, Integer> remainingBooks) {
        List<DiscountStack> stacks = new ArrayList<>();

        while (countDistinctAvailableTitles(remainingBooks) > 1) {

            int distinctTitles = countDistinctAvailableTitles(remainingBooks);
            List<Book> consumedTitles;

            if (distinctTitles == 5) {

                //simulate taking 5 distinct books
                Map<Book, Integer> simulatedBooks = new HashMap<>(remainingBooks);
                consumeDistinctTitles(simulatedBooks, 5);

                int remainingAfterSimulation =
                        countDistinctAvailableTitles(simulatedBooks);

                /*
                if taking 5 now would leave exactly 3 distinct titles, then 5 + 3 is worse than 4 + 4,
                so we take 4 instead to have optimal discount
                */
                if (remainingAfterSimulation == 3) {
                    consumedTitles = consumeDistinctTitles(remainingBooks, 4);
                } else {
                    //if we have no choice, then we take all 5
                    consumedTitles = consumeDistinctTitles(remainingBooks, 5);
                }

            } else {
                consumedTitles =
                        consumeDistinctTitles(remainingBooks, distinctTitles);
            }

            stacks.add(createDiscountStack(consumedTitles));
        }

        return stacks;
    }

    private List<Book> consumeDistinctTitles(
            Map<Book, Integer> books,
            int numberOfTitles
    ) {
        List<Book> consumed = new ArrayList<>();

        List<Map.Entry<Book, Integer>> sortedByQuantity =
                new ArrayList<>(books.entrySet());

        sortedByQuantity.sort(
                Map.Entry.<Book, Integer>comparingByValue().reversed()
        );

        for (Map.Entry<Book, Integer> entry : sortedByQuantity) {
            if (numberOfTitles == 0) {
                break;
            }

            if (entry.getValue() > 0) {
                consumed.add(entry.getKey());
                entry.setValue(entry.getValue() - 1);
                numberOfTitles--;
            }
        }

        return consumed;
    }

    private int countDistinctAvailableTitles(Map<Book, Integer> books) {
        return (int) books.values()
                .stream()
                .filter(quantity -> quantity > 0)
                .count();
    }

    private DiscountStack createDiscountStack(List<Book> titles) {
        DiscountType discountType =
                DiscountType.fromSetSize(titles.size());

        double basePrice = titles.stream()
                .mapToDouble(Book::price)
                .sum();

        double discountedPrice =
                discountType.applyDiscount(basePrice);

        return new DiscountStack(
                titles,
                discountType,
                basePrice,
                discountedPrice
        );
    }
}
