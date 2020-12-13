package ferancini.app.simplecheckin;

import android.content.ContentValues;
import android.database.Cursor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.BackingStoreException;

public class CheckIn implements Serializable {
    private String local;
    private int qtdVisitas;
    private int cat;
    private String latitude;
    private String longitude;
    private static List<CheckIn> listCheckIn;

    public CheckIn(String local, int qtdVisitas, int cat, String latitude, String longitude) {
        this.local = local;
        this.qtdVisitas = qtdVisitas;
        this.cat = cat;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static List<CheckIn> getCheckIns(){
        Cursor c = BancoDadosSingleton.getInstance().buscar(
                "CheckIn",
                new String[]{"Local","qtdVisitas","cat","latitude","longitude"},
                "",
                ""
                );
        listCheckIn = new ArrayList<CheckIn>();
        while(c.moveToNext()){
            int colLocal = c.getColumnIndex("Local");
            int colVisitas = c.getColumnIndex("qtdVisitas");
            int colCat = c.getColumnIndex("cat");
            int colLat = c.getColumnIndex("latitude");
            int colLon = c.getColumnIndex("longitude");

            String local = c.getString(colLocal);
            int qtdVisitas = c.getInt(colVisitas);
            int cat = c.getInt(colCat);
            String latutide = c.getString(colLat);
            String longitude = c.getString(colLon);

            CheckIn check = new CheckIn(local,qtdVisitas,cat,latutide,longitude);
            listCheckIn.add(check);
        }

        return listCheckIn;
    }

    public static void insertCheckIn(String local, int cat, String latitude, String longitude){
        Cursor cr = BancoDadosSingleton.getInstance().buscar("Checkin", new String[]{"Local","qtdVisitas"},"Local='"+local+"'","");
        if(cr.getCount() > 0){
            int visitas = 0;
            while (cr.moveToNext()){
                int colVisitas = cr.getColumnIndex("qtdVisitas");

                visitas = cr.getInt(colVisitas);
            }
            ContentValues ct = new ContentValues();
            ct.put("qtdVisitas", visitas+1);
            int i = BancoDadosSingleton.getInstance().atualizar("CheckIn",ct,"Local='"+local+"'");
        }
        else{
            //checkin em local novo
            ContentValues ct = new ContentValues();
            ct.put("local", local);
            ct.put("qtdVisitas", 1);
            ct.put("cat", cat);
            ct.put("latitude", latitude);
            ct.put("longitude", longitude);
            BancoDadosSingleton.getInstance().inserir("Checkin",ct);
        }
    }

    public int getCat() {
        return cat;
    }

    public void setCat(int cat) {
        this.cat = cat;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public int getQtdVisitas() {
        return qtdVisitas;
    }

    public void setQtdVisitas(int qtdVisitas) {
        this.qtdVisitas = qtdVisitas;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "local";
    }
}
