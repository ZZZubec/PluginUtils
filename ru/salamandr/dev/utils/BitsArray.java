package ru.salamandr.dev.utils;

/*
 * Create by Salamandr (c)2019
 */

import java.util.ArrayList;

public class BitsArray {

    ArrayList<Boolean> bits;

    public BitsArray(int number) {
        String str_bin = Integer.toBinaryString(number);
        createFromString(str_bin);
    }

    public BitsArray(String str_bin) {
        createFromString(str_bin);
    }

    public void createFromString(String string_binary) {
        bits = new ArrayList<Boolean>();
        StringBuilder buffer = new StringBuilder(string_binary);
        String str_bin = buffer.reverse().toString();
        int bits_count = str_bin.length();

        for (int i = 0; i < bits_count; i++)
        {
            if (str_bin.charAt(i) == '1')
                bits.add(true);
            else
                bits.add(false);
        }

        for (int i = bits_count; i < 8; i++)
            bits.add(false);
    }

    private void shiftLeft(int bitCount) {
        for(int i=7; i>=7-(bitCount-1); i--)
            bits.remove(i);
    }

    private void removeRange(int start, int end) {
        for(int i=end; i>=start; i--)
            bits.remove(i);
    }

    private void shiftRight(int bitIndex) {
        removeRange(0, bitIndex-1);
    }

    private void setBit(int bitIndex, boolean value) {
        bits.set(bitIndex-1, value);
    }

    private void set(int i, Boolean value) {
        setBit(i, value);
    }

    public int GetInt(int start_bit, int end_bit, int shift) {
        BitsArray new_bits = new BitsArray(0);
        for(int i=start_bit-1; i<=end_bit-1; i++)
        {
            new_bits.set(i+1, bits.get(i));
        }
        if(shift>0)
        {
            new_bits.shiftRight(shift);
            return new_bits.GetInt();
        }
        else
            return new_bits.GetInt();
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = bits.size()-1; i >= 0 ; i--)
        {
            result += (bits.get(i) ? '1' : '0');
        }
        return result;
    }

    public int GetInt() {
        int value = 0;
        int index = 1;
        for (int i = 0; i < bits.size(); i++)
        {
            value += (bits.get(i) ? 1 : 0)*index;
            if (index == 1)
                index = 2;
            else
                index *= 2;
        }
        return value;
    }

    public static BitsArray join(BitsArray a1, int bitIndex, BitsArray a2) {
        BitsArray newbits = new BitsArray(0);
        a1.shiftLeft(8-(bitIndex));
        a2.shiftLeft(bitIndex);
        int indexBit = 1;
        for(int i=0; i<a1.bits.size(); i++)
        {
            newbits.setBit(indexBit, a1.bits.get(i));
            indexBit++;
        }
        for(int i=0; i<a2.bits.size(); i++)
        {
            newbits.setBit(indexBit, a2.bits.get(i));
            indexBit++;
        }
        return newbits;
    }
}
