-- =====================
-- INSERTAR PERMISOS
-- =====================
INSERT INTO permission (name) VALUES ('projects:read');
INSERT INTO permission (name) VALUES ('projects:write');

-- =====================
-- INSERTAR ROLES
-- =====================
INSERT INTO role (name) VALUES ('ROLE_ADMIN');
INSERT INTO role (name) VALUES ('ROLE_USER');

-- ===============================
-- ASOCIAR PERMISOS A LOS ROLES
-- ===============================
INSERT INTO role_permission (role_id, permission_id) VALUES (1, 1); -- ADMIN -> read
INSERT INTO role_permission (role_id, permission_id) VALUES (1, 2); -- ADMIN -> write
INSERT INTO role_permission (role_id, permission_id) VALUES (2, 1); -- USER -> read

-- ==========================
-- INSERTAR USUARIOS
-- ==========================
INSERT INTO app_user (name, email, password)
VALUES ('admin', 'admin@example.com', '{noop}admin123');

INSERT INTO app_user (name, email, password)
VALUES ('user', 'user@example.com', '{noop}user123');

-- ===================================
-- ASOCIAR ROLES A LOS USUARIOS
-- ===================================
INSERT INTO usuario_rol (usuario_id, rol_id) VALUES (1, 1); -- admin -> ROLE_ADMIN
INSERT INTO usuario_rol (usuario_id, rol_id) VALUES (2, 2); -- user -> ROLE_USER
