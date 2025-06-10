
import java.util.InputMismatchException;
import java.util.Scanner;

public class ControleEstoqueApp {

    private static final int CAPACIDADE_MAXIMA = 20;
    
    private static Produto[] estoque = new Produto[CAPACIDADE_MAXIMA];
    
    private static int quantidadeAtual = 0;
  
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;
        do {
            exibirMenu();
            try {
                opcao = scanner.nextInt();
                scanner.nextLine(); 

                switch (opcao) {
                    case 1:
                        cadastrarProduto();
                        break;
                    case 2:
                        listarProdutos();
                        break;
                    case 3:
                        filtrarPorCategoria();
                        break;
                    case 4:
                        ordenarPorCategoria();
                        break;
                    case 5:
                        removerProduto();
                        break;
                    case 6:
                        atualizarPreco();
                        break;
                    case 7:
                        gerarRelatorio();
                        break;
                    case 0:
                        System.out.println("Saindo do sistema. Até logo!");
                        break;
                    default:
                        System.out.println("Opção inválida. Por favor, tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro: Entrada inválida. Por favor, insira um número.");
                scanner.nextLine(); 
                opcao = -1; 
            }

            if (opcao != 0) {
                System.out.println("\nPressione Enter para voltar ao menu...");
                scanner.nextLine();
            }

        } while (opcao != 0);

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n===== SISTEMA DE CONTROLE DE ESTOQUE =====");
        System.out.println("1 - Cadastrar novo produto");
        System.out.println("2 - Listar todos os produtos");
        System.out.println("3 - Filtrar produtos por categoria");
        System.out.println("4 - Ordenar produtos por categoria");
        System.out.println("5 - Remover produto");
        System.out.println("6 - Atualizar preço de um produto");
        System.out.println("7 - Gerar relatório de valor por categoria");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void cadastrarProduto() {
        if (quantidadeAtual >= CAPACIDADE_MAXIMA) {
            System.out.println("Erro: Estoque cheio. Não é possível cadastrar mais produtos.");
            return;
        }

        try {
            System.out.println("\n--- Cadastro de Novo Produto ---");
            System.out.print("Nome/Descrição: ");
            String nome = scanner.nextLine();
            System.out.print("Categoria: ");
            String categoria = scanner.nextLine();
            System.out.print("Quantidade em Estoque: ");
            int qtdEstoque = scanner.nextInt();
            System.out.print("Preço Unitário (use vírgula para decimais): ");
            double preco = scanner.nextDouble();
            System.out.print("Quantidade Mínima em Estoque: ");
            int qtdMinima = scanner.nextInt();

            Produto novoProduto = new Produto(nome, qtdEstoque, preco, categoria, qtdMinima);
            estoque[quantidadeAtual] = novoProduto;
            quantidadeAtual++;
            System.out.println("Produto cadastrado com sucesso!");

        } catch (InputMismatchException e) {
            System.out.println("Erro de entrada. Por favor, insira os dados no formato correto.");
            scanner.nextLine();
        }
    }

    private static void listarProdutos() {
        if (quantidadeAtual == 0) {
            System.out.println("Nenhum produto cadastrado no estoque.");
            return;
        }
        System.out.println("\n--- Lista de Todos os Produtos ---");
        for (int i = 0; i < quantidadeAtual; i++) {
            System.out.println(estoque[i]);
        }
        System.out.println("------------------------------------");
    }

    private static void filtrarPorCategoria() {
        System.out.print("\nDigite a categoria que deseja filtrar: ");
        String categoria = scanner.nextLine();
        boolean encontrou = false;
        
        System.out.println("\n--- Produtos na Categoria: " + categoria + " ---");
        for (int i = 0; i < quantidadeAtual; i++) {
            if (estoque[i].getCategoria().equalsIgnoreCase(categoria)) {
                System.out.println(estoque[i]);
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("Nenhum produto encontrado nesta categoria.");
        }
        System.out.println("------------------------------------");
    }
    
    private static void ordenarPorCategoria() {
        for (int i = 0; i < quantidadeAtual - 1; i++) {
            for (int j = 0; j < quantidadeAtual - 1 - i; j++) {
                int comparacaoCategoria = estoque[j].getCategoria().compareToIgnoreCase(estoque[j + 1].getCategoria());
                if (comparacaoCategoria > 0) {
                    Produto temp = estoque[j];
                    estoque[j] = estoque[j + 1];
                    estoque[j + 1] = temp;
                } else if (comparacaoCategoria == 0) {
                    if (estoque[j].getNome().compareToIgnoreCase(estoque[j + 1].getNome()) > 0) {
                        Produto temp = estoque[j];
                        estoque[j] = estoque[j + 1];
                        estoque[j + 1] = temp;
                    }
                }
            }
        }
        System.out.println("Produtos ordenados por categoria.");
        listarProdutos();
    }

    private static void removerProduto() {
        System.out.print("\nDigite o nome do produto que deseja remover: ");
        String nome = scanner.nextLine();
        int indiceParaRemover = -1;

        for (int i = 0; i < quantidadeAtual; i++) {
            if (estoque[i].getNome().equalsIgnoreCase(nome)) {
                indiceParaRemover = i;
                break;
            }
        }

        if (indiceParaRemover == -1) {
            System.out.println("Erro: Produto '" + nome + "' não encontrado.");
        } else {

            for (int i = indiceParaRemover; i < quantidadeAtual - 1; i++) {
                estoque[i] = estoque[i + 1];
            }
            quantidadeAtual--; 
            estoque[quantidadeAtual] = null; 
            System.out.println("Produto '" + nome + "' removido com sucesso.");
        }
    }

    private static void atualizarPreco() {
        try {
            System.out.print("\nDigite o nome do produto para atualizar o preço: ");
            String nome = scanner.nextLine();
            int indiceParaAtualizar = -1;

            for (int i = 0; i < quantidadeAtual; i++) {
                if (estoque[i].getNome().equalsIgnoreCase(nome)) {
                    indiceParaAtualizar = i;
                    break;
                }
            }

            if (indiceParaAtualizar != -1) {
                System.out.print("Digite o novo preço (use vírgula para decimais): ");
                double novoPreco = scanner.nextDouble();
                estoque[indiceParaAtualizar].setPrecoUnitario(novoPreco);
                System.out.println("Preço atualizado com sucesso!");
            } else {
                System.out.println("Erro: Produto '" + nome + "' não encontrado.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Erro de entrada. Por favor, insira um preço válido.");
            scanner.nextLine(); 
        }
    }

    private static void gerarRelatorio() {
        if (quantidadeAtual == 0) {
            System.out.println("Nenhum produto cadastrado para gerar relatório.");
            return;
        }

        System.out.println("\n--- Relatório de Valor em Estoque por Categoria ---");

        ordenarPorCategoria();

        String categoriaAtual = "";
        double subtotalCategoria = 0;
        double totalGeral = 0;

        for (int i = 0; i < quantidadeAtual; i++) {
            Produto p = estoque[i];

            if (!p.getCategoria().equalsIgnoreCase(categoriaAtual)) {
                if (i > 0) {
                    System.out.println("  Subtotal da Categoria '" + categoriaAtual + "': " + String.format("R$ %.2f", subtotalCategoria));
                }
                categoriaAtual = p.getCategoria();
                System.out.println("\nCategoria: " + categoriaAtual);
                subtotalCategoria = 0;
            }
            
            double valorDoProduto = p.getValorTotalEmEstoque();
            System.out.println("  - " + p.getNome() + ", Qtd: " + p.getQuantidadeEstoque() + ", Valor em Estoque: " + String.format("R$ %.2f", valorDoProduto));
            subtotalCategoria += valorDoProduto;
            totalGeral += valorDoProduto;
        }

        System.out.println("  Subtotal da Categoria '" + categoriaAtual + "': " + String.format("R$ %.2f", subtotalCategoria));

        System.out.println("\n----------------------------------------------------");
        System.out.println("Valor Total Geral em Estoque: " + String.format("R$ %.2f", totalGeral));
        System.out.println("----------------------------------------------------");
    }
}
