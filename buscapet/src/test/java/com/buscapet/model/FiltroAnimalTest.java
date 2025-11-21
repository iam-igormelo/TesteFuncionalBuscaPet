package com.buscapet.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class FiltroAnimalTest {

    private Animal cachorroPequenoFilhote;
    private Animal cachorroGrandeAdulto;
    private Animal gatoPequenoFilhote;

    @BeforeEach
    void setUp() {
        cachorroPequenoFilhote = new Animal("Rex", "Cachorro", "Pequeno", 6, "ong1");
        cachorroGrandeAdulto = new Animal("Thor", "Cachorro", "Grande", 36, "ong1");
        cachorroGrandeAdulto.setVacinado(true);
        cachorroGrandeAdulto.setCastrado(true);
        
        gatoPequenoFilhote = new Animal("Mia", "Gato", "Pequeno", 4, "ong2");
        gatoPequenoFilhote.setVacinado(true);
    }

    // ========================================================================
    // ANÁLISE DE VALOR LIMITE - Faixa de Idade
    // Limites identificados:
    // - Limite inferior: 0 (idade mínima possível)
    // - Valores de teste para mínima: -1, 0, 1
    // - Valores de teste para relação min/max: min < max, min = max, min > max
    // ========================================================================
    
    @Nested
    @DisplayName("Testes de Análise de Valor Limite - Faixa de Idade")
    class TesteIdadeFaixaValorLimite {
        
        @Test
        @DisplayName("AVL: Idade mínima = -1 (abaixo do limite - inválido)")
        void idadeMinimaAbaixoLimite() {
            FiltroAnimal filtro = new FiltroAnimal();
            
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                filtro.setFaixaIdade(-1, 12);
            });
            assertEquals("Idade mínima não pode ser negativa", exception.getMessage());
        }
        
        @Test
        @DisplayName("AVL: Idade mínima = 0 (no limite inferior - válido)")
        void idadeMinimaNoLimite() {
            FiltroAnimal filtro = new FiltroAnimal();
            filtro.setFaixaIdade(0, 12);
            
            assertEquals(0, filtro.getIdadeMinima());
        }
        
        @Test
        @DisplayName("AVL: Idade mínima = 1 (acima do limite - válido)")
        void idadeMinimaAcimaLimite() {
            FiltroAnimal filtro = new FiltroAnimal();
            filtro.setFaixaIdade(1, 12);
            
            assertEquals(1, filtro.getIdadeMinima());
        }
        
        @Test
        @DisplayName("AVL: Idade máxima = -1 (abaixo do limite - inválido)")
        void idadeMaximaAbaixoLimite() {
            FiltroAnimal filtro = new FiltroAnimal();
            
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                filtro.setFaixaIdade(0, -1);
            });
            assertEquals("Idade máxima não pode ser negativa", exception.getMessage());
        }
        
        @Test
        @DisplayName("AVL: Min = Max (no limite de igualdade - válido)")
        void idadeMinimaIgualMaxima() {
            FiltroAnimal filtro = new FiltroAnimal();
            filtro.setFaixaIdade(12, 12);
            
            assertEquals(12, filtro.getIdadeMinima());
            assertEquals(12, filtro.getIdadeMaxima());
        }
        
        @Test
        @DisplayName("AVL: Min = 13, Max = 12 (min > max - inválido)")
        void idadeMinimaMAiorQueMaxima() {
            FiltroAnimal filtro = new FiltroAnimal();
            
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                filtro.setFaixaIdade(13, 12);
            });
            assertEquals("Idade mínima não pode ser maior que a máxima", exception.getMessage());
        }
        
        @Test
        @DisplayName("AVL: Min = 12, Max = 13 (min < max - válido)")
        void idadeMinimaMenorQueMaxima() {
            FiltroAnimal filtro = new FiltroAnimal();
            filtro.setFaixaIdade(12, 13);
            
            assertEquals(12, filtro.getIdadeMinima());
            assertEquals(13, filtro.getIdadeMaxima());
        }
    }

    // ========================================================================
    // PARTIÇÃO DE EQUIVALÊNCIA - Filtro por Espécie
    // Partições:
    // 1. Espécie = "Cachorro" → filtra cachorros
    // 2. Espécie = "Gato" → filtra gatos
    // 3. Espécie = null → não filtra por espécie
    // 4. Espécie = valor inválido → exceção
    // ========================================================================
    
    @Nested
    @DisplayName("Testes de Partição de Equivalência - Filtro por Espécie")
    class TesteFiltroEspecieParticaoEquivalencia {
        
        @Test
        @DisplayName("PE: Filtro Cachorro - corresponde apenas cachorros (partição válida)")
        void filtroCachorro() {
            FiltroAnimal filtro = new FiltroAnimal();
            filtro.setEspecie("Cachorro");
            
            assertTrue(filtro.corresponde(cachorroPequenoFilhote));
            assertTrue(filtro.corresponde(cachorroGrandeAdulto));
            assertFalse(filtro.corresponde(gatoPequenoFilhote));
        }
        
        @Test
        @DisplayName("PE: Filtro Gato - corresponde apenas gatos (partição válida)")
        void filtroGato() {
            FiltroAnimal filtro = new FiltroAnimal();
            filtro.setEspecie("Gato");
            
            assertFalse(filtro.corresponde(cachorroPequenoFilhote));
            assertFalse(filtro.corresponde(cachorroGrandeAdulto));
            assertTrue(filtro.corresponde(gatoPequenoFilhote));
        }
        
        @Test
        @DisplayName("PE: Filtro null - corresponde a todos (partição válida)")
        void filtroEspecieNull() {
            FiltroAnimal filtro = new FiltroAnimal();
            filtro.setEspecie(null);
            
            assertTrue(filtro.corresponde(cachorroPequenoFilhote));
            assertTrue(filtro.corresponde(cachorroGrandeAdulto));
            assertTrue(filtro.corresponde(gatoPequenoFilhote));
        }
        
        @Test
        @DisplayName("PE: Filtro espécie inválida (partição inválida)")
        void filtroEspecieInvalida() {
            FiltroAnimal filtro = new FiltroAnimal();
            
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                filtro.setEspecie("Coelho");
            });
            assertEquals("Espécie deve ser 'Cachorro' ou 'Gato'", exception.getMessage());
        }
    }

    // ========================================================================
    // PARTIÇÃO DE EQUIVALÊNCIA - Filtro por Porte
    // Partições:
    // 1. Porte = "Pequeno" → filtra pequenos
    // 2. Porte = "Médio" → filtra médios
    // 3. Porte = "Grande" → filtra grandes
    // 4. Porte = null → não filtra por porte
    // 5. Porte = valor inválido → exceção
    // ========================================================================
    
    @Nested
    @DisplayName("Testes de Partição de Equivalência - Filtro por Porte")
    class TesteFiltroPorteParticaoEquivalencia {
        
        @Test
        @DisplayName("PE: Filtro Pequeno - corresponde apenas pequenos (partição válida)")
        void filtroPequeno() {
            FiltroAnimal filtro = new FiltroAnimal();
            filtro.setPorte("Pequeno");
            
            assertTrue(filtro.corresponde(cachorroPequenoFilhote));
            assertFalse(filtro.corresponde(cachorroGrandeAdulto));
            assertTrue(filtro.corresponde(gatoPequenoFilhote));
        }
        
        @Test
        @DisplayName("PE: Filtro Médio - corresponde apenas médios (partição válida)")
        void filtroMedio() {
            FiltroAnimal filtro = new FiltroAnimal();
            filtro.setPorte("Médio");
            
            Animal cachorroMedio = new Animal("Bobby", "Cachorro", "Médio", 24, "ong1");
            
            assertFalse(filtro.corresponde(cachorroPequenoFilhote));
            assertTrue(filtro.corresponde(cachorroMedio));
            assertFalse(filtro.corresponde(cachorroGrandeAdulto));
        }
        
        @Test
        @DisplayName("PE: Filtro Grande - corresponde apenas grandes (partição válida)")
        void filtroGrande() {
            FiltroAnimal filtro = new FiltroAnimal();
            filtro.setPorte("Grande");
            
            assertFalse(filtro.corresponde(cachorroPequenoFilhote));
            assertTrue(filtro.corresponde(cachorroGrandeAdulto));
            assertFalse(filtro.corresponde(gatoPequenoFilhote));
        }
        
        @Test
        @DisplayName("PE: Filtro porte null - corresponde a todos (partição válida)")
        void filtroPorteNull() {
            FiltroAnimal filtro = new FiltroAnimal();
            filtro.setPorte(null);
            
            assertTrue(filtro.corresponde(cachorroPequenoFilhote));
            assertTrue(filtro.corresponde(cachorroGrandeAdulto));
            assertTrue(filtro.corresponde(gatoPequenoFilhote));
        }
        
        @Test
        @DisplayName("PE: Filtro porte inválido (partição inválida)")
        void filtroPorteInvalido() {
            FiltroAnimal filtro = new FiltroAnimal();
            
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                filtro.setPorte("Gigante");
            });
            assertEquals("Porte inválido", exception.getMessage());
        }
    }

    // ========================================================================
    // PARTIÇÃO DE EQUIVALÊNCIA - Filtro por Vacinação
    // Partições:
    // 1. Vacinado = true → filtra apenas vacinados
    // 2. Vacinado = false → filtra apenas não vacinados
    // 3. Vacinado = null → não filtra por vacinação
    // ========================================================================
    
    @Nested
    @DisplayName("Testes de Partição de Equivalência - Filtro por Vacinação")
    class TesteFiltroVacinadoParticaoEquivalencia {
        
        @Test
        @DisplayName("PE: Filtro vacinado = true - apenas vacinados (partição válida)")
        void filtroVacinados() {
            FiltroAnimal filtro = new FiltroAnimal();
            filtro.setVacinado(true);
            
            assertFalse(filtro.corresponde(cachorroPequenoFilhote)); // não vacinado
            assertTrue(filtro.corresponde(cachorroGrandeAdulto)); // vacinado
            assertTrue(filtro.corresponde(gatoPequenoFilhote)); // vacinado
        }
        
        @Test
        @DisplayName("PE: Filtro vacinado = false - apenas não vacinados (partição válida)")
        void filtroNaoVacinados() {
            FiltroAnimal filtro = new FiltroAnimal();
            filtro.setVacinado(false);
            
            assertTrue(filtro.corresponde(cachorroPequenoFilhote)); // não vacinado
            assertFalse(filtro.corresponde(cachorroGrandeAdulto)); // vacinado
            assertFalse(filtro.corresponde(gatoPequenoFilhote)); // vacinado
        }
        
        @Test
        @DisplayName("PE: Filtro vacinado = null - todos (partição válida)")
        void filtroVacinadoNull() {
            FiltroAnimal filtro = new FiltroAnimal();
            filtro.setVacinado(null);
            
            assertTrue(filtro.corresponde(cachorroPequenoFilhote));
            assertTrue(filtro.corresponde(cachorroGrandeAdulto));
            assertTrue(filtro.corresponde(gatoPequenoFilhote));
        }
    }

    // ========================================================================
    // PARTIÇÃO DE EQUIVALÊNCIA - Filtro por Castração
    // Partições:
    // 1. Castrado = true → filtra apenas castrados
    // 2. Castrado = false → filtra apenas não castrados
    // 3. Castrado = null → não filtra por castração
    // ========================================================================
    
    @Nested
    @DisplayName("Testes de Partição de Equivalência - Filtro por Castração")
    class TesteFiltroCastradoParticaoEquivalencia {
        
        @Test
        @DisplayName("PE: Filtro castrado = true - apenas castrados (partição válida)")
        void filtroCastrados() {
            FiltroAnimal filtro = new FiltroAnimal();
            filtro.setCastrado(true);
            
            assertFalse(filtro.corresponde(cachorroPequenoFilhote)); // não castrado
            assertTrue(filtro.corresponde(cachorroGrandeAdulto)); // castrado
            assertFalse(filtro.corresponde(gatoPequenoFilhote)); // não castrado
        }
        
        @Test
        @DisplayName("PE: Filtro castrado = false - apenas não castrados (partição válida)")
        void filtroNaoCastrados() {
            FiltroAnimal filtro = new FiltroAnimal();
            filtro.setCastrado(false);
            
            assertTrue(filtro.corresponde(cachorroPequenoFilhote)); // não castrado
            assertFalse(filtro.corresponde(cachorroGrandeAdulto)); // castrado
            assertTrue(filtro.corresponde(gatoPequenoFilhote)); // não castrado
        }
        
        @Test
        @DisplayName("PE: Filtro castrado = null - todos (partição válida)")
        void filtroCastradoNull() {
            FiltroAnimal filtro = new FiltroAnimal();
            filtro.setCastrado(null);
            
            assertTrue(filtro.corresponde(cachorroPequenoFilhote));
            assertTrue(filtro.corresponde(cachorroGrandeAdulto));
            assertTrue(filtro.corresponde(gatoPequenoFilhote));
        }
    }

    // ========================================================================
    // PARTIÇÃO DE EQUIVALÊNCIA - Filtro por Status
    // Partições:
    // 1. Status = "Disponível" → filtra disponíveis
    // 2. Status = "Adotado" → filtra adotados
    // 3. Status = "Em processo" → filtra em processo
    // 4. Status = null → não filtra por status
    // ========================================================================
    
    @Nested
    @DisplayName("Testes de Partição de Equivalência - Filtro por Status")
    class TesteFiltroStatusParticaoEquivalencia {
        
        @Test
        @DisplayName("PE: Filtro Disponível - apenas disponíveis (partição válida)")
        void filtroDisponiveis() {
            FiltroAnimal filtro = new FiltroAnimal();
            filtro.setStatus("Disponível");
            
            assertTrue(filtro.corresponde(cachorroPequenoFilhote));
            assertTrue(filtro.corresponde(cachorroGrandeAdulto));
            
            cachorroGrandeAdulto.marcarComoAdotado();
            assertFalse(filtro.corresponde(cachorroGrandeAdulto));
        }
        
        @Test
        @DisplayName("PE: Filtro Adotado - apenas adotados (partição válida)")
        void filtroAdotados() {
            FiltroAnimal filtro = new FiltroAnimal();
            filtro.setStatus("Adotado");
            
            assertFalse(filtro.corresponde(cachorroPequenoFilhote));
            
            cachorroPequenoFilhote.marcarComoAdotado();
            assertTrue(filtro.corresponde(cachorroPequenoFilhote));
        }
        
        @Test
        @DisplayName("PE: Filtro Em processo (partição válida)")
        void filtroEmProcesso() {
            FiltroAnimal filtro = new FiltroAnimal();
            filtro.setStatus("Em processo");
            
            assertFalse(filtro.corresponde(cachorroPequenoFilhote));
            
            cachorroPequenoFilhote.atualizarStatus("Em processo");
            assertTrue(filtro.corresponde(cachorroPequenoFilhote));
        }
        
        @Test
        @DisplayName("PE: Filtro status = null - todos (partição válida)")
        void filtroStatusNull() {
            FiltroAnimal filtro = new FiltroAnimal();
            filtro.setStatus(null);
            
            assertTrue(filtro.corresponde(cachorroPequenoFilhote));
            
            cachorroPequenoFilhote.marcarComoAdotado();
            assertTrue(filtro.corresponde(cachorroPequenoFilhote));
        }
    }

    // ========================================================================
    // TESTES COMBINADOS - Múltiplos Filtros
    // ========================================================================
    
    @Nested
    @DisplayName("Testes Combinados - Múltiplos Filtros")
    class TestesFiltrosCombinados {
        
        @Test
        @DisplayName("Combinado: Cachorro + Grande + Vacinado + Castrado")
        void filtroCombinado1() {
            FiltroAnimal filtro = new FiltroAnimal();
            filtro.setEspecie("Cachorro");
            filtro.setPorte("Grande");
            filtro.setVacinado(true);
            filtro.setCastrado(true);
            
            assertFalse(filtro.corresponde(cachorroPequenoFilhote)); // Pequeno
            assertTrue(filtro.corresponde(cachorroGrandeAdulto)); // Atende todos
            assertFalse(filtro.corresponde(gatoPequenoFilhote)); // Gato
            
            assertEquals(4, filtro.contarFiltrosAtivos());
        }
        
        @Test
        @DisplayName("Combinado: Filhotes (0-12 meses) de qualquer espécie")
        void filtroCombinado2() {
            FiltroAnimal filtro = new FiltroAnimal();
            filtro.setFaixaIdade(0, 12);
            
            assertTrue(filtro.corresponde(cachorroPequenoFilhote)); // 6 meses
            assertFalse(filtro.corresponde(cachorroGrandeAdulto)); // 36 meses
            assertTrue(filtro.corresponde(gatoPequenoFilhote)); // 4 meses
            
            assertEquals(1, filtro.contarFiltrosAtivos());
        }
        
        @Test
        @DisplayName("Combinado: Gatos pequenos vacinados disponíveis")
        void filtroCombinado3() {
            FiltroAnimal filtro = new FiltroAnimal();
            filtro.setEspecie("Gato");
            filtro.setPorte("Pequeno");
            filtro.setVacinado(true);
            filtro.setStatus("Disponível");
            
            assertFalse(filtro.corresponde(cachorroPequenoFilhote));
            assertFalse(filtro.corresponde(cachorroGrandeAdulto));
            assertTrue(filtro.corresponde(gatoPequenoFilhote));
            
            assertEquals(4, filtro.contarFiltrosAtivos());
        }
    }
}