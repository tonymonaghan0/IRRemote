package com.mcssoftware.irremote;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Frequency is 38 KHz
// Header is HDR_MARK then HDR_SPACE each are 4.5ms
// Pulse width for 0 and 1 is 0.560ms ie BIT_MARK
// ONE_SPACE  = 1.69ms
// ZERO_SPACE = 0.56ms
// Data word is 32 bits (LSB first!)
// First and second byte are the same and give the address usually 7 (224 backwards) ie 11100000
// Byte 3 is the command
// Byte 4 is the binary complement of byte 3


public class IRSamsungFactory {

    public static final int HDR_MARK  = 4500; //4.5ms
    public static final int HDR_SPACE = 4500; //4.55ms
    public static final int BIT_MARK     = 560;
    public static final int ONE_SPACE  = 1690;
    public static final int ZERO_SPACE = 560;
    public static final int RPT_SPACE = 2250;

    public static IRMessage create( int address, int command)
    {
        List<Integer> message = new ArrayList<>();

        message.add(HDR_MARK);
        message.add(HDR_SPACE);

        List<Integer> byte1 = decodeInt(address);
        List<Integer> byte3 = decodeInt(command);
        List<Integer> byte4 = decodeInt(255 - command);
        message.addAll(byte1); //Custom address
        message.addAll(byte1); //Custom address repeated
        message.addAll(byte3); //Data
        message.addAll(byte4); //NOT Data ie complement

        //Add the 33rd bit
        message.add(BIT_MARK);
        message.add(RPT_SPACE); //End on 0

        //Convert List<Integer> message to int [] finalCode
        int [] finalCode = new int[message.size()];
        for(int a=0;a<message.size();++a)
        {
            finalCode[a] = message.get(a);
        }

        return new IRMessage(IRMessage.FREQ_38_KHZ,finalCode);
    }

    //Convert decimal num to list of 0's and 1's with LSB first
    private static List<Integer> decodeInt(int num)
    {
        List<Integer> values = new ArrayList<>();
        for (int i = 8 - 1; i >= 0; i--)
        {
            //Start with space as whole list is reversed
            values.add(((num & (1 << i)) == 0)?ZERO_SPACE:ONE_SPACE);
            values.add(BIT_MARK);
        }
        Collections.reverse(values);
        return values;
    }
}
