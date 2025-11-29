-- Создание таблицы charms_float
CREATE TABLE IF NOT EXISTS market.charms_float (
    class_id BIGINT NOT NULL,
    instance_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    listing_id BIGINT NOT NULL,
    pattern INTEGER NOT NULL,
    a BIGINT NOT NULL,
    d BIGINT NOT NULL,
    m BIGINT NOT NULL,

    PRIMARY KEY (listing_id),

    CONSTRAINT fk_charms_float_charms_info
        FOREIGN KEY (class_id, instance_id)
        REFERENCES market.charms_info(class_id, instance_id),

    CONSTRAINT fk_charms_float_listing_id
        FOREIGN KEY (listing_id)
        REFERENCES market.charm_sale_offers(listing_id)
);

-- Создание индексов
CREATE INDEX IF NOT EXISTS idx_charms_float_class_id_instance_id ON market.charms_float(class_id, instance_id);
CREATE INDEX IF NOT EXISTS idx_charms_float_item_id ON market.charms_float(item_id);

-- Комментарии к таблице и колонкам
COMMENT ON TABLE market.charms_float IS 'Информация о float значениях предметов';
COMMENT ON COLUMN market.charms_float.class_id IS 'Уникальный идентификатор класса предмета';
COMMENT ON COLUMN market.charms_float.instance_id IS 'Уникальный идентификатор экземпляра предмета';
COMMENT ON COLUMN market.charms_float.item_id IS 'Уникальный идентификатор предмета';
COMMENT ON COLUMN market.charms_float.listing_id IS 'Уникальный идентификатор листинга';
COMMENT ON COLUMN market.charms_float.pattern IS 'Номер паттерна (шаблона) предмета';
COMMENT ON COLUMN market.charms_float.a IS 'Параметр A инспекта';
COMMENT ON COLUMN market.charms_float.d IS 'Параметр D инспекта';
COMMENT ON COLUMN market.charms_float.m IS 'Параметр M инспекта';
