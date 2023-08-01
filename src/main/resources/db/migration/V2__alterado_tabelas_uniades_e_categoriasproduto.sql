USE db_erp_system;

ALTER TABLE tb_unidades
    MODIFY COLUMN nome VARCHAR(30) UNIQUE NOT NULL;

ALTER TABLE tb_categorias_produto
    MODIFY COLUMN codigo VARCHAR(10) UNIQUE NOT NULL;

ALTER TABLE tb_itens_fichatecnica
    MODIFY COLUMN quantidade DOUBLE NOT NULL,
    MODIFY COLUMN subTotal DECIMAL(15,4) NOT NULL;

ALTER TABLE tb_produtos
    ADD COLUMN status INTEGER NOT NULL;