-- ------------------------------------------------------------------------------
-- - Reconstruction de la base de données                                     ---
-- ------------------------------------------------------------------------------
DROP DATABASE IF EXISTS Shop;
CREATE DATABASE Shop;
USE Shop;

-- -----------------------------------------------------------------------------
-- - Construction de la table des utilisateurs                               ---
-- -----------------------------------------------------------------------------
CREATE TABLE T_Users (
	IdUser				int(4)		PRIMARY KEY AUTO_INCREMENT,
	Login				varchar(20)	NOT NULL UNIQUE,
	Password			varchar(20)	NOT NULL,
	Role					varchar(20) NOT NULL
) ENGINE = InnoDB;

INSERT INTO T_Users (IdUser, Login, Password, Role) VALUES ( 1, 'Nathan' ,	'Drake','User' );
INSERT INTO T_Users (IdUser, Login, Password, Role) VALUES ( 2, 'Lara',	'Croft','Admin'  );
INSERT INTO T_Users (IdUser, Login, Password, Role) VALUES ( 3, 'Geralt' ,	'Deriv', 'User' );
INSERT INTO T_Users (IdUser, Login, Password, Role) VALUES ( 4, 'Solid'   ,	'Snake' ,'User' );
INSERT INTO T_Users (IdUser, Login, Password, Role) VALUES ( 5, 'Samus'     ,	'Aran' ,'User' );

SELECT * FROM T_Users;

-- -----------------------------------------------------------------------------
-- - Construction de la table des clients	                                 ---
-- -----------------------------------------------------------------------------

CREATE TABLE T_Customers (
	IdCustomer				int(4)		PRIMARY KEY AUTO_INCREMENT,
	name					varchar(30)	NOT NULL,
	firstName				varchar(30)	NOT NULL,
	email 					varchar(30)	NOT NULL unique,
	phone 					varchar(20)	,
	address					varchar(50)	,
	idUser					int(4)		NOT NULL,
	FOREIGN KEY (idUser)	REFERENCES T_Users(idUser)
) ENGINE = InnoDB;

-- -----------------------------------------------------------------------------
-- - Construction de la table des catégories d'articles
-- -----------------------------------------------------------------------------

CREATE TABLE T_Categories (
	IdCategory 				INT(4) 		 PRIMARY KEY AUTO_INCREMENT,
	CatName 				VARCHAR(30)  NOT NULL,
	Description 			VARCHAR(100) NOT NULL
) ENGINE = InnoDB;

insert into T_Categories (IdCategory, CatName, Description) values (1 , 'Dev' , 'Développement informatique');
insert into T_Categories (IdCategory, CatName, Description) values (2 , 'Science' , 'Manipulation de données');
insert into T_Categories (IdCategory, CatName, Description) values (3 , 'Sécurité' , 'Sécurité informatique');
insert into T_Categories (IdCategory, CatName, Description) values (4 , 'IA' , 'Intelligence artificielle');

select * from t_categories;

-- -----------------------------------------------------------------------------
-- - Construction de la table des articles en vente                         ---
-- -----------------------------------------------------------------------------
CREATE TABLE T_Articles (
	IdArticle				int(4)		PRIMARY KEY AUTO_INCREMENT,
	Name				varchar(100)	NOT NULL,
	Description					varchar(200)	NOT NULL,
	Duration					int(4)	NOT NULL DEFAULT 0,
	Format					varchar(30) NOT NULL,
	Price			float(8)	NOT NULL DEFAULT 0,
	IdCategory				int(4),
	FOREIGN KEY (IdCategory)	REFERENCES T_Categories(IdCategory)
) ENGINE = InnoDB;

