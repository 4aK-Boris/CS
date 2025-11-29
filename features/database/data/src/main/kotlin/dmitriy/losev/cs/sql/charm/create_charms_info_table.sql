-- Создание таблицы charms_info
CREATE TABLE IF NOT EXISTS market.charms_info (
    class_id BIGINT NOT NULL,
    instance_id BIGINT NOT NULL,
    name VARCHAR(128) NOT NULL,
    last_check_time TIMESTAMP NOT NULL DEFAULT (NOW() - INTERVAL '5 minutes'),

    PRIMARY KEY (class_id, instance_id)
);

-- Создание индексов
CREATE INDEX IF NOT EXISTS idx_charms_info_class_id ON market.charms_info(class_id);
CREATE INDEX IF NOT EXISTS idx_charms_info_instance_id ON market.charms_info(instance_id);
CREATE INDEX IF NOT EXISTS idx_charms_info_name ON market.charms_info(name);
CREATE INDEX IF NOT EXISTS idx_charms_info_last_check_time ON market.charms_info(last_check_time);

-- Комментарии к таблице и колонкам
COMMENT ON TABLE market.charms_info IS 'Информация о брелках CS2';
COMMENT ON COLUMN market.charms_info.class_id IS 'Уникальный идентификатор класса предмета';
COMMENT ON COLUMN market.charms_info.instance_id IS 'Уникальный идентификатор экземпляра предмета';
COMMENT ON COLUMN market.charms_info.name IS 'Название брелка';
COMMENT ON COLUMN market.charms_info.last_check_time IS 'Время последней проверки предмета';