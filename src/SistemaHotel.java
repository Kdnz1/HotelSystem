import java.util.*;
import dao.*;
import model.*;

public class SistemaHotel {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HospedeDAO hospedeDAO = new HospedeDAO();
        QuartoDAO quartoDAO = new QuartoDAO();
        ReservaDAO reservaDAO = new ReservaDAO();

        int opcao;
        do {
            System.out.println("\n=== SISTEMA DE HOTEL (MySQL) ===");
            System.out.println("1 - Cadastrar hóspede");
            System.out.println("2 - Listar hóspedes");
            System.out.println("3 - Fazer reserva");
            System.out.println("4 - Listar reservas");
            System.out.println("5 - Listar quartos");
            System.out.println("6 - Adicionar quarto");
            System.out.println("7 - Excluir quarto");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Nome completo: ");
                    String nome = sc.nextLine().trim();
                    while (nome.isEmpty()) {
                        System.out.println("❌ Nome não pode estar vazio!");
                        System.out.print("Nome completo: ");
                        nome = sc.nextLine().trim();
                    }

                    System.out.print("CPF (xxx.xxx.xxx-xx): ");
                    String cpf = sc.nextLine().trim();
                    while (!validarCPF(cpf)) {
                        System.out.println("❌ CPF inválido! Use o formato: xxx.xxx.xxx-xx");
                        System.out.print("CPF (xxx.xxx.xxx-xx): ");
                        cpf = sc.nextLine().trim();
                    }

                    System.out.print("Telefone ((xx) x xxxx-xxxx): ");
                    String tel = sc.nextLine().trim();
                    while (!validarTelefone(tel)) {
                        System.out.println("❌ Telefone inválido! Use o formato: (xx) x xxxx-xxxx");
                        System.out.print("Telefone ((xx) x xxxx-xxxx): ");
                        tel = sc.nextLine().trim();
                    }

                    hospedeDAO.inserir(new Hospede(nome, cpf, tel));
                    break;

                case 2:
                    hospedeDAO.listar().forEach(System.out::println);
                    break;

                case 3:
                    ArrayList<Quarto> disponiveis = quartoDAO.listarDisponiveis();
                    if (disponiveis.isEmpty()) {
                        System.out.println("❌ Nenhum quarto disponível no momento!");
                    } else {
                        System.out.println("\n📋 QUARTOS DISPONÍVEIS:");
                        for (int i = 0; i < disponiveis.size(); i++) {
                            System.out.println((i + 1) + " - " + disponiveis.get(i));
                        }
                    }
                    System.out.println();

                    System.out.print("CPF do hóspede: ");
                    String cpfBusca = sc.nextLine();
                    System.out.print("ID do quarto (veja lista acima): ");
                    int quartoId = sc.nextInt();
                    System.out.print("Quantos dias? ");
                    int dias = sc.nextInt();
                    sc.nextLine();

                    int hospedeId = ReservaDAO.buscarHospedeIdPorCpf(cpfBusca);
                    if (hospedeId == -1) {
                        System.out.println("❌ Hóspede não encontrado!");
                        break;
                    }
                    
                    if (quartoId < 1 || quartoId > disponiveis.size()) {
                        System.out.println("❌ Quarto inválido!");
                        break;
                    }
                    Quarto q = disponiveis.get(quartoId - 1);
                    int quartoDbId = QuartoDAO.buscarIdPorNumero(q.getNumero());
                    if (quartoDbId == -1) {
                        System.out.println("❌ Erro ao buscar ID do quarto!");
                        break;
                    }
                    Hospede h = new Hospede("", cpfBusca, "");
                    Reserva r = new Reserva(h, q, dias);
                    reservaDAO.inserir(r, hospedeId, quartoDbId);
                    break;

                case 4:
                    reservaDAO.listar().forEach(System.out::println);
                    break;

                case 5:
                    quartoDAO.listar().forEach(System.out::println);
                    break;

                case 6:
                    System.out.print("Número do quarto: ");
                    int numero = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Tipo (Simples/Luxo/Suíte Master): ");
                    String tipo = sc.nextLine().trim();
                    System.out.print("Valor da diária: ");
                    double valor = sc.nextDouble();
                    sc.nextLine();
                    quartoDAO.inserir(new Quarto(numero, tipo, valor));
                    break;

                case 7:
                    System.out.print("Número do quarto a excluir: ");
                    int quartoExcluir = sc.nextInt();
                    sc.nextLine();
                    quartoDAO.excluir(quartoExcluir);
                    break;

                case 0:
                    System.out.println("Saindo...");
                    break;
            }
        } while (opcao != 0);
        sc.close();
    }

    // Valida CPF no formato xxx.xxx.xxx-xx
    private static boolean validarCPF(String cpf) {
        return cpf != null && cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}");
    }

    // Valida telefone no formato (xx) x xxxx-xxxx
    private static boolean validarTelefone(String telefone) {
        return telefone != null && telefone.matches("\\(\\d{2}\\) \\d \\d{4}-\\d{4}");
    }
}
