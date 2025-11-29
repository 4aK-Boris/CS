-- Выдача прав на использование схемы network пользователю cs
GRANT USAGE ON SCHEMA network TO cs;

-- Выдача прав на чтение и запись для всех существующих таблиц в схеме network
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA network TO cs;

-- Выдача прав на чтение и запись для всех будущих таблиц в схеме network
ALTER DEFAULT PRIVILEGES IN SCHEMA network GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO cs;

-- Выдача прав на использование последовательностей (sequences)
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA network TO cs;
ALTER DEFAULT PRIVILEGES IN SCHEMA network GRANT USAGE, SELECT ON SEQUENCES TO cs;

-- Комментарий
COMMENT ON SCHEMA network IS 'Права на чтение и запись для пользователя cs в схеме network';
