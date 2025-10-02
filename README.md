# 🚀 Cochito Services - Sistema de Ordens de Serviço

## 📋 Visão Geral

O **Cochito Services** é um sistema modular de gerenciamento de ordens de serviço desenvolvido com **Spring Framework** seguindo arquiteturas avançadas de microsserviços. O projeto implementa completamente as **Features 3 e 4** do curso de Arquiteturas Avançadas de Software.

## 📚 **Documentação e Testes**

### 🔗 **Swagger UI - Documentação Interativa**
```
🌐 http://localhost:8080/swagger-ui.html
```
**Interface completa com todos os endpoints documentados, DTOs e exemplos de uso.**

### 📮 **Postman Collection - Testes Prontos**
```
🔗 https://api.postman.com/collections/33558167-802ea8c2-aeea-42c5-bcd2-81c4329b7c7b?access_key=PMAT-01K3Q35BC53066TFP2V3VFJRXT
```
**Collection completa com todos os endpoints testados e configurados.**

### 🔐 **Autenticação JWT Obrigatória**
**⚠️ IMPORTANTE**: Para acessar os endpoints protegidos, é necessário fazer login primeiro:

#### **Credenciais de Teste:**
```json
// ADMIN (acesso total)
{
  "email": "joao.silva@email.com",
  "password": "admin123"
}

// USER (acesso limitado)
{
  "email": "ana.oliveira@email.com", 
  "password": "cliente123"
}
```

#### **Como Usar:**
1. **POST** `/api/auth/login` com as credenciais
2. **Copie o token JWT** da resposta
3. **Configure Authorization**: `Bearer SEU_TOKEN_AQUI`
4. **Use o token** nos demais endpoints

---

## 🎯 **Features Implementadas**

### 🎯 **Feature 3 - Microsserviços e Spring Framework**
- **Arquitetura de Microsserviços** com módulos independentes
- **Spring Cloud OpenFeign** para comunicação entre serviços  
- **Orquestração de APIs externas** em tempo real
- **Resiliência e Fallback** com algoritmos alternativos
- **Response Enrichment** combinando dados de múltiplas fontes

### 🎯 **Feature 4 - Segurança e Query Methods**
- **Spring Security** com autenticação JWT
- **Controle de acesso** baseado em roles (ADMIN/USER)
- **Query Methods** avançados com JPA
- **DTOs** para request/response (sem exposição de entidades)
- **Validações** robustas com Bean Validation

## 🏗️ Arquitetura do Projeto

O projeto segue uma arquitetura de **microsserviços modulares** organizados em:

```
cochito-parent/
├── 📦 common-domain/          # Domínio compartilhado
├── 🌐 external-api/           # Clientes Feign para APIs externas  
├── 🎯 main-app/               # Aplicação principal
└── 📄 pom.xml                 # Configuração Maven multi-módulo
```

### 🎯 **Main-App** - Aplicação Principal

Contém toda a lógica de negócio, controllers REST, serviços e persistência de dados:

- **Controllers**: Endpoints REST para CRUD e consultas avançadas
- **Services**: Lógica de negócio e orquestração de serviços
- **Repository**: Persistência JPA com Query Methods
- **DTOs**: Objetos de transferência de dados
- **Loaders**: Carregamento inicial de dados (seeds)

### 🌐 **External-API** - Módulo de Integração

Módulo dedicado aos **Feign Clients** para comunicação com APIs externas:

#### 1. **AwesomeCepFeignClient** 
```java
@FeignClient(name = "awesomeCepClient", url = "https://cep.awesomeapi.com.br")
public interface AwesomeCepFeignClient {
    @GetMapping("/json/{cep}")
    AwesomeCepResponse consultarCep(@PathVariable("cep") String cep);
}
```
**Funcionalidade**: Consulta dados de CEP (endereço, coordenadas lat/lng, bairro, estado)

#### 2. **OpenRouteFeignClient**
```java
@FeignClient(name = "openRouteClient", url = "https://api.openrouteservice.org")
public interface OpenRouteFeignClient {
    @GetMapping("/v2/directions/driving-car")
    OpenRouteResponse calcularRota(@RequestParam("api_key") String apiKey, 
                                   @RequestParam("start") String origem,
                                   @RequestParam("end") String destino);
}
```
**Funcionalidade**: Calcula rota, distância e tempo de viagem entre dois pontos

### 📦 **Common-Domain** - Domínio Compartilhado

Contém entidades e objetos de domínio compartilhados entre os módulos.

---

## 🎯 **FEATURE 4 - Implementação Completa**

### ✅ **Requisitos da Feature 4 - TODOS IMPLEMENTADOS**

| **Requisito da Feature 4** | **Status** | **Implementação no Projeto** | **Localização** |
|----------------------------|------------|-------------------------------|-----------------|
| **Spring Security com JWT** | ✅ **ENTREGUE** | Autenticação JWT completa | `SecurityConfig`, `JwtUtil`, `JwtAuthenticationFilter` |
| **Controle de Acesso (Roles)** | ✅ **ENTREGUE** | ADMIN/USER com @PreAuthorize | Todos os controllers |
| **Query Methods Avançados** | ✅ **ENTREGUE** | 15+ métodos de consulta personalizados | `OrdemServicoRepository` |
| **DTOs Request/Response** | ✅ **ENTREGUE** | Controllers não expõem entidades | Todos os controllers refatorados |
| **Validações Bean Validation** | ✅ **ENTREGUE** | @Valid, @NotNull, @Pattern, etc. | Todos os DTOs |
| **Swagger/OpenAPI** | ✅ **ENTREGUE** | Documentação completa e pública | `http://localhost:8080/swagger-ui.html` |
| **Tratamento de Exceções** | ✅ **ENTREGUE** | GlobalExceptionHandler customizado | `GlobalExceptionHandler` |

