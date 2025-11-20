package com.buscapet.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    @Test
    @DisplayName("Deve criar animal com dados válidos")
    void deveCriarAnimalComDadosValidos() {
        // Arrange & Act
        Animal animal = new Animal("Rex", "Cachorro", "Grande", 24, "ong123");
        
        // Assert
        assertEquals("Rex", animal.getNome());
        assertEquals("Cachorro", animal.getEspecie());
        assertEquals("Grande", animal.getPorte());
        assertEquals(24, animal.getIdade());
        assertEquals("Disponível", animal.getStatus());
        assertFalse(animal.isVacinado());
        assertFalse(animal.isCastrado());
    }

    @Test
    @DisplayName("Deve lançar exceção quando nome é vazio")
    void deveLancarExcecaoQuandoNomeVazio() {
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Animal("", "Cachorro", "Grande", 24, "ong123");
        });
        
        assertEquals("Nome do animal é obrigatório", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando espécie é inválida")
    void deveLancarExcecaoQuandoEspecieInvalida() {
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Animal("Rex", "Coelho", "Pequeno", 12, "ong123");
        });
        
        assertEquals("Espécie deve ser 'Cachorro' ou 'Gato'", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando idade é negativa")
    void deveLancarExcecaoQuandoIdadeNegativa() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Animal("Rex", "Cachorro", "Grande", -5, "ong123");
        });
    }

    @Test
    @DisplayName("Deve permitir adoção quando animal está disponível")
    void devePermitirAdocaoQuandoDisponivel() {
        // Arrange
        Animal animal = new Animal("Rex", "Cachorro", "Grande", 24, "ong123");
        
        // Act & Assert
        assertTrue(animal.podeSerAdotado());
    }

    @Test
    @DisplayName("Deve marcar animal como adotado")
    void deveMarcarComoAdotado() {
        // Arrange
        Animal animal = new Animal("Rex", "Cachorro", "Grande", 24, "ong123");
        
        // Act
        animal.marcarComoAdotado();
        
        // Assert
        assertEquals("Adotado", animal.getStatus());
        assertFalse(animal.podeSerAdotado());
    }

    @Test
    @DisplayName("Não deve permitir adotar animal já adotado")
    void naoDevePermitirAdotarAnimalJaAdotado() {
        // Arrange
        Animal animal = new Animal("Rex", "Cachorro", "Grande", 24, "ong123");
        animal.marcarComoAdotado();
        
        // Act & Assert
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            animal.marcarComoAdotado();
        });
        
        assertEquals("Animal não está disponível para adoção", exception.getMessage());
    }

    @Test
    @DisplayName("Deve atualizar status do animal")
    void deveAtualizarStatus() {
        // Arrange
        Animal animal = new Animal("Rex", "Cachorro", "Grande", 24, "ong123");
        
        // Act
        animal.atualizarStatus("Em processo");
        
        // Assert
        assertEquals("Em processo", animal.getStatus());
    }

    @Test
    @DisplayName("Deve atualizar informações de saúde")
    void deveAtualizarInformacoesSaude() {
        // Arrange
        Animal animal = new Animal("Rex", "Cachorro", "Grande", 24, "ong123");
        
        // Act
        animal.setVacinado(true);
        animal.setCastrado(true);
        
        // Assert
        assertTrue(animal.isVacinado());
        assertTrue(animal.isCastrado());
    }
}
