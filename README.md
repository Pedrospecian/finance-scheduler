# Finance Scheduler - API & Frontend

Este projeto consiste em uma solução completa para agendamento financeiro, composta por uma API REST desenvolvida em **Spring Boot** e uma aplicação client em **Vue.js com TypeScript**.

---

## Tecnologias e Ferramentas Utilizadas

### Backend
* **Linguagem:** Java 11 (LTS) — *Garantindo compatibilidade com o ambiente de execução solicitado.*
* **Framework:** Spring Boot
* **Gerenciador de Dependências:** Maven (via Maven Wrapper v4.0.6)
* **Banco de Dados:** H2 Database em memória

### Frontend
* **Framework:** Vue.js (v3)
* **Linguagem:** TypeScript — *Adotado para garantir tipagem estática, reduzindo bugs em tempo de execução e melhorando a manutenibilidade.*
* **Ferramenta de Build:** Vite

---

## Decisões Arquiteturais e de Design

### 1. Centralização da Regra de Negócio (Cálculo de Taxas)
* **Segurança e Consistência:** Toda a lógica de cálculo da taxa de transferência foi delegada exclusivamente ao backend. Isso impede cenários maliciosos ou errôneos onde um usuário poderia disparar o endpoint de criação de transação enviando um valor incorreto de taxa calculado pelo client.
* **Single Source of Truth (Fonte Única de Verdade):** Quando o usuário altera o valor ou a data da transferência no frontend, o sistema faz uma requisição assíncrona a um endpoint específico para obter o cálculo atualizado da taxa. Embora gerasse menos chamadas se feito no frontend, centralizar essa lógica no método `calcularTaxa()` da classe `TransactionController` garante que qualquer mudança futura na regra tributária seja feita em **um único lugar**.

### 2. Performance e Tratamento de Dados Massivos
* **Busca e Paginação no Server-side:** Na tela de listagem de transações, optei por implementar busca e paginação vindas diretamente da API. Inicialmente, foi considerada uma busca dinâmica via propriedades computadas (`computed`) no Vue.js, mas a abordagem foi descartada visando a eficiência do sistema, evitando gargalos de memória e processamento no navegador do usuário caso o sistema lide com dezenas de milhares de registros.

### 3. Modularidade e Experiência do Usuário (UX)
* **Estrutura do Frontend:** As páginas e componentes do frontend foram organizados em módulos focando em escalabilidade e manutenibilidade do código.
* **Gerenciamento de Contas:** Além das transações, foi implementada uma área para criação e listagem de contas, permitindo que o usuário gerencie as entidades de forma dinâmica diretamente pela interface.
* **Imutabilidade com DTOs (Java 11):** Para o tráfego seguro de dados na API, foram utilizadas classes DTO (`TaxaRequestDTO`, `TransactionRequestDTO`) com atributos imutáveis (`private final`), respeitando as limitações de sintaxe do Java 11.

---

## Como Executar o Projeto

Certifique-se de ter o **JDK 11** e o **Node.js** (versão 18+ recomendada) instalados em sua máquina.

### 1. Executando o Backend (API)

Navegue até a pasta do projeto backend (`finance-scheduler-api/demo`, que é onde fica o arquivo `pom.xml`) e execute o seguinte comando:

```bash
# Limpar builds antigos e iniciar a aplicação via Maven Wrapper
./mvnw clean spring-boot:run
```

A API estará disponível em: http://localhost:8080

### 2. Executando o Frontend

Navegue até a pasta `finance-scheduler-front` e execute os seguintes comandos:

```bash
# Instalar as dependências do projeto
npm install

# Iniciar o servidor de desenvolvimento
npm run dev
```

O frontend estará disponível no endereço `http://localhost:5173`.

---

## Guia de Testes (Passo a Passo)

Para validar o fluxo completo da aplicação sem erros, siga a ordem abaixo ao interagir com a interface:

1. **Cadastrar Contas (Obrigatório):** Acesse a tela `Nova Conta` e **cadastre pelo menos duas (2) contas diferentes**. O sistema exige uma conta de origem e uma conta de destino para validar as regras de agendamento.
2. **Simular Taxa:** Na tela `Nova Transação`, ao preencher o valor e a data, o sistema fará uma chamada ao backend para trazer o valor da taxa de transferência.
3. **Agendar Transação:** Informe o número da conta de origem, o número da conta de destino, defina o valor/data e confirme o agendamento.
4. **Listagem e Busca:** Acesse a tela de listagem para ver a paginação e testar os filtros de busca.