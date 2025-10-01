# 🏥 UBSFácil – Gestão e Consulta de Insumos em UBS

## 📌 Visão Geral
O **UBSFácil** é um sistema desenvolvido para melhorar a **gestão de insumos e materiais** em Unidades Básicas de Saúde (UBS) e oferecer aos pacientes uma forma simples de consultar a disponibilidade de medicamentos próximos à sua localização.

A solução é baseada em **Java com Spring Boot**, utiliza **PostgreSQL** como banco de dados e segue **arquitetura hexagonal** para garantir modularidade e escalabilidade.  

---

## 🚨 Problema
Segundo o **TCU (2023)**, 35% das UBS tiveram episódios de falta de insumos essenciais em 2022. Apenas 15% dessas unidades utilizam sistemas digitais para controle de estoque (Fiocruz, 2023), e o **Ministério da Saúde** estima desperdício superior a **R$ 50 milhões/ano** devido a vencimentos ou má distribuição.  

Essas limitações dificultam o acesso dos pacientes a insumos básicos e sobrecarregam os profissionais de saúde.  

---

## 💡 Solução
O **UBSFácil** atua em duas frentes principais:

1. **Gestão interna da UBS**  
   - Cadastro de insumos, estoques e unidades.  
   - Controle de validade e quantidade.  
   - Alertas para reposição e vencimento.  

2. **Consulta pública**  
   - O paciente informa **CEP** + **nome do insumo**.  
   - O sistema usa geolocalização + fórmula de Haversine para calcular distâncias.  
   - Retorna UBS próximas com disponibilidade em um raio (default: 5km).  

---

## ⚙️ Tecnologias Utilizadas
- **Java 21**  
- **Spring Boot**  
- **PostgreSQL**  
- **Docker Compose**  
- **Swagger (OpenAPI 3.0)** para documentação da API  
- **Arquitetura Hexagonal** (adaptada, MVP utilizando Services)  
- **APIs externas:**  
  - [Nominatim](https://nominatim.org/) (conversão de CEP → latitude/longitude)  
  - Adaptável para Google Maps API  

---

## 🚀 Como Executar

### Pré-requisitos
- Docker + Docker Compose instalados  
- Java 21  
- Maven  

### Passos
1. Clone o repositório:  
   ```bash
   git clone https://github.com/Emersoaresj/UBSFACIL.git
   cd UBSFACIL
2. Suba o ambiente com Docker Compose:
    ```bash
   docker-compose up -d
3. Rode a aplicação:
    ```bash
    mvn spring-boot:run
4. Acesse a documentação da API no navegador:
    ```bash
    http://localhost:8080/swagger-ui.html

## 📊 Funcionalidades Principais
- Cadastro de UBS com geolocalização automática pelo CEP.
- Cadastro de insumos e estoques vinculados à UBS.
- Consulta pública de insumos por CEP + nome do medicamento.
- Alertas para estoque baixo ou validade próxima.

## 🛠 Próximos Passos
- Integração com o sistema Aqui Tem Remédio.
- Implementação de fila virtual inspirada no programa Saúde Fila Zero (Viçosa/MG).
- Relatórios analíticos avançados.
- Interface web amigável para gestores e pacientes.
- Integração com sistemas municipais, estaduais e federais.
- Notificações assíncronas (e-mail/SMS) para alertas de estoque.

## 📚 Referências
- Tribunal de Contas da União (TCU), Relatório 2023.
- Fiocruz, Estudos de Saúde Pública, 2023.
- Ministério da Saúde, Relatórios de Gestão, 2022.
- Aqui Tem Remédio – Prefeitura de SP
- Saúde Fila Zero – Viçosa/MG

##
- 📌 Autor: Emerson Soares Jeronimo
- 📌 Projeto desenvolvido para Pós Tech – FIAP (Arquitetura e Desenvolvimento Java)
