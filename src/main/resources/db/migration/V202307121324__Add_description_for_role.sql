ALTER TABLE "role" ADD COLUMN Description varchar(100);
UPDATE "role" SET Description='Reviewer approves or rejects requests for access to learning resources.'
WHERE name='REVIEWER';
UPDATE "role" SET Description='User can request access to learning resources.'
WHERE name='USER';
UPDATE "role" SET Description='Admin administrates the website.'
WHERE name='ADMIN';
