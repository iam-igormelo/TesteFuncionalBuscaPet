package com.buscapet.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    // ========================================================================
    // PARTIÇÃO DE EQUIVALÊNCIA - Nome
    // Partições:
    // 1. Nome válido (não vazio, não nulo)
    // 2. Nome vazio
    // 3. Nome nulo
    // 4. Nome com espaços em branco
    // ========================================================================
    
    @Nested
    @DisplayName("Testes de Partição de Equivalência - Nome")
    class TesteNomeParticaoEquivalencia {
        
        @Test
        @DisplayName("PE: Nome válido (partição válida)")
        void nomeValido() {
            Animal animal = new Animal("Rex", "Cachorro", "Grande", 24, "ong123");
            assertEquals("Rex", animal.getNome());
        }
        
        @Test
        @DisplayName("PE: Nome vazio (partição inválida)")
        void nomeVazio() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                new Animal("", "Cachorro", "Grande", 24, "ong123");
            });
            assertEquals("Nome do animal é obrigatório", exception.getMessage());
        }
        
        @Test
        @DisplayName("PE: Nome nulo (partição inválida)")
        void nomeNulo() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                new Animal(null, "Cachorro", "Grande", 24, "ong123");
            });
            assertEquals("Nome do animal é obrigatório", exception.getMessage());
        }
        
        @Test
        @DisplayName("PE: Nome apenas com espaços (partição inválida)")
        void nomeApenasEspacos() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                new Animal("   ", "Cachorro", "Grande", 24, "ong123");
            });
            assertEquals("Nome do animal é obrigatório", exception.getMessage());
        }
    }

    // ========================================================================
    // PARTIÇÃO DE EQUIVALÊNCIA - Espécie
    // Partições:
    // 1. "Cachorro" (válida)
    // 2. "Gato" (válida)
    // 3. Qualquer outra string (inválida)
    // ========================================================================
    
    @Nested
    @DisplayName("Testes de Partição de Equivalência - Espécie")
    class TesteEspecieParticaoEquivalencia {
        
        @Test
        @DisplayName("PE: Espécie Cachorro (partição válida)")
        void especieCachorro() {
            Animal animal = new Animal("Rex", "Cachorro", "Grande", 24, "ong123");
            assertEquals("Cachorro", animal.getEspecie());
        }
        
        @Test
        @DisplayName("PE: Espécie Gato (partição válida)")
        void especieGato() {
            Animal animal = new Animal("Mia", "Gato", "Pequeno", 12, "ong123");
            assertEquals("Gato", animal.getEspecie());
        }
        
        @Test
        @DisplayName("PE: Espécie inválida - Coelho (partição inválida)")
        void especieInvalida() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                new Animal("Paco", "Coelho", "Pequeno", 6, "ong123");
            });
            assertEquals("Espécie deve ser 'Cachorro' ou 'Gato'", exception.getMessage());
        }
    }

    // ========================================================================
    // PARTIÇÃO DE EQUIVALÊNCIA - Porte
    // Partições:
    // 1. "Pequeno" (válida)
    // 2. "Médio" (válida)
    // 3. "Grande" (válida)
    // 4. Qualquer outra string (inválida)
    // ========================================================================
    
    @Nested
    @DisplayName("Testes de Partição de Equivalência - Porte")
    class TestePorteParticaoEquivalencia {
        
        @Test
        @DisplayName("PE: Porte Pequeno (partição válida)")
        void portePequeno() {
            Animal animal = new Animal("Rex", "Cachorro", "Pequeno", 12, "ong123");
            assertEquals("Pequeno", animal.getPorte());
        }
        
        @Test
        @DisplayName("PE: Porte Médio (partição válida)")
        void porteMedio() {
            Animal animal = new Animal("Rex", "Cachorro", "Médio", 24, "ong123");
            assertEquals("Médio", animal.getPorte());
        }
        
        @Test
        @DisplayName("PE: Porte Grande (partição válida)")
        void porteGrande() {
            Animal animal = new Animal("Rex", "Cachorro", "Grande", 36, "ong123");
            assertEquals("Grande", animal.getPorte());
        }
        
        @Test
        @DisplayName("PE: Porte inválido - Gigante (partição inválida)")
        void porteInvalido() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                new Animal("Rex", "Cachorro", "Gigante", 48, "ong123");
            });
            assertEquals("Porte inválido", exception.getMessage());
        }
    }

    // ========================================================================
    // ANÁLISE DE VALOR LIMITE - Idade
    // Limites identificados:
    // - Limite inferior: 0 (mínimo válido)
    // - Valores de teste: -1, 0, 1 (abaixo, no limite, acima)
    // ========================================================================
    
    @Nested
    @DisplayName("Testes de Análise de Valor Limite - Idade")
    class TesteIdadeValorLimite {
        
        @Test
        @DisplayName("AVL: Idade = -1 (abaixo do limite inferior - inválido)")
        void idadeAbaixoLimiteInferior() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                new Animal("Rex", "Cachorro", "Grande", -1, "ong123");
            });
            assertEquals("Idade não pode ser negativa", exception.getMessage());
        }
        
        @Test
        @DisplayName("AVL: Idade = 0 (no limite inferior - válido)")
        void idadeNoLimiteInferior() {
            Animal animal = new Animal("Rex", "Cachorro", "Grande", 0, "ong123");
            assertEquals(0, animal.getIdade());
        }
        
        @Test
        @DisplayName("AVL: Idade = 1 (acima do limite inferior - válido)")
        void idadeAcimaLimiteInferior() {
            Animal animal = new Animal("Rex", "Cachorro", "Grande", 1, "ong123");
            assertEquals(1, animal.getIdade());
        }
        
        @Test
        @DisplayName("AVL: Idade = 120 (valor médio válido)")
        void idadeValorMedio() {
            Animal animal = new Animal("Rex", "Cachorro", "Grande", 120, "ong123");
            assertEquals(120, animal.getIdade());
        }
    }

    // ========================================================================
    // PARTIÇÃO DE EQUIVALÊNCIA - Status
    // Partições identificadas para podeSerAdotado():
    // 1. Status = "Disponível" → retorna true
    // 2. Status ≠ "Disponível" → retorna false
    // ========================================================================
    
    @Nested
    @DisplayName("Testes de Partição de Equivalência - Status de Adoção")
    class TesteStatusAdocaoParticaoEquivalencia {
        
        @Test
        @DisplayName("PE: Status Disponível - pode ser adotado (partição válida)")
        void statusDisponivelPodeAdotar() {
            Animal animal = new Animal("Rex", "Cachorro", "Grande", 24, "ong123");
            assertTrue(animal.podeSerAdotado());
        }
        
        @Test
        @DisplayName("PE: Status Adotado - não pode ser adotado (partição inválida)")
        void statusAdotadoNaoPodeAdotar() {
            Animal animal = new Animal("Rex", "Cachorro", "Grande", 24, "ong123");
            animal.marcarComoAdotado();
            assertFalse(animal.podeSerAdotado());
        }
        
        @Test
        @DisplayName("PE: Status Em processo - não pode ser adotado (partição inválida)")
        void statusEmProcessoNaoPodeAdotar() {
            Animal animal = new Animal("Rex", "Cachorro", "Grande", 24, "ong123");
            animal.atualizarStatus("Em processo");
            assertFalse(animal.podeSerAdotado());
        }
    }
}