CREATE TABLE public.credits
(
    credit_id integer NOT NULL DEFAULT nextval('credits_credit_id_seq'::regclass),
    name character varying(50) COLLATE pg_catalog."default" NOT NULL,
    date_added character varying(50) COLLATE pg_catalog."default" NOT NULL,
    approved boolean,
    description character varying(500) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT credits_pkey PRIMARY KEY (credit_id)
);

CREATE TABLE public.persons
(
    person_id integer NOT NULL DEFAULT nextval('persons_person_id_seq'::regclass),
    credit_id integer NOT NULL,
    phone_number character varying(50) COLLATE pg_catalog."default",
    email character varying(50) COLLATE pg_catalog."default",
    personal_info character varying(500) COLLATE pg_catalog."default",
    CONSTRAINT persons_pkey PRIMARY KEY (person_id),
    CONSTRAINT persons_credit_id_fkey FOREIGN KEY (credit_id)
        REFERENCES public.credits (credit_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);

CREATE TABLE public.categories
(
    category_id integer NOT NULL DEFAULT nextval('categories_category_id_seq'::regclass),
    category character varying(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT categories_pkey PRIMARY KEY (category_id)
);

CREATE TABLE public.productions
(
    production_id integer NOT NULL DEFAULT nextval('productions_production_id_seq'::regclass),
    credit_id integer NOT NULL,
    category_id integer NOT NULL,
    length_in_secs integer NOT NULL,
    release_date character varying(50) COLLATE pg_catalog."default" NOT NULL,
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
    show_id integer NOT NULL DEFAULT nextval('shows_show_id_seq'::regclass),
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
    season_id integer NOT NULL DEFAULT nextval('seasons_season_id_seq'::regclass),
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
    group_id integer NOT NULL DEFAULT nextval('groups_group_id_seq'::regclass),
    credit_id integer NOT NULL,
    CONSTRAINT groups_pkey PRIMARY KEY (group_id),
    CONSTRAINT groups_credit_id_fkey FOREIGN KEY (credit_id)
        REFERENCES public.credits (credit_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);

CREATE TABLE public.companies
(
    company_id integer NOT NULL DEFAULT nextval('companies_company_id_seq'::regclass),
    credit_id integer NOT NULL,
    CONSTRAINT companies_pkey PRIMARY KEY (company_id),
    CONSTRAINT companies_credit_id_fkey FOREIGN KEY (credit_id)
        REFERENCES public.credits (credit_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE public.movies
(
    movie_id integer NOT NULL DEFAULT nextval('movies_movie_id_seq'::regclass),
    production_id integer,
    CONSTRAINT movies_pkey PRIMARY KEY (movie_id),
    CONSTRAINT movies_production_id_fkey FOREIGN KEY (production_id)
        REFERENCES public.productions (production_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);

CREATE TABLE public.episodes
(
    episode_id integer NOT NULL DEFAULT nextval('episodes_episode_id_seq'::regclass),
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
    job_role_id integer NOT NULL DEFAULT nextval('job_roles_job_role_id_seq'::regclass),
    job_role character varying(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT job_roles_pkey PRIMARY KEY (job_role_id)
);

CREATE TABLE public.jobs
(
    job_id integer NOT NULL DEFAULT nextval('jobs_job_id_seq'::regclass),
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