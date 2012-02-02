import java.util.Arrays;
import java.util.Random;


public class BigTest
{

    final static long LONG_MASK = 0xffffffffL;
    
	/*
	 * Copyright (c) 1996, 2007, Oracle and/or its affiliates. All rights reserved.
	 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 */
	static class MutableBigInteger {
	    /**
	     * Holds the magnitude of this MutableBigInteger in big endian order.
	     * The magnitude may start at an offset into the value array, and it may
	     * end before the length of the value array.
	     */
	    int[] value;

	    /**
	     * The number of ints of the value array that are currently used
	     * to hold the magnitude of this MutableBigInteger. The magnitude starts
	     * at an offset and offset + intLen may be less than value.length.
	     */
	    int intLen;

	    /**
	     * The offset into the value array where the magnitude of this
	     * MutableBigInteger begins.
	     */
	    int offset = 0;

	    // Constants
	    /**
	     * MutableBigInteger with one element value array with the value 1. Used by
	     * BigDecimal divideAndRound to increment the quotient. Use this constant
	     * only when the method is not going to modify this object.
	     */
	    static final MutableBigInteger ONE = new MutableBigInteger(1);

	    // Constructors

	    /**
	     * The default constructor. An empty MutableBigInteger is created with
	     * a one word capacity.
	     */
	    MutableBigInteger() {
	        value = new int[1];
	        intLen = 0;
	    }

	    /**
	     * Construct a new MutableBigInteger with a magnitude specified by
	     * the int val.
	     */
	    MutableBigInteger(int val) {
	        value = new int[1];
	        intLen = 1;
	        value[0] = val;
	    }

	    /**
	     * Construct a new MutableBigInteger with the specified value array
	     * up to the length of the array supplied.
	     */
	    MutableBigInteger(int[] val) {
	        value = val;
	        intLen = val.length;
	    }

	    /**
	     * Construct a new MutableBigInteger with a magnitude equal to the
	     * specified BigInteger.
	     */
	    MutableBigInteger(BigInteger b) {
	        intLen = b.mag.length;
	        value = Arrays.copyOf(b.mag, intLen);
	    }

	    /**
	     * Construct a new MutableBigInteger with a magnitude equal to the
	     * specified MutableBigInteger.
	     */
	    MutableBigInteger(MutableBigInteger val) {
	        intLen = val.intLen;
	        value = Arrays.copyOfRange(val.value, val.offset, val.offset + intLen);
	    }

	    /**
	     * Internal helper method to return the magnitude array. The caller is not
	     * supposed to modify the returned array.
	     */
	    private int[] getMagnitudeArray() {
	        if (offset > 0 || value.length != intLen)
	            return Arrays.copyOfRange(value, offset, offset + intLen);
	        return value;
	    }

	    /**
	     * Convert this MutableBigInteger to a long value. The caller has to make
	     * sure this MutableBigInteger can be fit into long.
	     */
	    private long toLong() {
	        assert (intLen <= 2) : "this MutableBigInteger exceeds the range of long";
	        if (intLen == 0)
	            return 0;
	        long d = value[offset] & LONG_MASK;
	        return (intLen == 2) ? d << 32 | (value[offset + 1] & LONG_MASK) : d;
	    }

	    /**
	     * Convert this MutableBigInteger to a BigInteger object.
	     */
	    BigInteger toBigInteger(int sign) {
	        if (intLen == 0 || sign == 0)
	            return BigInteger.ZERO;
	        return new BigInteger(getMagnitudeArray(), sign);
	    }



	    /**
	     * Clear out a MutableBigInteger for reuse.
	     */
	    void clear() {
	        offset = intLen = 0;
	        for (int index=0, n=value.length; index < n; index++)
	            value[index] = 0;
	    }

	    /**
	     * Set a MutableBigInteger to zero, removing its offset.
	     */
	    void reset() {
	        offset = intLen = 0;
	    }

	    /**
	     * Compare the magnitude of two MutableBigIntegers. Returns -1, 0 or 1
	     * as this MutableBigInteger is numerically less than, equal to, or
	     * greater than <tt>b</tt>.
	     */
	    final int compare(MutableBigInteger b) {
	        int blen = b.intLen;
	        if (intLen < blen)
	            return -1;
	        if (intLen > blen)
	           return 1;

	        // Add Integer.MIN_VALUE to make the comparison act as unsigned integer
	        // comparison.
	        int[] bval = b.value;
	        for (int i = offset, j = b.offset; i < intLen + offset; i++, j++) {
	            int b1 = value[i] + 0x80000000;
	            int b2 = bval[j]  + 0x80000000;
	            if (b1 < b2)
	                return -1;
	            if (b1 > b2)
	                return 1;
	        }
	        return 0;
	    }

	    /**
	     * Compare this against half of a MutableBigInteger object (Needed for
	     * remainder tests).
	     * Assumes no leading unnecessary zeros, which holds for results
	     * from divide().
	     */
	    final int compareHalf(MutableBigInteger b) {
	        int blen = b.intLen;
	        int len = intLen;
	        if (len <= 0)
	            return blen <=0 ? 0 : -1;
	        if (len > blen)
	            return 1;
	        if (len < blen - 1)
	            return -1;
	        int[] bval = b.value;
	        int bstart = 0;
	        int carry = 0;
	        // Only 2 cases left:len == blen or len == blen - 1
	        if (len != blen) { // len == blen - 1
	            if (bval[bstart] == 1) {
	                ++bstart;
	                carry = 0x80000000;
	            } else
	                return -1;
	        }
	        // compare values with right-shifted values of b,
	        // carrying shifted-out bits across words
	        int[] val = value;
	        for (int i = offset, j = bstart; i < len + offset;) {
	            int bv = bval[j++];
	            long hb = ((bv >>> 1) + carry) & LONG_MASK;
	            long v = val[i++] & LONG_MASK;
	            if (v != hb)
	                return v < hb ? -1 : 1;
	            carry = (bv & 1) << 31; // carray will be either 0x80000000 or 0
	        }
	        return carry == 0? 0 : -1;
	    }

	    /**
	     * Return the index of the lowest set bit in this MutableBigInteger. If the
	     * magnitude of this MutableBigInteger is zero, -1 is returned.
	     */
	    private final int getLowestSetBit() {
	        if (intLen == 0)
	            return -1;
	        int j, b;
	        for (j=intLen-1; (j>0) && (value[j+offset]==0); j--)
	            ;
	        b = value[j+offset];
	        if (b==0)
	            return -1;
	        return ((intLen-1-j)<<5) + Integer.numberOfTrailingZeros(b);
	    }

	    /**
	     * Ensure that the MutableBigInteger is in normal form, specifically
	     * making sure that there are no leading zeros, and that if the
	     * magnitude is zero, then intLen is zero.
	     */
	    final void normalize() {
	        if (intLen == 0) {
	            offset = 0;
	            return;
	        }

	        int index = offset;
	        if (value[index] != 0)
	            return;

	        int indexBound = index+intLen;
	        do {
	            index++;
	        } while(index < indexBound && value[index]==0);

	        int numZeros = index - offset;
	        intLen -= numZeros;
	        offset = (intLen==0 ?  0 : offset+numZeros);
	    }

	    /**
	     * Convert this MutableBigInteger into an int array with no leading
	     * zeros, of a length that is equal to this MutableBigInteger's intLen.
	     */
	    int[] toIntArray() {
	        int[] result = new int[intLen];
	        for(int i=0; i<intLen; i++)
	            result[i] = value[offset+i];
	        return result;
	    }

	    /**
	     * Sets the int at index+offset in this MutableBigInteger to val.
	     * This does not get inlined on all platforms so it is not used
	     * as often as originally intended.
	     */
	    void setInt(int index, int val) {
	        value[offset + index] = val;
	    }

	    /**
	     * Sets this MutableBigInteger's value array to the specified array.
	     * The intLen is set to the specified length.
	     */
	    void setValue(int[] val, int length) {
	        value = val;
	        intLen = length;
	        offset = 0;
	    }

	    /**
	     * Sets this MutableBigInteger's value array to a copy of the specified
	     * array. The intLen is set to the length of the new array.
	     */
	    void copyValue(MutableBigInteger src) {
	        int len = src.intLen;
	        if (value.length < len)
	            value = new int[len];
	        System.arraycopy(src.value, src.offset, value, 0, len);
	        intLen = len;
	        offset = 0;
	    }

	    /**
	     * Sets this MutableBigInteger's value array to a copy of the specified
	     * array. The intLen is set to the length of the specified array.
	     */
	    void copyValue(int[] val) {
	        int len = val.length;
	        if (value.length < len)
	            value = new int[len];
	        System.arraycopy(val, 0, value, 0, len);
	        intLen = len;
	        offset = 0;
	    }

	    /**
	     * Returns true iff this MutableBigInteger has a value of one.
	     */
	    boolean isOne() {
	        return (intLen == 1) && (value[offset] == 1);
	    }

	    /**
	     * Returns true iff this MutableBigInteger has a value of zero.
	     */
	    boolean isZero() {
	        return (intLen == 0);
	    }

	    /**
	     * Returns true iff this MutableBigInteger is even.
	     */
	    boolean isEven() {
	        return (intLen == 0) || ((value[offset + intLen - 1] & 1) == 0);
	    }

	    /**
	     * Returns true iff this MutableBigInteger is odd.
	     */
	    boolean isOdd() {
	        return isZero() ? false : ((value[offset + intLen - 1] & 1) == 1);
	    }

	    /**
	     * Returns true iff this MutableBigInteger is in normal form. A
	     * MutableBigInteger is in normal form if it has no leading zeros
	     * after the offset, and intLen + offset <= value.length.
	     */
	    boolean isNormal() {
	        if (intLen + offset > value.length)
	            return false;
	        if (intLen ==0)
	            return true;
	        return (value[offset] != 0);
	    }

	    /**
	     * Returns a String representation of this MutableBigInteger in radix 10.
	     */
	    public String toString() {
	        BigInteger b = toBigInteger(1);
	        return b.toString();
	    }

	    /**
	     * Right shift this MutableBigInteger n bits. The MutableBigInteger is left
	     * in normal form.
	     */
	    void rightShift(int n) {
	        if (intLen == 0)
	            return;
	        int nInts = n >>> 5;
	        int nBits = n & 0x1F;
	        this.intLen -= nInts;
	        if (nBits == 0)
	            return;
	        int bitsInHighWord = BigInteger.bitLengthForInt(value[offset]);
	        if (nBits >= bitsInHighWord) {
	            this.primitiveLeftShift(32 - nBits);
	            this.intLen--;
	        } else {
	            primitiveRightShift(nBits);
	        }
	    }

	    /**
	     * Left shift this MutableBigInteger n bits.
	     */
	    void leftShift(int n) {
	        /*
	         * If there is enough storage space in this MutableBigInteger already
	         * the available space will be used. Space to the right of the used
	         * ints in the value array is faster to utilize, so the extra space
	         * will be taken from the right if possible.
	         */
	        if (intLen == 0)
	           return;
	        int nInts = n >>> 5;
	        int nBits = n&0x1F;
	        int bitsInHighWord = BigInteger.bitLengthForInt(value[offset]);

	        // If shift can be done without moving words, do so
	        if (n <= (32-bitsInHighWord)) {
	            primitiveLeftShift(nBits);
	            return;
	        }

	        int newLen = intLen + nInts +1;
	        if (nBits <= (32-bitsInHighWord))
	            newLen--;
	        if (value.length < newLen) {
	            // The array must grow
	            int[] result = new int[newLen];
	            for (int i=0; i<intLen; i++)
	                result[i] = value[offset+i];
	            setValue(result, newLen);
	        } else if (value.length - offset >= newLen) {
	            // Use space on right
	            for(int i=0; i<newLen - intLen; i++)
	                value[offset+intLen+i] = 0;
	        } else {
	            // Must use space on left
	            for (int i=0; i<intLen; i++)
	                value[i] = value[offset+i];
	            for (int i=intLen; i<newLen; i++)
	                value[i] = 0;
	            offset = 0;
	        }
	        intLen = newLen;
	        if (nBits == 0)
	            return;
	        if (nBits <= (32-bitsInHighWord))
	            primitiveLeftShift(nBits);
	        else
	            primitiveRightShift(32 -nBits);
	    }

	    /**
	     * A primitive used for division. This method adds in one multiple of the
	     * divisor a back to the dividend result at a specified offset. It is used
	     * when qhat was estimated too large, and must be adjusted.
	     */
	    private int divadd(int[] a, int[] result, int offset) {
	        long carry = 0;

	        for (int j=a.length-1; j >= 0; j--) {
	            long sum = (a[j] & LONG_MASK) +
	                       (result[j+offset] & LONG_MASK) + carry;
	            result[j+offset] = (int)sum;
	            carry = sum >>> 32;
	        }
	        return (int)carry;
	    }

	    /**
	     * This method is used for division. It multiplies an n word input a by one
	     * word input x, and subtracts the n word product from q. This is needed
	     * when subtracting qhat*divisor from dividend.
	     */
	    private int mulsub(int[] q, int[] a, int x, int len, int offset) {
	        long xLong = x & LONG_MASK;
	        long carry = 0;
	        offset += len;

	        for (int j=len-1; j >= 0; j--) {
	            long product = (a[j] & LONG_MASK) * xLong + carry;
	            long difference = q[offset] - product;
	            q[offset--] = (int)difference;
	            carry = (product >>> 32)
	                     + (((difference & LONG_MASK) >
	                         (((~(int)product) & LONG_MASK))) ? 1:0);
	        }
	        return (int)carry;
	    }

	    /**
	     * Right shift this MutableBigInteger n bits, where n is
	     * less than 32.
	     * Assumes that intLen > 0, n > 0 for speed
	     */
	    private final void primitiveRightShift(int n) {
	        int[] val = value;
	        int n2 = 32 - n;
	        for (int i=offset+intLen-1, c=val[i]; i>offset; i--) {
	            int b = c;
	            c = val[i-1];
	            val[i] = (c << n2) | (b >>> n);
	        }
	        val[offset] >>>= n;
	    }

