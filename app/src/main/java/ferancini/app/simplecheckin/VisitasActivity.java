package ferancini.app.simplecheckin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class VisitasActivity extends AppCompatActivity {

    private LinearLayout ltCheckIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitas);

        ltCheckIn = (LinearLayout) findViewById(R.id.ltCheckIn);

        List<CheckIn> list = CheckIn.getCheckIns();
        for(CheckIn checkIn : list){
            LinearLayout lt = new LinearLayout(this);
            lt.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams ltparms = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            lt.setLayoutParams(ltparms);

            TextView txtLocal = new TextView(this);
            txtLocal.setText(checkIn.getLocal());

            TextView txtVisitas = new TextView(this);
            txtVisitas.setText(String.valueOf(checkIn.getQtdVisitas()));
            LinearLayout.LayoutParams lt2 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            lt2.gravity = Gravity.RIGHT;
            lt2.weight = 2;
            txtVisitas.setGravity(Gravity.RIGHT);

            txtVisitas.setLayoutParams(lt2);

            lt.addView(txtLocal);
            lt.addView(txtVisitas);

            ltCheckIn.addView(lt);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       switch (item.getItemId()){
           case R.id.action_voltar:
               Intent it = new Intent(this, MainActivity.class);
               startActivity(it);
               finish();
               break;
           default: break;
       }
       return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_gestao, menu);
        return true;
    }
}