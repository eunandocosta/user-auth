API de Autenticação de Usuário

Esta API fornece um conjunto de endpoints para a autenticação e registro de usuários, bem como a recuperação de informações do usuário autenticado. Utiliza Spring Boot, Spring Security para autenticação e JWT (JSON Web Tokens) para gerenciamento de sessões.

Tecnologias Utilizadas

Spring Boot para a criação da API.
Spring Security para a autenticação e autorização.
JWT (JSON Web Tokens) para a gestão de sessões.
BCrypt para a criptografia de senhas.
JPA (Java Persistence API) para o mapeamento objeto-relacional.
Endpoints Disponíveis
POST /auth/login
Autentica um usuário utilizando seu e-mail e senha. Retorna um token JWT em caso de sucesso.

Body:

email: E-mail do usuário.
password: Senha do usuário.
POST /auth/register
Registra um novo usuário no sistema.

Body:

name: Nome do usuário.
email: E-mail do usuário.
password: Senha do usuário.
dateOfBirth: Data de nascimento do usuário (formato ISO_LOCAL_DATE).
image: Imagem do perfil do usuário (byte array).
GET /auth/getuser
Recupera as informações do usuário autenticado baseado no token JWT fornecido.

Parameters:

email: E-mail do usuário a ser recuperado.
Segurança
Esta API utiliza Spring Security para garantir que apenas usuários autenticados possam acessar endpoints específicos. As senhas dos usuários são criptografadas usando BCrypt antes de serem armazenadas no banco de dados.

Configuração e Execução

Para executar esta API, é necessário ter o JDK instalado (versão 11 ou superior) e configurar adequadamente as seguintes propriedades no arquivo application.properties:

spring.datasource.url: URL de conexão ao banco de dados.
spring.datasource.username: Nome de usuário para o banco de dados.
spring.datasource.password: Senha para o banco de dados.
api.security.token.secret: Chave secreta usada para assinar os tokens JWT.
Após configurar as propriedades, o projeto pode ser iniciado através do comando:

./mvnw spring-boot:run

Considerações Finais

Este projeto exemplifica uma implementação básica de um sistema de autenticação utilizando Spring Boot e JWT. É recomendado adicionar validações adicionais e considerar o uso de HTTPS para a produção para aumentar a segurança da aplicação.
