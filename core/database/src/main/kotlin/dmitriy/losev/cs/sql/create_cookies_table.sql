-- Создание таблицы cookies
CREATE TABLE IF NOT EXISTS network.cookies (
    steam_id BIGINT NOT NULL,
    name VARCHAR(256) NOT NULL,
    value BYTEA NOT NULL,
    encoding INTEGER NOT NULL,
    max_age INTEGER,
    expires BIGINT,
    domain VARCHAR(512),
    path VARCHAR(512),
    secure BOOLEAN NOT NULL,
    http_only BOOLEAN NOT NULL,
    extensions TEXT NOT NULL,

    PRIMARY KEY (steam_id, name, domain, path)
);

-- Создание индексов
CREATE INDEX IF NOT EXISTS idx_cookies_steam_id ON network.cookies(steam_id);
CREATE INDEX IF NOT EXISTS idx_cookies_domain ON network.cookies(domain);
CREATE INDEX IF NOT EXISTS idx_cookies_expires ON network.cookies(expires);

-- Комментарии к таблице и колонкам
COMMENT ON TABLE network.cookies IS 'Таблица для хранения HTTP cookies для Steam аккаунтов';
COMMENT ON COLUMN network.cookies.steam_id IS 'Steam ID пользователя, которому принадлежат cookies';
COMMENT ON COLUMN network.cookies.name IS 'Имя cookie';
COMMENT ON COLUMN network.cookies.value IS 'Значение cookie';
COMMENT ON COLUMN network.cookies.encoding IS 'Тип кодирования cookie (ordinal из CookieEncoding)';
COMMENT ON COLUMN network.cookies.max_age IS 'Максимальный возраст cookie в секундах';
COMMENT ON COLUMN network.cookies.expires IS 'Время истечения cookie (timestamp в миллисекундах)';
COMMENT ON COLUMN network.cookies.domain IS 'Домен, для которого действует cookie';
COMMENT ON COLUMN network.cookies.path IS 'Путь, для которого действует cookie';
COMMENT ON COLUMN network.cookies.secure IS 'Флаг безопасности (только HTTPS)';
COMMENT ON COLUMN network.cookies.http_only IS 'Флаг HttpOnly (недоступен для JavaScript)';
COMMENT ON COLUMN network.cookies.extensions IS 'Дополнительные расширения cookie в формате JSON';
