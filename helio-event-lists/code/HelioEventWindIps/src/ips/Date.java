/*
Copyright (c) 2009 Sebastian Hennebrueder, http://www.laliluna.de

Licensed under the Open Source Robin Hood License, Version 0.1 (the "License"); 
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.laliluna.de/open-source-robin-hood-license.html

Unless required by applicable law or agreed to in writing, software distributed under the License
is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied,
without even the implied warranty of  FITNESS FOR A PARTICULAR PURPOSE.
See the License for the specific language governing permissions and limitations under the License.
 */

package ips;

import java.io.Serializable;
import java.util.*;

/**
 * The central class of this API which allows to create dates, substract them, add time to a date
 * and more convient functions. There are methods to calculate working days between two dates.
 * A nice feature is the option to move a date by a fractions. 1/1 is the next day, 24/25 is a day and an hour.
 * 
 * It is mutable and not thread safe.
 */
public class Date implements Comparable<Date>, Serializable {
  private final Calendar calendar;
  private TimeZone timeZone = TimeZone.locale();

  public static final Fraction DAY = new Fraction(1, 1);
  public static final Fraction HOUR = new Fraction(1, 24);
  public static final Fraction MINUTE = new Fraction(1, 1440);
  public static final Fraction SECOND = new Fraction(1, 86400);
  static final long MILLIS_PER_DAY = 86400000;
  public static final Fraction MILLISECOND = new Fraction(1, MILLIS_PER_DAY);

  /**
   * Returns the current Date and Time, same as {@code Date.now();}
   */
  public Date() {
		 calendar = GregorianCalendar.getInstance();
  }

  public Date(TimeZone timeZone) {
		calendar = GregorianCalendar.getInstance(TimeZoneUtil.convert(timeZone));
    this.timeZone = timeZone;
  }

  public Date(int year, int month, int day) {
		calendar = GregorianCalendar.getInstance();
    calendar.set(year, month - 1, day);
    resetTime();
  }

  public Date(int year, Months month, int day) {
		calendar = GregorianCalendar.getInstance();
    calendar.set(year, month.toValue(), day);
    resetTime();
  }

  public Date(int year, int month, int day, int hour, int minute) {
		calendar = GregorianCalendar.getInstance();
    calendar.set(year, month - 1, day, hour, minute, 0);
  }

  public Date(int year, Months month, int day, int hour, int minute) {
		calendar = GregorianCalendar.getInstance();
    calendar.set(year, month.toValue() - 1, day, hour, minute, 0);
  }

  public Date(int year, int month, int day, int hour, int minute, int second) {
		calendar = GregorianCalendar.getInstance();
    calendar.set(year, month - 1, day, hour, minute, second);
  }

  public Date(int year, Months month, int day, int hour, int minute, int second) {
		calendar = GregorianCalendar.getInstance();
    calendar.set(year, month.toValue() - 1, day, hour, minute, second);
  }

  public Date(Date date) {
    calendar = (Calendar) date.calendar.clone();
    timeZone = date.timeZone;
  }

  public Date(Calendar calendar) {
    this.calendar = (Calendar) calendar.clone();
    timeZone = TimeZoneUtil.convert(calendar.getTimeZone());
  }

  public Date(java.util.Date date) {
		calendar = GregorianCalendar.getInstance(TimeZoneUtil.convert(timeZone));
    calendar.setTime(date);
  }

  /**
   * Creates a date instance for todays date and sets the time to 00:00:00 000
   * @return the created instance
   */
  public static Date today() {
    return new Date().resetTime();
  }
/**
 * Creates a date instance for todays date and the given timeZone, sets the time to 00:00:00 000
 * @return the created instance
 */
  public static Date today(TimeZone timeZone) {
    return new Date(timeZone).resetTime();
  }

  /**
   * Creates a date and time instance for now
   * @return the created instance
   */
  public static Date now() {
    return new Date();
  }

