ALTER TABLE license
ALTER COLUMN website SET DATA TYPE varchar(30),
ALTER COLUMN description SET DATA TYPE varchar(250),
ALTER COLUMN logo SET DATA TYPE bytea,
ALTER COLUMN duration SET DATA TYPE integer,
ALTER COLUMN seats SET DATA TYPE integer;


ALTER TABLE license
    DROP CONSTRAINT IF EXISTS name_length_check,
    DROP CONSTRAINT IF EXISTS website_length_check,
    DROP CONSTRAINT IF EXISTS description_length_check,
    DROP CONSTRAINT IF EXISTS logo_size_check,
    DROP CONSTRAINT IF EXISTS duration_range_check,
    DROP CONSTRAINT IF EXISTS seats_range_check;


ALTER TABLE license
    ADD CONSTRAINT name_length_check CHECK (LENGTH(name) >= 3 AND LENGTH(name) <= 20);

ALTER TABLE license
    ADD CONSTRAINT website_length_check CHECK (LENGTH(website) >= 5 AND LENGTH(website) <= 30);

ALTER TABLE license
    ADD CONSTRAINT description_length_check CHECK (LENGTH(description) >= 5 AND LENGTH(description) <= 250);

ALTER TABLE license
    ADD CONSTRAINT logo_size_check CHECK (LENGTH(logo) >= 2097152 AND LENGTH(logo) <= 10485760);

ALTER TABLE license
    ADD CONSTRAINT duration_range_check CHECK (duration >= 1 AND duration <= 12);

ALTER TABLE license
    ADD CONSTRAINT seats_range_check CHECK (seats >= 20 AND seats <= 250);
