package cs301.birthdaycake;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        // initialize a new local cake view variable and cake controller
        CakeView cakeview = (CakeView) findViewById(R.id.cakeview);
        CakeController control = new CakeController(cakeview);

        // set the listener for the blow out buttons to be handled
        Button blowOut = (Button) findViewById(R.id.blow_out);
        blowOut.setOnClickListener(control);

        // switch whether the candles are shown or not
        Switch candleSwitch = (Switch) findViewById(R.id.candles);
        candleSwitch.setOnCheckedChangeListener(control);

        // set how many candles are there
        SeekBar candleCountBar = (SeekBar) findViewById(R.id.num_candles);
        candleCountBar.setOnSeekBarChangeListener(control);

        // initialize the on touch listener with the controller
        cakeview.setOnTouchListener(control);
    }

    public void goodbye(View button) {
        //System.out.println("Goodbye");
        // recommended way!!
        Log.i("button", "Goodbye");
    }
}
