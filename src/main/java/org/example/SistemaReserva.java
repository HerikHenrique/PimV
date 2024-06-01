package org.example;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SistemaReserva implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Equipamento> equipamentos;
    private List<Reserva> reservas;
    private transient Scanner scanner;

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
            System.out.println("4. Zerar Reservas");
            System.out.println("5. Adicionar Novo Equipamento");
            System.out.println("6. Sair");
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
                    zerarReservas();
                    break;
                case 5:
                    adicionarNovoEquipamento();
                    break;
                case 6:
                    System.out.println("Saindo do sistema...");
                    salvarDados();
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 6);
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
            LocalDate dataReserva = obterDataReserva();
            LocalTime horaEntrada = obterHoraReserva("entrada");
            LocalTime horaSaida = obterHoraReserva("saída");

            if (horaSaida.isAfter(horaEntrada)) {
                if (!verificarConflitoReserva(equipamento, dataReserva, horaEntrada, horaSaida)) {
                    Reserva reserva = new Reserva(equipamento, dataReserva, horaEntrada, horaSaida, solicitante);
                    reservas.add(reserva);
                    System.out.println("Equipamento reservado com sucesso.");
                } else {
                    System.out.println("Erro: Horário de reserva conflita com uma reserva existente.");
                }
            } else {
                System.out.println("Erro: O horário de saída deve ser após o horário de entrada.");
            }
        } else {
            System.out.println("Nenhum equipamento reservado.");
        }
    }

    private Equipamento escolherEquipamento() {
        while (true) {
            System.out.println("\n--- Escolher Equipamento ---");
            for (int i = 0; i < equipamentos.size(); i++) {
                Equipamento equipamento = equipamentos.get(i);
                System.out.println((i + 1) + ". " + equipamento.getNome());
            }
            System.out.print("Escolha um equipamento pelo número (ou 0 para cancelar): ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha

            if (opcao == 0) {
                return null; // Cancelar a reserva
            } else if (opcao > 0 && opcao <= equipamentos.size()) {
                return equipamentos.get(opcao - 1);
            } else {
                System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private LocalDate obterDataReserva() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate hoje = LocalDate.now();

        while (true) {
            System.out.print("Digite a data da reserva (dd/MM/yyyy): ");
            String dataInput = scanner.nextLine();
            try {
                LocalDate dataReserva = LocalDate.parse(dataInput, dateFormatter);
                if (dataReserva.isBefore(hoje)) {
                    System.out.println("Erro: A data da reserva não pode ser anterior à data atual.");
                } else {
                    return dataReserva;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data inválido. Tente novamente.");
            }
        }
    }

    private LocalTime obterHoraReserva(String tipo) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        while (true) {
            System.out.print("Digite o horário de " + tipo + " (HH:mm): ");
            String horaInput = scanner.nextLine();
            try {
                return LocalTime.parse(horaInput, timeFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de horário inválido. Tente novamente.");
            }
        }
    }

    private boolean verificarConflitoReserva(Equipamento equipamento, LocalDate data, LocalTime horaEntrada, LocalTime horaSaida) {
        for (Reserva reserva : reservas) {
            if (reserva.getEquipamento().equals(equipamento) && reserva.getDataReserva().equals(data)) {
                if (horaEntrada.isBefore(reserva.getHoraSaida()) && horaSaida.isAfter(reserva.getHoraEntrada())) {
                    return true;
                }
            }
        }
        return false;
    }

    private void listarReservas() {
        System.out.println("\n--- Reservas ---");
        for (Reserva reserva : reservas) {
            System.out.println(reserva);
        }
    }

    private void zerarReservas() {
        reservas.clear();
        System.out.println("Todas as reservas foram zeradas.");
    }

    private void adicionarNovoEquipamento() {
        System.out.print("Digite o nome do novo equipamento: ");
        String nomeEquipamento = scanner.nextLine();
        if (!nomeEquipamento.trim().isEmpty()) {
            equipamentos.add(new Equipamento(nomeEquipamento));
            System.out.println("Equipamento \"" + nomeEquipamento + "\" adicionado com sucesso.");
        } else {
            System.out.println("Nome do equipamento não pode estar vazio.");
        }
    }

    private void salvarDados() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("dadosReserva.ser"))) {
            oos.writeObject(equipamentos);
            oos.writeObject(reservas);
            System.out.println("Dados salvos com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }

    private void carregarDados() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("dadosReserva.ser"))) {
            equipamentos = (List<Equipamento>) ois.readObject();
            reservas = (List<Reserva>) ois.readObject();
            System.out.println("Dados carregados com sucesso.");
        } catch (FileNotFoundException e) {
            System.out.println("Nenhum dado salvo encontrado. Iniciando com novos dados.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar os dados: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SistemaReserva sistema = new SistemaReserva();
        sistema.carregarDados();
        sistema.exibirMenu();
    }
}





