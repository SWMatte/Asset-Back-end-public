CREATE TABLE IF NOT EXISTS `user` (
  `id_user` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) UNIQUE NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` ENUM('USER','ADMIN') NOT NULL DEFAULT 'ADMIN',
  `delete_flag` BOOLEAN DEFAULT FALSE,
  PRIMARY KEY (`id_user`)
);

CREATE TABLE IF NOT EXISTS `company` (
`id_company` int NOT NULL AUTO_INCREMENT,
`company_name` varchar(255) NOT NULL,
PRIMARY KEY (`id_company`)
);

CREATE TABLE IF NOT EXISTS `employee` (
	`id_employee` int NOT NULL AUTO_INCREMENT,
    `first_name` varchar(255) NOT NULL,
    `last_name` varchar(255) NOT NULL,
    `cf` varchar(16) NOT NULL,
    `address` varchar(255),
    `birth_date` DATE,
    `birth_place` varchar(255),
    `registration_date` DATE NOT NULL,
    `employee_role` ENUM('AMMINISTRAZIONE','COMMERCIALE','DIPENDENTE','ACADEMY','P_IVA','EX_DIPENDENTE') NOT NULL,
    `personal_phone_number` varchar(255),
    `company_phone_number` varchar(255),
    `personal_email` varchar(255),
    `company_email` varchar(255),
    `company` int NOT NULL,
    `created_by` int NOT NULL,
	PRIMARY KEY (`id_employee`),
	FOREIGN KEY(`company`) REFERENCES company (`id_company`),
    FOREIGN KEY(`created_by`) REFERENCES user (`id_user`)
);

CREATE TABLE IF NOT EXISTS `asset` (
	`id_asset` int NOT NULL AUTO_INCREMENT,
    `type` ENUM('PC','TELEFONO') NOT NULL,
    `brand` varchar(255) NOT NULL,
    `serial_number` varchar(255) NOT NULL,
    `purchase_date` DATE,
    `creation_date` DATE NOT NULL,
    `additional_software` varchar(255),
    `additional_hardware` varchar(255),
    `hw_features` varchar(255),
    `has_antivirus` BOOLEAN DEFAULT FALSE,
    `antivirus_expiration_date` DATE,
    `sim_number` varchar(255),
    `processor` varchar(255),
    `ram` varchar(255) ,
    `storage` varchar(255),
    `os` varchar(255),
    `note` varchar(255),
    `company` int NOT NULL,
    `created_by` int NOT NULL,
	PRIMARY KEY (`id_asset`),
	FOREIGN KEY(`company`) REFERENCES company (`id_company`),
    FOREIGN KEY(`created_by`) REFERENCES user (`id_user`)
);

CREATE TABLE IF NOT EXISTS `history`(
`id_history` int NOT NULL AUTO_INCREMENT,
`employee` int,
`asset` int NOT NULL,
`asset_status` ENUM('DA_CONFIGURARE','DA_ASSEGNARE','ASSEGNATO','RICONSEGNATO','DA_FORMATTARE','ROTTO','MULETTO') NOT NULL DEFAULT 'DA_CONFIGURARE',
`assignment_date` DATE,
`effective_assignment_date` DATE,
`signed_document` VARCHAR(255),
`uploaded_signed_document` BOOLEAN DEFAULT FALSE,
`created_by` int NOT NULL,
PRIMARY KEY (`id_history`),
FOREIGN KEY(`employee`) REFERENCES employee (`id_employee`),
FOREIGN KEY(`asset`) REFERENCES asset (`id_asset`),
FOREIGN KEY(`created_by`) REFERENCES user (`id_user`)
);

INSERT INTO company (company_name)
VALUES ('VLC2');

INSERT INTO company (company_name)
VALUES ('BLACKSHEEP');

INSERT INTO user (email,password)
VALUES ('supportoA@vlc2.com', '$2a$10$50xgyNJ/WLiRHnRxQk173.DS7yvmQx6GAhYr4heimAXLdyPlTaJfq');

INSERT INTO user (email,password)
VALUES ('supportoM@vlc2.com', '$2a$10$mOtj2/qfG0aXWn68bbj4q.hlSeqfVjy4HlTCAR5W5Ss2pOM.eob9K');

INSERT INTO user (email,password)
VALUES ('amministrazione@vlc2.com', '$2a$10$KDCqxaygJdRoIBcUlek.peQKYlF/X8IoHgl4GP7Q51VibOtqsrEc2');