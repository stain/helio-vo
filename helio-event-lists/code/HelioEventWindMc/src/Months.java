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

/**
 * Enum representing the months of the year. There are converting methods from
 * int to the enum and back.
 * Finally you can get the english abbreviation or full name for the month.
 */
public enum Months {
  JAN(1), FEB(2), MAR(3), APR(4), MAY(5), JUN(6), JUL(7), AUG(8), SEP(9), OCT(10), NOV(11), DEC(12);

  private final int value;

  public static final String shortNames[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
  public static final String names[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

  Months(int value) {
    this.value = value;
  }

	/**
	 * Returns a english abbreviation for the month name of this instance
	 *
	 * @return an english abbreviation for the month
	 */
  public String toNameShort() {
    return shortNames[this.ordinal()];
  }

	/**
	 * Returns an english month name of this instance
	 *
	 * @return an english month name
	 */
  public String toName() {
    return names[this.ordinal()];
  }

	/**
	 * Returns the month enum corresponding to the value of {@code monthValue}
	 * 1 corresponds to JAN, 2 to FEB, ...
	 * @param monthValue - value representing a month
	 * @return - the month enum corresponding to the param value
	 */
  public static Months valueOf(int monthValue) {
    Months result = null;
    switch (monthValue){
      case 1: result=JAN;
        break;
      case 2: result=FEB;
        break;
      case 3: result=MAR;
        break;
      case 4: result=APR;
        break;
      case 5: result=MAY;
        break;
      case 6: result=JUN;
        break;
      case 7: result=JUL;
        break;
      case 8: result=AUG;
        break;
      case 9: result=SEP;
        break;
      case 10: result=OCT;
        break;
      case 11: result=NOV;
        break;
      case 12: result=DEC;
        break;
    }
    return result;
  }

	/**
	 * Returns the int value corresponding to the month
	 * 1 corresponds to JAN, 2 to FEB, ...
	 *
	 * @return int value of the month
	 */
  public int toValue() {
    return value;
  }
}