package com.buscapet.model;

// 3. Classe Usuario - Gerencia autenticação e perfis (RF001, RF003, RF004)
public class Usuario {
    private String id;
    private String email;
    private String senha;
    private String tipoUsuario; // "Adotante", "ONG", "Admin"
    private boolean emailVerificado;
    private boolean perfilCompleto;
    private String statusOng; // "Pendente", "Verificada", "Recusada" (apenas para ONGs)

    public Usuario(String email, String senha, String tipoUsuario) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("E-mail inválido");
        }
        if (senha == null || senha.length() < 8) {
            throw new IllegalArgumentException("Senha deve ter no mínimo 8 caracteres");
        }
        if (!tipoUsuario.equals("Adotante") && !tipoUsuario.equals("ONG") && !tipoUsuario.equals("Admin")) {
            throw new IllegalArgumentException("Tipo de usuário inválido");
        }
        
        this.email = email.toLowerCase().trim();
        this.senha = senha; // Em produção, deveria ser hash
        this.tipoUsuario = tipoUsuario;
        this.emailVerificado = false;
        this.perfilCompleto = false;
        
        if (tipoUsuario.equals("ONG")) {
            this.statusOng = "Pendente";
        }
    }

    public boolean autenticar(String email, String senha) {
        if (!this.emailVerificado) {
            throw new IllegalStateException("E-mail não verificado");
        }
        return this.email.equals(email.toLowerCase().trim()) && this.senha.equals(senha);
    }

    public void verificarEmail() {
        this.emailVerificado = true;
    }

    public void completarPerfil() {
        if (!this.emailVerificado) {
            throw new IllegalStateException("E-mail deve ser verificado antes de completar o perfil");
        }
        this.perfilCompleto = true;
    }

    public void verificarOng() {
        if (!this.tipoUsuario.equals("ONG")) {
            throw new IllegalStateException("Usuário não é uma ONG");
        }
        this.statusOng = "Verificada";
    }

    public void recusarOng(String motivo) {
        if (!this.tipoUsuario.equals("ONG")) {
            throw new IllegalStateException("Usuário não é uma ONG");
        }
        if (motivo == null || motivo.trim().isEmpty()) {
            throw new IllegalArgumentException("Motivo da recusa é obrigatório");
        }
        this.statusOng = "Recusada";
    }

    public boolean podePublicarAnimais() {
        return this.tipoUsuario.equals("ONG") && 
               this.statusOng.equals("Verificada") && 
               this.emailVerificado;
    }

    public boolean podeCandidatar() {
        return this.tipoUsuario.equals("Adotante") && 
               this.emailVerificado && 
               this.perfilCompleto;
    }

    // Getters
    public String getEmail() { return email; }
    public String getTipoUsuario() { return tipoUsuario; }
    public boolean isEmailVerificado() { return emailVerificado; }
    public boolean isPerfilCompleto() { return perfilCompleto; }
    public String getStatusOng() { return statusOng; }
}