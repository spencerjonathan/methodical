USE `methodical`;

ALTER TABLE `composition` ADD `length` INT NULL DEFAULT NULL AFTER `author`;
ALTER TABLE `composition` ADD `isTrue` BOOLEAN NOT NULL DEFAULT TRUE AFTER `created`;
DROP PROCEDURE `AddComposition`; 

DELIMITER $$

CREATE PROCEDURE `AddComposition`(IN `p_title` VARCHAR(50), IN `p_authorid` INT(50) UNSIGNED, IN `p_created` DATETIME, IN `p_length` INT UNSIGNED, IN `p_true` BOOLEAN, IN `p_composition` LONGTEXT) NO SQL
BEGIN

    DELETE FROM composition WHERE title = p_title and author = p_authorid;
	
	INSERT composition VALUES (
		NULL, 
		p_title, 
		p_authorid, 
		p_length,
		p_created,
		p_true,
		p_composition
	);

	SELECT id
	FROM composition
	WHERE title = p_title and author = p_authorid;
  
END$$

DROP PROCEDURE `GetCompositionById`;
CREATE PROCEDURE `GetCompositionById`(IN `p_id` INT) NO SQL 
BEGIN

	SELECT id, title, author, length, created, isTrue, composition
    from composition
    where id = p_id;


END$$

DROP PROCEDURE `GetCompositionByAttributes`; 
CREATE PROCEDURE `GetCompositionByAttributes`(IN `p_title` VARCHAR(150), IN `p_author` VARCHAR(40), IN `p_length_min` INT(0), IN `p_length_max` INT, IN `p_created_min` DATE, IN `p_created_max` DATE) NO SQL
begin	
	SELECT id, title, author, created, length, isTrue, 20 as music, null as composition from composition 
	where title like p_title 
	and "Jon" like p_author 
	and (p_length_min is null or length >= p_length_min)
	and (p_length_max is null or length <= p_length_max)
	and (p_created_min is null or created >= p_created_min)
	and (p_created_max is null or created <= p_created_max);
end$$

update composition set created = '2017-01-12'$$


