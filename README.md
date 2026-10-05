# Portal de Solicitações Internas

Sistema web Full Stack desenvolvido como solução para o desafio técnico da **BIT Soluções – Desenvolvedor(a) de Sistemas Júnior**.

A aplicação permite que usuários autenticados registrem e acompanhem solicitações internas, enquanto usuários administradores podem gerenciar o andamento das solicitações e alterar seus status.

O projeto foi desenvolvido com foco em organização de código, separação de responsabilidades, segurança básica, persistência de dados, validações e uma interface simples e funcional.

---

## 📋 Funcionalidades

### Autenticação

- Login com usuário e senha
- Controle de sessão
- Logout
- Proteção das rotas da aplicação
- Senhas armazenadas utilizando hash BCrypt
- Controle de acesso baseado em perfil

### Solicitações

- Criação de solicitações
- Visualização de solicitações
- Edição de solicitações abertas pelo próprio usuário
- Exclusão lógica de solicitações abertas pelo próprio usuário
- Registro automático da data de criação
- Identificação automática do usuário solicitante
- Status inicial definido automaticamente como `ABERTO`

### Gerenciamento

Administradores podem:

- Visualizar todas as solicitações ativas
- Consultar detalhes das solicitações
- Alterar o status das solicitações

Status disponíveis:

- `ABERTO`
- `EM_ATENDIMENTO`
- `CONCLUIDO`

### Consulta e filtros

A listagem permite combinar filtros por:

- Período
- Categoria
- Status
- Texto livre no título

Categorias disponíveis:

- TI
- RH
- Compras
- Financeiro
- Infraestrutura

### Dashboard

O dashboard apresenta indicadores simples de:

- Total de solicitações
- Solicitações abertas
- Solicitações em atendimento
- Solicitações concluídas

Os indicadores respeitam as regras de acesso do usuário autenticado.

---

## 🔐 Perfis e regras de acesso

O sistema utiliza controle de acesso baseado em papéis (**RBAC – Role-Based Access Control**).

### Usuário comum — `ROLE_USER`

Pode:

- Criar solicitações
- Visualizar suas próprias solicitações
- Editar suas próprias solicitações enquanto estiverem abertas
- Excluir logicamente suas próprias solicitações enquanto estiverem abertas

Não pode:

- Visualizar solicitações de outros usuários
- Alterar o status das solicitações

### Administrador — `ROLE_ADMIN`

Pode:

- Visualizar todas as solicitações ativas
- Consultar detalhes das solicitações
- Alterar o status das solicitações

O administrador não possui as ações de edição e exclusão de solicitações.

As regras de autorização são aplicadas no backend. O frontend apenas adapta a interface de acordo com o perfil do usuário.

---

## 🏗️ Arquitetura

O projeto utiliza uma arquitetura separada em frontend, backend e banco de dados.

```text
desafio-bit-solucoes/
│
├── backend/
│   └── Aplicação Spring Boot
│
├── frontend/
│   └── Aplicação Angular
│
├── docker-compose.yml
├── .env.example
├── .gitignore
└── README.md
```
### Backend

Responsável por:

- Autenticação e autorização
- Regras de negócio 
- APIs REST
- Validação das requisições
- Persistência dos dados
- Controle de acesso às solicitações
- Dashboard
- Gerenciamento das sessões

### Frontend

Responsável por:

- Interface do usuário
- Navegação
- Formulários
- Listagem e filtros
- Dashboard
- Consumo das APIs do backend
- Controle visual das funcionalidades de acordo com o perfil autenticado

### Banco de dados

Responsável pela persistência de:

- Usuários
- Solicitações

A estrutura do banco é criada automaticamente através das migrations do Flyway.

## 🛠️ Tecnologias utilizadas

### Frontend

- Angular
- TypeScript
- HTML
- CSS
- RxJS
- Angular HttpClient

### Backend
 
- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- Hibernate
- BCrypt

### Banco de dados

- MySQL 8.0
- Flyway

### Infraestrutura e ferramentas

- Docker
- Docker Compose
- Maven
- Git
- GitHub

## 📦 Pré-requisitos

Para executar o projeto localmente, é necessário possuir:

- Git
- Docker Desktop
- Java JDK 21
- Maven
- Node.js
- npm
- Angular CLI

O MySQL não precisa ser instalado diretamente na máquina, pois o projeto utiliza um container Docker para executar o banco de dados.

## 🚀 Instalação e execução

### 1. Clonar o repositório
```text
git clone <URL_DO_REPOSITORIO>
```
Entre na pasta do projeto:
```text
cd desafio-bit-solucoes
```

### 2. Configuração das variáveis de ambiente

Na raiz do projeto existe um arquivo .env.example.

Crie uma cópia chamada .env:
```text
.env.example
    ↓
.env
```


