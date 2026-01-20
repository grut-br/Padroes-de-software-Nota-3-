# Etapa 01 - Implementação Arquitetural

## Sistema de Consultas Médicas - Arquitetura Hexagonal

**Instituição:** Instituto Federal do Maranhão - IFMA
**Curso:** Sistemas de Informação
**Disciplina:** Padrões de Software e Refatoração
**Atividade:** Etapa 03 - Arquitetura Hexagonal aplicada ao Sistema de Consultas Médicas

---

## 1. Visão Geral da Implementação

O sistema de consultas e prontuários médicos foi desenvolvido seguindo rigorosamente os princípios da Arquitetura Hexagonal (Ports and Adapters), promovendo isolamento do domínio, baixo acoplamento, alta coesão e facilidade de testes e manutenção.

### 1.1 Objetivos Alcançados

- Implementação completa do modelo de domínio conforme diagrama UML
- Criação de casos de uso como serviços de aplicação
- Definição correta de portas de entrada e saída
- Implementação de adaptadores respeitando o desacoplamento
- Domínio totalmente independente de frameworks
- Demonstração clara da aplicação da Arquitetura Hexagonal

---

## 2. Modelo de Domínio (Core)

### 2.1 Entidades Implementadas

O domínio foi implementado com 10 entidades principais, todas sem dependências externas:

#### Paciente
Representa o paciente pediátrico do sistema.

**Atributos:**
- `nomeCrianca`: String
- `nomeResponsavel`: String
- `dataNascimento`: LocalDate
- `sexo`: String
- `endereco`: Endereco
- `telefones`: List<Telefone>
- `planoSaude`: PlanoSaude
- `prontuarios`: List<Prontuario>

**Métodos principais:**
- `adicionarTelefone(Telefone)`: Adiciona telefone ao paciente
- `adicionarProntuario(Prontuario)`: Adiciona prontuário ao histórico
- Getters para todos os atributos

**Localização:** `src/core/domain/Paciente.java`

#### Consulta
Representa uma consulta médica agendada ou realizada.

**Atributos:**
- `dataHora`: LocalDateTime
- `paciente`: Paciente
- `medico`: Medico
- `prontuario`: Prontuario
- `observers`: List<ConsultaObserver>

**Métodos principais:**
- `realizarConsulta()`: Cria prontuário e notifica observers
- `adicionarObserver(ConsultaObserver)`: Registra observador
- `isPacienteNovo()`: Verifica se paciente é novo
- `getProntuario()`: Retorna prontuário associado

**Regras de negócio:**
- Uma consulta gera exatamente um prontuário
- Não permite realizar a mesma consulta duas vezes
- Notifica observers ao realizar consulta

**Localização:** `src/core/domain/Consulta.java`

#### Prontuario
Armazena informações médicas da consulta.

**Atributos:**
- `dataCriacao`: LocalDateTime
- `peso`: double
- `altura`: double
- `sintomas`: String
- `observacaoClinica`: String
- `prescricoes`: List<Prescricao>
- `exames`: List<Exame>

**Métodos principais:**
- `adicionarPrescricao(Prescricao)`: Adiciona prescrição
- `adicionarExame(Exame)`: Adiciona exame
- Setters para peso, altura, sintomas e observações
- Getters para todos os atributos

**Localização:** `src/core/domain/Prontuario.java`

#### Prescricao
Prescrição médica de medicamento.

**Atributos:**
- `medicamento`: Medicamento
- `dosagem`: String
- `administracao`: String
- `tempoUso`: String

**Localização:** `src/core/domain/Prescricao.java`

#### Demais Entidades

- **Medicamento** (`src/core/domain/Medicamento.java`): Cadastro de medicamentos
- **Exame** (`src/core/domain/Exame.java`): Cadastro de exames
- **Medico** (`src/core/domain/Medico.java`): Informações do médico (nome, CRM)
- **PlanoSaude** (`src/core/domain/PlanoSaude.java`): Dados do plano de saúde
- **Endereco** (`src/core/domain/Endereco.java`): Endereço completo com CEP e UF
- **Telefone** (`src/core/domain/Telefone.java`): Telefone com identificação do responsável

### 2.2 Eventos de Domínio

#### ConsultaRealizadaEvent
Evento disparado quando uma consulta é realizada.

**Atributos:**
- `consulta`: Consulta

**Localização:** `src/core/domain/ConsultaRealizadaEvent.java`

#### ConsultaObserver (Interface)
Interface do padrão Observer para receber notificações.

**Métodos:**
- `notificar(ConsultaRealizadaEvent)`: Processa evento de consulta realizada

