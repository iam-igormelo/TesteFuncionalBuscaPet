package com.buscapet.model;

// 1. Classe Animal - Representa os pets cadastrados (RF002)
public class Animal {
    private String id;
    private String nome;
    private String especie; // "Cachorro" ou "Gato"
    private String porte; // "Pequeno", "Médio", "Grande"
    private int idade; // em meses
    private String status; // "Disponível", "Adotado", "Em processo"
    private String ongId;
    private boolean vacinado;
    private boolean castrado;

    public Animal(String nome, String especie, String porte, int idade, String ongId) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do animal é obrigatório");
        }
        if (!especie.equals("Cachorro") && !especie.equals("Gato")) {
            throw new IllegalArgumentException("Espécie deve ser 'Cachorro' ou 'Gato'");
        }
        if (!porte.equals("Pequeno") && !porte.equals("Médio") && !porte.equals("Grande")) {
            throw new IllegalArgumentException("Porte inválido");
        }
        if (idade < 0) {
            throw new IllegalArgumentException("Idade não pode ser negativa");
        }
        
        this.nome = nome.trim();
        this.especie = especie;
        this.porte = porte;
        this.idade = idade;
        this.ongId = ongId;
        this.status = "Disponível";
        this.vacinado = false;
        this.castrado = false;
    }

    public boolean podeSerAdotado() {
        return this.status.equals("Disponível");
    }

    public void marcarComoAdotado() {
        if (!podeSerAdotado()) {
            throw new IllegalStateException("Animal não está disponível para adoção");
        }
        this.status = "Adotado";
    }

    public void atualizarStatus(String novoStatus) {
        if (novoStatus == null || novoStatus.trim().isEmpty()) {
            throw new IllegalArgumentException("Status não pode ser vazio");
        }
        this.status = novoStatus;
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public String getEspecie() { return especie; }
    public String getPorte() { return porte; }
    public int getIdade() { return idade; }
    public String getStatus() { return status; }
    public boolean isVacinado() { return vacinado; }
    public boolean isCastrado() { return castrado; }
    
    public void setVacinado(boolean vacinado) { this.vacinado = vacinado; }
    public void setCastrado(boolean castrado) { this.castrado = castrado; }
}