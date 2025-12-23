# 🧪 Automação Web – SwagLabs (Selenide + Gradle)

Projeto de automação de testes web utilizando **Java**, **Selenide**, **JUnit** e **Gradle**, com execução **local (browser visível)** e **CI/CD no GitHub Actions (headless)**.

O projeto valida fluxos principais do sistema SwagLabs, como:
- Login
- Adição de produtos ao carrinho
- Checkout

---

## 🚀 Tecnologias utilizadas

- Java 17
- Gradle
- Selenide
- Selenium WebDriver
- JUnit
- GitHub Actions (CI)

---

## 📂 Estrutura do projeto

```text
src
 └── test
     └── java
         └── br.com.swaglabs
             ├── config        # Configuração do browser (Selenide)
             ├── pages         # Page Objects
             ├── tests         # Casos de teste
             └── utils         # Leitura de properties e utilidades
```




[![Java CI with Gradle](https://github.com/JeanHeberth/swaglabs/actions/workflows/gradle.yml/badge.svg)](https://github.com/JeanHeberth/swaglabs/actions/workflows/gradle.yml)