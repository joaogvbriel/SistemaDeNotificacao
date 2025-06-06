# Sistema de Notificação

**Sobre o Projeto:**  
O Sistema de Notificação é uma solução desacoplada e escalável para gerenciamento, envio e monitoramento de notificações via múltiplos canais (e-mail e SMS). Projetado em arquitetura de microsserviços, o projeto separa as responsabilidades entre módulos Producer (responsável por autenticação e enfileiramento das notificações) e Consumer (responsável pelo processamento e envio efetivo). A comunicação entre módulos é realizada via Apache Kafka, garantindo alta disponibilidade, desacoplamento e facilidade de escalar componentes de forma independente. O sistema traz integração pronta com Docker Compose para facilitar o setup e a execução, além de aplicar boas práticas de autenticação (JWT), observabilidade e segurança.

---

Este repositório contém um sistema de notificação desacoplado, dividido em dois módulos principais: **Producer** (Produtor) e **Consumer** (Consumidor). O projeto é preparado para execução via Docker Compose, facilitando a orquestração e integração dos serviços.

## Estrutura do Projeto

- **Producer**: Responsável por receber requisições de notificação (por exemplo, via API REST), autenticar e publicá-las em filas separadas por prioridade.
  - Caminho: `SistemaDeNotificacao - Producer/sdn`
- **Consumer**: Responsável por consumir as notificações das filas, processá-las e realizar o envio via canais como e-mail e SMS.
  - Caminho: `SistemaDeNotificacao - Consumer/sdn - consumer`

---

## Tecnologias Utilizadas

### Backend (Producer & Consumer)
- **Java 17+**
- **Spring Boot**: Framework principal de desenvolvimento.
- **Spring Web**: APIs RESTful (Producer).
- **Spring Security**: JWT para autenticação e autorização de endpoints (Producer).
- **Spring Scheduling**: Agendamento de tarefas (Consumer).
- **Spring for Apache Kafka**: Integração com mensageria Kafka (Producer e Consumer).
- **Maven**: Gerenciamento de dependências e build.
- **Docker & Docker Compose**: Containerização e orquestração.

### Integrações Externas
- **Apache Kafka**: Mensageria para desacoplamento entre Producer e Consumer.
- **Twilio**: Envio de notificações via SMS (Consumer).
- **Serviço de E-mail (AWS SES)**: Envio de mensagens via e-mail (Consumer).

---

## Configuração de Ambiente

Antes de executar o projeto, crie um arquivo `.env` na raiz do repositório com as seguintes variáveis:

```
# Database
DATABASE_URL=jdbc:postgresql://postgres:5432/sdn
DATABASE_USERNAME=
DATABASE_PASSWORD=

# JWT
JWT_SECRET=

# AWS
AWS_SES_ACCESS_KEY=
AWS_SES_SECRET_KEY=
AWS_SES_REGION=
AWS_EMAIL=

# Twilio
TWILIO_ACCOUNT_SID=
TWILIO_AUTH_TOKEN=
TWILIO_PHONE_NUMBER=

# SSL
SSL_KEYSTORE_PASSWORD=
```
> Preencha cada campo conforme suas credenciais e necessidades do ambiente.

---

## Fluxos de Trabalho

### Envio de Notificação

1. **Cliente** envia uma requisição `POST /notifications` com os detalhes da notificação.
2. **API Gateway** autentica e valida a requisição.
3. Mensagem é **enfileirada** no sistema de filas com a prioridade especificada.
4. **Consumers** consomem a mensagem e enviam a notificação através do canal adequado (e-mail ou SMS).
5. **Status da notificação** é atualizado no banco de dados.
6. **Logs** são registrados para monitoramento e auditoria.

### Reenvio Automático

1. Em caso de **falha no envio** da notificação:
2. O Consumer reenvia a mensagem conforme a política de reintento.
3. Após exceder o número máximo de tentativas, a mensagem é movida para a **Dead Letter Queue**.
4. **Administradores** podem revisar e reprocessar mensagens presentes na Dead Letter Queue.

### Consulta de Status

1. O cliente pode realizar uma requisição `GET /notifications/{id}`.
2. A API busca o status da notificação no banco de dados.
3. É retornado o status atual: enviado, pendente ou falhado.

---

## 📋 Regras de Negócio