	    /**
	     * Left shift this MutableBigInteger n bits, where n is
	     * less than 32.
	     * Assumes that intLen > 0, n > 0 for speed
	     */
	    private final void primitiveLeftShift(int n) {
	        int[] val = value;
	        int n2 = 32 - n;
	        for (int i=offset, c=val[i], m=i+intLen-1; i<m; i++) {
	            int b = c;
	            c = val[i+1];
	            val[i] = (b << n) | (c >>> n2);
	        }
	        val[offset+intLen-1] <<= n;
	    }

	    /**
	     * Adds the contents of two MutableBigInteger objects.The result
	     * is placed within this MutableBigInteger.
	     * The contents of the addend are not changed.
	     */
	    void add(MutableBigInteger addend) {
	        int x = intLen;
	        int y = addend.intLen;
	        int resultLen = (intLen > addend.intLen ? intLen : addend.intLen);
	        int[] result = (value.length < resultLen ? new int[resultLen] : value);

	        int rstart = result.length-1;
	        long sum;
	        long carry = 0;

	        // Add common parts of both numbers
	        while(x>0 && y>0) {
	            x--; y--;
	            sum = (value[x+offset] & LONG_MASK) +
	                (addend.value[y+addend.offset] & LONG_MASK) + carry;
	            result[rstart--] = (int)sum;
	            carry = sum >>> 32;
	        }

	        // Add remainder of the longer number
	        while(x>0) {
	            x--;
	            if (carry == 0 && result == value && rstart == (x + offset))
	                return;
	            sum = (value[x+offset] & LONG_MASK) + carry;
	            result[rstart--] = (int)sum;
	            carry = sum >>> 32;
	        }
	        while(y>0) {
	            y--;
	            sum = (addend.value[y+addend.offset] & LONG_MASK) + carry;
	            result[rstart--] = (int)sum;
	            carry = sum >>> 32;
	        }

	        if (carry > 0) { // Result must grow in length
	            resultLen++;
	            if (result.length < resultLen) {
	                int temp[] = new int[resultLen];
	                // Result one word longer from carry-out; copy low-order
	                // bits into new result.
	                System.arraycopy(result, 0, temp, 1, result.length);
	                temp[0] = 1;
	                result = temp;
	            } else {
	                result[rstart--] = 1;
	            }
	        }

	        value = result;
	        intLen = resultLen;
	        offset = result.length - resultLen;
	    }


	    /**
	     * Subtracts the smaller of this and b from the larger and places the
	     * result into this MutableBigInteger.
	     */
	    int subtract(MutableBigInteger b) {
	        MutableBigInteger a = this;

	        int[] result = value;
	        int sign = a.compare(b);

	        if (sign == 0) {
	            reset();
	            return 0;
	        }
	        if (sign < 0) {
	            MutableBigInteger tmp = a;
	            a = b;
	            b = tmp;
	        }

	        int resultLen = a.intLen;
	        if (result.length < resultLen)
	            result = new int[resultLen];

	        long diff = 0;
	        int x = a.intLen;
	        int y = b.intLen;
	        int rstart = result.length - 1;

	        // Subtract common parts of both numbers
	        while (y>0) {
	            x--; y--;

	            diff = (a.value[x+a.offset] & LONG_MASK) -
	                   (b.value[y+b.offset] & LONG_MASK) - ((int)-(diff>>32));
	            result[rstart--] = (int)diff;
	        }
	        // Subtract remainder of longer number
	        while (x>0) {
	            x--;
	            diff = (a.value[x+a.offset] & LONG_MASK) - ((int)-(diff>>32));
	            result[rstart--] = (int)diff;
	        }

	        value = result;
	        intLen = resultLen;
	        offset = value.length - resultLen;
	        normalize();
	        return sign;
	    }

	    /**
	     * Subtracts the smaller of a and b from the larger and places the result
	     * into the larger. Returns 1 if the answer is in a, -1 if in b, 0 if no
	     * operation was performed.
	     */
	    private int difference(MutableBigInteger b) {
	        MutableBigInteger a = this;
	        int sign = a.compare(b);
	        if (sign ==0)
	            return 0;
	        if (sign < 0) {
	            MutableBigInteger tmp = a;
	            a = b;
	            b = tmp;
	        }

	        long diff = 0;
	        int x = a.intLen;
	        int y = b.intLen;

	        // Subtract common parts of both numbers
	        while (y>0) {
	            x--; y--;
	            diff = (a.value[a.offset+ x] & LONG_MASK) -
	                (b.value[b.offset+ y] & LONG_MASK) - ((int)-(diff>>32));
	            a.value[a.offset+x] = (int)diff;
	        }
	        // Subtract remainder of longer number
	        while (x>0) {
	            x--;
	            diff = (a.value[a.offset+ x] & LONG_MASK) - ((int)-(diff>>32));
	            a.value[a.offset+x] = (int)diff;
	        }

	        a.normalize();
	        return sign;
	    }

	    /**
	     * Multiply the contents of two MutableBigInteger objects. The result is
	     * placed into MutableBigInteger z. The contents of y are not changed.
	     */
	    void multiply(MutableBigInteger y, MutableBigInteger z) {
	        int xLen = intLen;
	        int yLen = y.intLen;
	        int newLen = xLen + yLen;

	        // Put z into an appropriate state to receive product
	        if (z.value.length < newLen)
	            z.value = new int[newLen];
	        z.offset = 0;
	        z.intLen = newLen;

	        // The first iteration is hoisted out of the loop to avoid extra add
	        long carry = 0;
	        for (int j=yLen-1, k=yLen+xLen-1; j >= 0; j--, k--) {
	                long product = (y.value[j+y.offset] & LONG_MASK) *
	                               (value[xLen-1+offset] & LONG_MASK) + carry;
	                z.value[k] = (int)product;
	                carry = product >>> 32;
	        }
	        z.value[xLen-1] = (int)carry;

	        // Perform the multiplication word by word
	        for (int i = xLen-2; i >= 0; i--) {
	            carry = 0;
	            for (int j=yLen-1, k=yLen+i; j >= 0; j--, k--) {
	                long product = (y.value[j+y.offset] & LONG_MASK) *
	                               (value[i+offset] & LONG_MASK) +
	                               (z.value[k] & LONG_MASK) + carry;
	                z.value[k] = (int)product;
	                carry = product >>> 32;
	            }
	            z.value[i] = (int)carry;
	        }

	        // Remove leading zeros from product
	        z.normalize();
	    }

	    /**
	     * Multiply the contents of this MutableBigInteger by the word y. The
	     * result is placed into z.
	     */
	    void mul(int y, MutableBigInteger z) {
	        if (y == 1) {
	            z.copyValue(this);
	            return;
	        }

	        if (y == 0) {
	            z.clear();
	            return;
	        }

	        // Perform the multiplication word by word
	        long ylong = y & LONG_MASK;
	        int[] zval = (z.value.length<intLen+1 ? new int[intLen + 1]
	                                              : z.value);
	        long carry = 0;
	        for (int i = intLen-1; i >= 0; i--) {
	            long product = ylong * (value[i+offset] & LONG_MASK) + carry;
	            zval[i+1] = (int)product;
	            carry = product >>> 32;
	        }

	        if (carry == 0) {
	            z.offset = 1;
	            z.intLen = intLen;
	        } else {
	            z.offset = 0;
	            z.intLen = intLen + 1;
	            zval[0] = (int)carry;
	        }
	        z.value = zval;
	    }

	     /**
	     * This method is used for division of an n word dividend by a one word
	     * divisor. The quotient is placed into quotient. The one word divisor is
	     * specified by divisor.
	     *
	     * @return the remainder of the division is returned.
	     *
	     */
	    int divideOneWord(int divisor, MutableBigInteger quotient) {
	        long divisorLong = divisor & LONG_MASK;

	        // Special case of one word dividend
	        if (intLen == 1) {
	            long dividendValue = value[offset] & LONG_MASK;
	            int q = (int) (dividendValue / divisorLong);
	            int r = (int) (dividendValue - q * divisorLong);
	            quotient.value[0] = q;
	            quotient.intLen = (q == 0) ? 0 : 1;
	            quotient.offset = 0;
	            return r;
	        }

	        if (quotient.value.length < intLen)
	            quotient.value = new int[intLen];
	        quotient.offset = 0;
	        quotient.intLen = intLen;

	        // Normalize the divisor
	        int shift = Integer.numberOfLeadingZeros(divisor);

	        int rem = value[offset];
	        long remLong = rem & LONG_MASK;
	        if (remLong < divisorLong) {
	            quotient.value[0] = 0;
	        } else {
	            quotient.value[0] = (int)(remLong / divisorLong);
	            rem = (int) (remLong - (quotient.value[0] * divisorLong));
	            remLong = rem & LONG_MASK;
	        }

	        int xlen = intLen;
	        int[] qWord = new int[2];
	        while (--xlen > 0) {
	            long dividendEstimate = (remLong<<32) |
	                (value[offset + intLen - xlen] & LONG_MASK);
	            if (dividendEstimate >= 0) {
	                qWord[0] = (int) (dividendEstimate / divisorLong);
	                qWord[1] = (int) (dividendEstimate - qWord[0] * divisorLong);
	            } else {
	                divWord(qWord, dividendEstimate, divisor);
	            }
	            quotient.value[intLen - xlen] = qWord[0];
	            rem = qWord[1];
	            remLong = rem & LONG_MASK;
	        }

	        quotient.normalize();
	        // Unnormalize
	        if (shift > 0)
	            return rem % divisor;
	        else
	            return rem;
	    }

	    /**
	     * Calculates the quotient of this div b and places the quotient in the
	     * provided MutableBigInteger objects and the remainder object is returned.
	     *
	     * Uses Algorithm D in Knuth section 4.3.1.
	     * Many optimizations to that algorithm have been adapted from the Colin
	     * Plumb C library.
	     * It special cases one word divisors for speed. The content of b is not
	     * changed.
	     *
	     */
	    MutableBigInteger divide(MutableBigInteger b, MutableBigInteger quotient) {
	        if (b.intLen == 0)
	            throw new ArithmeticException("BigInteger divide by zero");

	        // Dividend is zero
	        if (intLen == 0) {
	            quotient.intLen = quotient.offset;
	            return new MutableBigInteger();
	        }

	        int cmp = compare(b);
	        // Dividend less than divisor
	        if (cmp < 0) {
	            quotient.intLen = quotient.offset = 0;
	            return new MutableBigInteger(this);
	        }
	        // Dividend equal to divisor
	        if (cmp == 0) {
	            quotient.value[0] = quotient.intLen = 1;
	            quotient.offset = 0;
	            return new MutableBigInteger();
	        }

	        quotient.clear();
	        // Special case one word divisor
	        if (b.intLen == 1) {
	            int r = divideOneWord(b.value[b.offset], quotient);
	            if (r == 0)
	                return new MutableBigInteger();
	            return new MutableBigInteger(r);
	        }

	        // Copy divisor value to protect divisor
	        int[] div = Arrays.copyOfRange(b.value, b.offset, b.offset + b.intLen);
	        return divideMagnitude(div, quotient);
	    }

	    /**
	     * Internally used  to calculate the quotient of this div v and places the
	     * quotient in the provided MutableBigInteger object and the remainder is
	     * returned.
	     *
	     * @return the remainder of the division will be returned.
	     */
	    long divide(long v, MutableBigInteger quotient) {
	        if (v == 0)
	            throw new ArithmeticException("BigInteger divide by zero");

	        // Dividend is zero
	        if (intLen == 0) {
	            quotient.intLen = quotient.offset = 0;
	            return 0;
	        }
	        if (v < 0)
	            v = -v;

	        int d = (int)(v >>> 32);
	        quotient.clear();
	        // Special case on word divisor
	        if (d == 0)
	            return divideOneWord((int)v, quotient) & LONG_MASK;
	        else {
	            int[] div = new int[]{ d, (int)(v & LONG_MASK) };
	            return divideMagnitude(div, quotient).toLong();
	        }
	    }