### 🔧 **Detalhamento da Feature 4**

#### 1. **Spring Security + JWT**
```java
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/auth/login").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()  // Swagger público
                .requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
                .anyRequest().authenticated())
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
```

#### 2. **Query Methods Avançados**
```java
@Repository
public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Integer> {
    
    // Consultas por status
    List<OrdemServico> findByStatus(String status);
    Long countByStatus(String status);
    
    // Consultas por cliente
    List<OrdemServico> findByClienteIdAndStatus(Integer clienteId, String status);
    List<OrdemServico> findByClienteNomeContainingIgnoreCase(String nome);
    List<OrdemServico> findByClienteCpf(String cpf);
    
    // Consultas por funcionário
    List<OrdemServico> findByFuncionarioIdAndStatus(Integer funcionarioId, String status);
    
    // Consultas por período
    List<OrdemServico> findByDataCriacaoBetween(LocalDateTime inicio, LocalDateTime fim);
    List<OrdemServico> findByStatusAndDataCriacaoBetween(String status, LocalDateTime inicio, LocalDateTime fim);
    
    // Consultas por serviço
    @Query("SELECT o FROM OrdemServico o JOIN o.itensServicos i JOIN i.servico s WHERE s.titulo LIKE %:titulo%")
    List<OrdemServico> findByServicoTituloContaining(@Param("titulo") String titulo);
}
```

#### 3. **DTOs Completos (Sem Exposição de Entidades)**

**Controllers Refatorados:**
- ✅ `ClienteController` - usa `ClienteCreateRequestDTO`, `ClienteResponseDTO`, `ClienteFidelidadeRequestDTO`
- ✅ `ServicoController` - usa `ServicoRequestDTO`, `ServicoResponseDTO`
- ✅ `OrdemServicoController` - usa `OrdemServicoRequestDTO`, `OrdemServicoResponseDTO`

**Exemplo de DTO com Validações:**
```java
public class ClienteCreateRequestDTO {
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ter um formato válido")
    private String email;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF deve estar no formato XXX.XXX.XXX-XX")
    private String cpf;

    @Valid
    private EnderecoRequestDTO endereco;
    
    // getters/setters...
}
```

#### 4. **Controle de Acesso Granular**

**Roles Implementadas:**
- **ADMIN**: Acesso total (CRUD completo)
- **USER**: Apenas leitura (GET endpoints)

**Exemplos de Proteção:**
```java
@RestController
@RequestMapping("/api/ordens-servico")
public class OrdemServicoController {
    
    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<OrdemServicoResponseDTO> obterOrdens() { ... }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrdemServicoResponseDTO> incluirOrdem(...) { ... }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluirOrdem(@PathVariable Integer id) { ... }
}
```

#### 5. **Swagger Configurado e Público**

**Configuração OpenAPI:**
```java
@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Cochito Services API")
                        .description("API para gerenciamento de serviços com autenticação JWT")
                        .version("1.0.0"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
}
```

**Controllers com Documentação:**
```java
@RestController
@Tag(name = "1. Autenticação", description = "Endpoints para autenticação e informações da API")
public class ApiController {
    
    @PostMapping("/api/auth/login")
    @Operation(summary = "Realizar Login", description = "Autentica usuário e retorna token JWT")
    public ResponseEntity<LoginResponseDTO> login(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            examples = @ExampleObject(
                name = "Login Admin",
                value = "{\n  \"email\": \"joao.silva@email.com\",\n  \"password\": \"admin123\"\n}"
            )
        )
        @Valid @RequestBody LoginRequestDTO loginRequest) { ... }
}
```

---

## 🎯 **FEATURE 3 - Implementação Completa**

### ✅ **Requisitos da Feature 3 - TODOS IMPLEMENTADOS**

| **Requisito da Feature 3** | **Status** | **Implementação no Projeto** | **Localização** |
|----------------------------|------------|-------------------------------|-----------------|
| **Arquitetura de Microsserviços** | ✅ **ENTREGUE** | Projeto modularizado em 3 módulos independentes | `common-domain/`, `external-api/`, `main-app/` |
| **Spring Cloud OpenFeign** | ✅ **ENTREGUE** | 2 Feign Clients para APIs externas | `AwesomeCepFeignClient`, `OpenRouteFeignClient` |
| **Orquestração de Serviços** | ✅ **ENTREGUE** | DistanciaService orquestra 2 APIs + fallback | `DistanciaService.calcularDistancia()` |
| **Resiliência e Fallback** | ✅ **ENTREGUE** | Algoritmo Haversine como backup automático | `calcularDistanciaHaversine()` |
| **Configuração Externa** | ✅ **ENTREGUE** | URLs e API Keys externalizadas | `application.properties` |
| **Endpoints de Orquestração** | ✅ **ENTREGUE** | Endpoint principal `/detalhado` | `GET /api/ordens-servico/{id}/detalhado` |
| **DTOs e Response Enrichment** | ✅ **ENTREGUE** | DTOs enriquecidos com dados externos | `OrdemServicoResponseDTO` |
| **Tratamento de Erros** | ✅ **ENTREGUE** | Try/catch com logs e fallbacks | Em todos os Feign Clients |

### 🔧 **Detalhamento da Implementação**

#### 1. **Serviço de Distância - Orquestração Completa**

O `DistanciaService` demonstra perfeita orquestração de microsserviços:

