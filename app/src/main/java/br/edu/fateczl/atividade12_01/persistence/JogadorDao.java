package br.edu.fateczl.atividade12_01.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.edu.fateczl.atividade12_01.model.Jogador;
import br.edu.fateczl.atividade12_01.model.Time;

public class JogadorDao implements IJogadorDao, ICRUDDao<Jogador>{
    /*
     * @author: Gustavo Guimarães de Oliveira
     */
    private final Context context;
    private GenericDao gDao;
    private SQLiteDatabase db;

    public JogadorDao(Context context) {
        this.context = context;
    }

    @Override
    public JogadorDao open() throws SQLException {
        gDao = new GenericDao(context);
        db = gDao.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        gDao.close();
    }

    @Override
    public void insert(Jogador jogador) throws SQLException {
        ContentValues contentValues = getContentValues(jogador);
        db.insert("jogador", null, contentValues);
    }

    @Override
    public int update(Jogador jogador) throws SQLException {
        ContentValues contentValues = getContentValues(jogador);
        return db.update("jogador", contentValues, "id = " + jogador.getId(), null);
    }

    @Override
    public void delete(Jogador jogador) throws SQLException {
        db.delete("jogador", "id = " + jogador.getId(), null);
    }

    @SuppressLint("Range")
    @Override
    public Jogador findOne(Jogador jogador) throws SQLException {
        System.out.println("Jogador DAO Find One");
        String sql = "SELECT j.id AS cod_jog, j.nome AS nome_jog, j.nasc AS nasc_jog, j.altura AS altura_jog, " +
                "j.peso AS peso_jog, t.codigo, t.nome, t.cidade " +
                "FROM jogador j INNER JOIN time t " +
                "ON j.codigo_time = t.codigo " +
                "AND j.id = " + jogador.getId();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            System.out.println("Cursor Nulo");
            cursor.moveToFirst();
        }
        if (!cursor.isAfterLast()) {
            jogador.setId(cursor.getInt(cursor.getColumnIndex("cod_jog")));
            jogador.setNome(cursor.getString(cursor.getColumnIndex("nome_jog")));
            jogador.setDataNasc(LocalDate.parse(cursor.getString(cursor.getColumnIndex("nasc_jog"))));
            jogador.setAltura(cursor.getFloat(cursor.getColumnIndex("altura_jog")));
            jogador.setPeso(cursor.getFloat(cursor.getColumnIndex("peso_jog")));

            Time time = new Time();
            time.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            time.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            time.setCidade(cursor.getString(cursor.getColumnIndex("cidade")));

            jogador.setTime(time);
            System.out.println(time.toString());
            System.out.println(jogador.toString());
        }
        cursor.close();
        return jogador;
    }

    @SuppressLint("Range")
    @Override
    public List<Jogador> findAll() throws SQLException {
        List<Jogador> jogadores = new ArrayList<>();
        String sql = "SELECT j.id AS cod_jog, j.nome AS nome_jog, j.nasc AS nasc_jog, j.altura AS altura_jog, " +
                "j.peso AS peso_jog, t.codigo, t.nome, t.cidade " +
                "FROM jogador j INNER JOIN time t " +
                "ON j.codigo_time = t.codigo ";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()) {
            Jogador jogador = new Jogador();
            jogador.setId(cursor.getInt(cursor.getColumnIndex("cod_jog")));
            jogador.setNome(cursor.getString(cursor.getColumnIndex("nome_jog")));
            jogador.setDataNasc(LocalDate.parse(cursor.getString(cursor.getColumnIndex("nasc_jog"))));
            jogador.setAltura(cursor.getFloat(cursor.getColumnIndex("altura_jog")));
            jogador.setPeso(cursor.getFloat(cursor.getColumnIndex("peso_jog")));

            Time time = new Time();
            time.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            time.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            time.setCidade(cursor.getString(cursor.getColumnIndex("cidade")));

            jogador.setTime(time);
            System.out.println(time.toString());
            System.out.println(jogador.toString());

            jogadores.add(jogador);
            cursor.moveToNext();
        }
        cursor.close();
        return jogadores;
    }

    private ContentValues getContentValues(Jogador jogador) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", jogador.getId());
        contentValues.put("nome", jogador.getNome());
        contentValues.put("nasc", jogador.getDataNasc().toString());
        contentValues.put("altura", jogador.getAltura());
        contentValues.put("peso", jogador.getPeso());
        contentValues.put("codigo_time", jogador.getTime().getCodigo());
        return contentValues;
    }
}