	    /**
	     * Divide this MutableBigInteger by the divisor represented by its magnitude
	     * array. The quotient will be placed into the provided quotient object &
	     * the remainder object is returned.
	     */
	    private MutableBigInteger divideMagnitude(int[] divisor,
	                                              MutableBigInteger quotient) {

	        // Remainder starts as dividend with space for a leading zero
	        MutableBigInteger rem = new MutableBigInteger(new int[intLen + 1]);
	        System.arraycopy(value, offset, rem.value, 1, intLen);
	        rem.intLen = intLen;
	        rem.offset = 1;

	        int nlen = rem.intLen;

	        // Set the quotient size
	        int dlen = divisor.length;
	        int limit = nlen - dlen + 1;
	        if (quotient.value.length < limit) {
	            quotient.value = new int[limit];
	            quotient.offset = 0;
	        }
	        quotient.intLen = limit;
	        int[] q = quotient.value;

	        // D1 normalize the divisor
	        int shift = Integer.numberOfLeadingZeros(divisor[0]);
	        if (shift > 0) {
	            // First shift will not grow array
	            BigInteger.primitiveLeftShift(divisor, dlen, shift);
	            // But this one might
	            rem.leftShift(shift);
	        }

	        // Must insert leading 0 in rem if its length did not change
	        if (rem.intLen == nlen) {
	            rem.offset = 0;
	            rem.value[0] = 0;
	            rem.intLen++;
	        }

	        int dh = divisor[0];
	        long dhLong = dh & LONG_MASK;
	        int dl = divisor[1];
	        int[] qWord = new int[2];

	        // D2 Initialize j
	        for(int j=0; j<limit; j++) {
	            // D3 Calculate qhat
	            // estimate qhat
	            int qhat = 0;
	            int qrem = 0;
	            boolean skipCorrection = false;
	            int nh = rem.value[j+rem.offset];
	            int nh2 = nh + 0x80000000;
	            int nm = rem.value[j+1+rem.offset];

	            if (nh == dh) {
	                qhat = ~0;
	                qrem = nh + nm;
	                skipCorrection = qrem + 0x80000000 < nh2;
	            } else {
	                long nChunk = (((long)nh) << 32) | (nm & LONG_MASK);
	                if (nChunk >= 0) {
	                    qhat = (int) (nChunk / dhLong);
	                    qrem = (int) (nChunk - (qhat * dhLong));
	                } else {
	                    divWord(qWord, nChunk, dh);
	                    qhat = qWord[0];
	                    qrem = qWord[1];
	                }
	            }

	            if (qhat == 0)
	                continue;

	            if (!skipCorrection) { // Correct qhat
	                long nl = rem.value[j+2+rem.offset] & LONG_MASK;
	                long rs = ((qrem & LONG_MASK) << 32) | nl;
	                long estProduct = (dl & LONG_MASK) * (qhat & LONG_MASK);

	                if (unsignedLongCompare(estProduct, rs)) {
	                    qhat--;
	                    qrem = (int)((qrem & LONG_MASK) + dhLong);
	                    if ((qrem & LONG_MASK) >=  dhLong) {
	                        estProduct -= (dl & LONG_MASK);
	                        rs = ((qrem & LONG_MASK) << 32) | nl;
	                        if (unsignedLongCompare(estProduct, rs))
	                            qhat--;
	                    }
	                }
	            }

	            // D4 Multiply and subtract
	            rem.value[j+rem.offset] = 0;
	            int borrow = mulsub(rem.value, divisor, qhat, dlen, j+rem.offset);

	            // D5 Test remainder
	            if (borrow + 0x80000000 > nh2) {
	                // D6 Add back
	                divadd(divisor, rem.value, j+1+rem.offset);
	                qhat--;
	            }

	            // Store the quotient digit
	            q[j] = qhat;
	        } // D7 loop on j

	        // D8 Unnormalize
	        if (shift > 0)
	            rem.rightShift(shift);

	        quotient.normalize();
	        rem.normalize();
	        return rem;
	    }

	    /**
	     * Compare two longs as if they were unsigned.
	     * Returns true iff one is bigger than two.
	     */
	    private boolean unsignedLongCompare(long one, long two) {
	        return (one+Long.MIN_VALUE) > (two+Long.MIN_VALUE);
	    }

	    /**
	     * This method divides a long quantity by an int to estimate
	     * qhat for two multi precision numbers. It is used when
	     * the signed value of n is less than zero.
	     */
	    private void divWord(int[] result, long n, int d) {
	        long dLong = d & LONG_MASK;

	        if (dLong == 1) {
	            result[0] = (int)n;
	            result[1] = 0;
	            return;
	        }

	        // Approximate the quotient and remainder
	        long q = (n >>> 1) / (dLong >>> 1);
	        long r = n - q*dLong;

	        // Correct the approximation
	        while (r < 0) {
	            r += dLong;
	            q--;
	        }
	        while (r >= dLong) {
	            r -= dLong;
	            q++;
	        }

	        // n - q*dlong == r && 0 <= r <dLong, hence we're done.
	        result[0] = (int)q;
	        result[1] = (int)r;
	    }

	    /**
	     * Calculate GCD of this and b. This and b are changed by the computation.
	     */
	    MutableBigInteger hybridGCD(MutableBigInteger b) {
	        // Use Euclid's algorithm until the numbers are approximately the
	        // same length, then use the binary GCD algorithm to find the GCD.
	        MutableBigInteger a = this;
	        MutableBigInteger q = new MutableBigInteger();

	        while (b.intLen != 0) {
	            if (Math.abs(a.intLen - b.intLen) < 2)
	                return a.binaryGCD(b);

	            MutableBigInteger r = a.divide(b, q);
	            a = b;
	            b = r;
	        }
	        return a;
	    }

	    /**
	     * Calculate GCD of this and v.
	     * Assumes that this and v are not zero.
	     */
	    private MutableBigInteger binaryGCD(MutableBigInteger v) {
	        // Algorithm B from Knuth section 4.5.2
	        MutableBigInteger u = this;
	        MutableBigInteger r = new MutableBigInteger();

	        // step B1
	        int s1 = u.getLowestSetBit();
	        int s2 = v.getLowestSetBit();
	        int k = (s1 < s2) ? s1 : s2;
	        if (k != 0) {
	            u.rightShift(k);
	            v.rightShift(k);
	        }

	        // step B2
	        boolean uOdd = (k==s1);
	        MutableBigInteger t = uOdd ? v: u;
	        int tsign = uOdd ? -1 : 1;

	        int lb;
	        while ((lb = t.getLowestSetBit()) >= 0) {
	            // steps B3 and B4
	            t.rightShift(lb);
	            // step B5
	            if (tsign > 0)
	                u = t;
	            else
	                v = t;

	            // Special case one word numbers
	            if (u.intLen < 2 && v.intLen < 2) {
	                int x = u.value[u.offset];
	                int y = v.value[v.offset];
	                x  = binaryGcd(x, y);
	                r.value[0] = x;
	                r.intLen = 1;
	                r.offset = 0;
	                if (k > 0)
	                    r.leftShift(k);
	                return r;
	            }

	            // step B6
	            if ((tsign = u.difference(v)) == 0)
	                break;
	            t = (tsign >= 0) ? u : v;
	        }

	        if (k > 0)
	            u.leftShift(k);
	        return u;
	    }

	    /**
	     * Calculate GCD of a and b interpreted as unsigned integers.
	     */
	    static int binaryGcd(int a, int b) {
	        if (b==0)
	            return a;
	        if (a==0)
	            return b;

	        // Right shift a & b till their last bits equal to 1.
	        int aZeros = Integer.numberOfTrailingZeros(a);
	        int bZeros = Integer.numberOfTrailingZeros(b);
	        a >>>= aZeros;
	        b >>>= bZeros;

	        int t = (aZeros < bZeros ? aZeros : bZeros);

	        while (a != b) {
	            if ((a+0x80000000) > (b+0x80000000)) {  // a > b as unsigned
	                a -= b;
	                a >>>= Integer.numberOfTrailingZeros(a);
	            } else {
	                b -= a;
	                b >>>= Integer.numberOfTrailingZeros(b);
	            }
	        }
	        return a<<t;
	    }

	   
	    /*
	     * Calculate the multiplicative inverse of this mod 2^k.
	     */
	    MutableBigInteger modInverseMP2(int k) {
	        if (isEven())
	            throw new ArithmeticException("Non-invertible. (GCD != 1)");

	        if (k > 64)
	            return euclidModInverse(k);

	        int t = inverseMod32(value[offset+intLen-1]);

	        if (k < 33) {
	            t = (k == 32 ? t : t & ((1 << k) - 1));
	            return new MutableBigInteger(t);
	        }

	        long pLong = (value[offset+intLen-1] & LONG_MASK);
	        if (intLen > 1)
	            pLong |=  ((long)value[offset+intLen-2] << 32);
	        long tLong = t & LONG_MASK;
	        tLong = tLong * (2 - pLong * tLong);  // 1 more Newton iter step
	        tLong = (k == 64 ? tLong : tLong & ((1L << k) - 1));

	        MutableBigInteger result = new MutableBigInteger(new int[2]);
	        result.value[0] = (int)(tLong >>> 32);
	        result.value[1] = (int)tLong;
	        result.intLen = 2;
	        result.normalize();
	        return result;
	    }

	    /*
	     * Returns the multiplicative inverse of val mod 2^32.  Assumes val is odd.
	     */
	    static int inverseMod32(int val) {
	        // Newton's iteration!
	        int t = val;
	        t *= 2 - val*t;
	        t *= 2 - val*t;
	        t *= 2 - val*t;
	        t *= 2 - val*t;
	        return t;
	    }

	    /*
	     * Calculate the multiplicative inverse of 2^k mod mod, where mod is odd.
	     */
	    static MutableBigInteger modInverseBP2(MutableBigInteger mod, int k) {
	        // Copy the mod to protect original
	        return fixup(new MutableBigInteger(1), new MutableBigInteger(mod), k);
	    }

	   
	    /*
	     * The Fixup Algorithm
	     * Calculates X such that X = C * 2^(-k) (mod P)
	     * Assumes C<P and P is odd.
	     */
	    static MutableBigInteger fixup(MutableBigInteger c, MutableBigInteger p,
	                                                                      int k) {
	        MutableBigInteger temp = new MutableBigInteger();
	        // Set r to the multiplicative inverse of p mod 2^32
	        int r = -inverseMod32(p.value[p.offset+p.intLen-1]);

	        for(int i=0, numWords = k >> 5; i<numWords; i++) {
	            // V = R * c (mod 2^j)
	            int  v = r * c.value[c.offset + c.intLen-1];
	            // c = c + (v * p)
	            p.mul(v, temp);
	            c.add(temp);
	            // c = c / 2^j
	            c.intLen--;
	        }
	        int numBits = k & 0x1f;
	        if (numBits != 0) {
	            // V = R * c (mod 2^j)
	            int v = r * c.value[c.offset + c.intLen-1];
	            v &= ((1<<numBits) - 1);
	            // c = c + (v * p)
	            p.mul(v, temp);
	            c.add(temp);
	            // c = c / 2^j
	            c.rightShift(numBits);
	        }

	        // In theory, c may be greater than p at this point (Very rare!)
	        while (c.compare(p) >= 0)
	            c.subtract(p);

	        return c;
	    }

	    /**
	     * Uses the extended Euclidean algorithm to compute the modInverse of base
	     * mod a modulus that is a power of 2. The modulus is 2^k.
	     */
	    MutableBigInteger euclidModInverse(int k) {
	        MutableBigInteger b = new MutableBigInteger(1);
	        b.leftShift(k);
	        MutableBigInteger mod = new MutableBigInteger(b);

	        MutableBigInteger a = new MutableBigInteger(this);
	        MutableBigInteger q = new MutableBigInteger();
	        MutableBigInteger r = b.divide(a, q);

	        MutableBigInteger swapper = b;
	        // swap b & r
	        b = r;
	        r = swapper;

	        MutableBigInteger t1 = new MutableBigInteger(q);
	        MutableBigInteger t0 = new MutableBigInteger(1);
	        MutableBigInteger temp = new MutableBigInteger();

	        while (!b.isOne()) {
	            r = a.divide(b, q);

	            if (r.intLen == 0)
	                throw new ArithmeticException("BigInteger not invertible.");

	            swapper = r;
	            a = swapper;

	            if (q.intLen == 1)
	                t1.mul(q.value[q.offset], temp);
	            else
	                q.multiply(t1, temp);
	            swapper = q;
	            q = temp;
	            temp = swapper;
	            t0.add(q);

	            if (a.isOne())
	                return t0;

	            r = b.divide(a, q);

	            if (r.intLen == 0)
	                throw new ArithmeticException("BigInteger not invertible.");

	            swapper = b;
	            b =  r;

	            if (q.intLen == 1)
	                t0.mul(q.value[q.offset], temp);
	            else
	                q.multiply(t0, temp);
	            swapper = q; q = temp; temp = swapper;

	            t1.add(q);
	        }
	        mod.subtract(t1);
	        return mod;
	    }

	}

	/**
	 * Immutable arbitrary-precision integers.  All operations behave as if
	 * BigIntegers were represented in two's-complement notation (like Java's
	 * primitive integer types).  BigInteger provides analogues to all of Java's
	 * primitive integer operators, and all relevant methods from java.lang.Math.
	 * Additionally, BigInteger provides operations for modular arithmetic, GCD
	 * calculation, primality testing, prime generation, bit manipulation,
	 * and a few other miscellaneous operations.
	 *
	 * <p>Semantics of arithmetic operations exactly mimic those of Java's integer
	 * arithmetic operators, as defined in <i>The Java Language Specification</i>.
	 * For example, division by zero throws an {@code ArithmeticException}, and
	 * division of a negative by a positive yields a negative (or zero) remainder.
	 * All of the details in the Spec concerning overflow are ignored, as
	 * BigIntegers are made as large as necessary to accommodate the results of an
	 * operation.
	 *
	 * <p>Semantics of shift operations extend those of Java's shift operators
	 * to allow for negative shift distances.  A right-shift with a negative
	 * shift distance results in a left shift, and vice-versa.  The unsigned
	 * right shift operator ({@code >>>}) is omitted, as this operation makes
	 * little sense in combination with the "infinite word size" abstraction
	 * provided by this class.
	 *
	 * <p>Semantics of bitwise logical operations exactly mimic those of Java's
	 * bitwise integer operators.  The binary operators ({@code and},
	 * {@code or}, {@code xor}) implicitly perform sign extension on the shorter
	 * of the two operands prior to performing the operation.
	 *
	 * <p>Comparison operations perform signed integer comparisons, analogous to
	 * those performed by Java's relational and equality operators.
	 *
	 * <p>Modular arithmetic operations are provided to compute residues, perform
	 * exponentiation, and compute multiplicative inverses.  These methods always
	 * return a non-negative result, between {@code 0} and {@code (modulus - 1)},
	 * inclusive.
	 *
	 * <p>Bit operations operate on a single bit of the two's-complement
	 * representation of their operand.  If necessary, the operand is sign-
	 * extended so that it contains the designated bit.  None of the single-bit
	 * operations can produce a BigInteger with a different sign from the
	 * BigInteger being operated on, as they affect only a single bit, and the
	 * "infinite word size" abstraction provided by this class ensures that there
	 * are infinitely many "virtual sign bits" preceding each BigInteger.
	 *
	 * <p>For the sake of brevity and clarity, pseudo-code is used throughout the
	 * descriptions of BigInteger methods.  The pseudo-code expression
	 * {@code (i + j)} is shorthand for "a BigInteger whose value is
	 * that of the BigInteger {@code i} plus that of the BigInteger {@code j}."
	 * The pseudo-code expression {@code (i == j)} is shorthand for
	 * "{@code true} if and only if the BigInteger {@code i} represents the same
	 * value as the BigInteger {@code j}."  Other pseudo-code expressions are
	 * interpreted similarly.
	 *
	 * <p>All methods and constructors in this class throw
	 * {@code NullPointerException} when passed
	 * a null object reference for any input parameter.
	 *
	 * @see     BigDecimal
	 * @author  Josh Bloch
	 * @author  Michael McCloskey
	 * @since JDK1.1
	 */