A configuração utilizada pelo projeto é:
```text
MYSQL_DATABASE=portal_solicitacoes
MYSQL_USER=bit_user
MYSQL_PASSWORD=bit_pass_123
MYSQL_ROOT_PASSWORD=root_secret_123
```


O arquivo .env não deve ser versionado no Git. Ele está incluído no .gitignore.

### 3. Subir o banco de dados

Na raiz do projeto, execute:
```text
docker compose up -d
```
Isso irá iniciar o container:
```text
MySQL 8.0
```

utilizando:
```text
Banco: portal_solicitacoes
Usuário: bit_user
Porta: 3306
```

Para verificar o container:
```text
docker compose ps
```

O MySQL deve estar com status de execução.

### 4. Executar o backend

Abra um terminal na raiz do projeto e entre na pasta:
```text
cd backend
```

Execute:
```text
mvn spring-boot:run
```

O backend será iniciado em:

```text
http://localhost:8080
```

### Banco de dados e Flyway

Ao iniciar a aplicação, o Flyway executa as migrations pendentes e cria automaticamente a estrutura necessária para o sistema.

Entre as estruturas criadas estão:

- usuario
- solicitacao

A aplicação utiliza:
```text
Hibernate ddl-auto: validate
```

Dessa forma, o Hibernate valida a estrutura existente sem assumir a responsabilidade pela criação das tabelas.

### 5. Executar o frontend

Em outro terminal, a partir da raiz do projeto:
```text
cd frontend
```

Instale as dependências:
```text
npm install
```

Depois execute:
```text
ng serve
```

A aplicação ficará disponível em:
```text
http://localhost:4200
```

## 🔑 Credenciais de demonstração

O projeto possui usuários de teste criados pela migration inicial.

| Perfil | Usuário | Senha |
|---|---|---|
| Administrador | `admin` | `123456` |
| Usuário comum | `comum` | `123456` |

> ⚠️ As credenciais acima são destinadas exclusivamente à execução e avaliação local do projeto.

# 🧪 Cenários para avaliação

Para verificar as principais regras de negócio da aplicação, recomenda-se utilizar os dois perfis disponíveis.

### Teste com usuário comum

1. Acesse o sistema utilizando `comum / 123456`.
2. Crie uma nova solicitação.
3. Verifique que o status inicial é `ABERTO`.
4. Verifique que a solicitação aparece na listagem.
5. Edite a solicitação enquanto ela estiver com status `ABERTO`.
6. Exclua a solicitação.
7. Verifique que ela deixa de aparecer na listagem.

### Teste com administrador

1. Faça logout da aplicação.
2. Acesse o sistema utilizando `admin / 123456`.
3. Acesse a listagem de solicitações.
4. Verifique que o administrador consegue visualizar as solicitações existentes.
5. Acesse os detalhes de uma solicitação.
6. Altere o status para `EM_ATENDIMENTO`.
7. Altere posteriormente o status para `CONCLUIDO`.
8. Acesse o dashboard e verifique a atualização dos indicadores.

### Teste dos filtros

Na listagem de solicitações, teste os seguintes filtros:

- Período;
- Categoria;
- Status;
- Título.

Os filtros podem ser utilizados individualmente ou combinados entre si.

---

# 🔒 Segurança e controle de acesso

A autenticação e a autorização da aplicação são implementadas no backend utilizando **Spring Security**.

A sessão do usuário autenticado é utilizada para determinar sua identidade e suas permissões.

O identificador do usuário não é confiado ao frontend para determinar o proprietário de uma solicitação. O backend utiliza o usuário autenticado na sessão para aplicar as regras de autorização.

Dessa forma, um usuário não consegue simplesmente alterar um identificador enviado pelo cliente para tentar acessar ou modificar solicitações pertencentes a outro usuário.

As senhas não são armazenadas em texto puro. O sistema utiliza **BCrypt** para realizar o armazenamento seguro das credenciais.

O controle de acesso é baseado em dois perfis:

| Perfil | Permissões |
|---|---|
| `ROLE_USER` | Criar solicitações, visualizar as próprias solicitações e editar ou excluir solicitações próprias enquanto estiverem abertas |
| `ROLE_ADMIN` | Visualizar todas as solicitações ativas e alterar seus status |

---

# 🗃️ Persistência e exclusão lógica

As solicitações utilizam exclusão lógica (*soft delete*).

Em vez de remover fisicamente o registro do banco de dados, o campo `ativo` é alterado para indicar que a solicitação não está mais ativa.

As consultas normais da aplicação consideram apenas registros ativos.

Essa abordagem preserva o registro no banco de dados e evita a perda imediata do histórico da solicitação.

---

A comunicação entre frontend e backend utiliza requisições HTTP e dados no formato JSON.

A autenticação utiliza sessão HTTP, permitindo que o usuário autenticado acesse os recursos protegidos conforme suas permissões.

---

# 🗂️ Estrutura de dados

O banco de dados possui duas entidades principais, `usuario` e `solicitacao`, relacionadas por uma associação de um para muitos (1:N).

