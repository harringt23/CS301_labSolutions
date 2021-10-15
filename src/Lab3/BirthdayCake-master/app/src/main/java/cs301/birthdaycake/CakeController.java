package cs301.birthdaycake;

import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;

public class CakeController
        implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener,
        SeekBar.OnSeekBarChangeListener {
    // declare a private instance variable in the CakeView
    // class of type CakeModel.
    private CakeView cakeView;
    private CakeModel cakeModel;

    //  Then initialize this variable with a new CakeModel
    //  object when CakeView object is created (aka in the CakeView constructor).
    public CakeController(CakeView cv){
        cakeView = cv;
        cakeModel = cv.getCakeMode();
    }

    // blow-out the candle when the button is clicked
    @Override
    public void onClick(View view) {
        Log.d("button", "ClickedLit");
        cakeModel.lit = !cakeModel.lit;
        cakeView.invalidate();
    }

    // decide whether the candles are drawn on the cake
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        Log.d("buttonCP", "ClickedNumCandles");
        cakeModel.hasCandles = !cakeModel.hasCandles;
        cakeView.invalidate();
    }

    //
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        Log.d("buttonSB", "BarNumCandles");
        cakeModel.numCandles = i;
        cakeView.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

}
