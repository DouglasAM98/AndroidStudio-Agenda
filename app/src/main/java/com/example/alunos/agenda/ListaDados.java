package com.example.alunos.agenda;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListaDados extends AppCompatActivity {
    private ListView lstConteudo;
    ArrayAdapter<String> adpConteudo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_dados);
        lstConteudo=(ListView)(findViewById(R.id.lstConteudo));
        adpConteudo=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        carregarLista();
    }

    private void carregarLista() {
        Bundle b=getIntent().getExtras();
        ArrayList<String> list=new ArrayList<>();
        list=b.getStringArrayList("DADOS");
        for (int i=0;i<list.size();i++) {
            adpConteudo.add(list.get(i));
        }
        lstConteudo.setAdapter(adpConteudo);
        /*adpConteudo.add(list.get(0));
        adpConteudo.add(list.get(1));
        adpConteudo.add(list.get(2));
        adpConteudo.add(list.get(3));
        adpConteudo.add(list.get(4));
        lstConteudo.setAdapter(adpConteudo);*/
    }
}
