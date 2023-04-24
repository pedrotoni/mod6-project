CREATE TABLE IF NOT EXISTS cliente (
    id_cliente INTEGER PRIMARY KEY,
    nome VARCHAR(100),
    cpf VARCHAR(11)
);

CREATE TABLE IF NOT EXISTS produto (
    id_produto INTEGER PRIMARY KEY,
    nome VARCHAR(100),
    descricao VARCHAR(255),
    preco NUMERIC(10,2)
);

CREATE TABLE IF NOT EXISTS carrinho (
    id_carrinho INTEGER PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS item_venda (
    id_item INTEGER PRIMARY KEY,
    id_produto INTEGER,
    id_carrinho INTEGER,
    qtd INTEGER,
    FOREIGN KEY (id_produto) REFERENCES produto(id_produto),
    FOREIGN KEY (id_carrinho) REFERENCES carrinho(id_carrinho)
);

CREATE TABLE IF NOT EXISTS venda (
    id_venda INTEGER PRIMARY KEY,
    id_cliente INTEGER,
    valor_total NUMERIC(10,2),
    FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente)
);