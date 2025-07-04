package com.sushikhacapitals.common.idgen;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class UniqueIdGenerator implements IdGen {
    private final Random random = new SecureRandom();
    private static AtomicInteger counter = new AtomicInteger();

    @Override
    public String nextId() {
        long timestamp = System.currentTimeMillis();

        //adding a random number to generate a non predictive number
        Integer randomInt = random.nextInt(999);

        StringBuffer uniqueId = new StringBuffer();
        uniqueId.append(timestamp);
        uniqueId.append(randomInt);
        uniqueId.append(getNextCounter());

        return new BigInteger(uniqueId.toString()).abs().toString(Character.MAX_RADIX).toUpperCase();
    }

    @Override
    public String nextId(Long id) {
        return null;
    }

    private static synchronized int getNextCounter() {
        counter.incrementAndGet();
        // counter will be reset after generating 1 million
        if (counter.get() > 999999) {
            counter.set(0);
        }
        return counter.get();
    }

    public long getNumericId(int length) {
        Random random = new Random();
        char[] digits = new char[length];
        digits[0] = (char) (random.nextInt(9) + '1');
        for (int i = 1; i < length; i++) {
            digits[i] = (char) (random.nextInt(10) + '0');
        }
        return Long.parseLong(new String(digits));
    }

    public String getUniqueIdFromDateTime() {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
        String datetime = ft.format(dNow);
        return datetime;
    }

}