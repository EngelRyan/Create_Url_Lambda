# URL Shortener - AWS Lambda (Java)
Este é um projeto de encurtador de URLs desenvolvido como parte de um curso da Rocketseat. O sistema foi criado utilizando o serviço AWS Lambda para funções serverless e Java como linguagem de programação. O objetivo é criar uma função que recebe uma URL original e a transforma em um código curto (UUID), proporcionando uma solução simples e escalável para encurtamento de URLs.

## Tecnologias Utilizadas
AWS Lambda - Funções serverless para processamento de URLs

Java - Linguagem de programação utilizada para implementar a função Lambda

AWS S3 - Armazenamento de dados (futuro estágio do projeto)

UUID - Geração de códigos únicos para as URLs encurtadas

## Funcionalidades Implementadas
Até o momento, a aplicação possui a funcionalidade básica de criar um código curto para uma URL original, como mostrado abaixo:

Recepção da URL original: O sistema recebe uma URL original via JSON.

Geração de código curto: A partir da URL original, é gerado um código único (UUID) de 8 caracteres.

Resposta com código curto: A função Lambda retorna esse código como resposta.

### Exemplo de entrada
A entrada para a função deve ser um JSON com a seguinte estrutura:

{

  "originalUrl": "https://www.exemplo.com",
  
  "expirationTime": "3600"
  
}

originalUrl: A URL que você deseja encurtar.

expirationTime: O tempo em segundos para a URL expirada (ainda não implementado, mas previsto para os próximos passos).

### Exemplo de saída

A função Lambda retorna um JSON com o código curto gerado:

{

  "code": "abcd1234"
  
}

## Como rodar o projeto

### Pré-requisitos

Java 11 ou superior instalado

AWS CLI configurado com suas credenciais de acesso

Maven para compilar o projeto

Passos para rodar

### Clone o repositório:

```
  git clone https://github.com/engelryan/URL-Shortener.git
```

### Compile o projeto:
```
mvn clean install
```

Implemente a função Lambda no AWS (consulte a documentação da AWS para mais detalhes sobre como implantar funções Lambda usando Java).

Teste a função Lambda diretamente na AWS ou usando uma ferramenta como Postman para enviar requisições HTTP.

## Testando localmente

Para testar a função localmente, você pode usar o AWS SAM (Serverless Application Model) ou o LocalStack. Ambos simulam o ambiente da AWS na sua máquina local.
