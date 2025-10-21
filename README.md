# 📚 Cadastro de Autor

## 📝 Descrição
Deseja-se cadastrar os autores de livros, bem como realizar suas **atualizações**, **consultas** e permitir sua **exclusão**.

---

## 👥 Atores
- **Gerente** → pode cadastrar, atualizar e remover Autores.
- **Operador** → pode apenas consultar os dados dos Autores.

---

## 📋 Campos Solicitados pelo Negócio
**Dados que deverão ser guardados:**
- Nome *
- Data de Nascimento *
- Nacionalidade *

> Campos com (*) são obrigatórios.

---

## ⚙️ Campos Lógicos
Dados não solicitados pela equipe de negócio, mas necessários para **controle da aplicação e auditoria**:
- ID — UUID
- Data de Cadastro
- Data da Última Atualização
- Usuário da Última Atualização

---

## 🧩 Regras de Negócio
- Não permitir cadastrar um **Autor** com o mesmo **Nome**, **Data de Nascimento** e **Nacionalidade**.
  > Se houver dois autores com essas informações idênticas, serão considerados duplicados e o cadastro será impedido.

- Não permitir **excluir um Autor** que possua algum livro cadastrado.

---

## 🔗 Contrato da API

### 📍 Cadastrar novo Autor
**Requisição**
```
URI: /autores
Método: POST
```

**Body:**
```json
{
  "nome": "string",
  "dataNascimento": "date",
  "nacionalidade": "string"
}
```

**Respostas:**

#### ✅ Sucesso
```
Código: 201 - Created
Header: Location - URI do recurso criado
```

#### ⚠️ Erro de Validação
```json
{
  "status": 422,
  "message": "Erro de Validação",
  "errors": [
    { "field": "nome", "error": "Nome é obrigatório" }
  ]
}
```

#### ❌ Autor Duplicado
```json
{
  "status": 409,
  "message": "Registro Duplicado",
  "errors": []
}
```

---

### 📍 Visualizar Detalhes do Autor
**Requisição**
```
URI: /autores/{ID}
Método: GET
```

**Respostas:**

#### ✅ Sucesso
```json
{
  "id": "uuid",
  "nome": "string",
  "dataNascimento": "date",
  "nacionalidade": "string"
}
```

#### ❌ Erro
```
Código: 404 - Not Found
```

---

### 📍 Excluir Autor
**Requisição**
```
URI: /autores/{ID}
Método: DELETE
```

**Respostas:**

#### ✅ Sucesso
```
Código: 204 - No Content
```

#### ⚠️ Erro
```json
{
  "status": 400,
  "message": "Erro na exclusão: registro está sendo utilizado.",
  "errors": []
}
```

---

### 📍 Pesquisar Autores
**Requisição**
```
URI: /autores
Query Params: nome, nacionalidade
Método: GET
```

**Respostas:**

#### ✅ Sucesso
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

### 📍 Atualizar Autor
**Requisição**
```
URI: /autores/{ID}
Método: PUT
```

**Body:**
```json
{
  "nome": "string",
  "dataNascimento": "date",
  "nacionalidade": "string"
}
```

**Respostas:**

#### ✅ Sucesso
```
Código: 204 - No Content
```

#### ⚠️ Erro de Validação
```json
{
  "status": 422,
  "message": "Erro de Validação",
  "errors": [
    { "field": "nome", "error": "Nome é obrigatório" }
  ]
}
```

#### ❌ Autor Duplicado
```json
{
  "status": 409,
  "message": "Registro Duplicado",
  "errors": []
}
```
# 📘 Cadastro de Livros

## 📝 Descrição
Deseja-se cadastrar os livros, bem como realizar suas **atualizações**, **consultas** e permitir sua **exclusão**.  
Ao consultar um livro, deverão estar disponíveis filtros de pesquisa para **busca paginada**.  
**Campos de busca:** título, gênero, ISBN, nome do autor e ano de publicação.