	static class BigInteger extends Number implements Comparable<BigInteger> {
	    /**
	     * The signum of this BigInteger: -1 for negative, 0 for zero, or
	     * 1 for positive.  Note that the BigInteger zero <i>must</i> have
	     * a signum of 0.  This is necessary to ensures that there is exactly one
	     * representation for each BigInteger value.
	     *
	     * @serial
	     */
	    final int signum;

	    /**
	     * The magnitude of this BigInteger, in <i>big-endian</i> order: the
	     * zeroth element of this array is the most-significant int of the
	     * magnitude.  The magnitude must be "minimal" in that the most-significant
	     * int ({@code mag[0]}) must be non-zero.  This is necessary to
	     * ensure that there is exactly one representation for each BigInteger
	     * value.  Note that this implies that the BigInteger zero has a
	     * zero-length mag array.
	     */
	    final int[] mag;

	    // These "redundant fields" are initialized with recognizable nonsense
	    // values, and cached the first time they are needed (or never, if they
	    // aren't needed).

	     /**
	     * One plus the bitCount of this BigInteger. Zeros means unitialized.
	     *
	     * @serial
	     * @see #bitCount
	     * @deprecated Deprecated since logical value is offset from stored
	     * value and correction factor is applied in accessor method.
	     */
	    @Deprecated
	    private int bitCount;

	    /**
	     * One plus the bitLength of this BigInteger. Zeros means unitialized.
	     * (either value is acceptable).
	     *
	     * @serial
	     * @see #bitLength()
	     * @deprecated Deprecated since logical value is offset from stored
	     * value and correction factor is applied in accessor method.
	     */
	    @Deprecated
	    private int bitLength;

	    /**
	     * Two plus the lowest set bit of this BigInteger, as returned by
	     * getLowestSetBit().
	     *
	     * @serial
	     * @see #getLowestSetBit
	     * @deprecated Deprecated since logical value is offset from stored
	     * value and correction factor is applied in accessor method.
	     */
	    @Deprecated
	    private int lowestSetBit;

	    /**
	     * Two plus the index of the lowest-order int in the magnitude of this
	     * BigInteger that contains a nonzero int, or -2 (either value is acceptable).
	     * The least significant int has int-number 0, the next int in order of
	     * increasing significance has int-number 1, and so forth.
	     * @deprecated Deprecated since logical value is offset from stored
	     * value and correction factor is applied in accessor method.
	     */
	    @Deprecated
	    private int firstNonzeroIntNum;

	    /**
	     * This mask is used to obtain the value of an int as if it were unsigned.
	     */

	    //Constructors

	    /**
	     * Translates a byte array containing the two's-complement binary
	     * representation of a BigInteger into a BigInteger.  The input array is
	     * assumed to be in <i>big-endian</i> byte-order: the most significant
	     * byte is in the zeroth element.
	     *
	     * @param  val big-endian two's-complement binary representation of
	     *         BigInteger.
	     * @throws NumberFormatException {@code val} is zero bytes long.
	     */
	    public BigInteger(byte[] val) {
	        if (val.length == 0)
	            throw new NumberFormatException("Zero length BigInteger");

	        if (val[0] < 0) {
	            mag = makePositive(val);
	            signum = -1;
	        } else {
	            mag = stripLeadingZeroBytes(val);
	            signum = (mag.length == 0 ? 0 : 1);
	        }
	    }

	    /**
	     * This private constructor translates an int array containing the
	     * two's-complement binary representation of a BigInteger into a
	     * BigInteger. The input array is assumed to be in <i>big-endian</i>
	     * int-order: the most significant int is in the zeroth element.
	     */
	    private BigInteger(int[] val) {
	        if (val.length == 0)
	            throw new NumberFormatException("Zero length BigInteger");

	        if (val[0] < 0) {
	            mag = makePositive(val);
	            signum = -1;
	        } else {
	            mag = trustedStripLeadingZeroInts(val);
	            signum = (mag.length == 0 ? 0 : 1);
	        }
	    }

	    /**
	     * Translates the sign-magnitude representation of a BigInteger into a
	     * BigInteger.  The sign is represented as an integer signum value: -1 for
	     * negative, 0 for zero, or 1 for positive.  The magnitude is a byte array
	     * in <i>big-endian</i> byte-order: the most significant byte is in the
	     * zeroth element.  A zero-length magnitude array is permissible, and will
	     * result in a BigInteger value of 0, whether signum is -1, 0 or 1.
	     *
	     * @param  signum signum of the number (-1 for negative, 0 for zero, 1
	     *         for positive).
	     * @param  magnitude big-endian binary representation of the magnitude of
	     *         the number.
	     * @throws NumberFormatException {@code signum} is not one of the three
	     *         legal values (-1, 0, and 1), or {@code signum} is 0 and
	     *         {@code magnitude} contains one or more non-zero bytes.
	     */
	    public BigInteger(int signum, byte[] magnitude) {
	        this.mag = stripLeadingZeroBytes(magnitude);

	        if (signum < -1 || signum > 1)
	            throw(new NumberFormatException("Invalid signum value"));

	        if (this.mag.length==0) {
	            this.signum = 0;
	        } else {
	            if (signum == 0)
	                throw(new NumberFormatException("signum-magnitude mismatch"));
	            this.signum = signum;
	        }
	    }

	    /**
	     * Translates the String representation of a BigInteger in the
	     * specified radix into a BigInteger.  The String representation
	     * consists of an optional minus or plus sign followed by a
	     * sequence of one or more digits in the specified radix.  The
	     * character-to-digit mapping is provided by {@code
	     * Character.digit}.  The String may not contain any extraneous
	     * characters (whitespace, for example).
	     *
	     * @param val String representation of BigInteger.
	     * @param radix radix to be used in interpreting {@code val}.
	     * @throws NumberFormatException {@code val} is not a valid representation
	     *         of a BigInteger in the specified radix, or {@code radix} is
	     *         outside the range from {@link Character#MIN_RADIX} to
	     *         {@link Character#MAX_RADIX}, inclusive.
	     * @see    Character#digit
	     */
	    public BigInteger(String val, int radix) {
	        int cursor = 0, numDigits;
	        final int len = val.length();

	        if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX)
	            throw new NumberFormatException("Radix out of range");
	        if (len == 0)
	            throw new NumberFormatException("Zero length BigInteger");

	        // Check for at most one leading sign
	        int sign = 1;
	        int index1 = val.lastIndexOf('-');
	        int index2 = val.lastIndexOf('+');
	        if ((index1 + index2) <= -1) {
	            // No leading sign character or at most one leading sign character
	            if (index1 == 0 || index2 == 0) {
	                cursor = 1;
	                if (len == 1)
	                    throw new NumberFormatException("Zero length BigInteger");
	            }
	            if (index1 == 0)
	                sign = -1;
	        } else
	            throw new NumberFormatException("Illegal embedded sign character");

	        // Skip leading zeros and compute number of digits in magnitude
	        while (cursor < len &&
	               Character.digit(val.charAt(cursor), radix) == 0)
	            cursor++;
	        if (cursor == len) {
	            signum = 0;
	            mag = ZERO.mag;
	            return;
	        }

	        numDigits = len - cursor;
	        signum = sign;

	        // Pre-allocate array of expected size. May be too large but can
	        // never be too small. Typically exact.
	        int numBits = (int)(((numDigits * bitsPerDigit[radix]) >>> 10) + 1);
	        int numWords = (numBits + 31) >>> 5;
	        int[] magnitude = new int[numWords];

	        // Process first (potentially short) digit group
	        int firstGroupLen = numDigits % digitsPerInt[radix];
	        if (firstGroupLen == 0)
	            firstGroupLen = digitsPerInt[radix];
	        String group = val.substring(cursor, cursor += firstGroupLen);
	        magnitude[numWords - 1] = Integer.parseInt(group, radix);
	        if (magnitude[numWords - 1] < 0)
	            throw new NumberFormatException("Illegal digit");

	        // Process remaining digit groups
	        int superRadix = intRadix[radix];
	        int groupVal = 0;
	        while (cursor < len) {
	            group = val.substring(cursor, cursor += digitsPerInt[radix]);
	            groupVal = Integer.parseInt(group, radix);
	            if (groupVal < 0)
	                throw new NumberFormatException("Illegal digit");
	            destructiveMulAdd(magnitude, superRadix, groupVal);
	        }
	        // Required for cases where the array was overallocated.
	        mag = trustedStripLeadingZeroInts(magnitude);
	    }

	    // Constructs a new BigInteger using a char array with radix=10
	    BigInteger(char[] val) {
	        int cursor = 0, numDigits;
	        int len = val.length;

	        // Check for leading minus sign
	        int sign = 1;
	        if (val[0] == '-') {
	            if (len == 1)
	                throw new NumberFormatException("Zero length BigInteger");
	            sign = -1;
	            cursor = 1;
	        } else if (val[0] == '+') {
	            if (len == 1)
	                throw new NumberFormatException("Zero length BigInteger");
	            cursor = 1;
	        }

	        // Skip leading zeros and compute number of digits in magnitude
	        while (cursor < len && Character.digit(val[cursor], 10) == 0)
	            cursor++;
	        if (cursor == len) {
	            signum = 0;
	            mag = ZERO.mag;
	            return;
	        }

	        numDigits = len - cursor;
	        signum = sign;

	        // Pre-allocate array of expected size
	        int numWords;
	        if (len < 10) {
	            numWords = 1;
	        } else {
	            int numBits = (int)(((numDigits * bitsPerDigit[10]) >>> 10) + 1);
	            numWords = (numBits + 31) >>> 5;
	        }
	        int[] magnitude = new int[numWords];

	        // Process first (potentially short) digit group
	        int firstGroupLen = numDigits % digitsPerInt[10];
	        if (firstGroupLen == 0)
	            firstGroupLen = digitsPerInt[10];
	        magnitude[numWords - 1] = parseInt(val, cursor,  cursor += firstGroupLen);

	        // Process remaining digit groups
	        while (cursor < len) {
	            int groupVal = parseInt(val, cursor, cursor += digitsPerInt[10]);
	            destructiveMulAdd(magnitude, intRadix[10], groupVal);
	        }
	        mag = trustedStripLeadingZeroInts(magnitude);
	    }

	    // Create an integer with the digits between the two indexes
	    // Assumes start < end. The result may be negative, but it
	    // is to be treated as an unsigned value.
	    private int parseInt(char[] source, int start, int end) {
	        int result = Character.digit(source[start++], 10);
	        if (result == -1)
	            throw new NumberFormatException(new String(source));

	        for (int index = start; index<end; index++) {
	            int nextVal = Character.digit(source[index], 10);
	            if (nextVal == -1)
	                throw new NumberFormatException(new String(source));
	            result = 10*result + nextVal;
	        }

	        return result;
	    }

	    // bitsPerDigit in the given radix times 1024
	    // Rounded up to avoid underallocation.
	    private static long bitsPerDigit[] = { 0, 0,
	        1024, 1624, 2048, 2378, 2648, 2875, 3072, 3247, 3402, 3543, 3672,
	        3790, 3899, 4001, 4096, 4186, 4271, 4350, 4426, 4498, 4567, 4633,
	        4696, 4756, 4814, 4870, 4923, 4975, 5025, 5074, 5120, 5166, 5210,
	                                           5253, 5295};

	    // Multiply x array times word y in place, and add word z
	    private static void destructiveMulAdd(int[] x, int y, int z) {
	        // Perform the multiplication word by word
	        long ylong = y & LONG_MASK;
	        long zlong = z & LONG_MASK;
	        int len = x.length;

	        long product = 0;
	        long carry = 0;
	        for (int i = len-1; i >= 0; i--) {
	            product = ylong * (x[i] & LONG_MASK) + carry;
	            x[i] = (int)product;
	            carry = product >>> 32;
	        }

	        // Perform the addition
	        long sum = (x[len-1] & LONG_MASK) + zlong;
	        x[len-1] = (int)sum;
	        carry = sum >>> 32;
	        for (int i = len-2; i >= 0; i--) {
	            sum = (x[i] & LONG_MASK) + carry;
	            x[i] = (int)sum;
	            carry = sum >>> 32;
	        }
	    }

	    /**
	     * Translates the decimal String representation of a BigInteger into a
	     * BigInteger.  The String representation consists of an optional minus
	     * sign followed by a sequence of one or more decimal digits.  The
	     * character-to-digit mapping is provided by {@code Character.digit}.
	     * The String may not contain any extraneous characters (whitespace, for
	     * example).
	     *
	     * @param val decimal String representation of BigInteger.
	     * @throws NumberFormatException {@code val} is not a valid representation
	     *         of a BigInteger.
	     * @see    Character#digit
	     */
	    public BigInteger(String val) {
	        this(val, 10);
	    }

	    /**
	     * Constructs a randomly generated BigInteger, uniformly distributed over
	     * the range 0 to (2<sup>{@code numBits}</sup> - 1), inclusive.
	     * The uniformity of the distribution assumes that a fair source of random
	     * bits is provided in {@code rnd}.  Note that this constructor always
	     * constructs a non-negative BigInteger.
	     *
	     * @param  numBits maximum bitLength of the new BigInteger.
	     * @param  rnd source of randomness to be used in computing the new
	     *         BigInteger.
	     * @throws IllegalArgumentException {@code numBits} is negative.
	     * @see #bitLength()
	     */
	    public BigInteger(int numBits, Random rnd) {
	        this(1, randomBits(numBits, rnd));
	    }

