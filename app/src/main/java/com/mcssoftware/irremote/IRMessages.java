package com.mcssoftware.irremote;

public class IRMessages
{
    // Sony HiFi
    public static IRMessage HOME_SONY_HIFI_ON;
    public static IRMessage HOME_SONY_HIFI_VOLUME_UP;
    public static IRMessage HOME_SONY_HIFI_VOLUME_DOWN;
    public static IRMessage HOME_SONY_HIFI_FUNCTION;
    public static IRMessage  HOME_SONY_HIFI_TUNER_DOWN;
    public static IRMessage  HOME_SONY_HIFI_TUNER_UP;

    // Sony Soundbar
    public static IRMessage SONY_SOUNDBAR_ON;
    public static IRMessage SONY_SOUNDBAR_VOLUME_UP;
    public static IRMessage SONY_SOUNDBAR_VOLUME_DOWN;
    public static IRMessage SONY_SOUNDBAR_MUTE;

    // JVC TV
    public static IRMessage HOME_JVC_TV_ON;
    public static IRMessage HOME_JVC_TV_VOLUME_UP;
    public static IRMessage HOME_JVC_TV_VOLUME_DOWN;
    public static IRMessage HOME_JVC_TV_MUTE;
    public static IRMessage HOME_JVC_TV_CHANNEL_UP;
    public static IRMessage HOME_JVC_TV_CHANNEL_DOWN;
    public static IRMessage HOME_JVC_TV_SOURCE;
    public static IRMessage HOME_JVC_TV_UP;
    public static IRMessage HOME_JVC_TV_DOWN;
    public static IRMessage HOME_JVC_OK;
    public static IRMessage HOME_JVC_TV_LEFT;
    public static IRMessage HOME_JVC_TV_RIGHT;

    //Samsung
    public static IRMessage SAMSUNG_TV_ON;
    public static IRMessage SAMSUNG_TV_SOURCE;

    public static void initialize()
    {
        // Sony HI-FI 12 bits, 7 command then 5 address
        HOME_SONY_HIFI_ON           = IRSonyFactory.create(IRSonyFactory.TYPE_12_BITS,84,1,3);
        HOME_SONY_HIFI_VOLUME_UP    = IRSonyFactory.create(IRSonyFactory.TYPE_12_BITS,36,1,3);
        HOME_SONY_HIFI_VOLUME_DOWN  = IRSonyFactory.create(IRSonyFactory.TYPE_12_BITS,100,1,3);
        HOME_SONY_HIFI_TUNER_UP     = IRSonyFactory.create(IRSonyFactory.TYPE_12_BITS,4,1,3);
        HOME_SONY_HIFI_TUNER_DOWN   = IRSonyFactory.create(IRSonyFactory.TYPE_12_BITS,68,1,3);
        // Sony HI-FI 15 bits, 7 command then 8 address
        HOME_SONY_HIFI_FUNCTION     = IRSonyFactory.create(IRSonyFactory.TYPE_15_BITS,75,9,3);

        // Sony Soundbar 15 bits, 7 command then 8 address
        SONY_SOUNDBAR_ON            = IRSonyFactory.create(IRSonyFactory.TYPE_15_BITS,84,12,3);
        SONY_SOUNDBAR_VOLUME_UP     = IRSonyFactory.create(IRSonyFactory.TYPE_15_BITS,36,12,3);
        SONY_SOUNDBAR_VOLUME_DOWN   = IRSonyFactory.create(IRSonyFactory.TYPE_15_BITS,100,12,3);
        SONY_SOUNDBAR_MUTE            = IRSonyFactory.create(IRSonyFactory.TYPE_15_BITS,20,12,3);

        // JVC TV
        HOME_JVC_TV_ON           = IRJVCFactory.create(5,56);
        HOME_JVC_TV_VOLUME_UP    = IRJVCFactory.create(5,2);
        HOME_JVC_TV_VOLUME_DOWN  = IRJVCFactory.create(5,186);
        HOME_JVC_TV_MUTE         = IRJVCFactory.create(5,250);
        HOME_JVC_TV_CHANNEL_UP   = IRJVCFactory.create(5,192);
        HOME_JVC_TV_CHANNEL_DOWN = IRJVCFactory.create(5,248);
        HOME_JVC_TV_SOURCE       = IRJVCFactory.create(5,88);
        HOME_JVC_TV_UP           = IRJVCFactory.create(5,154);
        HOME_JVC_TV_DOWN         = IRJVCFactory.create(5,138);
        HOME_JVC_OK              = IRJVCFactory.create(5,170);
        HOME_JVC_TV_LEFT         = IRJVCFactory.create(5,106);
        HOME_JVC_TV_RIGHT        = IRJVCFactory.create(5,40);

        //Samsung TV
        SAMSUNG_TV_ON           = IRSamsungFactory.create(7,2);
        SAMSUNG_TV_SOURCE       = IRSamsungFactory.create(7,1);

    }
}
