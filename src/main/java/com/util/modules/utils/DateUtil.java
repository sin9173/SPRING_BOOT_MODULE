package com.util.modules.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    //현재 날짜를 반환
    public static String getNowDate(String pattern) {
        return new SimpleDateFormat(pattern).format(new Date());
    }


    //현재 날짜에서 일수를 변경해 반환
    public static String dateCalc(String input_date, int num, String pattern) throws ParseException {
        //DateFormat(Parser)
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        //DateFormat(Formater)
        SimpleDateFormat sf = new SimpleDateFormat(pattern);

        //String -> Date
        Date date = dateFormat.parse(input_date);

        //Calendar Object
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        //일수를 더함
        cal.add(Calendar.DATE, num);

        //FORMAT
        return sf.format(cal.getTime());
    }
}
