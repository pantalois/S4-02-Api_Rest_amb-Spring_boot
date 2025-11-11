# 🍏 Fruit API

API REST con **Spring Boot** para gestionar frutas: crear, listar, consultar por ID, actualizar y eliminar. Incluye DTOs, validación, manejador global de excepciones, tests con MockMvc y empaquetado con **Docker** (multi-stage).

---

## ⚙️ Tecnologías
- **Java 21** · **Maven 3.9+**
- **Spring Boot 3** (Web, Validation)
- **H2 Database** (dev/test)
- **JUnit 5** + **MockMvc**
- **Docker** (JRE Alpine, multi-stage build)

---

## 📦 Requisitos
- JDK 21
- Maven 3.9+
- (Opcional) Docker 24+

---

## 🚀 Ejecución local
```bash
mvn clean package -DskipTests
java -jar target/fruit-api.jar
```

```bash
docker build -t fruit-api .
docker run -p 8080:8080 fruit-api
```

## Abrir url en navegador
```bash
http://localhost:8080/fruits
```