**Localização:** `src/core/domain/ConsultaObserver.java`

### 2.3 Regras de Negócio Implementadas

1. **Uma consulta gera exatamente um prontuário**
   - Implementado em `Consulta.realizarConsulta()`
   - Valida que consulta não foi realizada anteriormente

2. **Um prontuário pode conter zero ou mais exames**
   - Implementado através de `List<Exame>` em Prontuario

3. **Um prontuário pode conter zero ou mais prescrições**
   - Implementado através de `List<Prescricao>` em Prontuario

4. **Um paciente pode ou não estar vinculado a um plano de saúde**
   - PlanoSaude é opcional (pode ser null)

5. **Identificação automática de paciente novo**
   - Método `isPacienteNovo()` verifica se lista de prontuários está vazia

---

## 3. Portas (Interfaces)

### 3.1 Portas de Entrada (Use Cases)

Definem os casos de uso do sistema, representando as operações que o sistema oferece.

#### RegistrarProntuarioUseCase
Registra prontuário em uma consulta existente.

**Métodos:**
```java
void registrar(Long consultaId, Prontuario prontuario)
```

**Localização:** `src/ports/in/RegistrarProntuarioUseCase.java`

#### ListarConsultasDoDiaUseCase
Lista todas as consultas agendadas para determinado dia.

**Métodos:**
```java
List<Consulta> listar(LocalDate data)
```

**Localização:** `src/ports/in/ListarConsultasDoDiaUseCase.java`

#### ConsultarHistoricoProntuarioUseCase
Consulta histórico de prontuários de um paciente.

**Métodos:**
```java
List<Prontuario> consultarHistorico(Long pacienteId)
List<Double> consultarHistoricoPeso(Long pacienteId)
List<Double> consultarHistoricoAltura(Long pacienteId)
```

**Localização:** `src/ports/in/ConsultarHistoricoProntuarioUseCase.java`

### 3.2 Portas de Saída (Repositories)

Definem contratos para acesso a recursos externos (persistência).

#### PacienteRepository
Persistência de pacientes.

**Métodos:**
```java
void salvar(Long id, Paciente paciente)
Optional<Paciente> buscarPorId(Long id)
List<Paciente> listarTodos()
```

**Localização:** `src/ports/out/PacienteRepository.java`

#### ConsultaRepository
Persistência de consultas.

**Métodos:**
```java
void salvar(Long id, Consulta consulta)
Optional<Consulta> buscarPorId(Long id)
List<Consulta> listarTodas()
```

**Localização:** `src/ports/out/ConsultaRepository.java`

#### ProntuarioRepository
Persistência de prontuários.

**Métodos:**
```java
void salvar(Prontuario prontuario)
```

**Localização:** `src/ports/out/ProntuarioRepository.java`

#### MedicamentoRepository
Persistência de medicamentos.

**Métodos:**
```java
List<Medicamento> listarTodos()
```

**Localização:** `src/ports/out/MedicamentoRepository.java`

#### ExameRepository
Persistência de exames.

**Métodos:**
```java
List<Exame> listarTodos()
```

**Localização:** `src/ports/out/ExameRepository.java`

---

## 4. Casos de Uso (Implementações)

### 4.1 RegistrarProntuarioUseCaseImpl

**Responsabilidade:** Orquestra o registro de prontuário em uma consulta.

**Dependências:**
- `ConsultaRepository`: Buscar consulta
- `ProntuarioRepository`: Salvar prontuário

**Fluxo de Execução:**
1. Busca consulta por ID
2. Valida existência da consulta
3. Realiza consulta (cria prontuário)
4. Atualiza dados do prontuário
5. Salva prontuário no repositório

**Localização:** `src/core/usecase/RegistrarProntuarioUseCaseImpl.java`

### 4.2 ListarConsultasDoDiaUseCaseImpl

**Responsabilidade:** Lista consultas agendadas para determinado dia.

**Dependências:**
- `ConsultaRepository`: Listar todas as consultas

**Fluxo de Execução:**
1. Busca todas as consultas
2. Filtra consultas do dia especificado
3. Retorna lista filtrada

**Localização:** `src/core/usecase/ListarConsultasDoDiaUseCaseImpl.java`

### 4.3 ConsultarHistoricoProntuarioUseCaseImpl

**Responsabilidade:** Consulta histórico de prontuários de um paciente.

**Dependências:**
- `PacienteRepository`: Buscar paciente

**Fluxo de Execução:**
1. Busca paciente por ID
2. Valida existência do paciente
3. Retorna lista de prontuários do paciente
4. Permite filtrar histórico de peso
5. Permite filtrar histórico de altura

