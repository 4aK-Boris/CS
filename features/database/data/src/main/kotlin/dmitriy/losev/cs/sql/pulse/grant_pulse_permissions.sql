-- Выдача прав на использование схемы pulse пользователю cs
GRANT USAGE ON SCHEMA pulse TO cs;

-- Выдача прав на чтение и запись для всех существующих таблиц в схеме pulse
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA pulse TO cs;

-- Выдача прав на чтение и запись для всех будущих таблиц в схеме pulse
ALTER DEFAULT PRIVILEGES IN SCHEMA pulse GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO cs;

-- Выдача прав на использование последовательностей (sequences)
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA pulse TO cs;
ALTER DEFAULT PRIVILEGES IN SCHEMA pulse GRANT USAGE, SELECT ON SEQUENCES TO cs;

-- Комментарий
COMMENT ON SCHEMA pulse IS 'Права на чтение и запись для пользователя cs в схеме pulse';