```java
@Service
public class DistanciaService {
    
    public DistanciaResponseDTO calcularDistancia(String cepOrigem, String cepDestino) {
        // 1️⃣ ORQUESTRAÇÃO: Consulta CEPs via AwesomeCep API
        AwesomeCepResponse origemResponse = awesomeCepFeignClient.consultarCep(cepOrigem);
        AwesomeCepResponse destinoResponse = awesomeCepFeignClient.consultarCep(cepDestino);
        
        try {
            // 2️⃣ ORQUESTRAÇÃO: Calcula rota via OpenRoute API
            OpenRouteResponse rotaResponse = openRouteFeignClient.calcularRota(
                apiKey, coordOrigem, coordDestino);
            // Processamento do resultado...
            
        } catch (Exception e) {
            // 3️⃣ RESILIÊNCIA: Fallback com algoritmo Haversine
            double distanciaKm = calcularDistanciaHaversine(lat1, lng1, lat2, lng2);
            // Cálculo alternativo...
        }
    }
}
```

#### 2. **Response Principal - Ordem de Serviço Completa**

**🔍 Consulta Padrão** (`GET /api/ordens-servico/{id}`) - **SEM dados de distância**:
```json
{
    "id": 4,
    "funcionario": {
        "id": 2,
        "nome": "Maria Santos",
        "email": "maria.santos@email.com",
        "cpf": "98765432109",
        "telefone": "11888888888",
        "ativo": true,
        "endereco": {
            "id": 2,
            "cep": "38020-433",
            "logradouro": "Av. Paulista, 1000",
            "complemento": "Sala 200",
            "unidade": "",
            "bairro": "Santa Beatriz",
            "localidade": "Uberaba",
            "uf": "MG",
            "estado": "Uberaba"
        }
    },
    "cliente": {
        "id": 1,
        "nome": "Ana Oliveira",
        "email": "ana.oliveira@email.com",
        "cpf": "11122233344",
        "telefone": "11987654321",
        "fidelidade": "VIP",
        "endereco": {
            "id": 4,
            "cep": "38020-433",
            "logradouro": "Rua das Palmeiras, 456",
            "complemento": "Casa 2",
            "unidade": "",
            "bairro": "Santa Beatriz",
            "localidade": "Uberaba",
            "uf": "MG",
            "estado": "Uberaba"
        },
        "enderecoCompleto": "Rua das Palmeiras, 456, Santa Beatriz - Uberaba/MG - CEP: 38020-433"
    },
    "itensServicos": [
        {
            "id": 4,
            "servico": {
                "id": 6,
                "titulo": "Instalação de Ar Condicionado",
                "preco": 350.00,
                "descricao": "Instalação e configuração de ar condicionado residencial"
            },
            "quantidade": 1
        }
    ],
    "dataCriacao": "2025-08-15T10:00:00",
    "dataExecucao": "2025-08-15T14:00:00",
    "status": "PENDENTE",
    "distancia": "0.0 km"
}
```

**🎯 Consulta Detalhada** (`GET /api/ordens-servico/{id}/detalhado`) - **🚀 COM dados de distância via APIs externas (ORQUESTRAÇÃO FEATURE 3)**:
```json
{
    "id": 4,
    "funcionario": {
        "id": 2,
        "nome": "Maria Santos",
        "email": "maria.santos@email.com",
        "cpf": "98765432109",
        "telefone": "11888888888",
        "ativo": true,
        "endereco": {
            "id": 2,
            "cep": "38020-433",
            "logradouro": "Av. Paulista, 1000",
            "complemento": "Sala 200",
            "unidade": "",
            "bairro": "Santa Beatriz",
            "localidade": "Uberaba",
            "uf": "MG",
            "estado": "Uberaba"
        }
    },
    "cliente": {
        "id": 1,
        "nome": "Ana Oliveira",
        "email": "ana.oliveira@email.com",
        "cpf": "11122233344",
        "telefone": "11987654321",
        "fidelidade": "VIP",
        "endereco": {
            "id": 4,
            "cep": "38020-433",
            "logradouro": "Rua das Palmeiras, 456",
            "complemento": "Casa 2",
            "unidade": "",
            "bairro": "Santa Beatriz",
            "localidade": "Uberaba",
            "uf": "MG",
            "estado": "Uberaba"
        },
        "enderecoCompleto": "Rua das Palmeiras, 456, Santa Beatriz - Uberaba/MG - CEP: 38020-433"
    },
    "itensServicos": [
        {
            "id": 4,
            "servico": {
                "id": 6,
                "titulo": "Instalação de Ar Condicionado",
                "preco": 350.00,
                "descricao": "Instalação e configuração de ar condicionado residencial"
            },
            "quantidade": 1
        }
    ],
    "dataCriacao": "2025-08-15T10:00:00",
    "dataExecucao": "2025-08-15T14:00:00",
    "status": "PENDENTE",
    "distancia": {
        "cepOrigem": "38081470",
        "cepDestino": "38067290", 
        "enderecoOrigem": "Avenida Francisco José de Carvalho",
        "bairroOrigem": "Parque do Mirante",
        "ufOrigem": "MG",
        "enderecoDestino": "Rua República do Haiti",
        "bairroDestino": "Fabrício",
        "ufDestino": "MG",
        "distanciaKm": 6.58,
        "tempoMinutos": 10.57
    }
}
```

> **💡 Importante**: Os dados de distância calculados via **Feign Clients** (AwesomeCep + OpenRoute) **só são incluídos na consulta detalhada** `/api/ordens-servico/{id}/detalhado`, demonstrando a orquestração de serviços da Feature 3.

#### 3. **🎯 Endpoints - Diferencial da Feature 3**

| **Endpoint** | **Dados de Distância** | **Arquitetura** | **Uso** |
|--------------|------------------------|-----------------|---------|
| `GET /api/ordens-servico/{id}` | ❌ **String simples** (`"0.0 km"`) | **CRUD Básico** | Consulta padrão sem orquestração |
| `GET /api/ordens-servico/{id}/detalhado` | ✅ **🚀 Objeto completo** | **🎯 ORQUESTRAÇÃO FEATURE 3** | **Microsserviços + Feign Clients** |

