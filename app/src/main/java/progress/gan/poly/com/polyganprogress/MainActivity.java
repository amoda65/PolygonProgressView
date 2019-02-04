package progress.gan.poly.com.polyganprogress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import progress.gan.poly.com.lib.PolygonProgressView;

public class MainActivity extends AppCompatActivity {
    private SeekBar barProgress;
    private SeekBar barSides;
    private SeekBar barStroke;
    private TextView textProgress;
    private TextView textSides;
    private TextView textStroke;
    private PolygonProgressView polygonProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        barProgress=findViewById(R.id.seekBarProgress);
        barSides=findViewById(R.id.seekBarSides);
        barStroke=findViewById(R.id.seekBarStroke);
        polygonProgressView=findViewById(R.id.polygonProgressView);

        textProgress=findViewById(R.id.txtProgressValue);
        textSides=findViewById(R.id.txtSidesValue);
        textStroke=findViewById(R.id.txtStrokeWidth);


        barProgress.setMax(polygonProgressView.getMaxProgress());
        barProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                polygonProgressView.setProgressValue(i);
                textProgress.setText(polygonProgressView.getMaxProgress()+"/"+i);

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        barSides.setMax(20);
        barSides.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                polygonProgressView.setNumberOfSides(i);
                textSides.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        barStroke.setMax(40);
        barStroke.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                polygonProgressView.setStrokeWidth(i);
                textStroke.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
