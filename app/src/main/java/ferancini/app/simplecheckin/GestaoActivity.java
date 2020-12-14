package ferancini.app.simplecheckin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import static android.widget.LinearLayout.*;

public class GestaoActivity extends AppCompatActivity {
    private LinearLayout ltTxt;
    private LinearLayout ltBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestao);
        ltTxt = (LinearLayout)findViewById(R.id.ltTxt);
        ltBtn = (LinearLayout)findViewById(R.id.ltBtn);

        List<CheckIn> listChekIn = CheckIn.getCheckIns();
        for(CheckIn checkin : listChekIn){
            TextView txt = new TextView(this);
            LinearLayout.LayoutParams ltparm = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            ltparm.setMargins(0,20,0,0);
            txt.setText(checkin.getLocal());
            txt.setLayoutParams(ltparm);
            txt.setTextSize(23);
            ltTxt.addView(txt);

            ImageButton btn = new ImageButton(this);
            btn.setImageResource(android.R.drawable.ic_delete);
            btn.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                    )
            );
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CheckIn.delete(btn.getTag().toString());
                    ltTxt.removeView(txt);
                    ltBtn.removeView(btn);
                }
            });
            btn.setTag(checkin.getLocal());
            ltBtn.addView(btn);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_gestao, menu);
        return true;
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
}