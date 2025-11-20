package com.buscapet.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class CandidaturaTest {
    
    private String adotanteId;
    private String animalId;
    
    @BeforeEach
    void setUp() {
        adotanteId = "adotante123";
        animalId = "animal456";
    }

    @Test
    @DisplayName("Deve criar candidatura com status inicial 'Recebida'")
    void deveCriarCandidaturaComStatusInicial() {
        // Act
        Candidatura candidatura = new Candidatura(adotanteId, animalId);
        
        // Assert
        assertEquals("Recebida", candidatura.getStatusCandidatura());
        assertEquals(0, candidatura.getPontuacao());
        assertEquals(adotanteId, candidatura.getAdotanteId());
        assertEquals(animalId, candidatura.getAnimalId());
    }

    @Test
    @DisplayName("Deve lançar exceção quando ID do adotante é nulo")
    void deveLancarExcecaoQuandoAdotanteNulo() {
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Candidatura(null, animalId);
        });
        
        assertEquals("ID do adotante é obrigatório", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando ID do adotante é vazio")
    void deveLancarExcecaoQuandoAdotanteVazio() {
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Candidatura("", animalId);
        });
        
        assertEquals("ID do adotante é obrigatório", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando ID do animal é nulo")
    void deveLancarExcecaoQuandoAnimalNulo() {
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Candidatura(adotanteId, null);
        });
        
        assertEquals("ID do animal é obrigatório", exception.getMessage());
    }

    @Test
    @DisplayName("Deve iniciar análise da candidatura")
    void deveIniciarAnalise() {
        // Arrange
        Candidatura candidatura = new Candidatura(adotanteId, animalId);
        
        // Act
        candidatura.iniciarAnalise();
        
        // Assert
        assertEquals("Em análise", candidatura.getStatusCandidatura());
    }

    @Test
    @DisplayName("Não deve permitir iniciar análise de candidatura já analisada")
    void naoDevePermitirIniciarAnaliseJaAnalisada() {
        // Arrange
        Candidatura candidatura = new Candidatura(adotanteId, animalId);
        candidatura.iniciarAnalise();
        
        // Act & Assert
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            candidatura.iniciarAnalise();
        });
        
        assertEquals("Candidatura já foi analisada", exception.getMessage());
    }

    @Test
    @DisplayName("Deve atribuir pontuação válida")
    void deveAtribuirPontuacaoValida() {
        // Arrange
        Candidatura candidatura = new Candidatura(adotanteId, animalId);
        candidatura.iniciarAnalise();
        
        // Act
        candidatura.atribuirPontuacao(85);
        
        // Assert
        assertEquals(85, candidatura.getPontuacao());
    }

    @Test
    @DisplayName("Deve mudar para 'Pré-aprovado' quando pontuação >= 70")
    void deveMudarParaPreAprovadoComPontuacaoAlta() {
        // Arrange
        Candidatura candidatura = new Candidatura(adotanteId, animalId);
        candidatura.iniciarAnalise();
        
        // Act
        candidatura.atribuirPontuacao(70);
        
        // Assert
        assertEquals("Pré-aprovado", candidatura.getStatusCandidatura());
    }

    @Test
    @DisplayName("Não deve mudar para 'Pré-aprovado' quando pontuação < 70")
    void naoDeveMudarParaPreAprovadoComPontuacaoBaixa() {
        // Arrange
        Candidatura candidatura = new Candidatura(adotanteId, animalId);
        candidatura.iniciarAnalise();
        
        // Act
        candidatura.atribuirPontuacao(65);
        
        // Assert
        assertEquals("Em análise", candidatura.getStatusCandidatura());
    }

    @Test
    @DisplayName("Deve lançar exceção quando pontuação é negativa")
    void deveLancarExcecaoQuandoPontuacaoNegativa() {
        // Arrange
        Candidatura candidatura = new Candidatura(adotanteId, animalId);
        
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            candidatura.atribuirPontuacao(-10);
        });
        
        assertEquals("Pontuação deve estar entre 0 e 100", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando pontuação é maior que 100")
    void deveLancarExcecaoQuandoPontuacaoMaiorQue100() {
        // Arrange
        Candidatura candidatura = new Candidatura(adotanteId, animalId);
        
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            candidatura.atribuirPontuacao(150);
        });
        
        assertEquals("Pontuação deve estar entre 0 e 100", exception.getMessage());
    }

    @Test
    @DisplayName("Deve aprovar candidatura")
    void deveAprovarCandidatura() {
        // Arrange
        Candidatura candidatura = new Candidatura(adotanteId, animalId);
        candidatura.iniciarAnalise();
        
        // Act
        candidatura.aprovar();
        
        // Assert
        assertEquals("Aprovada", candidatura.getStatusCandidatura());
        assertTrue(candidatura.estaAprovada());
    }

    @Test
    @DisplayName("Não deve aprovar candidatura já recusada")
    void naoDeveAprovarCandidaturaRecusada() {
        // Arrange
        Candidatura candidatura = new Candidatura(adotanteId, animalId);
        candidatura.recusar("Perfil inadequado");
        
        // Act & Assert
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            candidatura.aprovar();
        });
        
        assertEquals("Candidatura já foi recusada", exception.getMessage());
    }

    @Test
    @DisplayName("Não deve aprovar candidatura já aprovada")
    void naoDeveAprovarCandidaturaJaAprovada() {
        // Arrange
        Candidatura candidatura = new Candidatura(adotanteId, animalId);
        candidatura.aprovar();
        
        // Act & Assert
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            candidatura.aprovar();
        });
        
        assertEquals("Candidatura já foi aprovada", exception.getMessage());
    }

    @Test
    @DisplayName("Deve recusar candidatura com motivo")
    void deveRecusarCandidaturaComMotivo() {
        // Arrange
        Candidatura candidatura = new Candidatura(adotanteId, animalId);
        String motivo = "Não possui espaço adequado";
        
        // Act
        candidatura.recusar(motivo);
        
        // Assert
        assertEquals("Recusada", candidatura.getStatusCandidatura());
        assertEquals(motivo, candidatura.getMotivoRecusa());
        assertFalse(candidatura.estaAprovada());
    }

    @Test
    @DisplayName("Deve lançar exceção ao recusar sem motivo")
    void deveLancarExcecaoAoRecusarSemMotivo() {
        // Arrange
        Candidatura candidatura = new Candidatura(adotanteId, animalId);
        
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            candidatura.recusar("");
        });
        
        assertEquals("Motivo da recusa é obrigatório", exception.getMessage());
    }

    @Test
    @DisplayName("Não deve recusar candidatura já aprovada")
    void naoDeveRecusarCandidaturaAprovada() {
        // Arrange
        Candidatura candidatura = new Candidatura(adotanteId, animalId);
        candidatura.aprovar();
        
        // Act & Assert
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            candidatura.recusar("Tentando recusar aprovada");
        });
        
        assertEquals("Candidatura já foi aprovada", exception.getMessage());
    }
}