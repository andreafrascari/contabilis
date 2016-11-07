delete from `_system_decode` where sd_class=220;


INSERT INTO `_system_decode` (`sd_description`, `sd_value`, `sd_locale`, `sd_notes`, `sd_class`, `creation_date`, `creation_user`, `deletion_flag`, `activation_flag`) VALUES
('cliente', 31, 'it', NULL, 220, NULL, NULL, 0, 1),
('operatore', 32, 'it', NULL, 220, NULL, NULL, 0, 1),

('contabilita noi: no', 21, 'it', NULL, 220, NULL, NULL, 0, 1),
('contabilita noi: si', 20, 'it', NULL, 220, NULL, NULL, 0, 1),

('partita iva: no', 18, 'it', NULL, 220, NULL, NULL, 0, 1),
('partita iva: si', 19, 'it', NULL, 220, NULL, NULL, 0, 1),

('tipo cliente: P', 2, 'it', NULL, 220, NULL, NULL, 0, 1),
('tipo cliente: SP', 1, 'it', NULL, 220, NULL, NULL, 0, 1),
('tipo cliente: PF', 0, 'it', NULL, 220, NULL, NULL, 0, 1),
('tipo cliente: AP', 4, 'it', NULL, 220, NULL, NULL, 0, 1),
('tipo cliente: I', 3, 'it', NULL, 220, NULL, NULL, 0, 1),
('tipo cliente: NP', 5, 'it', NULL, 220, NULL, NULL, 0, 1),
('tipo cliente: SC', 6, 'it', NULL, 220, NULL, NULL, 0, 1),
('tipo cliente: CP', 7, 'it', NULL, 220, NULL, NULL, 0, 1),
('tipo cliente: INTERNI', 13, 'it', NULL, 220, NULL, NULL, 0, 1),
('tipo cliente: AC', 8, 'it', NULL, 220, NULL, NULL, 0, 1),
('tipo cliente: CO', 9, 'it', NULL, 220, NULL, NULL, 0, 1),
('tipo cliente: FT', 10, 'it', NULL, 220, NULL, NULL, 0, 1),
('tipo cliente: SCP', 11, 'it', NULL, 220, NULL, NULL, 0, 1),
('tipo cliente: SPP', 12, 'it', NULL, 220, NULL, NULL, 0, 1),

('regime iva: L.398/91', 41, 'it', NULL, 220, NULL, NULL, 0, 1),
('regime iva: Ordinario mensile', 42, 'it', NULL, 220, NULL, NULL, 0, 1),
('regime iva: No IVA (superminimi, np privi p.iva)', 43, 'it', NULL, 220, NULL, NULL, 0, 1),
('regime iva: Residuali', 44, 'it', NULL, 220, NULL, NULL, 0, 1),
('regime iva: Ordinario trimestrale', 45, 'it', NULL, 220, NULL, NULL, 0, 1),
('regime iva: Trimestrale IV trim. (autotrasporto, carburanti, etc.)', 46, 'it', NULL, 220, NULL, NULL, 0, 1),
('regime iva: Nuove inziative (L.388/2000)', 47, 'it', NULL, 220, NULL, NULL, 0, 1),

('regime contabile: Semplificato', 51, 'it', NULL, 220, NULL, NULL, 0, 1),
('regime contabile: Ordinario', 52, 'it', NULL, 220, NULL, NULL, 0, 1),
('regime contabile: Residuale', 53, 'it', NULL, 220, NULL, NULL, 0, 1),
('regime contabile: NP privi p.iva', 54, 'it', NULL, 220, NULL, NULL, 0, 1),
('regime contabile: L.398/91', 55, 'it', NULL, 220, NULL, NULL, 0, 1),
('regime contabile: Superminimi', 56, 'it', NULL, 220, NULL, NULL, 0, 1),
('regime contabile: Nuove inziative (L.388/2000)', 57, 'it', NULL, 220, NULL, NULL, 0, 1),

('sostituto d''imposta: si', 61, 'it', NULL, 220, NULL, NULL, 0, 1),
('sostituto d''imposta: no', 62, 'it', NULL, 220, NULL, NULL, 0, 1),

('immobili: si', 71, 'it', NULL, 220, NULL, NULL, 0, 1),
('immobili: no', 72, 'it', NULL, 220, NULL, NULL, 0, 1),
('immobili: leasing', 73, 'it', NULL, 220, NULL, NULL, 0, 1),

('conservazione sostitutiva a norma: si', 81, 'it', NULL, 220, NULL, NULL, 0, 1),
('conservazione sostitutiva a norma: no', 82, 'it', NULL, 220, NULL, NULL, 0, 1),

('f24 cumulativo: si', 91, 'it', NULL, 220, NULL, NULL, 0, 1),
('f24 cumulativo: no', 92, 'it', NULL, 220, NULL, NULL, 0, 1);




