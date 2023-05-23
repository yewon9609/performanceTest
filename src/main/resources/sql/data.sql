

-- DELIMITER $$
-- -- DROP PROCEDURE IF EXISTS insertDummyData$$
--
-- CREATE PROCEDURE insertDummyData()
-- BEGIN
--     DECLARE i INT DEFAULT 1;
--     WHILE i <= 500 DO
--         insert into travelogue (create_date, etc, lodge, total, transportation, country_name, end_date, start_date, thumbnail, title)
--         values (, 0, 0, 0, 500000, "일본", now(), now(), "ㅇㅇㅇ", concat('짱구보고옴',i));
--         SET i = i + 1;
--   END WHILE;
-- END$$
-- DELIMITER;
--
-- CALL insertDummyData;
