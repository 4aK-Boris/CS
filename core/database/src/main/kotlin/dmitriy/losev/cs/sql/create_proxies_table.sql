-- Создание таблицы proxies
CREATE TABLE IF NOT EXISTS network.proxies (
    host VARCHAR(32) NOT NULL,
    port INTEGER NOT NULL,
    login BYTEA NOT NULL,
    password BYTEA NOT NULL,
    steam_id BIGINT,

    PRIMARY KEY (host, port)
);

-- Создание индексов
CREATE INDEX IF NOT EXISTS idx_proxies_steam_id ON network.proxies(steam_id);

-- Комментарии к таблице и колонкам
COMMENT ON TABLE network.proxies IS 'Таблица для хранения прокси-серверов';
COMMENT ON COLUMN network.proxies.host IS 'Адрес хоста прокси-сервера';
COMMENT ON COLUMN network.proxies.port IS 'Порт прокси-сервера';
COMMENT ON COLUMN network.proxies.login IS 'Логин для авторизации на прокси-сервере';
COMMENT ON COLUMN network.proxies.password IS 'Пароль для авторизации на прокси-сервере';
COMMENT ON COLUMN network.proxies.steam_id IS 'Steam ID пользователя, закрепленного за прокси';
