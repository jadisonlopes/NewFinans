package newproject.com.br.newfinans.objetc;

import java.util.ArrayList;

/**
 * Created by Jadison on 25/01/2018.
 */

public class ObjectFormasPag extends ArrayList<ObjectFormasPag> {
    private Integer cod;
    private Integer diacomp;
    private Integer venc;
    private Boolean parcela;
    private Boolean exclusao;
    private String tipo;
    private String descricao;

    public ObjectFormasPag(Integer Cod, Integer DiaComp, Integer Venc, Boolean Parcela, Boolean Exclusao, String Tipo, String Descricao){
        this.cod       = Cod;
        this.diacomp   = DiaComp;
        this.venc      = Venc;
        this.parcela   = Parcela;
        this.exclusao  = Exclusao;
        this.tipo      = Tipo;
        this.descricao = Descricao;
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    public Integer getDiacomp() {
        return diacomp;
    }

    public Integer getVenc() {
        return venc;
    }

    public void setVenc(Integer venc) {
        this.venc = venc;
    }

    public void setDiacomp(Integer diacomp) {
        this.diacomp = diacomp;
    }

    public Boolean getParcela() {
        return parcela;
    }

    public void setParcela(Boolean parcela) {
        this.parcela = parcela;
    }

    public Boolean getExclusao() {
        return exclusao;
    }

    public void setExclusao(Boolean exclusao) {
        this.exclusao = exclusao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
