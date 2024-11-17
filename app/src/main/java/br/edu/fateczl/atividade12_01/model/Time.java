package br.edu.fateczl.atividade12_01.model;

public class Time {
    /*
     * @author: Gustavo Guimarães de Oliveira
     */
    private int codigo;
    private String nome;
    private String cidade;

    public Time() {
        super();
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    @Override
    public String toString() {
        return String.format("%d - %s - %s", codigo, nome, cidade);
    }
}
