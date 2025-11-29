-- Создание таблицы steam_accounts
CREATE TABLE IF NOT EXISTS steam.steam_accounts (
    account_name VARCHAR(128) NOT NULL,
    shared_secret VARCHAR(128) NOT NULL,
    serial_number VARCHAR(64) NOT NULL,
    revocation_code VARCHAR(16) NOT NULL,
    uri VARCHAR(512) NOT NULL,
    server_time BIGINT NOT NULL,
    token_gid VARCHAR(64) NOT NULL,
    identity_secret VARCHAR(128) NOT NULL,
    secret_1 VARCHAR(128) NOT NULL,
    status INTEGER NOT NULL,
    device_id VARCHAR(128) NOT NULL,
    fully_enrolled BOOLEAN NOT NULL,
    steam_id BIGINT NOT NULL,
    access_token TEXT NOT NULL,
    refresh_token TEXT NOT NULL,
    session_id VARCHAR(128),

    PRIMARY KEY (steam_id)
);

-- Создание индексов
CREATE INDEX IF NOT EXISTS idx_steam_accounts_name ON steam.steam_accounts(account_name);
CREATE INDEX IF NOT EXISTS idx_steam_accounts_steam_id ON steam.steam_accounts(steam_id);
CREATE INDEX IF NOT EXISTS idx_steam_accounts_status ON steam.steam_accounts(status);

-- Комментарии к таблице и колонкам
COMMENT ON TABLE steam.steam_accounts IS 'Таблица для хранения Steam аккаунтов с данными аутентификации';
COMMENT ON COLUMN steam.steam_accounts.account_name IS 'Имя Steam аккаунта';
COMMENT ON COLUMN steam.steam_accounts.shared_secret IS 'Shared secret для Steam Guard';
COMMENT ON COLUMN steam.steam_accounts.serial_number IS 'Серийный номер';
COMMENT ON COLUMN steam.steam_accounts.revocation_code IS 'Код отзыва';
COMMENT ON COLUMN steam.steam_accounts.uri IS 'URI для TOTP';
COMMENT ON COLUMN steam.steam_accounts.server_time IS 'Время сервера';
COMMENT ON COLUMN steam.steam_accounts.token_gid IS 'GID токена';
COMMENT ON COLUMN steam.steam_accounts.identity_secret IS 'Identity secret';
COMMENT ON COLUMN steam.steam_accounts.secret_1 IS 'Дополнительный секрет';
COMMENT ON COLUMN steam.steam_accounts.status IS 'Статус аккаунта';
COMMENT ON COLUMN steam.steam_accounts.device_id IS 'ID устройства';
COMMENT ON COLUMN steam.steam_accounts.fully_enrolled IS 'Флаг полной регистрации в Steam Guard';
COMMENT ON COLUMN steam.steam_accounts.steam_id IS 'Steam ID пользователя';
COMMENT ON COLUMN steam.steam_accounts.access_token IS 'Access токен для авторизации';
COMMENT ON COLUMN steam.steam_accounts.refresh_token IS 'Refresh токен для обновления сессии';
COMMENT ON COLUMN steam.steam_accounts.session_id IS 'ID сессии (может быть null)';