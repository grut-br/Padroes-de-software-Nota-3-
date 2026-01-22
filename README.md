# Sistema de Consultas Médicas - Arquitetura Hexagonal

Sistema de gerenciamento de consultas e prontuários médicos desenvolvido com Arquitetura Hexagonal (Ports and Adapters), aplicando princípios SOLID e padrões de projeto.

## Arquitetura

O projeto segue rigorosamente os princípios da Arquitetura Hexagonal, organizando o código em três camadas principais:

### 1. Core (Domínio)

Contém a lógica de negócio pura, sem dependências externas.

**Entidades:**
- `Paciente` - Dados do paciente e responsável
- `Consulta` - Agendamento e realização de consultas
- `Prontuario` - Registro médico com peso, altura, sintomas e observações
- `Prescricao` - Prescrição de medicamentos
- `Medicamento` - Cadastro de medicamentos
- `Exame` - Cadastro de exames
- `Medico` - Informações do médico
- `PlanoSaude` - Dados do plano de saúde
- `Endereco` - Endereço completo do paciente
- `Telefone` - Telefones de contato

**Eventos:**
- `ConsultaRealizadaEvent` - Evento disparado ao realizar consulta
- `ConsultaObserver` - Interface do padrão Observer

### 2. Portas (Interfaces)

**Portas de Entrada (Use Cases):**
- `RegistrarProntuarioUseCase` - Registra prontuário em uma consulta
- `ListarConsultasDoDiaUseCase` - Lista consultas agendadas para determinado dia
- `ConsultarHistoricoProntuarioUseCase` - Consulta histórico de prontuários do paciente

**Portas de Saída (Repositories):**
- `PacienteRepository` - Persistência de pacientes
- `ConsultaRepository` - Persistência de consultas
- `ProntuarioRepository` - Persistência de prontuários
- `MedicamentoRepository` - Persistência de medicamentos
- `ExameRepository` - Persistência de exames

### 3. Adaptadores

**Adaptadores de Entrada:**
- `ProntuarioCLIController` - Interface CLI para registro de prontuários

**Adaptadores de Saída:**
- `PacienteRepositoryMemory` - Implementação em memória
- `ConsultaRepositoryMemory` - Implementação em memória
- `ProntuarioRepositoryMemory` - Implementação em memória
- `MedicamentoRepositoryMemory` - Implementação em memória
- `ExameRepositoryMemory` - Implementação em memória
- `NotificacaoService` - Serviço de notificações (Observer)

## Padrões de Projeto Implementados

### 1. Repository Pattern
Isola a lógica de persistência do domínio, permitindo trocar a implementação sem afetar as regras de negócio.

### 2. Observer Pattern
Sistema de notificações automáticas quando uma consulta é realizada. O `NotificacaoService` observa eventos de consultas e envia notificações por email e SMS.

### 3. Dependency Injection
Todos os casos de uso recebem suas dependências via construtor, promovendo baixo acoplamento e facilitando testes.

### 4. Adapter Pattern
Adaptadores isolam detalhes de implementação (CLI, persistência) do núcleo da aplicação.

## Princípios SOLID Aplicados

**Single Responsibility Principle (SRP)**
Cada classe tem uma única responsabilidade bem definida.

**Open/Closed Principle (OCP)**
Sistema aberto para extensão através de interfaces, fechado para modificação.

**Liskov Substitution Principle (LSP)**
Implementações dos repositórios podem ser substituídas sem quebrar o sistema.

**Interface Segregation Principle (ISP)**
Interfaces específicas e coesas para cada caso de uso.

**Dependency Inversion Principle (DIP)**
Dependências apontam para abstrações (interfaces), não para implementações concretas.

## Regras de Negócio

- Uma consulta gera exatamente um prontuário
- Um prontuário pode conter zero ou mais exames
- Um prontuário pode conter zero ou mais prescrições
- Um paciente pode ou não estar vinculado a um plano de saúde
- O sistema identifica automaticamente se é paciente novo
- Notificações automáticas são enviadas ao realizar consulta

## Estrutura do Projeto

```
src/
├── core/
│   ├── domain/          # Entidades e eventos do domínio
│   └── usecase/         # Implementação dos casos de uso
├── ports/
│   ├── in/              # Interfaces dos casos de uso
│   └── out/             # Interfaces dos repositórios
├── adapters/
│   ├── in/cli/          # Adaptador CLI
│   └── out/
│       ├── repository/  # Implementações dos repositórios
│       └── notification/# Serviço de notificações
└── tests/               # Testes automatizados
```

## Compilação e Execução

### Compilar o projeto

```bash
cd src
javac Main.java
```

### Executar os testes

```bash
cd src
java Main
```

## Testes

O projeto inclui testes automatizados para:

- **DomainTest** - Testes das regras de domínio
- **AdapterTest** - Testes dos adaptadores de repositório
- **UseCaseTest** - Testes dos casos de uso
- **ObserverTest** - Testes do padrão Observer

## Benefícios da Arquitetura Hexagonal

1. **Isolamento do Domínio** - Regras de negócio independentes de frameworks
2. **Baixo Acoplamento** - Componentes desacoplados através de interfaces
3. **Alta Coesão** - Cada módulo tem responsabilidade bem definida
4. **Testabilidade** - Fácil criar testes unitários com mocks
5. **Flexibilidade** - Fácil trocar implementações (ex: memória → banco de dados)
6. **Manutenibilidade** - Código organizado e fácil de evoluir

## Evolução Futura

O sistema está preparado para evoluir com:

- Implementação de persistência em banco de dados
- API REST para atendimento online
- Sistema de agendamento de consultas
- Integração com outros sistemas de saúde
- Suporte a múltiplas clínicas e médicos
- Geração de relatórios e estatísticas

## Autores
Jordan Feliphe Rubim , Pedro Lucas Reis e Rodrigo Cesar Costa
