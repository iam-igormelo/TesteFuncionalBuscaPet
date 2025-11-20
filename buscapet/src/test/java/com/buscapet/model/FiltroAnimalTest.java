package com.buscapet.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
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

    @Test
    @DisplayName("Deve criar filtro vazio")
    void deveCriarFiltroVazio() {
        // Act
        FiltroAnimal filtro = new FiltroAnimal();
        
        // Assert
        assertEquals(0, filtro.contarFiltrosAtivos());
        assertNull(filtro.getEspecie());
        assertNull(filtro.getPorte());
    }

    @Test
    @DisplayName("Deve filtrar por espécie Cachorro")
    void deveFiltrarPorEspecieCachorro() {
        // Arrange
        FiltroAnimal filtro = new FiltroAnimal();
        filtro.setEspecie("Cachorro");
        
        // Act & Assert
        assertTrue(filtro.corresponde(cachorroPequenoFilhote));
        assertTrue(filtro.corresponde(cachorroGrandeAdulto));
        assertFalse(filtro.corresponde(gatoPequenoFilhote));
    }

    @Test
    @DisplayName("Deve filtrar por espécie Gato")
    void deveFiltrarPorEspecieGato() {
        // Arrange
        FiltroAnimal filtro = new FiltroAnimal();
        filtro.setEspecie("Gato");
        
        // Act & Assert
        assertFalse(filtro.corresponde(cachorroPequenoFilhote));
        assertFalse(filtro.corresponde(cachorroGrandeAdulto));
        assertTrue(filtro.corresponde(gatoPequenoFilhote));
    }

    @Test
    @DisplayName("Deve lançar exceção quando espécie é inválida")
    void deveLancarExcecaoQuandoEspecieInvalida() {
        // Arrange
        FiltroAnimal filtro = new FiltroAnimal();
        
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            filtro.setEspecie("Coelho");
        });
        
        assertEquals("Espécie deve ser 'Cachorro' ou 'Gato'", exception.getMessage());
    }

    @Test
    @DisplayName("Deve filtrar por porte")
    void deveFiltrarPorPorte() {
        // Arrange
        FiltroAnimal filtro = new FiltroAnimal();
        filtro.setPorte("Grande");
        
        // Act & Assert
        assertFalse(filtro.corresponde(cachorroPequenoFilhote));
        assertTrue(filtro.corresponde(cachorroGrandeAdulto));
        assertFalse(filtro.corresponde(gatoPequenoFilhote));
    }

    @Test
    @DisplayName("Deve lançar exceção quando porte é inválido")
    void deveLancarExcecaoQuandoPorteInvalido() {
        // Arrange
        FiltroAnimal filtro = new FiltroAnimal();
        
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            filtro.setPorte("Gigante");
        });
        
        assertEquals("Porte inválido", exception.getMessage());
    }

    @Test
    @DisplayName("Deve filtrar por faixa de idade")
    void deveFiltrarPorFaixaIdade() {
        // Arrange
        FiltroAnimal filtro = new FiltroAnimal();
        filtro.setFaixaIdade(0, 12); // Filhotes até 1 ano
        
        // Act & Assert
        assertTrue(filtro.corresponde(cachorroPequenoFilhote)); // 6 meses
        assertFalse(filtro.corresponde(cachorroGrandeAdulto)); // 36 meses
        assertTrue(filtro.corresponde(gatoPequenoFilhote)); // 4 meses
    }

    @Test
    @DisplayName("Deve filtrar apenas por idade mínima")
    void deveFiltrarPorIdadeMinima() {
        // Arrange
        FiltroAnimal filtro = new FiltroAnimal();
        filtro.setFaixaIdade(24, null); // Adultos acima de 2 anos
        
        // Act & Assert
        assertFalse(filtro.corresponde(cachorroPequenoFilhote));
        assertTrue(filtro.corresponde(cachorroGrandeAdulto));
        assertFalse(filtro.corresponde(gatoPequenoFilhote));
    }

    @Test
    @DisplayName("Deve lançar exceção quando idade mínima é negativa")
    void deveLancarExcecaoQuandoIdadeMinimaNegativa() {
        // Arrange
        FiltroAnimal filtro = new FiltroAnimal();
        
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            filtro.setFaixaIdade(-1, 12);
        });
        
        assertEquals("Idade mínima não pode ser negativa", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando idade máxima é menor que mínima")
    void deveLancarExcecaoQuandoIdadeMaximaMenorQueMinima() {
        // Arrange
        FiltroAnimal filtro = new FiltroAnimal();
        
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            filtro.setFaixaIdade(24, 12);
        });
        
        assertEquals("Idade mínima não pode ser maior que a máxima", exception.getMessage());
    }

    @Test
    @DisplayName("Deve filtrar por vacinado")
    void deveFiltrarPorVacinado() {
        // Arrange
        FiltroAnimal filtro = new FiltroAnimal();
        filtro.setVacinado(true);
        
        // Act & Assert
        assertFalse(filtro.corresponde(cachorroPequenoFilhote));
        assertTrue(filtro.corresponde(cachorroGrandeAdulto));
        assertTrue(filtro.corresponde(gatoPequenoFilhote));
    }

    @Test
    @DisplayName("Deve filtrar por castrado")
    void deveFiltrarPorCastrado() {
        // Arrange
        FiltroAnimal filtro = new FiltroAnimal();
        filtro.setCastrado(true);
        
        // Act & Assert
        assertFalse(filtro.corresponde(cachorroPequenoFilhote));
        assertTrue(filtro.corresponde(cachorroGrandeAdulto));
        assertFalse(filtro.corresponde(gatoPequenoFilhote));
    }

    @Test
    @DisplayName("Deve filtrar por status")
    void deveFiltrarPorStatus() {
        // Arrange
        FiltroAnimal filtro = new FiltroAnimal();
        filtro.setStatus("Disponível");
        cachorroGrandeAdulto.marcarComoAdotado();
        
        // Act & Assert
        assertTrue(filtro.corresponde(cachorroPequenoFilhote));
        assertFalse(filtro.corresponde(cachorroGrandeAdulto));
        assertTrue(filtro.corresponde(gatoPequenoFilhote));
    }

    @Test
    @DisplayName("Deve aplicar múltiplos filtros simultaneamente")
    void deveAplicarMultiplosFiltros() {
        // Arrange
        FiltroAnimal filtro = new FiltroAnimal();
        filtro.setEspecie("Cachorro");
        filtro.setPorte("Grande");
        filtro.setVacinado(true);
        filtro.setCastrado(true);
        
        // Act & Assert
        assertFalse(filtro.corresponde(cachorroPequenoFilhote)); // Pequeno
        assertTrue(filtro.corresponde(cachorroGrandeAdulto)); // Atende todos
        assertFalse(filtro.corresponde(gatoPequenoFilhote)); // É gato
        assertEquals(4, filtro.contarFiltrosAtivos());
    }

    @Test
    @DisplayName("Filtro vazio deve corresponder a todos os animais")
    void filtroVazioDeveCorresponderTodos() {
        // Arrange
        FiltroAnimal filtro = new FiltroAnimal();
        
        // Act & Assert
        assertTrue(filtro.corresponde(cachorroPequenoFilhote));
        assertTrue(filtro.corresponde(cachorroGrandeAdulto));
        assertTrue(filtro.corresponde(gatoPequenoFilhote));
    }

    @Test
    @DisplayName("Deve contar filtros ativos corretamente")
    void deveContarFiltrosAtivos() {
        // Arrange
        FiltroAnimal filtro = new FiltroAnimal();
        
        // Act & Assert
        assertEquals(0, filtro.contarFiltrosAtivos());
        
        filtro.setEspecie("Cachorro");
        assertEquals(1, filtro.contarFiltrosAtivos());
        
        filtro.setPorte("Grande");
        assertEquals(2, filtro.contarFiltrosAtivos());
        
        filtro.setFaixaIdade(0, 12);
        assertEquals(3, filtro.contarFiltrosAtivos());
        
        filtro.setVacinado(true);
        assertEquals(4, filtro.contarFiltrosAtivos());
        
        filtro.setCastrado(true);
        assertEquals(5, filtro.contarFiltrosAtivos());
        
        filtro.setStatus("Disponível");
        assertEquals(6, filtro.contarFiltrosAtivos());
    }

    @Test
    @DisplayName("Deve filtrar cachorro grande vacinado e castrado")
    void deveFiltrarCachorroGrandeVacinadoCastrado() {
        // Arrange
        FiltroAnimal filtro = new FiltroAnimal();
        filtro.setEspecie("Cachorro");
        filtro.setPorte("Grande");
        filtro.setVacinado(true);
        filtro.setCastrado(true);
        
        // Act & Assert
        assertFalse(filtro.corresponde(cachorroPequenoFilhote));
        assertTrue(filtro.corresponde(cachorroGrandeAdulto));
        assertFalse(filtro.corresponde(gatoPequenoFilhote));
    }

    @Test
    @DisplayName("Deve filtrar filhotes de qualquer espécie")
    void deveFiltrarFilhotes() {
        // Arrange
        FiltroAnimal filtro = new FiltroAnimal();
        filtro.setFaixaIdade(0, 12); // Até 1 ano
        
        // Act & Assert
        assertTrue(filtro.corresponde(cachorroPequenoFilhote)); // 6 meses
        assertFalse(filtro.corresponde(cachorroGrandeAdulto)); // 36 meses
        assertTrue(filtro.corresponde(gatoPequenoFilhote)); // 4 meses
    }

    @Test
    @DisplayName("Deve permitir limpar filtros definindo valores nulos")
    void devePermitirLimparFiltros() {
        // Arrange
        FiltroAnimal filtro = new FiltroAnimal();
        filtro.setEspecie("Cachorro");
        filtro.setPorte("Grande");
        
        // Act
        filtro.setEspecie(null);
        filtro.setPorte(null);
        
        // Assert
        assertTrue(filtro.corresponde(cachorroPequenoFilhote));
        assertTrue(filtro.corresponde(cachorroGrandeAdulto));
        assertTrue(filtro.corresponde(gatoPequenoFilhote));
        assertEquals(0, filtro.contarFiltrosAtivos());
    }

    @Test
    @DisplayName("Deve filtrar animais não vacinados")
    void deveFiltrarAnimaisNaoVacinados() {
        // Arrange
        FiltroAnimal filtro = new FiltroAnimal();
        filtro.setVacinado(false);
        
        // Act & Assert
        assertTrue(filtro.corresponde(cachorroPequenoFilhote));
        assertFalse(filtro.corresponde(cachorroGrandeAdulto));
        assertFalse(filtro.corresponde(gatoPequenoFilhote));
    }

    @Test
    @DisplayName("Deve filtrar combinação complexa: gatos pequenos vacinados")
    void deveFiltrarCombinacaoComplexa() {
        // Arrange
        FiltroAnimal filtro = new FiltroAnimal();
        filtro.setEspecie("Gato");
        filtro.setPorte("Pequeno");
        filtro.setVacinado(true);
        
        // Act & Assert
        assertFalse(filtro.corresponde(cachorroPequenoFilhote));
        assertFalse(filtro.corresponde(cachorroGrandeAdulto));
        assertTrue(filtro.corresponde(gatoPequenoFilhote));
    }
}