package com.example.torneo_tenis_de_mesa.Polimorfismo;
import java.util.ArrayList;
import java.util.UUID;

public class Torneo {


    public Matches encuentro_cuar_uno, encuentro_cuar_dos, encuentro_cuar_tres,
        encuentro_cuar_cuatro, encuentro_semi_uno, encuentro_semi_dos,  encuentroFinal;







    public Torneo( Matches encuentro_cuar_uno, Matches encuentro_cuar_dos,
                  Matches encuentro_cuar_tres, Matches encuentro_cuar_cuatro, Matches encuentro_semi_uno,
                  Matches encuentro_semi_dos, Matches encuentroFinal) {
        this.encuentro_cuar_uno = encuentro_cuar_uno;
        this.encuentro_cuar_dos = encuentro_cuar_dos;
        this.encuentro_cuar_tres = encuentro_cuar_tres;
        this.encuentro_cuar_cuatro = encuentro_cuar_cuatro;
        this.encuentro_semi_uno = encuentro_semi_uno;
        this.encuentro_semi_dos = encuentro_semi_dos;
        this.encuentroFinal = encuentroFinal;
    }


    public Matches getEncuentro_cuar_uno() {
        return encuentro_cuar_uno;
    }

    public void setEncuentro_cuar_uno(Matches encuentro_cuar_uno) {
        this.encuentro_cuar_uno = encuentro_cuar_uno;
    }

    public Matches getEncuentro_cuar_dos() {
        return encuentro_cuar_dos;
    }

    public void setEncuentro_cuar_dos(Matches encuentro_cuar_dos) {
        this.encuentro_cuar_dos = encuentro_cuar_dos;
    }

    public Matches getEncuentro_cuar_tres() {
        return encuentro_cuar_tres;
    }

    public void setEncuentro_cuar_tres(Matches encuentro_cuar_tres) {
        this.encuentro_cuar_tres = encuentro_cuar_tres;
    }

    public Matches getEncuentro_cuar_cuatro() {
        return encuentro_cuar_cuatro;
    }

    public void setEncuentro_cuar_cuatro(Matches encuentro_cuar_cuatro) {
        this.encuentro_cuar_cuatro = encuentro_cuar_cuatro;
    }

    public Matches getEncuentro_semi_uno() {
        return encuentro_semi_uno;
    }

    public void setEncuentro_semi_uno(Matches encuentro_semi_uno) {
        this.encuentro_semi_uno = encuentro_semi_uno;
    }

    public Matches getEncuentro_semi_dos() {
        return encuentro_semi_dos;
    }

    public void setEncuentro_semi_dos(Matches encuentro_semi_dos) {
        this.encuentro_semi_dos = encuentro_semi_dos;
    }

    public Matches getEncuentroFinal() {
        return encuentroFinal;
    }

    public void setEncuentroFinal(Matches encuentroFinal) {
        this.encuentroFinal = encuentroFinal;
    }



}
