DROP PROCEDURE  `AddMethodSet` ;

CREATE DEFINER =  `root`@`localhost` PROCEDURE  `AddMethodSet` ( IN  `p_stage` TINYINT UNSIGNED, IN  `p_lengthOfLead` SMALLINT UNSIGNED, IN  `p_numberOfHunts` TINYINT UNSIGNED, IN  `p_huntBellPath` VARCHAR( 100 ) , IN  `p_symmetry` VARCHAR( 50 ) ) DETERMINISTIC NO SQL SQL SECURITY DEFINER 

BEGIN 
	
	IF( NOT EXISTS (
		SELECT 1 
		FROM ccmethodset
		WHERE stage = p_stage
		AND lengthOfLead = p_lengthOfLead
		AND numberOfHunts = p_numberOfHunts
		AND huntBellPath = p_huntBellPath
		AND symmetry = p_symmetry) ) 
	THEN 
		INSERT ccmethodset VALUES (
			NULL , 
			p_stage, 
			p_lengthOfLead, 
			p_numberOfHunts, 
			p_huntBellPath, 
			p_symmetry
		);

	END IF ;

	SELECT id
	FROM ccmethodset
	WHERE stage = p_stage
	AND lengthOfLead = p_lengthOfLead
	AND numberOfHunts = p_numberOfHunts
	AND huntBellPath = p_huntBellPath
	AND symmetry = p_symmetry;

END
