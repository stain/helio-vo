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


//package de.laliluna.date;

import java.util.List;

/**
 * DayFilter is an interface to be implemented by classes that can filter holidays from
 * a time range. A day filter can be used in the method {@link Date#workingDaysBetween(Date, DayFilter[])} when working
 * days are calculated.
 */
public interface DayFilter {
  /**
   * calculates the total of days to be filtered from a time span
   * filter should be able to work correctly if the {@code from} date is later then the
	 * {@code start} date
   * @param from - the start date
   * @param to - the end date
   * @return the total of days to be filtered
   */
  public int totalFilteredDays(Date from, Date to);

  /**
   * collects the days to be filtered from a time span
   * filter should be able to work correctly if the {@code from} date is later then the
	 * {@code start} date
   * @param from - the start date
   * @param to - the end date
   * @return Returns a list of days being filtered from a time span
   */
  public List<Date> filteredDays(Date from, Date to);
}
