package com.example.healthstatements;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper helper;
    private TextView textViewData;
    private EditText name, surname, age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewData = findViewById(R.id.TextViewData);
        name = findViewById(R.id.EditTextName);
        surname = findViewById(R.id.EditTextSurname);
        age = findViewById(R.id.EditTextAge);

        helper = new DatabaseHelper(this);
    }

    public void onDelete(View view) {
        /** метод кнопки, по нажатию на которую удаляются все записи из editText'ов */
        textViewData.setText("");
        name.setText("");
        surname.setText("");
        age.setText("");
        helper.deleteAll();
    }

    public void onAdd(View view) {
        /** метод кнопки, по нажатию на которую добавляются данные из current editText'ов */
        Data data = new Data(name.getText().toString(), surname.getText().toString(),
                Integer.parseInt(age.getText().toString()));
        helper.addOne(data);
    }

    public void onGet(View view) {
        /** метод кнопки, по нажатию на которую в txt поле добавляются все значения из бд */
        LinkedList<Data> myList = helper.getAll();
        for (Data data : myList) {
            textViewData.append(data.name + " " + data.surname + " " + data.age + "\n");
        }
    }
}