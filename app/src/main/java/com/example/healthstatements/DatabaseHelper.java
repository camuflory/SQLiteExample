package com.example.healthstatements;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.LinkedList;

public class DatabaseHelper extends SQLiteOpenHelper {

    /**
     * how to create private const?
     * just select a value and press Ctrl+Alt+C;
     **/

    private static final String MY_TABLE = "my_table";            // name of database
    private static final String COLUMN_NAME = "column_name";      // name of column with names
    private static final String COLUMN_SURNAME = "column_surname";// name of column with surnames
    private static final String COLUMN_AGE = "column_age";        // name of column with ages

    public DatabaseHelper(@Nullable Context context) {
        /**
         * context - для определения пути к фалйлу
         * name ("example.db") - имя файла базы данных, null для базы данных, хранящейся в памяти
         * factory (null) - для создания объектов курсора, null по умолчанию
         * version (1) - версия базы данных, начинается с 1
         **/
        super(context, "example.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /** метод на создание таблицы в базе данных */
        // execSQL - выполняет single SQL-запрос, который не (выборка)/(запрос с возвратом данных)
        // на нормальном SQL запрос выглядел бы так:
        // CREATE TABLE my_table (column_name TEXT, column_surname TEXT, column_age INTEGER);
        db.execSQL("CREATE TABLE "+MY_TABLE+" ("+COLUMN_NAME+" TEXT, "+COLUMN_SURNAME+" TEXT, "+
                COLUMN_AGE+" INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public void deleteAll(){
        /** метод на удаление всех записей из таблицы по нажатию на кнопку */
        // getWritableDatabase() - метод, ктороый позволяет изменять/добавлять записи в таблице
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MY_TABLE, null, null);// удаляем всю таблицу
        db.close(); // закрываем таблицу, чтобы не было утечки данных
    }

    public void addOne(Data data) { // один объект класса Data, то есть три переменные
        /** метод, добавляющий в таблицу базы данных введенные строки с экрана */
        // один объект класса Data, то есть три переменные
        SQLiteDatabase db = this.getWritableDatabase();
        // ContentValues -класс для добавления new строк в таблицу, своеобразный hashMap для баз данных
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, data.name);
        cv.put(COLUMN_SURNAME, data.surname);
        cv.put(COLUMN_AGE, data.age);
        db.insert(MY_TABLE, null, cv); // вставляются данные в таблицу
        db.close(); // закрываем таблицу, чтобы не было утечки данных
    }

    public LinkedList<Data> getAll() {
        /** метод, который отправляет все данные из бд в виде LinkedList'а */
        LinkedList<Data> myList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        // Cursor - объекты класса return запросы к бд, объекты ссылаются на choosed data in bd
        // query() - метод для запроса-выборки к базе данных
        Cursor cursor = db.query(MY_TABLE, null, null, null, null,
                null, null);
        // moveToFirst() - проверяет вернулся ли пустой набор, moves курсор к 1 not null резу
        if (cursor.moveToFirst()) {
            do {
                int idName = cursor.getColumnIndex(COLUMN_NAME);
                int idSurname = cursor.getColumnIndex(COLUMN_SURNAME);
                int idAge = cursor.getColumnIndex(COLUMN_AGE);
                Data data = new Data(cursor.getString(idName), cursor.getString(idSurname),
                        cursor.getInt(idAge));
                myList.add(data);
            } while (cursor.moveToNext());
        }
        db.close();
        return myList;
    }
}
