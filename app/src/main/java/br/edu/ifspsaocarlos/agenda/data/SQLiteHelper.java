package br.edu.ifspsaocarlos.agenda.data;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "agenda.db";
    static final String DATABASE_TABLE = "contatos";
    static final String KEY_ID = "id";
    static final String KEY_NAME = "nome";
    static final String KEY_FONE = "fone";
    static final String KEY_FONE2 = "fone2";
    static final String KEY_EMAIL = "email";
    static final String KEY_FAVORITO = "favorito";
    static final String KEY_NASCIMENTO = "nascimento";
    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_CREATE = "CREATE TABLE "+ DATABASE_TABLE +" (" +
            KEY_ID  +  " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_NAME + " TEXT NOT NULL, " +
            KEY_FONE + " TEXT, "  +
            KEY_EMAIL + " TEXT);";

    SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i("DBERRO", "PASSOU PELO CRIADOR");
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int    newVersion) {
        if(oldVersion < 2){

            try {
                String sql = "alter table " + DATABASE_TABLE + " add column " + KEY_FAVORITO + " integer";
                database.execSQL(sql);

                sql = "update " + DATABASE_TABLE + " set " + KEY_FAVORITO + " = 0";
                database.execSQL(sql);

                Log.i("DBERRO", "Atualizou o BANCO DE DADOS");
            }catch (Exception e){
                Log.i("DBERRO", e.getMessage());
            }
        }

        if(oldVersion < 3){

            String sql = "alter table " + DATABASE_TABLE + " add column " + KEY_FAVORITO + " integer";
            database.execSQL(sql);

            sql = "update " + DATABASE_TABLE + " set " + KEY_FAVORITO + " = 0";
            database.execSQL(sql);
        }

        if(oldVersion < 4){

            String sql = "alter table " + DATABASE_TABLE + " add column " + KEY_FONE2 + " TEXT";
            database.execSQL(sql);

            Log.i("DBERRO", "Atualizou o BANCO DE DADOS 4");
        }

        if(oldVersion < 5){

            String sql = "alter table " + DATABASE_TABLE + " add column " + KEY_NASCIMENTO + " TEXT";
            database.execSQL(sql);

            Log.i("DBERRO", "Atualizou o BANCO DE DADOS 4");
        }
    }
}

