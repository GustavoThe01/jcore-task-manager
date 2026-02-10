# JCore Manager

> **Status do Projeto:** 🚀 Concluído

## 📝 Sobre o Projeto

O **JCore Manager** é uma aplicação robusta de gerenciamento de tarefas executada via console (CLI), desenvolvida em **Java Puro**.

Este projeto foi criado com o objetivo de compor um portfólio profissional, demonstrando competências avançadas em engenharia de software, arquitetura limpa e manipulação de dados sem a dependência de frameworks pesados.

### 🎯 Função e Objetivo
O sistema serve para gerenciar o ciclo de vida de tarefas pessoais ou profissionais. Ele permite que o usuário registre atividades, defina prioridades, acompanhe o status de conclusão e mantenha um histórico persistente dos seus dados.

Diferente de scripts simples, o JCore Manager implementa uma **Arquitetura em Camadas** profissional, simulando como grandes sistemas corporativos são estruturados internamente.

---

## 🛠️ Tecnologias e Ferramentas

O projeto utiliza uma stack moderna e focada na robustez do Java:

*   **Linguagem:** [Java 17+](https://www.oracle.com/java/)
    *   Uso de *Switch Expressions*, *Streams API*, *Lambdas* e *Optional*.
*   **Build System:** [Apache Maven](https://maven.apache.org/)
    *   Gerenciamento de dependências e ciclo de vida de build.
*   **Persistência de Dados:** JSON
    *   Os dados não são perdidos ao fechar o programa.
*   **Biblioteca Externa:** [Google Gson](https://github.com/google/gson)
    *   Utilizada para serialização (Objeto -> JSON) e desserialização (JSON -> Objeto).
*   **IDE Recomendada:** IntelliJ IDEA, Eclipse ou VS Code.

---

## 🏗️ Arquitetura do Sistema

O código segue estritamente o padrão de separação de responsabilidades (SoC):

1.  **Model (`com.jcoremanager.model`)**
    *   Representa os objetos de domínio (`Task`, `Priority`).
    *   Regras de encapsulamento e formatação de dados.
2.  **Repository (`com.jcoremanager.repository`)**
    *   Camada de acesso a dados. Abstrai a leitura/escrita no arquivo `tasks.json`.
    *   Implementa o padrão *Repository Pattern*.
3.  **Service (`com.jcoremanager.service`)**
    *   Contém as regras de negócio.
    *   Validações (ex: "Título não pode ser vazio").
    *   Lógica de datas (ex: "Data de conclusão é definida automaticamente ao finalizar tarefa").
4.  **Controller (`com.jcoremanager.controller`)**
    *   Gerencia a interação com o usuário.
    *   Exibe menus e captura entradas do teclado.
    *   Tratamento de erros de entrada (UX no Console).

---

## 🚀 Como Executar o Projeto

### Pré-requisitos
*   Java JDK 17 ou superior instalado.
*   Maven instalado (opcional, mas recomendado).

### Passo a Passo

1.  **Clone o repositório** ou baixe os arquivos.
2.  **Abra o terminal** na pasta raiz do projeto.
3.  **Compile e Execute** usando o Maven:

```bash
mvn clean compile exec:java -Dexec.mainClass="com.jcoremanager.Main"
```

Se preferir compilar manualmente sem Maven:
```bash
# Compilar
javac -cp "lib/gson-2.10.1.jar" -d bin src/main/java/com/jcoremanager/**/*.java src/main/java/com/jcoremanager/*.java

# Executar (Linux/Mac)
java -cp "bin:lib/gson-2.10.1.jar" com.jcoremanager.Main

# Executar (Windows)
java -cp "bin;lib/gson-2.10.1.jar" com.jcoremanager.Main
```

---

## ✨ Funcionalidades Principais

*   ✅ **Criar Tarefa:** Com validação de campos obrigatórios.
*   📋 **Listar Tarefas:** Visualização formatada com ID, Status e Datas.
*   ✏️ **Atualizar:** Edição parcial (pressione Enter para manter o valor atual).
*   🗑️ **Remover:** Exclusão física do registro.
*   ✅ **Concluir:** Marcação de status e registro automático da data/hora de término.
*   🚨 **Prioridades:** Classificação via Enum (BAIXA, MEDIA, ALTA).
*   💾 **Auto-Save:** Tudo é salvo automaticamente em `tasks.json`.

---

Desenvolvido para demonstração de proficiência técnica em Java.