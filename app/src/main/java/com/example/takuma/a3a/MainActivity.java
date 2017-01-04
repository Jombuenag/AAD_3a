package com.example.takuma.a3a;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

//static file stored in the internal storage called text.txt
    final static String FILE_NAME = "text.txt";
//variable created for aknowledge the app that the file has been deleted
    boolean isDeleted = false;
//widgets
    Button btnInput, btnOutput, btnDelete;
    EditText etInput;
    TextView tvOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnInput = (Button)findViewById(R.id.btnInput);
        btnOutput = (Button)findViewById(R.id.btnOutput);
        btnDelete =(Button)findViewById(R.id.btnDelete);
        etInput = (EditText)findViewById(R.id.txtInput);
        tvOutput = (TextView)findViewById(R.id.txtOutput);

        btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//get what is writted in the editText "etInput" and save it
                saveText(etInput.getText().toString());
            }
        });
        btnOutput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//load the text saved previously in the saveText() method
                loadText();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//delete text.txt and make the user know that this file no longer exists
                deleteText();
                Toast.makeText(MainActivity.this, "File has been deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }
//method which it's in charge of handle the streams and write the info to the file
//also this checks if the user has written something or the text variable is empty
    private void saveText(String text){
//if it's empty
        if(text.length() == 0) {
            Toast.makeText(this, "Please write something", Toast.LENGTH_SHORT).show();
        }else{
//if isn't empty, set boolean variable isDeleted to false
//write the file with the text inside stringOutput variable
            try {
                isDeleted = false;
//give me the file and open it for future writting
                FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_APPEND);
                String stringOutput = new String(text + "\n");
//stream in charge of writting the info
                DataOutputStream dos = new DataOutputStream(fos);
                dos.writeBytes(stringOutput);
//close it, it's all about good maners
                fos.close();
//possible exceptions captured
            } catch (FileNotFoundException FNFE) {
                FNFE.printStackTrace();
            } catch (IOException IOE) {
                IOE.printStackTrace();
            }
        }
    }
//method in charge of loading the text stored in the file previously
//also it checks if the file has been deleted and warns the user
    private void loadText() {
//if it's not deleted open the file,
//read the bytes of the file and put the obtained text in the textView
        if (isDeleted == false) {
            try {
                FileInputStream fis = openFileInput(FILE_NAME);
                DataInputStream dis = new DataInputStream(fis);

                byte[] readed = new byte[1024];
                dis.read(readed);
                tvOutput.setText(new String(readed));
//close it, it's all about good maners
                fis.close();
//possible exceptions captured
            } catch (IOException IOE) {
                IOE.printStackTrace();
            }
//if its deleted warn the user
        }else{
            isDeleted = true;
            Toast.makeText(this, "File doesn't exist, please write something to create a new file", Toast.LENGTH_SHORT).show();
        }

    }
//method which delete the file inside the storage and clean the textView
    private void deleteText(){
        isDeleted = deleteFile(FILE_NAME);
        tvOutput.setText("");
    }
}
