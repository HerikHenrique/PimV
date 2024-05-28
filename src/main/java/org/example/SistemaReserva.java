package org.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class SistemaReserva {
    private List<Equipamento> equipamentos;
    private List<Reserva> reservas;
    private Scanner scanner;

    public SistemaReserva() {
        equipamentos = new ArrayList<>();
        reservas = new ArrayList<>();
        scanner = new Scanner(System.in);
        inicializarEquipamentos();
    }

    private void inicializarEquipamentos() {
        equipamentos.add(new Equipamento("Datashow"));
        equipamentos.add(new Equipamento("TV com VCR"));
        equipamentos.add(new Equipamento("TV com DVD"));
        equipamentos.add(new Equipamento("Projetor de slides"));
        equipamentos.add(new Equipamento("Sistema de áudio-microfone"));
        equipamentos.add(new Equipamento("Caixa amplificada"));
        equipamentos.add(new Equipamento("Notebooks"));
        equipamentos.add(new Equipamento("Kits multimídia"));
    }

    private void exibirMenu() {
        int opcao;
        do {
            System.out.println("\n--- Sistema de Reserva de Equipamentos ---");
            System.out.println("1. Listar Equipamentos");
            System.out.println("2. Reservar Equipamento");
            System.out.println("3. Listar Reservas");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha

            switch (opcao) {
                case 1:
                    listarEquipamentos();
                    break;
                case 2:
                    reservarEquipamento();
                    break;
                case 3:
                    listarReservas();
                    break;
                case 4:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 4);
    }

    private void listarEquipamentos() {
        System.out.println("\n--- Equipamentos Disponíveis ---");
        for (Equipamento equipamento : equipamentos) {
            System.out.println(equipamento);
        }
    }

    private void reservarEquipamento() {
        System.out.println("\n--- Reservar Equipamento ---");
        System.out.print("Nome do solicitante: ");
        String solicitante = scanner.nextLine();

        if (solicitante.isEmpty()) {
            System.out.println("Nome do solicitante não pode estar vazio.");
            return;
        }

        Equipamento equipamento = escolherEquipamento();
        if (equipamento != null) {
            Date dataReserva = new Date(); // Usar a data atual para a reserva
            Reserva reserva = new Reserva(equipamento, dataReserva, solicitante);
            reservas.add(reserva);
            System.out.println("Equipamento reservado com sucesso.");
        } else {
            System.out.println("Nenhum equipamento reservado.");
        }
    }

    private Equipamento escolherEquipamento() {
        while (true) {
            System.out.println("\n--- Escolher Equipamento ---");
            for (int i = 0; i < equipamentos.size(); i++) {
                Equipamento equipamento = equipamentos.get(i);
                if (equipamento.isDisponivel()) {
                    System.out.println((i + 1) + ". " + equipamento.getNome());
                }
            }
            System.out.print("Escolha um equipamento pelo número (ou 0 para cancelar): ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha

            if (opcao == 0) {
                return null; // Cancelar a reserva
            } else if (opcao > 0 && opcao <= equipamentos.size()) {
                Equipamento equipamentoEscolhido = equipamentos.get(opcao - 1);
                if (equipamentoEscolhido.isDisponivel()) {
                    return equipamentoEscolhido;
                } else {
                    System.out.println("Equipamento indisponível. Escolha outro.");
                }
            } else {
                System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void listarReservas() {
        System.out.println("\n--- Reservas ---");
        for (Reserva reserva : reservas) {
            System.out.println(reserva);
        }
    }

    public static void main(String[] args) {
        SistemaReserva sistema = new SistemaReserva();
        sistema.exibirMenu();
    }
}

