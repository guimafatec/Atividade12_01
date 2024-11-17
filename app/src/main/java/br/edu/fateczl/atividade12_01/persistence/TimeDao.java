package br.edu.fateczl.atividade12_01.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.fateczl.atividade12_01.model.Time;

public class TimeDao implements ITimeDao, ICRUDDao<Time> {
    /*
     * @author: Gustavo Guimarães de Oliveira
     */
    private final Context context;
    private GenericDao gDao;
    private SQLiteDatabase db;

    public TimeDao(Context context) {
        this.context = context;
    }

    @Override
    public TimeDao open() throws SQLException {
        gDao = new GenericDao(context);
        db = gDao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gDao.close();
    }

    @Override
    public void insert(Time time) throws SQLException {
        ContentValues contentValues = getContentValues(time);
        db.insert("time", null, contentValues);
    }

    @Override
    public int update(Time time) throws SQLException {
        ContentValues contentValues = getContentValues(time);
        return db.update("time", contentValues, "codigo = " + time.getCodigo(), null);
    }

    @Override
    public void delete(Time time) throws SQLException {
        db.delete("time", "codigo = " + time.getCodigo(), null);
    }

    @SuppressLint("Range")
    @Override
    public Time findOne(Time time) throws SQLException {
        System.out.println("Find One");
        String sql = "SELECT codigo, nome, cidade FROM time WHERE codigo = " + time.getCodigo();

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            System.out.println("Cursor nulo!");
            cursor.moveToFirst();
        }
        if (!cursor.isAfterLast()) {
            System.out.println("Cursor não nulo!");
            time.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            time.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            time.setCidade(cursor.getString(cursor.getColumnIndex("cidade")));
        }
        cursor.close();
        return time;
    }

    @SuppressLint("Range")
    @Override
    public List<Time> findAll() throws SQLException {
        List<Time> times = new ArrayList<>();
        String sql = "SELECT codigo, nome, cidade FROM time";

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()) {
            Time time = new Time();
            time.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            time.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            time.setCidade(cursor.getString(cursor.getColumnIndex("cidade")));

            times.add(time);
            cursor.moveToNext();
        }
        cursor.close();
        return times;
    }

    private ContentValues getContentValues(Time time) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("codigo", time.getCodigo());
        contentValues.put("nome", time.getNome());
        contentValues.put("cidade", time.getCidade());
        return contentValues;
    }
}
