# 🎬 Sistema de Filmes - Backend Documentation

## 📋 Visão Geral

API REST desenvolvida para suportar o sistema de filmes, fornecendo endpoints para gerenciamento de avaliações de usuários. Construída com arquitetura RESTful e integração com banco de dados.

## 🚀 Tecnologias Utilizadas

- **Java 17+** - Linguagem de programação
- **Spring Boot 3.x** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **Spring Web** - API REST
- **H2 Database** - Banco de dados em memória (desenvolvimento)
- **MySQL/PostgreSQL** - Banco de dados (produção)
- **Maven** - Gerenciamento de dependências

## 📁 Estrutura do Projeto

```
backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/movieproject/
│   │   │       ├── controller/
│   │   │       │   └── ReviewController.java
│   │   │       │   └── UserController.java
│   │   │       │   └── FavoritesController.java
│   │   │       ├── model/
│   │   │       │   └── Review.java
│   │   │       │   └── User.java
│   │   │       │   └── Favorites.java
│   │   │       ├── repository/
│   │   │       │   └── ReviewRepository.java
│   │   │       │   └── UserRepository.java
│   │   │       │   └── FavoritesRepository.java
│   │   │       ├── service/
│   │   │       │   └── ReviewService.java
│   │   │       │   └── UserService.java
│   │   │       │   └── FavoritesService.java
│   │   │       └── MovieProjectApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   └── test/
├── pom.xml
└── README.md
```

## 🛠️ Instalação e Configuração

### Pré-requisitos
- Java 17+
- Maven 3.6+
- MySQL/PostgreSQL (produção)

### Instalação
```bash
git clone <repository>
cd backend
mvn clean install
```

### Executar em Desenvolvimento
```bash
mvn spring-boot:run
```

### Build para Produção
```bash
mvn clean package
java -jar target/movie-project-0.0.1-SNAPSHOT.jar
```

## 🗄️ Modelo de Dados

### Entidade Review
```java
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long movieId;
    
    @Column(nullable = false)
    private String userName;
    
    @Column(nullable = false)
    private Integer rating; // 1-5
    
    @Column(columnDefinition = "TEXT")
    private String comment;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    // Getters e Setters
}
```

### Estrutura da Tabela
```sql
CREATE TABLE reviews (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    movie_id BIGINT NOT NULL,
    user_name VARCHAR(255) NOT NULL,
    rating INTEGER NOT NULL CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## 🛣️ Endpoints da API

### Base URL
```
http://localhost:8080
```

### 1. Buscar Avaliações por Filme
```http
GET /reviews/movie/{movieId}
```

**Parâmetros:**
- `movieId` (Long) - ID do filme

**Resposta de Sucesso (200):**
```json
[
    {
        "id": 1,
        "movieId": 1,
        "userName": "João Silva",
        "rating": 5,
        "comment": "Filme excelente! Recomendo muito.",
        "createdAt": "2024-01-15T10:30:00"
    },
    {
        "id": 2,
        "movieId": 1,
        "userName": "Maria Santos",
        "rating": 4,
        "comment": "Muito bom, mas poderia ser melhor.",
        "createdAt": "2024-01-16T14:20:00"
    }
]
```

**Resposta Vazia (200):**
```json
[]
```

### 2. Criar Nova Avaliação
```http
POST /reviews
Content-Type: application/json
```

**Corpo da Requisição:**
```json
{
    "movieId": 1,
    "userName": "Pedro Costa",
    "rating": 5,
    "comment": "Obra-prima do cinema!"
}
```

**Resposta de Sucesso (201):**
```json
{
    "id": 3,
    "movieId": 1,
    "userName": "Pedro Costa",
    "rating": 5,
    "comment": "Obra-prima do cinema!",
    "createdAt": "2024-01-17T16:45:00"
}
```

**Resposta de Erro (400):**
```json
{
    "error": "Bad Request",
    "message": "Rating deve estar entre 1 e 5",
    "timestamp": "2024-01-17T16:45:00"
}
```

### 3. Buscar Todas as Avaliações
```http
GET /reviews
```

**Resposta de Sucesso (200):**
```json
[
    {
        "id": 1,
        "movieId": 1,
        "userName": "João Silva",
        "rating": 5,
        "comment": "Filme excelente!",
        "createdAt": "2024-01-15T10:30:00"
    }
]
```

### 4. Buscar Avaliação por ID
```http
GET /reviews/{id}
```

**Parâmetros:**
- `id` (Long) - ID da avaliação

**Resposta de Sucesso (200):**
```json
{
    "id": 1,
    "movieId": 1,
    "userName": "João Silva",
    "rating": 5,
    "comment": "Filme excelente!",
    "createdAt": "2024-01-15T10:30:00"
}
```

**Resposta de Erro (404):**
```json
{
    "error": "Not Found",
    "message": "Avaliação não encontrada",
    "timestamp": "2024-01-17T16:45:00"
}
```

## 🏗️ Arquitetura

### Controller Layer
**ReviewController.java**
- Responsável por receber requisições HTTP
- Validação básica de parâmetros
- Mapeamento de endpoints
- Tratamento de exceções

```java
@RestController
@RequestMapping("/reviews")
@CrossOrigin(origins = "http://localhost:8081")
public class ReviewController {
    
