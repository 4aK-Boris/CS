-- Создание таблицы charm_sale_offers
CREATE TABLE IF NOT EXISTS market.charm_sale_offers (
    class_id BIGINT NOT NULL,
    instance_id BIGINT NOT NULL,
    listing_id BIGINT NOT NULL,
    price_for_seller INTEGER NOT NULL,
    price_for_buyer INTEGER NOT NULL,
    asset_id BIGINT NOT NULL,
    link VARCHAR(512) NOT NULL,

    PRIMARY KEY (listing_id),

    CONSTRAINT fk_charm_sale_offers_charms_info
        FOREIGN KEY (class_id, instance_id)
        REFERENCES market.charms_info(class_id, instance_id)
);

-- Создание индексов
CREATE INDEX IF NOT EXISTS idx_charm_sale_offers_class_id_instance_id ON market.charm_sale_offers(class_id, instance_id);

-- Комментарии к таблице и колонкам
COMMENT ON TABLE market.charm_sale_offers IS 'Предложения о продаже предметов на маркете';
COMMENT ON COLUMN market.charm_sale_offers.class_id IS 'Уникальный идентификатор класса предмета';
COMMENT ON COLUMN market.charm_sale_offers.instance_id IS 'Уникальный идентификатор экземпляра предмета';
COMMENT ON COLUMN market.charm_sale_offers.listing_id IS 'Уникальный идентификатор листинга';
COMMENT ON COLUMN market.charm_sale_offers.price_for_seller IS 'Цена для продавца (в центах)';
COMMENT ON COLUMN market.charm_sale_offers.price_for_buyer IS 'Цена для покупателя (в центах)';
COMMENT ON COLUMN market.charm_sale_offers.asset_id IS 'Идентификатор ассета';
COMMENT ON COLUMN market.charm_sale_offers.link IS 'Ссылка на инспект предмета';