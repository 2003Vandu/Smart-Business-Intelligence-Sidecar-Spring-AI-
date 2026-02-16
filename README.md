# 🤖 Smart Business Intelligence Sidecar (Spring AI)

A specialized AI service designed to work alongside a core POS Billing system. Built with **Spring Boot 3.5.10**, this project implements **RAG (Retrieval-Augmented Generation)** using **Google Gemini** to provide high-level business insights.

## 🏛️ Architectural Approach: Shared Database Sidecar
This project follows a **Sidecar Pattern** where the AI logic is decoupled from the main POS logic to ensure the stability of financial transactions. Both projects interact with a shared **MySQL** database:
* **Core POS:** Handles high-speed billing, inventory, and payments.
* **Spring AI Sidecar:** Processes historical data from the same DB to generate predictions and insights using LLMs.

> This design ensures **Data Integrity** and **Atomicity** by maintaining a single source of truth (MySQL) while allowing the AI features to scale independently without affecting checkout performance.

---

## 👥 My Contributions & Collaboration
* **Sidecar Development:** Developed a separate Spring Boot 3.5.10 application to handle heavy AI processing, keeping the main POS "light" and secure.
* **LLM Integration:** Integrated **Google Gemini** for business intelligence and planned for **Ollama** integration to ensure a cost-efficient, hybrid cloud-local model.
* **SQL Mastery:** Wrote optimized queries to fetch business context for the **RAG pipeline**, ensuring the LLM has accurate, real-time data.
* **API Documentation:** Implemented **Swagger/OpenAPI** for easy testing of AI endpoints.

---

## 🛠 Tech Stack
* **Framework:** **Spring Boot 3.5.10** (Java).
* **AI Engine:** **Spring AI** with **Google Gemini** & **Ollama** support.
* **Database:** **MySQL** (Relational storage for ACID compliance).
* **AI Pattern:** **RAG (Retrieval-Augmented Generation)** for business-specific insights.

---

## 🧩 Key Features
* **🤖 Business Intelligence:** Natural language queries to get sales trends and stock predictions.

---

## 🔮 Future Roadmap (Under Development)
* **Local LLM Migration:** Fully migrating to **Ollama** for 100% cost-free local processing.
* **Transactional Integrity:** Adding **ACID-compliant Transaction Management** for multi-project data sync.

---

## 🔌 API Reference

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/ai/analyze` | Generates business insights from POS data |
| `GET` | `/ai/forecast` | Predicts future sales using Spring AI |

---

## 🏃 Setup
1. Point `spring.datasource.url` to your existing POS MySQL database.
2. Add your `spring.ai.gemini.api-key` in `application.properties`.
3. Run `mvn spring-boot:run`.

---

## 🧾 Author
* **👤 Vandesh Ghodke** | Java Backend & AI Developer
* 📧 **vandesghodke2003@gmail.com**
* **🔗** [GitHub - 2003Vandu](https://github.com/2003Vandu)