	    private static byte[] randomBits(int numBits, Random rnd) {
	        if (numBits < 0)
	            throw new IllegalArgumentException("numBits must be non-negative");
	        int numBytes = (int)(((long)numBits+7)/8); // avoid overflow
	        byte[] randomBits = new byte[numBytes];

	        // Generate random bytes and mask out any excess bits
	        if (numBytes > 0) {
	            rnd.nextBytes(randomBits);
	            int excessBits = 8*numBytes - numBits;
	            randomBits[0] &= (1 << (8-excessBits)) - 1;
	        }
	        return randomBits;
	    }

	    /**
	     * This internal constructor differs from its public cousin
	     * with the arguments reversed in two ways: it assumes that its
	     * arguments are correct, and it doesn't copy the magnitude array.
	     */
	    BigInteger(int[] magnitude, int signum) {
	        this.signum = (magnitude.length==0 ? 0 : signum);
	        this.mag = magnitude;
	    }

	    

	    //Static Factory Methods

	    /**
	     * Returns a BigInteger whose value is equal to that of the
	     * specified {@code long}.  This "static factory method" is
	     * provided in preference to a ({@code long}) constructor
	     * because it allows for reuse of frequently used BigIntegers.
	     *
	     * @param  val value of the BigInteger to return.
	     * @return a BigInteger with the specified value.
	     */
	    public static BigInteger valueOf(long val) {
	        // If -MAX_CONSTANT < val < MAX_CONSTANT, return stashed constant
	        if (val == 0)
	            return ZERO;
	        if (val > 0 && val <= MAX_CONSTANT)
	            return posConst[(int) val];
	        else if (val < 0 && val >= -MAX_CONSTANT)
	            return negConst[(int) -val];

	        return new BigInteger(val);
	    }

	    /**
	     * Constructs a BigInteger with the specified value, which may not be zero.
	     */
	    private BigInteger(long val) {
	        if (val < 0) {
	            val = -val;
	            signum = -1;
	        } else {
	            signum = 1;
	        }

	        int highWord = (int)(val >>> 32);
	        if (highWord==0) {
	            mag = new int[1];
	            mag[0] = (int)val;
	        } else {
	            mag = new int[2];
	            mag[0] = highWord;
	            mag[1] = (int)val;
	        }
	    }

	    /**
	     * Returns a BigInteger with the given two's complement representation.
	     * Assumes that the input array will not be modified (the returned
	     * BigInteger will reference the input array if feasible).
	     */
	    private static BigInteger valueOf(int val[]) {
	        return (val[0]>0 ? new BigInteger(val, 1) : new BigInteger(val));
	    }

	    // Constants

	    /**
	     * Initialize static constant array when class is loaded.
	     */
	    private final static int MAX_CONSTANT = 16;
	    private static BigInteger posConst[] = new BigInteger[MAX_CONSTANT+1];
	    private static BigInteger negConst[] = new BigInteger[MAX_CONSTANT+1];
	    static {
	        for (int i = 1; i <= MAX_CONSTANT; i++) {
	            int[] magnitude = new int[1];
	            magnitude[0] = i;
	            posConst[i] = new BigInteger(magnitude,  1);
	            negConst[i] = new BigInteger(magnitude, -1);
	        }
	    }

	    /**
	     * The BigInteger constant zero.
	     *
	     * @since   1.2
	     */
	    public static final BigInteger ZERO = new BigInteger(new int[0], 0);

	    /**
	     * The BigInteger constant one.
	     *
	     * @since   1.2
	     */
	    public static final BigInteger ONE = valueOf(1);

	    /**
	     * The BigInteger constant ten.
	     *
	     * @since   1.5
	     */
	    public static final BigInteger TEN = valueOf(10);

	    // Arithmetic Operations

	    /**
	     * Returns a BigInteger whose value is {@code (this + val)}.
	     *
	     * @param  val value to be added to this BigInteger.
	     * @return {@code this + val}
	     */
	    public BigInteger add(BigInteger val) {
	        if (val.signum == 0)
	            return this;
	        if (signum == 0)
	            return val;
	        if (val.signum == signum)
	            return new BigInteger(add(mag, val.mag), signum);

	        int cmp = compareMagnitude(val);
	        if (cmp == 0)
	            return ZERO;
	        int[] resultMag = (cmp > 0 ? subtract(mag, val.mag)
	                           : subtract(val.mag, mag));
	        resultMag = trustedStripLeadingZeroInts(resultMag);

	        return new BigInteger(resultMag, cmp == signum ? 1 : -1);
	    }

	    /**
	     * Adds the contents of the int arrays x and y. This method allocates
	     * a new int array to hold the answer and returns a reference to that
	     * array.
	     */
	    private static int[] add(int[] x, int[] y) {
	        // If x is shorter, swap the two arrays
	        if (x.length < y.length) {
	            int[] tmp = x;
	            x = y;
	            y = tmp;
	        }

	        int xIndex = x.length;
	        int yIndex = y.length;
	        int result[] = new int[xIndex];
	        long sum = 0;

	        // Add common parts of both numbers
	        while(yIndex > 0) {
	            sum = (x[--xIndex] & LONG_MASK) +
	                  (y[--yIndex] & LONG_MASK) + (sum >>> 32);
	            result[xIndex] = (int)sum;
	        }

	        // Copy remainder of longer number while carry propagation is required
	        boolean carry = (sum >>> 32 != 0);
	        while (xIndex > 0 && carry)
	            carry = ((result[--xIndex] = x[xIndex] + 1) == 0);

	        // Copy remainder of longer number
	        while (xIndex > 0)
	            result[--xIndex] = x[xIndex];

	        // Grow result if necessary
	        if (carry) {
	            int bigger[] = new int[result.length + 1];
	            System.arraycopy(result, 0, bigger, 1, result.length);
	            bigger[0] = 0x01;
	            return bigger;
	        }
	        return result;
	    }

	    /**
	     * Returns a BigInteger whose value is {@code (this - val)}.
	     *
	     * @param  val value to be subtracted from this BigInteger.
	     * @return {@code this - val}
	     */
	    public BigInteger subtract(BigInteger val) {
	        if (val.signum == 0)
	            return this;
	        if (signum == 0)
	            return val.negate();
	        if (val.signum != signum)
	            return new BigInteger(add(mag, val.mag), signum);

	        int cmp = compareMagnitude(val);
	        if (cmp == 0)
	            return ZERO;
	        int[] resultMag = (cmp > 0 ? subtract(mag, val.mag)
	                           : subtract(val.mag, mag));
	        resultMag = trustedStripLeadingZeroInts(resultMag);
	        return new BigInteger(resultMag, cmp == signum ? 1 : -1);
	    }

	    /**
	     * Subtracts the contents of the second int arrays (little) from the
	     * first (big).  The first int array (big) must represent a larger number
	     * than the second.  This method allocates the space necessary to hold the
	     * answer.
	     */
	    private static int[] subtract(int[] big, int[] little) {
	        int bigIndex = big.length;
	        int result[] = new int[bigIndex];
	        int littleIndex = little.length;
	        long difference = 0;

	        // Subtract common parts of both numbers
	        while(littleIndex > 0) {
	            difference = (big[--bigIndex] & LONG_MASK) -
	                         (little[--littleIndex] & LONG_MASK) +
	                         (difference >> 32);
	            result[bigIndex] = (int)difference;
	        }

	        // Subtract remainder of longer number while borrow propagates
	        boolean borrow = (difference >> 32 != 0);
	        while (bigIndex > 0 && borrow)
	            borrow = ((result[--bigIndex] = big[bigIndex] - 1) == -1);

	        // Copy remainder of longer number
	        while (bigIndex > 0)
	            result[--bigIndex] = big[bigIndex];

	        return result;
	    }

	    /**
	     * Returns a BigInteger whose value is {@code (this * val)}.
	     *
	     * @param  val value to be multiplied by this BigInteger.
	     * @return {@code this * val}
	     */
	    public BigInteger multiply(BigInteger val) {
	        if (val.signum == 0 || signum == 0)
	            return ZERO;

	        int[] result = multiplyToLen(mag, mag.length,
	                                     val.mag, val.mag.length, null);
	        result = trustedStripLeadingZeroInts(result);
	        return new BigInteger(result, signum == val.signum ? 1 : -1);
	    }

	   

	    /**
	     * Multiplies int arrays x and y to the specified lengths and places
	     * the result into z. There will be no leading zeros in the resultant array.
	     */
	    private int[] multiplyToLen(int[] x, int xlen, int[] y, int ylen, int[] z) {
	        int xstart = xlen - 1;
	        int ystart = ylen - 1;

	        if (z == null || z.length < (xlen+ ylen))
	            z = new int[xlen+ylen];

	        long carry = 0;
	        for (int j=ystart, k=ystart+1+xstart; j>=0; j--, k--) {
	            long product = (y[j] & LONG_MASK) *
	                           (x[xstart] & LONG_MASK) + carry;
	            z[k] = (int)product;
	            carry = product >>> 32;
	        }
	        z[xstart] = (int)carry;

	        for (int i = xstart-1; i >= 0; i--) {
	            carry = 0;
	            for (int j=ystart, k=ystart+1+i; j>=0; j--, k--) {
	                long product = (y[j] & LONG_MASK) *
	                               (x[i] & LONG_MASK) +
	                               (z[k] & LONG_MASK) + carry;
	                z[k] = (int)product;
	                carry = product >>> 32;
	            }
	            z[i] = (int)carry;
	        }
	        return z;
	    }

	    /**
	     * Squares the contents of the int array x. The result is placed into the
	     * int array z.  The contents of x are not changed.
	     */
	    private static final int[] squareToLen(int[] x, int len, int[] z) {
	        /*
	         * The algorithm used here is adapted from Colin Plumb's C library.
	         * Technique: Consider the partial products in the multiplication
	         * of "abcde" by itself:
	         *
	         *               a  b  c  d  e
	         *            *  a  b  c  d  e
	         *          ==================
	         *              ae be ce de ee
	         *           ad bd cd dd de
	         *        ac bc cc cd ce
	         *     ab bb bc bd be
	         *  aa ab ac ad ae
	         *
	         * Note that everything above the main diagonal:
	         *              ae be ce de = (abcd) * e
	         *           ad bd cd       = (abc) * d
	         *        ac bc             = (ab) * c
	         *     ab                   = (a) * b
	         *
	         * is a copy of everything below the main diagonal:
	         *                       de
	         *                 cd ce
	         *           bc bd be
	         *     ab ac ad ae
	         *
	         * Thus, the sum is 2 * (off the diagonal) + diagonal.
	         *
	         * This is accumulated beginning with the diagonal (which
	         * consist of the squares of the digits of the input), which is then
	         * divided by two, the off-diagonal added, and multiplied by two
	         * again.  The low bit is simply a copy of the low bit of the
	         * input, so it doesn't need special care.
	         */
	        int zlen = len << 1;
	        if (z == null || z.length < zlen)
	            z = new int[zlen];

	        // Store the squares, right shifted one bit (i.e., divided by 2)
	        int lastProductLowWord = 0;
	        for (int j=0, i=0; j<len; j++) {
	            long piece = (x[j] & LONG_MASK);
	            long product = piece * piece;
	            z[i++] = (lastProductLowWord << 31) | (int)(product >>> 33);
	            z[i++] = (int)(product >>> 1);
	            lastProductLowWord = (int)product;
	        }

	        // Add in off-diagonal sums
	        for (int i=len, offset=1; i>0; i--, offset+=2) {
	            int t = x[i-1];
	            t = mulAdd(z, x, offset, i-1, t);
	            addOne(z, offset-1, i, t);
	        }

	        // Shift back up and set low bit
	        primitiveLeftShift(z, zlen, 1);
	        z[zlen-1] |= x[len-1] & 1;

	        return z;
	    }

	    /**
	     * Returns a BigInteger whose value is {@code (this / val)}.
	     *
	     * @param  val value by which this BigInteger is to be divided.
	     * @return {@code this / val}
	     * @throws ArithmeticException if {@code val} is zero.
	     */
	    public BigInteger divide(BigInteger val) {
	        MutableBigInteger q = new MutableBigInteger(),
	                          a = new MutableBigInteger(this.mag),
	                          b = new MutableBigInteger(val.mag);

	        a.divide(b, q);
	        return q.toBigInteger(this.signum == val.signum ? 1 : -1);
	    }

	    /**
	     * Returns an array of two BigIntegers containing {@code (this / val)}
	     * followed by {@code (this % val)}.
	     *
	     * @param  val value by which this BigInteger is to be divided, and the
	     *         remainder computed.
	     * @return an array of two BigIntegers: the quotient {@code (this / val)}
	     *         is the initial element, and the remainder {@code (this % val)}
	     *         is the final element.
	     * @throws ArithmeticException if {@code val} is zero.
	     */
	    public BigInteger[] divideAndRemainder(BigInteger val) {
	        BigInteger[] result = new BigInteger[2];
	        MutableBigInteger q = new MutableBigInteger(),
	                          a = new MutableBigInteger(this.mag),
	                          b = new MutableBigInteger(val.mag);
	        MutableBigInteger r = a.divide(b, q);
	        result[0] = q.toBigInteger(this.signum == val.signum ? 1 : -1);
	        result[1] = r.toBigInteger(this.signum);
	        return result;
	    }

	    /**
	     * Returns a BigInteger whose value is {@code (this % val)}.
	     *
	     * @param  val value by which this BigInteger is to be divided, and the
	     *         remainder computed.
	     * @return {@code this % val}
	     * @throws ArithmeticException if {@code val} is zero.
	     */
	    public BigInteger remainder(BigInteger val) {
	        MutableBigInteger q = new MutableBigInteger(),
	                          a = new MutableBigInteger(this.mag),
	                          b = new MutableBigInteger(val.mag);

	        return a.divide(b, q).toBigInteger(this.signum);
	    }