INSERT INTO T_Articles ( Name, Description, Duration ,Format, Price, IdCategory ) VALUES ( 'Développeur Web Full Stack'     ,	'Développeur web complet.', 90, 'Présentielle'		, 3000,1 );
INSERT INTO T_Articles ( Name, Description, Duration ,Format, Price, IdCategory ) VALUES ( 'Data Science'     ,	'Analyser et interpréter des données', 60, 'Distancielle'		, 2500,2 );
INSERT INTO T_Articles ( Name, Description, Duration ,Format, Price, IdCategory ) VALUES ( 'Cybersécurité'     ,	'Protéger les systèmes.', 45, 'Présentielle'		, 2000,4 );
INSERT INTO T_Articles ( Name, Description, Duration ,Format, Price, IdCategory ) VALUES ( 'Administration Système Linux'     ,	'Administration système sous Linux.', 30, 'Distancielle'		, 1500,1 );
INSERT INTO T_Articles ( Name, Description, Duration ,Format, Price, IdCategory ) VALUES ( 'Développement Mobile'     ,	'Créer des applications mobile', 60, 'Distancielle'		, 2800,2 );
INSERT INTO T_Articles ( Name, Description, Duration ,Format, Price, IdCategory ) VALUES ( 'Cloud Computing'     ,	'Déployer dans le cloud', 45, 'Présentielle'		, 1800,2 );
INSERT INTO T_Articles ( Name, Description, Duration ,Format, Price, IdCategory ) VALUES ( 'Python pour les débutants'     ,	'Python pour les débutants', 30, 'Présentielle'		, 1200,1 );
INSERT INTO T_Articles ( Name, Description, Duration ,Format, Price, IdCategory ) VALUES ( 'Big Data et Hadoop'     ,	'Apprenez à utiliser Hadoop', 60, 'Présentielle'		, 2500,4 );
INSERT INTO T_Articles ( Name, Description, Duration ,Format, Price, IdCategory ) VALUES ( 'Blockchain et Cryptomonnaies'     ,	'La technologie blockchain', 40, 'Distancielle'		, 2000,2 );
INSERT INTO T_Articles ( Name, Description, Duration ,Format, Price, IdCategory ) VALUES ( 'DevOps'     ,	'Apprenez les pratiques DevOps', 45, 'Présentielle'		, 3500,2 );



SELECT * FROM T_Articles;

-- ALTER TABLE t_articles ADD COLUMN IdCategory INT(4);
-- ALTER TABLE T_Articles ADD FOREIGN KEY(IdCategory) REFERENCES T_Categories(IdCategory);

-- select IdArticle,T_Articles.Description,Brand,UnitaryPrice,T_Articles.IdCategory,CatName,T_Categories.Description 
-- from t_articles inner join t_categories where t_articles.IdCategory = t_categories.IdCategory and IdArticle=1;

-- SELECT IdArticle,t_articles.Description,brand,UnitaryPrice,CatName FROM t_articles 
-- INNER JOIN t_categories WHERE t_articles.IdCategory=t_categories.IdCategory AND IdArticle>10 ORDER BY UnitaryPrice;

CREATE TABLE T_Orders (
	IdOrder				int(4)		PRIMARY KEY AUTO_INCREMENT,
	Amount				float(4)	NOT NULL DEFAULT 0,
	DateOrder 			DATE		NOT NULL DEFAULT NOW(),
	IdCustomer          INT(4)   	NOT NULL,
	FOREIGN KEY(IdCustomer) REFERENCES T_Customers(IdCustomer)
) ENGINE = InnoDB;



CREATE TABLE T_Order_Items (
	IdOrderItem			int(4)	PRIMARY KEY AUTO_INCREMENT,
	
	IdArticle           INT(4)   NOT NULL,
	FOREIGN KEY(IdArticle) REFERENCES T_Articles(IdArticle),
	
	Quantity			FLOAT(4) NOT NULL DEFAULT 1,
	Price		FLOAT(4)	NOT NULL DEFAULT 0,
	
	IdOrder             INT(4)   NOT NULL,
	FOREIGN KEY(IdOrder) REFERENCES T_Orders(IdOrder)
) ENGINE = InnoDB;