package com.emile.festafimdeano.views;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.emile.festafimdeano.FimDeAnoConstants;
import com.emile.festafimdeano.R;
import com.emile.festafimdeano.data.SecurityPreferences;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewHolder mViewHolder = new ViewHolder();
    private SecurityPreferences mSecurityPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        this.mSecurityPreferences = new SecurityPreferences(this);

        this.mViewHolder.checkParticipate = findViewById(R.id.check_participate);
        this.mViewHolder.checkParticipate.setOnClickListener(this);

        this.loadDataFromActivity();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.check_participate) {
            if (this.mViewHolder.checkParticipate.isChecked()) {
                this.mSecurityPreferences.storestring(FimDeAnoConstants.PRESENCE_KEY, FimDeAnoConstants.CONFIRMATION_YES);
            } else {
                this.mSecurityPreferences.storestring(FimDeAnoConstants.PRESENCE_KEY, FimDeAnoConstants.CONFIRMATION_NO);
            }
        }
    }

    private void loadDataFromActivity() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String presence = extras.getString(FimDeAnoConstants.PRESENCE_KEY);
            if (presence != null && presence.equals(FimDeAnoConstants.CONFIRMATION_YES)) {
                this.mViewHolder.checkParticipate.setChecked(true);
            } else {
                this.mViewHolder.checkParticipate.setChecked(false);
            }
        }
    }

    private static class ViewHolder{
        CheckBox checkParticipate;
    }
}
