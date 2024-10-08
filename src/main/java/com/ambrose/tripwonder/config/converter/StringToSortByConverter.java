package com.ambrose.tripwonder.config.converter;

import com.ambrose.tripwonder.entities.enums.SortBy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToSortByConverter implements Converter<String, SortBy> {
    @Override
    public SortBy convert(String source) {
        switch (source.toLowerCase()) {
            case "attendanceasc":
                return SortBy.SORT_BY_NUM_ATTENDANCE_ASC;
            case "attendancedesc":
                return SortBy.SORT_BY_NUM_ATTENDANCE_DESC;
            case "dateasc":
                return SortBy.SORT_BY_DATE_ASC;
            case "datedesc":
                return SortBy.SORT_BY_DATE_DESC;
            case "priceasc":
                return SortBy.SORT_BY_PRICE_ASC;
            case "pricedesc":
                return SortBy.SORT_BY_PRICE_DESC;
            default:
                throw new IllegalArgumentException("Invalid sort parameter: " + source);
        }
    }
}
