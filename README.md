# Teste Backend

API RESTful que possibilita a leitura da lista de indicados e vencedores
da categoria Pior Filme do Golden Raspberry Awards.

## Pré-requisitos

Antes de começar, certifique-se de ter as seguintes ferramentas instaladas na sua máquina:

- **Java JDK 17**: Você pode baixá-lo [aqui](https://www.oracle.com/br/java/technologies/downloads/#java17).
- **Maven (Opcional)**: Você pode baixá-lo [aqui](https://dlcdn.apache.org/maven/maven-3/3.9.4/binaries/apache-maven-3.9.4-bin.zip), mas é opcional caso queira compilar manualmente o projeto.
- **Docker (Opcional)**: Caso queira rodar a aplicação via Docker, você pode baixá-lo [aqui](https://www.docker.com/get-started).
- **Git (Opcional)**: Caso não queira ter o Git instalado, basta fazer o download do projeto [aqui](https://github.com/lsilverio/api-leitura-filmes/archive/refs/heads/master.zip).
- **Postman (Opcional)**: Para testar as requisições da API, você pode baixá-lo [aqui](https://www.postman.com/downloads/).

## Configuração

1. Abra o terminal ou prompt de comando no seu computador.

2. Baixe o projeto para sua máquina clicando [aqui](https://github.com/lsilverio/api-leitura-filmes/archive/refs/heads/master.zip) ou clone este repositório para a sua máquina local usando o seguinte comando:


    git clone https://github.com/lsilverio/api-leitura-filmes.git

3. Navegue até o diretório do projeto:


    cd api-leitura-filmes

4. Execute a aplicação usando o Maven:


    mvn spring-boot:run

A aplicação será iniciada e você poderá acessá-la em: [localhost](http://localhost:8080/actuator/health)

## Rodando a Aplicação via Docker (Opcional)

Se preferir, você pode rodar a aplicação dentro de um contêiner Docker:

1. Certifique-se de ter o Docker instalado na sua máquina. Se ainda não tiver, você pode baixá-lo [aqui](https://www.docker.com/get-started).

2. Na raiz do projeto (diretório onde está o arquivo `Dockerfile`), abra um terminal ou prompt de comando.

3. Execute o seguinte comando para gerar o JAR da aplicação (caso ainda não tenha gerado):

    
    mvn clean package

4. Depois de gerar o JAR, execute o comando a seguir para construir a imagem Docker:

    
    docker build -t teste-backend-image .

5. Após a conclusão do processo de construção da imagem, você pode iniciar a aplicação dentro de um contêiner Docker com o seguinte comando:


    docker run -p 8080:8080 teste-backend

6. A aplicação estará rodando dentro do contêiner Docker. Você poderá acessá-la em: [localhost](http://localhost:8080/actuator/health)

## Uso

1. Certifique-se de que a aplicação esteja em execução.

2. Acesse a página do [Swagger](http://localhost:8080/swagger-ui.html), onde você encontrará a documentação detalhada dos endpoints.

3. Experimente os endpoints disponíveis para obter os resultados desejados.

4. Se preferir, você também pode utilizar o Postman para testar as requisições. Abaixo está um exemplo de como fazer uma requisição:

- Método: GET
- URL: http://localhost:8080/movies/prizes
- Descrição: Retorna o produtor com maior intervalo entre dois prêmios consecutivos e o produtor que obteve dois prêmios mais rápido.

![Exemplo de Requisição no Postman](https://github.com/lsilverio/api-leitura-filmes/blob/master/src/main/resources/static/img/teste_postman.png)

## Acessando o Banco de Dados

1. Após a inicialização da aplicação, você pode acessar o console do banco de dados H2 em: [H2 Console](http://localhost:8080/h2-console)

2. Para se conectar ao banco de dados, não é necessário inserir uma senha.

![Exemplo conexão h2-console](https://github.com/lsilverio/api-leitura-filmes/blob/master/src/main/resources/static/img/connect_h2_console.png)

## Testes de Integração

Para executar os testes de integração, execute o seguinte comando:


    mvn test


## Configuração dos Dados

Caso você queira alterar os dados do arquivo CSV utilizado pela aplicação, siga as instruções abaixo:

1. Localize o arquivo CSV no diretório `\src\main\resources\movie`.

2. Mantenha o cabeçalho e a estrutura do arquivo CSV com o seguinte formato:


    year;title;studios;producers;winner

Certifique-se de manter o separador `;` e as colunas `year`, `title`, `studios`, `producers` e `winner` na ordem indicada.

3. Após fazer as alterações desejadas, salve o arquivo. Ao iniciar a aplicação, os novos dados do arquivo CSV serão carregados automaticamente.

## Tecnologias Utilizadas

- Spring Boot
- Spring Boot Actuator
- Spring Data JPA
- H2 Database
- Springdoc OpenAPI
- Lombok
- Open CSV
- Docker (opcional, para execução via contêiner)


## Autor

[Lucas Alves Silvério](https://www.linkedin.com/in/lucas-silverio/)

