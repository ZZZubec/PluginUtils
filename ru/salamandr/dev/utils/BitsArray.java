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

    /*
    private void ShiftLeft(int count) {
        removeRange(count, 8);
    }

    private void removeRange(int start, int end) {
        for(int i=end; i>=start; i--)
            bits.remove(i);
    }

    private void ShiftRight(int v) {
        removeRange(0, v);
    }
    */

    private void setBit(int bitIndex, boolean value) {
        bits.set(bitIndex, value);
    }

    private void set(int i, Boolean value) {
        setBit(i, value);
    }

    public int GetInt(int start_bit, int end_bit) {
        BitsArray new_bits = new BitsArray(0);
        for(int i=start_bit-1; i<end_bit-1; i++)
        {
            new_bits.set(i, bits.get(i));
        }
        return new_bits.GetInt();
    }

    public int GetInt() {
        int value = 0;
        int index = 1;
        for (int i = bits.size()-1; i >= 0 ; i--)
        {
            value += (bits.get(i) ? 1 : 0)*index;
            if (index == 1)
                index = 2;
            else
                index *= 2;
        }
        return value;
    }
}
