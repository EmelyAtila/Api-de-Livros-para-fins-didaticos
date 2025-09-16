
# 📚 API de Cadastro de Autores

## 📖 Descrição
Esta API permite o **cadastro, atualização, consulta e exclusão** de autores de livros.  
O objetivo é gerenciar informações de autores e garantir integridade das regras de negócio.

---

## 👥 Atores do Sistema
- **Gerente**
  - Pode **cadastrar, atualizar e remover** autores.
- **Operador**
  - Pode **consultar** os dados dos autores.

---

## 📌 Campos do Autor

### **Campos Obrigatórios (Negócio)**
- `nome` (string) – Nome do autor
- `dataNascimento` (date) – Data de nascimento
- `nacionalidade` (string) – Nacionalidade

### **Campos Lógicos (Controle e Auditoria)**
- `id` (UUID) – Identificador único
- `dataCadastro` (datetime) – Data do cadastro
- `dataUltimaAtualizacao` (datetime) – Última atualização
- `usuarioUltimaAtualizacao` (string) – Usuário que realizou a última atualização

---

## ⚖️ Regras de Negócio
- Não é permitido cadastrar **dois autores** com os mesmos **Nome + Data de Nascimento + Nacionalidade**.  
- Não é permitido excluir um autor que possua livros cadastrados.

---

## 🔗 Endpoints da API

### ➕ Cadastrar Autor
**Requisição**
```
POST /autores
```
**Body**
```json
{
  "nome": "string",
  "dataNascimento": "date",
  "nacionalidade": "string"
}
```

**Respostas**
- ✅ `201 Created` – Autor criado com sucesso  
- ⚠️ `422 Unprocessable Entity` – Erro de validação  
- ❌ `409 Conflict` – Autor duplicado  

---

### 🔍 Visualizar Detalhes do Autor
**Requisição**
```
GET /autores/{id}
```

**Respostas**
- ✅ `200 OK`  
```json
{
  "id": "uuid",
  "nome": "string",
  "dataNascimento": "date",
  "nacionalidade": "string"
}
```
- ❌ `404 Not Found`  

---

### ❌ Excluir Autor
**Requisição**
```
DELETE /autores/{id}
```

**Respostas**
- ✅ `204 No Content`  
- ❌ `400 Bad Request` – Autor possui livros vinculados  

```json
{
   "status": 400,
   "message": "Erro na exclusão: registro está sendo utilizado.",
   "errors": []
}
```

---

### 🔎 Pesquisar Autores
**Requisição**
```
GET /autores?nome={nome}&nacionalidade={nacionalidade}
```

**Respostas**
- ✅ `200 OK`  
```json
[
  {
    "id": "uuid",
    "nome": "string",
    "dataNascimento": "date",
    "nacionalidade": "string"
  }
]
```

---

### ✏️ Atualizar Autor
**Requisição**
```
PUT /autores/{id}
```
**Body**
```json
{
  "nome": "string",
  "dataNascimento": "date",
  "nacionalidade": "string"
}
```

**Respostas**
- ✅ `204 No Content`  
- ⚠️ `422 Unprocessable Entity` – Erro de validação  
- ❌ `409 Conflict` – Autor duplicado  

---

## 🚀 Tecnologias Recomendadas
- Linguagem: **Node.js / Java / C#**  
- Banco de Dados: **PostgreSQL / MySQL**  
- Formato de Resposta: **JSON**  

---

## 📌 Status do Projeto
✅ Em desenvolvimento inicial.  
Futuras melhorias:
- Autenticação JWT  
- Paginação na busca  
- Integração com cadastro de livros  
