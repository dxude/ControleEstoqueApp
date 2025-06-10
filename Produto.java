
public class Produto {
    private String nome;
    private int quantidadeEstoque;
    private double precoUnitario;
    private String categoria;
    private int quantidadeMinima;

    public Produto(String nome, int quantidadeEstoque, double precoUnitario, String categoria, int quantidadeMinima) {
        this.nome = nome;
        this.quantidadeEstoque = quantidadeEstoque;
        this.precoUnitario = precoUnitario;
        this.categoria = categoria;
        this.quantidadeMinima = quantidadeMinima;
    }

    
    public String getNome() {
        return nome;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public String getCategoria() {
        return categoria;
    }
    

    public void setPrecoUnitario(double precoUnitario) {
        if (precoUnitario >= 0) {
            this.precoUnitario = precoUnitario;
        }
    }


    public double getValorTotalEmEstoque() {
        return this.quantidadeEstoque * this.precoUnitario;
    }
    

    @Override
    public String toString() {
        return String.format("Produto: %-20s | Categoria: %-15s | Qtd: %-5d | Preço Unit.: R$ %.2f",
                this.nome, this.categoria, this.quantidadeEstoque, this.precoUnitario);
    }
}