```text
USUARIO
   │
   │ 1:N
   │
   ▼
SOLICITACAO
```

### `usuario`

Armazena os usuários do sistema e seus respectivos perfis de acesso.

Principais campos:

* `id`: identificador único do usuário;
* `username`: nome de usuário utilizado para autenticação;
* `password`: senha armazenada de forma criptografada por hash;
* `role`: perfil de acesso do usuário.

### `solicitacao`

Armazena as solicitações cadastradas no sistema.

Principais campos:

* `id`: identificador único da solicitação;
* `titulo`: título da solicitação;
* `descricao`: descrição detalhada;
* `categoria`: categoria da solicitação;
* `status`: situação atual da solicitação;
* `data_criacao`: data e hora de criação;
* `usuario_id`: identificador do usuário responsável pelo registro;
* `ativo`: indicador utilizado para controlar a exclusão lógica.

O relacionamento entre as entidades permite que um usuário possua várias solicitações, enquanto cada solicitação está associada a um único usuário.

```text
Um usuário
    │
    ├── Solicitação 1
    ├── Solicitação 2
    ├── Solicitação 3
    └── ...
```

---

# 🗄️ Migrations

O projeto utiliza **Flyway** para versionamento e execução das alterações estruturais do banco de dados.

A migration inicial é responsável pela criação das tabelas necessárias para a aplicação.

Os arquivos de migration estão localizados em:

```text
backend/src/main/resources/db/migration/
```

Essa abordagem permite que a estrutura do banco seja criada de maneira consistente durante a inicialização da aplicação, facilitando a reprodução do ambiente em diferentes máquinas.

---

# 🌐 API REST

A aplicação disponibiliza uma API REST para comunicação entre o frontend e o backend.

Os principais recursos são:

| Recurso      | Responsabilidade                            |
| ------------ | ------------------------------------------- |
| Autenticação | Login e controle da sessão                  |
| Solicitações | Criação, consulta, edição e exclusão lógica |
| Dashboard    | Consulta dos indicadores das solicitações   |

O acesso aos recursos é controlado pelo backend, considerando o perfil do usuário autenticado e as regras de negócio da aplicação.

---

# 📱 Interface

A aplicação possui as seguintes áreas principais:

* **Login:** autenticação do usuário;
* **Dashboard:** visualização dos indicadores das solicitações;
* **Listagem de solicitações:** consulta dos registros e utilização dos filtros;
* **Nova solicitação:** formulário para cadastro de solicitações;
* **Detalhes e edição:** visualização dos dados e edição das solicitações permitidas conforme as regras de acesso.

A interface foi desenvolvida com foco em uma navegação simples e objetiva, permitindo a utilização das funcionalidades previstas no desafio sem adicionar complexidade desnecessária.

---

# 📚 Documentação complementar

Além deste README, o projeto possui documentação técnica complementar por meio do **Memorial Técnico de Desenvolvimento**.

O memorial apresenta as principais decisões técnicas e arquiteturais do projeto, incluindo:

* Tecnologias, frameworks e bibliotecas utilizados;
* Justificativas para as escolhas tecnológicas;
* Estrutura arquitetural da aplicação;
* Organização das camadas;
* Modelagem de dados;
* Estratégia de autenticação e autorização;
* Comunicação entre frontend e backend;
* Organização do código;
* Limitações atuais;
* Possíveis melhorias futuras;
* Considerações para um ambiente de produção.

---

# 🎯 Objetivo do projeto

O projeto foi desenvolvido para demonstrar, em uma aplicação Full Stack de pequeno porte, conhecimentos fundamentais relacionados a:

* Desenvolvimento de APIs REST;
* Desenvolvimento frontend;
* Integração entre frontend e backend;
* Persistência de dados;
* Modelagem de banco de dados relacional;
* Autenticação e autorização;
* Validação de dados;
* Implementação de regras de negócio;
* Organização de código;
* Controle de versão;
* Containerização do banco de dados;
* Documentação técnica.

A solução prioriza **clareza, organização, funcionamento e facilidade de manutenção**, evitando a adição de complexidade desnecessária para os requisitos do projeto.

---

# 📄 Desafio técnico

Este projeto foi desenvolvido como parte da segunda etapa do processo seletivo para **Desenvolvedor(a) de Sistemas Júnior da BIT Soluções**.

O desafio propõe o desenvolvimento de uma aplicação Full Stack para gerenciamento de solicitações internas, contemplando autenticação, cadastro e gerenciamento de solicitações, filtros, dashboard, persistência em banco de dados e documentação técnica.

A solução busca atender aos requisitos propostos, priorizando uma aplicação funcional, organizada, tecnicamente justificada e devidamente documentada.

---

# 👨‍💻 Desenvolvedor

**Gabriel Vitório Francisco da Silva**

Projeto desenvolvido para fins de avaliação técnica e demonstração de conhecimentos em desenvolvimento Full Stack.
