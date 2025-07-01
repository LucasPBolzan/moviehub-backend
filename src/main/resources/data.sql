-- Criar tabela de favoritos
CREATE TABLE IF NOT EXISTS favorites (
                                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         movie_id BIGINT NOT NULL,
                                         user_name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY unique_favorite (movie_id, user_name)
    );

-- Dados de exemplo
INSERT INTO favorites (movie_id, user_name, created_at) VALUES
                                                            (1, 'João Silva', '2024-01-15 10:30:00'),
                                                            (2, 'João Silva', '2024-01-16 14:20:00'),
                                                            (3, 'Maria Santos', '2024-01-17 16:45:00');
