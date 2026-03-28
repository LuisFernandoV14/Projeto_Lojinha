# Projeto Lojinha


## Funcionalidades

O intuito do projeto é ser um sistema que simula uma loja, com criação e pagamento de pedidos. Para isso, usamos Java e ligamos ao MySQL usando JDBC.
Era também um dos intuitos do projeto aprender sobre padrão de desenvolvimento Singleton.

## Uso do Singleton

Usamos o padrão de desenvolvimento Singleton em 3 classes: "ShoppingService", "PaymentService" e "IdManager". Decidimos separar em 3 classes para não violar o princípio de Responsabilidade Única. <br>
O primeiro Singleton, "ShoppingService", lida com a lógica de criação e visualização de pedidos. <br>
O segundo Singleton, "PaymentService", lida com lógica de criação e visualização de pagamentos. <br>
O terceiro Singleton, "IdManager", lida com o banco de dados, ele recebe valores dos dois primeiros Singletons e insere ou busca valores da tabelas do SQL. <br>

Todas essas classes seguem o modelo de ter um construtor privado e um método `.getInstance()`, caracterizando assim um Singleton.

## Sobre o projeto

O Projeto Lojinha é um projeto da disciplina Arquitetura de Software, ministrada pelo professor JefFerson Salomão, no quinto semestre de Engenharia de Software.