**Localização:** `src/core/usecase/ConsultarHistoricoProntuarioUseCaseImpl.java`

---

## 5. Adaptadores

### 5.1 Adaptadores de Entrada

#### ProntuarioCLIController

**Responsabilidade:** Interface CLI para registro de prontuários.

**Função:** Orquestra chamadas aos casos de uso através de linha de comando.

**Localização:** `src/adapters/in/cli/ProntuarioCLIController.java`

### 5.2 Adaptadores de Saída - Repositories

Todas as implementações utilizam estruturas em memória (HashMap/ArrayList) para persistência.

#### PacienteRepositoryMemory
**Implementação:** HashMap<Long, Paciente>
**Localização:** `src/adapters/out/repository/PacienteRepositoryMemory.java`

#### ConsultaRepositoryMemory
**Implementação:** HashMap<Long, Consulta>
**Localização:** `src/adapters/out/repository/ConsultaRepositoryMemory.java`

#### ProntuarioRepositoryMemory
**Implementação:** ArrayList<Prontuario>
**Localização:** `src/adapters/out/repository/ProntuarioRepositoryMemory.java`

#### MedicamentoRepositoryMemory
**Implementação:** ArrayList<Medicamento>
**Localização:** `src/adapters/out/repository/MedicamentoRepositoryMemory.java`

#### ExameRepositoryMemory
**Implementação:** ArrayList<Exame>
**Localização:** `src/adapters/out/repository/ExameRepositoryMemory.java`

### 5.3 Adaptadores de Saída - Notificações

#### NotificacaoService

**Responsabilidade:** Implementa padrão Observer para notificações de consultas realizadas.

**Funcionalidades:**
- Notificação detalhada ao realizar consulta
- Simulação de envio de email
- Simulação de envio de SMS

**Padrão Implementado:** Observer Pattern

**Localização:** `src/adapters/out/notification/NotificacaoService.java`

---

## 6. Padrões de Projeto Implementados

### 6.1 Repository Pattern

**Objetivo:** Isolar lógica de persistência do domínio.

**Benefícios:**
- Domínio não conhece detalhes de persistência
- Fácil trocar implementação (memória → banco de dados)
- Testabilidade através de mocks

**Aplicação:**
- 5 repositórios implementados
- Interfaces definem contratos
- Implementações concretas isoladas em adaptadores

### 6.2 Observer Pattern

**Objetivo:** Notificar múltiplos observadores quando consulta é realizada.

**Benefícios:**
- Desacoplamento entre consulta e notificações
- Fácil adicionar novos tipos de notificação
- Seguir princípio Open/Closed

**Aplicação:**
- `ConsultaObserver` define contrato
- `NotificacaoService` implementa observador
- `Consulta` gerencia lista de observers

### 6.3 Dependency Injection

**Objetivo:** Inverter dependências através de injeção via construtor.

**Benefícios:**
- Baixo acoplamento
- Facilita testes unitários
- Segue Dependency Inversion Principle

**Aplicação:**
- Todos os casos de uso recebem dependências via construtor
- Nenhuma instanciação direta de dependências

### 6.4 Adapter Pattern

**Objetivo:** Adaptar interfaces externas para o domínio.

**Benefícios:**
- Isola tecnologias externas
- Permite múltiplas implementações
- Facilita migração tecnológica

**Aplicação:**
- Controllers CLI adaptam entrada
- Repositories adaptam persistência
- NotificacaoService adapta sistema de notificações

---

## 7. Princípios SOLID Aplicados

### 7.1 Single Responsibility Principle (SRP)

**Definição:** Cada classe deve ter uma única responsabilidade.

**Aplicação:**
- `Consulta`: Apenas gerencia lógica de consultas
- `Prontuario`: Apenas armazena dados médicos
- `RegistrarProntuarioUseCaseImpl`: Apenas registra prontuários
- `NotificacaoService`: Apenas envia notificações

### 7.2 Open/Closed Principle (OCP)

**Definição:** Aberto para extensão, fechado para modificação.

**Aplicação:**
- Novos observers podem ser adicionados sem modificar `Consulta`
- Novos repositórios podem ser criados implementando interfaces
- Novos casos de uso não afetam o domínio

### 7.3 Liskov Substitution Principle (LSP)

**Definição:** Subtipos devem ser substituíveis por seus tipos base.

**Aplicação:**
- Qualquer implementação de `PacienteRepository` pode substituir outra
- Qualquer `ConsultaObserver` pode ser usado sem quebrar o sistema
- Implementações de repositórios são intercambiáveis

