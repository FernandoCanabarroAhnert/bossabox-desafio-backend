# Desafio Backend: VUTTR (Very Useful Tools to Remember) 🛠️

![Java](https://img.shields.io/badge/java-8338EC.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-00A878?style=for-the-badge&logo=spring-boot&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-FF6F61?style=for-the-badge&logo=mongodb&logoColor=white)
![Mongo Express](https://img.shields.io/badge/Mongo%20Express-285C35?style=for-the-badge&logo=mongodb&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-0077B6?style=for-the-badge&logo=docker&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-FB5607?style=for-the-badge&logo=springsecurity&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)

## O que é o desafio? 🤔

O desafio, encontrado no Github da BossaBox, é um simples repositório para gerenciar ferramentas com seus respectivos nomes, links, descrições e tags.

Como um diferencial para este desafio, eu utilizei JWT para a Autenticação,o Banco de Dados NoSQL MongoDB, para o armazenamento de dados, a interface gráfica Mongo Express, e também implementei o TDD - Test Driven Development, junto dos Testes Unitários e de Integração.

O desafio pode ser encontrado aqui: <https://bossabox.notion.site/Back-end-0b2c45f1a00e4a849eefe3b1d57f23c6>

<p align="left" width="100%">
    <img width="25%" src="https://github.com/user-attachments/assets/30bb90cc-4520-41e0-955e-4b2908594096"> 
</p>

## Requisitos da Aplicação ✅

Esses foram os requisitos definidos no enunciado original:

## O que será avaliado

Queremos avaliar sua capacidade de desenvolver e documentar um back-end para uma aplicação. Serão avaliados:

- Código bem escrito e limpo;
- Quais ferramentas foram usadas, como e por quê, além do seu conhecimento das mesmas;
- Seu conhecimento em banco de dados, requisições HTTP, APIs REST, etc;
- Sua capacidade de se comprometer com o que foi fornecido;
- Sua capacidade de documentação da sua parte da aplicação.

## O mínimo necessário

- Uma aplicação contendo uma API real simples, sem autenticação, que atenda os requisitos descritos abaixo, fazendo requisições à um banco de dados para persistência;
- README.md contendo informações básicas do projeto e como executá-lo;
- [API Blueprint](https://apiblueprint.org/) ou [Swagger](https://swagger.io/docs/specification/basic-structure/) da aplicação.

# Requisitos

1: Deve haver uma rota para listar todas as ferramentas cadastradas

2: Deve ser possível filtrar ferramentas utilizando uma busca por tag

3: Deve haver uma rota para cadastrar uma nova ferramenta

4: O usuário deve poder remover uma ferramenta por ID

## Bônus

Os seguintes itens não são obrigatórios, mas darão mais valor ao seu trabalho (os em negrito são mais significativos para nós)

- Uso de ferramentas externas que facilitem o seu trabalho;
- Cuidados especiais com otimização, padrões, entre outros;
- **Testes**;
- **Conteinerização da aplicação**;
- **Autenticação e autorização** (**OAuth, JWT**);
- Sugestões sobre o challenge embasadas em alguma argumentação.

### Serviço RESTful 🚀

* Desenvolvimento de um serviço RESTful para toda a aplicação.

## Tecnologias 💻
 
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring MVC](https://docs.spring.io/spring-framework/reference/web/webmvc.html)
- [MongoDB](https://www.mongodb.com/)
- [Mongo Express](https://alphasec.io/mongo-express-mongodb-management-made-easy/)
- [Spring Security](https://spring.io/projects/spring-security)
- [JWT](https://jwt.io/)
- [SpringDoc OpenAPI 3](https://springdoc.org/v2/#spring-webflux-support)
- [Docker](https://www.docker.com/)
- [JUnit5](https://junit.org/junit5/)
- [Mockito](https://site.mockito.org/)
- [MockMvc](https://docs.spring.io/spring-framework/reference/testing/spring-mvc-test-framework.html)
- [Jacoco](https://www.eclemma.org/jacoco/)
- [Bean Validation](https://docs.spring.io/spring-framework/reference/core/validation/beanvalidation.html)
- [HATEOAS](https://spring.io/projects/spring-hateoas)

## Práticas adotadas ✨

- SOLID, DRY, YAGNI, KISS
- API REST
- TDD
- Injeção de Dependências
- Testes Automatizados
- Geração automática do Swagger com a OpenAPI 3
- Autenticação e Autorização com JWT

## Diferenciais 🔥

Alguns diferenciais que não foram solicitados no desafio:

* Utilização de Docker
* TDD-Test Driven Development
* Tratamento de exceções
* Validações com Constraints Customizados
* Testes Unitários e de Integração
* Cobertura de Testes com Jacoco
* Documentação Swagger
* Implementação de HATEOAS

## Como executar 🎉

1.Clonar repositório git:

```text
git clone https://github.com/FernandoCanabarroAhnert/bossabox-desafio-backend.git
```

2.Instalar dependências.

```text
mvn clean install
```

3.Executar a aplicação Spring Boot.

4.Testar endpoints através do Postman ou da url
<http://localhost:8080/swagger-ui/index.html#/>

### Usando Docker 🐳

- Clonar repositório git
- Construir o projeto:
```
./mvnw clean package
```
- Construir a imagem:
```
./mvnw spring-boot:build-image
```
- Executar o container:
```
docker run --name desafio-bossabox-tools -p 8080:8080  -d desafio-bossabox-tools:0.0.1-SNAPSHOT
```
## API Endpoints 📚

Para fazer as requisições HTTP abaixo, foi utilizada a ferramenta [Postman](https://www.postman.com/):
- Collection do Postman completa: [Postman-Collection](https://github.com/user-attachments/files/16826791/BossaBoxTools.json)

- Registrar Usuário
```
$ http POST http://localhost:8080/users/register


{
    "fullName": "Fernando",
    "email": "fernando@gmail.com",
    "password": "12345Az@"
}

```

- Efetuar Login
```
$ http POST http://localhost:8080/users/login

{
    "email": "fernando@gmail.com",
    "password": "12345Az@"
}
```

- Inserir Ferramenta
```
$ http POST http://localhost:8080/tools

{
    "title": "hotel",
    "link": "https://github.com/typicode/hotel",
    "description": "Local app manager. Start apps within your browser, developer tool with local .localhost domain and https out of the box.",
    "tags":[
        "node",
        "organizing", 
        "webapps", 
        "domain", 
        "developer", 
        "https", 
        "proxy"
    ]
}

```

- Consultar Ferramentas
```
$ http POST http://localhost:8080/loans

[
    {
        "_id": 66d392d2604d7b39ca465141,
        "title": "json-server",
        "link": "https://github.com/typicode/json-server",
        "description": "Fake REST API based on a json schema. Useful for mocking and creating APIs for front-end devs to consume in coding challenges.",
        "tags": [
            "api",
            "json",
            "schema",
            "node",
            "github",
            "rest"
        ]
    },
    {
        "_id": 66d392dd604d7b39ca465143,
        "title": "fastify",
        "link": "https://www.fastify.io/",
        "description:" "Extremely fast and simple, low-overhead web framework for NodeJS. Supports HTTP2.",
        "tags": [
            "web",
            "framework",
            "node",
            "http2",
            "https",
            "localhost"
        ]
    }
]

```


