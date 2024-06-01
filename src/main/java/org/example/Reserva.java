package org.example;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Reserva implements Serializable {
    private static final long serialVersionUID = 1L;
    private Equipamento equipamento;
    private LocalDate dataReserva;
    private LocalTime horaEntrada;
    private LocalTime horaSaida;
    private String solicitante;

    public Reserva(Equipamento equipamento, LocalDate dataReserva, LocalTime horaEntrada, LocalTime horaSaida, String solicitante) {
        this.equipamento = equipamento;
        this.dataReserva = dataReserva;
        this.horaEntrada = horaEntrada;
        this.horaSaida = horaSaida;
        this.solicitante = solicitante;
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public LocalDate getDataReserva() {
        return dataReserva;
    }

    public LocalTime getHoraEntrada() {
        return horaEntrada;
    }

    public LocalTime getHoraSaida() {
        return horaSaida;
    }

    public String getSolicitante() {
        return solicitante;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "equipamento=" + equipamento +
                ", dataReserva=" + dataReserva +
                ", horaEntrada=" + horaEntrada +
                ", horaSaida=" + horaSaida +
                ", solicitante='" + solicitante + '\'' +
                '}';
    }
}



