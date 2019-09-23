package newproject.com.br.newfinans.objetc;

import java.util.ArrayList;

/**
 * Created by Jadison on 24/01/2018.
 */

public class ObjectDespesa extends ArrayList<ObjectDespesa>{
    private Integer cod;
    private String descricao;
    private String obs;

    public ObjectDespesa(Integer Cod, String Descricao, String Obs){
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