---

## 👥 Atores
- **Operador** e **Gerente** podem **consultar**, **cadastrar**, **atualizar** e **remover** livros.

---

## 📋 Campos Solicitados pelo Negócio
**Dados que deverão ser guardados:**
- ISBN *
- Título *
- Data de Publicação *
- Gênero
- Preço
- Autor *

> Campos com (*) são obrigatórios.

---

## ⚙️ Campos Lógicos
Dados não solicitados pela equipe de negócio, mas necessários para **controle da aplicação e auditoria**:
- ID — UUID
- Data de Cadastro
- Data da Última Atualização
- Usuário da Última Atualização

---

## 🧩 Regras de Negócio
- Não permitir cadastrar um **Livro** com o mesmo **ISBN** que outro.
- Se a **Data de Publicação** for **a partir de 2020**, o **Preço** deve ser informado obrigatoriamente.
- A **Data de Publicação** não pode ser uma data **futura**.

---

## 🔗 Contrato da API

### 📍 Cadastrar novo Livro
**Requisição**
```
URI: /livros
Método: POST
```

**Body:**
```json
{
  "isbn": "string",
  "titulo": "string",
  "dataPublicacao": "date",
  "genero": "enum",
  "preco": number,
  "id_autor": "uuid"
}
```

**Respostas:**

#### ✅ Sucesso
```
Código: 201 - Created
Header: Location - URI do recurso criado
```

#### ⚠️ Erro de Validação
```json
{
  "status": 422,
  "message": "Erro de Validação",
  "errors": [
    { "field": "titulo", "error": "Campo obrigatório" }
  ]
}
```

#### ❌ ISBN Duplicado
```json
{
  "status": 409,
  "message": "Isbn Duplicado",
  "errors": []
}
```

---

### 📍 Visualizar Detalhes do Livro
**Requisição**
```
URI: /livros/{ID}
Método: GET
```

**Respostas:**

#### ✅ Sucesso
```json
{
  "id": "uuid",
  "isbn": "string",
  "titulo": "string",
  "dataPublicacao": "date",
  "genero": "enum",
  "preco": number,
  "autor": {
    "nome": "string",
    "dataNascimento": "date",
    "nacionalidade": "string"
  }
}
```

#### ❌ Erro
```
Código: 404 - Not Found
```

---

### 📍 Excluir Livro
**Requisição**
```
URI: /livros/{ID}
Método: DELETE
```

**Respostas:**

#### ✅ Sucesso
```
Código: 204 - No Content
```

#### ⚠️ Erro
```
Código: 404 - Livro inexistente
```

---

### 📍 Pesquisar Livros
**Requisição**
```
URI: /livros
Query Params: isbn, titulo, nome autor, genero, ano de publicação
Método: GET
```

**Respostas:**

#### ✅ Sucesso
```json
[
  {
    "id": "uuid",
    "isbn": "string",
    "titulo": "string",
    "dataPublicacao": "date",
    "genero": "enum",
    "preco": number,
    "autor": {
      "nome": "string",
      "dataNascimento": "date",
      "nacionalidade": "string"
    }
  }
]
```

---

### 📍 Atualizar Livro
**Requisição**
```
URI: /livros/{ID}
Método: PUT
```

**Body:**
```json
{
  "isbn": "string",
  "titulo": "string",
  "dataPublicacao": "date",
  "genero": "enum",
  "preco": number,
  "id_autor": "uuid"
}
```

**Respostas:**

#### ✅ Sucesso
```
Código: 204 - No Content
```

#### ⚠️ Erro de Validação
```json
{
  "status": 422,
  "message": "Erro de Validação",
  "errors": [
    { "field": "titulo", "error": "Campo obrigatório" }
  ]
}
```

#### ❌ ISBN Duplicado
```json
{
  "status": 409,
  "message": "ISBN Duplicado",
  "errors": []
}
```
