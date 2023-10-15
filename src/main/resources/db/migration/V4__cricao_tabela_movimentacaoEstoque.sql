CREATE TABLE IF NOT EXISTS tb_movimentacao_estoque(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    produto_id BIGINT NOT NULL,
    almoxarifado_id BIGINT NOT NULL,
    quantidade DOUBLE NOT NULL,
    tipoMovimentacao ENUM('ENTRADA', 'RETIRADA', 'AJUSTE') NOT NULL,
    observacao TEXT,
    nomePessoa VARCHAR(100) NOT NULL,
    funcionario_id BIGINT,
    dataHoraMovimentacao DATETIME NOT NULL,
    FOREIGN KEY(produto_id) REFERENCES tb_produtos(id),
    FOREIGN KEY(almoxarifado_id) REFERENCES tb_almoxarifados(id),
    FOREIGN KEY(funcionario_id) REFERENCES tb_funcionarios(id)
);