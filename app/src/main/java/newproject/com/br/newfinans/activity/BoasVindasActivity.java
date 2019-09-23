package newproject.com.br.newfinans.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import newproject.com.br.newfinans.R;
import newproject.com.br.newfinans.util.Preference;
import newproject.com.br.newfinans.util.SQLLite;

public class BoasVindasActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boas_vindas);

        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                TelaInicial();
                SQLLite.dataBase = CreateDataBase();
                SQLLite.CreateTable();
                SQLLite.AlterTable();
            }
        }, 2000);
    }

    private void TelaInicial() {
        Intent intent = new Intent(BoasVindasActivity.this,
                HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private SQLiteDatabase CreateDataBase(){
        @SuppressLint("WrongConstant") SQLiteDatabase bancoDados = openOrCreateDatabase("NewFinans",MODE_APPEND,null);
        String path = bancoDados.getPath();
        return bancoDados;
    }
}