### 7.4 Interface Segregation Principle (ISP)

**Definição:** Interfaces específicas são melhores que interfaces gerais.

**Aplicação:**
- Cada repository tem interface específica
- Use cases têm interfaces dedicadas
- Não há interfaces "gordas" com métodos desnecessários

### 7.5 Dependency Inversion Principle (DIP)

**Definição:** Dependa de abstrações, não de implementações concretas.

**Aplicação:**
- Use cases dependem de interfaces de repositórios
- Consulta depende de `ConsultaObserver`, não de `NotificacaoService`
- Nenhuma dependência direta de implementações concretas no domínio

---

## 8. Testes Automatizados

### 8.1 Estrutura de Testes

Foram implementados 4 módulos de testes sem dependência de frameworks externos.

#### DomainTest
Testa regras de negócio do domínio.

**Testes:**
- Consulta gera prontuário corretamente
- Impede realização duplicada de consulta

**Localização:** `src/tests/DomainTest.java`

#### AdapterTest
Testa adaptadores de repositório.

**Testes:**
- Salvar e buscar consulta
- Listar todas as consultas

**Localização:** `src/tests/AdapterTest.java`

#### UseCaseTest
Testa casos de uso.

**Testes:**
- Registrar prontuário
- Listar consultas do dia
- Consultar histórico de prontuários
- Consultar histórico de peso
- Consultar histórico de altura

**Localização:** `src/tests/UseCaseTest.java`

#### ObserverTest
Testa padrão Observer.

**Testes:**
- Notificação não é disparada antes de realizar consulta
- Notificação é disparada após realizar consulta

**Localização:** `src/tests/ObserverTest.java`

### 8.2 Framework de Testes Customizado

**TestInfo** - Classe utilitária para assertions.

**Métodos:**
- `assertTrue(boolean, String)`: Verifica condição verdadeira
- `assertEquals(Object, Object, String)`: Verifica igualdade
- `assertNotNull(Object, String)`: Verifica não nulo

**Localização:** `src/tests/TestInfo.java`

---

## 9. Arquitetura Hexagonal - Demonstração

### 9.1 Camadas da Arquitetura

```
┌─────────────────────────────────────────┐
│        ADAPTADORES DE ENTRADA           │
│   (CLI Controller, REST, etc.)          │
└──────────────┬──────────────────────────┘
               │
┌──────────────▼──────────────────────────┐
│         PORTAS DE ENTRADA               │
│      (Use Case Interfaces)              │
└──────────────┬──────────────────────────┘
               │
┌──────────────▼──────────────────────────┐
│            DOMÍNIO (CORE)               │
│  ┌────────────────────────────────────┐ │
│  │  Entidades e Regras de Negócio     │ │
│  │  (Consulta, Paciente, Prontuario)  │ │
│  └────────────────────────────────────┘ │
└──────────────┬──────────────────────────┘
               │
┌──────────────▼──────────────────────────┐
│         PORTAS DE SAÍDA                 │
│    (Repository Interfaces)              │
└──────────────┬──────────────────────────┘
               │
┌──────────────▼──────────────────────────┐
│        ADAPTADORES DE SAÍDA             │
│  (Memory Repos, JPA, APIs externas)     │
└─────────────────────────────────────────┘
```

### 9.2 Fluxo de Execução

1. **Adaptador de Entrada** recebe requisição (CLI, REST, etc.)
2. **Porta de Entrada** define contrato do caso de uso
3. **Caso de Uso** orquestra lógica de aplicação
4. **Domínio** executa regras de negócio
5. **Porta de Saída** define contrato de persistência
6. **Adaptador de Saída** implementa persistência

### 9.3 Independência do Domínio

O domínio é completamente independente de:
- Frameworks (Spring, Hibernate, etc.)
- Tecnologias de persistência (SQL, NoSQL, etc.)
- Interfaces de usuário (Web, CLI, Mobile)
- Bibliotecas externas

**Evidências:**
- Nenhuma anotação de framework no domínio
- Nenhuma importação de bibliotecas externas
- Apenas classes Java puras
- Apenas tipos do java.time e java.util

---

## 10. Benefícios Alcançados

### 10.1 Isolamento do Domínio
- Regras de negócio protegidas de mudanças tecnológicas
- Domínio testável sem dependências externas
- Facilita compreensão da lógica de negócio

### 10.2 Baixo Acoplamento
- Componentes conectados por interfaces
- Fácil substituição de implementações
- Mudanças localizadas