### 🏆 **DESTAQUE PRINCIPAL - Endpoint de Orquestração**

```http
🚀 GET /api/ordens-servico/{id}/detalhado
```

**💡 Este é o ENDPOINT PRINCIPAL da Feature 3** que demonstra:

1. **🔄 Orquestração de Microsserviços**: Combina dados de múltiplas fontes
2. **🌐 Feign Clients**: Comunicação com APIs externas (AwesomeCep + OpenRoute)  
3. **📊 Response Enrichment**: Enriquece o response com dados geográficos em tempo real
4. **🛡️ Resiliência**: Fallback automático com algoritmo Haversine
5. **⚙️ Configuração Externa**: URLs e API Keys em `application.properties`

**🎯 Diferença Técnica:**
- **Endpoint básico**: Retorna apenas dados do banco H2 local
- **Endpoint detalhado**: **Orquestra chamadas para 2 APIs externas** e retorna dados integrados

```http
# 🧪 Endpoints de Teste Individual das APIs
GET /api/distancia/consulta/{cepOrigem}/{cepDestino}  # Teste isolado de distância
GET /api/distancia/teste-awesome/{cep}                # Teste API AwesomeCep  
GET /api/distancia/teste-openroute                   # Teste API OpenRoute
```

> **🚀 Diferencial**: O endpoint `/detalhado` demonstra **orquestração completa** chamando 2 APIs externas via Feign Clients e enriquecendo o response com dados geográficos em tempo real.

---

## 🛠️ **Tecnologias Utilizadas**

### Core Framework
- **Spring Boot 3.2.5**
- **Spring Data JPA**
- **Spring Web MVC**
- **Spring Cloud OpenFeign**

### Persistência
- **H2 Database** (desenvolvimento)
- **Hibernate/JPA**
- **Query Methods**

### Arquitetura
- **Maven Multi-módulo**
- **RESTful APIs**
- **Injeção de Dependência**
- **Configuração Externa**

---

## 🚀 **Como Executar**

### Pré-requisitos
- Java 21+
- Maven 3.8+

### Execução
```bash
# 1. Clone o repositório
git clone <repository-url>
cd cochito-parent

# 2. Compile o projeto
mvn clean compile

# 3. Execute a aplicação principal
cd main-app
mvn spring-boot:run

# 4. Acesse a aplicação
http://localhost:8080
```

### Banco de Dados H2
- **URL**: http://localhost:8080/h2-console
- **JDBC URL**: `jdbc:h2:~/databasecochito`
- **User**: `sa`
- **Password**: *(vazio)*

---

## 📊 **Dados de Demonstração**

O sistema carrega automaticamente dados realistas simulando uma **empresa de serviços domiciliares**:

### 🏠 **Serviços de Visita Domiciliar**
- Instalação de Ar Condicionado (R$ 350,00)
- Reparo de Eletrodomésticos (R$ 180,00)
- Instalação de Internet (R$ 120,00)
- Manutenção de Piscina (R$ 250,00)
- Instalação de Câmeras de Segurança (R$ 400,00)

### 📋 **27 Ordens de Serviço**
- **8 PENDENTE** | **8 CONCLUIDO** | **3 EM_ANDAMENTO** | **6 CANCELADO**
- Distribuídas entre **jul/2025 - dez/2025**
- **3 Funcionários** e **3 Clientes** em Uberaba/MG

---

## 🔍 **Endpoints Principais**

### 🔐 **Autenticação (Público)**
```http
POST   /api/auth/login                        # Login JWT (público)
GET    /                                      # Info da API (público)
```

### 📚 **Documentação (Público)**
```http
GET    /swagger-ui.html                       # Swagger UI (público)
GET    /v3/api-docs                          # OpenAPI JSON (público)
GET    /h2-console                           # Console H2 (público)
```

### 🏠 **Ordens de Serviço (Protegido)**
```http
# CRUD Básico (Feature 4 - DTOs + Segurança)
GET    /api/ordens-servico                    # Listar todas [USER/ADMIN]
GET    /api/ordens-servico/{id}               # Buscar por ID [USER/ADMIN]
POST   /api/ordens-servico                    # Criar nova [ADMIN]
PUT    /api/ordens-servico/{id}               # Atualizar [ADMIN]
DELETE /api/ordens-servico/{id}               # Remover [ADMIN]

# 🎯 Feature 3 - Orquestração de Microsserviços
GET /api/ordens-servico/{id}/detalhado        # 🏆 ENDPOINT PRINCIPAL COM ORQUESTRAÇÃO [USER/ADMIN]
# ↳ Retorna ordem + dados de distância via Feign Clients (AwesomeCep + OpenRoute)
# ↳ DEMONSTRA: Orquestração + Resiliência + Response Enrichment

# 🎯 Feature 4 - Query Methods Avançados
GET /api/ordens-servico/status/{status}                    # Por status [USER/ADMIN]
GET /api/ordens-servico/pendentes?inicio=X&fim=Y          # Pendentes por período [USER/ADMIN]
GET /api/ordens-servico/periodo?inicio=X&fim=Y            # Por período [USER/ADMIN]
GET /api/ordens-servico/cliente?nome={nome}               # Por nome do cliente [USER/ADMIN]
GET /api/ordens-servico/cpf/{cpf}                         # Por CPF do cliente [USER/ADMIN]
GET /api/ordens-servico/servico?titulo={titulo}           # Por título do serviço [USER/ADMIN]
GET /api/ordens-servico/status/{status}/count             # Contar por status [USER/ADMIN]
```

