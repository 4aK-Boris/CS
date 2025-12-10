-- Выдача прав на использование схемы system пользователю cs
GRANT USAGE ON SCHEMA system TO cs;

-- Выдача прав на чтение и запись для всех существующих таблиц в схеме system
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA system TO cs;

-- Выдача прав на чтение и запись для всех будущих таблиц в схеме system
ALTER DEFAULT PRIVILEGES IN SCHEMA system GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO cs;

-- Выдача прав на использование последовательностей (sequences)
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA system TO cs;
ALTER DEFAULT PRIVILEGES IN SCHEMA system GRANT USAGE, SELECT ON SEQUENCES TO cs;

-- Комментарий
COMMENT ON SCHEMA system IS 'Права на чтение и запись для пользователя cs в схеме system';

