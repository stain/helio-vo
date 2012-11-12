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
 * An immutable class representing fractions as pairs of longs.
 * Fractions are always maintained in reduced form.
 */
public class Fraction implements Cloneable, Comparable, java.io.Serializable {
  protected final long numerator;
  protected final long denominator;

	/**
   * Return the numerator *
   */
  public final long numerator() {
    return numerator;
  }

  /**
   * Return the denominator *
   */
  public final long denominator() {
    return denominator;
  }

  /**
   * Create a Fraction equal in value to num / den *
   */
  public Fraction(long num, long den) {
    // normalize while constructing
    boolean numNonnegative = (num >= 0);
    boolean denNonnegative = (den >= 0);
    long a = numNonnegative ? num : -num;
    long b = denNonnegative ? den : -den;
    long g = gcd(a, b);
    numerator = (numNonnegative == denNonnegative) ? (a / g) : (-a / g);
    denominator = b / g;
  }

  /**
   * Create a fraction with the same value as Fraction f *
   */
  public Fraction(Fraction f) {
    numerator = f.numerator();
    denominator = f.denominator();
  }

  public String toString() {
    if (denominator() == 1)
      return "" + numerator();
    else
      return numerator() + "/" + denominator();
  }

  public Object clone() {
    return new Fraction(this);
  }

  /**
   * Return the value of the Fraction as a double *
   */
  public double asDouble() {
    return ((double) (numerator())) / ((double) (denominator()));
  }

  /**
   * Compute the nonnegative greatest common divisor of a and b.
   * (This is needed for normalizing Fractions, but can be
   * useful on its own.)
   */
  public static long gcd(long a, long b) {
    long x;
    long y;

    if (a < 0) a = -a;
    if (b < 0) b = -b;

    if (a >= b) {
      x = a;
      y = b;
    } else {
      x = b;
      y = a;
    }

    while (y != 0) {
      long t = x % y;
      x = y;
      y = t;
    }
    return x;
  }

  /**
   * return a Fraction representing the negated value of this Fraction *
   */
  public Fraction negative() {
    long an = numerator();
    long ad = denominator();
    return new Fraction(-an, ad);
  }

  /**
   * return a Fraction representing 1 / this Fraction *
   */
  public Fraction inverse() {
    long an = numerator();
    long ad = denominator();
    return new Fraction(ad, an);
  }


  /**
   * return a Fraction representing this Fraction plus b *
   */
  public Fraction plus(Fraction b) {
    long an = numerator();
    long ad = denominator();
    long bn = b.numerator();
    long bd = b.denominator();
    return new Fraction(an * bd + bn * ad, ad * bd);
  }

  /**
   * return a Fraction representing this Fraction plus n *
   */
  public Fraction plus(long n) {
    long an = numerator();
    long ad = denominator();
    long bn = n;
    long bd = 1;
    return new Fraction(an * bd + bn * ad, ad * bd);
  }

  /**
   * Creates a fraction from the passed num and den value and adds it.
   *
   * @param num - numinator of a fraction
   * @param den - denominator of a fraction
   * @return total of this and the passed fraction
   */
  public Fraction plus(long num, long den) {
    return plus(new Fraction(num, den));
  }

  /**
   * return a Fraction representing this Fraction minus b *
   */
  public Fraction minus(Fraction b) {
    long an = numerator();
    long ad = denominator();
    long bn = b.numerator();
    long bd = b.denominator();
    return new Fraction(an * bd - bn * ad, ad * bd);
  }

  /**
   * return a Fraction representing this Fraction minus n *
   */
  public Fraction minus(long n) {
    long an = numerator();
    long ad = denominator();
    long bn = n;
    long bd = 1;
    return new Fraction(an * bd - bn * ad, ad * bd);
  }

  /**
   * Creates a fraction from the passed num and den value and subtracts it.
   *
   * @param num - numinator of a fraction
   * @param den - denominator of a fraction
   * @return difference of this and the passed fraction
   */
  public Fraction minus(long num, long den) {
    return minus(new Fraction(num, den));
  }

  /**
   * return a Fraction representing this Fraction times b *
   */
  public Fraction times(Fraction b) {
    long an = numerator();
    long ad = denominator();
    long bn = b.numerator();
    long bd = b.denominator();
    return new Fraction(an * bn, ad * bd);
  }

  /**
   * return a Fraction representing this Fraction times n *
   */
  public Fraction times(long n) {
    long an = numerator();
    long ad = denominator();
    long bn = n;
    long bd = 1;
    return new Fraction(an * bn, ad * bd);
  }

  /**
   * Creates a fraction from the passed num and den value and multiplies it with the current value.
   *
   * @param num - numinator of a fraction
   * @param den - denominator of a fraction
   * @return this multiplied with the passed fraction
   */
  public Fraction times(long num, long den) {
    return times(new Fraction(num, den));
  }

  /**
   * return a Fraction representing this Fraction divided by b *
   */
  public Fraction dividedBy(Fraction b) {
    long an = numerator();
    long ad = denominator();
    long bn = b.numerator();
    long bd = b.denominator();
    return new Fraction(an * bd, ad * bn);
  }

  /**
   * return a Fraction representing this Fraction divided by n *
   */
  public Fraction dividedBy(long n) {
    long an = numerator();
    long ad = denominator();
    long bn = n;
    long bd = 1;
    return new Fraction(an * bd, ad * bn);
  }

  /**
   * Creates a fraction from the passed num and den value and divides the current value by this fraction.
   *
   * @param num - numinator of a fraction
   * @param den - denominator of a fraction
   * @return this divided by the passed fraction
   */
  public Fraction dividedBy(long num, long den) {
    return dividedBy(new Fraction(num, den));
  }

  /**
   * return a number less, equal, or greater than zero
   * reflecting whether this Fraction is less, equal or greater than
   * the value of Fraction other.
   */
  public int compareTo(Object other) {
    Fraction b = (Fraction) (other);
    long an = numerator();
    long ad = denominator();
    long bn = b.numerator();
    long bd = b.denominator();
    long l = an * bd;
    long r = bn * ad;
    return (l < r) ? -1 : ((l == r) ? 0 : 1);
  }

  /**
   * return a number less, equal, or greater than zero
   * reflecting whether this Fraction is less, equal or greater than n.
   */

  public int compareTo(long n) {
    long an = numerator();
    long ad = denominator();
    long bn = n;
    long bd = 1;
    long l = an * bd;
    long r = bn * ad;
    return (l < r) ? -1 : ((l == r) ? 0 : 1);
  }

	public boolean equals(long n) {
    return compareTo(n) == 0;
  }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Fraction fraction = (Fraction) o;

		if (denominator != fraction.denominator) return false;
		if (numerator != fraction.numerator) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = (int) (numerator ^ (numerator >>> 32));
		result = 31 * result + (int) (denominator ^ (denominator >>> 32));
		return result;
	}

	public Fraction abs() {
    return new Fraction(Math.abs(numerator()), denominator());
  }

}