    @Autowired
    private ReviewService reviewService;
    
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Review>> getReviewsByMovie(@PathVariable Long movieId) {
        List<Review> reviews = reviewService.getReviewsByMovieId(movieId);
        return ResponseEntity.ok(reviews);
    }
    
    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        Review savedReview = reviewService.saveReview(review);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReview);
    }
}
```

### Service Layer
**ReviewService.java**
- Lógica de negócio
- Validações complexas
- Processamento de dados
- Integração com repository

```java
@Service
public class ReviewService {
    
    @Autowired
    private ReviewRepository reviewRepository;
    
    public List<Review> getReviewsByMovieId(Long movieId) {
        return reviewRepository.findByMovieIdOrderByCreatedAtDesc(movieId);
    }
    
    public Review saveReview(Review review) {
        validateReview(review);
        return reviewRepository.save(review);
    }
    
    private void validateReview(Review review) {
        if (review.getRating() < 1 || review.getRating() > 5) {
            throw new IllegalArgumentException("Rating deve estar entre 1 e 5");
        }
        if (review.getUserName() == null || review.getUserName().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do usuário é obrigatório");
        }
    }
}
```

### Repository Layer
**ReviewRepository.java**
- Interface com banco de dados
- Queries customizadas
- Operações CRUD

```java
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    List<Review> findByMovieIdOrderByCreatedAtDesc(Long movieId);
    
    List<Review> findByUserNameOrderByCreatedAtDesc(String userName);
    
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.movieId = :movieId")
    Double getAverageRatingByMovieId(@Param("movieId") Long movieId);
    
    Long countByMovieId(Long movieId);
}
```

## ⚙️ Configurações

### application.properties (Desenvolvimento)
```properties
# Servidor
server.port=8080

# Banco H2 (Desenvolvimento)
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA/Hibernate
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# H2 Console (apenas desenvolvimento)
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# CORS
spring.web.cors.allowed-origins=http://localhost:8081
spring.web.cors.allowed-methods=GET,POST,PUT,DELETE,OPTIONS
spring.web.cors.allowed-headers=*
```

### application-prod.properties (Produção)
```properties
# Servidor
server.port=${PORT:8080}

