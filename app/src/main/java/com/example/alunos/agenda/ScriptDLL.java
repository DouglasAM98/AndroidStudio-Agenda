package com.example.alunos.agenda;

public class ScriptDLL {
    public static String createTable(){
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE IF NOT EXISTS TBL_AGENDA(");
        sql.append("ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,");
        sql.append("DESCRICAO VARCHAR(100),");
        sql.append("DATA VARCHAR(10),");
        sql.append("HORA VARCHAR(6),");
        sql.append("LOCAL VARCHAR(100),");
        sql.append("CONTATO VARCHAR (40),");
        sql.append("TIPO VARCHAR(15))");
        return sql.toString();

    }
}