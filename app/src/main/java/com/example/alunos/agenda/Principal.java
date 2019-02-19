package com.example.alunos.agenda;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class Principal extends AppCompatActivity
        implements View.OnClickListener{
    private EditText edtData,edtHora,edtDesc,edtLocal,edtContato;
    private Spinner spTipo;
    private ArrayAdapter<String> adpTipo;
    private int ano,mes,dia,hora,minuto;

    private DataBase db;
    private CRUD crud;
    private SQLiteDatabase conn;
    private GetSetTable compromisso;
    private ListView lstCompromiso;

    private ArrayAdapter<GetSetTable> adpCompromisso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtData=(EditText)(findViewById(R.id.edtData));
        edtHora=(EditText)(findViewById(R.id.edtHora));
        edtDesc=(EditText)(findViewById(R.id.edtDesc));
        edtLocal=(EditText)(findViewById(R.id.edtLocal));
        edtContato=(EditText)(findViewById(R.id.edtContato));
        spTipo=(Spinner)(findViewById(R.id.spTipo));
        lstCompromiso = (ListView)(findViewById(R.id.lstCompromisso));

        compromisso=new GetSetTable();

        edtData.setOnClickListener(this);
        edtHora.setOnClickListener(this);

        adpTipo=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);
        tipoContato();
        criarBanco();
        listarCompromisso();
    }

    private void listarCompromisso() {
        adpCompromisso = crud.listaCompromisso(this);
        lstCompromiso.setAdapter(adpCompromisso);
    }

    private void tipoContato() {
        adpTipo.add("Telefone");
        adpTipo.add("Email");
        adpTipo.add("Whatsapp");
        spTipo.setAdapter(adpTipo);
    }

    @Override
    public void onClick(View v) {
        if(v==edtData){
            carregarData();
        }else if(v==edtHora) {
            carregarHora();
        }
    }

    private void enviarDados() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < adpCompromisso.getCount(); i++) {
        list.add(adpCompromisso.getItem(i).toString());
    }

        Intent destino=new Intent(Principal.this,ListaDados.class);
        destino.putExtra("DADOS",list);
        startActivity(destino);
    }

    private void carregarHora() {
        Calendar c=Calendar.getInstance();
        hora=c.get(Calendar.HOUR_OF_DAY);
        minuto=c.get(Calendar.MINUTE);
        TimePickerDialog relogio;
        relogio=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int h, int m) {
                edtHora.setText(h+":"+m);
            }
        },hora,minuto,true);
        relogio.show();
    }
    private void carregarData() {
        Calendar c=Calendar.getInstance();
        ano=c.get(Calendar.YEAR);
        mes=c.get(Calendar.MONTH);
        dia=c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog calendario;
        calendario=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int a, int m, int d) {
                edtData.setText(d+"/"+(m+1)+"/"+a);
            }
        },ano,mes,dia);
        calendario.show();
    }
    private void limpar() {
        edtData.setText("");
        edtHora.setText("");
        edtDesc.setText("");
        edtContato.setText("");
        edtLocal.setText("");
    }
    private void criarBanco(){
        try{
            db =new DataBase(this);
            conn=db.getWritableDatabase();
            crud=new CRUD(conn);
        }catch (SQLException ex){
            AlertDialog.Builder msg=new AlertDialog.Builder(this);
            msg.setTitle("Alerta!");
            msg.setMessage("Erro ao criar banco"+ex.getMessage());
            msg.setNeutralButton("Ok",null);
            msg.show();
        }
    }
    private void salvar() {
        try {
            compromisso.setData(edtData.getText().toString());
            compromisso.setHora(edtHora.getText().toString());
            compromisso.setDesc(edtDesc.getText().toString());
            compromisso.setContato(edtContato.getText().toString());
            compromisso.setLocal(edtLocal.getText().toString());
            compromisso.setTipo(spTipo.getSelectedItem().toString());
            crud.InserirCompromisso(compromisso);
            Toast.makeText(this, "Informações salvas com sucesso", Toast.LENGTH_SHORT).show();

        } catch (Exception ex) {
            AlertDialog.Builder msg = new AlertDialog.Builder(this);
            msg.setTitle("Alerta!");
            msg.setMessage("Erro ao salvar dados " + ex.getMessage());
            msg.setNeutralButton("Ok", null);
            msg.show();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.mnSalvar:
                criarBanco();
                salvar();
                break;
            case R.id.mnExcluir:

                break;
            case R.id.mnAlterar:

                break;
            case R.id.mnListar:
                enviarDados();
                break;
            case R.id.mnLimpar:
                limpar();
                break;
            case R.id.mnSair:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}