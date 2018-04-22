package com.example.android.sunshine.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class PlayerNumbers extends ActionBarActivity {

    public static int numberOfPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.number_players);
    }

    public void enterNames(View view) {
        EditText editText = (EditText)findViewById(R.id.editText);
        numberOfPlayers = Integer.parseInt(editText.getText().toString());
        if (numberOfPlayers >= 5) {
            Intent myIntent = new Intent(view.getContext(), EnterNames.class);
            startActivityForResult(myIntent, 0);
        }
        else {
            Toast.makeText(getApplicationContext(), "Minimum 5 players!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_player_numbers, menu);
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
