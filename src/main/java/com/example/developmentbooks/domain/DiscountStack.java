package com.example.developmentbooks.domain;

import java.util.List;

public record DiscountStack (
        List<Book> books,
        DiscountType discountType,
        double basePrice,
        double discountedPrice) {
}
