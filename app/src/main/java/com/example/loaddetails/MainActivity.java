package com.example.loaddetails;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.*;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private static final int BIN_1_SEEK_BAR_ID = R.id.bin1SeekBar;
    private static final int BIN_2_SEEK_BAR_ID = R.id.bin2SeekBar;
    private static final int BIN_3_SEEK_BAR_ID = R.id.bin3SeekBar;
    private static final int BIN_4_SEEK_BAR_ID = R.id.bin4SeekBar;
    private TextView bin1Level, bin2Level, bin3Level, bin4Level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Initialize SeekBars
        SeekBar bin1SeekBar = findViewById(R.id.bin1SeekBar);
        SeekBar bin2SeekBar = findViewById(R.id.bin2SeekBar);
        SeekBar bin3SeekBar = findViewById(R.id.bin3SeekBar);
        SeekBar bin4SeekBar = findViewById(R.id.bin4SeekBar);

        // Initialize TextViews
        TextView bin1Level = findViewById(R.id.bin1Level);
        TextView bin2Level = findViewById(R.id.bin2Level);
        TextView bin3Level = findViewById(R.id.bin3Level);
        TextView bin4Level = findViewById(R.id.bin4Level);



        // Attach listeners to SeekBars
        bin1SeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        bin2SeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        bin3SeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        bin4SeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
    }

    private final SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            int seekBarId = seekBar.getId();

            // Update the corresponding TextView with the current progress
            if (seekBarId == R.id.bin1SeekBar) {
                bin1Level.setText(String.valueOf(progress));
                updateFirebaseValue("0001", "weight", progress);
            } else if (seekBarId == R.id.bin2SeekBar) {
                bin2Level.setText(String.valueOf(progress));
                updateFirebaseValue("0002", "weight", progress);
            } else if (seekBarId == R.id.bin3SeekBar) {
                bin3Level.setText(String.valueOf(progress));
                updateFirebaseValue("0003", "weight", progress);
            } else if (seekBarId == R.id.bin4SeekBar) {
                bin4Level.setText(String.valueOf(progress));
                updateFirebaseValue("0004", "weight", progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
    };

    private void updateFirebaseValue(String node, String weight, int value) {
        // Update weight value
        mDatabase.child(node).child("weight").setValue(value);
        // Calculate and update percentage value
        double goal = 40.0;
        double percentage = ((double) value / goal) * 100;
        mDatabase.child(node).child("percentage").setValue(percentage);

        // Show toast message
        Toast.makeText(MainActivity.this, "Firebase values updated", Toast.LENGTH_SHORT).show();
    }
}

