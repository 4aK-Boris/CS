-- Выдача прав на использование схемы steam пользователю cs
GRANT USAGE ON SCHEMA steam TO cs;

-- Выдача прав на чтение и запись для всех существующих таблиц в схеме steam
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA steam TO cs;

-- Выдача прав на чтение и запись для всех будущих таблиц в схеме steam
ALTER DEFAULT PRIVILEGES IN SCHEMA steam GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO cs;

-- Выдача прав на использование последовательностей (sequences)
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA steam TO cs;
ALTER DEFAULT PRIVILEGES IN SCHEMA steam GRANT USAGE, SELECT ON SEQUENCES TO cs;

-- Комментарий
COMMENT ON SCHEMA steam IS 'Права на чтение и запись для пользователя cs в схеме steam';