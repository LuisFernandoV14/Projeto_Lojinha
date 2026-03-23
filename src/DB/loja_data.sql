-- Endereços
INSERT INTO Endereco (end_estado, end_cidade, end_bairro, end_numero) VALUES 
('SP', 'São Paulo', 'Liberdade', 123),
('RJ', 'Rio de Janeiro', 'Copacabana', 450),
('MG', 'Belo Horizonte', 'Savassi', 10);

-- Clientes
INSERT INTO Cliente (nom_cliente, CPF, dat_nascimento, id_endereco) VALUES 
('Tanjiro Kamado', '111.222.333-44', '2000-07-14', 1),
('Nezuko Kamado', '222.333.444-55', '2002-12-28', 1),
('Zenitsu Agatsuma', '333.444.555-66', '2000-09-03', 2),
('Inosuke Hashibira', '444.555.666-77', '2000-04-22', 3);

-- Produtos
INSERT INTO Produto (nom_produto, preco) VALUES 
('Teclado Mecânico RGB', 350.00),
('Mouse Gamer 16000 DPI', 220.50),
('Monitor 144Hz 24"', 1200.00),
('Headset 7.1 Surround', 400.00),
('Cadeira Gamer Ergonômica', 1500.00),
('Webcam Full HD', 280.00);

-- Pedidos
INSERT INTO Pedido (id_cliente, dat_pedido) VALUES 
(1, '2026-03-20'),
(2, '2026-03-21'),
(1, '2026-03-22'),
(3, '2026-03-23');

-- Itens dos Pedidos
INSERT INTO Itens_Pedido (id_pedido, id_produto, num_quantidade) VALUES 
(1, 1, 1), (1, 2, 1), -- Pedido 1: Teclado + Mouse
(2, 3, 1), (2, 6, 2), -- Pedido 2: Monitor + 2 Webcams
(3, 5, 1),            -- Pedido 3: Cadeira
(4, 4, 1);            -- Pedido 4: Headset

-- Pagamentos
INSERT INTO Pagamento (id_pedido, val_pagamento, dat_pagamento) VALUES 
(1, 570.50, NULL),
(2, 1760.00, NULL),
(3, 1500.00, NULL),
(4, 400.00, '2026-03-23');

-- (Para conferir se o JOIN no Java vai funcionar)
SELECT 
    p.id_pedido,
    c.nom_cliente,
    pr.nom_produto,
    ip.num_quantidade,
    pr.preco AS valor_unitario
FROM Pedido p
JOIN Cliente c ON p.id_cliente = c.id_cliente
JOIN Itens_Pedido ip ON p.id_pedido = ip.id_pedido
JOIN Produto pr ON ip.id_produto = pr.id_produto;