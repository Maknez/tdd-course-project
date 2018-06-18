package com.comarch.tdd.holiday;

import com.comarch.tdd.holiday.exception.ServiceInvocationException;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class FetchHolidaysRepository {


    public Set<Date> findAll() {
        int nextInt = ThreadLocalRandom.current().nextInt(0, 10);
        if (nextInt == 9) {
            throw new ServiceInvocationException("Can't connect to database");
        }

        try {
            Thread.sleep(nextInt);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Set<Date> result = new HashSet<>();
        result.add(new Date());

        return result;
    }

}