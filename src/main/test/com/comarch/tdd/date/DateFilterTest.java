package com.comarch.tdd.date;

import com.comarch.tdd.date.ConfigurableFilter;
import com.comarch.tdd.date.DateFilter;
import com.comarch.tdd.date.FilterCriteria;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;


public class DateFilterTest {

    private ConfigurableFilter dateFilter;

    @Before
    public void setUp() {
        this.dateFilter = new DateFilter();
    }

    @Test
    public void shouldReturnEmptySetWhenDataSetIsNull() {
        // when
        Set<LocalDate> result = dateFilter.filter(null, FilterCriteria.WEEKENDS);
        // then
        assertTrue(result.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenFilterCriteriaIsNull() {
        // when
        dateFilter.filter(null, null);
    }

    @Test
    public void shouldReturnWeekendsWhenFilterCriteriaIsWeekend() {
        //given
        LocalDate saturday = LocalDate.of(2018, 6, 30);
        LocalDate friday = LocalDate.of(2018, 6, 29);
        Set<LocalDate> dateSet = new HashSet<>();
        dateSet.add(saturday);
        dateSet.add(friday);

        //when
        Set<LocalDate> result = dateFilter.filter(dateSet, FilterCriteria.WEEKENDS);

        //then
        assertFalse(result.contains(friday));
        assertTrue(result.contains(saturday));
    }

    @Test
    public void shouldReturnNoMoreThanFiveElementsInOutputList() {
        //given
        LocalDate otherFriday = LocalDate.of(2018, 6, 8);
        LocalDate friday = LocalDate.of(2018, 6, 29);
        LocalDate otherMonday = LocalDate.of(2018, 6, 18);
        LocalDate monday = LocalDate.of(2018, 6, 11);
        LocalDate wednesday = LocalDate.of(2018, 6, 6);
        LocalDate tuesday = LocalDate.of(2018, 6, 19);
        Set<LocalDate> dateSet = new HashSet<>();
        dateSet.add(otherFriday);
        dateSet.add(friday);
        dateSet.add(monday);
        dateSet.add(otherMonday);
        dateSet.add(wednesday);
        dateSet.add(tuesday);

        //when
        Set<LocalDate> result = dateFilter.filter(dateSet, FilterCriteria.WORKING_DAYS);

        //then
        assertTrue(result.size() <= 5);
    }

}