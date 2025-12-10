-- Создание таблицы task_execution_logs
CREATE TABLE IF NOT EXISTS system.task_execution_logs (
    id BIGSERIAL PRIMARY KEY,
    task_id VARCHAR(128) NOT NULL,
    status VARCHAR(16) NOT NULL,
    started_at TIMESTAMP NOT NULL,
    finished_at TIMESTAMP,
    duration_millis BIGINT,
    error_message TEXT,
    error_count INT NOT NULL DEFAULT 0,
    success_count INT NOT NULL DEFAULT 0,
    total_count INT NOT NULL DEFAULT 0,
    data TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Индексы для быстрого поиска
CREATE INDEX IF NOT EXISTS idx_task_execution_logs_task_id ON system.task_execution_logs(task_id);
CREATE INDEX IF NOT EXISTS idx_task_execution_logs_status ON system.task_execution_logs(status);
CREATE INDEX IF NOT EXISTS idx_task_execution_logs_started_at ON system.task_execution_logs(started_at);
CREATE INDEX IF NOT EXISTS idx_task_execution_logs_task_id_started_at ON system.task_execution_logs(task_id, started_at DESC);

-- Комментарии к таблице и колонкам
COMMENT ON TABLE system.task_execution_logs IS 'Таблица для хранения логов выполнения задач планировщика';
COMMENT ON COLUMN system.task_execution_logs.id IS 'Уникальный идентификатор записи';
COMMENT ON COLUMN system.task_execution_logs.task_id IS 'Идентификатор задачи';
COMMENT ON COLUMN system.task_execution_logs.status IS 'Статус выполнения (STARTED, COMPLETED, FAILED, SKIPPED)';
COMMENT ON COLUMN system.task_execution_logs.started_at IS 'Время начала выполнения';
COMMENT ON COLUMN system.task_execution_logs.finished_at IS 'Время завершения выполнения';
COMMENT ON COLUMN system.task_execution_logs.duration_millis IS 'Длительность выполнения в миллисекундах';
COMMENT ON COLUMN system.task_execution_logs.error_message IS 'Сообщение об ошибке';
COMMENT ON COLUMN system.task_execution_logs.error_count IS 'Количество ошибок';
COMMENT ON COLUMN system.task_execution_logs.success_count IS 'Количество успешных операций';
COMMENT ON COLUMN system.task_execution_logs.total_count IS 'Общее количество операций';
COMMENT ON COLUMN system.task_execution_logs.data IS 'Данные результата выполнения (JSON)';
COMMENT ON COLUMN system.task_execution_logs.created_at IS 'Дата и время создания записи';

