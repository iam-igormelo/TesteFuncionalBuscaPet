package com.buscapet.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

class CandidaturaTest {

    // ========================================================================
    // ANÁLISE DE VALOR LIMITE - Pontuação
    // Limites identificados:
    // - Limite inferior: 0
    // - Limite superior: 100
    // - Limite de pré-aprovação: 70
    // Valores de teste: -1, 0, 1, 69, 70, 71, 99, 100, 101
    // ========================================================================
    
    @Nested
    @DisplayName("Testes de Análise de Valor Limite - Pontuação")
    class TestePontuacaoValorLimite {
        
        @Test
        @DisplayName("AVL: Pontuação = -1 (abaixo do limite inferior - inválido)")
        void pontuacaoAbaixoLimiteInferior() {
            Candidatura candidatura = new Candidatura("adot123", "animal456");
            
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                candidatura.atribuirPontuacao(-1);
            });
            assertEquals("Pontuação deve estar entre 0 e 100", exception.getMessage());
        }
        
        @Test
        @DisplayName("AVL: Pontuação = 0 (no limite inferior - válido)")
        void pontuacaoNoLimiteInferior() {
            Candidatura candidatura = new Candidatura("adot123", "animal456");
            candidatura.atribuirPontuacao(0);
            
            assertEquals(0, candidatura.getPontuacao());
            assertNotEquals("Pré-aprovado", candidatura.getStatusCandidatura());
        }
        
        @Test
        @DisplayName("AVL: Pontuação = 1 (acima do limite inferior - válido)")
        void pontuacaoAcimaLimiteInferior() {
            Candidatura candidatura = new Candidatura("adot123", "animal456");
            candidatura.atribuirPontuacao(1);
            
            assertEquals(1, candidatura.getPontuacao());
        }
        
        @Test
        @DisplayName("AVL: Pontuação = 69 (abaixo do limite de pré-aprovação - não pré-aprova)")
        void pontuacaoAbaixoLimitePreAprovacao() {
            Candidatura candidatura = new Candidatura("adot123", "animal456");
            candidatura.iniciarAnalise();
            candidatura.atribuirPontuacao(69);
            
            assertEquals(69, candidatura.getPontuacao());
            assertEquals("Em análise", candidatura.getStatusCandidatura());
        }
        
        @Test
        @DisplayName("AVL: Pontuação = 70 (no limite de pré-aprovação - pré-aprova)")
        void pontuacaoNoLimitePreAprovacao() {
            Candidatura candidatura = new Candidatura("adot123", "animal456");
            candidatura.iniciarAnalise();
            candidatura.atribuirPontuacao(70);
            
            assertEquals(70, candidatura.getPontuacao());
            assertEquals("Pré-aprovado", candidatura.getStatusCandidatura());
        }
        
        @Test
        @DisplayName("AVL: Pontuação = 71 (acima do limite de pré-aprovação - pré-aprova)")
        void pontuacaoAcimaLimitePreAprovacao() {
            Candidatura candidatura = new Candidatura("adot123", "animal456");
            candidatura.iniciarAnalise();
            candidatura.atribuirPontuacao(71);
            
            assertEquals(71, candidatura.getPontuacao());
            assertEquals("Pré-aprovado", candidatura.getStatusCandidatura());
        }
        
        @Test
        @DisplayName("AVL: Pontuação = 99 (abaixo do limite superior - válido)")
        void pontuacaoAbaixoLimiteSuperior() {
            Candidatura candidatura = new Candidatura("adot123", "animal456");
            candidatura.iniciarAnalise();
            candidatura.atribuirPontuacao(99);
            
            assertEquals(99, candidatura.getPontuacao());
        }
        
        @Test
        @DisplayName("AVL: Pontuação = 100 (no limite superior - válido)")
        void pontuacaoNoLimiteSuperior() {
            Candidatura candidatura = new Candidatura("adot123", "animal456");
            candidatura.iniciarAnalise();
            candidatura.atribuirPontuacao(100);
            
            assertEquals(100, candidatura.getPontuacao());
            assertEquals("Pré-aprovado", candidatura.getStatusCandidatura());
        }
        
        @Test
        @DisplayName("AVL: Pontuação = 101 (acima do limite superior - inválido)")
        void pontuacaoAcimaLimiteSuperior() {
            Candidatura candidatura = new Candidatura("adot123", "animal456");
            
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                candidatura.atribuirPontuacao(101);
            });
            assertEquals("Pontuação deve estar entre 0 e 100", exception.getMessage());
        }
    }

    // ========================================================================
    // PARTIÇÃO DE EQUIVALÊNCIA - Status da Candidatura
    // Partições para aprovação:
    // 1. Status = "Recebida" → pode aprovar
    // 2. Status = "Em análise" → pode aprovar
    // 3. Status = "Pré-aprovado" → pode aprovar
    // 4. Status = "Aprovada" → não pode aprovar (já aprovada)
    // 5. Status = "Recusada" → não pode aprovar
    // ========================================================================
    
    @Nested
    @DisplayName("Testes de Partição de Equivalência - Aprovação de Candidatura")
    class TesteAprovacaoParticaoEquivalencia {
        
        @Test
        @DisplayName("PE: Aprovar candidatura Recebida (partição válida)")
        void aprovarCandidaturaRecebida() {
            Candidatura candidatura = new Candidatura("adot123", "animal456");
            
            candidatura.aprovar();
            
            assertEquals("Aprovada", candidatura.getStatusCandidatura());
            assertTrue(candidatura.estaAprovada());
        }
        
        @Test
        @DisplayName("PE: Aprovar candidatura Em análise (partição válida)")
        void aprovarCandidaturaEmAnalise() {
            Candidatura candidatura = new Candidatura("adot123", "animal456");
            candidatura.iniciarAnalise();
            
            candidatura.aprovar();
            
            assertEquals("Aprovada", candidatura.getStatusCandidatura());
        }
        
        @Test
        @DisplayName("PE: Aprovar candidatura Pré-aprovado (partição válida)")
        void aprovarCandidaturaPreAprovada() {
            Candidatura candidatura = new Candidatura("adot123", "animal456");
            candidatura.iniciarAnalise();
            candidatura.atribuirPontuacao(80);
            
            candidatura.aprovar();
            
            assertEquals("Aprovada", candidatura.getStatusCandidatura());
        }
        
        @Test
        @DisplayName("PE: Tentar aprovar candidatura já Aprovada (partição inválida)")
        void tentarAprovarCandidaturaJaAprovada() {
            Candidatura candidatura = new Candidatura("adot123", "animal456");
            candidatura.aprovar();
            
            Exception exception = assertThrows(IllegalStateException.class, () -> {
                candidatura.aprovar();
            });
            assertEquals("Candidatura já foi aprovada", exception.getMessage());
        }
        
        @Test
        @DisplayName("PE: Tentar aprovar candidatura Recusada (partição inválida)")
        void tentarAprovarCandidaturaRecusada() {
            Candidatura candidatura = new Candidatura("adot123", "animal456");
            candidatura.recusar("Perfil inadequado");
            
            Exception exception = assertThrows(IllegalStateException.class, () -> {
                candidatura.aprovar();
            });
            assertEquals("Candidatura já foi recusada", exception.getMessage());
        }
    }

    // ========================================================================
    // PARTIÇÃO DE EQUIVALÊNCIA - Motivo de Recusa
    // Partições:
    // 1. Motivo válido (string não vazia)
    // 2. Motivo vazio
    // 3. Motivo nulo
    // ========================================================================
    
    @Nested
    @DisplayName("Testes de Partição de Equivalência - Recusa com Motivo")
    class TesteRecusaMotivoParticaoEquivalencia {
        
        @Test
        @DisplayName("PE: Recusar com motivo válido (partição válida)")
        void recusarComMotivoValido() {
            Candidatura candidatura = new Candidatura("adot123", "animal456");
            String motivo = "Não possui espaço adequado";
            
            candidatura.recusar(motivo);
            
            assertEquals("Recusada", candidatura.getStatusCandidatura());
            assertEquals(motivo, candidatura.getMotivoRecusa());
        }
        
        @Test
        @DisplayName("PE: Recusar com motivo vazio (partição inválida)")
        void recusarComMotivoVazio() {
            Candidatura candidatura = new Candidatura("adot123", "animal456");
            
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                candidatura.recusar("");
            });
            assertEquals("Motivo da recusa é obrigatório", exception.getMessage());
        }
        
        @Test
        @DisplayName("PE: Recusar com motivo nulo (partição inválida)")
        void recusarComMotivoNulo() {
            Candidatura candidatura = new Candidatura("adot123", "animal456");
            
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                candidatura.recusar(null);
            });
            assertEquals("Motivo da recusa é obrigatório", exception.getMessage());
        }
    }
}