  /**
   * Creates a date and time instance for now and sets the given timeZone
   * @return the created instance
   */
  public static Date now(TimeZone timeZone) {
    return new Date(timeZone);
  }

  /**
   * Sets year, month, day to zero
   *
   * @return itself
   */
  public Date resetDate() {
    calendar.clear(Calendar.YEAR);
    calendar.clear(Calendar.MONTH);
    calendar.clear(Calendar.DATE);
    return this;
  }

  /**
   * Sets hour, minute, second and millisecond to zero
   *
   * @return itself
   */
  public Date resetTime() {
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    return this;
  }

  /**
   * Adds time expressed as fraction
   * {@code add(new Fraction(1/1))} - adds a day
   * {@code add(new Fraction(1/24))} - adds an hour
   * {@code add(new Fraction(5/1440))} - adds 5 minutes
   *
   * The maximum you can add are 4085 years
   *
   * @param f - fraction to be added
   * @return the same date, after the fraction was added
   */
  public Date add(Fraction f) {

    if (f.abs().compareTo(MINUTE) >= 0) {
      int minutes = (int) f.times(1440).asDouble();
      f = f.minus(MINUTE.times(minutes));
      calendar.add(Calendar.MINUTE, minutes);
    }

    if (f.abs().compareTo(MILLISECOND) >= 0) {
      int millis = (int) Math.round(f.times(MILLIS_PER_DAY).asDouble());
      calendar.add(Calendar.MILLISECOND, millis);
    }
    return this;
  }

  public Date addYears(int years) {
    calendar.add(Calendar.YEAR, years);
    return this;

  }

  public Date addMonths(int month) {
    calendar.add(Calendar.MONTH, month);
    return this;

  }

  public Date addDays(int day) {
    calendar.add(Calendar.DATE, day);
    return this;
  }


  public Date addHours(int hours) {
    calendar.add(Calendar.HOUR_OF_DAY, hours);
    return this;

  }

  public Date addMinutes(int minutes) {
    calendar.add(Calendar.MINUTE, minutes);
    return this;
  }

  public Date addSeconds(int seconds) {
    calendar.add(Calendar.SECOND, seconds);
    return this;
  }

  public Date addMilliSeconds(int milliSeconds) {
    calendar.add(Calendar.MILLISECOND, milliSeconds);
    return this;
  }

  public Date setYear(int year) {
    calendar.set(Calendar.YEAR, year);
    return this;
  }

  public Date setMonth(int month) {
    calendar.set(Calendar.MONTH, month - 1);
    return this;
  }

  public Date setMonth(Months month) {
    calendar.set(Calendar.MONTH, month.toValue() - 1);
    return this;
  }

  public Date setDay(int day) {
    calendar.set(Calendar.DATE, day);
    return this;
  }

  public Date setHour(int hours) {
    calendar.set(Calendar.HOUR_OF_DAY, hours);
    return this;

  }

  public Date setMinute(int minutes) {
    calendar.set(Calendar.MINUTE, minutes);
    return this;
  }

  public Date setSeconds(int seconds) {
    calendar.set(Calendar.SECOND, seconds);
    return this;
  }

  public Date setMilliSeconds(int milliSeconds) {
    calendar.set(Calendar.MILLISECOND, milliSeconds);
    return this;
  }

  public Date setTimeZone(TimeZone timeZone) {
    this.timeZone = timeZone;
    return this;
  }

  /**
   * Subtracts a time span from the date
   *
   * @param f - the time span
   * @return itself with the new time
   */
  public Date substract(Fraction f) {
    return add(f.negative());
  }

  /**
   * Returns the time span between two dates as fraction
   * 1/1 = 1 day, 1/24 = 1 hour, 26/24 = 1 day and 2 hours
   *
   * @param date - the date to be substracted
   * @return the time span
   */
  public Fraction minus(Date date) {
    return new Fraction(calendar.getTimeInMillis() - date.calendar.getTimeInMillis(), MILLIS_PER_DAY);
  }

