package com.buscapet.model;

// 2. Classe Candidatura - Gerencia solicitações de adoção (RF004, RF005)
public class Candidatura {
    private String id;
    private String adotanteId;
    private String animalId;
    private String statusCandidatura; // "Recebida", "Em análise", "Pré-aprovado", "Aprovada", "Recusada"
    private int pontuacao;
    private String motivoRecusa;
    private String dataEnvio;

    public Candidatura(String adotanteId, String animalId) {
        if (adotanteId == null || adotanteId.trim().isEmpty()) {
            throw new IllegalArgumentException("ID do adotante é obrigatório");
        }
        if (animalId == null || animalId.trim().isEmpty()) {
            throw new IllegalArgumentException("ID do animal é obrigatório");
        }
        
        this.adotanteId = adotanteId;
        this.animalId = animalId;
        this.statusCandidatura = "Recebida";
        this.pontuacao = 0;
    }

    public void iniciarAnalise() {
        if (!this.statusCandidatura.equals("Recebida")) {
            throw new IllegalStateException("Candidatura já foi analisada");
        }
        this.statusCandidatura = "Em análise";
    }

    public void atribuirPontuacao(int pontos) {
        if (pontos < 0 || pontos > 100) {
            throw new IllegalArgumentException("Pontuação deve estar entre 0 e 100");
        }
        this.pontuacao = pontos;
        
        if (pontos >= 70) {
            this.statusCandidatura = "Pré-aprovado";
        }
    }

    public void aprovar() {
        if (this.statusCandidatura.equals("Recusada")) {
            throw new IllegalStateException("Candidatura já foi recusada");
        }
        if (this.statusCandidatura.equals("Aprovada")) {
            throw new IllegalStateException("Candidatura já foi aprovada");
        }
        this.statusCandidatura = "Aprovada";
    }

    public void recusar(String motivo) {
        if (motivo == null || motivo.trim().isEmpty()) {
            throw new IllegalArgumentException("Motivo da recusa é obrigatório");
        }
        if (this.statusCandidatura.equals("Aprovada")) {
            throw new IllegalStateException("Candidatura já foi aprovada");
        }
        
        this.statusCandidatura = "Recusada";
        this.motivoRecusa = motivo;
    }

    public boolean estaAprovada() {
        return this.statusCandidatura.equals("Aprovada");
    }

    // Getters
    public String getStatusCandidatura() { return statusCandidatura; }
    public int getPontuacao() { return pontuacao; }
    public String getMotivoRecusa() { return motivoRecusa; }
    public String getAdotanteId() { return adotanteId; }
    public String getAnimalId() { return animalId; }
}