package br.edu.fateczl.atividade12_01.model;

import java.time.LocalDate;

public class Jogador {
    /*
     * @author: Gustavo Guimarães de Oliveira
     */
    private int id;
    private String nome;
    private LocalDate dataNasc;
    private float altura;
    private float peso;
    private Time time;

    public Jogador() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }
    public void setDataNasc(String dataNasc) {
        String nasc = dataNasc;
        if (nasc.equals("")) {
            nasc = "9999-01-01";
        }
        this.dataNasc = LocalDate.parse(nasc);
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return String.format("ID: %d - Nome: %s - Nasc.: %s - Altura: %.2fm - Peso: %.2fkg - Time: %s", id, nome, dataNasc.toString(), altura, peso, time.getNome());
    }
}
