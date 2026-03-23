CREATE DATABASE IF NOT EXISTS Lojinha;
USE Lojinha;

CREATE TABLE IF NOT EXISTS Endereco (
	id_endereco INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    end_estado VARCHAR(50),
    end_cidade VARCHAR(50),
    end_bairro VARCHAR(50),
    end_numero INT
);

CREATE TABLE IF NOT EXISTS Cliente (
	id_cliente INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nom_cliente VARCHAR(255),
    CPF VARCHAR(14),
    dat_nascimento DATE,
    id_endereco INT UNSIGNED,
    
    CONSTRAINT fk_cliente_endereco FOREIGN KEY (id_endereco) REFERENCES Endereco (id_endereco)
    ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS Produto (
	id_produto INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nom_produto VARCHAR(255),
    preco DECIMAL(10, 2) 
);

CREATE TABLE IF NOT EXISTS Pedido ( 
	id_pedido INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT UNSIGNED,
    dat_pedido DATE,
    id_itens_pedido INT UNSIGNED,
    
    CONSTRAINT fk_pedido_cliente FOREIGN KEY (id_cliente) REFERENCES Cliente (id_cliente)
    ON UPDATE CASCADE ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS Itens_Pedido (
	id_itens_pedido INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	id_pedido INT UNSIGNED, 
    id_produto INT UNSIGNED,
    num_quantidade INT UNSIGNED,
    
    CONSTRAINT fk_itensPedido_pedidoid FOREIGN KEY (id_pedido) REFERENCES Pedido (id_pedido)
    ON UPDATE CASCADE ON DELETE CASCADE
);

ALTER TABLE Pedido 
ADD CONSTRAINT fk_pedido_itensPedido FOREIGN KEY (id_itens_pedido) REFERENCES Itens_Pedido (id_itens_pedido)
ON UPDATE CASCADE ON DELETE RESTRICT;

CREATE TABLE IF NOT EXISTS Pagamento (
	id_pagamento INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT UNSIGNED,
    val_pagamento DATE,
    dat_pagamento DATE,
    
    CONSTRAINT fk_pagamento_pedido FOREIGN KEY (id_pedido) REFERENCES Pedido (id_pedido)
    ON UPDATE CASCADE ON DELETE RESTRICT
)