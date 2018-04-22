package com.example.android.sunshine.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Random;


public class EnterNames extends ActionBarActivity {

    static String[] names;
    static int[] roles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_names);
        LinearLayout ll = (LinearLayout) findViewById(R.id.enter_names);
        for (int i = 0; i < PlayerNumbers.numberOfPlayers; i++) {
            EditText et = new EditText(this);
            ll.addView(et);
        }
    }

    public void assignRoles(View view) {
        LinearLayout ll = (LinearLayout) findViewById(R.id.enter_names);
        int len = PlayerNumbers.numberOfPlayers, done = 0;
        names = new String[len];
        for (int i = 0, j = 0; i < ll.getChildCount(); i++) {
            View v = ll.getChildAt(i);
            if (v instanceof  EditText) {
                names[j] = ((EditText) v).getText().toString();
                if (!names[j].isEmpty()) {
                    done++;
                }
                j++;
            }
        }
        if (done == len) {
            roles = new int[len];
            int allDone = 0;
            while (allDone < len) {
                Random ran = new Random();
                int role = ran.nextInt(len);
                boolean correct = true;
                for (int i = 0; i < allDone; i++) {
                    if (roles[i] == role) {
                        correct = false;
                    }
                }
                if (correct) {
                    roles[allDone] = role;
                    allDone++;
                }
            }
            Intent myIntent = new Intent(view.getContext(), ShowRoles.class);
            startActivityForResult(myIntent, 0);
        }
        else {
            Toast.makeText(getApplicationContext(), "Player name can't be empty!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_enter_names, menu);
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