# MySQL/PostgreSQL
spring.datasource.url=${DATABASE_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# CORS
spring.web.cors.allowed-origins=${FRONTEND_URL:http://localhost:8081}
```

### pom.xml - Dependências Principais
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>
    
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <scope>runtime</scope>
    </dependency>
    
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
</dependencies>
```

## 🔒 Segurança e CORS

### Configuração CORS
```java
@Configuration
public class CorsConfig {
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8081"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
```

### Validações Implementadas
- Rating entre 1 e 5
- Nome de usuário obrigatório
- MovieId obrigatório
- Sanitização de comentários
- Limite de caracteres nos campos

## 📊 Banco de Dados

### Scripts de Inicialização
**data.sql**
```sql
-- Dados de exemplo para desenvolvimento
INSERT INTO reviews (movie_id, user_name, rating, comment, created_at) VALUES
(1, 'João Silva', 5, 'Filme incrível! Efeitos visuais espetaculares.', '2024-01-15 10:30:00'),
(1, 'Maria Santos', 4, 'Muito bom, mas um pouco longo.', '2024-01-16 14:20:00'),
(2, 'Pedro Costa', 5, 'Obra-prima do cinema!', '2024-01-17 16:45:00'),
(3, 'Ana Oliveira', 3, 'Filme ok, nada excepcional.', '2024-01-18 09:15:00');
```

### Índices Recomendados
```sql
-- Índice para busca por filme
CREATE INDEX idx_reviews_movie_id ON reviews(movie_id);

-- Índice para busca por usuário
CREATE INDEX idx_reviews_user_name ON reviews(user_name);

-- Índice para ordenação por data
CREATE INDEX idx_reviews_created_at ON reviews(created_at);
```

## 🧪 Testes

### Testes de Unidade
```java
@SpringBootTest
class ReviewServiceTest {
    
    @Autowired
    private ReviewService reviewService;
    
    @Test
    void shouldCreateReviewSuccessfully() {
        Review review = new Review();
        review.setMovieId(1L);
        review.setUserName("Test User");
        review.setRating(5);
        review.setComment("Great movie!");
        
        Review saved = reviewService.saveReview(review);
        
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getUserName()).isEqualTo("Test User");
    }
    
    @Test
    void shouldThrowExceptionForInvalidRating() {
        Review review = new Review();
        review.setMovieId(1L);
        review.setUserName("Test User");
        review.setRating(6); // Invalid rating
        
        assertThrows(IllegalArgumentException.class, () -> {
            reviewService.saveReview(review);
        });
    }
}
```

### Testes de Integração
```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReviewControllerIntegrationTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    void shouldGetReviewsByMovie() {
        ResponseEntity<Review[]> response = restTemplate.getForEntity(
            "/reviews/movie/1", Review[].class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }
}
```

## 📈 Monitoramento e Logs

### Configuração de Logs
```properties
# Níveis de log
logging.level.com.movieproject=DEBUG
logging.level.org.springframework.web=INFO
logging.level.org.hibernate.SQL=DEBUG

# Arquivo de log
logging.file.name=logs/movie-project.log
logging.pattern.file=%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n
```

### Health Check
```java
@RestController
public class HealthController {
    
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> status = new HashMap<>();
        status.put("status", "UP");
        status.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.ok(status);
    }
}
```

## 🚀 Deploy

### Docker
```dockerfile
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/movie-project-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
```

### docker-compose.yml
```yaml
version: '3.8'
services:
  backend:
    build: .
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=prod
      - DATABASE_URL=jdbc:mysql://db:3306/moviedb
      - DB_USERNAME=root
      - DB_PASSWORD=password
    depends_on:
      - db
  
  db:
    image: mysql:8.0
    environment:
      - MYSQL_ROOT_PASSWORD=password
      - MYSQL_DATABASE=moviedb
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql

volumes:
  mysql_data:
```

## 🔧 Troubleshooting

### Problemas Comuns

1. **CORS Error**
   - Verificar configuração de CORS
   - Confirmar URL do frontend

2. **Conexão com Banco**
   - Verificar credenciais
   - Confirmar se banco está rodando

3. **Port Already in Use**
   ```bash
   lsof -ti:8080 | xargs kill -9
   ```

4. **Memory Issues**
   ```bash
   java -Xmx512m -jar app.jar
   ```

## 📞 API Testing

### Usando cURL
```bash
# Buscar avaliações
curl -X GET http://localhost:8080/reviews/movie/1

# Criar avaliação
curl -X POST http://localhost:8080/reviews \
  -H "Content-Type: application/json" \
  -d '{
    "movieId": 1,
    "userName": "Test User",
    "rating": 5,
    "comment": "Great movie!"
  }'
```

### Usando Postman
1. Importar collection com endpoints
2. Configurar environment variables
3. Testar todos os cenários

## 📈 Melhorias Futuras

### Funcionalidades Planejadas
- Autenticação JWT
- Sistema de usuários completo
- Cache com Redis
- Rate limiting
- Paginação de resultados
- Busca avançada
- Notificações em tempo real

### Otimizações Técnicas
- Connection pooling
- Query optimization
- Async processing
- Microservices architecture
- API versioning

---

**Desenvolvido com ☕ usando Spring Boot**