### 10.3 Alta Coesão
- Cada classe tem responsabilidade bem definida
- Métodos relacionados agrupados
- Separação clara de conceitos

### 10.4 Facilidade de Testes
- Testes sem dependências externas
- Fácil criar mocks de adaptadores
- Testes rápidos e confiáveis

### 10.5 Manutenibilidade
- Código organizado e estruturado
- Fácil localizar funcionalidades
- Mudanças com baixo impacto

### 10.6 Preparação para Evolução
- Fácil adicionar novos casos de uso
- Fácil adicionar novos adaptadores
- Arquitetura escalável

---

## 11. Estrutura de Diretórios

```
src/
├── core/
│   ├── domain/
│   │   ├── Consulta.java
│   │   ├── ConsultaObserver.java
│   │   ├── ConsultaRealizadaEvent.java
│   │   ├── Endereco.java
│   │   ├── Exame.java
│   │   ├── Medicamento.java
│   │   ├── Medico.java
│   │   ├── Paciente.java
│   │   ├── PlanoSaude.java
│   │   ├── Prescricao.java
│   │   ├── Prontuario.java
│   │   └── Telefone.java
│   └── usecase/
│       ├── ConsultarHistoricoProntuarioUseCaseImpl.java
│       ├── ListarConsultasDoDiaUseCaseImpl.java
│       └── RegistrarProntuarioUseCaseImpl.java
├── ports/
│   ├── in/
│   │   ├── ConsultarHistoricoProntuarioUseCase.java
│   │   ├── ListarConsultasDoDiaUseCase.java
│   │   └── RegistrarProntuarioUseCase.java
│   └── out/
│       ├── ConsultaRepository.java
│       ├── ExameRepository.java
│       ├── MedicamentoRepository.java
│       ├── PacienteRepository.java
│       └── ProntuarioRepository.java
├── adapters/
│   ├── in/
│   │   └── cli/
│   │       └── ProntuarioCLIController.java
│   └── out/
│       ├── repository/
│       │   ├── ConsultaRepositoryMemory.java
│       │   ├── ExameRepositoryMemory.java
│       │   ├── MedicamentoRepositoryMemory.java
│       │   ├── PacienteRepositoryMemory.java
│       │   └── ProntuarioRepositoryMemory.java
│       └── notification/
│           └── NotificacaoService.java
├── tests/
│   ├── AdapterTest.java
│   ├── DomainTest.java
│   ├── ObserverTest.java
│   ├── TestInfo.java
│   └── UseCaseTest.java
└── Main.java
```

---

## 12. Compilação e Execução

### 12.1 Compilar o Projeto

```bash
cd src
javac Main.java
```

### 12.2 Executar os Testes

```bash
cd src
java Main
```

### 12.3 Saída Esperada

```
🚀 INICIANDO BATERIA DE TESTES (JAVA PURO)

=== Testes de Domínio ===
✅ SUCESSO: Consulta deve gerar um prontuário
✅ SUCESSO: Impediu consulta duplicada corretamente

=== Testes de Adaptadores ===
✅ SUCESSO: Deve encontrar consulta salva
✅ SUCESSO: Deve listar 1 consulta

=== Testes de Casos de Uso ===
✅ SUCESSO: RegistrarProntuario executado sem erros
✅ SUCESSO: Deve retornar apenas as 2 consultas de hoje
✅ SUCESSO: Deve retornar 2 prontuarios no historico
✅ SUCESSO: Deve retornar 2 pesos no historico
✅ SUCESSO: Deve retornar 2 alturas no historico

=== Testes de Observer (Eventos) ===
✅ SUCESSO: Não deve ter notificação antes de realizar consulta
   [Teste] Observer chamado para consulta de: Pedro
✅ SUCESSO: Deve ter recebido notificação após realizar consulta

✅✅✅ TODOS OS TESTES PASSARAM COM SUCESSO! ✅✅✅
```

---

## 13. Conclusão da Etapa 01

A Etapa 01 foi implementada com sucesso, atendendo a todos os requisitos especificados:

1. ✅ Modelo de domínio completo conforme diagrama UML
2. ✅ Casos de uso implementados como serviços de aplicação
3. ✅ Portas de entrada e saída corretamente definidas
4. ✅ Adaptadores implementados respeitando desacoplamento
5. ✅ Domínio independente de frameworks
6. ✅ Aplicação clara da Arquitetura Hexagonal

O sistema está preparado para evolução, manutenção e refatoração, demonstrando os benefícios da Arquitetura Hexagonal e aplicação consistente dos princípios SOLID e padrões de projeto.
