package com.xhj.bms.util;

/**
 *  The contents of this file are subject to the Mozilla Public
 *  License Version 1.1 (the "License"); you may not use this file
 *  except in compliance with the License. You may obtain a copy of
 *  the License at http://www.mozilla.org/MPL/
 *
 *  Software distributed under the License is distributed on an "AS
 *  IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
 *  implied. See the License for the specific language governing
 *  rights and limitations under the License.
 *
 *  The Original Code is pow2toolkit library.
 *
 *  The Initial Owner of the Original Code is
 *  Power Of Two S.R.L. (www.pow2.com)
 *
 *  Portions created by Power Of Two S.R.L. are
 *  Copyright (C) Power Of Two S.R.L.
 *  All Rights Reserved.
 *
 * Contributor(s):
 */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 *  Date utility class.
 *
 *  @author Luca Fossato
 */
public class DateUtil {
    /** Log4j category. */
    private static Log cat = LogFactory.getLog(DateUtil.class);
    private static SimpleDateFormat longDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    public static Date getDate(String sDate, String sfmt) {
        SimpleDateFormat fmt = new SimpleDateFormat(sfmt);
        ParsePosition pos = new ParsePosition(0);
        return fmt.parse(sDate, pos);
    }

	public static Date addYears(Date startDate, int years) {
		GregorianCalendar gCal = new GregorianCalendar();
		gCal.setTime(startDate);
		gCal.add(Calendar.YEAR, years);

		return gCal.getTime();
	}
	
    /**
     *  Add the input number of days to the start Date object.
     *
     * @param startDate  the start Date object
     * @param days       the number of days to add to the startDate object
     * @return the Date object representing the resulting date
     */
    public static Date addDays(Date startDate, int days) {
        GregorianCalendar gCal = new GregorianCalendar();
        gCal.setTime(startDate);
        gCal.add(Calendar.DATE, days);

        return gCal.getTime();
    }

    public static Date addWeeks(Date startDate, int weeks) {
        GregorianCalendar gCal = new GregorianCalendar();
        gCal.setTime(startDate);
        gCal.add(Calendar.WEEK_OF_YEAR, weeks);

        return gCal.getTime();
    }

   public static Date addMonths(Date startDate, int months) {
        GregorianCalendar gCal = new GregorianCalendar();
        gCal.setTime(startDate);
        gCal.add(Calendar.MONTH, months);

        return gCal.getTime();
    }

       /**
     *  Add the input number of days to the start Date object.
     *
     * @param startDate  the start Date object
     * @param hours       the number of days to add to the startDate object
     * @return the Date object representing the resulting date
     */
    public static Date addHours(Date startDate, int hours) {
        GregorianCalendar gCal = new GregorianCalendar();
        gCal.setTime(startDate);
        gCal.add(Calendar.HOUR, hours);

        return gCal.getTime();
    }

    /**
     *  Check if the <code>d</code> input date is between <code>d1</code> and
     *  <code>d2</code>.
     *
     * @param  d   the date to check
     * @param  d1  the lower boundary date
     * @param  d2  the upper boundary date
     * @return     true if d1 <= d <= d2, false otherwise
     */
    public static boolean isDateBetween(Date d, Date d1, Date d2) {
        return ((d1.before(d) || d1.equals(d)) &&
                (d.before(d2) || d.equals(d2)));
    }
    
    public static String formatDate(Date theDate){
    	if(theDate==null)
    		return "";
    	else 
    		return longDateTimeFormat.format(theDate);
    }
    
    public static String formatDate(Date theDate,String sFormat) {
        Locale locale = Locale.CHINA;
        String dateString = "";
        try {
            Calendar cal = Calendar.getInstance(locale);
            cal.setFirstDayOfWeek(Calendar.TUESDAY);
            cal.setTime((Date) theDate);

            //DateFormat dateFormatter = DateFormat.getDateTimeInstance(DateFormat.LONG,DateFormat.MEDIUM,locale);
            SimpleDateFormat dateFormatter = new SimpleDateFormat(sFormat, locale);
            dateString = dateFormatter.format(cal.getTime());
            //System.out.println(dateString);
            //System.out.println(cal.get(Calendar.YEAR));
            //System.out.println(cal.get(cal.DAY_OF_WEEK));
        } catch (Exception e) {
            System.out.println("test result:" + e.getMessage());
        }
        return dateString;
    }

