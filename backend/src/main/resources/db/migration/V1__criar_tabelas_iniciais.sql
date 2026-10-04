CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
);

CREATE TABLE solicitacao (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    descricao TEXT NOT NULL,
    categoria VARCHAR(30) NOT NULL,
    status VARCHAR(20) NOT NULL,
    data_criacao DATETIME NOT NULL,
    usuario_id BIGINT NOT NULL,
    ativo TINYINT(1) NOT NULL DEFAULT 1,

    INDEX idx_solicitacao_usuario (usuario_id),
    INDEX idx_solicitacao_data (data_criacao),
    INDEX idx_solicitacao_categoria (categoria),
    INDEX idx_solicitacao_status (status),

    CONSTRAINT fk_solicitacao_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuario(id)
);