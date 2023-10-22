CREATE OR REPLACE VIEW v_asset AS
SELECT
  a.id_asset AS asset_id,
  a.type AS asset_type,
  a.brand AS asset_brand,
  a.serial_number AS asset_serial_number,
  a.purchase_date AS asset_purchase_date,
  a.has_antivirus AS asset_has_antivirus,
  a.antivirus_expiration_date AS asset_antivirus_expiration_date,
  a.sim_number AS asset_sim_number,
  a.os AS asset_os,
  c.company_name AS company_name,
  e.first_name AS employee_first_name,
  e.id_employee AS employee_id_employee,
  e.last_name AS employee_last_name,
  h.assignment_date AS history_assignment_date,
  h.id_history AS history_id_history,
  h.asset_status AS history_asset_status,
  h.effective_assignment_date AS history_effective_assignment_date,
  h.signed_document AS history_signed_document,
  h.uploaded_signed_document AS history_uploaded_signed_document
FROM asset AS a
JOIN (
    SELECT asset, MAX(id_history) AS last_id_history
    FROM history
    GROUP BY asset
) latest_history
ON a.id_asset = latest_history.asset
JOIN history h
ON a.id_asset = h.asset AND latest_history.last_id_history = h.id_history
LEFT JOIN company AS c ON a.company = c.id_company
LEFT JOIN employee AS e ON h.employee = e.id_employee
ORDER BY h.effective_assignment_date DESC, h.assignment_date DESC;