	    /**
	     * Returns a BigInteger whose value is <tt>(this<sup>exponent</sup>)</tt>.
	     * Note that {@code exponent} is an integer rather than a BigInteger.
	     *
	     * @param  exponent exponent to which this BigInteger is to be raised.
	     * @return <tt>this<sup>exponent</sup></tt>
	     * @throws ArithmeticException {@code exponent} is negative.  (This would
	     *         cause the operation to yield a non-integer value.)
	     */
	    public BigInteger pow(int exponent) {
	        if (exponent < 0)
	            throw new ArithmeticException("Negative exponent");
	        if (signum==0)
	            return (exponent==0 ? ONE : this);

	        // Perform exponentiation using repeated squaring trick
	        int newSign = (signum<0 && (exponent&1)==1 ? -1 : 1);
	        int[] baseToPow2 = this.mag;
	        int[] result = {1};

	        while (exponent != 0) {
	            if ((exponent & 1)==1) {
	                result = multiplyToLen(result, result.length,
	                                       baseToPow2, baseToPow2.length, null);
	                result = trustedStripLeadingZeroInts(result);
	            }
	            if ((exponent >>>= 1) != 0) {
	                baseToPow2 = squareToLen(baseToPow2, baseToPow2.length, null);
	                baseToPow2 = trustedStripLeadingZeroInts(baseToPow2);
	            }
	        }
	        return new BigInteger(result, newSign);
	    }

	    /**
	     * Returns a BigInteger whose value is the greatest common divisor of
	     * {@code abs(this)} and {@code abs(val)}.  Returns 0 if
	     * {@code this==0 && val==0}.
	     *
	     * @param  val value with which the GCD is to be computed.
	     * @return {@code GCD(abs(this), abs(val))}
	     */
	    public BigInteger gcd(BigInteger val) {
	        if (val.signum == 0)
	            return this.abs();
	        else if (this.signum == 0)
	            return val.abs();

	        MutableBigInteger a = new MutableBigInteger(this);
	        MutableBigInteger b = new MutableBigInteger(val);

	        MutableBigInteger result = a.hybridGCD(b);

	        return result.toBigInteger(1);
	    }

	    /**
	     * Package private method to return bit length for an integer.
	     */
	    static int bitLengthForInt(int n) {
	        return 32 - Integer.numberOfLeadingZeros(n);
	    }

	    // shifts a up to len right n bits assumes no leading zeros, 0<n<32
	    static void primitiveRightShift(int[] a, int len, int n) {
	        int n2 = 32 - n;
	        for (int i=len-1, c=a[i]; i>0; i--) {
	            int b = c;
	            c = a[i-1];
	            a[i] = (c << n2) | (b >>> n);
	        }
	        a[0] >>>= n;
	    }

	    // shifts a up to len left n bits assumes no leading zeros, 0<=n<32
	    static void primitiveLeftShift(int[] a, int len, int n) {
	        if (len == 0 || n == 0)
	            return;

	        int n2 = 32 - n;
	        for (int i=0, c=a[i], m=i+len-1; i<m; i++) {
	            int b = c;
	            c = a[i+1];
	            a[i] = (b << n) | (c >>> n2);
	        }
	        a[len-1] <<= n;
	    }

	    /**
	     * Returns a BigInteger whose value is the absolute value of this
	     * BigInteger.
	     *
	     * @return {@code abs(this)}
	     */
	    public BigInteger abs() {
	        return (signum >= 0 ? this : this.negate());
	    }

	    /**
	     * Returns a BigInteger whose value is {@code (-this)}.
	     *
	     * @return {@code -this}
	     */
	    public BigInteger negate() {
	        return new BigInteger(this.mag, -this.signum);
	    }

	    /**
	     * Returns the signum function of this BigInteger.
	     *
	     * @return -1, 0 or 1 as the value of this BigInteger is negative, zero or
	     *         positive.
	     */
	    public int signum() {
	        return this.signum;
	    }

	    // Modular Arithmetic Operations

	    /**
	     * Returns a BigInteger whose value is {@code (this mod m}).  This method
	     * differs from {@code remainder} in that it always returns a
	     * <i>non-negative</i> BigInteger.
	     *
	     * @param  m the modulus.
	     * @return {@code this mod m}
	     * @throws ArithmeticException {@code m} &le; 0
	     * @see    #remainder
	     */
	    public BigInteger mod(BigInteger m) {
	        if (m.signum <= 0)
	            throw new ArithmeticException("BigInteger: modulus not positive");

	        BigInteger result = this.remainder(m);
	        return (result.signum >= 0 ? result : result.add(m));
	    }


	    static int[] bnExpModThreshTable = {7, 25, 81, 241, 673, 1793,
	                                                Integer.MAX_VALUE}; // Sentinel

	    /**
	     * Multiply an array by one word k and add to result, return the carry
	     */
	    static int mulAdd(int[] out, int[] in, int offset, int len, int k) {
	        long kLong = k & LONG_MASK;
	        long carry = 0;

	        offset = out.length-offset - 1;
	        for (int j=len-1; j >= 0; j--) {
	            long product = (in[j] & LONG_MASK) * kLong +
	                           (out[offset] & LONG_MASK) + carry;
	            out[offset--] = (int)product;
	            carry = product >>> 32;
	        }
	        return (int)carry;
	    }

	    /**
	     * Add one word to the number a mlen words into a. Return the resulting
	     * carry.
	     */
	    static int addOne(int[] a, int offset, int mlen, int carry) {
	        offset = a.length-1-mlen-offset;
	        long t = (a[offset] & LONG_MASK) + (carry & LONG_MASK);

	        a[offset] = (int)t;
	        if ((t >>> 32) == 0)
	            return 0;
	        while (--mlen >= 0) {
	            if (--offset < 0) { // Carry out of number
	                return 1;
	            } else {
	                a[offset]++;
	                if (a[offset] != 0)
	                    return 0;
	            }
	        }
	        return 1;
	    }

	    

	    /**
	     * Returns a BigInteger whose value is {@code (this}<sup>-1</sup> {@code mod m)}.
	     *
	     * @param  m the modulus.
	     * @return {@code this}<sup>-1</sup> {@code mod m}.
	     * @throws ArithmeticException {@code  m} &le; 0, or this BigInteger
	     *         has no multiplicative inverse mod m (that is, this BigInteger
	     *         is not <i>relatively prime</i> to m).
	     */

	    // Shift Operations

	    /**
	     * Returns a BigInteger whose value is {@code (this << n)}.
	     * The shift distance, {@code n}, may be negative, in which case
	     * this method performs a right shift.
	     * (Computes <tt>floor(this * 2<sup>n</sup>)</tt>.)
	     *
	     * @param  n shift distance, in bits.
	     * @return {@code this << n}
	     * @throws ArithmeticException if the shift distance is {@code
	     *         Integer.MIN_VALUE}.
	     * @see #shiftRight
	     */
	    public BigInteger shiftLeft(int n) {
	        if (signum == 0)
	            return ZERO;
	        if (n==0)
	            return this;
	        if (n<0) {
	            if (n == Integer.MIN_VALUE) {
	                throw new ArithmeticException("Shift distance of Integer.MIN_VALUE not supported.");
	            } else {
	                return shiftRight(-n);
	            }
	        }

	        int nInts = n >>> 5;
	        int nBits = n & 0x1f;
	        int magLen = mag.length;
	        int newMag[] = null;

	        if (nBits == 0) {
	            newMag = new int[magLen + nInts];
	            for (int i=0; i<magLen; i++)
	                newMag[i] = mag[i];
	        } else {
	            int i = 0;
	            int nBits2 = 32 - nBits;
	            int highBits = mag[0] >>> nBits2;
	            if (highBits != 0) {
	                newMag = new int[magLen + nInts + 1];
	                newMag[i++] = highBits;
	            } else {
	                newMag = new int[magLen + nInts];
	            }
	            int j=0;
	            while (j < magLen-1)
	                newMag[i++] = mag[j++] << nBits | mag[j] >>> nBits2;
	            newMag[i] = mag[j] << nBits;
	        }

	        return new BigInteger(newMag, signum);
	    }

	    /**
	     * Returns a BigInteger whose value is {@code (this >> n)}.  Sign
	     * extension is performed.  The shift distance, {@code n}, may be
	     * negative, in which case this method performs a left shift.
	     * (Computes <tt>floor(this / 2<sup>n</sup>)</tt>.)
	     *
	     * @param  n shift distance, in bits.
	     * @return {@code this >> n}
	     * @throws ArithmeticException if the shift distance is {@code
	     *         Integer.MIN_VALUE}.
	     * @see #shiftLeft
	     */
	    public BigInteger shiftRight(int n) {
	        if (n==0)
	            return this;
	        if (n<0) {
	            if (n == Integer.MIN_VALUE) {
	                throw new ArithmeticException("Shift distance of Integer.MIN_VALUE not supported.");
	            } else {
	                return shiftLeft(-n);
	            }
	        }

	        int nInts = n >>> 5;
	        int nBits = n & 0x1f;
	        int magLen = mag.length;
	        int newMag[] = null;

	        // Special case: entire contents shifted off the end
	        if (nInts >= magLen)
	            return (signum >= 0 ? ZERO : negConst[1]);

	        if (nBits == 0) {
	            int newMagLen = magLen - nInts;
	            newMag = new int[newMagLen];
	            for (int i=0; i<newMagLen; i++)
	                newMag[i] = mag[i];
	        } else {
	            int i = 0;
	            int highBits = mag[0] >>> nBits;
	            if (highBits != 0) {
	                newMag = new int[magLen - nInts];
	                newMag[i++] = highBits;
	            } else {
	                newMag = new int[magLen - nInts -1];
	            }

	            int nBits2 = 32 - nBits;
	            int j=0;
	            while (j < magLen - nInts - 1)
	                newMag[i++] = (mag[j++] << nBits2) | (mag[j] >>> nBits);
	        }

	        if (signum < 0) {
	            // Find out whether any one-bits were shifted off the end.
	            boolean onesLost = false;
	            for (int i=magLen-1, j=magLen-nInts; i>=j && !onesLost; i--)
	                onesLost = (mag[i] != 0);
	            if (!onesLost && nBits != 0)
	                onesLost = (mag[magLen - nInts - 1] << (32 - nBits) != 0);

	            if (onesLost)
	                newMag = javaIncrement(newMag);
	        }

	        return new BigInteger(newMag, signum);
	    }

	    int[] javaIncrement(int[] val) {
	        int lastSum = 0;
	        for (int i=val.length-1;  i >= 0 && lastSum == 0; i--)
	            lastSum = (val[i] += 1);
	        if (lastSum == 0) {
	            val = new int[val.length+1];
	            val[0] = 1;
	        }
	        return val;
	    }

	    // Bitwise Operations

	    /**
	     * Returns a BigInteger whose value is {@code (this & val)}.  (This
	     * method returns a negative BigInteger if and only if this and val are
	     * both negative.)
	     *
	     * @param val value to be AND'ed with this BigInteger.
	     * @return {@code this & val}
	     */
	    public BigInteger and(BigInteger val) {
	        int[] result = new int[Math.max(intLength(), val.intLength())];
	        for (int i=0; i<result.length; i++)
	            result[i] = (getInt(result.length-i-1)
	                         & val.getInt(result.length-i-1));

	        return valueOf(result);
	    }

	    /**
	     * Returns a BigInteger whose value is {@code (this | val)}.  (This method
	     * returns a negative BigInteger if and only if either this or val is
	     * negative.)
	     *
	     * @param val value to be OR'ed with this BigInteger.
	     * @return {@code this | val}
	     */
	    public BigInteger or(BigInteger val) {
	        int[] result = new int[Math.max(intLength(), val.intLength())];
	        for (int i=0; i<result.length; i++)
	            result[i] = (getInt(result.length-i-1)
	                         | val.getInt(result.length-i-1));

	        return valueOf(result);
	    }

	    /**
	     * Returns a BigInteger whose value is {@code (this ^ val)}.  (This method
	     * returns a negative BigInteger if and only if exactly one of this and
	     * val are negative.)
	     *
	     * @param val value to be XOR'ed with this BigInteger.
	     * @return {@code this ^ val}
	     */
	    public BigInteger xor(BigInteger val) {
	        int[] result = new int[Math.max(intLength(), val.intLength())];
	        for (int i=0; i<result.length; i++)
	            result[i] = (getInt(result.length-i-1)
	                         ^ val.getInt(result.length-i-1));

	        return valueOf(result);
	    }

	    /**
	     * Returns a BigInteger whose value is {@code (~this)}.  (This method
	     * returns a negative value if and only if this BigInteger is
	     * non-negative.)
	     *
	     * @return {@code ~this}
	     */
	    public BigInteger not() {
	        int[] result = new int[intLength()];
	        for (int i=0; i<result.length; i++)
	            result[i] = ~getInt(result.length-i-1);

	        return valueOf(result);
	    }

	    /**
	     * Returns a BigInteger whose value is {@code (this & ~val)}.  This
	     * method, which is equivalent to {@code and(val.not())}, is provided as
	     * a convenience for masking operations.  (This method returns a negative
	     * BigInteger if and only if {@code this} is negative and {@code val} is
	     * positive.)
	     *
	     * @param val value to be complemented and AND'ed with this BigInteger.
	     * @return {@code this & ~val}
	     */
	    public BigInteger andNot(BigInteger val) {
	        int[] result = new int[Math.max(intLength(), val.intLength())];
	        for (int i=0; i<result.length; i++)
	            result[i] = (getInt(result.length-i-1)
	                         & ~val.getInt(result.length-i-1));

	        return valueOf(result);
	    }


	    // Single Bit Operations

