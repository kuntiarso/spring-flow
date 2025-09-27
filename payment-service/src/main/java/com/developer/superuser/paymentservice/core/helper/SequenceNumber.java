package com.developer.superuser.paymentservice.core.helper;

import com.developer.superuser.shared.helper.Generator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class SequenceNumber implements Generator<Void, String> {
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public String generate(Void unused) {
        String date = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        String key = "sequence_number" + date;
        Long sequence = stringRedisTemplate.opsForValue().increment(key);
        if (sequence != null && sequence == 1L) stringRedisTemplate.expire(key, 1, TimeUnit.DAYS);
        String padded = String.format("%0" + 6 + "d", sequence);
        return date + padded;
    }
}