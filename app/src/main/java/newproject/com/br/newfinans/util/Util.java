package newproject.com.br.newfinans.util;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;


/**
 * Created by Jadison on 08/01/2018.
 */

public class Util {

    public static String[] Meses={"Janeiro","Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"};
    public static String[] Anos={"2017","2018","2019","2020","2021","2022"};
    public static String NumeroMes;
    public static Date Hoje = new Date();

    /*=======================================================*/
    public static void OcultaTeclado(View v,Activity activity){
        ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                v.getWindowToken(), 0);
    }
    /*=========================================================*/
    public static String MaskString(String valor, String Format){
        String Result = "";
        valor = StrNotNull(valor);
        if (!valor.equals("")) {
            for (int i = 0; i < Format.length(); i++) {
                if (Format.charAt(i) == '#') {
                    Result += Copy(valor, 0, 1);
                    valor = Copy(valor, 1, valor.length());
                } else {
                    Result += String.valueOf(Format.charAt(i));
                }
            }
        }
        return Result;
    }
    /*=========================================*/
    public static void MaskDate(EditText Edite) {
        SimpleMaskFormatter SimplesMaskData = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher MaskData = new MaskTextWatcher(Edite, SimplesMaskData);
        Edite.addTextChangedListener(MaskData);
    }
    /*============================================*/
    public static void MaskCelular(EditText Edite) {
        SimpleMaskFormatter SimplesMaskCelular = new SimpleMaskFormatter("(NN) N NNNN-NNNN");
        MaskTextWatcher MaskData = new MaskTextWatcher(Edite, SimplesMaskCelular);
        Edite.addTextChangedListener(MaskData);
    }
    /*=============================================*/
    public static void MaskTelefone(EditText Edite) {
        SimpleMaskFormatter SimplesMaskTelefone = new SimpleMaskFormatter("(NN) NNNN-NNNN");
        MaskTextWatcher MaskData = new MaskTextWatcher(Edite, SimplesMaskTelefone);
        Edite.addTextChangedListener(MaskData);
    }
    /*=====================================================*/
    public static TextWatcher Numeric(final EditText Edite) {
        return new TextWatcher() {
            private boolean mUpdating;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence cs, int i, int i1, int i2) {
                if (this.mUpdating) {
                    this.mUpdating = false;
                    return;
                }
                this.mUpdating = true;
                String Str = MaskNumeric(cs.toString());
                if (Str.equals("0.00")){
                    Edite.setText(null);
                }else{
                    Str="R$ "+Str;
                    Edite.setText(Str);
                    Edite.setSelection(Str.length());
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        };
    }
    /*=============================================*/
    private static String MaskNumeric(String valor) {
        BigDecimal parsed = null;
        try {
            String cleanString = valor.replaceAll("[R,$,., ]", "");
            parsed = new BigDecimal(cleanString).setScale(2, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(100), BigDecimal.ROUND_FLOOR);
        } catch (Exception e) {
            Log.e("sua_tag", e.getMessage(), e);
        }
        return parsed.toString();
    }
    /*=============================================*/
    public static Float getNumeric(String valor) {
        BigDecimal parsed = null;
        try {
            String cleanString = valor.replaceAll("[R,$,., ]", "");
            parsed = new BigDecimal(cleanString).setScale(2, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(100), BigDecimal.ROUND_FLOOR);
        } catch (Exception e) {
            Log.e("sua_tag", e.getMessage(), e);
        }
        return parsed.floatValue();
    }
    /*==============================================*/
    public static String SetMaskNumeric(Float valor,String moeda) {
        String Result = "";
        String MascNumeric = String.format("%.2f",valor);
        MascNumeric = MascNumeric.replaceAll("[.,]","");
        if (!MascNumeric.equals("")) {
            int i = 0;
            while (i<MascNumeric.length()) {
                if (MascNumeric.length()-i == 3) {
                    Result += String.valueOf(MascNumeric.charAt(i))+",";
                } else if (((MascNumeric.length()-i)-2)%3==1 && ((MascNumeric.length()-i)-3)/3!=0 ){
                    Result += String.valueOf(MascNumeric.charAt(i))+".";
                } else {
                    Result += String.valueOf(MascNumeric.charAt(i));
                }
                i++;
            }
        }
        if (!StrNotNull(moeda).equals(""))
            MascNumeric = moeda+" "+Result;
        else
            MascNumeric = Result;
        return MascNumeric;
    }
    /*=========================================*/
    public static String StrNotNull(String valor) {
        if (valor != null)
            return valor;
        else
            return "";
    }
    /*================================*/
    public static String Trim(String S){
        S = S.replaceAll(" ","");
        return S;
    }
    /*================================================*/
    public static String TrimMask(String S,String Mask){
        S = S.replaceAll(Mask,"");
        return S;
    }
    /*======================================================*/
    public static String PreencheZeros(String S,Integer Qtde){
        Integer i = 0;
        String Retorno = S;
        if (S.length()<Qtde){
            for (i=0;i<Qtde-S.length();i++){
                Retorno="0"+Retorno;
            }
        }
        return Retorno;
    }
    /*========================================*/
    public static Boolean TestBoolean(String B){
        Boolean S;
        if (B.equals("T")){
            S = true;
        }else{
            S = false;
        }
        return S;
    }
    /*=========================================================*/
    public static String Copy(String B,Integer Ini, Integer Fim){
        String S = B;
        String Aux;
        if (B.length()>Fim){
            Aux = S.substring(Ini,Fim);
        }else {
            Aux = S.substring(Ini,B.length());
        }
        return Aux;
    }
    /*===================================================================*/
    public static Integer AchaDiaComp(Integer DiaComp,Integer Vencimento){
        Calendar c = Calendar.getInstance();
        c.setTime(Hoje);
        Integer Mes = c.get(Calendar.MONTH);
        Integer Dia = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        Integer Result;
        if (Dia==30 && DiaComp==31)
            DiaComp=30;
        else if ( Mes==2 && DiaComp <= 30 && DiaComp > Vencimento) {
            Result = 30 - DiaComp;
            return 27 - Result;
        }
        return DiaComp;
    }
    /*=============================================================================================*/
    public static View.OnFocusChangeListener ValidaData (final EditText Edit, final Context context){
        return new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String DataArmzenada = Edit.getText().toString();
                if (!b && !Edit.getText().toString().equals("")) {
                    if (Edit.getText().toString().length()>6 && Edit.getText().toString().length()<10){
                        String Temp = Copy(DataArmzenada,0,6)+"2"+PreencheZeros(Copy(DataArmzenada,6,DataArmzenada.length()),3);
                        DataArmzenada = Temp;
                    }else {
                        Edit.setText(null);
                    }
                    String pattern = "dd/MM/yyyy";
                    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                    sdf.setLenient(false);

                    Date date;

                    try {
                        date = sdf.parse(DataArmzenada);
                        Edit.setText(DataArmzenada);
                    } catch (ParseException e) {
                        Edit.setText(null);
                        Toast.makeText(context, "Data Inválida!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        };
    }
    /*======================================*/
    public static Date DataRetorno (String S){
        String DataArmzenada = S;
        Date date = null;
        if (!S.equals(null)) {
            String pattern = "dd/MM/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            try {
                date = sdf.parse(DataArmzenada);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }
    /*=======================================*/
    public static Date DataPriDiaMes (Date D){
        Calendar c = Calendar.getInstance();
        c.setTime(D);
        c.set(Calendar.DAY_OF_MONTH,1);
        return c.getTime();
    }
    /*=======================================*/
    public static Date DataUltDiaMes (Date D){
        Calendar c = Calendar.getInstance();
        c.setTime(D);
        c.set(Calendar.DAY_OF_MONTH,c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }
    /*=======================================*/
    public static String DataToString (Date D, String Format){
        String S = null;
        DateFormat df = null;
        if (Format.equals("")) {
            df = new SimpleDateFormat("dd/MM/yyyy");
        }else{
            df = new SimpleDateFormat(Format);
        }
        if (D != null) {
            S = df.format(D);
        }
        return S;
    }
    /*=====================================*/
    public static Integer DataDia (String S){
        String DataArmzenada = S;
        Integer Dia = 0;
        if (!S.equals(null)) {
            Calendar c = Calendar.getInstance();
            c.setTime(DataRetorno(DataArmzenada));
            Dia = c.get(Calendar.DAY_OF_MONTH);
        }
        return Dia;
    }
    /*================================================*/
    public static Date DataDiaInc (Date D,Integer Qtde){
        Date date = null;
        if (!D.equals(null)) {
            Calendar c = Calendar.getInstance();
            c.setTime(D);
            c.add(Calendar.DAY_OF_MONTH,Qtde);
            date = c.getTime();
        }
        return date;
    }
    /*=====================================*/
    public static Integer DataMes (String S){
        String DataArmzenada = S;
        Integer Mes = 0;
        if (!S.equals(null)) {
            Calendar c = Calendar.getInstance();
            c.setTime(DataRetorno(DataArmzenada));
            Mes = c.get(Calendar.MONTH);
        }
        return Mes;
    }
    /*=================================================*/
    public static Date DataMesInc (Date D,Integer Qtde){
        Date date = null;
        if (!D.equals(null)) {
            Calendar c = Calendar.getInstance();
            c.setTime(D);
            c.add(Calendar.MONTH,Qtde);
            date = c.getTime();
        }
        return date;
    }
    /*=====================================*/
    public static Integer DataAno (String S){
        String DataArmzenada = S;
        Integer Ano = 0;
        if (!S.equals(null)) {
            Calendar c = Calendar.getInstance();
            c.setTime(DataRetorno(DataArmzenada));
            Ano = c.get(Calendar.YEAR);
        }
        return Ano;
    }
    /*================================================*/
    public static Date DataAnoInc (Date D,Integer Qtde){
        Date date = null;
        if (!D.equals(null)) {
            Calendar c = Calendar.getInstance();
            c.setTime(D);
            c.add(Calendar.YEAR,Qtde);
            date = c.getTime();
        }
        return date;
    }
    /*=================================================================================================*/
    public static AdapterView.OnItemSelectedListener MesNumeric (String Retorno , final Context context){
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(context,String.valueOf(i+1),Toast.LENGTH_LONG).show();
                NumeroMes=String.valueOf(i+1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
    }
}