package com.mcssoftware.irremote;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener
{
    private IRController _irController;

    private ImageButton _buttonPowerAll;

    private final LinearLayout [] _button = new LinearLayout[12];
    //private final IRMessageRequest [] _buttonRequest = new IRMessageRequest[12];

    private final LinearLayout [] _rows = new LinearLayout[4];

    //private final View _currentClickedView = null;

    private Vibrator _vibrator = null;

    private long _lastBurstTime = 0;     // Microseconds
    private long _waitTime = 315000;     // Microseconds

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        IRMessages.initialize();

        setContentView(R.layout.main_activity);

        Objects.requireNonNull(getSupportActionBar()).hide();

        _vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);

        _irController = new IRController(getApplicationContext());
        _irController.startWork();

        getRowReferences();

        _buttonPowerAll = findViewById(R.id.id_Button_ALL);

        _button[0] =  createIRButton("TV",R.drawable.icon_01,_rows[0], new IRMessageRequest(IRMessages.HOME_JVC_TV_ON, IRMessages.SAMSUNG_TV_ON));
        _button[1] =  createIRButton("MUTE",R.drawable.icon_01,_rows[0],new IRMessageRequest(IRMessages.HOME_JVC_TV_MUTE, IRMessages.SONY_SOUNDBAR_MUTE));
        _button[2] =  createIRButton("SOURCE",R.drawable.icon_10,_rows[0],new IRMessageRequest(IRMessages.HOME_JVC_TV_SOURCE, IRMessages.HOME_SONY_HIFI_FUNCTION, IRMessages.SAMSUNG_TV_SOURCE));


        _button[3] =  createIRButton("SONY V+",R.drawable.icon_02,_rows[1],new IRMessageRequest(IRMessages.HOME_SONY_HIFI_VOLUME_UP));
        _button[4] =  createIRButton("SONY HI-FI",R.drawable.icon_03,_rows[1],new IRMessageRequest(IRMessages.HOME_SONY_HIFI_ON));
        _button[5] =  createIRButton("SONY V-",R.drawable.icon_08,_rows[1],new IRMessageRequest(IRMessages.HOME_SONY_HIFI_VOLUME_DOWN));


        _button[6] =  createIRButton("VOLUME UP",R.drawable.icon_02,_rows[2],new IRMessageRequest(IRMessages.HOME_JVC_TV_VOLUME_UP, IRMessages.HOME_SONY_HIFI_VOLUME_UP,IRMessages.SONY_SOUNDBAR_VOLUME_UP));
        _button[7] =  createIRButton("UP",R.drawable.icon_02,_rows[2],new IRMessageRequest(IRMessages.HOME_JVC_TV_UP));
        _button[8] =  createIRButton("CHANNEL UP",R.drawable.icon_02,_rows[2],new IRMessageRequest(IRMessages.HOME_JVC_TV_CHANNEL_UP, IRMessages.HOME_SONY_HIFI_TUNER_UP));


        _button[9] =  createIRButton("VOLUME DOWN",R.drawable.icon_08,_rows[3],new IRMessageRequest(IRMessages.HOME_JVC_TV_VOLUME_DOWN, IRMessages.HOME_SONY_HIFI_VOLUME_DOWN, IRMessages.SONY_SOUNDBAR_VOLUME_DOWN));
        _button[10] =  createIRButton("DOWN",R.drawable.icon_08,_rows[3],new IRMessageRequest(IRMessages.HOME_JVC_TV_DOWN));
        _button[11] =  createIRButton("CHANNEL DOWN",R.drawable.icon_08,_rows[3],new IRMessageRequest(IRMessages.HOME_JVC_TV_CHANNEL_DOWN, IRMessages.HOME_SONY_HIFI_TUNER_DOWN));

        _lastBurstTime = System.nanoTime();

        for(int a=0;a<12;a++)
        {
            _button[a].setOnTouchListener(this);
        }

        _buttonPowerAll.setOnTouchListener(this);

    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Log.d("Zoser","onPause");
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();

        Log.d("Zoser","onRestart");
    }

    @Override
    protected void onDestroy()
    {
        Log.d("Zoser","onDestroy");
        _irController.stopWork();
        super.onDestroy();
    }

    private void getRowReferences()
    {
        _rows[0] = findViewById(R.id.id_Layout_Row_01);
        _rows[1] = findViewById(R.id.id_Layout_Row_02);
        _rows[2] = findViewById(R.id.id_Layout_Row_03);
        _rows[3] = findViewById(R.id.id_Layout_Row_04);

    }


    private LinearLayout createIRButton(String title, int imageResource, LinearLayout row,IRMessageRequest request)
    {
        View child = getLayoutInflater().inflate(R.layout.widget_button, null);

        RelativeLayout mainLayout = child.findViewById(R.id.layout_main);
        LinearLayout buttonLayout = child.findViewById(R.id.layout_button);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.weight = 1;
        mainLayout.setLayoutParams(lp);

        ImageView imageView = child.findViewById(R.id.image_main);
        TextView titleView  = child.findViewById(R.id.text_title);

        imageView.setImageResource(imageResource);
        titleView.setText(title);

        row.addView(child);

        buttonLayout.setTag(request);

        return buttonLayout;
    }

    public boolean onTouch(View v, MotionEvent event)
    {
        // Microsecconds
        long _waitTimeMin = 300000;
        _waitTime = Math.max(_waitTime, _waitTimeMin);
        // Microsecconds
        long _waitTimeMax = 1000000;
        _waitTime = Math.min(_waitTime, _waitTimeMax);

        if((System.nanoTime() - _lastBurstTime) > (_waitTime * 1000))
        {
            int ev = event.getActionMasked();

            if(ev == MotionEvent.ACTION_MOVE)
            {
                _lastBurstTime = System.nanoTime();

                if(v == _buttonPowerAll)
                {
                    _waitTime = sendIRMessage(new IRMessageRequest(IRMessages.HOME_JVC_TV_ON, IRMessages.HOME_SONY_HIFI_ON, IRMessages.SAMSUNG_TV_ON,IRMessages. SONY_SOUNDBAR_ON));
                }
                else
                {
                    _waitTime = sendIRMessage(((IRMessageRequest) v.getTag()));
                }
                return true;
            }
        }
        return false;
    }


    public long sendIRMessage(IRMessageRequest request)
    {
        _vibrator.vibrate(60);
        return _irController.sendMessage(request);
    }
}
