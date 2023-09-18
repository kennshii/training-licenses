ALTER TABLE "user"
ADD COLUMN "email" varchar(50) UNIQUE;
ALTER TABLE "user" ADD CONSTRAINT chech_email CHECK("email" ~ '^[a-zA-Z0-9._+-]+@endava\.com$');
UPDATE "user" SET email=first_name || '.' || last_name || '@endava.com' WHERE email IS NULL;