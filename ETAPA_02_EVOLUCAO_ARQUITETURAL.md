# Etapa 02 - Evolução e Justificativas Arquiteturais

## I. Atendimento Online

### Descrição
Implementar funcionalidades de agendamento de consultas, visualização de histórico e pagamentos online.

### Padrões de Projeto Aplicáveis

**1. Strategy Pattern**
Para diferentes métodos de pagamento (cartão de crédito, PIX, boleto).

```java
interface PagamentoStrategy {
    void processar(double valor);
}

class PagamentoCartao implements PagamentoStrategy {
    void processar(double valor) { }
}

class PagamentoPix implements PagamentoStrategy {
    void processar(double valor) { }
}
```

**2. Factory Pattern**
Para criação de diferentes tipos de agendamento (presencial, online, retorno).

```java
interface AgendamentoFactory {
    Consulta criar(LocalDateTime data, Paciente paciente, Medico medico);
}
```

**3. Facade Pattern**
Para simplificar a interface de pagamento integrando múltiplos subsistemas.

```java
class PagamentoFacade {
    void processarPagamentoConsulta(Long consultaId, PagamentoStrategy strategy) { }
}
```

### Princípios SOLID

**Single Responsibility**: Cada strategy de pagamento tem responsabilidade única.

**Open/Closed**: Novos métodos de pagamento podem ser adicionados sem modificar código existente.

**Dependency Inversion**: Casos de uso dependem de interfaces, não de implementações concretas de pagamento.

### Justificativa Arquitetura Hexagonal

A arquitetura hexagonal permite adicionar adaptadores de entrada (API REST, WebSocket) e saída (gateways de pagamento) sem modificar o domínio. As portas de entrada seriam novos use cases como `AgendarConsultaOnlineUseCase` e `ProcessarPagamentoUseCase`.

---

## II. Notificações e Lembretes (IMPLEMENTADO)

### Descrição
Sistema de lembretes de consultas e alertas de retorno médico.

### Padrões de Projeto Implementados

**1. Observer Pattern**
Já implementado através de `ConsultaObserver` e `NotificacaoService`.

```java
interface ConsultaObserver {
    void notificar(ConsultaRealizadaEvent event);
}

class NotificacaoService implements ConsultaObserver {
    void notificar(ConsultaRealizadaEvent event) {
        enviarEmail();
        enviarSMS();
    }
}
```

**2. Template Method Pattern (Proposta de Evolução)**
Para padronizar o fluxo de envio de notificações.

```java
abstract class NotificacaoTemplate {
    final void enviar(Consulta consulta) {
        validar(consulta);
        formatarMensagem(consulta);
        enviarNotificacao();
        registrarLog();
    }

    protected abstract void enviarNotificacao();
}

class EmailNotificacao extends NotificacaoTemplate {
    protected void enviarNotificacao() { }
}

class SMSNotificacao extends NotificacaoTemplate {
    protected void enviarNotificacao() { }
}
```

**3. Chain of Responsibility (Proposta)**
Para diferentes canais de notificação com fallback.

```java
abstract class NotificacaoHandler {
    protected NotificacaoHandler proximo;

    void setProximo(NotificacaoHandler handler) {
        this.proximo = handler;
    }

    abstract void enviar(Notificacao notificacao);
}
```

### Princípios SOLID

**Single Responsibility**: `NotificacaoService` tem responsabilidade única de notificar.

**Open/Closed**: Novos observers podem ser adicionados sem modificar código existente.

**Liskov Substitution**: Qualquer implementação de `ConsultaObserver` pode ser usada.

**Dependency Inversion**: `Consulta` depende da interface `ConsultaObserver`, não de implementações concretas.

### Justificativa Arquitetura Hexagonal

O padrão Observer se encaixa perfeitamente como adaptador de saída. O domínio dispara eventos sem conhecer os detalhes de implementação (email, SMS, push notification). Novos canais podem ser adicionados implementando a interface `ConsultaObserver`.

### Implementação Atual

O sistema já implementa:
- Notificações automáticas ao realizar consulta
- Envio simulado de email e SMS
- Sistema extensível para adicionar novos canais

---

## III. Compartilhamento e Integração

### Descrição
Integração com outros sistemas de saúde e plataformas externas.

### Padrões de Projeto Aplicáveis

**1. Adapter Pattern**
Para integrar com diferentes sistemas externos mantendo interface única.

```java
interface SistemaExternoAdapter {
    void sincronizarProntuario(Prontuario prontuario);
}

class SUSAdapter implements SistemaExternoAdapter {
    void sincronizarProntuario(Prontuario prontuario) { }
}

class PlanoSaudeAdapter implements SistemaExternoAdapter {
    void sincronizarProntuario(Prontuario prontuario) { }
}
```

**2. Bridge Pattern**
Para separar abstração de implementação ao integrar com múltiplos sistemas.

```java
interface IntegracaoImplementacao {
    void enviarDados(String dados);
}

class IntegracaoREST implements IntegracaoImplementacao { }
class IntegracaoSOAP implements IntegracaoImplementacao { }

abstract class Integracao {
    protected IntegracaoImplementacao implementacao;
    abstract void sincronizar(Object dados);
}
```

**3. Proxy Pattern**
Para controlar acesso a sistemas externos e implementar cache.

