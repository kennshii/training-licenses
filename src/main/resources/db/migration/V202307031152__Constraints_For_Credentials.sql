ALTER TABLE credential
    ALTER COLUMN username SET DATA TYPE VARCHAR(50),
    ALTER COLUMN username SET NOT NULL,
    ALTER COLUMN password SET DATA TYPE VARCHAR(20),
    ALTER COLUMN password SET NOT NULL;

ALTER TABLE credential
    DROP CONSTRAINT IF EXISTS username_format_check,
    DROP CONSTRAINT IF EXISTS password_format_check,
    DROP CONSTRAINT IF EXISTS password_length_check;

ALTER TABLE credential
    ADD CONSTRAINT username_format_check CHECK (username ~ '^[a-zA-Z0-9._+-]+@endava\.com$');

ALTER TABLE credential
    ADD CONSTRAINT password_format_check CHECK (password ~ '^[a-zA-Z0-9!@#$%^&*()\-=_+]+'),
    ADD CONSTRAINT password_length_check CHECK (LENGTH(password) >= 5 AND LENGTH(password) <= 20);