  /**
   * Calculates the number of working days between to dates.
   * Calculation follows the following logic:
   * Working days are Mon to Fri.
   * It works in the sense I am working from 4th of Feb to 6 of Feb => 3 days
   * Working days between 4. Feb and 4. Feb is 1
   * Working days between 4. Feb and 6. Feb are 3
   * <p/>
   * It will not recognize any holidays.
   *
   * @param other - the other date
   * @return - number of working days as fraction 1/1 = 1 day, 3/1 = 3 days
   */
  public Fraction workingDaysBetween(Date other) {
    long days = 1 + Math.abs(calendar.getTimeInMillis() - other.calendar.getTimeInMillis()) / MILLIS_PER_DAY;
    int start = this.before(other) ? this.dayOfWeek() : other.dayOfWeek();
    long rest = days % 7;
    int end = (int) (start + rest - 1);
    end = end > 7 ? 7 : end;
    int weekend = 0;
    if (end > 5) {
      weekend = end - 5 - (start > 5 ? start - 5 : 0);
    }

    return new Fraction(days / 7 * 5 + days % 7 - weekend, 1);
  }

  /**
   * Calculates the number of working days between to dates.
   * Calculation follows the following logic:
   * Working days are Mon to Fri.
   * It works in the sense I am working from 4th of Feb to 6 of Feb => 3 days
   * Working days between 4. Feb and 4. Feb is 1
   * Working days between 4. Feb and 6. Feb are 3
   * <p/>
   * It will use the passed dayFilter to filter the working days. If you pass only one filter, then the
   * filter will be asked for the total days. If you pass multiple filters, all days returned from the filter are
   * collected and the total of unique days is substracted.
   *
   * You could create for example a filter to return all free days on a workingday.
   *
   * @param other - the other date
   * @return - number of working days as fraction 1/1 = 1 day, 3/1 = 3 days
   */
  public Fraction workingDaysBetween(Date other, DayFilter... dayFilter) {
    Fraction days = workingDaysBetween(other);
    if (dayFilter.length==1){
      return days.minus(dayFilter[0].totalFilteredDays(this, other));
    } else{
      Set<Date> distinct = new HashSet<Date>();
// reset time before adding to the set
      for (DayFilter filter : dayFilter) {
        List<Date> list = filter.filteredDays(this, other);
        for (Date date : list) {
          distinct.add(date.resetTime());
        }
      }
      return days.minus(distinct.size());
    }
  }


  /**
   * Translates the current Date to the passed time zone.
   *
   * @param timeZone - the target time zone
   * @return itself
   */
  public Date translate(TimeZone timeZone) {
    long millis = calendar.getTimeInMillis();
    calendar.setTimeZone(timeZone.toJavaTimezone());
    calendar.setTimeInMillis(millis);
    return this;
  }

  /**
   *
   * @return - the year
   */
  public int year() {
    return calendar.get(Calendar.YEAR);
  }

  /**
   * the month, 1 is January and 12 is December
   * @return - the month
   */
  public int month() {
    return calendar.get(Calendar.MONTH) + 1;
  }

  /**
   * The day part of the date
   * @return
   */
  public int day() {
    return calendar.get(Calendar.DATE);
  }

  /**
   * Day of week is presented by 1 for Monday, 7 for Sunday,
   * You can use the enum <i>de.laliluna.date.Days</i> to get a meaningful value
   * @return the day of the week
   */
  public int dayOfWeek() {
    int i = calendar.get(Calendar.DAY_OF_WEEK);
    return i == 1 ? 7 : i - 1;
  }

  /**
   * returns the hour from the time part. Range is between 0 and 23
   * @return the hour
   */
  public int hour() {
    return calendar.get(Calendar.HOUR_OF_DAY);
  }