```java
class SistemaExternoProxy implements SistemaExternoAdapter {
    private SistemaExternoAdapter sistemaReal;
    private Cache cache;

    void sincronizarProntuario(Prontuario prontuario) {
        if (!cache.contem(prontuario)) {
            sistemaReal.sincronizarProntuario(prontuario);
            cache.adicionar(prontuario);
        }
    }
}
```

### Princípios SOLID

**Interface Segregation**: Cada sistema externo tem interface específica para suas necessidades.

**Dependency Inversion**: Use cases dependem de interfaces de integração, não de implementações específicas.

**Single Responsibility**: Cada adapter é responsável por integrar com um sistema específico.

### Justificativa Arquitetura Hexagonal

Sistemas externos são adaptadores de saída. O domínio não conhece detalhes de integração. Portas de saída como `SistemaExternoPort` definem contratos, e múltiplos adaptadores podem implementá-las (SUS, planos de saúde, HL7, FHIR).

---

## IV. Suporte a Múltiplas Clínicas e Médicos

### Descrição
Escalabilidade do modelo para suportar múltiplas clínicas e médicos com isolamento de regras de negócio.

### Padrões de Projeto Aplicáveis

**1. Multitenancy Pattern**
Para isolar dados de diferentes clínicas.

```java
class TenantContext {
    private static ThreadLocal<Long> clinicaId = new ThreadLocal<>();

    static void setClinicaId(Long id) {
        clinicaId.set(id);
    }

    static Long getClinicaId() {
        return clinicaId.get();
    }
}

interface TenantRepository {
    List<T> buscarPorClinica(Long clinicaId);
}
```

**2. Strategy Pattern**
Para diferentes regras de negócio por clínica.

```java
interface RegraClinicaStrategy {
    boolean permitirAgendamento(Consulta consulta);
    double calcularValorConsulta(Consulta consulta);
}

class RegraClinicaParticular implements RegraClinicaStrategy { }
class RegraClinicaConveniada implements RegraClinicaStrategy { }
```

**3. Template Method Pattern**
Para fluxos comuns com variações por clínica.

```java
abstract class FluxoConsultaTemplate {
    final void realizarConsulta(Consulta consulta) {
        validarPreRequisitos(consulta);
        registrarPresenca();
        realizarAtendimento(consulta);
        finalizarConsulta();
        acoesPosConsulta(consulta);
    }

    protected abstract void acoesPosConsulta(Consulta consulta);
}
```

**4. Composite Pattern**
Para hierarquia de clínicas e departamentos.

```java
interface UnidadeAtendimento {
    void adicionarMedico(Medico medico);
    List<Medico> listarMedicos();
}

class Clinica implements UnidadeAtendimento {
    private List<Departamento> departamentos;
}

class Departamento implements UnidadeAtendimento {
    private List<Medico> medicos;
}
```

### Princípios SOLID

**Open/Closed**: Novas clínicas podem ser adicionadas sem modificar código existente.

**Single Responsibility**: Cada strategy de regra de clínica tem responsabilidade única.

**Liskov Substitution**: Diferentes implementações de regras de clínica são intercambiáveis.

**Dependency Inversion**: Casos de uso dependem de interfaces de regras, não de implementações específicas.

### Justificativa Arquitetura Hexagonal

A arquitetura hexagonal facilita multitenancy através de:

1. **Isolamento do Domínio**: Regras de negócio específicas de cada clínica ficam em strategies injetadas via portas.

2. **Adaptadores Configuráveis**: Cada clínica pode ter adaptadores específicos (ex: diferentes sistemas de pagamento).

3. **Portas Flexíveis**: Interfaces permitem comportamentos diferentes por tenant sem modificar o core.

4. **Casos de Uso Reutilizáveis**: Mesmos use cases servem múltiplas clínicas com diferentes configurações.

### Exemplo de Implementação

```java
interface ClinicaRepository extends TenantRepository<Clinica> {
    Optional<Clinica> buscarPorId(Long id);
}

class AgendarConsultaUseCaseImpl implements AgendarConsultaUseCase {
    private ClinicaRepository clinicaRepository;

    void agendar(Long clinicaId, AgendamentoDTO dto) {
        Clinica clinica = clinicaRepository.buscarPorId(clinicaId)
            .orElseThrow();

        RegraClinicaStrategy regra = clinica.getRegras();
        if (regra.permitirAgendamento(dto)) {
        }
    }
}
```

---

## Benefícios da Arquitetura Hexagonal para Evolução

1. **Isolamento**: Domínio protegido de mudanças tecnológicas
2. **Extensibilidade**: Novos adaptadores sem modificar o core
3. **Testabilidade**: Fácil criar mocks de adaptadores
4. **Flexibilidade**: Trocar implementações sem impacto no domínio
5. **Manutenibilidade**: Separação clara de responsabilidades
6. **Escalabilidade**: Adicionar novos recursos mantendo coesão

## Conclusão

A Arquitetura Hexagonal provou ser ideal para evolução do sistema de consultas médicas. As quatro funcionalidades propostas podem ser implementadas através de:

- Novos casos de uso (portas de entrada)
- Novos adaptadores (implementações de portas de saída)
- Padrões de projeto complementares (Strategy, Factory, Observer, etc.)
- Sem modificar o domínio existente

Isso demonstra os princípios Open/Closed e Dependency Inversion em ação, permitindo que o sistema cresça de forma sustentável e manutenível.
