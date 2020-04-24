package com.emile.festafimdeano.views;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.emile.festafimdeano.FimDeAnoConstants;
import com.emile.festafimdeano.R;
import com.emile.festafimdeano.data.SecurityPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ViewHolder mViewHolder = new ViewHolder();
    private static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private SecurityPreferences mSecurityPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mSecurityPreferences = new SecurityPreferences(this);

        this.mViewHolder.textToday = findViewById(R.id.text_today);
        this.mViewHolder.textDiasRestantes = findViewById(R.id.text_diasRestantes);
        this.mViewHolder.buttonConfirme = findViewById(R.id.button_confirme);

        this.mViewHolder.buttonConfirme.setOnClickListener(this);

        this.mViewHolder.textToday.setText(SIMPLE_DATE_FORMAT.format(Calendar.getInstance().getTime()));
        String daysLeft = String.format("%s %s",String.valueOf(this.getDaysleft()), getString(R.string.dias));
        this.mViewHolder.textDiasRestantes.setText(daysLeft);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.verifyPresence();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button_confirme){
            String presence = this.mSecurityPreferences.getStoreString(FimDeAnoConstants.PRESENCE_KEY);
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra(FimDeAnoConstants.PRESENCE_KEY,presence);
            startActivity(intent);
        }
    }

    private void verifyPresence(){
        String presence = this.mSecurityPreferences.getStoreString(FimDeAnoConstants.PRESENCE_KEY);
        if (presence.equals(""))
            this.mViewHolder.buttonConfirme.setText(R.string.nao_confirmado);
        else if (presence.equals(FimDeAnoConstants.CONFIRMATION_YES))
            this.mViewHolder.buttonConfirme.setText(R.string.sim);
        else
            this.mViewHolder.buttonConfirme.setText(R.string.nao);
    }

    private int getDaysleft(){
        Calendar calendarToday = Calendar.getInstance();
        int today = calendarToday.get(Calendar.DAY_OF_YEAR);
        Calendar calendarLastDay = Calendar.getInstance();
        int dayMax = calendarLastDay.getActualMaximum(Calendar.DAY_OF_YEAR);

        return dayMax - today;
    }

    private static class ViewHolder{
        TextView textToday;
        TextView textDiasRestantes;
        Button buttonConfirme;
    }
}
