package com.example.android.sunshine.app;

import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


public class StartRound extends ActionBarActivity {

    Timer timer;
    TimerTask timerTask;

    final Handler handler = new Handler();

    boolean[] alive;

    String killed;

    int round = 1;
    int slept = 0;
    int stage = 0;

    boolean isKill = false;

    int healerPower = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_round);
        TextView tv = (TextView) findViewById(R.id.roundNumber);
        tv.setText("Round Number: " + String.valueOf(round));
        alive = new boolean[PlayerNumbers.numberOfPlayers];
        LinearLayout ll = (LinearLayout) findViewById(R.id.start_round);
        for (int i = 0; i < PlayerNumbers.numberOfPlayers; i++) {
            alive[i] = true;
            ((Button)ll.getChildAt(i + 1)).setText(EnterNames.names[i]);
            ((Button)ll.getChildAt(i + 11)).setText(EnterNames.names[i]);
        }
    }

    public void saved(View view) {
        stage++;
        isKill = false;
        healerPower--;
        killed = "";
        TextView tv = (TextView)findViewById(R.id.doctorSave);
        tv.setVisibility(View.GONE);
        Button yes = (Button)findViewById(R.id.yes);
        yes.setVisibility(View.GONE);
        Button no = (Button)findViewById(R.id.no);
        no.setVisibility(View.GONE);
    }

    public void notSaved(View view) {
        stage++;
        isKill = true;
        TextView tv = (TextView)findViewById(R.id.doctorSave);
        tv.setVisibility(View.GONE);
        Button yes = (Button)findViewById(R.id.yes);
        yes.setVisibility(View.GONE);
        Button no = (Button)findViewById(R.id.no);
        no.setVisibility(View.GONE);
    }

    int getNameId(String suspect) {
        for (int i = 0; i < PlayerNumbers.numberOfPlayers; i++) {
            if (EnterNames.names[i].equals(suspect)) {
                return i;
            }
        }
        return 0;
    }

    boolean detKilled() {
        for (int i = 0; i < PlayerNumbers.numberOfPlayers; i++) {
            if (EnterNames.roles[i] == 2) {
                return !alive[i];
            }
        }
        return false;
    }

    public void suspect(View view) {
        System.out.println("SUSPECTING\n");
        Button but = (Button)view;
        String suspect = but.getText().toString();
        int id = getNameId(suspect);
        if (EnterNames.roles[id] == 1) {
            Toast.makeText(getApplicationContext(), "YES! " + suspect + " is Mafia", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "NO! " + suspect + " is NOT Mafia", Toast.LENGTH_SHORT).show();
        }
        LinearLayout ll = (LinearLayout) findViewById(R.id.start_round);

        for (int i = 0; i < PlayerNumbers.numberOfPlayers; i++) {
            if (alive[i] && EnterNames.roles[i] != 2) {
                System.out.println("GOING NOW");
                ((Button) ll.getChildAt(i + 11)).setVisibility(View.GONE);
            }
        }
        stage++;
    }

    public void kill(View view) {
        stage++;
        Button but = (Button)view;
        String person = but.getText().toString();
        killed = person;
        LinearLayout ll = (LinearLayout) findViewById(R.id.start_round);
        for (int i = 0; i < PlayerNumbers.numberOfPlayers; i++) {
            alive[i] = true;
            ((Button)ll.getChildAt(i + 1)).setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
    }

    public void startTimer() {
        timer = new Timer();
        initializeTimerTask();
        timer.schedule(timerTask, 4000, 4000);
    }

    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                                 public void run() {
                                    if (stage == 0) {
                                        final MediaPlayer cityGoToSleep = MediaPlayer.create(StartRound.this, R.raw.citygotosleep);
                                        cityGoToSleep.start();
                                        cityGoToSleep.setLooping(false);
                                        stage++;
                                    }
                                    else if (stage == 1) {
                                        final MediaPlayer cityGoToSleep = MediaPlayer.create(StartRound.this, R.raw.citygotosleep);
                                        cityGoToSleep.start();
                                        cityGoToSleep.setLooping(false);
                                        stage++;
                                    }
                                    else if (stage == 2) {
                                        stage++;
                                        LinearLayout ll = (LinearLayout) findViewById(R.id.start_round);
                                        for (int i = 0; i < PlayerNumbers.numberOfPlayers; i++) {
                                            if (alive[i]) {
                                                ((Button)ll.getChildAt(i + 1)).setVisibility(View.VISIBLE);
                                            }
                                        }
                                        final MediaPlayer dmWakeUp = MediaPlayer.create(StartRound.this, R.raw.dandmafia);
                                        dmWakeUp.start();
                                        dmWakeUp.setLooping(false);
                                    }
                                    else if (stage == 3) {
                                        final MediaPlayer dmKill = MediaPlayer.create(StartRound.this, R.raw.dandm);
                                        dmKill.start();
                                        dmKill.setLooping(false);
                                    }
                                    else if (stage == 4) {
                                        final MediaPlayer dmSleep = MediaPlayer.create(StartRound.this, R.raw.dnf);
                                        dmSleep.start();
                                        dmSleep.setLooping(false);
                                        stage++;
                                    }
                                    else if (stage == 5) {
                                        stage++;
                                        if (healerPower > 0) {
                                            TextView tv = (TextView)findViewById(R.id.doctorSave);
                                            tv.setVisibility(View.VISIBLE);
                                            tv.setText("Do you want to save: " + killed + " ?");
                                            Button yes = (Button)findViewById(R.id.yes);
                                            yes.setVisibility(View.VISIBLE);
                                            Button no = (Button)findViewById(R.id.no);
                                            no.setVisibility(View.VISIBLE);
                                        }
                                        final MediaPlayer doctorWake = MediaPlayer.create(StartRound.this, R.raw.dogo);
                                        doctorWake.start();
                                        doctorWake.setLooping(false);
                                    }
                                    else if (stage == 6) {
                                        final MediaPlayer doctorSave = MediaPlayer.create(StartRound.this, R.raw.doct);
                                        doctorSave.start();
                                        doctorSave.setLooping(false);
                                        if (healerPower <= 0) {
                                            stage++;
                                        }
                                    }
                                    else if (stage == 7) {
                                        final MediaPlayer docSleep = MediaPlayer.create(StartRound.this, R.raw.d);
                                        docSleep.start();
                                        docSleep.setLooping(false);
                                        stage++;
                                    }
                                    else if (stage == 8) {
                                        stage++;
                                        if (!detKilled()) {
                                            LinearLayout ll = (LinearLayout) findViewById(R.id.start_round);
                                            for (int i = 0; i < PlayerNumbers.numberOfPlayers; i++) {
                                                if (alive[i] && EnterNames.roles[i] != 2) {
                                                    ((Button) ll.getChildAt(i + 11)).setVisibility(View.VISIBLE);
                                                }
                                            }
                                        }
                                        final MediaPlayer detWake = MediaPlayer.create(StartRound.this, R.raw.det);
                                        detWake.start();
                                        detWake.setLooping(false);
                                    }
                                    else if (stage == 9) {
                                        System.out.println("STAGE 9");
                                        final MediaPlayer detSuspect = MediaPlayer.create(StartRound.this, R.raw.dete);
                                        detSuspect.start();
                                        detSuspect.setLooping(false);
                                        if (detKilled()) {
                                            System.out.println("DETECTIVE KILLED");
                                            stage++;
                                        }
                                    }
                                    else if (stage == 10) {
                                        final MediaPlayer detSleep = MediaPlayer.create(StartRound.this, R.raw.dgsl);
                                        detSleep.start();
                                        detSleep.setLooping(false);
                                        stage++;
                                    }
                                    else if (stage == 11) {
                                        final MediaPlayer cityWake = MediaPlayer.create(StartRound.this, R.raw.citywak);
                                        cityWake.start();
                                        cityWake.setLooping(false);
                                        TextView tv = (TextView) findViewById(R.id.status);
                                        tv.setVisibility(View.VISIBLE);
                                        if (isKill) {
                                            tv.setText(killed + " DIED!");
                                            alive[getNameId(killed)] = false;
                                        }
                                        else {
                                            tv.setText("No one died");
                                        }
                                        stage++;
                                    }
                                    else if (stage == 11) {

                                    }
                                 }
                             }
                );
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_round, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