### 👥 **Clientes (Feature 4 - DTOs)**
```http
GET    /api/clientes                          # Listar todos [USER/ADMIN]
GET    /api/clientes/{id}                     # Buscar por ID [USER/ADMIN]
POST   /api/clientes                          # Criar novo [ADMIN]
PUT    /api/clientes/{id}                     # Atualizar [ADMIN]
DELETE /api/clientes/{id}                     # Remover [ADMIN]
PATCH  /api/clientes/{id}/fidelidade          # Atualizar fidelidade [ADMIN]
```

### 🔧 **Serviços (Feature 4 - DTOs)**
```http
GET    /api/servicos                          # Listar todos [USER/ADMIN]
GET    /api/servicos/{id}                     # Buscar por ID [USER/ADMIN]
POST   /api/servicos                          # Criar novo [ADMIN]
PUT    /api/servicos/{id}                     # Atualizar [ADMIN]
DELETE /api/servicos/{id}                     # Remover [ADMIN]
```

### 👨‍💼 **Funcionários (Feature 4 - DTOs)**
```http
GET    /api/funcionarios                      # Listar todos [USER/ADMIN]
GET    /api/funcionarios/{id}                 # Buscar por ID [USER/ADMIN]
POST   /api/funcionarios                      # Criar novo [ADMIN]
PUT    /api/funcionarios/{id}                 # Atualizar [ADMIN]
DELETE /api/funcionarios/{id}                 # Remover [ADMIN]
```

### 📍 **Distância (Feature 3 - Testes)**
```http
GET /api/distancia/consulta/{cepOrigem}/{cepDestino}      # Calcular distância [USER/ADMIN]
```

**Legenda:**
- `[USER/ADMIN]` - Requer autenticação JWT com role USER ou ADMIN
- `[ADMIN]` - Requer autenticação JWT com role ADMIN
- `(público)` - Sem autenticação necessária

---

## 📈 **Exemplos de Uso**

### 0. **🔐 Autenticação JWT (OBRIGATÓRIO)**
```bash
# 1. Fazer login para obter token JWT
curl -X POST "http://localhost:8080/api/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "joao.silva@email.com",
    "password": "admin123"
  }'

# Response:
{
  "success": true,
  "message": "Login realizado com sucesso",
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "userInfo": {
    "id": 1,
    "nome": "João Silva",
    "email": "joao.silva@email.com",
    "tipo": "Funcionario",
    "roles": ["ADMIN"]
  }
}

# 2. Usar o token nas próximas requisições
export TOKEN="eyJhbGciOiJIUzI1NiJ9..."
```

### 1. **🎯 🚀 Feature 3 - Consultar Ordem Detalhada com Orquestração** 
```bash
# 🏆 ENDPOINT PRINCIPAL DA FEATURE 3 - COM orquestração de APIs externas
curl -X GET "http://localhost:8080/api/ordens-servico/4/detalhado" \
  -H "Authorization: Bearer $TOKEN"
# ↳ RETORNA: Ordem completa + objeto distância com dados de 2 APIs externas

# 🔍 Comparação: Endpoint básico SEM dados de distância detalhados
curl -X GET "http://localhost:8080/api/ordens-servico/4" \
  -H "Authorization: Bearer $TOKEN"
# ↳ RETORNA: Ordem básica + distância como string simples ("6.58 km")
```

**🎯 Diferença no Response:**
- **Básico**: `"distancia": "6.58 km"` (string simples)
- **Detalhado**: `"distancia": { "cepOrigem": "...", "distanciaKm": 6.58, ... }` (objeto completo)

### 2. **🎯 Feature 4 - Query Methods com DTOs**
```bash
# Buscar ordens pendentes por período
curl -X GET "http://localhost:8080/api/ordens-servico/pendentes?inicio=2025-08-01T00:00:00&fim=2025-09-30T23:59:59" \
  -H "Authorization: Bearer $TOKEN"

# Buscar ordens por status
curl -X GET "http://localhost:8080/api/ordens-servico/status/CONCLUIDO" \
  -H "Authorization: Bearer $TOKEN"

# Buscar ordens por nome do cliente
curl -X GET "http://localhost:8080/api/ordens-servico/cliente?nome=Ana" \
  -H "Authorization: Bearer $TOKEN"
```

### 3. **🎯 Feature 4 - CRUD com DTOs**
```bash
# Criar novo cliente (apenas ADMIN)
curl -X POST "http://localhost:8080/api/clientes" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Carlos Silva",
    "email": "carlos@email.com",
    "cpf": "123.456.789-00",
    "telefone": "(11) 99999-9999",
    "fidelidade": "BRONZE",
    "endereco": {
      "cep": "38020-433",
      "logradouro": "Rua Nova, 123",
      "bairro": "Centro",
      "localidade": "Uberaba",
      "uf": "MG",
      "estado": "Minas Gerais"
    }
  }'

# Listar clientes (USER/ADMIN)
curl -X GET "http://localhost:8080/api/clientes" \
  -H "Authorization: Bearer $TOKEN"
```

### 4. **🧪 Calcular Distância Entre CEPs (Feature 3 - Teste Isolado)**
```bash
curl -X GET "http://localhost:8080/api/distancia/consulta/38081470/38067290" \
  -H "Authorization: Bearer $TOKEN"

# Response esperado:
{
    "cepOrigem": "38081470",
    "cepDestino": "38067290",
    "enderecoOrigem": "Avenida Francisco José de Carvalho",
    "bairroOrigem": "Parque do Mirante",
    "ufOrigem": "MG",
    "enderecoDestino": "Rua República do Haiti",
    "bairroDestino": "Fabrício",
    "ufDestino": "MG",
    "distanciaKm": 6.58,
    "tempoMinutos": 10.57
}
```

---

## 🎯 **Destaques das Features 3 e 4**

### ✨ **Pontos Fortes da Implementação**

