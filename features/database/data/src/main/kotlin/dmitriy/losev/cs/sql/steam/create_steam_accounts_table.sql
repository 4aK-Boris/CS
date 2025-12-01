-- Создание таблицы steam_accounts
CREATE TABLE IF NOT EXISTS steam.steam_accounts (
    steam_id BIGINT NOT NULL,
    login VARCHAR(64) NOT NULL,
    password BYTEA NOT NULL,
    shared_secret BYTEA NOT NULL,
    revocation_code BYTEA NOT NULL,
    identity_secret BYTEA NOT NULL,
    device_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (steam_id)
);

-- Создание индексов
CREATE INDEX IF NOT EXISTS idx_steam_accounts_login ON steam.steam_accounts(login);

-- Комментарии к таблице и колонкам
COMMENT ON TABLE steam.steam_accounts IS 'Таблица для хранения Steam аккаунтов';
COMMENT ON COLUMN steam.steam_accounts.steam_id IS 'Уникальный Steam ID аккаунта';
COMMENT ON COLUMN steam.steam_accounts.login IS 'Логин Steam аккаунта';
COMMENT ON COLUMN steam.steam_accounts.password IS 'Зашифрованный пароль аккаунта';
COMMENT ON COLUMN steam.steam_accounts.shared_secret IS 'Общий секрет для Steam Guard';
COMMENT ON COLUMN steam.steam_accounts.revocation_code IS 'Код отзыва для Steam Guard';
COMMENT ON COLUMN steam.steam_accounts.identity_secret IS 'Секрет идентификации для подтверждений';
COMMENT ON COLUMN steam.steam_accounts.device_id IS 'UUID устройства для Steam Guard';
COMMENT ON COLUMN steam.steam_accounts.created_at IS 'Дата и время создания записи';
