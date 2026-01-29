package com.example.developmentbooks.domain;

public enum DiscountType {
    TWO_DIFFERENT_BOOKS(0.05, 2),
    THREE_DIFFERENT_BOOKS(0.10, 3),
    FOUR_DIFFERENT_BOOKS(0.20, 4),
    FIVE_DIFFERENT_BOOKS(0.25, 5);

    private final double discountRate;
    private final int setSize;

    DiscountType (double discountRate, int setSize) {
        this.discountRate = discountRate;
        this.setSize = setSize;
    }

    public double applyDiscount(double basePrice) {
        return basePrice * (1.0 - discountRate);
    }

    public static DiscountType fromSetSize(int size) {
        for (DiscountType type : values()) {
            if (type.setSize == size) {
                return type;
            }
        }
        throw new IllegalArgumentException("No discount for set size: " + size);
    }

    public double getDiscountRate() {
        return discountRate;
    }
}