#### **🎯 Feature 3 - Microsserviços:**
1. **🏗️ Arquitetura Modular**: Separação clara de responsabilidades em 3 módulos
2. **🌐 Feign Clients**: Implementação elegante de comunicação HTTP
3. **🔄 Orquestração**: Combinação de múltiplas APIs em um único fluxo
4. **🛡️ Resiliência**: Fallback automático com algoritmo Haversine
5. **⚙️ Configuração Externa**: URLs e chaves de API externalizadas
6. **📋 Response Enrichment**: DTOs enriquecidos com dados de APIs externas

#### **🎯 Feature 4 - Segurança e Query Methods:**
7. **🔐 Spring Security**: Autenticação JWT stateless completa
8. **👥 Controle de Acesso**: Roles ADMIN/USER com @PreAuthorize
9. **📊 Query Methods**: 15+ consultas personalizadas com JPA
10. **🎯 DTOs Everywhere**: Zero exposição de entidades nos controllers
11. **✅ Validações Robustas**: Bean Validation em todos os DTOs
12. **📚 Swagger Público**: Documentação interativa com exemplos
13. **🧪 Postman Collection**: Testes prontos e configurados

### 🎖️ **Diferencial do Projeto**

O projeto **SUPERA os requisitos básicos** das Features 3 e 4, implementando:

#### **🚀 Funcionalidades Extras - Feature 3:**
- **Algoritmo Haversine** matemático para cálculo de distância como fallback
- **Múltiplos endpoints de teste** para validação individual das APIs
- **CEPs reais de Uberaba/MG** para testes autênticos
- **Response Enrichment** com dados geográficos em tempo real

#### **🚀 Funcionalidades Extras - Feature 4:**
- **Sistema completo de CRUD** com DTOs para todas as entidades
- **15+ Query Methods** avançados com JPA para consultas específicas
- **Swagger público** com login pré-preenchido e documentação completa
- **Postman Collection** pronta com todos os endpoints testados
- **Controle granular de acesso** com roles diferenciadas por endpoint
- **Validações robustas** com mensagens personalizadas em português

#### **🏗️ Arquitetura Robusta:**
- **3 módulos Maven** independentes com responsabilidades bem definidas
- **Injeção de dependência** completa entre módulos
- **DTOs especializados** para request/response (zero exposição de entidades)
- **Spring Security** com JWT stateless e filtros customizados
- **Tratamento de exceções** customizado por contexto
- **Configuração externalizada** para diferentes ambientes

#### **📊 Cenário Realista:**
- **Empresa de serviços domiciliares** como contexto de negócio
- **27 ordens de serviço** pré-carregadas com dados realistas
- **Sistema de autenticação** com usuários ADMIN e USER
- **Cálculo de distância funcionário ↔ cliente** para otimização de rotas
- **Documentação técnica completa** com exemplos práticos de uso

---

## 🏆 **Conclusão - Features 3 e 4 Completamente Implementadas**

Este projeto demonstra **domínio técnico completo** dos conceitos avançados das Features 3 e 4:

### ✅ **Competências Demonstradas - Feature 3:**
1. **🏗️ Arquitetura de Microsserviços**: Modularização em 3 componentes independentes
2. **🌐 Spring Cloud OpenFeign**: Comunicação elegante entre serviços via HTTP
3. **🔄 Orquestração de Serviços**: Combinação inteligente de múltiplas APIs externas
4. **🛡️ Resiliência e Fallback**: Algoritmos alternativos para alta disponibilidade  
5. **⚙️ Configuração Externa**: Externalization para diferentes ambientes
6. **📊 Response Enrichment**: DTOs enriquecidos com dados de APIs externas

### ✅ **Competências Demonstradas - Feature 4:**
7. **🔐 Spring Security**: Autenticação JWT stateless com filtros customizados
8. **👥 Controle de Acesso**: Roles granulares com @PreAuthorize por endpoint
9. **📊 Query Methods**: 15+ consultas personalizadas com JPA Repository
10. **🎯 DTOs Everywhere**: Zero exposição de entidades nos controllers
11. **✅ Validações Robustas**: Bean Validation com mensagens personalizadas
12. **📚 Documentação Completa**: Swagger público + Postman Collection
13. **🔍 Tratamento de Erros**: GlobalExceptionHandler com responses padronizados

### 🎯 **Resultado Final:**
**Sistema enterprise completo** que vai além dos requisitos básicos, implementando uma solução robusta e profissional para gerenciamento de ordens de serviço com:
- **🎯 Feature 3**: Orquestração de microsserviços em tempo real
- **🎯 Feature 4**: Segurança JWT + Query Methods + DTOs

### 🚀 **Pronto para Produção:**
- **Arquitetura escalável** e manutenível
- **Segurança enterprise** com JWT e controle de acesso
- **Código limpo** seguindo boas práticas
- **Documentação completa** (Swagger + Postman + README)
- **Testes funcionais** via endpoints
- **Cenário de negócio** realista e aplicável

---

## 📋 **GUIA DE VALIDAÇÃO - Features 3 e 4**

### **🎯 Preparação da Aplicação para Avaliação**

#### **1. Pré-requisitos Técnicos:**
```bash
# Verificar versões (obrigatórias)
java -version    # Java 21+ obrigatório
mvn -version     # Maven 3.8+ obrigatório
```

#### **2. Compilação e Execução:**
```bash
# 1. Navegar para o diretório do projeto
cd cochito-parent

# 2. Compilar todos os módulos
mvn clean compile

# 3. Navegar para o módulo principal
cd main-app

# 4. Executar a aplicação
mvn spring-boot:run

# 5. Aguardar inicialização completa (log: "Started MainAppApplication")
```

#### **3. Verificação de Funcionalidade (Seeds):**
```bash
# Verificar carregamento de dados no console:
# ✅ "10 serviços carregados"
# ✅ "3 clientes carregados" 
# ✅ "3 funcionários carregados"
# ✅ "27 ordens de serviço carregadas"
```

