package com.comarch.tdd.holiday.repository;

import com.comarch.tdd.holiday.exception.ServiceInvocationException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class ConverterSetOfDateToListOfLocalDateTest {

    private ConverterSetOfDateToListOfLocalDate converterSetOfDateToListOfLocalDate;
    private FetchHolidaysRepository fetchHolidaysRepository;

    @Before
    public void setUp() {
        fetchHolidaysRepository = Mockito.mock(FetchHolidaysRepository.class);
        converterSetOfDateToListOfLocalDate = new ConverterSetOfDateToListOfLocalDate();
        converterSetOfDateToListOfLocalDate.setFetchHolidaysRepository(fetchHolidaysRepository);
    }

    @Test
    public void shouldReturnListOfLocalDate() {
        //given
        Set<Date> result = new HashSet<>();
        result.add(getDateFromString("2018-07-02"));
        result.add(getDateFromString("2018-07-10"));
        result.add(getDateFromString("2018-07-14"));
        // when
        when(fetchHolidaysRepository.findAll()).thenReturn(result);
        List<LocalDate> listOfFreeDays = converterSetOfDateToListOfLocalDate.getListOfLocalDateFromConvertedSetOfDate();
        // then
        assertTrue(listOfFreeDays.contains(LocalDate.of(2018, 7, 14)));
        assertTrue(listOfFreeDays.contains(LocalDate.of(2018, 7, 10)));
        assertTrue(listOfFreeDays.contains(LocalDate.of(2018, 7, 2)));
        assertEquals(3, listOfFreeDays.size());
    }

    @Test
    public void shouldReturnEmptyListOfLocalDateIfFindAllFunctionReturnNull() {
        //when
        when(fetchHolidaysRepository.findAll()).thenReturn(null);
        List<LocalDate> listOfFreeDays = converterSetOfDateToListOfLocalDate.getListOfLocalDateFromConvertedSetOfDate();
        //then
        assertNotNull(listOfFreeDays);
    }

    @Test
    public void shouldReturnEmptyListOfLocalDateIfServiceInvocationExceptionIsThrown() {
        //when
        when(fetchHolidaysRepository.findAll()).thenThrow(ServiceInvocationException.class);
        List<LocalDate> listOfFreeDays = converterSetOfDateToListOfLocalDate.getListOfLocalDateFromConvertedSetOfDate();
        //then
        assertEquals(Collections.emptyList(), listOfFreeDays);
    }

    @Test
    public void shouldReturnDateSortedInAscendingOrder() {
        //given
        Set<Date> result = new HashSet<>();
        result.add(getDateFromString("2018-07-14"));
        result.add(getDateFromString("2018-07-02"));
        result.add(getDateFromString("2018-07-10"));
        result.add(getDateFromString("2018-08-14"));
        result.add(getDateFromString("2018-10-02"));
        result.add(getDateFromString("2018-09-10"));
        result.add(getDateFromString("2018-07-25"));
        result.add(getDateFromString("2018-07-08"));
        result.add(getDateFromString("2018-07-19"));
        // when
        when(fetchHolidaysRepository.findAll()).thenReturn(result);
        List<LocalDate> listOfFreeDays = converterSetOfDateToListOfLocalDate.getListOfLocalDateFromConvertedSetOfDate();
        // then
        assertEquals(LocalDate.of(2018, 7, 2), listOfFreeDays.get(0));
        assertEquals(LocalDate.of(2018, 7, 8), listOfFreeDays.get(1));
        assertEquals(LocalDate.of(2018, 7, 10), listOfFreeDays.get(2));
        assertEquals(LocalDate.of(2018, 7, 14), listOfFreeDays.get(3));
        assertEquals(LocalDate.of(2018, 7, 19), listOfFreeDays.get(4));
        assertEquals(LocalDate.of(2018, 7, 25), listOfFreeDays.get(5));
        assertEquals(LocalDate.of(2018, 8, 14), listOfFreeDays.get(6));
        assertEquals(LocalDate.of(2018, 9, 10), listOfFreeDays.get(7));
        assertEquals(LocalDate.of(2018, 10, 2), listOfFreeDays.get(8));
        assertEquals(9, listOfFreeDays.size());
    }

    private Date getDateFromString(String dateSting) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateSting);
        } catch (ParseException e) {
            return null;
        }
    }

}
