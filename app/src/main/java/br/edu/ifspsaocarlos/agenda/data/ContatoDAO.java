package br.edu.ifspsaocarlos.agenda.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.edu.ifspsaocarlos.agenda.adapter.ContatoAdapter;
import br.edu.ifspsaocarlos.agenda.model.Contato;
import java.util.ArrayList;
import java.util.List;


public class ContatoDAO {
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    public ContatoDAO(Context context) {
        this.dbHelper=new SQLiteHelper(context);
    }

    public List<Contato> buscaContatosFavoritos(){
        database = dbHelper.getReadableDatabase();
        List<Contato> contatos = new ArrayList<>();
        String[] cols=new String[] {SQLiteHelper.KEY_ID,
                SQLiteHelper.KEY_NAME,
                SQLiteHelper.KEY_FONE,
                SQLiteHelper.KEY_EMAIL,
                SQLiteHelper.KEY_FAVORITO,
                SQLiteHelper.KEY_FONE2,
                SQLiteHelper.KEY_NASCIMENTO};

        Cursor cursor;

        cursor = database.query(SQLiteHelper.DATABASE_TABLE, cols, SQLiteHelper.KEY_FAVORITO + "=1", null,null,null,null);

        while (cursor.moveToNext())
        {
            Contato contato = new Contato();
            contato.setId(cursor.getInt(0));
            contato.setNome(cursor.getString(1));
            contato.setFone(cursor.getString(2));
            contato.setEmail(cursor.getString(3));
            contato.setFavorito(cursor.getInt(4));
            contato.setFone2(cursor.getString(5));
            contato.setNascimento(cursor.getString(6));
            contatos.add(contato);
        }
        cursor.close();

        database.close();

        return contatos;
    }

    public  List<Contato> buscaTodosContatos() {
        database = dbHelper.getReadableDatabase();
        List<Contato> contatos = new ArrayList<>();

        Cursor cursor;

        String[] cols=new String[] {SQLiteHelper.KEY_ID,
                SQLiteHelper.KEY_NAME,
                SQLiteHelper.KEY_FONE,
                SQLiteHelper.KEY_EMAIL,
                SQLiteHelper.KEY_FAVORITO,
                SQLiteHelper.KEY_FONE2,
                SQLiteHelper.KEY_NASCIMENTO};

        cursor = database.query(SQLiteHelper.DATABASE_TABLE, cols, null , null,
                null, null, SQLiteHelper.KEY_NAME);

        while (cursor.moveToNext())
        {
            Contato contato = new Contato();
            contato.setId(cursor.getInt(0));
            contato.setNome(cursor.getString(1));
            contato.setFone(cursor.getString(2));
            contato.setEmail(cursor.getString(3));
            contato.setFavorito(cursor.getInt(4));
            contato.setFone2(cursor.getString(5));
            contato.setNascimento(cursor.getString(6));
            contatos.add(contato);
        }
        cursor.close();


        database.close();
        return contatos;
    }

    public  List<Contato> buscaContato(String nome)
    {
        database=dbHelper.getReadableDatabase();
        List<Contato> contatos = new ArrayList<>();

        Cursor cursor;

        String[] cols=new String[] {SQLiteHelper.KEY_ID,
                SQLiteHelper.KEY_NAME,
                SQLiteHelper.KEY_FONE,
                SQLiteHelper.KEY_EMAIL,
                SQLiteHelper.KEY_FAVORITO,
                SQLiteHelper.KEY_FONE2,
                SQLiteHelper.KEY_NASCIMENTO};
        String where=SQLiteHelper.KEY_NAME + " like ?";
        String[] argWhere=new String[]{nome + "%"};


        /*cursor = database.query(SQLiteHelper.DATABASE_TABLE, cols, where , argWhere,
                null, null, SQLiteHelper.KEY_NAME);*/

        cursor = database.query(SQLiteHelper.DATABASE_TABLE, cols,
                SQLiteHelper.KEY_NAME + " like '%" + nome + "%' or " + SQLiteHelper.KEY_EMAIL + " like '%" + nome + "%'",
                null,
                null,
                null,
                SQLiteHelper.KEY_NAME);

        while (cursor.moveToNext())
        {
            Contato contato = new Contato();
            contato.setId(cursor.getInt(0));
            contato.setNome(cursor.getString(1));
            contato.setFone(cursor.getString(2));
            contato.setEmail(cursor.getString(3));
            contato.setFavorito(cursor.getInt(4));
            contato.setFone2(cursor.getString(5));
            contato.setNascimento(cursor.getString(6));
            contatos.add(contato);


        }
        cursor.close();

        database.close();
        return contatos;
    }

    public void salvaContato(Contato c) {

        database=dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.KEY_NAME, c.getNome());
        values.put(SQLiteHelper.KEY_FONE, c.getFone());
        values.put(SQLiteHelper.KEY_EMAIL, c.getEmail());
        values.put(SQLiteHelper.KEY_FAVORITO, c.getFavorito());
        values.put(SQLiteHelper.KEY_FONE2, c.getFone2());
        values.put(SQLiteHelper.KEY_NASCIMENTO, c.getNascimento());

       if (c.getId()>0)
          database.update(SQLiteHelper.DATABASE_TABLE, values, SQLiteHelper.KEY_ID + "="
                + c.getId(), null);
        else
           database.insert(SQLiteHelper.DATABASE_TABLE, null, values);

        database.close();

    }

    public void favoritarContato(int id, int favorito){

        database=dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.KEY_FAVORITO, favorito);

        database.update(SQLiteHelper.DATABASE_TABLE, values, SQLiteHelper.KEY_ID + "="
                + id, null);
    }

    public void apagaContato(Contato c)
    {
        database=dbHelper.getWritableDatabase();
        database.delete(SQLiteHelper.DATABASE_TABLE, SQLiteHelper.KEY_ID + "="
                + c.getId(), null);

        database.close();
    }
}
