-- Создание таблицы market_items
CREATE TABLE IF NOT EXISTS pulse.market_items (
    item_name VARCHAR(255) NOT NULL,
    min_buy_price DOUBLE PRECISION NOT NULL,
    max_sell_price DOUBLE PRECISION NOT NULL,
    buy_market VARCHAR(32) NOT NULL,
    sell_market VARCHAR(32) NOT NULL,
    offers_count INTEGER NOT NULL,
    profit DOUBLE PRECISION NOT NULL,
    last_updated_in_buy_market TIMESTAMP NOT NULL,
    last_updated_in_sell_market TIMESTAMP NOT NULL,
    first_addition TIMESTAMP NOT NULL DEFAULT NOW(),

    PRIMARY KEY (item_name)
);

-- Создание индексов
CREATE INDEX IF NOT EXISTS idx_market_items_min_buy_price ON pulse.market_items(min_buy_price);
CREATE INDEX IF NOT EXISTS idx_market_items_offers_count ON pulse.market_items(offers_count);
CREATE INDEX IF NOT EXISTS idx_market_items_buy_market ON pulse.market_items(buy_market);
CREATE INDEX IF NOT EXISTS idx_market_items_sell_market ON pulse.market_items(sell_market);
CREATE INDEX IF NOT EXISTS idx_market_items_profit ON pulse.market_items(profit);
CREATE INDEX IF NOT EXISTS idx_market_items_last_updated_in_buy_market ON pulse.market_items(last_updated_in_buy_market);
CREATE INDEX IF NOT EXISTS idx_market_items_last_updated_in_sell_market ON pulse.market_items(last_updated_in_sell_market);
CREATE INDEX IF NOT EXISTS idx_market_items_first_addition ON pulse.market_items(first_addition);

-- Комментарии к таблице и колонкам
COMMENT ON TABLE pulse.market_items IS 'Торговые предложения для перепродажи';
COMMENT ON COLUMN pulse.market_items.item_name IS 'Название предмета';
COMMENT ON COLUMN pulse.market_items.min_buy_price IS 'Минимальная цена покупки в магазине покупки';
COMMENT ON COLUMN pulse.market_items.max_sell_price IS 'Максимальная цена продажи на в магазине продажи';
COMMENT ON COLUMN pulse.market_items.offers_count IS 'Количество предложений';
COMMENT ON COLUMN pulse.market_items.profit IS 'Профит от перепродажи';
COMMENT ON COLUMN pulse.market_items.last_updated_in_buy_market IS 'Время последнего обновления цены в магазине покупки';
COMMENT ON COLUMN pulse.market_items.last_updated_in_sell_market IS 'Время последнего обновления цены в магазине продажи';
COMMENT ON COLUMN pulse.market_items.first_addition IS 'Время первого добавления предмета в таблицу';