    public static int getDateDiff(Date date1,Date date2,int sign) {

         long base = 1;
         switch(sign){
             case Calendar.DATE:
                base *=1000*60*60*24;
                break;
             case Calendar.HOUR:
                base *=1000*60*60;
                break;
             case Calendar.MINUTE:
                base *=1000*60;
                break;
             case Calendar.SECOND:
                 base *=1000;
                break;
             default:
                break;
         }
         return (int)((date1.getTime()-date2.getTime())/base);
     }

    public static String getWeekDayName(Calendar date){
        int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
        dayOfWeek--;
        String weekDayName = "";
        switch(dayOfWeek){
        	case 0:
        		weekDayName = "星期日";
        		break;
            case 1:
                weekDayName = "星期一";
                break;
            case 2:
                weekDayName = "星期二";
                break;
            case 3:
                weekDayName = "星期三";
                break;
            case 4:
                weekDayName = "星期四";
                break;
            case 5:
                weekDayName = "星期五";
                break;
            case 6:
                weekDayName = "星期六";
                break;
        }
        return weekDayName;
    }
    
    //需要另外写一个结束日期获取的方法（在原有日期上加一天）
    public static Date strToEndDate(String szDate)
    {
            try
            {
                         szDate = addDay(szDate, 1);
                         SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                         Date date = fmt.parse(szDate);
                         System.out.println(szDate + " ==> " + date);//  2011-12-24 ==> Fri Dec 23 00:00:00 GMT 2011  (打印结果)
                        return date;
            }catch (Exception e){
                 e.printStackTrace();
            }
                return null;
    }
    
    //实现日期加一天的方法
    public static String addDay(String s, int n) {   
         try {   
                  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
   
                  Calendar cd = Calendar.getInstance();   
                  cd.setTime(sdf.parse(s));   
                  cd.add(Calendar.DATE, n);//增加一天   
                  //cd.add(Calendar.MONTH, n);//增加一个月   
   
                  return sdf.format(cd.getTime());   
        
              } catch (Exception e) {   
                  return null;   
              }   
      } 

    public static boolean jisuan(String date1, String date2,int hour) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
        Date start = sdf.parse(date1);
        Date end = sdf.parse(date2);
        long cha = end.getTime() - start.getTime(); 
        double result = cha * 1.0 / (1000 * 60 * 60); 
        if(result<=hour){
             return true; 
        }else{ 
             return false; 
        } 
    } 
    
    
    public static Date strToDate(String szDate)
    {
            try
            {
                         SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                         Date date = fmt.parse(szDate);
                        return date;
            }catch (Exception e){
                 e.printStackTrace();
            }
                return null;
    }
    
    public static int daysOfTwo(Date fDate, Date oDate) {

        Calendar aCalendar = Calendar.getInstance();

        aCalendar.setTime(fDate);

        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);

        aCalendar.setTime(oDate);

        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);

        return day2 - day1;

     }

    /**
     * 获取某日期往前多少天的日期
     * @CreateTime 2017-4-24
     * @Author Jason
     * @param nowDate
     * @param beforeNum
     * @return
     */
    public static Date getBeforeDate(Date nowDate, Integer beforeNum){
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(nowDate);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -beforeNum);  //设置为前beforeNum天
        return calendar.getTime();   //得到前beforeNum天的时间
    }

    /**
     * 获取某日期往前多少小时的时间
     * @CreateTime 2017-4-24
     * @Author Jason
     * @param nowDate
     * @param beforeNum
     * @return
     */
    public static Date getBeforeHour(Date nowDate, Integer beforeNum){
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(nowDate);//把当前时间赋给日历
        calendar.add(Calendar.HOUR_OF_DAY, -beforeNum);  //设置为前beforeNum小时
        return calendar.getTime();   //得到前beforeNum小时的时间
    }
}
