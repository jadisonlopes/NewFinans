package newproject.com.br.newfinans.objetc;

import java.util.ArrayList;
import java.util.Date;
import java.util.function.Predicate;

/**
 * Created by Jadison on 25/01/2018.
 */

public class ObjectGasto extends ArrayList<ObjectGasto> {
    private Integer cod;
    private String descricao;
    private Integer formapag;
    private Integer codparc;
    private Integer despesa;
    private Integer fonte_despesa;
    private Date datacartao;
    private Date data;
    private Float valor;

    public ObjectGasto(Integer Cod, String Descricao, Integer FormaPag, Integer CodParc, Integer Fonte_Despesa, Integer Despesa, Date DataCartao, Date Data, Float Valor){
        this.cod           = Cod;
        this.descricao     = Descricao;
        this.formapag      = FormaPag;
        this.codparc       = CodParc;
        this.despesa       = Despesa;
        this.fonte_despesa = Fonte_Despesa;
        this.datacartao    = DataCartao;
        this.data          = Data;
        this.valor         = Valor;
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getFormapag() {
        return formapag;
    }

    public void setFormapag(Integer formapag) {
        this.formapag = formapag;
    }

    public Integer getDespesa() {
        return despesa;
    }

    public void setDespesa(Integer despesa) {
        this.despesa = despesa;
    }

    public Date getDatacartao() {
        return datacartao;
    }

    public void setDatacartao(Date datacartao) {
        this.datacartao = datacartao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public Integer getFonte_despesa() {
        return fonte_despesa;
    }

    public void setFonte_despesa(Integer fonte_despesa) {
        this.fonte_despesa = fonte_despesa;
    }

    public Integer getCodparc() {
        return codparc;
    }

    public void setCodparc(Integer codparc) {
        this.codparc = codparc;
    }

}
