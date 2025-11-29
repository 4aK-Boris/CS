SELECT
    fl.pattern,
    offer.price_for_buyer
FROM market.charms_float fl
INNER JOIN market.charm_sale_offers offer ON offer.listing_id = fl.listing_id
WHERE (fl.pattern < 5000 or (fl.pattern > 20000 and fl.pattern < 25000) or fl.pattern > 95000) and fl.class_id = -9223372030638221808;

SELECT
    fl.pattern,
    offer.price_for_buyer,
    offer.price_for_seller
FROM market.charms_float fl
INNER JOIN market.charm_sale_offers offer ON offer.listing_id = fl.listing_id;

SELECT
    *
FROM market.charm_sale_offers;

TRUNCATE TABLE market.charms_float;
TRUNCATE TABLE market.charm_sale_offers CASCADE;

DROP TABLE market.charms_float;
DROP TABLE market.charm_sale_offers;

SELECT * FROM market.charms_info

