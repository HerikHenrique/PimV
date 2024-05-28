package org.example;

import java.util.Date;

public class Reserva {
    private Equipamento equipamento;
    private Date dataReserva;
    private String solicitante;

    public Reserva(Equipamento equipamento, Date dataReserva, String solicitante) {
        this.equipamento = equipamento;
        this.dataReserva = dataReserva;
        this.solicitante = solicitante;
        equipamento.setDisponivel(false);
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public Date getDataReserva() {
        return dataReserva;
    }

    public String getSolicitante() {
        return solicitante;
    }

    @Override
    public String toString() {
        return "Reserva feita por: " + solicitante + " em " + dataReserva + " para o equipamento: " + equipamento.getNome();
    }
}
