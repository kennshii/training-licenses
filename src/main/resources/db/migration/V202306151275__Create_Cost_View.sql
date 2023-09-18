CREATE TABLE license_history
(license_id int8 NOT NULL PRIMARY KEY,
start_date date NOT NULL
);

CREATE FUNCTION add_date_for_license()
RETURNS TRIGGER
AS $$
BEGIN
INSERT INTO license_history VALUES(NEW.id,DATE(NOW()));
RETURN NEW;
END; $$ language plpgsql;

CREATE TRIGGER license_trigger
AFTER INSERT
ON license
FOR EACH ROW
EXECUTE PROCEDURE add_date_for_license();


CREATE VIEW license_cost_view
AS
SELECT id, expires_on, start_date, cost AS license_cost, license_type
FROM license
INNER JOIN
license_history
ON id = license_id;



