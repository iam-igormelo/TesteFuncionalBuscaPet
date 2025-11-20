package com.buscapet.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    @DisplayName("Deve criar usuário adotante com dados válidos")
    void deveCriarUsuarioAdotante() {
        // Act
        Usuario usuario = new Usuario("joao@email.com", "senha12345", "Adotante");
        
        // Assert
        assertEquals("joao@email.com", usuario.getEmail());
        assertEquals("Adotante", usuario.getTipoUsuario());
        assertFalse(usuario.isEmailVerificado());
        assertFalse(usuario.isPerfilCompleto());
    }

    @Test
    @DisplayName("Deve criar usuário ONG com status pendente")
    void deveCriarUsuarioOng() {
        // Act
        Usuario usuario = new Usuario("ong@email.com", "senha12345", "ONG");
        
        // Assert
        assertEquals("ONG", usuario.getTipoUsuario());
        assertEquals("Pendente", usuario.getStatusOng());
    }

    @Test
    @DisplayName("Deve criar usuário Admin")
    void deveCriarUsuarioAdmin() {
        // Act
        Usuario usuario = new Usuario("admin@email.com", "senha12345", "Admin");
        
        // Assert
        assertEquals("Admin", usuario.getTipoUsuario());
    }

    @Test
    @DisplayName("Deve normalizar email para minúsculas")
    void deveNormalizarEmail() {
        // Act
        Usuario usuario = new Usuario("JOAO@EMAIL.COM", "senha12345", "Adotante");
        
        // Assert
        assertEquals("joao@email.com", usuario.getEmail());
    }

    @Test
    @DisplayName("Deve lançar exceção quando email é inválido")
    void deveLancarExcecaoQuandoEmailInvalido() {
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Usuario("emailinvalido", "senha12345", "Adotante");
        });
        
        assertEquals("E-mail inválido", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando email é nulo")
    void deveLancarExcecaoQuandoEmailNulo() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Usuario(null, "senha12345", "Adotante");
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando senha tem menos de 8 caracteres")
    void deveLancarExcecaoQuandoSenhaCurta() {
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Usuario("joao@email.com", "1234567", "Adotante");
        });
        
        assertEquals("Senha deve ter no mínimo 8 caracteres", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando tipo de usuário é inválido")
    void deveLancarExcecaoQuandoTipoInvalido() {
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Usuario("joao@email.com", "senha12345", "Visitante");
        });
        
        assertEquals("Tipo de usuário inválido", exception.getMessage());
    }

    @Test
    @DisplayName("Deve verificar email do usuário")
    void deveVerificarEmail() {
        // Arrange
        Usuario usuario = new Usuario("joao@email.com", "senha12345", "Adotante");
        
        // Act
        usuario.verificarEmail();
        
        // Assert
        assertTrue(usuario.isEmailVerificado());
    }

    @Test
    @DisplayName("Deve completar perfil após verificar email")
    void deveCompletarPerfilAposVerificar() {
        // Arrange
        Usuario usuario = new Usuario("joao@email.com", "senha12345", "Adotante");
        usuario.verificarEmail();
        
        // Act
        usuario.completarPerfil();
        
        // Assert
        assertTrue(usuario.isPerfilCompleto());
    }

    @Test
    @DisplayName("Não deve completar perfil sem verificar email")
    void naoDeveCompletarPerfilSemVerificar() {
        // Arrange
        Usuario usuario = new Usuario("joao@email.com", "senha12345", "Adotante");
        
        // Act & Assert
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            usuario.completarPerfil();
        });
        
        assertEquals("E-mail deve ser verificado antes de completar o perfil", 
                     exception.getMessage());
    }

    @Test
    @DisplayName("Deve autenticar usuário com credenciais corretas")
    void deveAutenticarComCredenciaisCorretas() {
        // Arrange
        Usuario usuario = new Usuario("joao@email.com", "senha12345", "Adotante");
        usuario.verificarEmail();
        
        // Act
        boolean autenticado = usuario.autenticar("joao@email.com", "senha12345");
        
        // Assert
        assertTrue(autenticado);
    }

    @Test
    @DisplayName("Não deve autenticar com senha incorreta")
    void naoDeveAutenticarComSenhaIncorreta() {
        // Arrange
        Usuario usuario = new Usuario("joao@email.com", "senha12345", "Adotante");
        usuario.verificarEmail();
        
        // Act
        boolean autenticado = usuario.autenticar("joao@email.com", "senhaerrada");
        
        // Assert
        assertFalse(autenticado);
    }

    @Test
    @DisplayName("Não deve autenticar sem verificar email")
    void naoDeveAutenticarSemVerificarEmail() {
        // Arrange
        Usuario usuario = new Usuario("joao@email.com", "senha12345", "Adotante");
        
        // Act & Assert
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            usuario.autenticar("joao@email.com", "senha12345");
        });
        
        assertEquals("E-mail não verificado", exception.getMessage());
    }

    @Test
    @DisplayName("Deve verificar ONG")
    void deveVerificarOng() {
        // Arrange
        Usuario usuario = new Usuario("ong@email.com", "senha12345", "ONG");
        
        // Act
        usuario.verificarOng();
        
        // Assert
        assertEquals("Verificada", usuario.getStatusOng());
    }

    @Test
    @DisplayName("Não deve verificar usuário que não é ONG")
    void naoDeveVerificarUsuarioQueNaoEOng() {
        // Arrange
        Usuario usuario = new Usuario("joao@email.com", "senha12345", "Adotante");
        
        // Act & Assert
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            usuario.verificarOng();
        });
        
        assertEquals("Usuário não é uma ONG", exception.getMessage());
    }

    @Test
    @DisplayName("Deve recusar ONG com motivo")
    void deveRecusarOngComMotivo() {
        // Arrange
        Usuario usuario = new Usuario("ong@email.com", "senha12345", "ONG");
        String motivo = "Documentação incompleta";
        
        // Act
        usuario.recusarOng(motivo);
        
        // Assert
        assertEquals("Recusada", usuario.getStatusOng());
    }

    @Test
    @DisplayName("Deve lançar exceção ao recusar ONG sem motivo")
    void deveLancarExcecaoAoRecusarOngSemMotivo() {
        // Arrange
        Usuario usuario = new Usuario("ong@email.com", "senha12345", "ONG");
        
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            usuario.recusarOng("");
        });
        
        assertEquals("Motivo da recusa é obrigatório", exception.getMessage());
    }

    @Test
    @DisplayName("ONG verificada pode publicar animais")
    void ongVerificadaPodePublicar() {
        // Arrange
        Usuario usuario = new Usuario("ong@email.com", "senha12345", "ONG");
        usuario.verificarEmail();
        usuario.verificarOng();
        
        // Act & Assert
        assertTrue(usuario.podePublicarAnimais());
    }

    @Test
    @DisplayName("ONG não verificada não pode publicar animais")
    void ongNaoVerificadaNaoPodePublicar() {
        // Arrange
        Usuario usuario = new Usuario("ong@email.com", "senha12345", "ONG");
        usuario.verificarEmail();
        
        // Act & Assert
        assertFalse(usuario.podePublicarAnimais());
    }

    @Test
    @DisplayName("Adotante não pode publicar animais")
    void adotanteNaoPodePublicar() {
        // Arrange
        Usuario usuario = new Usuario("joao@email.com", "senha12345", "Adotante");
        usuario.verificarEmail();
        
        // Act & Assert
        assertFalse(usuario.podePublicarAnimais());
    }

    @Test
    @DisplayName("Adotante com perfil completo pode se candidatar")
    void adotanteComPerfilCompletoPodeCandidatar() {
        // Arrange
        Usuario usuario = new Usuario("joao@email.com", "senha12345", "Adotante");
        usuario.verificarEmail();
        usuario.completarPerfil();
        
        // Act & Assert
        assertTrue(usuario.podeCandidatar());
    }

    @Test
    @DisplayName("Adotante sem perfil completo não pode se candidatar")
    void adotanteSemPerfilCompletoNaoPodeCandidatar() {
        // Arrange
        Usuario usuario = new Usuario("joao@email.com", "senha12345", "Adotante");
        usuario.verificarEmail();
        
        // Act & Assert
        assertFalse(usuario.podeCandidatar());
    }

    @Test
    @DisplayName("ONG não pode se candidatar")
    void ongNaoPodeCandidatar() {
        // Arrange
        Usuario usuario = new Usuario("ong@email.com", "senha12345", "ONG");
        usuario.verificarEmail();
        
        // Act & Assert
        assertFalse(usuario.podeCandidatar());
    }
}