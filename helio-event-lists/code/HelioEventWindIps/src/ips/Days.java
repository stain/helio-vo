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
//package de.laliluna.date;

/**
 * Enum containing the days of a week. It provides methods to convert an int to a enum and back.
 * In addition you can get english abbreviations and the full name of the day name. 
 */
public enum Days {
  MON, TUE, WED, THU, FRI, SAT, SUN;

  public static final String shortNames[] = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
  public static final String names[] = {"Monday", "Tueday", "Wednesday", "Thuesday", "Friday", "Saturday", "Sunday"};

  public String toNameShort() {
    return shortNames[this.ordinal()];
  }

  public String toName() {
    return names[this.ordinal()];
  }

  /**
   * Returns the day of the give int value.
   * 1 corresponds to Monday, 7 to Sunday
   * @param day - int value representing the day
   * @return - the day
   */
  public static Days valueOf(int day) {
    Days days = null;
    switch (day) {
      case 1:
        days = MON;
        break;
      case 2:
        days = TUE;
        break;
      case 3:
        days = WED;
        break;
      case 4:
        days = THU;
        break;
      case 5:
        days = FRI;
        break;
      case 6:
        days = SAT;
        break;
      case 7:
        days = SUN;
        break;
    }

    return days;
  }

}
