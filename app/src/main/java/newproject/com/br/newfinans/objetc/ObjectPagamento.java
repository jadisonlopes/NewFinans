package newproject.com.br.newfinans.objetc;

import java.util.ArrayList;

public class ObjectPagamento extends ArrayList<ObjectPagamento> {
    private Float valor;
    private String despesa;
    private String fonteDespesa;

    public ObjectPagamento(Float valor, String despesa, String fonteDespesa){
        this.valor = valor;
        this.despesa = despesa;
        this.fonteDespesa = fonteDespesa;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public String getDespesa() {
        return despesa;
    }

    public void setDespesa(String despesa) {
        this.despesa = despesa;
    }

    public String getFonteDespesa() {
        return fonteDespesa;
    }

    public void setFonteDespesa(String fonteDespesa) {
        this.fonteDespesa = fonteDespesa;
    }
}
