package newproject.com.br.newfinans.activity;

import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import newproject.com.br.newfinans.R;
import newproject.com.br.newfinans.util.Preference;
import newproject.com.br.newfinans.util.SQLLite;

public class ConfigActivity extends AppCompatActivity {

    Button btZerarBanco;
    Button btBackup;
    Button btAjuda;
    Switch swMultiPagamentos;
    Switch swDetalhe;
    private Preference pf;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        btZerarBanco = (Button) findViewById(R.id.idCBtZerarBanco);
        btBackup = (Button) findViewById(R.id.idCBtBackup);
        btAjuda = (Button) findViewById(R.id.idCBtAjuda);
        swMultiPagamentos = (Switch) findViewById(R.id.idCSwConfirma);
        swDetalhe = (Switch) findViewById(R.id.idCSwDetalhe);


        pf = new Preference(this.getSharedPreferences("Config", this.MODE_PRIVATE));
        swMultiPagamentos.setChecked(pf.preferences.getBoolean("multiplos_pagamentos",false));
        swDetalhe.setChecked(pf.preferences.getBoolean("detalhes",false));
        btZerarBanco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ConfigActivity.this);
                builder.setTitle("Confirmação");
                builder.setMessage("Tem certeza que deseja realizar está operaçao?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        SQLLite.DropTable();
                        SQLLite.CreateTable();
                        Toast.makeText(ConfigActivity.this,"Concluído",Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(ConfigActivity.this, "Oparação Cancelada!", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.create();
                builder.show();
            }
        });

        btBackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ConfigActivity.this,"Opção disponível na versão Premium!",Toast.LENGTH_LONG).show();
            }
        });

        btAjuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ConfigActivity.this,"Para EXCLUIR precione e segure.",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pf.editor.putBoolean("multiplos_pagamentos",swMultiPagamentos.isChecked());
        pf.editor.putBoolean("detalhes",swDetalhe.isChecked());
        pf.editor.commit();
    }
}
