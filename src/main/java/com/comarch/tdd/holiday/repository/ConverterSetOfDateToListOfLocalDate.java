package com.comarch.tdd.holiday.repository;

import com.comarch.tdd.holiday.exception.ServiceInvocationException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class ConverterSetOfDateToListOfLocalDate {

    private FetchHolidaysRepository fetchHolidaysRepository;

    public void setFetchHolidaysRepository(FetchHolidaysRepository fetchHolidaysRepository) {
        this.fetchHolidaysRepository = fetchHolidaysRepository;
    }

    public List<LocalDate> getListOfLocalDateFromConvertedSetOfDate() {
        List<LocalDate> listOfLocalDateFromSetOfDate = new ArrayList<>();
        Set<Date> setOfDateFromFetchHolidaysRepository = getSetOfDateFromFetchHolidaysRepository();

        if (setOfDateFromFetchHolidaysRepository == null) {
            return listOfLocalDateFromSetOfDate;
        }

        for (Date singleDateFromFetchHolidaysRepository : setOfDateFromFetchHolidaysRepository) {
            listOfLocalDateFromSetOfDate.add(convertDateToLocalDate(singleDateFromFetchHolidaysRepository));
        }

        Collections.sort(listOfLocalDateFromSetOfDate);

        return listOfLocalDateFromSetOfDate;
    }

    private LocalDate convertDateToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private Set<Date> getSetOfDateFromFetchHolidaysRepository() {
        try {
            return fetchHolidaysRepository.findAll();
        } catch (ServiceInvocationException exception) {
            return Collections.emptySet();
        }
    }
}
