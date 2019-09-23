package newproject.com.br.newfinans.objetc;

import java.util.ArrayList;

/**
 * Created by Jadison on 24/01/2018.
 */

public class ObjectRenda extends ArrayList<ObjectRenda> {
    private Integer cod;
    private Float valor;
    private String data;
    private String descricao;

    public ObjectRenda(Integer Cod,Float Valor, String Data, String Descricao){
        this.cod       = Cod;
        this.valor     = Valor;
        this.data      = Data;
        this.descricao = Descricao;

    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }
}
