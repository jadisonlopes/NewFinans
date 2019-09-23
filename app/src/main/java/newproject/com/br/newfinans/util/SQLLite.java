package newproject.com.br.newfinans.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import java.util.Date;
import static android.text.TextUtils.substring;
import static newproject.com.br.newfinans.util.Util.Trim;

/**
 * Created by Jadison on 11/01/2018.
 */

public class SQLLite{
    public static SQLiteDatabase dataBase;

    /*=============================*/
    public static void CreateTable(){
        //Criando Tabelas;

        //Forma de Pagamento;
        dataBase.execSQL("CREATE TABLE IF NOT EXISTS formapag (cod INTEGER PRIMARY KEY AUTOINCREMENT," +
                "descricao VARCHAR(50),parcela VARCHAR(1),tipo VARCHAR(10),diacomp INTEGER,venc INTEGER,exclusao VARCHAR(1));");
        //Renda;
        dataBase.execSQL("CREATE TABLE IF NOT EXISTS renda (cod INTEGER PRIMARY KEY AUTOINCREMENT," +
                "descricao VARCHAR(50),data DATE,valor NUMERIC(10,2));");

        //Tipo de Despesa;
        dataBase.execSQL("CREATE TABLE IF NOT EXISTS despesa (cod INTEGER PRIMARY KEY AUTOINCREMENT," +
                "descricao VARCHAR(50),observ VARCHAR(50), exclusao VARCHAR(1));");

        //Fonte de Despesa;
        dataBase.execSQL("CREATE TABLE IF NOT EXISTS fonte_despesa (cod INTEGER PRIMARY KEY AUTOINCREMENT," +
                "descricao VARCHAR(50),observ VARCHAR(50), exclusao VARCHAR(1));");

        //Gasto;
        dataBase.execSQL("CREATE TABLE IF NOT EXISTS gasto (cod INTEGER PRIMARY KEY AUTOINCREMENT," +
                "descricao VARCHAR(50),formapag INTEGER,fonte_despesa INTEGER,despesa INTEGER,datacartao DATE,codparc INTEGER,data DATE,valor Numeric(10,2));");
    }

