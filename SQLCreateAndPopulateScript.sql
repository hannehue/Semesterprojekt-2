CREATE TABLE public.credits
(
    credit_id SERIAL NOT NULL,
    name character varying(50) NOT NULL,
    date_added character varying(50) NOT NULL,
    approved boolean,
    description character varying(500) NOT NULL,
    CONSTRAINT credits_pkey PRIMARY KEY (credit_id)
);

CREATE TABLE public.persons
(
    person_id SERIAL NOT NULL,
    credit_id INT NOT NULL,
    phone_number character varying(50),
    email character varying(50),
    personal_info character varying(500),
    CONSTRAINT persons_pkey PRIMARY KEY (person_id),
    CONSTRAINT persons_credit_id_fkey FOREIGN KEY (credit_id)
        REFERENCES public.credits (credit_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);

CREATE TABLE public.categories
(
    category_id SERIAL NOT NULL,
    category character varying(50) NOT NULL,
    CONSTRAINT categories_pkey PRIMARY KEY (category_id)
);

CREATE TABLE public.productions
(
    production_id SERIAL NOT NULL,
    credit_id integer NOT NULL,
    category_id integer NOT NULL,
    length_in_secs integer NOT NULL,
    release_date character varying(50) NOT NULL,
    CONSTRAINT productions_pkey PRIMARY KEY (production_id),
    CONSTRAINT productions_category_id_fkey FOREIGN KEY (category_id)
        REFERENCES public.categories (category_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT productions_credit_id_fkey FOREIGN KEY (credit_id)
        REFERENCES public.credits (credit_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);

CREATE TABLE public.shows
(
    show_id SERIAL NOT NULL,
    credit_id integer NOT NULL,
    all_seasons_approved boolean,
    CONSTRAINT shows_pkey PRIMARY KEY (show_id),
    CONSTRAINT shows_credit_id_fkey FOREIGN KEY (credit_id)
        REFERENCES public.credits (credit_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);

CREATE TABLE public.seasons
(
    season_id SERIAL NOT NULL,
    credit_id integer NOT NULL,
    show_id integer NOT NULL,
    all_episodes_approved boolean,
    CONSTRAINT seasons_pkey PRIMARY KEY (season_id),
    CONSTRAINT seasons_credit_id_fkey FOREIGN KEY (credit_id)
        REFERENCES public.credits (credit_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE,
    CONSTRAINT seasons_show_id_fkey FOREIGN KEY (show_id)
        REFERENCES public.shows (show_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);

CREATE TABLE public.groups
(
    group_id SERIAL NOT NULL,
    credit_id integer NOT NULL,
    CONSTRAINT groups_pkey PRIMARY KEY (group_id),
    CONSTRAINT groups_credit_id_fkey FOREIGN KEY (credit_id)
        REFERENCES public.credits (credit_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);

CREATE TABLE public.companies
(
    company_id SERIAL NOT NULL,
    credit_id integer NOT NULL,
    CONSTRAINT companies_pkey PRIMARY KEY (company_id),
    CONSTRAINT companies_credit_id_fkey FOREIGN KEY (credit_id)
        REFERENCES public.credits (credit_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE public.movies
(
    movie_id SERIAL NOT NULL,
    production_id integer,
    CONSTRAINT movies_pkey PRIMARY KEY (movie_id),
    CONSTRAINT movies_production_id_fkey FOREIGN KEY (production_id)
        REFERENCES public.productions (production_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);

CREATE TABLE public.episodes
(
    episode_id SERIAL NOT NULL,
    production_id integer,
    season_id integer,
    CONSTRAINT episodes_pkey PRIMARY KEY (episode_id),
    CONSTRAINT episodes_production_id_fkey FOREIGN KEY (production_id)
        REFERENCES public.productions (production_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE,
    CONSTRAINT episodes_season_id_fkey FOREIGN KEY (season_id)
        REFERENCES public.seasons (season_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);

CREATE TABLE public.job_roles
(
    job_role_id SERIAL NOT NULL,
    job_role character varying(50) NOT NULL,
    CONSTRAINT job_roles_pkey PRIMARY KEY (job_role_id)
);

CREATE TABLE public.jobs
(
    job_id SERIAL NOT NULL,
    person_id integer,
    job_role_id integer,
    production_id integer,
    CONSTRAINT jobs_pkey PRIMARY KEY (job_id),
    CONSTRAINT jobs_job_role_id_fkey FOREIGN KEY (job_role_id)
        REFERENCES public.job_roles (job_role_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT jobs_person_id_fkey FOREIGN KEY (person_id)
        REFERENCES public.persons (person_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE,
    CONSTRAINT jobs_production_id_fkey FOREIGN KEY (production_id)
        REFERENCES public.productions (production_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);


CREATE TABLE public.character_names
(
    character_name_id SERIAL NOT NULL,
    job_id integer NOT NULL,
    character_name character varying(50) NOT NULL,
    CONSTRAINT character_names_pkey PRIMARY KEY (character_name_id),
    CONSTRAINT character_names_job_id_fkey FOREIGN KEY (job_id)
        REFERENCES public.jobs (job_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
)

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


INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (79, 'Season1E1 - Episode title1', 'Sun May 23 21:51:09 CEST 2021', true, 'description');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (73, 'Season1E3 - The 100 Season 1 episode 3', 'Sun May 23 03:34:01 CEST 2021', true, 'description');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (78, 'Season1', 'Sun May 23 21:50:40 CEST 2021', true, 'sesh');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (63, 'Test Show', 'Fri May 21 17:51:46 CEST 2021', true, 'Test show desc');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (21, 'The 100', 'Tue May 18 12:59:08 CEST 2021', false, 'Yeet a couple 100 people to die on earth');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (67, 'Season1', 'Sat May 22 00:09:37 CEST 2021', false, 'The 100 season 1');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (72, 'Season1E2 - The 100 season 1 episode 2', 'Sun May 23 03:32:27 CEST 2021', false, 'description');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (114, 'Season1', 'Tue May 25 18:52:04 CEST 2021', false, 'god søson');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (113, 'This is ', 'Tue May 25 18:51:50 CEST 2021', true, '	fdsf');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (70, 'Season2', 'Sat May 22 00:56:39 CEST 2021', false, 'The 100 Season 2');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (64, 'Season1', 'Fri May 21 17:51:55 CEST 2021', false, 'Test season desc');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (65, 'Season1E1 - Test Episode', 'Fri May 21 17:52:25 CEST 2021', false, 'description');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (68, 'Season1E1 - First episode', 'Sat May 22 00:10:41 CEST 2021', false, 'description');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (117, 'Test movie with actor', 'Wed May 26 17:02:17 CEST 2021', false, 'To test actor role in gui add');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (131, 'Test person cascade', 'Thu May 27 15:02:46 CEST 2021', false, 'cascade test persons');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (133, 'Kurs mod fjerne kyster', 'Sat May 29 21:23:34 CEST 2021', false, 'Få et skud sydhavsvarme her i vinterkulden og tag med Mikkel Beha Erichsen og hans familie på en eventyrlig sørejse!');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (134, 'Season1', 'Sat May 29 21:25:05 CEST 2021', false, ' ');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (135, 'Mikkel Beha Erichsen', 'Sat May 29 21:28:37 CEST 2021', true, 'Rejserapportør og tv vært');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (142, 'Henrik Rivold', 'Sat May 29 21:35:51 CEST 2021', true, '');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (139, 'Henrik Toubro', 'Sat May 29 21:33:11 CEST 2021', true, '');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (143, 'Emil Midé Erichsen', 'Sat May 29 21:36:38 CEST 2021', true, '');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (140, 'Marian Midé', 'Sat May 29 21:33:51 CEST 2021', true, '');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (8, 'Hans Pedersen', 'Tue May 18 10:52:01 CEST 2021', true, 'Jeg er sej');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (141, 'Finn Hagemann', 'Sat May 29 21:35:04 CEST 2021', true, '');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (138, 'Jesper Ærø', 'Sat May 29 21:32:28 CEST 2021', true, '');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (144, 'Theis Midé Erichsen', 'Sat May 29 21:36:48 CEST 2021', true, '');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (145, 'Season1E1 - Rejserapportage ', 'Sat May 29 21:43:34 CEST 2021', false, 'description');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (146, 'Mads Mikkelsen', 'Sat May 29 21:48:49 CEST 2021', false, '');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (148, 'Leonardo de Cabrio', 'Sat May 29 22:09:58 CEST 2021', false, '');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (149, 'The Wolf of Wallstreet', 'Sat May 29 22:10:45 CEST 2021', false, 'Afhængig børsmægler');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (150, 'Kate Winslet', 'Sat May 29 22:20:00 CEST 2021', false, '');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (151, 'Billy Zane', 'Sat May 29 22:22:16 CEST 2021', false, '');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (23, 'Jørgen Morgen', 'Wed May 19 12:14:18 CEST 2021', false, '');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (24, 'Jeg er ændret fra GUI', 'Wed May 19 12:15:29 CEST 2021', false, '');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (112, 'Børge Mogense', 'Tue May 25 12:24:30 CEST 2021', false, 'Dansker');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (76, 'write', 'Sun May 23 21:49:52 CEST 2021', false, 'to db2');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (77, 'writethistodb', 'Sun May 23 21:50:17 CEST 2021', false, 'hello');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (49, 'movie test', 'Thu May 20 13:16:23 CEST 2021', false, 'movie test');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (18, 'Interstellar', 'Tue May 18 11:56:16 CEST 2021', true, 'A very good movie');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (1, 'Jens Peter', 'Sun Apr 11 14:55:59 CEST 2021', true, 'Jens er en kameramand');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (152, 'Titanic', 'Sat May 29 22:24:53 CEST 2021', false, 'Titanic rammer et isbjerg og synker');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (4, 'This jen', 'Tue Apr 20 12:20:01 CEST 2020', true, 'this');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (2, 'Howl´s Moving Castle', 'Tue Apr 20 12:20:01 CEST 2020', false, 'A movie about a magic moving castle');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (25, 'Test with description', 'Wed May 19 12:21:43 CEST 2021', false, 'Not empty');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (147, 'Jagten', 'Sat May 29 21:49:36 CEST 2021', true, 'Jagten er en dansk drama film fra 2012 instrueret af Thomas Vinterberg og med Mads Mikkelsen i hovedrollen. Historien foregår i en lille dansk landsby omkring jul og følger en mand, der bliver mål for massehysteri efter fejlagtigt at være beskyldt for at have seksuelt misbrugt et barn.');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (74, 'Hyeeet EDIT GUI', 'Sun May 23 03:38:57 CEST 2021', true, 'test movie');
INSERT INTO public.credits (credit_id, name, date_added, approved, description) VALUES (115, 'Season1E1 - epi', 'Tue May 25 18:52:26 CEST 2021', false, 'Beskrivelse fra gui');


INSERT INTO public.productions (production_id, credit_id, category_id, length_in_secs, release_date) VALUES (17, 65, 4, 123456, 'Fri May 21 17:52:25 CEST 2021');
INSERT INTO public.productions (production_id, credit_id, category_id, length_in_secs, release_date) VALUES (18, 68, 4, 1234, 'Sat May 22 00:10:41 CEST 2021');
INSERT INTO public.productions (production_id, credit_id, category_id, length_in_secs, release_date) VALUES (20, 72, 4, 12345, 'Sun May 23 03:32:27 CEST 2021');
INSERT INTO public.productions (production_id, credit_id, category_id, length_in_secs, release_date) VALUES (21, 73, 4, 12456, 'Sun May 23 03:34:01 CEST 2021');
INSERT INTO public.productions (production_id, credit_id, category_id, length_in_secs, release_date) VALUES (24, 79, 2, 1231, 'Sun May 23 21:51:09 CEST 2021');
INSERT INTO public.productions (production_id, credit_id, category_id, length_in_secs, release_date) VALUES (4, 18, 12, 10420, 'Thu Nov 06 00:00:00 CET 2014');
INSERT INTO public.productions (production_id, credit_id, category_id, length_in_secs, release_date) VALUES (26, 117, 2, 123456, 'Wed May 26 17:02:17 CEST 2021');
INSERT INTO public.productions (production_id, credit_id, category_id, length_in_secs, release_date) VALUES (33, 145, 1, 2349, 'Sat May 29 21:43:34 CEST 2021');
INSERT INTO public.productions (production_id, credit_id, category_id, length_in_secs, release_date) VALUES (34, 147, 4, 4230, 'Sat May 29 21:49:36 CEST 2021');
INSERT INTO public.productions (production_id, credit_id, category_id, length_in_secs, release_date) VALUES (35, 149, 6, 5137, 'Sat May 29 22:10:45 CEST 2021');
INSERT INTO public.productions (production_id, credit_id, category_id, length_in_secs, release_date) VALUES (7, 49, 11, 657531, 'Thu May 20 13:16:23 CEST 2021');
INSERT INTO public.productions (production_id, credit_id, category_id, length_in_secs, release_date) VALUES (22, 74, 7, 34, 'Sun May 23 03:38:57 CEST 2021');
INSERT INTO public.productions (production_id, credit_id, category_id, length_in_secs, release_date) VALUES (1, 2, 13, 527023, 'Sun Sep 05 12:20:02 CEST 2004');
INSERT INTO public.productions (production_id, credit_id, category_id, length_in_secs, release_date) VALUES (36, 152, 3, 3659, 'Sat May 29 22:24:53 CEST 2021');
INSERT INTO public.productions (production_id, credit_id, category_id, length_in_secs, release_date) VALUES (25, 115, 4, 56223, 'Tue May 25 18:52:26 CEST 2021');



INSERT INTO public.persons (person_id, credit_id, phone_number, email, personal_info) VALUES (1, 1, '42124433', 'jens@test.com', 'Det har jens skrevet selv');
INSERT INTO public.persons (person_id, credit_id, phone_number, email, personal_info) VALUES (3, 4, '123434234', 'this@that.com', 'This is this isjdflsakdfj ');
INSERT INTO public.persons (person_id, credit_id, phone_number, email, personal_info) VALUES (5, 8, '12345678', 'bareMig@gmail.com', 'Yeet');
INSERT INTO public.persons (person_id, credit_id, phone_number, email, personal_info) VALUES (48, 131, '12341234', 'cascade@test.dk', '');
INSERT INTO public.persons (person_id, credit_id, phone_number, email, personal_info) VALUES (49, 135, '', '', '');
INSERT INTO public.persons (person_id, credit_id, phone_number, email, personal_info) VALUES (52, 138, '', '', '');
INSERT INTO public.persons (person_id, credit_id, phone_number, email, personal_info) VALUES (53, 139, '', '', '');
INSERT INTO public.persons (person_id, credit_id, phone_number, email, personal_info) VALUES (54, 140, '', '', '');
INSERT INTO public.persons (person_id, credit_id, phone_number, email, personal_info) VALUES (55, 141, '', '', '');
INSERT INTO public.persons (person_id, credit_id, phone_number, email, personal_info) VALUES (56, 142, '', '', '');
INSERT INTO public.persons (person_id, credit_id, phone_number, email, personal_info) VALUES (57, 143, '', '', '');
INSERT INTO public.persons (person_id, credit_id, phone_number, email, personal_info) VALUES (58, 144, '', '', '');
INSERT INTO public.persons (person_id, credit_id, phone_number, email, personal_info) VALUES (59, 146, '', '', '');
INSERT INTO public.persons (person_id, credit_id, phone_number, email, personal_info) VALUES (60, 148, '', '', '');
INSERT INTO public.persons (person_id, credit_id, phone_number, email, personal_info) VALUES (61, 150, '', '', '');
INSERT INTO public.persons (person_id, credit_id, phone_number, email, personal_info) VALUES (62, 151, '', '', '');
INSERT INTO public.persons (person_id, credit_id, phone_number, email, personal_info) VALUES (9, 23, '20202020', 'Jorgen.Attempt1@medister.pølse', 'Jørgen er fra Langeland og har 372 børn');
INSERT INTO public.persons (person_id, credit_id, phone_number, email, personal_info) VALUES (10, 24, '78947894', 'detMegaFed@gmail.com', 'Jeg har lige ændret denne fra gui');
INSERT INTO public.persons (person_id, credit_id, phone_number, email, personal_info) VALUES (45, 112, '123', 'et@hotmail.com', 'Edit fra GUI');
INSERT INTO public.persons (person_id, credit_id, phone_number, email, personal_info) VALUES (11, 25, '20022002', 'desc@test.com', '');



INSERT INTO public.shows (show_id, credit_id, all_seasons_approved) VALUES (1, 21, false);
INSERT INTO public.shows (show_id, credit_id, all_seasons_approved) VALUES (15, 63, false);
INSERT INTO public.shows (show_id, credit_id, all_seasons_approved) VALUES (18, 113, false);
INSERT INTO public.shows (show_id, credit_id, all_seasons_approved) VALUES (22, 133, false);
INSERT INTO public.shows (show_id, credit_id, all_seasons_approved) VALUES (16, 76, false);
INSERT INTO public.shows (show_id, credit_id, all_seasons_approved) VALUES (17, 77, false);



INSERT INTO public.seasons (season_id, credit_id, show_id, all_episodes_approved) VALUES (9, 64, 15, false);
INSERT INTO public.seasons (season_id, credit_id, show_id, all_episodes_approved) VALUES (11, 67, 1, false);
INSERT INTO public.seasons (season_id, credit_id, show_id, all_episodes_approved) VALUES (13, 70, 1, false);
INSERT INTO public.seasons (season_id, credit_id, show_id, all_episodes_approved) VALUES (14, 78, 17, false);
INSERT INTO public.seasons (season_id, credit_id, show_id, all_episodes_approved) VALUES (15, 114, 18, false);
INSERT INTO public.seasons (season_id, credit_id, show_id, all_episodes_approved) VALUES (19, 134, 22, false);



INSERT INTO public.episodes (episode_id, production_id, season_id) VALUES (4, 17, 9);
INSERT INTO public.episodes (episode_id, production_id, season_id) VALUES (5, 18, 11);
INSERT INTO public.episodes (episode_id, production_id, season_id) VALUES (7, 20, 11);
INSERT INTO public.episodes (episode_id, production_id, season_id) VALUES (8, 21, 11);
INSERT INTO public.episodes (episode_id, production_id, season_id) VALUES (10, 24, 14);
INSERT INTO public.episodes (episode_id, production_id, season_id) VALUES (11, 25, 15);
INSERT INTO public.episodes (episode_id, production_id, season_id) VALUES (15, 33, 19);



INSERT INTO public.movies (movie_id, production_id) VALUES (1, 1);
INSERT INTO public.movies (movie_id, production_id) VALUES (4, 4);
INSERT INTO public.movies (movie_id, production_id) VALUES (6, 7);
INSERT INTO public.movies (movie_id, production_id) VALUES (14, 22);
INSERT INTO public.movies (movie_id, production_id) VALUES (15, 26);
INSERT INTO public.movies (movie_id, production_id) VALUES (17, 34);
INSERT INTO public.movies (movie_id, production_id) VALUES (18, 35);
INSERT INTO public.movies (movie_id, production_id) VALUES (19, 36);



INSERT INTO public.jobs (job_id, person_id, job_role_id, production_id) VALUES (1, 1, 11, 1);
INSERT INTO public.jobs (job_id, person_id, job_role_id, production_id) VALUES (2, 1, 7, 1);
INSERT INTO public.jobs (job_id, person_id, job_role_id, production_id) VALUES (6, 1, 8, 1);
INSERT INTO public.jobs (job_id, person_id, job_role_id, production_id) VALUES (20, 1, 16, 17);
INSERT INTO public.jobs (job_id, person_id, job_role_id, production_id) VALUES (24, 1, 4, 25);
INSERT INTO public.jobs (job_id, person_id, job_role_id, production_id) VALUES (27, 9, 1, 18);
INSERT INTO public.jobs (job_id, person_id, job_role_id, production_id) VALUES (28, 9, 1, 26);
INSERT INTO public.jobs (job_id, person_id, job_role_id, production_id) VALUES (30, 58, 24, 33);
INSERT INTO public.jobs (job_id, person_id, job_role_id, production_id) VALUES (31, 57, 24, 33);
INSERT INTO public.jobs (job_id, person_id, job_role_id, production_id) VALUES (32, 59, 1, 34);
INSERT INTO public.jobs (job_id, person_id, job_role_id, production_id) VALUES (33, 60, 1, 35);
INSERT INTO public.jobs (job_id, person_id, job_role_id, production_id) VALUES (35, 61, 1, 36);
INSERT INTO public.jobs (job_id, person_id, job_role_id, production_id) VALUES (36, 60, 1, 36);
INSERT INTO public.jobs (job_id, person_id, job_role_id, production_id) VALUES (37, 62, 1, 36);
INSERT INTO public.jobs (job_id, person_id, job_role_id, production_id) VALUES (38, 9, 5, 4);
INSERT INTO public.jobs (job_id, person_id, job_role_id, production_id) VALUES (44, 9, 1, 35);
INSERT INTO public.jobs (job_id, person_id, job_role_id, production_id) VALUES (45, 9, 3, 1);
INSERT INTO public.jobs (job_id, person_id, job_role_id, production_id) VALUES (46, 11, 3, 1);
INSERT INTO public.jobs (job_id, person_id, job_role_id, production_id) VALUES (47, 9, 4, 22);
INSERT INTO public.jobs (job_id, person_id, job_role_id, production_id) VALUES (48, 45, 4, 4);
INSERT INTO public.jobs (job_id, person_id, job_role_id, production_id) VALUES (50, 11, 1, 7);
INSERT INTO public.jobs (job_id, person_id, job_role_id, production_id) VALUES (51, 11, 2, 1);
INSERT INTO public.jobs (job_id, person_id, job_role_id, production_id) VALUES (52, 3, 1, 7);
INSERT INTO public.jobs (job_id, person_id, job_role_id, production_id) VALUES (53, 3, 4, 1);
INSERT INTO public.jobs (job_id, person_id, job_role_id, production_id) VALUES (54, 3, 8, 22);
INSERT INTO public.jobs (job_id, person_id, job_role_id, production_id) VALUES (55, 48, 1, 26);
INSERT INTO public.jobs (job_id, person_id, job_role_id, production_id) VALUES (56, 3, 3, 4);



INSERT INTO public.character_names (character_name_id, job_id, character_name) VALUES (1, 27, 'Murphy');
INSERT INTO public.character_names (character_name_id, job_id, character_name) VALUES (2, 28, 'Dreng #1');
INSERT INTO public.character_names (character_name_id, job_id, character_name) VALUES (3, 32, 'Lucas');
INSERT INTO public.character_names (character_name_id, job_id, character_name) VALUES (4, 33, 'Jordan belfort');
INSERT INTO public.character_names (character_name_id, job_id, character_name) VALUES (5, 35, 'Rose');
INSERT INTO public.character_names (character_name_id, job_id, character_name) VALUES (6, 36, 'Jack Dawson');
INSERT INTO public.character_names (character_name_id, job_id, character_name) VALUES (7, 37, 'Caledon');
INSERT INTO public.character_names (character_name_id, job_id, character_name) VALUES (13, 44, 'Baggrunds person 3');
INSERT INTO public.character_names (character_name_id, job_id, character_name) VALUES (15, 50, 'Test123');
INSERT INTO public.character_names (character_name_id, job_id, character_name) VALUES (16, 52, 'Feldip');
INSERT INTO public.character_names (character_name_id, job_id, character_name) VALUES (17, 55, 'den');