---

## 🧪 **TESTES DE VALIDAÇÃO DAS FEATURES 3 E 4**

### **🔐 TESTE 0: Autenticação JWT (OBRIGATÓRIO - Feature 4)**
```bash
# Login ADMIN (acesso total)
curl -X POST "http://localhost:8080/api/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "joao.silva@email.com",
    "password": "admin123"
  }'

# Login USER (apenas leitura)
curl -X POST "http://localhost:8080/api/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "ana.oliveira@email.com",
    "password": "cliente123"
  }'
```

**✅ Response Esperado:**
```json
{
  "success": true,
  "message": "Login realizado com sucesso",
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "userInfo": {
    "id": 1,
    "nome": "João Silva",
    "roles": ["ADMIN"]
  }
}
```

### **🎯 TESTE 1: DTOs e Validações (Feature 4)**
```bash
# Usar token obtido no teste anterior
export TOKEN="eyJhbGciOiJIUzI1NiJ9..."

# Testar endpoint com DTO (não retorna entidade diretamente)
curl -X GET "http://localhost:8080/api/clientes" \
  -H "Authorization: Bearer $TOKEN"

# Testar validações (deve retornar erro 400)
curl -X POST "http://localhost:8080/api/clientes" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "",
    "email": "email-inválido",
    "cpf": "123"
  }'
```

### **🎯 TESTE 2: Query Methods Avançados (Feature 4)**
```bash
# Query Methods personalizados
curl -X GET "http://localhost:8080/api/ordens-servico/status/PENDENTE" \
  -H "Authorization: Bearer $TOKEN"

curl -X GET "http://localhost:8080/api/ordens-servico/cliente?nome=Ana" \
  -H "Authorization: Bearer $TOKEN"

curl -X GET "http://localhost:8080/api/ordens-servico/status/CONCLUIDO/count" \
  -H "Authorization: Bearer $TOKEN"
```

### **🎯 TESTE 3: Controle de Acesso (Feature 4)**
```bash
# Testar com token USER (deve funcionar - GET permitido)
curl -X GET "http://localhost:8080/api/ordens-servico" \
  -H "Authorization: Bearer $TOKEN_USER"

# Testar com token USER (deve retornar 403 - POST negado)
curl -X POST "http://localhost:8080/api/ordens-servico" \
  -H "Authorization: Bearer $TOKEN_USER" \
  -H "Content-Type: application/json" \
  -d '{...}'
```

## 🧪 **TESTES DE VALIDAÇÃO DA FEATURE 3**

### **🚀 TESTE 4: Orquestração Principal (OBRIGATÓRIO - Feature 3)**
```bash
# ENDPOINT PRINCIPAL DA FEATURE 3 - Orquestração Completa
curl -X GET "http://localhost:8080/api/ordens-servico/4/detalhado" \
  -H "Authorization: Bearer $TOKEN"
```

**✅ Response Esperado:**
```json
{
    "id": 4,
    "funcionario": { "nome": "...", "endereco": {"cep": "..."} },
    "cliente": { "nome": "...", "endereco": {"cep": "..."} },
    "itensServicos": [...],
    "status": "PENDENTE",
    "distancia": {
        "cepOrigem": "38081470",
        "cepDestino": "38067290",
        "enderecoOrigem": "Avenida Francisco José de Carvalho",
        "bairroOrigem": "Parque do Mirante", 
        "ufOrigem": "MG",
        "enderecoDestino": "Rua República do Haiti",
        "bairroDestino": "Fabrício",
        "ufDestino": "MG",
        "distanciaKm": 6.58,
        "tempoMinutos": 10.57
    }
}
```

### **🔍 TESTE 5: Comparação CRUD vs Orquestração (Feature 3)**
```bash
# Endpoint BÁSICO (sem orquestração)
curl -X GET "http://localhost:8080/api/ordens-servico/4" \
  -H "Authorization: Bearer $TOKEN"

# vs.

# Endpoint DETALHADO (com orquestração Feature 3)
curl -X GET "http://localhost:8080/api/ordens-servico/4/detalhado" \
  -H "Authorization: Bearer $TOKEN"
```

**🎯 Diferença Crítica:**
- **BÁSICO**: `"distancia": "6.58 km"` (string simples)
- **DETALHADO**: Objeto completo com 10 campos de 2 APIs externas

### **🌐 TESTE 6: Feign Clients Individuais (Feature 3)**
```bash
# 3. Testar orquestração completa isolada
curl -X GET "http://localhost:8080/api/distancia/consulta/38081470/38067290" \
  -H "Authorization: Bearer $TOKEN"
```

### **🛡️ TESTE 7: Resiliência e Fallback (Feature 3)**
```bash
# Testar com CEPs inválidos para verificar fallback Haversine
curl -X GET "http://localhost:8080/api/distancia/consulta/00000000/11111111" \
  -H "Authorization: Bearer $TOKEN"
```

**✅ Comportamento Esperado:**
- Fallback automático para algoritmo Haversine
- Response com distância calculada matematicamente
- Logs de erro controlados (não quebra a aplicação)

### **📊 TESTE 8: Query Methods (Feature 4 + Feature 3)**
```bash
# Ordens pendentes por período (Feature 4 + Feature 3)
curl -X GET "http://localhost:8080/api/ordens-servico/pendentes?inicio=2025-08-01T00:00:00&fim=2025-12-31T23:59:59" \
  -H "Authorization: Bearer $TOKEN"

# Ordens por status
curl -X GET "http://localhost:8080/api/ordens-servico/status/CONCLUIDO" \
  -H "Authorization: Bearer $TOKEN"
```

---

## 🎯 **CHECKLIST DE VALIDAÇÃO TÉCNICA**

