-- phpMyAdmin SQL Dump
-- version 4.0.10.12
-- http://www.phpmyadmin.net
--
-- Host: 127.7.236.2:3306
-- Generation Time: Dec 14, 2016 at 08:20 AM
-- Server version: 5.5.52
-- PHP Version: 5.3.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `methodical`
--

DROP DATABASE IF EXISTS methodical;

CREATE DATABASE IF NOT EXISTS `methodical` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `methodical`;

DROP USER IF EXISTS 'mmuser'@'localhost';

-- CREATE USER 'mmuser'@'localhost' IDENTIFIED WITH mysql_native_password AS 'vtt6UYPH9ST8h9LW';
CREATE USER 'mmuser'@'localhost' IDENTIFIED BY 'vtt6UYPH9ST8h9LW';
GRANT SELECT, INSERT, UPDATE, DELETE, FILE, EXECUTE ON *.* TO 'mmuser'@'localhost' REQUIRE NONE WITH MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0;

DELIMITER ;


-- --------------------------------------------------------

--
-- Table structure for table `ccmethod`
--

CREATE TABLE IF NOT EXISTS `ccmethod` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ccid` varchar(15) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `classification` varchar(20) DEFAULT NULL,
  `plain` tinyint(1) NOT NULL,
  `little` tinyint(1) NOT NULL,
  `title` varchar(150) NOT NULL,
  `notation` varchar(100) NOT NULL,
  `leadHead` varchar(20) NOT NULL,
  `methodsetid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `title` (`title`),
  KEY `name` (`name`,`classification`,`title`,`methodsetid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=61261 ;

-- --------------------------------------------------------

--
-- Table structure for table `ccmethodset`
--

CREATE TABLE IF NOT EXISTS `ccmethodset` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `stage` tinyint(3) unsigned NOT NULL,
  `lengthOfLead` smallint(5) unsigned NOT NULL,
  `numberOfHunts` tinyint(3) unsigned NOT NULL,
  `huntBellPath` varchar(100) NOT NULL,
  `symmetry` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `stage` (`stage`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=633 ;

-- --------------------------------------------------------

--
-- Table structure for table `composition`
--

CREATE TABLE IF NOT EXISTS `composition` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL,
  `author` int(11) NOT NULL,
  `created` datetime NOT NULL,
  `composition` longtext NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=66 ;

-- --------------------------------------------------------

--
-- Table structure for table `event`
--

CREATE TABLE IF NOT EXISTS `event` (
  `event_id` int(11) NOT NULL AUTO_INCREMENT,
  `tower_id` int(11) NOT NULL,
  `date` date NOT NULL,
  `eventtype_id` int(11) NOT NULL,
  PRIMARY KEY (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `eventtype`
--

CREATE TABLE IF NOT EXISTS `eventtype` (
  `eventtype_id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(50) NOT NULL,
  `description` text NOT NULL,
  PRIMARY KEY (`eventtype_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `favouritemethods`
--

CREATE TABLE IF NOT EXISTS `favouritemethods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `methodid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `userid` (`userid`,`methodid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

-- --------------------------------------------------------

--
-- Table structure for table `method`
--

CREATE TABLE IF NOT EXISTS `method` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `number_of_bells` tinyint(3) unsigned NOT NULL,
  `place_notation` varchar(100) NOT NULL,
  `bob_place_notation` varchar(20) DEFAULT NULL,
  `single_place_notation` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `name` (`name`,`number_of_bells`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

-- --------------------------------------------------------

--
-- Table structure for table `performance`
--

CREATE TABLE IF NOT EXISTS `performance` (
  `performance_id` int(11) NOT NULL AUTO_INCREMENT,
  `event_id` int(11) NOT NULL,
  `length` int(11) NOT NULL,
  `notes` text NOT NULL,
  PRIMARY KEY (`performance_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `performancemethod`
--

CREATE TABLE IF NOT EXISTS `performancemethod` (
  `pm_id` int(11) NOT NULL AUTO_INCREMENT,
  `performance_id` int(11) NOT NULL,
  `method` varchar(50) NOT NULL,
  `changes` int(11) NOT NULL,
  PRIMARY KEY (`pm_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `performanceringer`
--

CREATE TABLE IF NOT EXISTS `performanceringer` (
  `ringer` varchar(50) NOT NULL,
  `position` int(11) NOT NULL,
  `pr_id` int(11) NOT NULL AUTO_INCREMENT,
  `perfor` int(11) NOT NULL,
  PRIMARY KEY (`pr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `tower`
--

CREATE TABLE IF NOT EXISTS `tower` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `city` varchar(30) NOT NULL,
  `county` varchar(30) NOT NULL,
  `country` varchar(30) NOT NULL,
  `designation` varchar(100) NOT NULL,
  `bells` int(11) NOT NULL,
  `notes` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6158 ;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(20) NOT NULL,
  `surname` varchar(20) NOT NULL,
  `email_address` varchar(60) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_address` (`email_address`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
-- --------------------------------------------------------

--
-- Table structure for table `ccmethod`
--

CREATE TABLE IF NOT EXISTS `ccmethod` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ccid` varchar(15) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `classification` varchar(20) DEFAULT NULL,
  `plain` tinyint(1) NOT NULL,
  `little` tinyint(1) NOT NULL,
  `title` varchar(150) NOT NULL,
  `notation` varchar(100) NOT NULL,
  `leadHead` varchar(20) NOT NULL,
  `methodsetid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `title` (`title`),
  KEY `name` (`name`,`classification`,`title`,`methodsetid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=61261 ;

-- --------------------------------------------------------

--
-- Table structure for table `ccmethodset`
--

CREATE TABLE IF NOT EXISTS `ccmethodset` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `stage` tinyint(3) unsigned NOT NULL,
  `lengthOfLead` smallint(5) unsigned NOT NULL,
  `numberOfHunts` tinyint(3) unsigned NOT NULL,
  `huntBellPath` varchar(100) NOT NULL,
  `symmetry` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `stage` (`stage`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=633 ;

-- --------------------------------------------------------

--
-- Table structure for table `composition`
--

CREATE TABLE IF NOT EXISTS `composition` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL,
  `author` int(11) NOT NULL,
  `created` datetime NOT NULL,
  `composition` longtext NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=66 ;

-- --------------------------------------------------------

--
-- Table structure for table `event`
--

CREATE TABLE IF NOT EXISTS `event` (
  `event_id` int(11) NOT NULL AUTO_INCREMENT,
  `tower_id` int(11) NOT NULL,
  `date` date NOT NULL,
  `eventtype_id` int(11) NOT NULL,
  PRIMARY KEY (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `eventtype`
--

CREATE TABLE IF NOT EXISTS `eventtype` (
  `eventtype_id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(50) NOT NULL,
  `description` text NOT NULL,
  PRIMARY KEY (`eventtype_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `favouritemethods`
--

CREATE TABLE IF NOT EXISTS `favouritemethods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `methodid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `userid` (`userid`,`methodid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

-- --------------------------------------------------------

--
-- Table structure for table `method`
--

CREATE TABLE IF NOT EXISTS `method` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `number_of_bells` tinyint(3) unsigned NOT NULL,
  `place_notation` varchar(100) NOT NULL,
  `bob_place_notation` varchar(20) DEFAULT NULL,
  `single_place_notation` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `name` (`name`,`number_of_bells`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

-- --------------------------------------------------------

--
-- Table structure for table `performance`
--

CREATE TABLE IF NOT EXISTS `performance` (
  `performance_id` int(11) NOT NULL AUTO_INCREMENT,
  `event_id` int(11) NOT NULL,
  `length` int(11) NOT NULL,
  `notes` text NOT NULL,
  PRIMARY KEY (`performance_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `performancemethod`
--

CREATE TABLE IF NOT EXISTS `performancemethod` (
  `pm_id` int(11) NOT NULL AUTO_INCREMENT,
  `performance_id` int(11) NOT NULL,
  `method` varchar(50) NOT NULL,
  `changes` int(11) NOT NULL,
  PRIMARY KEY (`pm_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `performanceringer`
--

CREATE TABLE IF NOT EXISTS `performanceringer` (
  `ringer` varchar(50) NOT NULL,
  `position` int(11) NOT NULL,
  `pr_id` int(11) NOT NULL AUTO_INCREMENT,
  `perfor` int(11) NOT NULL,
  PRIMARY KEY (`pr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `tower`
--

CREATE TABLE IF NOT EXISTS `tower` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `city` varchar(30) NOT NULL,
  `county` varchar(30) NOT NULL,
  `country` varchar(30) NOT NULL,
  `designation` varchar(100) NOT NULL,
  `bells` int(11) NOT NULL,
  `notes` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6158 ;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(20) NOT NULL,
  `surname` varchar(20) NOT NULL,
  `email_address` varchar(60) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_address` (`email_address`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;


DELIMITER $$
--
-- Procedures
--
CREATE PROCEDURE `AddComposition`(IN `p_title` VARCHAR(50), IN `p_author` VARCHAR(50), IN `p_composition` LONGTEXT)
    NO SQL
BEGIN

    DELETE FROM composition WHERE title = p_title;
	
	INSERT composition VALUES (
		NULL, 
		p_title, 
		1, 
		"23 Oct 2016", 
		p_composition
	);

	SELECT id
	FROM composition
	WHERE title = p_title;
  
END$$

CREATE PROCEDURE `AddMethod`(IN `p_ccId` VARCHAR(15), IN `p_name` VARCHAR(100), IN `p_classification` VARCHAR(20), IN `p_plain` BOOLEAN, IN `p_title` VARCHAR(150), IN `p_notation` VARCHAR(100), IN `p_leadHead` VARCHAR(20), IN `p_methodSetId` INT, IN `p_little` BOOLEAN)
    NO SQL
BEGIN 
	
	DELETE FROM ccmethod WHERE title = p_title;
	
	INSERT ccmethod VALUES (
		NULL, 
		p_ccid, 
		p_name, 
		p_classification, 
		p_plain, 
        p_little,
		p_title, 
		p_notation, 
		p_leadHead, 
		p_methodSetId
	);

	SELECT id
	FROM ccmethod
	WHERE title = p_title;

END$$

CREATE PROCEDURE `AddMethodSet`(IN `p_stage` TINYINT UNSIGNED, IN `p_lengthOfLead` SMALLINT UNSIGNED, IN `p_numberOfHunts` TINYINT UNSIGNED, IN `p_huntBellPath` VARCHAR(100), IN `p_symmetry` VARCHAR(50))
    NO SQL
    DETERMINISTIC
begin

if (not exists (select 1 from ccmethodset where 
stage=p_stage 
and lengthOfLead=p_lengthOfLead
and numberOfHunts=p_numberOfHunts
and huntBellPath=p_huntBellPath
and symmetry=p_symmetry)) then

	insert ccmethodset 
	values (null, p_stage, p_lengthOfLead, p_numberOfHunts, p_huntBellPath, p_symmetry);
end if;

select id 
from ccmethodset 
where stage=p_stage 
and lengthOfLead=p_lengthOfLead
and numberOfHunts=p_numberOfHunts
and huntBellPath=p_huntBellPath
and symmetry=p_symmetry;

end$$

CREATE PROCEDURE `AddTower`(IN `p_city` VARCHAR(30), IN `p_county` VARCHAR(30), IN `p_country` VARCHAR(30), IN `p_designation` VARCHAR(100), IN `p_bells` INT, IN `p_notes` VARCHAR(200))
    NO SQL
BEGIN 
	
	INSERT tower VALUES (
		NULL, 
		p_city, 
		p_county, 
		p_country, 
		p_designation, 
        p_bells,
		p_notes
	);


END$$

CREATE PROCEDURE `GetCompositionById`(IN `p_id` INT)
    NO SQL
BEGIN

	SELECT id, title, "Jon" as author, created, composition
    from composition
    where id = p_id;


END$$

CREATE PROCEDURE `GetCompositionByTitle`(IN `p_title` VARCHAR(50))
    NO SQL
SELECT id, title, "Jon" as author, created, null as composition
from composition
where title like p_title$$

CREATE PROCEDURE `GetFavouriteMethods`(IN `p_partialTitle` VARCHAR(50), IN `p_numberOfBells` INT, IN `p_userId` INT)
    NO SQL
BEGIN

	SELECT 
		ccmethod.id "id", 
		ccmethod.title "name", 
		ccmethodset.stage "number_of_bells", 
		ccmethod.notation "place_notation",
		ccmethodset.numberOfHunts,
		"14" as "bob_place_notation", 
		"1234" as "single_place_notation",
		case when userid is null then false else true end as "favourite"
	FROM ccmethod 
	INNER JOIN ccmethodset 
		ON ccmethodset.id = ccmethod.methodsetid
	INNER JOIN favouritemethods 
		ON methodid = ccmethod.id
		AND userid = p_userId
	WHERE ccmethod.title like p_partialTitle
		AND ccmethodset.stage = p_numberOfBells
		AND ccmethodset.id = ccmethod.methodsetid
	ORDER BY ccmethod.title;


END$$

CREATE PROCEDURE `GetMethod`(IN `p_id` INT UNSIGNED)
    NO SQL
begin

	SELECT 
		ccmethod.id "id", 
		ccmethod.title "name", 
		ccmethodset.stage "number_of_bells", 
		ccmethod.notation "place_notation",
		ccmethodset.numberOfHunts,
		"14" as "bob_place_notation", 
		"1234" as "single_place_notation",
		coalesce(favouritemethods.id, 0)>1 as "favourite"
	FROM  `ccmethod`
	inner join `ccmethodset` on ccmethodset.id = ccmethod.methodsetid
	left outer join `favouritemethods` on ccmethod.id = favouritemethods.methodid and userid = 1
	WHERE ccmethod.id = p_id;

	/* SELECT 
		ccmethod.id "id", 
		ccmethod.title "name", 
		ccmethodset.stage "number_of_bells", 
		ccmethod.notation "place_notation",
		ccmethodset.numberOfHunts,
		"14" as "bob_place_notation", 
		"1234" as "single_place_notation"
	FROM  `ccmethod`, `ccmethodset` 
	WHERE ccmethod.id = p_id
		AND ccmethodset.id = ccmethod.methodsetid; */


end$$

CREATE PROCEDURE `GetMethodByTitle`(IN `p_title` VARCHAR(50), IN `p_userId` INT)
    NO SQL
BEGIN

	SELECT 
		ccmethod.id "id", 
		ccmethod.title "name", 
		ccmethodset.stage "number_of_bells", 
		ccmethod.notation "place_notation",
		ccmethodset.numberOfHunts,
		"14" as "bob_place_notation", 
		"1234" as "single_place_notation",
		case when userid is null then false else true end as "favourite"
	FROM ccmethod 
	INNER JOIN ccmethodset 
		ON ccmethodset.id = ccmethod.methodsetid
	LEFT OUTER JOIN favouritemethods 
		ON methodid = ccmethod.id
		AND userid = p_userId
	WHERE ccmethod.title like p_title
		AND ccmethodset.id = ccmethod.methodsetid
	ORDER BY ccmethod.title;


END$$

CREATE PROCEDURE `GetMethods`(IN `p_partialTitle` VARCHAR(50), IN `p_numberOfBells` INT UNSIGNED, IN `p_userId` INT)
    NO SQL
    DETERMINISTIC
BEGIN

	SELECT 
		ccmethod.id "id", 
		ccmethod.title "name", 
		ccmethodset.stage "number_of_bells", 
		ccmethod.notation "place_notation",
		ccmethodset.numberOfHunts,
		"14" as "bob_place_notation", 
		"1234" as "single_place_notation",
		case when userid is null then false else true end as "favourite"
	FROM ccmethod 
	INNER JOIN ccmethodset 
		ON ccmethodset.id = ccmethod.methodsetid
	LEFT OUTER JOIN favouritemethods 
		ON methodid = ccmethod.id
		AND userid = p_userId
	WHERE ccmethod.title like p_partialTitle
		AND ccmethodset.stage = p_numberOfBells
		AND ccmethodset.id = ccmethod.methodsetid
	ORDER BY ccmethod.title;


END$$

CREATE PROCEDURE `SetFavouriteMethod`(IN `p_userid` INT UNSIGNED, IN `p_methodid` INT UNSIGNED, IN `p_favourite` BOOLEAN)
    NO SQL
begin

	if (p_favourite) then
		if (not exists (select 1 from favouritemethods where userid = p_userid and methodid = p_methodid)) then
			insert into favouritemethods (userid, methodid) values (p_userid, p_methodid);
        end if;
	end if;
            
	if (!p_favourite) then
            delete from favouritemethods where userid = p_userid and methodid = p_methodid;
	end if;
            
end$$

