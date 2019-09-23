package newproject.com.br.newfinans.objetc;

import java.util.ArrayList;

/**
 * Created by Jadison on 17/02/2018.
 */

public class ObjectGrafico extends ArrayList<ObjectGrafico> {
    private Float valor;
    private String descricao;

    public ObjectGrafico(Float Valor, String Descricao){
        this.valor     = Valor;
        this.descricao = Descricao;

    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
