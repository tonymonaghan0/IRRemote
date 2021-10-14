package com.mcssoftware.irremote;

import java.util.ArrayList;
import java.util.List;

// Frequency is 38 KHz
// Pulse width for 0 and 1 is 0.527ms ie BIT_MARK
// Pulse interval (0) 1.055ms +–60 us
// Pulse interval (1) 2.11ms +–100 us
// ONE_SPACE  = 1.583ms
// ZERO_SPACE = 0.527ms
// Header is HDR_MARK then HDR_SPACE
// Data word is 32 bits, think it's MSB first???
// First byte is always 5 00000101 and second is always 250 11111010
// Byte 2 is the binary complement of byte 1
// Byte 4 is the binary complement of byte 3
// Time before FOOTER is 46 milliseconds
// Footer is FOOTER_MARK then FOOTER_SPACE then BIT_MARK and then PAUSE_TIME

public class IRJVCFactory {

    public static final int HDR_MARK  = 8440; //8.44ms
    public static final int HDR_SPACE = 4220; //4.22ms
    public static final int FOOTER_MARK  = 8440; //8.44ms
    public static final int FOOTER_SPACE = 2110; //2.11ms
    public static final int BIT_MARK     = 527;
    public static final int ONE_SPACE  = 1583;
    public static final int ZERO_SPACE = 527;
    public static final int PAUSE_TIME = 46000; //46 ms

    public static IRMessage create( int address, int command)
    {
        List<Integer> message = new ArrayList<>();

        //Header
        message.add(HDR_MARK);
        message.add(HDR_SPACE);

        List<Integer> byte1 = decodeInt(address);
        List<Integer> byte2 = decodeInt(255 - address);
        List<Integer> byte3 = decodeInt(command);
        List<Integer> byte4 = decodeInt(255 - command);
        message.addAll(byte1);
        message.addAll(byte2);
        message.addAll(byte3);
        message.addAll(byte4);

        //Add the 17th bit and pause
        message.add(BIT_MARK);
        message.add(PAUSE_TIME);

        //Add Footer
        message.add(FOOTER_MARK);
        message.add(FOOTER_SPACE);
        message.add(BIT_MARK);
        message.add(PAUSE_TIME);

        int [] finalCode = new int[message.size()];

        for(int a=0;a<message.size();++a)
        {
            finalCode[a] = message.get(a);
        }

        return new IRMessage(IRMessage.FREQ_38_KHZ,finalCode);
    }

    private static List<Integer> decodeInt(int num)
    {
        List<Integer> values = new ArrayList<>();
        for (int i = 8 - 1; i >= 0; i--)
        {
            values.add(BIT_MARK);
            values.add(((num & (1 << i)) == 0)?ZERO_SPACE:ONE_SPACE);
        }
        return values;
    }
}

