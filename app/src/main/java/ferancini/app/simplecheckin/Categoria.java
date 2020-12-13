package ferancini.app.simplecheckin;

import android.database.Cursor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.BackingStoreException;

public class Categoria implements Serializable {
    private int idCategoria;
    private String nome;
    private static List<Categoria> listCategoria;

    public Categoria(int idCategoria, String nome) {
        this.nome = nome;
    }

    public static List<Categoria> getCategorias(){
        listCategoria = new ArrayList<Categoria>();
        Cursor c = BancoDadosSingleton.getInstance().buscar("Categoria",new String[]{"idCategoria","nome"}, "","");
        while (c.moveToNext()){
            int colId = c.getColumnIndex("idCategoria");
            int colNome = c.getColumnIndex("nome");
            Categoria cat = new Categoria(c.getInt(colId), c.getString(colNome));
            listCategoria.add(cat);
        }
        return listCategoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    @Override
    public String toString() {
        return nome;
    }
}