  /**
   * returns the hours from the time part in a 0 to 12 range
   * @return the hour
   */
  public int hour12(){
    return calendar.get(Calendar.HOUR_OF_DAY) % 12;
  }

  /**
   * Ante Meridiem or AM
   * @return true if the time is before 12:00:00 and after 23:59:59 999
   */
  public boolean am(){
    return hour() < 12;
  }

  /**
   * Post Meridiem or PM
   * @return true if the time is between 12:00:00 and 23:59:59

   * @return
   */
  public boolean pm(){
    return hour() >= 12;
  }

  /**
   * Minute part of the time
   * @return int value in minutes
   */
  public int minute() {
    return calendar.get(Calendar.MINUTE);
  }

  /**
   * Second part of the time
   * @return int value in seconds
   */
  public int second() {
    return calendar.get(Calendar.SECOND);
  }

  /**
   * Milli seconds of the time, there is no guaranty that the time is measured precisely
   * @return int value in milli seconds
   */
  public int millisecond() {
    return calendar.get(Calendar.MILLISECOND);
  }

  /**
   * Compares date and time parts of a date to the other date
   * @param other - date to compare with
   * @return 1 if after, 0 if equal and -1 if before the other date
   */
  public int compareTo(Date other) {
    if (other == null)
      return 1;
    return calendar.compareTo(((Date) other).calendar);
  }

  /**
   * Compares date and time parts of a date to the other date
   * @param other - date to compare with
   * @return 1 if after, 0 if equal and -1 if before the other date
   */
  public int compareTo(java.util.Date other) {
    if (other == null)
      return 1;
    return ((Long) calendar.getTimeInMillis()).compareTo(((java.util.Date) other).getTime());
  }

  /**
   * Compares date and time parts of a date to the other date
   * @param other - date to compare with
   * @return 1 if after, 0 if equal and -1 if before the other date
   */
  public int compareTo(Calendar other) {
    if (other == null)
      return 1;
    return calendar.compareTo((Calendar) other);
  }

  /**
   * Compares only the date part of a date to the other dates date part. (= It ignores the time)
   * @param other - date to compare with
   * @return 1 if after, 0 if equal and -1 if before the other date
   */
  public int compareDateTo(Date other) {
    if (other == null)
      return 1;
    long millis = calendar.getTimeInMillis() / MILLIS_PER_DAY;
    long otherMillis = other.calendar.getTimeInMillis() / MILLIS_PER_DAY;
    if (millis > otherMillis)
      return 1;
    else if (millis < otherMillis)
      return -1;
    else return 0;
  }

  /**
   * Compares only the date part of a date to the other dates date part. (= It ignores the time)
   * @param other - date to compare with
   * @return 1 if after, 0 if equal and -1 if before the other date
   */
  public int compareDateTo(java.util.Date other) {
    long millis = calendar.getTimeInMillis() / MILLIS_PER_DAY;
    long otherMillis = other.getTime() / MILLIS_PER_DAY;
    if (millis > otherMillis)
      return 1;
    else if (millis < otherMillis)
      return -1;
    else return 0;
  }

  /**
   * Compares only the date part of a date to the other dates date part. (= It ignores the time)
   * @param other - date to compare with
   * @return 1 if after, 0 if equal and -1 if before the other date
   */
  public int compareDateTo(Calendar other) {
    long millis = calendar.getTimeInMillis() / MILLIS_PER_DAY;
    long otherMillis = other.getTimeInMillis() / MILLIS_PER_DAY;
    if (millis > otherMillis)
      return 1;
    else if (millis < otherMillis)
      return -1;
    else return 0;
  }