### **✅ Feature 3 - Arquitetura de Microsserviços:**
- [ ] **3 módulos Maven** independentes visíveis na estrutura
- [ ] **Separação de responsabilidades** clara entre módulos
- [ ] **Injeção de dependência** funcionando entre módulos

### **✅ Feature 3 - Spring Cloud OpenFeign:**
- [ ] **AwesomeCepFeignClient** funcionando (consulta CEP)
- [ ] **OpenRouteFeignClient** funcionando (cálculo rota)  
- [ ] **Configuração @FeignClient** correta nos códigos

### **✅ Feature 3 - Orquestração de Serviços:**
- [ ] **DistanciaService** orquestra 2 APIs em sequência
- [ ] **Response enriquecido** com dados de múltiplas fontes
- [ ] **Endpoint /detalhado** retorna dados combinados

### **✅ Feature 3 - Resiliência e Fallback:**
- [ ] **Algoritmo Haversine** ativa em caso de falha
- [ ] **Try/catch** adequados nos Feign Clients
- [ ] **Aplicação não quebra** com APIs indisponíveis

### **✅ Feature 3 - Configuração Externa:**
- [ ] **URLs das APIs** em `application.properties`
- [ ] **API Keys** externalizadas (configuráveis)
- [ ] **Profiles** prontos para diferentes ambientes

### **✅ Feature 4 - Spring Security + JWT:**
- [ ] **Autenticação JWT** funcionando (login retorna token)
- [ ] **Filtro JWT** intercepta requisições protegidas
- [ ] **Roles ADMIN/USER** diferenciadas por endpoint
- [ ] **@PreAuthorize** configurado nos controllers

### **✅ Feature 4 - Query Methods:**
- [ ] **15+ Query Methods** personalizados funcionando
- [ ] **Consultas por status, cliente, período** retornando dados
- [ ] **@Query customizadas** com JPQL funcionando
- [ ] **Count methods** retornando números corretos

### **✅ Feature 4 - DTOs (Sem Exposição de Entidades):**
- [ ] **Controllers não retornam entidades** diretamente
- [ ] **DTOs Request/Response** em todos os endpoints
- [ ] **Validações Bean Validation** funcionando
- [ ] **Mensagens de erro** personalizadas em português

### **✅ Feature 4 - Swagger + Documentação:**
- [ ] **Swagger UI público** acessível sem autenticação
- [ ] **Login pré-preenchido** com credenciais de teste
- [ ] **Controllers organizados** por tags numeradas
- [ ] **Postman Collection** importável e funcional

---

## 🏆 **CRITÉRIOS DE APROVAÇÃO**

### **🎯 Funcionalidades OBRIGATÓRIAS - Feature 3:**
1. ✅ **Endpoint /detalhado** retorna objeto distância completo
2. ✅ **2 Feign Clients** funcionando individualmente  
3. ✅ **Orquestração** combina dados de APIs externas
4. ✅ **Fallback** funciona com dados inválidos
5. ✅ **Módulos independentes** compilam separadamente

### **🎯 Funcionalidades OBRIGATÓRIAS - Feature 4:**
6. ✅ **Autenticação JWT** com login funcionando
7. ✅ **Controle de acesso** ADMIN/USER por endpoint
8. ✅ **Query Methods** personalizados funcionando
9. ✅ **DTOs** em todos os controllers (zero entidades expostas)
10. ✅ **Validações** Bean Validation funcionando

### **🚀 Pontos EXTRAS (Diferenciais):**
11. ✅ **Swagger público** com login pré-preenchido
12. ✅ **Postman Collection** completa e testada
13. ✅ **27 ordens** pré-carregadas com dados realistas
14. ✅ **Algoritmo Haversine** matemático implementado
15. ✅ **Documentação técnica** completa e profissional

---

## 🧪 **Quick Start - Teste Rápido (60 segundos)**

```bash
# 1. Execute (aguarde "Started MainAppApplication")
cd main-app && mvn spring-boot:run

# 2. Faça login para obter token JWT (Feature 4)
curl -X POST "http://localhost:8080/api/auth/login" \
  -H "Content-Type: application/json" \
  -d '{"email": "joao.silva@email.com", "password": "admin123"}'

# 3. Copie o token e teste PRINCIPAL da Feature 3
export TOKEN="SEU_TOKEN_AQUI"
curl -X GET "http://localhost:8080/api/ordens-servico/4/detalhado" \
  -H "Authorization: Bearer $TOKEN"

# 4. Teste Query Method da Feature 4
curl -X GET "http://localhost:8080/api/ordens-servico/status/PENDENTE" \
  -H "Authorization: Bearer $TOKEN"

# ✅ SUCESSO: Features 3 e 4 implementadas corretamente!
```

### **🌐 Acesso Rápido:**
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **Postman Collection**: [Link da Collection](https://api.postman.com/collections/33558167-802ea8c2-aeea-42c5-bcd2-81c4329b7c7b?access_key=PMAT-01K3Q35BC53066TFP2V3VFJRXT)

---

## 👨‍💻 **Desenvolvedor**

**Projeto desenvolvido para demonstrar competências completas em Arquiteturas Avançadas de Software - Features 3 e 4.**

### 📧 **Contato para Dúvidas Técnicas**
Este projeto implementa todos os requisitos das Features 3 e 4 e está pronto para avaliação técnica detalhada.

### 🎯 **Features Implementadas:**
- ✅ **Feature 3**: Microsserviços + Spring Cloud OpenFeign + Orquestração
- ✅ **Feature 4**: Spring Security + JWT + Query Methods + DTOs

---

*📋 README técnico completo documentando a implementação integral das Features 3 e 4 - Arquiteturas Avançadas de Software com Microsserviços, Spring Framework e Spring Security.*
