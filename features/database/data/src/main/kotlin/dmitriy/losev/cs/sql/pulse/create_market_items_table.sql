-- Создание таблицы market_items
CREATE TABLE pulse.market_items (

    id SERIAL PRIMARY KEY,

    market_hash_name VARCHAR(255) NOT NULL,

    market VARCHAR(50) NOT NULL,

    min_price INT DEFAULT NULL,
    min_price_updated_at TIMESTAMP DEFAULT NULL,

    buy_order_price INT DEFAULT NULL,
    buy_order_updated_at TIMESTAMP DEFAULT NULL,

    trade_on_price INT DEFAULT NULL,
    trade_on_price_updated_at TIMESTAMP DEFAULT NULL,

    recommended_sell_price INT DEFAULT NULL,
    recommended_sell_price_updated_at TIMESTAMP DEFAULT NULL,

    recommended_buy_price INT DEFAULT NULL,
    recommended_buy_price_updated_at TIMESTAMP DEFAULT NULL,
    recommended_buy_orders_count INT DEFAULT NULL,

    weekly_sales_count INT NOT NULL,

    sales_updated_at TIMESTAMP NOT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT NOW(),

    UNIQUE(market_hash_name, market)
);

-- Создание индексов
CREATE INDEX idx_market_items_name ON pulse.market_items(market_hash_name);
CREATE INDEX idx_market_items_market ON pulse.market_items(market);
CREATE INDEX idx_market_items_liquidity ON pulse.market_items(weekly_sales_count DESC NULLS LAST);

-- Комментарий к таблице
COMMENT ON TABLE pulse.market_items IS 'Данные о предметах CS2 на различных маркетплейсах для арбитражной торговли';

-- Комментарии к столбцам
COMMENT ON COLUMN pulse.market_items.id IS 'Уникальный идентификатор записи';
COMMENT ON COLUMN pulse.market_items.market_hash_name IS 'Каноническое название предмета из Steam';
COMMENT ON COLUMN pulse.market_items.market IS 'Код маркетплейса (steam, buff, dmarket, lisskins, csfloat)';

COMMENT ON COLUMN pulse.market_items.min_price IS 'Минимальная цена продажи в центах USD';
COMMENT ON COLUMN pulse.market_items.min_price_updated_at IS 'Время последнего обновления минимальной цены';

COMMENT ON COLUMN pulse.market_items.buy_order_price IS 'Максимальная цена ордера на покупку в центах USD';
COMMENT ON COLUMN pulse.market_items.buy_order_updated_at IS 'Время последнего обновления цены ордера';

COMMENT ON COLUMN pulse.market_items.trade_on_price IS 'Рекомендованная цена от TradeOn в центах USD';
COMMENT ON COLUMN pulse.market_items.trade_on_price_updated_at IS 'Время последнего обновления цены TradeOn';

COMMENT ON COLUMN pulse.market_items.recommended_sell_price IS 'Моя рекомендованная цена продажи в центах USD';
COMMENT ON COLUMN pulse.market_items.recommended_sell_price_updated_at IS 'Время последнего обновления рекомендованной цены продажи';

COMMENT ON COLUMN pulse.market_items.recommended_buy_price IS 'Моя рекомендованная цена покупки в центах USD';
COMMENT ON COLUMN pulse.market_items.recommended_buy_price_updated_at IS 'Время последнего обновления рекомендованной цены покупки';
COMMENT ON COLUMN pulse.market_items.recommended_buy_orders_count IS 'Рекомендуемое количество ордеров на покупку';

COMMENT ON COLUMN pulse.market_items.weekly_sales_count IS 'Количество продаж за последнюю неделю (ликвидность)';
COMMENT ON COLUMN pulse.market_items.sales_updated_at IS 'Время последнего обновления данных о продажах';

COMMENT ON COLUMN pulse.market_items.created_at IS 'Дата первого добавления записи';