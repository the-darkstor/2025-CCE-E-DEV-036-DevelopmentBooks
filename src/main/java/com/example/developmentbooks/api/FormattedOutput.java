package com.example.developmentbooks.api;

import com.example.developmentbooks.domain.Book;
import com.example.developmentbooks.domain.DiscountStack;

import java.util.List;

public class FormattedOutput {

    public String formatOutput(List<DiscountStack> discountStacks) {
        StringBuilder sb = new StringBuilder();

        for(DiscountStack discountStack : discountStacks) {
            sb.append("Books: \n");
            for(Book book : discountStack.books()) {
                sb.append(book.name())
                        .append(", Price: ")
                        .append(book.price());
                sb.append("\n");
            }
            sb.append("Total without discounts: ")
                    .append(discountStack.basePrice())
                    .append("\n");
            sb.append("Total with discounts ")
                    .append("(")
                    .append(discountStack.discountType().getDiscountRate() * 100)
                    .append("%")
                    .append("): ")
                    .append(discountStack.discountedPrice())
                    .append("\n\n");

        }

        sb.append("Total final price: ");
        sb.append(discountStacks.stream().mapToDouble(DiscountStack::discountedPrice).sum());

        return sb.toString();
    }
}
