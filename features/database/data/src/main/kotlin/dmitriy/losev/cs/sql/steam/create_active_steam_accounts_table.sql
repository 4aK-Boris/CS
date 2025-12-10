-- Создание таблицы active_steam_accounts
CREATE TABLE IF NOT EXISTS steam.active_steam_accounts (
    steam_id BIGINT NOT NULL,
    market_csgo_api_token BYTEA NOT NULL,
    access_token TEXT NOT NULL,
    refresh_token BYTEA NOT NULL,
    session_id VARCHAR(24) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    access_token_updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    refresh_token_updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    cs_float_token_updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (steam_id),
    FOREIGN KEY (steam_id) REFERENCES steam.steam_accounts(steam_id) ON DELETE CASCADE
);

-- Комментарии к таблице и колонкам
COMMENT ON TABLE steam.active_steam_accounts IS 'Таблица для хранения активных Steam аккаунтов с токенами доступа';
COMMENT ON COLUMN steam.active_steam_accounts.steam_id IS 'Уникальный Steam ID аккаунта (ссылается на steam.steam_accounts)';
COMMENT ON COLUMN steam.active_steam_accounts.market_csgo_api_token IS 'API токен для CS:GO маркета';
COMMENT ON COLUMN steam.active_steam_accounts.access_token IS 'Токен доступа для Steam API';
COMMENT ON COLUMN steam.active_steam_accounts.refresh_token IS 'Токен обновления для продления сессии';
COMMENT ON COLUMN steam.active_steam_accounts.session_id IS 'Идентификатор сессии';
COMMENT ON COLUMN steam.active_steam_accounts.created_at IS 'Дата и время создания записи';
COMMENT ON COLUMN steam.active_steam_accounts.access_token_updated_at IS 'Дата и время последнего обновления access токена';
COMMENT ON COLUMN steam.active_steam_accounts.refresh_token_updated_at IS 'Дата и время последнего обновления refresh токена';
COMMENT ON COLUMN steam.active_steam_accounts.cs_float_token_updated_at IS 'Дата и время последнего обновления CS Float токена';
