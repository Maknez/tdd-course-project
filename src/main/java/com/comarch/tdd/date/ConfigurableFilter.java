package com.comarch.tdd.date;

import java.time.LocalDate;
import java.util.Set;

public interface ConfigurableFilter {

    Set<LocalDate> filter(Set<LocalDate> dateList, FilterCriteria filterCriteria);

}