  /**
   * Compares only the time part of a date to the other dates time part.
   * @param other - date to compare with
   * @return 1 if after, 0 if equal and -1 if before the other date
   */
  public int compareTimeTo(Date other) {
    if (other == null)
      return 1;
    long millis = calendar.getTimeInMillis() % MILLIS_PER_DAY;
    long otherMillis = other.calendar.getTimeInMillis() % MILLIS_PER_DAY;
    if (millis > otherMillis)
      return 1;
    else if (millis < otherMillis)
      return -1;
    else return 0;
  }

  /**
   * Compares only the time part of a date to the other dates time part.
   * @param other - date to compare with
   * @return 1 if after, 0 if equal and -1 if before the other date
   */
  public int compareTimeTo(java.util.Date other) {
    long millis = calendar.getTimeInMillis() % MILLIS_PER_DAY;
    long otherMillis = other.getTime() % MILLIS_PER_DAY;
    if (millis > otherMillis)
      return 1;
    else if (millis < otherMillis)
      return -1;
    else return 0;
  }

  /**
   * Compares only the time part of a date to the other dates time part.
   * @param other - date to compare with
   * @return 1 if after, 0 if equal and -1 if before the other date
   */
  public int compareTimeTo(Calendar other) {
    long millis = calendar.getTimeInMillis() % MILLIS_PER_DAY;
    long otherMillis = other.getTimeInMillis() % MILLIS_PER_DAY;
    if (millis > otherMillis)
      return 1;
    else if (millis < otherMillis)
      return -1;
    else return 0;
  }

  public boolean before(Date other) {
    return (compareTo(other) == -1);
  }

  public boolean beforeDate(Date other) {
    return compareDateTo(other) == -1;
  }

  public boolean beforeTime(Date other) {
    return compareTimeTo(other) == -1;
  }

  public boolean after(Date other) {
    return (compareTo(other) == 1);
  }

  public boolean afterDate(Date other) {
    return compareDateTo(other) == 1;
  }

  public boolean afterTime(Date other) {
    return compareTimeTo(other) == 1;
  }

  public boolean equalDateTime(Date other) {
    return compareTo(other) == 0;
  }

  public boolean equalDate(Date other) {
    return compareDateTo(other) == 0;
  }

  public boolean equalTime(Date other) {
    return compareTimeTo(other) == 0;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Date date = (Date) o;

    if (!calendar.equals(date.calendar)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return calendar.hashCode();
  }

  /**
   * Converts the date to java.util.Calendar
   *
   * @return - the date as <i>Calendar</i>
   */
  public Calendar toCalendar() {
    return (Calendar) calendar.clone();
  }

  /**
   * Converts the date to java.util.Date
   *
   * @return - the date as <i>java.util.Date</i>
   */
  public java.util.Date toJavaDate() {
    return calendar.getTime();
  }

  /**
   * Returns milli seconds since 1.1.1970 0:00:00 UTC
   *
   * @return milli seconds
   */
  public long timeInMillis() {
    return calendar.getTimeInMillis();
  }

  /**
   * Prints a short English name of the month.
   * The values are taken from <i>Months.shortNames</i>
   *
   * @return - the name
   */
  public String monthShort() {
    return Months.valueOf(month()).toNameShort();
  }

  /**
   * Prints an English name of the month.
   * The values are taken from <i>Months.names</i>
   *
   * @return - the name
   */
  public String monthName() {
    return Months.valueOf(month()).toName();
  }

  /**
   * Prints a short English name of the day.
   * The values are taken from <i>Days.shortNames</i>
   *
   * @return - the name
   */
  public String dayShort() {
    return Days.valueOf(dayOfWeek()).toNameShort();
  }

  /**
   * Prints an English name of the month.
   * The values are taken from <i>Days.names</i>
   *
   * @return - the name
   */
  public String dayName() {
    return Days.valueOf(day()).toName();
  }

	@Override
  public String toString() {
    return String.format("Date %s, %s %d %d %02d:%02d:%02d %03dms %s", dayShort(), monthShort(), day(), year(), hour(),
            minute(), second(), millisecond(), timeZone);
  }

}
