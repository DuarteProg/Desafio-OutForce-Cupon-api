# Cupom API – Desafio Técnico Java Spring

API REST para cadastro, consulta e exclusão lógica (soft delete) de cupons de desconto.  
Desenvolvida como solução para um desafio técnico de Desenvolvedor Java Júnior.

---

## Tecnologias Utilizadas

| Tecnologia | Versão |
|------------|--------|
| Java | 17 |
| Spring Boot | 3.5.7 |
| Spring Web | ✔ |
| Spring Data JPA | ✔ |
| Spring Validation | ✔ |
| H2 Database | ✔ |
| Lombok | ✔ |
| JUnit 5 | ✔ |
| Mockito | ✔ |
| MockMvc | ✔ |

---

## Como Executar o Projeto


### 1️⃣ Clone o repositório

```sh
git clone https://github.com/DuarteProg/Desafio-OutForce-Cupon-api.git
```

2️⃣ Entre no diretório do projeto
cd Desafio-OutForce-Cupon-api


3️⃣ Execute o projeto
mvn spring-boot:run

A API ficará disponível em:
http://localhost:8080

Acessando o Banco H2:
http://localhost:8080/h2-console


# Endpoints da API:
## POST	/coupon	Cria um novo cupom
## GET	/coupon/{id}	Busca cupom pelo ID
## DELETE	/coupon/{id}	Soft delete do cupom

 Exemplo de Requisição POST
{
  "code": "ABC-123",
  "description": "Cupom de teste",
  "discountValue": 0.8,
  "expirationDate": "2030-01-01T10:00:00",
  "published": true
}


 Resposta 201 CREATED

{
  "id": "cef9d1e3-aae5-4ab6-a297-358c6032b1e7",
  "code": "ABC123",
  "description": "Cupom de teste",
  "discountValue": 0.8,
  "expirationDate": "2030-01-01T10:00:00",
  "status": "ACTIVE",
  "published": true,
  "redeemed": false
}

 Regras de Negócio Implementadas

✔ Código deve conter exatamente 6 caracteres
 → remove caracteres especiais
 → se ultrapassar 6, corta
 → se faltar, completa com "X"

✔ Valor mínimo do desconto = 0.5

✔ Cupom não pode expirar no passado

✔ Cupom pode ser criado já published

✔ Exclusão é soft delete
→ campo deleted = true
→ status muda para DELETED

✔ Não é permitido deletar cupom já deletado

✔ Status possíveis: ACTIVE ou DELETED

# Testes Automatizados
🔹 Testes de Service (Mockito)

✔ Criar cupom válido
✔ Código sanitizado corretamente
✔ Rejeitar desconto menor que 0.5
✔ Rejeitar expiração no passado
✔ Buscar cupom existente
✔ Buscar cupom inexistente
✔ Soft delete correto
✔ Impedir delete duplicado

🔹 Testes de Controller (MockMvc)

✔ POST /coupon → 201
✔ POST /coupon inválido → 400
✔ GET /coupon/{id} → 200
✔ GET cupom inexistente → 404
✔ DELETE /coupon/{id} → 204
✔ DELETE cupom já deletado → 400

▶ Rodar testes:

mvn test

### Melhorias Futuras

🔹 Atualizar cupom (PUT/PATCH)
🔹 Paginação de cupons ativos
🔹 Expiração automática via Scheduler
🔹 Autenticação (JWT)

## Desenvolvedor

Lucas Duarte Barbosa
 Backend Java Developer
 GitHub: https://github.com/DuarteProg
