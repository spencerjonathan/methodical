CREATE PROCEDURE  `AddMethod` ( IN  `p_ccId` VARCHAR( 15 ) , IN  `p_name` VARCHAR( 50 ) , IN `p_classification` VARCHAR( 20 ) , IN  `p_plain` BOOLEAN, IN  `p_title` VARCHAR( 100 ) , IN `p_notation` VARCHAR( 100 ) , IN  `p_leadHead` VARCHAR( 20 ) , IN  `p_methodSetId` INT ) NOT DETERMINISTIC NO SQL SQL SECURITY DEFINER 
BEGIN 
	
	DELETE FROM ccmethod WHERE title = p_title;
	
	INSERT ccmethod VALUES (
		NULL, 
		p_ccid, 
		p_name, 
		p_classification, 
		p_plain, 
		p_title, 
		p_notation, 
		p_leadHead, 
		p_methodSetId
	);

	SELECT id
	FROM ccmethod
	WHERE title = p_title;

END