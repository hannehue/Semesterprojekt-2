CREATE TABLE credits (
	credit_id  SERIAL PRIMARY KEY,
	name VARCHAR(50) NOT NULL,
	date_added VARCHAR(50) NOT NULL,
	approved BOOLEAN,
	description VARCHAR(500) NOT NULL
);

CREATE TABLE persons (
	person_id SERIAL PRIMARY KEY,
	credit_id INT REFERENCES credits(credit_id) NOT NULL, 
	phone_number VARCHAR(50),
	email VARCHAR(50),
	personal_info VARCHAR(500)
);

CREATE TABLE categories (
	category_id SERIAL PRIMARY KEY,
	category VARCHAR(50) NOT NULL
);

CREATE TABLE productions (
	production_id SERIAL PRIMARY KEY,
	credit_id INT REFERENCES credits(credit_id) NOT NULL, 
	category_id INT REFERENCES categories(category_id) NOT NULL, 
	length_in_secs INT NOT NULL,
	release_date VARCHAR(50) NOT NULL
);

CREATE TABLE shows (
	show_id SERIAL PRIMARY KEY,
	credit_id INT REFERENCES credits(credit_id) NOT NULL, 
	all_seasons_approved BOOLEAN
);

CREATE TABLE seasons (
	season_id SERIAL PRIMARY KEY, 
	credit_id INT REFERENCES credits(credit_id) NOT NULL, 
	show_id INT REFERENCES shows(show_id) NOT NULL,
	all_episodes_approved BOOLEAN
);

CREATE TABLE groups (
	group_id SERIAL PRIMARY KEY,
	credit_id INT REFERENCES credits(credit_id) NOT NULL 
);

CREATE TABLE companies (
	company_id SERIAL PRIMARY KEY,
	credit_id INT REFERENCES credits(credit_id) NOT NULL 
);

CREATE TABLE movies (
	movie_id SERIAL PRIMARY KEY,
	production_id INT REFERENCES productions(production_id)
);

CREATE TABLE episodes (
	episode_id SERIAL PRIMARY KEY,
	production_id INT REFERENCES productions(production_id),
	season_id INT REFERENCES seasons(season_id)
);

CREATE TABLE job_roles (
	job_role_id SERIAL PRIMARY KEY,
	job_role VARCHAR(50) NOT NULL
);

CREATE TABLE jobs (
	job_id SERIAL PRIMARY KEY,
	person_id INT REFERENCES persons(person_id),
	job_role_id INT REFERENCES job_roles(job_role_id),
	production_id INT REFERENCES productions(production_id)
);

INSERT INTO job_roles(job_role)
VALUES	('Skuespiller'),
		('Billedkunstnere'),
		('Billed- og lydredigering'),
		('Casting'),
		('Colourgrading'),
		('Dirigenter'),
		('Drone'),
		('Dukkefører'),
		('Dukkeskaber'),
		('Fortæller'),
		('Fotografer'),
		('Forlæg'),
		('Grafiske designere'),
		('Indtalere'),
		('Kapelmester'),
		('Klipper'),
		('Koncept/programkoncept'),
		('Konsulent'),
		('Kor'),
		('Koreografi'),
		('Lyd eller tonemester'),
		('Lydredigering'),
		('Lys'),
		('Medvirkende'),
		('Musikere'),
		('Musikalsk arrangement'),
		('Orkester/band'),
		('Oversættere'),
		('Producent'),
		('Producer'),
		('Produktionskoordinator el produktionsleder'),
		('Programansvarlige'),
		('Redaktion/tilrettelæggelse'),
		('Redaktøren'),
		('Rekvisitør'),
		('Scenografer'),
		('Scripter/producerassistent'),
		('Special effects'),
		('Sponsorer'),
		('Tegnefilm eller animation'),
		('Tekstere'),
		('Tekst og musik'),
		('Uhonoreret og ekstraordinær indsats');

INSERT INTO categories (category)
VALUES ('Aktualitet'),
		('Dokumentar'),
		('Magasin'),
		('Kultur og Natur'),
		('Drama'),
		('TV-Serie'),
		('Underholdning'),
		('Musik'),
		('Børn'),
		('Regionalprogram'),
		('Sport'),
		('Nyheder'),
		('Film');