    /*=============================*/
    public static void AlterTable(){
        //Gasto;
        try {
            Cursor cursor = dataBase.rawQuery("SELECT codparc FROM gasto limit 1;",null);
            cursor.moveToFirst();
            cursor.close();
        }catch (Exception e) {
            dataBase.execSQL("ALTER TABLE gasto ADD COLUMN codparc INTEGER;");
        }

        //Gasto;
        try {
            Cursor cursor = dataBase.rawQuery("SELECT fonte_despesa FROM gasto limit 1;",null);
            cursor.moveToFirst();
            cursor.close();
        }catch (Exception e) {
            dataBase.execSQL("ALTER TABLE gasto ADD COLUMN fonte_despesa INTEGER;");
        }
    }
    /*===========================*/
    public static void DropTable(){
        //Criando Tabelas;
        //Forma de Pagamento;
        dataBase.execSQL("DROP TABLE IF EXISTS formapag");
        //Renda;
        dataBase.execSQL("DROP TABLE IF EXISTS renda");
        //Tipo de Despesa;
        dataBase.execSQL("DROP TABLE IF EXISTS despesa");
        //Fonte de Despesa;
        dataBase.execSQL("DROP TABLE IF EXISTS fonte_despesa");
        //Gasto;
        dataBase.execSQL("DROP TABLE IF EXISTS gasto");
    }
    /*=============================================*/
    public static Cursor TotalizaFormaPag(Date data){
        String S;
        S = "SELECT SUM(valor) as total,f.descricao FROM gasto as g,formapag as f WHERE f.cod=g.formapag " +
                " AND strftime('%m',data)="+SQLVarchar(Util.DataToString(data,"MM"))+
                " AND strftime('%Y',data)="+SQLVarchar(Util.DataToString(data,"yyyy"))+
                " GROUP BY f.cod ORDER BY f.descricao;";
        Cursor cursor = dataBase.rawQuery(S, null);
        cursor.moveToFirst();
        return cursor;
    }
    /*============================================*/
    public static Cursor TotalizaDespesa(Date data){
        String S;
        S = "SELECT SUM(valor) as total,d.descricao FROM gasto as g,despesa as d WHERE d.cod=g.despesa " +
                " AND strftime('%m',data)="+SQLVarchar(Util.DataToString(data,"MM"))+
                " AND strftime('%Y',data)="+SQLVarchar(Util.DataToString(data,"yyyy"))+
                " GROUP BY d.cod ORDER BY d.descricao;";
        Cursor cursor = dataBase.rawQuery(S, null);
        cursor.moveToFirst();
        return cursor;
    }
    /*============================================*/
    public static Cursor TotalizaFonteDespesa(Date data){
        String S;
        S = "SELECT SUM(valor) as total,fd.descricao FROM gasto as g,fonte_despesa as fd WHERE fd.cod=g.fonte_despesa " +
                " AND strftime('%m',data)="+SQLVarchar(Util.DataToString(data,"MM"))+
                " AND strftime('%Y',data)="+SQLVarchar(Util.DataToString(data,"yyyy"))+
                " GROUP BY fd.cod ORDER BY fd.descricao;";
        Cursor cursor = dataBase.rawQuery(S, null);
        cursor.moveToFirst();
        return cursor;
    }
    /*============================================*/
    public static Float TotalizaGasto(Date data){
        String S;
        S = "SELECT SUM(valor) as total FROM gasto WHERE strftime('%m',data)="+SQLVarchar(Util.DataToString(data,"MM"))+
                " AND strftime('%Y',data)="+SQLVarchar(Util.DataToString(data,"yyyy"));
        Cursor cursor = dataBase.rawQuery(S, null);
        cursor.moveToFirst();
        return cursor.getFloat(cursor.getColumnIndex("total"));
    }
    /*============================================*/
    public static Float TotalizaRenda(Date data){
        String S;
        S = "SELECT SUM(valor) as total FROM renda WHERE strftime('%m',data)="+SQLVarchar(Util.DataToString(data,"MM"))+
            " AND strftime('%Y',data)="+SQLVarchar(Util.DataToString(data,"yyyy"));
        Cursor cursor = dataBase.rawQuery(S, null);
        cursor.moveToFirst();
        return cursor.getFloat(cursor.getColumnIndex("total"));
    }
    /*===============================================*/
    public static Integer ConsultaUltimoCod(String Tabela){
        Cursor cursor = dataBase.rawQuery("SELECT max(cod) AS cod FROM " + Tabela,null);
        cursor.moveToFirst();
        return cursor.getInt(cursor.getColumnIndex("cod"));
    }
    /*============================================*/
    public static Cursor ConsultaGasto(String Sql){
        String S;
        if ( !Trim(Sql).equals("") ) {
            S = Sql;
        } else
            S = SQLConsulta("gasto", "", "");
        Cursor cursor = dataBase.rawQuery(S, null);
        cursor.moveToFirst();
        return cursor;
    }
    /*=============================================*/
    public static Cursor ConsultaRenda(Integer Cod){
        String S;
        if (Cod>0){
            S=SQLConsulta("renda","cod="+SQLInteger(Cod),"");
        }else
            S=SQLConsulta("renda","","");
        Cursor cursor = dataBase.rawQuery(S,null);
        cursor.moveToFirst();
        return cursor;
    }
    /*==============================================*/
    public static Cursor ConsultaDespesa(Integer Cod){
        String S;
        if (Cod>0){
            S=SQLConsulta("despesa","cod="+SQLInteger(Cod)+" AND exclusao<>'T' ","");
        }else
            S=SQLConsulta("despesa"," exclusao<>'T' ","");
        Cursor cursor = dataBase.rawQuery(S,null);
        cursor.moveToFirst();
        return cursor;
    }
    /*==============================================*/
    public static Cursor ConsultaFonteDespesa(Integer Cod){
        String S;
        if (Cod>0){
            S=SQLConsulta("fonte_despesa","cod="+SQLInteger(Cod)+" AND exclusao<>'T' ","");
        }else
            S=SQLConsulta("fonte_despesa"," exclusao<>'T' ","");
        Cursor cursor = dataBase.rawQuery(S,null);
        cursor.moveToFirst();
        return cursor;
    }
    /*===============================================*/
    public static Cursor ConsultaFormaPag(Integer Cod){
        String S;
        if (Cod>0){
            S=SQLConsulta("formapag","cod="+SQLInteger(Cod)+" AND exclusao<>'T' ","");
        }else
            S=SQLConsulta("formapag"," exclusao<>'T' ","");
        Cursor cursor = dataBase.rawQuery(S,null);
        cursor.moveToFirst();
        return cursor;
    }
    /*============================================================*/
    public static Cursor ConsultaRendaData(String Mes, String Ano){
        String S = null;
        if (!Mes.equals(null)){
            S=" AND strftime('%m',data)="+SQLVarchar(Util.PreencheZeros(Mes,2));
        }
        if (!Ano.equals(null)){
            S=S+" AND strftime('%Y',data)="+SQLVarchar(Ano);
        }
        S="SELECT strftime('%d',data) as teste,* FROM renda WHERE 1=1 "+S+" ORDER BY data; ";
        Cursor cursor = dataBase.rawQuery(S,null);
        cursor.moveToFirst();
        return cursor;
    }
    /*====================================================*/
    public static Cursor ConsultaFormasPagNome(String Nome){
        String S = null;
        if (!Nome.equals(null)){
            S=" AND descricao="+SQLVarchar(Nome);
        }
        S="SELECT * FROM formapag WHERE exclusao<>'T' "+S+" ORDER BY cod; ";
        Cursor cursor = dataBase.rawQuery(S,null);
        cursor.moveToFirst();
        return cursor;
    }
    /*==================================================*/
    public static Cursor ConsultaDespesaNome(String Nome){
        String S = null;
        if (!Nome.equals(null)){
            S=" AND descricao="+SQLVarchar(Nome);
        }
        S="SELECT * FROM despesa WHERE exclusao<>'T' "+S+" ORDER BY cod; ";
        Cursor cursor = dataBase.rawQuery(S,null);
        cursor.moveToFirst();
        return cursor;
    }

