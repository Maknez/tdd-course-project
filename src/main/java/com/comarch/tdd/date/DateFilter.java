package com.comarch.tdd.date;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

public class DateFilter implements ConfigurableFilter {

    @Override
    public Set<LocalDate> filter(Set<LocalDate> dateSet, FilterCriteria filterCriteria) {
        checkFilterCriteria(filterCriteria);

        if (dateSet == null) {
            return Collections.emptySet();
        }

        Set<LocalDate> weekendDaysSet = new HashSet<>();
        Set<LocalDate> weekDaysSet = new HashSet<>();

        if (filterCriteria == FilterCriteria.WEEKENDS) {
            addToWeekendSetOnlyWeekendDays(dateSet, weekendDaysSet);
            return weekendDaysSet;
        } else if (filterCriteria == FilterCriteria.WORKING_DAYS) {
            addToWeekDaysSetOnlyWeekDays(dateSet, weekDaysSet);
            return weekDaysSet;
        }
        return Collections.emptySet();
    }

    private void addToWeekDaysSetOnlyWeekDays(Set<LocalDate> dateSet, Set<LocalDate> weekDaysSet) {
        for (LocalDate date : dateSet) {
            if (isWeekDay(date)) {
                if (weekDaysSet.size() == 5) {
                    break;
                }
                weekDaysSet.add(date);
            }
        }
    }

    private boolean isWeekDay(LocalDate date) {
        return !date.getDayOfWeek().equals(DayOfWeek.SATURDAY) || !date.getDayOfWeek().equals(DayOfWeek.SUNDAY);
    }

    private void addToWeekendSetOnlyWeekendDays(Set<LocalDate> dateSet, Set<LocalDate> weekendsSet) {
        for (LocalDate date : dateSet) {
            if (isWeekend(date)) {
                if (weekendsSet.size() < 5) {
                    weekendsSet.add(date);
                }
            }
        }
    }

    private boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek().equals(DayOfWeek.SATURDAY) || date.getDayOfWeek().equals(DayOfWeek.SUNDAY);
    }

    private void checkFilterCriteria(FilterCriteria filterCriteria) {
        if (filterCriteria == null) {
            throw new IllegalArgumentException("Filter criteria is null!");
        }
    }

}