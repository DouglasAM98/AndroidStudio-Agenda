package com.example.alunos.agenda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class CRUD {
    private SQLiteDatabase conn;
    public CRUD(SQLiteDatabase conn){
        this.conn = conn;
    }
    public void InserirCompromisso(GetSetTable compromisso){
        ContentValues valores=new ContentValues();
        valores.put("DESCRICAO",compromisso.getDesc());
        valores.put("DATA",compromisso.getData());
        valores.put("HORA",compromisso.getHora());
        valores.put("LOCAL",compromisso.getLocal());
        valores.put("CONTATO",compromisso.getContato());
        valores.put("TIPO",compromisso.getTipo());
        conn.insertOrThrow("TBL_AGENDA",null,valores);

    }

    public ArrayAdapter<GetSetTable> listaCompromisso(Context context) {
        ArrayAdapter<GetSetTable> adpCompromisso
                = new ArrayAdapter<GetSetTable>(context, android.R.layout.simple_list_item_1);
        Cursor cursor = conn.query("TBL_AGENDA", null, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                GetSetTable compromisso = new GetSetTable();
                compromisso.setId(Integer.valueOf(cursor.getString(0)));
                compromisso.setDesc(cursor.getString(1));
                compromisso.setData(cursor.getString(2));
                compromisso.setHora(cursor.getString(3));
                compromisso.setLocal(cursor.getString(4));
                compromisso.setContato(cursor.getString(5));
                compromisso.setTipo(cursor.getString(6));
                adpCompromisso.add(compromisso);
            } while (cursor.moveToNext());
        }
        return adpCompromisso;
    }
}