- **Prioridade de Mensagens**:
  - **Alta:** Alertas críticos (e.g., falhas de segurança) — processados imediatamente.
  - **Média:** Confirmações e atualizações de status — processados após os de alta prioridade.
  - **Baixa:** Promoções e newsletters — processados quando não há mensagens de maior prioridade.

- **Tentativas de Envio**:
  - Máximo de 3 tentativas.
  - Intervalo de 5 minutos entre tentativas.

- **Dead Letter Queue**:
  - Mensagens que falham após todas as tentativas são armazenadas para análise e reprocessamento manual.

- **Autenticação e Segurança**:
  - Uso de HTTPS para todas as comunicações.
  - Autenticação via JWT ou chaves de API.
  - Limitação de taxa (Rate Limiting) para prevenir abusos.

- **Validação de Dados**:
  - Formatos obrigatórios para e-mail e número de telefone.
  - Campos obrigatórios devem ser preenchidos.

---

## APIs do Producer

O módulo Producer expõe endpoints REST para gerenciamento de notificações e autenticação de usuários. As principais rotas são:

| Caminho                       | Método | Descrição                                 | Autenticação |
|-------------------------------|--------|-------------------------------------------|--------------|
| `/api/login`                  | POST   | Autenticação de usuário (JWT)             | Não          |
| `/api/usuario/new`            | POST   | Cadastro de novos usuários                | Não          |
| `/api/notifications`          | POST   | Envio de nova notificação                 | Sim          |
| `/api/notifications/{id}`     | GET    | Consulta de notificação específica        | Sim          |

> **Obs.:** Todos os endpoints (exceto `/api/login` e `/api/usuario/new`) requerem autenticação via Bearer Token (JWT).

---

### Parâmetros das Requisições

#### Envio de Notificação (`POST /api/notifications`)

Para criar uma nova notificação, envie um JSON com os seguintes campos no corpo da requisição:

| Parâmetro  | Tipo   | Descrição                                                                             |
|------------|--------|---------------------------------------------------------------------------------------|
| channel    | string | Canal de envio (`email`, `SMS`, `push`)                                               |
| recipient  | string | Destinatário (endereço de e-mail, número de telefone, ou device token)                |
| message    | string | Conteúdo da mensagem ou referência ao template                                        |
| priority   | string | Prioridade da notificação (`alta`, `média`, `baixa`)                                  |
| data       | object | Dados adicionais para personalização de templates(ainda em desenvolvimento)           | 

**Exemplo de payload:**
```json
{
  "channel": "email",
  "recipient": "usuario@dominio.com",
  "message": "Bem-vindo ao sistema!",
  "priority": "alta",
  "data": {
    (ainda em desenvolvimento)
    "nome": "Usuário Exemplo"
  }
}
```

#### Cadastro de Usuário (`POST /api/usuario/new`)

A criação de usuários é simples e pensada apenas para fins de autenticação e demonstração. O objetivo do projeto é destacar o uso e integração de tecnologias como mensageria, autenticação JWT, orquestração com Docker e o fluxo completo de processamento de notificações — por isso, o sistema de usuários não é foco de complexidade e segue o mínimo necessário.

Parâmetros esperados no corpo da requisição:

| Parâmetro | Tipo   | Descrição                 |
|-----------|--------|--------------------------|
| username  | string | Nome de usuário desejado  |
| password  | string | Senha do usuário         |

**Exemplo de payload:**
```json
{
  "username": "novousuario",
  "password": "senhaSegura123"
}
```

---

## Como Executar

1. **Pré-requisitos**:
   - Docker e Docker Compose instalados.
   - Arquivo `.env` criado conforme instruções acima.

2. **Clone o repositório**:
   ```bash
   git clone https://github.com/joaogvbriel/SistemaDeNotificacao.git
   cd SistemaDeNotificacao
   ```

3. **Execute via Docker Compose**:
   ```bash
   docker-compose up --build
   ```

---

## Estrutura dos Diretórios

```
SistemaDeNotificacao/
├── SistemaDeNotificacao - Consumer/
│   └── sdn - consumer/
├── SistemaDeNotificacao - Producer/
│   └── sdn/
├── docker-compose.yml
└── ...
```

---

## Licença

Consulte o arquivo [LICENSE](https://github.com/joaogvbriel/SistemaDeNotificacao/blob/main/SistemaDeNotificacao%20-%20Producer/sdn/LICENSE) para detalhes sobre a licença de uso.

---

> Projeto desenvolvido por [joaogvbriel](https://github.com/joaogvbriel)
