package com.artocons.carshop.persistence.enums;

public enum RatingStar {
    ONE_STAR(1),
    TWO_STARS(2),
    THREE_STARS(3),
    FOUR_STARS(4),
    FIVE_STARS(5);

    private final int value;

    RatingStar(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static RatingStar fromValue(int value) {
        for (RatingStar rating : RatingStar.values()) {
            if (rating.getValue() == value) {
                return rating;
            }
        }
        throw new IllegalArgumentException("Unknown rating value: " + value);
    }
}
