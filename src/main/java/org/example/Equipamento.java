package org.example;
import java.util.Date;

public class Equipamento {
    private String nome;
    private boolean disponivel;

    public Equipamento(String nome) {
        this.nome = nome;
        this.disponivel = true;
    }

    public String getNome() {
        return nome;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    @Override
    public String toString() {
        return "Equipamento: " + nome + " (Disponível: " + disponivel + ")";
    }
}
