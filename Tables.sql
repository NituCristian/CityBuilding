
CREATE TABLE `City` (
	`Id` INT NOT NULL AUTO_INCREMENT,
	`Name` varchar(255),
	`Surface` NUMERIC NOT NULL,
	`Population` INT NOT NULL,
	`Altitude` INT NOT NULL,
	`YearFounded` INT NOT NULL,
    `Lat` NUMERIC( 10, 6 ),
	`Long` NUMERIC( 10, 6 ),
    `TopLeftX` INT NOT NULL,
	`TopLeftY` INT NOT NULL,
	`CenterX` INT NOT NULL,
	`CenterY` INT NOT NULL,
	PRIMARY KEY (`Id`)
);

CREATE TABLE `CityLayers` (
	`LayerId` INT NOT NULL AUTO_INCREMENT,
	`Type` varchar(255) NOT NULL,
	`ImagePath` varchar(255) NOT NULL,
	`CityId` INT NOT NULL,
	PRIMARY KEY (`LayerId`),
    FOREIGN KEY (`CityId`) REFERENCES `City`(`Id`)
);


CREATE TABLE `Neighbourhood` (
	`Id` INT NOT NULL AUTO_INCREMENT,
	`Name` varchar(255) NOT NULL,
	`Rating` NUMERIC(4, 2) NOT NULL,
	`CityId` INT NOT NULL,
	PRIMARY KEY (`Id`),
    FOREIGN KEY (`CityId`) REFERENCES `City`(`Id`)
);

CREATE TABLE `Type` (
	`Id` INT NOT NULL AUTO_INCREMENT,
	`Name` varchar(255) NOT NULL,
	`Description` varchar(255),
	`Icon` varchar(255),
	PRIMARY KEY (`Id`)
);

CREATE TABLE `Amenity` (
	`Id` INT NOT NULL AUTO_INCREMENT,
	`NeighbourhoodId` INT NOT NULL,
    `TypeId` INT NOT NULL,
	`TopLeftX` INT NOT NULL,
	`TopLeftY` INT NOT NULL,
	`CenterX` INT NOT NULL,
	`CenterY` INT NOT NULL,
	`Prestige` NUMERIC(4, 2) NOT NULL,
	`Address` varchar(255) NOT NULL,
	`IsAvailable` BOOL NOT NULL,
	`PermanentlyClosed` BOOL NOT NULL,
	`Name` varchar(255) NOT NULL,
    `Description` varchar(255) NOT NULL,
	PRIMARY KEY (`Id`),
	FOREIGN KEY (`NeighbourhoodId`) REFERENCES `Neighbourhood`(`Id`),
	FOREIGN KEY (`TypeId`) REFERENCES `Type`(`Id`)
);

CREATE TABLE `Photo` (
	`Id` INT NOT NULL AUTO_INCREMENT,
	`AmenityId` INT NOT NULL,
	`Width` INT NOT NULL,
	`Height` INT NOT NULL,
	`Path` varchar(255),
	PRIMARY KEY (`Id`),
    FOREIGN KEY (`AmenityId`) REFERENCES `Amenity`(`Id`)
);


CREATE TABLE `Contact` (
	`Id` INT NOT NULL AUTO_INCREMENT,
	`AmenityId` INT NOT NULL,
	`Phone` varchar(255) NOT NULL,
	`Mail` varchar(255) NOT NULL,
	PRIMARY KEY (`Id`),
    FOREIGN KEY (`AmenityId`) REFERENCES `Amenity`(`Id`)
);


