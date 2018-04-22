package com.example.android.sunshine.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ShowRoles extends ActionBarActivity {

    public int current = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_roles);
        TextView tv = (TextView) findViewById(R.id.personIs);
        tv.setText(EnterNames.names[0]);
    }

    public static String getRole(int val) {
        if (val == 0) {
            return "DON";
        }
        else if (val == 1) {
            return "MAFIA";
        }
        else if (val == 2) {
            return "DETECTIVE";
        }
        else if (val == 3) {
            return "DOCTOR";
        }
        return "VILLAGER";
    }

    public void reveal(View view) {
        Button bt = (Button) findViewById(R.id.clickToKnow);
        bt.setVisibility(View.GONE);
        String role = getRole(EnterNames.roles[current]);
        TextView tv = (TextView) findViewById(R.id.identity);
        tv.setText(role);
        tv.setVisibility(View.VISIBLE);
        Button bt2 = (Button) findViewById(R.id.next);
        bt2.setVisibility(View.VISIBLE);
    }

    public void next(View view) {
        current++;
        if (current == PlayerNumbers.numberOfPlayers) {
            Intent myIntent = new Intent(view.getContext(), StartRound.class);
            startActivityForResult(myIntent, 0);
        }
        else {
            TextView tv = (TextView) findViewById(R.id.personIs);
            tv.setText(EnterNames.names[current]);
            Button bt = (Button) findViewById(R.id.clickToKnow);
            bt.setVisibility(View.VISIBLE);
            TextView tv2 = (TextView) findViewById(R.id.identity);
            tv2.setVisibility(View.GONE);
            Button bt2 = (Button) findViewById(R.id.next);
            bt2.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_roles, menu);
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
