package com.buscapet.model;

// 4. Classe FiltroAnimal - Implementa busca e filtros (RF003)
public class FiltroAnimal {
    private String especie;
    private String porte;
    private Integer idadeMinima;
    private Integer idadeMaxima;
    private Boolean vacinado;
    private Boolean castrado;
    private String status;

    public FiltroAnimal() {
        // Construtor vazio para filtro sem restrições
    }

    public void setEspecie(String especie) {
        if (especie != null && !especie.equals("Cachorro") && !especie.equals("Gato")) {
            throw new IllegalArgumentException("Espécie deve ser 'Cachorro' ou 'Gato'");
        }
        this.especie = especie;
    }

    public void setPorte(String porte) {
        if (porte != null && !porte.equals("Pequeno") && !porte.equals("Médio") && !porte.equals("Grande")) {
            throw new IllegalArgumentException("Porte inválido");
        }
        this.porte = porte;
    }

    public void setFaixaIdade(Integer min, Integer max) {
        if (min != null && min < 0) {
            throw new IllegalArgumentException("Idade mínima não pode ser negativa");
        }
        if (max != null && max < 0) {
            throw new IllegalArgumentException("Idade máxima não pode ser negativa");
        }
        if (min != null && max != null && min > max) {
            throw new IllegalArgumentException("Idade mínima não pode ser maior que a máxima");
        }
        this.idadeMinima = min;
        this.idadeMaxima = max;
    }

    public void setVacinado(Boolean vacinado) {
        this.vacinado = vacinado;
    }

    public void setCastrado(Boolean castrado) {
        this.castrado = castrado;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean corresponde(Animal animal) {
        if (especie != null && !animal.getEspecie().equals(especie)) {
            return false;
        }
        if (porte != null && !animal.getPorte().equals(porte)) {
            return false;
        }
        if (idadeMinima != null && animal.getIdade() < idadeMinima) {
            return false;
        }
        if (idadeMaxima != null && animal.getIdade() > idadeMaxima) {
            return false;
        }
        if (vacinado != null && animal.isVacinado() != vacinado) {
            return false;
        }
        if (castrado != null && animal.isCastrado() != castrado) {
            return false;
        }
        if (status != null && !animal.getStatus().equals(status)) {
            return false;
        }
        return true;
    }

    public int contarFiltrosAtivos() {
        int count = 0;
        if (especie != null) count++;
        if (porte != null) count++;
        if (idadeMinima != null || idadeMaxima != null) count++;
        if (vacinado != null) count++;
        if (castrado != null) count++;
        if (status != null) count++;
        return count;
    }

    // Getters
    public String getEspecie() { return especie; }
    public String getPorte() { return porte; }
    public Integer getIdadeMinima() { return idadeMinima; }
    public Integer getIdadeMaxima() { return idadeMaxima; }
}