package com.buscapet.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    // ========================================================================
    // ANÁLISE DE VALOR LIMITE - Tamanho da Senha
    // Limites identificados:
    // - Limite inferior: 8 caracteres (mínimo válido)
    // Valores de teste: 7, 8, 9
    // ========================================================================
    
    @Nested
    @DisplayName("Testes de Análise de Valor Limite - Tamanho da Senha")
    class TesteSenhaValorLimite {
        
        @Test
        @DisplayName("AVL: Senha com 7 caracteres (abaixo do limite - inválido)")
        void senhaAbaixoLimiteMinimo() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                new Usuario("joao@email.com", "1234567", "Adotante");
            });
            assertEquals("Senha deve ter no mínimo 8 caracteres", exception.getMessage());
        }
        
        @Test
        @DisplayName("AVL: Senha com 8 caracteres (no limite mínimo - válido)")
        void senhaNoLimiteMinimo() {
            Usuario usuario = new Usuario("joao@email.com", "12345678", "Adotante");
            assertNotNull(usuario);
            assertEquals("Adotante", usuario.getTipoUsuario());
        }
        
        @Test
        @DisplayName("AVL: Senha com 9 caracteres (acima do limite - válido)")
        void senhaAcimaLimiteMinimo() {
            Usuario usuario = new Usuario("joao@email.com", "123456789", "Adotante");
            assertNotNull(usuario);
            assertEquals("Adotante", usuario.getTipoUsuario());
        }
        
        @Test
        @DisplayName("AVL: Senha com 20 caracteres (valor médio válido)")
        void senhaValorMedio() {
            Usuario usuario = new Usuario("joao@email.com", "12345678901234567890", "Adotante");
            assertNotNull(usuario);
        }
        
        @Test
        @DisplayName("AVL: Senha com 1 caractere (muito abaixo do limite - inválido)")
        void senhaMuitoCurta() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                new Usuario("joao@email.com", "1", "Adotante");
            });
            assertEquals("Senha deve ter no mínimo 8 caracteres", exception.getMessage());
        }
        
        @Test
        @DisplayName("AVL: Senha vazia (inválido)")
        void senhaVazia() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                new Usuario("joao@email.com", "", "Adotante");
            });
            assertEquals("Senha deve ter no mínimo 8 caracteres", exception.getMessage());
        }
    }

    // ========================================================================
    // PARTIÇÃO DE EQUIVALÊNCIA - Formato de Email
    // Partições:
    // 1. Email válido (contém @)
    // 2. Email inválido (não contém @)
    // 3. Email nulo
    // 4. Email vazio
    // ========================================================================
    
    @Nested
    @DisplayName("Testes de Partição de Equivalência - Email")
    class TesteEmailParticaoEquivalencia {
        
        @Test
        @DisplayName("PE: Email válido com @ (partição válida)")
        void emailValido() {
            Usuario usuario = new Usuario("joao@email.com", "senha12345", "Adotante");
            assertEquals("joao@email.com", usuario.getEmail());
        }
        
        @Test
        @DisplayName("PE: Email válido normalizado para minúsculas")
        void emailNormalizadoMinusculas() {
            Usuario usuario = new Usuario("JOAO@EMAIL.COM", "senha12345", "Adotante");
            assertEquals("joao@email.com", usuario.getEmail());
        }
        
        @Test
        @DisplayName("PE: Email válido com espaços laterais (trim)")
        void emailComEspacos() {
            Usuario usuario = new Usuario("  joao@email.com  ", "senha12345", "Adotante");
            assertEquals("joao@email.com", usuario.getEmail());
        }
        
        @Test
        @DisplayName("PE: Email sem @ (partição inválida)")
        void emailSemArroba() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                new Usuario("joaoemail.com", "senha12345", "Adotante");
            });
            assertEquals("E-mail inválido", exception.getMessage());
        }
        
        @Test
        @DisplayName("PE: Email nulo (partição inválida)")
        void emailNulo() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                new Usuario(null, "senha12345", "Adotante");
            });
            assertEquals("E-mail inválido", exception.getMessage());
        }
        
        @Test
        @DisplayName("PE: Email vazio (partição inválida)")
        void emailVazio() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                new Usuario("", "senha12345", "Adotante");
            });
            assertEquals("E-mail inválido", exception.getMessage());
        }
        
        @Test
        @DisplayName("PE: Email com múltiplos @ (partição válida - formato aceito)")
        void emailComMultiplosArrobas() {
            Usuario usuario = new Usuario("joao@teste@email.com", "senha12345", "Adotante");
            assertEquals("joao@teste@email.com", usuario.getEmail());
        }
    }

    // ========================================================================
    // PARTIÇÃO DE EQUIVALÊNCIA - Tipo de Usuário
    // Partições:
    // 1. "Adotante" (válida)
    // 2. "ONG" (válida)
    // 3. "Admin" (válida)
    // 4. Qualquer outro valor (inválida)
    // ========================================================================
    
    @Nested
    @DisplayName("Testes de Partição de Equivalência - Tipo de Usuário")
    class TesteTipoUsuarioParticaoEquivalencia {
        
        @Test
        @DisplayName("PE: Tipo Adotante (partição válida)")
        void tipoAdotante() {
            Usuario usuario = new Usuario("joao@email.com", "senha12345", "Adotante");
            assertEquals("Adotante", usuario.getTipoUsuario());
            assertFalse(usuario.isEmailVerificado());
            assertFalse(usuario.isPerfilCompleto());
        }
        
        @Test
        @DisplayName("PE: Tipo ONG (partição válida)")
        void tipoOng() {
            Usuario usuario = new Usuario("ong@email.com", "senha12345", "ONG");
            assertEquals("ONG", usuario.getTipoUsuario());
            assertEquals("Pendente", usuario.getStatusOng());
        }
        
        @Test
        @DisplayName("PE: Tipo Admin (partição válida)")
        void tipoAdmin() {
            Usuario usuario = new Usuario("admin@email.com", "senha12345", "Admin");
            assertEquals("Admin", usuario.getTipoUsuario());
        }
        
        @Test
        @DisplayName("PE: Tipo inválido - Visitante (partição inválida)")
        void tipoVisitante() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                new Usuario("user@email.com", "senha12345", "Visitante");
            });
            assertEquals("Tipo de usuário inválido", exception.getMessage());
        }
        
        @Test
        @DisplayName("PE: Tipo inválido - string qualquer (partição inválida)")
        void tipoInvalidoQualquer() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                new Usuario("user@email.com", "senha12345", "Usuario");
            });
            assertEquals("Tipo de usuário inválido", exception.getMessage());
        }
        
        @Test
        @DisplayName("PE: Tipo nulo (partição inválida)")
        void tipoNulo() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                new Usuario("user@email.com", "senha12345", null);
            });
            assertEquals("Tipo de usuário inválido", exception.getMessage());
        }
    }

    // ========================================================================
    // PARTIÇÃO DE EQUIVALÊNCIA - Verificação de Email
    // Partições:
    // 1. Email não verificado → isEmailVerificado() = false
    // 2. Email verificado → isEmailVerificado() = true
    // ========================================================================
    
    @Nested
    @DisplayName("Testes de Partição de Equivalência - Verificação de Email")
    class TesteVerificacaoEmailParticaoEquivalencia {
        
        @Test
        @DisplayName("PE: Email não verificado por padrão (partição inicial)")
        void emailNaoVerificadoPadrao() {
            Usuario usuario = new Usuario("joao@email.com", "senha12345", "Adotante");
            assertFalse(usuario.isEmailVerificado());
        }
        
        @Test
        @DisplayName("PE: Email após verificação (partição válida)")
        void emailAposVerificacao() {
            Usuario usuario = new Usuario("joao@email.com", "senha12345", "Adotante");
            usuario.verificarEmail();
            assertTrue(usuario.isEmailVerificado());
        }
    }

    // ========================================================================
    // PARTIÇÃO DE EQUIVALÊNCIA - Completar Perfil
    // Partições:
    // 1. Perfil completo com email verificado → sucesso
    // 2. Perfil sem email verificado → exceção
    // ========================================================================
    
    @Nested
    @DisplayName("Testes de Partição de Equivalência - Completar Perfil")
    class TesteCompletarPerfilParticaoEquivalencia {
        
        @Test
        @DisplayName("PE: Completar perfil com email verificado (partição válida)")
        void completarPerfilComEmailVerificado() {
            Usuario usuario = new Usuario("joao@email.com", "senha12345", "Adotante");
            usuario.verificarEmail();
            usuario.completarPerfil();
            
            assertTrue(usuario.isPerfilCompleto());
        }
        
        @Test
        @DisplayName("PE: Tentar completar perfil sem email verificado (partição inválida)")
        void completarPerfilSemEmailVerificado() {
            Usuario usuario = new Usuario("joao@email.com", "senha12345", "Adotante");
            
            Exception exception = assertThrows(IllegalStateException.class, () -> {
                usuario.completarPerfil();
            });
            assertEquals("E-mail deve ser verificado antes de completar o perfil", 
                         exception.getMessage());
        }
    }

    // ========================================================================
    // PARTIÇÃO DE EQUIVALÊNCIA - Autenticação
    // Partições:
    // 1. Email e senha corretos com email verificado → true
    // 2. Senha incorreta → false
    // 3. Email incorreto → false
    // 4. Email não verificado → exceção
    // ========================================================================
    
    @Nested
    @DisplayName("Testes de Partição de Equivalência - Autenticação")
    class TesteAutenticacaoParticaoEquivalencia {
        
        @Test
        @DisplayName("PE: Autenticar com credenciais corretas (partição válida)")
        void autenticarCredenciaisCorretas() {
            Usuario usuario = new Usuario("joao@email.com", "senha12345", "Adotante");
            usuario.verificarEmail();
            
            boolean autenticado = usuario.autenticar("joao@email.com", "senha12345");
            assertTrue(autenticado);
        }
        
        @Test
        @DisplayName("PE: Autenticar com email em maiúsculas (normalização)")
        void autenticarEmailMaiusculas() {
            Usuario usuario = new Usuario("joao@email.com", "senha12345", "Adotante");
            usuario.verificarEmail();
            
            boolean autenticado = usuario.autenticar("JOAO@EMAIL.COM", "senha12345");
            assertTrue(autenticado);
        }
        
        @Test
        @DisplayName("PE: Autenticar com senha incorreta (partição inválida)")
        void autenticarSenhaIncorreta() {
            Usuario usuario = new Usuario("joao@email.com", "senha12345", "Adotante");
            usuario.verificarEmail();
            
            boolean autenticado = usuario.autenticar("joao@email.com", "senhaerrada");
            assertFalse(autenticado);
        }
        
        @Test
        @DisplayName("PE: Autenticar com email incorreto (partição inválida)")
        void autenticarEmailIncorreto() {
            Usuario usuario = new Usuario("joao@email.com", "senha12345", "Adotante");
            usuario.verificarEmail();
            
            boolean autenticado = usuario.autenticar("outro@email.com", "senha12345");
            assertFalse(autenticado);
        }
        
        @Test
        @DisplayName("PE: Tentar autenticar sem email verificado (partição inválida)")
        void autenticarSemEmailVerificado() {
            Usuario usuario = new Usuario("joao@email.com", "senha12345", "Adotante");
            
            Exception exception = assertThrows(IllegalStateException.class, () -> {
                usuario.autenticar("joao@email.com", "senha12345");
            });
            assertEquals("E-mail não verificado", exception.getMessage());
        }
    }

    // ========================================================================
    // PARTIÇÃO DE EQUIVALÊNCIA - Verificação de ONG
    // Partições para verificarOng():
    // 1. Usuário tipo ONG → sucesso
    // 2. Usuário tipo Adotante → exceção
    // 3. Usuário tipo Admin → exceção
    // ========================================================================
    
    @Nested
    @DisplayName("Testes de Partição de Equivalência - Verificação de ONG")
    class TesteVerificacaoOngParticaoEquivalencia {
        
        @Test
        @DisplayName("PE: Verificar ONG válida (partição válida)")
        void verificarOngValida() {
            Usuario usuario = new Usuario("ong@email.com", "senha12345", "ONG");
            assertEquals("Pendente", usuario.getStatusOng());
            
            usuario.verificarOng();
            assertEquals("Verificada", usuario.getStatusOng());
        }
        
        @Test
        @DisplayName("PE: Tentar verificar Adotante como ONG (partição inválida)")
        void tentarVerificarAdotante() {
            Usuario usuario = new Usuario("joao@email.com", "senha12345", "Adotante");
            
            Exception exception = assertThrows(IllegalStateException.class, () -> {
                usuario.verificarOng();
            });
            assertEquals("Usuário não é uma ONG", exception.getMessage());
        }
        
        @Test
        @DisplayName("PE: Tentar verificar Admin como ONG (partição inválida)")
        void tentarVerificarAdmin() {
            Usuario usuario = new Usuario("admin@email.com", "senha12345", "Admin");
            
            Exception exception = assertThrows(IllegalStateException.class, () -> {
                usuario.verificarOng();
            });
            assertEquals("Usuário não é uma ONG", exception.getMessage());
        }
    }

    // ========================================================================
    // PARTIÇÃO DE EQUIVALÊNCIA - Recusa de ONG
    // Partições:
    // 1. Recusar ONG com motivo válido → sucesso
    // 2. Recusar ONG sem motivo → exceção
    // 3. Recusar não-ONG → exceção
    // ========================================================================
    
    @Nested
    @DisplayName("Testes de Partição de Equivalência - Recusa de ONG")
    class TesteRecusaOngParticaoEquivalencia {
        
        @Test
        @DisplayName("PE: Recusar ONG com motivo válido (partição válida)")
        void recusarOngComMotivo() {
            Usuario usuario = new Usuario("ong@email.com", "senha12345", "ONG");
            String motivo = "Documentação incompleta";
            
            usuario.recusarOng(motivo);
            assertEquals("Recusada", usuario.getStatusOng());
        }
        
        @Test
        @DisplayName("PE: Recusar ONG sem motivo (partição inválida)")
        void recusarOngSemMotivo() {
            Usuario usuario = new Usuario("ong@email.com", "senha12345", "ONG");
            
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                usuario.recusarOng("");
            });
            assertEquals("Motivo da recusa é obrigatório", exception.getMessage());
        }
        
        @Test
        @DisplayName("PE: Recusar ONG com motivo nulo (partição inválida)")
        void recusarOngMotivoNulo() {
            Usuario usuario = new Usuario("ong@email.com", "senha12345", "ONG");
            
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                usuario.recusarOng(null);
            });
            assertEquals("Motivo da recusa é obrigatório", exception.getMessage());
        }
        
        @Test
        @DisplayName("PE: Tentar recusar Adotante (partição inválida)")
        void tentarRecusarAdotante() {
            Usuario usuario = new Usuario("joao@email.com", "senha12345", "Adotante");
            
            Exception exception = assertThrows(IllegalStateException.class, () -> {
                usuario.recusarOng("Qualquer motivo");
            });
            assertEquals("Usuário não é uma ONG", exception.getMessage());
        }
    }

    // ========================================================================
    // PARTIÇÃO DE EQUIVALÊNCIA - Permissões para Publicar Animais
    // Partições para podePublicarAnimais():
    // 1. ONG verificada com email verificado → true
    // 2. ONG pendente com email verificado → false
    // 3. ONG verificada sem email verificado → false
    // 4. ONG recusada → false
    // 5. Adotante → false
    // 6. Admin → false
    // ========================================================================
    
    @Nested
    @DisplayName("Testes de Partição de Equivalência - Permissão para Publicar Animais")
    class TestePublicarAnimaisParticaoEquivalencia {
        
        @Test
        @DisplayName("PE: ONG verificada e email verificado - pode publicar (partição válida)")
        void ongVerificadaPodePublicar() {
            Usuario usuario = new Usuario("ong@email.com", "senha12345", "ONG");
            usuario.verificarEmail();
            usuario.verificarOng();
            
            assertTrue(usuario.podePublicarAnimais());
        }
        
        @Test
        @DisplayName("PE: ONG pendente - não pode publicar (partição inválida)")
        void ongPendenteNaoPodePublicar() {
            Usuario usuario = new Usuario("ong@email.com", "senha12345", "ONG");
            usuario.verificarEmail();
            
            assertFalse(usuario.podePublicarAnimais());
            assertEquals("Pendente", usuario.getStatusOng());
        }
        
        @Test
        @DisplayName("PE: ONG verificada sem email - não pode publicar (partição inválida)")
        void ongVerificadaSemEmailNaoPodePublicar() {
            Usuario usuario = new Usuario("ong@email.com", "senha12345", "ONG");
            usuario.verificarOng();
            
            assertFalse(usuario.podePublicarAnimais());
            assertFalse(usuario.isEmailVerificado());
        }
        
        @Test
        @DisplayName("PE: ONG recusada - não pode publicar (partição inválida)")
        void ongRecusadaNaoPodePublicar() {
            Usuario usuario = new Usuario("ong@email.com", "senha12345", "ONG");
            usuario.verificarEmail();
            usuario.recusarOng("Documentação inválida");
            
            assertFalse(usuario.podePublicarAnimais());
            assertEquals("Recusada", usuario.getStatusOng());
        }
        
        @Test
        @DisplayName("PE: Adotante - não pode publicar (partição inválida)")
        void adotanteNaoPodePublicar() {
            Usuario usuario = new Usuario("joao@email.com", "senha12345", "Adotante");
            usuario.verificarEmail();
            
            assertFalse(usuario.podePublicarAnimais());
        }
        
        @Test
        @DisplayName("PE: Admin - não pode publicar (partição inválida)")
        void adminNaoPodePublicar() {
            Usuario usuario = new Usuario("admin@email.com", "senha12345", "Admin");
            usuario.verificarEmail();
            
            assertFalse(usuario.podePublicarAnimais());
        }
    }

    // ========================================================================
    // PARTIÇÃO DE EQUIVALÊNCIA - Permissões para Candidatar
    // Partições para podeCandidatar():
    // 1. Adotante com email verificado e perfil completo → true
    // 2. Adotante sem email verificado → false
    // 3. Adotante sem perfil completo → false
    // 4. Adotante sem ambos → false
    // 5. ONG → false
    // 6. Admin → false
    // ========================================================================
    
    @Nested
    @DisplayName("Testes de Partição de Equivalência - Permissão para Candidatar")
    class TesteCandidatarParticaoEquivalencia {
        
        @Test
        @DisplayName("PE: Adotante completo - pode candidatar (partição válida)")
        void adotanteCompletoPodeCandidatar() {
            Usuario usuario = new Usuario("joao@email.com", "senha12345", "Adotante");
            usuario.verificarEmail();
            usuario.completarPerfil();
            
            assertTrue(usuario.podeCandidatar());
            assertTrue(usuario.isEmailVerificado());
            assertTrue(usuario.isPerfilCompleto());
        }
        
        @Test
        @DisplayName("PE: Adotante sem email - não pode candidatar (partição inválida)")
        void adotanteSemEmailNaoPodeCandidatar() {
            Usuario usuario = new Usuario("joao@email.com", "senha12345", "Adotante");
            
            assertFalse(usuario.podeCandidatar());
            assertFalse(usuario.isEmailVerificado());
        }
        
        @Test
        @DisplayName("PE: Adotante sem perfil completo - não pode candidatar (partição inválida)")
        void adotanteSemPerfilNaoPodeCandidatar() {
            Usuario usuario = new Usuario("joao@email.com", "senha12345", "Adotante");
            usuario.verificarEmail();
            
            assertFalse(usuario.podeCandidatar());
            assertTrue(usuario.isEmailVerificado());
            assertFalse(usuario.isPerfilCompleto());
        }
        
        @Test
        @DisplayName("PE: Adotante sem email e perfil - não pode candidatar (partição inválida)")
        void adotanteSemNadaNaoPodeCandidatar() {
            Usuario usuario = new Usuario("joao@email.com", "senha12345", "Adotante");
            
            assertFalse(usuario.podeCandidatar());
            assertFalse(usuario.isEmailVerificado());
            assertFalse(usuario.isPerfilCompleto());
        }
        
        @Test
        @DisplayName("PE: ONG - não pode candidatar (partição inválida)")
        void ongNaoPodeCandidatar() {
            Usuario usuario = new Usuario("ong@email.com", "senha12345", "ONG");
            usuario.verificarEmail();
            
            assertFalse(usuario.podeCandidatar());
        }
        
        @Test
        @DisplayName("PE: Admin - não pode candidatar (partição inválida)")
        void adminNaoPodeCandidatar() {
            Usuario usuario = new Usuario("admin@email.com", "senha12345", "Admin");
            usuario.verificarEmail();
            
            assertFalse(usuario.podeCandidatar());
        }
    }
}