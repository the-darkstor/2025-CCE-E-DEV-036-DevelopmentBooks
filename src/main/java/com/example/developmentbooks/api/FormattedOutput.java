package com.example.developmentbooks.api;

import com.example.developmentbooks.domain.Book;
import com.example.developmentbooks.domain.DiscountStack;

import java.util.List;
import java.util.Map;

public class FormattedOutput {

    public String formatOutput(
            List<DiscountStack> discountStacks,
            Map<Book, Integer> remainingBooks
    ) {
        StringBuilder sb = new StringBuilder();

        double discountedTotal = 0;

        sb.append("DISCOUNTED SETS\n");
        sb.append("----------------\n\n");

        int setNumber = 1;
        for (DiscountStack stack : discountStacks) {

            sb.append("Set ")
                    .append(setNumber++)
                    .append("\n");

            sb.append("Books:\n");

            stack.books().stream()
                    .map(Book::name)
                    .sorted()
                    .forEach(name ->
                            sb.append("  - ").append(name).append("\n")
                    );

            sb.append("Base price: ")
                    .append(stack.basePrice())
                    .append("\n");

            sb.append("Discount: ")
                    .append(stack.discountType().getDiscountRate() * 100)
                    .append("%\n");

            sb.append("Discounted price: ")
                    .append(stack.discountedPrice())
                    .append("\n\n");

            discountedTotal += stack.discountedPrice();
        }

        sb.append("Total discounted: ")
                .append(discountedTotal)
                .append("\n\n");

        // ---- NON-DISCOUNTED BOOKS ----
        double fullPriceTotal = 0;

        sb.append("NON-DISCOUNTED BOOKS\n");
        sb.append("--------------------\n");

        boolean hasNonDiscountedBooks = false;

        for (Map.Entry<Book, Integer> entry : remainingBooks.entrySet()) {
            if (entry.getValue() > 0) {
                hasNonDiscountedBooks = true;

                double price = entry.getKey().price() * entry.getValue();

                sb.append("  - ")
                        .append(entry.getKey().name())
                        .append(" x")
                        .append(entry.getValue())
                        .append(" = ")
                        .append(price)
                        .append("\n");

                fullPriceTotal += price;
            }
        }

        if (!hasNonDiscountedBooks) {
            sb.append("  (none)\n");
        }

        sb.append("Total non-discounted: ")
                .append(fullPriceTotal)
                .append("\n\n");

        // ---- FINAL TOTAL ----
        sb.append("FINAL TOTAL\n");
        sb.append("-----------\n");
        sb.append(discountedTotal + fullPriceTotal);

        return sb.toString();
    }
}