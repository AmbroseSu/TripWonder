package com.ambrose.tripwonder.entities.enums;

import lombok.Getter;
import org.springframework.data.domain.Sort;

@Getter
public enum SortBy {
    SORT_BY_NUM_ATTENDANCE_DESC("numAttendance", Sort.Direction.DESC),
    SORT_BY_NUM_ATTENDANCE_ASC("numAttendance", Sort.Direction.ASC),
    SORT_BY_DATE_DESC("startTime", Sort.Direction.DESC),
    SORT_BY_DATE_ASC("startTime", Sort.Direction.ASC),
    SORT_BY_PRICE_DESC("price", Sort.Direction.DESC),
    SORT_BY_PRICE_ASC("price", Sort.Direction.ASC);

    private final String field;
    private final Sort.Direction direction;

    SortBy(String field, Sort.Direction direction) {
        this.field = field;
        this.direction = direction;
    }

}


