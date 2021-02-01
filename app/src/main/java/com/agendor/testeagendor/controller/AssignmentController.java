package com.agendor.testeagendor.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.agendor.testeagendor.model.Assignment;
import com.agendor.testeagendor.model.dao.CreateSQLiteDatabase;
import com.agendor.testeagendor.model.enums.AssignmentType;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robson Freitas 28/01/2021
 */
public class AssignmentController {

    private CreateSQLiteDatabase database;
    private SQLiteDatabase db;
    //private SimpleDateFormat dateFormat;
    private DateTimeFormatter formatter;

    public AssignmentController(Context context){
        database = new CreateSQLiteDatabase(context);
        //dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        formatter = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm:ss");
    }

    /**
     * Insere os dados no banco.
     * @param type Type
     * @param date Calendar
     * @param client String
     * @param description String
     * @param done boolean
     * @return boolean
     */
    public boolean insert(AssignmentType type, DateTime date, String client, String description, boolean done) throws Exception{
        if (type == null){
            throw new Exception("Escolha um tipo nos ícones acima");
        }
        if (date == null){
            throw new Exception("Data não pode ser vazia");
        }

        if (client == null || client.replaceAll("\\s+", "").isEmpty()){
            throw new Exception("Cliente não pode ser vazio");
        }
        if(date.isBefore(DateTime.now())){
            throw new Exception("Agende somente datas futuras.");
        }

        long result = 0;
        try {
            ContentValues values;
            db = database.getWritableDatabase();
            values = new ContentValues();
            values.put(CreateSQLiteDatabase.TYPE, type.toString());

            String dateFormatValue = date.toString(formatter);

            values.put(CreateSQLiteDatabase.DATE, dateFormatValue);
            values.put(CreateSQLiteDatabase.CLIENT, client);
            values.put(CreateSQLiteDatabase.DESCRIPTION, description);
            values.put(CreateSQLiteDatabase.DONE, done);

            result = db.insert(CreateSQLiteDatabase.TABLE, null, values);
            db.close();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return result == -1 ? false : true;
    }

    /**
     * Atualiza o Assignment selecionado no banco.
     * @param id Integer
     * @param type AssignmentType
     * @param date DateTime
     * @param client String
     * @param description String
     * @param done boolean
     * @return boolean
     */
    public boolean update(Integer id, AssignmentType type, DateTime date, String client, String description, boolean done){
        ContentValues values;
        db = database.getWritableDatabase();
        values = new ContentValues();
        values.put(CreateSQLiteDatabase.TYPE, type.toString());
        String dateFormatValue = date.toString(formatter);
        values.put(CreateSQLiteDatabase.DATE, dateFormatValue);
        values.put(CreateSQLiteDatabase.CLIENT, client);
        values.put(CreateSQLiteDatabase.DESCRIPTION, description);
        values.put(CreateSQLiteDatabase.DONE, done);
        String whereClause = "ID = " + id;
        long result = db.update(CreateSQLiteDatabase.TABLE,values, whereClause, null);
        db.close();

        return result == -1 ? false : true;
    }

    /**
     * Consulta o banco e cria uma lista de Assignment
     * @return List<Assignment>
     */
    public List<Assignment> read() throws Exception {
        List<Assignment> list = new ArrayList<>();
        Cursor cursor;
        String[] campos = { database.ID, database.TYPE, database.DATE, database.CLIENT, database.DESCRIPTION, database.DONE};
        try {
            db = database.getReadableDatabase();
            cursor = db.query(database.TABLE, campos, null, null, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        Assignment assignment = new Assignment();
                        assignment.setId(cursor.getLong(0));
                        assignment.setType(AssignmentType.valueOf(cursor.getString(1)));

                        DateTime dateValue = formatter.parseDateTime(cursor.getString(2));
                        assignment.setDate(dateValue);

                        assignment.setClient(cursor.getString(3));
                        assignment.setDescription(cursor.getString(4));
                        assignment.setDone(cursor.getInt(5));

                        list.add(assignment);
                    } while (cursor.moveToNext());
                }
            }
            db.close();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return list;
    }
}
