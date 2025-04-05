package com.example.SchoolLunchReport.statistics.domain.type;

import java.time.LocalDate;

public enum Period {
    WEEKLY, MONTHLY;
    private final static int PERIOD_BOUNDARY = 1;

    public static LocalDate getConditionDate(Period period) {
        LocalDate today = LocalDate.now();
        return switch (period) {
            case WEEKLY -> today.minusWeeks(PERIOD_BOUNDARY);
            case MONTHLY -> today.minusMonths(PERIOD_BOUNDARY);
        };
    }
}
