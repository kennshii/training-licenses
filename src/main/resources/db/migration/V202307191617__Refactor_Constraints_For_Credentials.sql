ALTER TABLE credential
    DROP CONSTRAINT IF EXISTS username_format_check,
    DROP CONSTRAINT IF EXISTS password_format_check,
    DROP CONSTRAINT IF EXISTS password_length_check;

ALTER TABLE credential
    ADD CONSTRAINT username_format_check CHECK (username ~ '^[!-/:-@\[-`{-~\w\s]+@endava\.com$');

ALTER TABLE credential
    ADD CONSTRAINT password_format_check CHECK (password ~ '^[!-/:-@\[-`{-~\w]{5,20}$');
