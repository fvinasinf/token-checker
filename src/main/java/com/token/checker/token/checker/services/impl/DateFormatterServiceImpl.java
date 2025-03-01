package com.token.checker.token.checker.services.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.stereotype.Component;

import com.token.checker.token.checker.services.DateFormatterService;

@Component
public class DateFormatterServiceImpl  implements DateFormatterService {

    @Override
    public String nowFormmattedTimestamp() {
        SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        outputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date formatted = getNowDate(inputFormat);
        return outputFormat.format(formatted);
    }

    private Date getNowDate(SimpleDateFormat inputFormat) {
        try {
            return inputFormat.parse(new Date().toString());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

}