    /*==================================================*/
    public static Cursor ConsultaFonteDespesaNome(String Nome){
        String S = null;
        if (!Nome.equals(null)){
            S=" AND descricao="+SQLVarchar(Nome);
        }
        S="SELECT * FROM fonte_despesa WHERE exclusao<>'T' "+S+" ORDER BY cod; ";
        Cursor cursor = dataBase.rawQuery(S,null);
        cursor.moveToFirst();
        return cursor;
    }
    /*=========================================*/
    public static SQLiteDatabase OpenDataBase(){
        dataBase = SQLiteDatabase.openDatabase("NewFinans", null, SQLiteDatabase.OPEN_READONLY);
        return dataBase;
    }
    /*========================================================================*/
    public static String SQLConsulta(String Tabela,String Where,String OrderBy){
        String SQL = "SELECT * FROM "+Tabela;
        if (!Trim(Where).equals("")){
            SQL=SQL+" WHERE "+Where;
        }
        if (!Trim(OrderBy).equals("")){
            SQL=SQL+" ORDER BY "+OrderBy+";";
        }else
            SQL=SQL+" ORDER BY cod;";
        return SQL;
    }
    /*======================================*/
    public static String SQLVarchar(String S){
        S = "CAST('"+S+"' AS VARCHAR)";
        return S;
    }
    /*=====================================*/
    public static String SQLNueric(Double N){
        String S = "CAST("+N+" AS NUMERIC)";
        return S;
    }
    /*=======================================*/
    public static String SQLBoolean(Boolean B){
        String S;
        if (B){
             S = "CAST('T' AS VARCHAR(1))";
        }else{
            S = "CAST('F' AS VARCHAR(1))";
        }
        return S;
    }
    /*===================================*/
    public static String SQLDate(String D){
        D = substring(D,6,10)+"-"+substring(D,3,5)+"-"+substring(D,0,2);
        String S = "'"+D+"'";
        return S;
    }
    /*===================================*/
    public static Date SQLDateToDate(String D){
        D = substring(D,8,10)+"/"+substring(D,5,7)+"/"+substring(D,0,4);
        Date data = Util.DataRetorno(D);
        return data;
    }
    /*=======================================*/
    public static String SQLInteger(Integer I){
        String S = "CAST("+I+" AS INTEGER)";
        return S;
    }
    /*=======================================*/
    public static void SQLExcluir(Integer Cod, String Table, Context c){
        String S;
        if (Table.toLowerCase().equals("despesa") || Table.toLowerCase().equals("formapag")) {
            S = "UPDATE " + Table + "  SET exclusao=" + SQLBoolean(true) + " WHERE cod=" + SQLInteger(Cod) + ";";
        } else if (Table.toLowerCase().equals("gasto")) {
            S = "DELETE FROM " + Table + " WHERE (codparc=" + SQLInteger(Cod) + " OR cod=" + SQLInteger(Cod) + ");";
        } else {
            S = "DELETE FROM " + Table + " WHERE cod=" + SQLInteger(Cod) + ";";
        }
        dataBase.execSQL(S);
        Toast.makeText(c, "Exclusão realizada com Sucesso!", Toast.LENGTH_SHORT).show();
    }
}
