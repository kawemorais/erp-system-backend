USE db_erp_system;

CREATE TABLE IF NOT EXISTS tb_enderecos(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    cep VARCHAR(8) NOT NULL,
    logradouro VARCHAR(200) NOT NULL,
    bairro VARCHAR(200) NOT NULL,
    cidade VARCHAR(200) NOT NULL,
    uf VARCHAR(2) NOT NULL,
    numero VARCHAR(10) NOT NULL,
    complemento VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS tb_fornecedores(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nomeFantasia VARCHAR(255) NOT NULL,
    razaoSocial VARCHAR(255) NOT NULL,
    cnpj VARCHAR(14) NOT NULL,
    inscricaoEstadual VARCHAR(9) NOT NULL,
    inscricaoMunicipal VARCHAR(11) NOT NULL,
    telefone VARCHAR(10),
    celular VARCHAR(11),
    email VARCHAR(255) NOT NULL,
	isAtivo BOOLEAN NOT NULL,
    endereco_id BIGINT,
    FOREIGN KEY(endereco_id) REFERENCES tb_enderecos(id)
);

CREATE TABLE IF NOT EXISTS tb_clientes(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nomeFantasia VARCHAR(255) NOT NULL,
    razaoSocial VARCHAR(255) NOT NULL,
    cnpj VARCHAR(14) NOT NULL,
    inscricaoEstadual VARCHAR(9) NOT NULL,
    inscricaoMunicipal VARCHAR(11) NOT NULL,
    telefone VARCHAR(10),
    celular VARCHAR(11),
    email VARCHAR(255) NOT NULL,
	isAtivo BOOLEAN NOT NULL,
    endereco_id BIGINT,
    FOREIGN KEY(endereco_id) REFERENCES tb_enderecos(id)
);

CREATE TABLE IF NOT EXISTS tb_cargos(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(150) NOT NULL
);

CREATE TABLE IF NOT EXISTS tb_funcoes(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(150) NOT NULL
);

CREATE TABLE IF NOT EXISTS tb_setores(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(150) NOT NULL
);

CREATE TABLE IF NOT EXISTS tb_funcionarios(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    rg VARCHAR(8) NOT NULL,
    reservista VARCHAR(12),
    dataNascimento DATE NOT NULL,
	sexo ENUM('FEMININO', 'MASCULINO', 'PREFERE_NAO_DIZER'),
    telefone VARCHAR(10),
    celular VARCHAR(11),
    email VARCHAR(255) NOT NULL,
    matricula VARCHAR(20),
    dataAdmissao DATE NOT NULL,
    salarioBase DECIMAL(15,4),
	isAtivo BOOLEAN NOT NULL,
    endereco_id BIGINT,
    setor_id BIGINT NOT NULL,
    cargo_id BIGINT NOT NULL,
    funcao_id BIGINT NOT NULL,
    FOREIGN KEY(endereco_id) REFERENCES tb_enderecos(id),
    FOREIGN KEY(setor_id) REFERENCES tb_setores(id),
    FOREIGN KEY(cargo_id) REFERENCES tb_cargos(id),
    FOREIGN KEY(funcao_id) REFERENCES tb_funcoes(id)
);

CREATE TABLE IF NOT EXISTS tb_unidades(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(30) NOT NULL,
    descricao VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS tb_categorias_produto(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    codigo VARCHAR(10) NOT NULL,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS tb_usuarios_sistema(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    usuario VARCHAR(50) NOT NULL,
    senha TEXT NOT NULL,
    isAtivo BOOLEAN,
    funcionario_id BIGINT NOT NULL,
    FOREIGN KEY(funcionario_id) REFERENCES tb_funcionarios(id)
);

CREATE TABLE IF NOT EXISTS tb_fichas_tecnicas(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    custoTotal DECIMAL(15,4),
    dataCriacao DATETIME NOT NULL,
    dataUltimaAlteracao DATETIME,
    funcionario_id BIGINT NOT NULL,
    FOREIGN KEY(funcionario_id) REFERENCES tb_funcionarios(id)
);

CREATE TABLE IF NOT EXISTS tb_produtos(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    codigo VARCHAR(10) UNIQUE NOT NULL,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    peso DOUBLE,
    dataCriacao DATETIME NOT NULL,
    dataAlteracao DATETIME NOT NULL,
    isAtivo BOOLEAN NOT NULL,
    precoUltimaCompra DECIMAL(15,4),
    custoProduto DECIMAL(15,4) DEFAULT 0,
    unidade_id BIGINT NOT NULL,
    categoria_id BIGINT NOT NULL,
    fornecedor_id BIGINT,
    fichaTecnica_id BIGINT,
    funcionario_id BIGINT NOT NULL,
    FOREIGN KEY(unidade_id) REFERENCES tb_unidades(id),
    FOREIGN KEY(categoria_id) REFERENCES tb_categorias_produto(id),
    FOREIGN KEY(fornecedor_id) REFERENCES tb_fornecedores(id),
    FOREIGN KEY(fichaTecnica_id) REFERENCES tb_fichas_tecnicas(id),
    FOREIGN KEY(funcionario_id) REFERENCES tb_funcionarios(id)
);

CREATE TABLE IF NOT EXISTS tb_itens_fichatecnica(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    produto_id BIGINT NOT NULL,
    fichaTecnica_id BIGINT NOT NULL,
    quantidade DOUBLE,
    subTotal DECIMAL(15,4),
    FOREIGN KEY(produto_id) REFERENCES tb_produtos(id),
    FOREIGN KEY(fichaTecnica_id) REFERENCES tb_fichas_tecnicas(id)
);

CREATE TABLE IF NOT EXISTS tb_almoxarifados(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    codigo VARCHAR(10) NOT NULL,
    nome VARCHAR(100) NOT NULL,
    isAtivo BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS tb_estoques_produto(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    quantidade DOUBLE NOT NULL,
    valorUnitarioProdutoEstoque DECIMAL(15,4) NOT NULL,
    valorTotalProdutoEstoque DECIMAL(15,4) NOT NULL,
    estoqueMinimo DOUBLE,
    estoqueMaximo DOUBLE,
    estoquePontoPedido DOUBLE,
    locCorredor VARCHAR(100),
    locPrateleira VARCHAR(100),
    locBox VARCHAR(100),
    almoxarifado_id BIGINT NOT NULL,
    produto_id BIGINT NOT NULL,
    FOREIGN KEY(almoxarifado_id) REFERENCES tb_almoxarifados(id),
    FOREIGN KEY(produto_id) REFERENCES tb_produtos(id)
);