-- Вставка данных в таблицу items_info
-- Использует ON CONFLICT для обновления существующих записей

INSERT INTO market.charms_info (class_id, instance_id, name, last_check_time)
VALUES
    (-9223372030638221808, -9223372036854775808, 'Charm | Semi-Precious', NOW() - INTERVAL '5 minutes')
ON CONFLICT (class_id, instance_id)
DO UPDATE SET
    name = EXCLUDED.name,
    last_check_time = EXCLUDED.last_check_time;

-- Примеры вставки одной записи
-- INSERT INTO market.items_info (class_id, instance_id, name, last_check_time)
-- VALUES (11, 0, 'AWP | Asiimov (Field-Tested)', NOW())
-- ON CONFLICT (class_id, instance_id)
-- DO UPDATE SET
--     name = EXCLUDED.name,
--     last_check_time = EXCLUDED.last_check_time;

-- Вставка с текущим временем
-- INSERT INTO market.items_info (class_id, instance_id, name, last_check_time)
-- VALUES (12, 0, 'AK-47 | Vulcan (Factory New)', NOW());

-- Вставка с кастомным временем
-- INSERT INTO market.items_info (class_id, instance_id, name, last_check_time)
-- VALUES (13, 0, 'M4A4 | Asiimov (Field-Tested)', '2025-01-01 12:00:00');