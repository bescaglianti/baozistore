# API REST – Baozi Store

Trabalho da disciplina **Desenvolvimento Web Back-End** – Análise e Desenvolvimento de Sistemas (Uninter).
Aluna: **Beatriz Scaglianti** – RU 1093081

API REST para controlar clientes, produtos e pedidos de uma pequena loja de pão chinês (Baozi Store).

## Tecnologias
- Java 17
- Spring Boot 3.5 (Spring Web + Spring Data JPA)
- MySQL 8.4
- Postman (testes)

## Estrutura (padrão MVC do Spring)
```
src/main/java/com/baozistore/
├── BaozistoreApplication.java
├── model/        Cliente, Produto, Pedido
├── repository/   ClienteRepository, ProdutoRepository, PedidoRepository
└── controller/   ClienteController, ProdutoController, PedidoController
```

## Entidades
| Entidade | Campos |
|---|---|
| Produto | id (Long), nome (String), preco (BigDecimal), estoque (Boolean) |
| Cliente | id (Long), nome (String), clienteDesde (LocalDate) |
| Pedido  | id (Long), clienteId (Long), produtoId (Long), quantidade (Integer) |

## Endpoints
| Método | Endpoint | Ação |
|---|---|---|
| POST | /clientes, /produtos, /pedidos | Criar |
| GET | /clientes, /produtos, /pedidos | Listar todos |
| GET | /clientes/{id}, /produtos/{id}, /pedidos/{id} | Consultar por ID |
| PUT | /clientes/{id}, /produtos/{id}, /pedidos/{id} | Atualizar (opcional) |
| DELETE | /clientes/{id}, /produtos/{id}, /pedidos/{id} | Apagar |

Ao criar um pedido, a API confere se o `clienteId` e o `produtoId` existem e se a quantidade é maior que zero. Se algo estiver errado, ela responde 400.

## Como executar
1. Ter o MySQL rodando em `localhost:3306` (o banco `baozistore` é criado automaticamente).
2. Em `src/main/resources/application.properties`, trocar `COLOQUE_SUA_SENHA_AQUI` pela senha do usuário `root`.
3. Importar no Eclipse: *File > Import > Maven > Existing Maven Projects*.
4. Rodar `BaozistoreApplication` (*Run As > Java Application*). A API sobe em `http://localhost:8080`.
5. Importar no Postman a coleção `BaoziStore.postman_collection.json` para testar.
