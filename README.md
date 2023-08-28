# Teste Backend

API RESTful que possibilita a leitura da lista de indicados e vencedores
da categoria Pior Filme do Golden Raspberry Awards.

## Pré-requisitos

- Java JDK 17
- Maven 3.9.3
- Git
- Postman (Opicional)

## Configuração

1. Clone este repositório para a sua máquina local usando o comando: `git clone https://github.com/lsilverio/api-leitura-filmes.git`

2. Navegue até o diretório do projeto, ex: `cd api-leitura-filmes`

3. Execute a aplicação usando o Maven: `mvn spring-boot:run`

A aplicação será iniciada e poderá ser acessada em: [localhost](http://localhost:8080/actuator/health)

## Uso

1. Certifique-se de que a aplicação esteja em execução.

2. Acesse a página do [Swagger](http://localhost:8080/swagger-ui.html), onde você encontrará a documentação detalhada dos endpoints.

3. Experimente os endpoints disponíveis para obter os resultados desejados.

4. É possível também utilizar o Postman para efeturar as requisições, conforme abaixo: 

O endpoint disponível na API é:

- Método: GET
- URL: http://localhost:8080/movies/prizes
- Descrição: Retorna o produtor com maior intervalo entre dois prêmios consecutivos e o produtor que obteve dois prêmios mais rápido.

![Exemplo de Requisição no Postman](https://github.com/lsilverio/api-leitura-filmes/blob/master/src/main/resources/static/img/teste_postman.png)

## Para acessar o banco de dados

1. Após a inicialização da aplicação, acesse a url: http://localhost:8080/h2-console
2. Para se conectar não é necessário a senha

![Exemplo conexão h2-console](https://github.com/lsilverio/api-leitura-filmes/blob/master/src/main/resources/static/img/connect_h2_console.png)

## Testes de Integração

Para executar os testes de integração, execute o seguinte comando: `mvn test`

## Configuração dos Dados

Caso você queira alterar os dados do arquivo CSV utilizado pela aplicação, siga as instruções abaixo:

1. Localize o arquivo CSV no diretório `\src\main\resources\movie`.
2. Mantenha o cabeçalho e a estrutura do arquivo CSV com o seguinte formato:

    
    year;title;studios;producers;winner

Certifique-se de manter o separador `;` e as colunas `year`, `title`, `studios`, `producers` e `winner` na ordem indicada.

Após fazer as alterações desejadas, salve o arquivo. Ao iniciar a aplicação, os novos dados do arquivo CSV serão carregados automaticamente.

## Tecnologias Utilizadas

- Spring Boot
- Spring Boot Actuator
- Spring Data JPA
- H2 Database
- Springdoc OpenAPI
- Lombok
- Open CSV

## Autor

[Lucas Alves Silvério](https://www.linkedin.com/in/lucas-silverio/)