	    /**
	     * Returns {@code true} if and only if the designated bit is set.
	     * (Computes {@code ((this & (1<<n)) != 0)}.)
	     *
	     * @param  n index of bit to test.
	     * @return {@code true} if and only if the designated bit is set.
	     * @throws ArithmeticException {@code n} is negative.
	     */
	    public boolean testBit(int n) {
	        if (n<0)
	            throw new ArithmeticException("Negative bit address");

	        return (getInt(n >>> 5) & (1 << (n & 31))) != 0;
	    }

	    /**
	     * Returns a BigInteger whose value is equivalent to this BigInteger
	     * with the designated bit set.  (Computes {@code (this | (1<<n))}.)
	     *
	     * @param  n index of bit to set.
	     * @return {@code this | (1<<n)}
	     * @throws ArithmeticException {@code n} is negative.
	     */
	    public BigInteger setBit(int n) {
	        if (n<0)
	            throw new ArithmeticException("Negative bit address");

	        int intNum = n >>> 5;
	        int[] result = new int[Math.max(intLength(), intNum+2)];

	        for (int i=0; i<result.length; i++)
	            result[result.length-i-1] = getInt(i);

	        result[result.length-intNum-1] |= (1 << (n & 31));

	        return valueOf(result);
	    }

	    /**
	     * Returns a BigInteger whose value is equivalent to this BigInteger
	     * with the designated bit cleared.
	     * (Computes {@code (this & ~(1<<n))}.)
	     *
	     * @param  n index of bit to clear.
	     * @return {@code this & ~(1<<n)}
	     * @throws ArithmeticException {@code n} is negative.
	     */
	    public BigInteger clearBit(int n) {
	        if (n<0)
	            throw new ArithmeticException("Negative bit address");

	        int intNum = n >>> 5;
	        int[] result = new int[Math.max(intLength(), ((n + 1) >>> 5) + 1)];

	        for (int i=0; i<result.length; i++)
	            result[result.length-i-1] = getInt(i);

	        result[result.length-intNum-1] &= ~(1 << (n & 31));

	        return valueOf(result);
	    }

	    /**
	     * Returns a BigInteger whose value is equivalent to this BigInteger
	     * with the designated bit flipped.
	     * (Computes {@code (this ^ (1<<n))}.)
	     *
	     * @param  n index of bit to flip.
	     * @return {@code this ^ (1<<n)}
	     * @throws ArithmeticException {@code n} is negative.
	     */
	    public BigInteger flipBit(int n) {
	        if (n<0)
	            throw new ArithmeticException("Negative bit address");

	        int intNum = n >>> 5;
	        int[] result = new int[Math.max(intLength(), intNum+2)];

	        for (int i=0; i<result.length; i++)
	            result[result.length-i-1] = getInt(i);

	        result[result.length-intNum-1] ^= (1 << (n & 31));

	        return valueOf(result);
	    }

	    /**
	     * Returns the index of the rightmost (lowest-order) one bit in this
	     * BigInteger (the number of zero bits to the right of the rightmost
	     * one bit).  Returns -1 if this BigInteger contains no one bits.
	     * (Computes {@code (this==0? -1 : log2(this & -this))}.)
	     *
	     * @return index of the rightmost one bit in this BigInteger.
	     */
	    public int getLowestSetBit() {
	        int lsb = lowestSetBit - 2;
	        if (lsb == -2) {  // lowestSetBit not initialized yet
	            lsb = 0;
	            if (signum == 0) {
	                lsb -= 1;
	            } else {
	                // Search for lowest order nonzero int
	                int i,b;
	                for (i=0; (b = getInt(i))==0; i++)
	                    ;
	                lsb += (i << 5) + Integer.numberOfTrailingZeros(b);
	            }
	            lowestSetBit = lsb + 2;
	        }
	        return lsb;
	    }


	    // Miscellaneous Bit Operations

	    /**
	     * Returns the number of bits in the minimal two's-complement
	     * representation of this BigInteger, <i>excluding</i> a sign bit.
	     * For positive BigIntegers, this is equivalent to the number of bits in
	     * the ordinary binary representation.  (Computes
	     * {@code (ceil(log2(this < 0 ? -this : this+1)))}.)
	     *
	     * @return number of bits in the minimal two's-complement
	     *         representation of this BigInteger, <i>excluding</i> a sign bit.
	     */
	    public int bitLength() {
	        int n = bitLength - 1;
	        if (n == -1) { // bitLength not initialized yet
	            int[] m = mag;
	            int len = m.length;
	            if (len == 0) {
	                n = 0; // offset by one to initialize
	            }  else {
	                // Calculate the bit length of the magnitude
	                int magBitLength = ((len - 1) << 5) + bitLengthForInt(mag[0]);
	                 if (signum < 0) {
	                     // Check if magnitude is a power of two
	                     boolean pow2 = (Integer.bitCount(mag[0]) == 1);
	                     for(int i=1; i< len && pow2; i++)
	                         pow2 = (mag[i] == 0);

	                     n = (pow2 ? magBitLength -1 : magBitLength);
	                 } else {
	                     n = magBitLength;
	                 }
	            }
	            bitLength = n + 1;
	        }
	        return n;
	    }

	    /**
	     * Returns the number of bits in the two's complement representation
	     * of this BigInteger that differ from its sign bit.  This method is
	     * useful when implementing bit-vector style sets atop BigIntegers.
	     *
	     * @return number of bits in the two's complement representation
	     *         of this BigInteger that differ from its sign bit.
	     */
	    public int bitCount() {
	        int bc = bitCount - 1;
	        if (bc == -1) {  // bitCount not initialized yet
	            bc = 0;      // offset by one to initialize
	            // Count the bits in the magnitude
	            for (int i=0; i<mag.length; i++)
	                bc += Integer.bitCount(mag[i]);
	            if (signum < 0) {
	                // Count the trailing zeros in the magnitude
	                int magTrailingZeroCount = 0, j;
	                for (j=mag.length-1; mag[j]==0; j--)
	                    magTrailingZeroCount += 32;
	                magTrailingZeroCount += Integer.numberOfTrailingZeros(mag[j]);
	                bc += magTrailingZeroCount - 1;
	            }
	            bitCount = bc + 1;
	        }
	        return bc;
	    }


	    // Comparison Operations

	    /**
	     * Compares this BigInteger with the specified BigInteger.  This
	     * method is provided in preference to individual methods for each
	     * of the six boolean comparison operators ({@literal <}, ==,
	     * {@literal >}, {@literal >=}, !=, {@literal <=}).  The suggested
	     * idiom for performing these comparisons is: {@code
	     * (x.compareTo(y)} &lt;<i>op</i>&gt; {@code 0)}, where
	     * &lt;<i>op</i>&gt; is one of the six comparison operators.
	     *
	     * @param  val BigInteger to which this BigInteger is to be compared.
	     * @return -1, 0 or 1 as this BigInteger is numerically less than, equal
	     *         to, or greater than {@code val}.
	     */
	    public int compareTo(BigInteger val) {
	        if (signum == val.signum) {
	            switch (signum) {
	            case 1:
	                return compareMagnitude(val);
	            case -1:
	                return val.compareMagnitude(this);
	            default:
	                return 0;
	            }
	        }
	        return signum > val.signum ? 1 : -1;
	    }

	    /**
	     * Compares the magnitude array of this BigInteger with the specified
	     * BigInteger's. This is the version of compareTo ignoring sign.
	     *
	     * @param val BigInteger whose magnitude array to be compared.
	     * @return -1, 0 or 1 as this magnitude array is less than, equal to or
	     *         greater than the magnitude aray for the specified BigInteger's.
	     */
	    final int compareMagnitude(BigInteger val) {
	        int[] m1 = mag;
	        int len1 = m1.length;
	        int[] m2 = val.mag;
	        int len2 = m2.length;
	        if (len1 < len2)
	            return -1;
	        if (len1 > len2)
	            return 1;
	        for (int i = 0; i < len1; i++) {
	            int a = m1[i];
	            int b = m2[i];
	            if (a != b)
	                return ((a & LONG_MASK) < (b & LONG_MASK)) ? -1 : 1;
	        }
	        return 0;
	    }

	    /**
	     * Compares this BigInteger with the specified Object for equality.
	     *
	     * @param  x Object to which this BigInteger is to be compared.
	     * @return {@code true} if and only if the specified Object is a
	     *         BigInteger whose value is numerically equal to this BigInteger.
	     */
	    public boolean equals(Object x) {
	        // This test is just an optimization, which may or may not help
	        if (x == this)
	            return true;

	        if (!(x instanceof BigInteger))
	            return false;

	        BigInteger xInt = (BigInteger) x;
	        if (xInt.signum != signum)
	            return false;

	        int[] m = mag;
	        int len = m.length;
	        int[] xm = xInt.mag;
	        if (len != xm.length)
	            return false;

	        for (int i = 0; i < len; i++)
	            if (xm[i] != m[i])
	                return false;

	        return true;
	    }

	    /**
	     * Returns the minimum of this BigInteger and {@code val}.
	     *
	     * @param  val value with which the minimum is to be computed.
	     * @return the BigInteger whose value is the lesser of this BigInteger and
	     *         {@code val}.  If they are equal, either may be returned.
	     */
	    public BigInteger min(BigInteger val) {
	        return (compareTo(val)<0 ? this : val);
	    }

	    /**
	     * Returns the maximum of this BigInteger and {@code val}.
	     *
	     * @param  val value with which the maximum is to be computed.
	     * @return the BigInteger whose value is the greater of this and
	     *         {@code val}.  If they are equal, either may be returned.
	     */
	    public BigInteger max(BigInteger val) {
	        return (compareTo(val)>0 ? this : val);
	    }


	    // Hash Function

	    /**
	     * Returns the hash code for this BigInteger.
	     *
	     * @return hash code for this BigInteger.
	     */
	    public int hashCode() {
	        int hashCode = 0;

	        for (int i=0; i<mag.length; i++)
	            hashCode = (int)(31*hashCode + (mag[i] & LONG_MASK));

	        return hashCode * signum;
	    }

	    /**
	     * Returns the String representation of this BigInteger in the
	     * given radix.  If the radix is outside the range from {@link
	     * Character#MIN_RADIX} to {@link Character#MAX_RADIX} inclusive,
	     * it will default to 10 (as is the case for
	     * {@code Integer.toString}).  The digit-to-character mapping
	     * provided by {@code Character.forDigit} is used, and a minus
	     * sign is prepended if appropriate.  (This representation is
	     * compatible with the {@link #BigInteger(String, int) (String,
	     * int)} constructor.)
	     *
	     * @param  radix  radix of the String representation.
	     * @return String representation of this BigInteger in the given radix.
	     * @see    Integer#toString
	     * @see    Character#forDigit
	     * @see    #BigInteger(java.lang.String, int)
	     */
	    public String toString(int radix) {
	        if (signum == 0)
	            return "0";
	        if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX)
	            radix = 10;

	        // Compute upper bound on number of digit groups and allocate space
	        int maxNumDigitGroups = (4*mag.length + 6)/7;
	        String digitGroup[] = new String[maxNumDigitGroups];

	        // Translate number to string, a digit group at a time
	        BigInteger tmp = this.abs();
	        int numGroups = 0;
	        while (tmp.signum != 0) {
	            BigInteger d = longRadix[radix];

	            MutableBigInteger q = new MutableBigInteger(),
	                              a = new MutableBigInteger(tmp.mag),
	                              b = new MutableBigInteger(d.mag);
	            MutableBigInteger r = a.divide(b, q);
	            BigInteger q2 = q.toBigInteger(tmp.signum * d.signum);
	            BigInteger r2 = r.toBigInteger(tmp.signum * d.signum);

	            digitGroup[numGroups++] = Long.toString(r2.longValue(), radix);
	            tmp = q2;
	        }

	        // Put sign (if any) and first digit group into result buffer
	        StringBuilder buf = new StringBuilder(numGroups*digitsPerLong[radix]+1);
	        if (signum<0)
	            buf.append('-');
	        buf.append(digitGroup[numGroups-1]);

