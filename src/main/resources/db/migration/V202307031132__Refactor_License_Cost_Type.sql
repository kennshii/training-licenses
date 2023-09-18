DROP VIEW license_cost_view;

ALTER TABLE license
    ALTER COLUMN cost TYPE NUMERIC(7, 2);

CREATE VIEW license_cost_view
AS
SELECT id, expires_on, start_date, cost AS license_cost, license_type
FROM license
         INNER JOIN
     license_history
     ON id = license_id;


