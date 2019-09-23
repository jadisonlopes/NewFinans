package newproject.com.br.newfinans.objetc;

import java.util.ArrayList;

/**
 * Created by Jadison on 06/11/2018.
 */

public class ObjectFonteDespesa extends ArrayList<ObjectFonteDespesa> {
    private Integer cod;
    private String descricao;
    private String obs;

    public ObjectFonteDespesa(Integer Cod, String Descricao, String Obs){
        this.cod       = Cod;
        this.descricao = Descricao;
        this.obs       = Obs;

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

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }
}