	        // Append remaining digit groups padded with leading zeros
	        for (int i=numGroups-2; i>=0; i--) {
	            // Prepend (any) leading zeros for this digit group
	            int numLeadingZeros = digitsPerLong[radix]-digitGroup[i].length();
	            if (numLeadingZeros != 0)
	                buf.append(zeros[numLeadingZeros]);
	            buf.append(digitGroup[i]);
	        }
	        return buf.toString();
	    }

	    /* zero[i] is a string of i consecutive zeros. */
	    private static String zeros[] = new String[64];
	    static {
	        zeros[63] =
	            "000000000000000000000000000000000000000000000000000000000000000";
	        for (int i=0; i<63; i++)
	            zeros[i] = zeros[63].substring(0, i);
	    }

	    /**
	     * Returns the decimal String representation of this BigInteger.
	     * The digit-to-character mapping provided by
	     * {@code Character.forDigit} is used, and a minus sign is
	     * prepended if appropriate.  (This representation is compatible
	     * with the {@link #BigInteger(String) (String)} constructor, and
	     * allows for String concatenation with Java's + operator.)
	     *
	     * @return decimal String representation of this BigInteger.
	     * @see    Character#forDigit
	     * @see    #BigInteger(java.lang.String)
	     */
	    public String toString() {
	        return toString(10);
	    }

	    /**
	     * Returns a byte array containing the two's-complement
	     * representation of this BigInteger.  The byte array will be in
	     * <i>big-endian</i> byte-order: the most significant byte is in
	     * the zeroth element.  The array will contain the minimum number
	     * of bytes required to represent this BigInteger, including at
	     * least one sign bit, which is {@code (ceil((this.bitLength() +
	     * 1)/8))}.  (This representation is compatible with the
	     * {@link #BigInteger(byte[]) (byte[])} constructor.)
	     *
	     * @return a byte array containing the two's-complement representation of
	     *         this BigInteger.
	     * @see    #BigInteger(byte[])
	     */
	    public byte[] toByteArray() {
	        int byteLen = bitLength()/8 + 1;
	        byte[] byteArray = new byte[byteLen];

	        for (int i=byteLen-1, bytesCopied=4, nextInt=0, intIndex=0; i>=0; i--) {
	            if (bytesCopied == 4) {
	                nextInt = getInt(intIndex++);
	                bytesCopied = 1;
	            } else {
	                nextInt >>>= 8;
	                bytesCopied++;
	            }
	            byteArray[i] = (byte)nextInt;
	        }
	        return byteArray;
	    }

	    /**
	     * Converts this BigInteger to an {@code int}.  This
	     * conversion is analogous to a
	     * <i>narrowing primitive conversion</i> from {@code long} to
	     * {@code int} as defined in section 5.1.3 of
	     * <cite>The Java&trade; Language Specification</cite>:
	     * if this BigInteger is too big to fit in an
	     * {@code int}, only the low-order 32 bits are returned.
	     * Note that this conversion can lose information about the
	     * overall magnitude of the BigInteger value as well as return a
	     * result with the opposite sign.
	     *
	     * @return this BigInteger converted to an {@code int}.
	     */
	    public int intValue() {
	        int result = 0;
	        result = getInt(0);
	        return result;
	    }

	    /**
	     * Converts this BigInteger to a {@code long}.  This
	     * conversion is analogous to a
	     * <i>narrowing primitive conversion</i> from {@code long} to
	     * {@code int} as defined in section 5.1.3 of
	     * <cite>The Java&trade; Language Specification</cite>:
	     * if this BigInteger is too big to fit in a
	     * {@code long}, only the low-order 64 bits are returned.
	     * Note that this conversion can lose information about the
	     * overall magnitude of the BigInteger value as well as return a
	     * result with the opposite sign.
	     *
	     * @return this BigInteger converted to a {@code long}.
	     */
	    public long longValue() {
	        long result = 0;

	        for (int i=1; i>=0; i--)
	            result = (result << 32) + (getInt(i) & LONG_MASK);
	        return result;
	    }

	    /**
	     * Converts this BigInteger to a {@code float}.  This
	     * conversion is similar to the
	     * <i>narrowing primitive conversion</i> from {@code double} to
	     * {@code float} as defined in section 5.1.3 of
	     * <cite>The Java&trade; Language Specification</cite>:
	     * if this BigInteger has too great a magnitude
	     * to represent as a {@code float}, it will be converted to
	     * {@link Float#NEGATIVE_INFINITY} or {@link
	     * Float#POSITIVE_INFINITY} as appropriate.  Note that even when
	     * the return value is finite, this conversion can lose
	     * information about the precision of the BigInteger value.
	     *
	     * @return this BigInteger converted to a {@code float}.
	     */
	    public float floatValue() {
	        // Somewhat inefficient, but guaranteed to work.
	        return Float.parseFloat(this.toString());
	    }

	    /**
	     * Converts this BigInteger to a {@code double}.  This
	     * conversion is similar to the
	     * <i>narrowing primitive conversion</i> from {@code double} to
	     * {@code float} as defined in section 5.1.3 of
	     * <cite>The Java&trade; Language Specification</cite>:
	     * if this BigInteger has too great a magnitude
	     * to represent as a {@code double}, it will be converted to
	     * {@link Double#NEGATIVE_INFINITY} or {@link
	     * Double#POSITIVE_INFINITY} as appropriate.  Note that even when
	     * the return value is finite, this conversion can lose
	     * information about the precision of the BigInteger value.
	     *
	     * @return this BigInteger converted to a {@code double}.
	     */
	    public double doubleValue() {
	        // Somewhat inefficient, but guaranteed to work.
	        return Double.parseDouble(this.toString());
	    }

	    /**
	     * Returns the input array stripped of any leading zero bytes.
	     * Since the source is trusted the copying may be skipped.
	     */
	    private static int[] trustedStripLeadingZeroInts(int val[]) {
	        int vlen = val.length;
	        int keep;

	        // Find first nonzero byte
	        for (keep = 0; keep < vlen && val[keep] == 0; keep++)
	            ;
	        return keep == 0 ? val : java.util.Arrays.copyOfRange(val, keep, vlen);
	    }

	    /**
	     * Returns a copy of the input array stripped of any leading zero bytes.
	     */
	    private static int[] stripLeadingZeroBytes(byte a[]) {
	        int byteLength = a.length;
	        int keep;

	        // Find first nonzero byte
	        for (keep = 0; keep < byteLength && a[keep]==0; keep++)
	            ;

	        // Allocate new array and copy relevant part of input array
	        int intLength = ((byteLength - keep) + 3) >>> 2;
	        int[] result = new int[intLength];
	        int b = byteLength - 1;
	        for (int i = intLength-1; i >= 0; i--) {
	            result[i] = a[b--] & 0xff;
	            int bytesRemaining = b - keep + 1;
	            int bytesToTransfer = Math.min(3, bytesRemaining);
	            for (int j=8; j <= (bytesToTransfer << 3); j += 8)
	                result[i] |= ((a[b--] & 0xff) << j);
	        }
	        return result;
	    }

	    /**
	     * Takes an array a representing a negative 2's-complement number and
	     * returns the minimal (no leading zero bytes) unsigned whose value is -a.
	     */
	    private static int[] makePositive(byte a[]) {
	        int keep, k;
	        int byteLength = a.length;

	        // Find first non-sign (0xff) byte of input
	        for (keep=0; keep<byteLength && a[keep]==-1; keep++)
	            ;


	        /* Allocate output array.  If all non-sign bytes are 0x00, we must
	         * allocate space for one extra output byte. */
	        for (k=keep; k<byteLength && a[k]==0; k++)
	            ;

	        int extraByte = (k==byteLength) ? 1 : 0;
	        int intLength = ((byteLength - keep + extraByte) + 3)/4;
	        int result[] = new int[intLength];

	        /* Copy one's complement of input into output, leaving extra
	         * byte (if it exists) == 0x00 */
	        int b = byteLength - 1;
	        for (int i = intLength-1; i >= 0; i--) {
	            result[i] = a[b--] & 0xff;
	            int numBytesToTransfer = Math.min(3, b-keep+1);
	            if (numBytesToTransfer < 0)
	                numBytesToTransfer = 0;
	            for (int j=8; j <= 8*numBytesToTransfer; j += 8)
	                result[i] |= ((a[b--] & 0xff) << j);

	            // Mask indicates which bits must be complemented
	            int mask = -1 >>> (8*(3-numBytesToTransfer));
	            result[i] = ~result[i] & mask;
	        }

	        // Add one to one's complement to generate two's complement
	        for (int i=result.length-1; i>=0; i--) {
	            result[i] = (int)((result[i] & LONG_MASK) + 1);
	            if (result[i] != 0)
	                break;
	        }

	        return result;
	    }

	    /**
	     * Takes an array a representing a negative 2's-complement number and
	     * returns the minimal (no leading zero ints) unsigned whose value is -a.
	     */
	    private static int[] makePositive(int a[]) {
	        int keep, j;

	        // Find first non-sign (0xffffffff) int of input
	        for (keep=0; keep<a.length && a[keep]==-1; keep++)
	            ;

	        /* Allocate output array.  If all non-sign ints are 0x00, we must
	         * allocate space for one extra output int. */
	        for (j=keep; j<a.length && a[j]==0; j++)
	            ;
	        int extraInt = (j==a.length ? 1 : 0);
	        int result[] = new int[a.length - keep + extraInt];

	        /* Copy one's complement of input into output, leaving extra
	         * int (if it exists) == 0x00 */
	        for (int i = keep; i<a.length; i++)
	            result[i - keep + extraInt] = ~a[i];

	        // Add one to one's complement to generate two's complement
	        for (int i=result.length-1; ++result[i]==0; i--)
	            ;

	        return result;
	    }

	    /*
	     * The following two arrays are used for fast String conversions.  Both
	     * are indexed by radix.  The first is the number of digits of the given
	     * radix that can fit in a Java long without "going negative", i.e., the
	     * highest integer n such that radix**n < 2**63.  The second is the
	     * "long radix" that tears each number into "long digits", each of which
	     * consists of the number of digits in the corresponding element in
	     * digitsPerLong (longRadix[i] = i**digitPerLong[i]).  Both arrays have
	     * nonsense values in their 0 and 1 elements, as radixes 0 and 1 are not
	     * used.
	     */
	    private static int digitsPerLong[] = {0, 0,
	        62, 39, 31, 27, 24, 22, 20, 19, 18, 18, 17, 17, 16, 16, 15, 15, 15, 14,
	        14, 14, 14, 13, 13, 13, 13, 13, 13, 12, 12, 12, 12, 12, 12, 12, 12};

	    private static BigInteger longRadix[] = {null, null,
	        valueOf(0x4000000000000000L), valueOf(0x383d9170b85ff80bL),
	        valueOf(0x4000000000000000L), valueOf(0x6765c793fa10079dL),
	        valueOf(0x41c21cb8e1000000L), valueOf(0x3642798750226111L),
	        valueOf(0x1000000000000000L), valueOf(0x12bf307ae81ffd59L),
	        valueOf( 0xde0b6b3a7640000L), valueOf(0x4d28cb56c33fa539L),
	        valueOf(0x1eca170c00000000L), valueOf(0x780c7372621bd74dL),
	        valueOf(0x1e39a5057d810000L), valueOf(0x5b27ac993df97701L),
	        valueOf(0x1000000000000000L), valueOf(0x27b95e997e21d9f1L),
	        valueOf(0x5da0e1e53c5c8000L), valueOf( 0xb16a458ef403f19L),
	        valueOf(0x16bcc41e90000000L), valueOf(0x2d04b7fdd9c0ef49L),
	        valueOf(0x5658597bcaa24000L), valueOf( 0x6feb266931a75b7L),
	        valueOf( 0xc29e98000000000L), valueOf(0x14adf4b7320334b9L),
	        valueOf(0x226ed36478bfa000L), valueOf(0x383d9170b85ff80bL),
	        valueOf(0x5a3c23e39c000000L), valueOf( 0x4e900abb53e6b71L),
	        valueOf( 0x7600ec618141000L), valueOf( 0xaee5720ee830681L),
	        valueOf(0x1000000000000000L), valueOf(0x172588ad4f5f0981L),
	        valueOf(0x211e44f7d02c1000L), valueOf(0x2ee56725f06e5c71L),
	        valueOf(0x41c21cb8e1000000L)};

	    /*
	     * These two arrays are the integer analogue of above.
	     */
	    private static int digitsPerInt[] = {0, 0, 30, 19, 15, 13, 11,
	        11, 10, 9, 9, 8, 8, 8, 8, 7, 7, 7, 7, 7, 7, 7, 6, 6, 6, 6,
	        6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 5};

	    private static int intRadix[] = {0, 0,
	        0x40000000, 0x4546b3db, 0x40000000, 0x48c27395, 0x159fd800,
	        0x75db9c97, 0x40000000, 0x17179149, 0x3b9aca00, 0xcc6db61,
	        0x19a10000, 0x309f1021, 0x57f6c100, 0xa2f1b6f,  0x10000000,
	        0x18754571, 0x247dbc80, 0x3547667b, 0x4c4b4000, 0x6b5a6e1d,
	        0x6c20a40,  0x8d2d931,  0xb640000,  0xe8d4a51,  0x1269ae40,
	        0x17179149, 0x1cb91000, 0x23744899, 0x2b73a840, 0x34e63b41,
	        0x40000000, 0x4cfa3cc1, 0x5c13d840, 0x6d91b519, 0x39aa400
	    };

	    /**
	     * These routines provide access to the two's complement representation
	     * of BigIntegers.
	     */

	    /**
	     * Returns the length of the two's complement representation in ints,
	     * including space for at least one sign bit.
	     */
	    private int intLength() {
	        return (bitLength() >>> 5) + 1;
	    }

	    /* Returns an int of sign bits */
	    private int signInt() {
	        return signum < 0 ? -1 : 0;
	    }

	    /**
	     * Returns the specified int of the little-endian two's complement
	     * representation (int 0 is the least significant).  The int number can
	     * be arbitrarily high (values are logically preceded by infinitely many
	     * sign ints).
	     */
	    private int getInt(int n) {
	        if (n < 0)
	            return 0;
	        if (n >= mag.length)
	            return signInt();

	        int magInt = mag[mag.length-n-1];

	        return (signum >= 0 ? magInt :
	                (n <= firstNonzeroIntNum() ? -magInt : ~magInt));
	    }

	    /**
	     * Returns the index of the int that contains the first nonzero int in the
	     * little-endian binary representation of the magnitude (int 0 is the
	     * least significant). If the magnitude is zero, return value is undefined.
	     */
	     private int firstNonzeroIntNum() {
	         int fn = firstNonzeroIntNum - 2;
	         if (fn == -2) { // firstNonzeroIntNum not initialized yet
	             fn = 0;

	             // Search for the first nonzero int
	             int i;
	             int mlen = mag.length;
	             for (i = mlen - 1; i >= 0 && mag[i] == 0; i--)
	                 ;
	             fn = mlen - i - 1;
	             firstNonzeroIntNum = fn + 2; // offset by two to initialize
	         }
	         return fn;
	     }

	    /** use serialVersionUID from JDK 1.1. for interoperability */
	    private static final long serialVersionUID = -8287574255936472291L;



	    /**
	     * Save the {@code BigInteger} instance to a stream.
	     * The magnitude of a BigInteger is serialized as a byte array for
	     * historical reasons.
	     *
	     * @serialData two necessary fields are written as well as obsolete
	     *             fields for compatibility with older versions.
	     */
	}

	
	
	public static void main(String[] args)
	{
		BigInteger a = new BigInteger("123456787654321345678765432345678987654323456789876543456789");
		BigInteger b = new BigInteger("9876543456787654345678");
		System.out.println(a.divide(b));
	}

}
