-- =====================================
-- Inventario de Recursos - PostgreSQL (SQL COMPLETO)
-- =====================================

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- =====================================================
-- USUARIOS
-- =====================================================
CREATE TABLE IF NOT EXISTS usuarios (
                                        created_at TIMESTAMPTZ DEFAULT NOW(),
                                        user_id    UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                        usuario    VARCHAR(80)  NOT NULL UNIQUE,
                                        nombre     VARCHAR(150) NOT NULL,
                                        correo     VARCHAR(150) NOT NULL UNIQUE,
                                        activo     BOOLEAN NOT NULL DEFAULT TRUE
);

-- =====================================================
-- ROLES
-- =====================================================
CREATE TABLE IF NOT EXISTS roles (
                                     rol_id      BIGSERIAL PRIMARY KEY,
                                     nombre      VARCHAR(100) NOT NULL,
                                     descripcion TEXT,
                                     created_at  TIMESTAMPTZ,
                                     updated_at  TIMESTAMPTZ,
                                     created_by  UUID,
                                     updated_by  UUID,
                                     CONSTRAINT fk_roles_created_by FOREIGN KEY (created_by) REFERENCES usuarios(user_id) ON DELETE SET NULL,
                                     CONSTRAINT fk_roles_updated_by FOREIGN KEY (updated_by) REFERENCES usuarios(user_id) ON DELETE SET NULL
);

-- =====================================================
-- USUARIOS_ROLES
-- =====================================================
CREATE TABLE IF NOT EXISTS usuarios_roles (
                                              user_id UUID   NOT NULL,
                                              rol_id  BIGINT NOT NULL,
                                              PRIMARY KEY (user_id, rol_id),
                                              CONSTRAINT fk_ur_user FOREIGN KEY (user_id) REFERENCES usuarios(user_id) ON DELETE CASCADE,
                                              CONSTRAINT fk_ur_role FOREIGN KEY (rol_id)  REFERENCES roles(rol_id)    ON DELETE CASCADE
);

-- =====================================================
-- CATEGORIA
-- =====================================================
CREATE TABLE IF NOT EXISTS categoria (
                                         categoria_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                         nombre       VARCHAR(100) NOT NULL,
                                         created_at   TIMESTAMPTZ,
                                         updated_at   TIMESTAMPTZ,
                                         created_by   UUID,
                                         updated_by   UUID,
                                         CONSTRAINT fk_categoria_created_by FOREIGN KEY (created_by) REFERENCES usuarios(user_id) ON DELETE SET NULL,
                                         CONSTRAINT fk_categoria_updated_by FOREIGN KEY (updated_by) REFERENCES usuarios(user_id) ON DELETE SET NULL
);

-- =====================================================
-- SUBCATEGORIA
-- =====================================================
CREATE TABLE IF NOT EXISTS subcategoria (
                                            subcategoria_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                            nombre           VARCHAR(100) NOT NULL,
                                            payload          TEXT,
                                            categoria_id     UUID NOT NULL,
                                            created_at       TIMESTAMPTZ,
                                            updated_at       TIMESTAMPTZ,
                                            created_by       UUID,
                                            updated_by       UUID,
                                            CONSTRAINT fk_subcat_categoria FOREIGN KEY (categoria_id)
                                                REFERENCES categoria(categoria_id) ON DELETE CASCADE,
                                            CONSTRAINT fk_subcat_createdby FOREIGN KEY (created_by)
                                                REFERENCES usuarios(user_id) ON DELETE SET NULL,
                                            CONSTRAINT fk_subcat_updatedby FOREIGN KEY (updated_by)
                                                REFERENCES usuarios(user_id) ON DELETE SET NULL
);

-- =====================================================
-- UBICACION (PLANA; padre_id opcional)
-- =====================================================
CREATE TABLE IF NOT EXISTS ubicacion (
                                         ubicacion_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                         nombre       VARCHAR(150) NOT NULL UNIQUE,
                                         padre_id     UUID,
                                         created_at   TIMESTAMPTZ,
                                         updated_at   TIMESTAMPTZ,
                                         created_by   UUID,
                                         updated_by   UUID,
                                         CONSTRAINT fk_ubic_padre FOREIGN KEY (padre_id)
                                             REFERENCES ubicacion(ubicacion_id) ON DELETE SET NULL,
                                         CONSTRAINT fk_ubic_createdby FOREIGN KEY (created_by)
                                             REFERENCES usuarios(user_id) ON DELETE SET NULL,
                                         CONSTRAINT fk_ubic_updatedby FOREIGN KEY (updated_by)
                                             REFERENCES usuarios(user_id) ON DELETE SET NULL
);

-- =====================================================
-- ALCANCE
-- =====================================================
CREATE TABLE IF NOT EXISTS alcance (
                                       alcance_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                       nombre     VARCHAR(120) NOT NULL UNIQUE
);

-- =====================================================
-- RECURSO
-- =====================================================
CREATE TABLE IF NOT EXISTS recurso (
                                       recurso_id      UUID PRIMARY KEY DEFAULT uuid_generate_v4(),

                                       ip              VARCHAR(50),
                                       titulo          VARCHAR(150),
                                       clave           VARCHAR(100),
                                       nombre          VARCHAR(150),
                                       usuario         VARCHAR(100),
                                       correo          VARCHAR(150),
                                       serie_codigo    VARCHAR(100),
                                       descripcion     TEXT,
                                       rangos_ip       VARCHAR(100),
                                       puerto          VARCHAR(50),
                                       nano_id         VARCHAR(50),

                                       activo          BOOLEAN NOT NULL DEFAULT TRUE,

                                       usuario_id      UUID NOT NULL,
                                       subcategoria_id UUID NOT NULL,
                                       ubicacion_id    UUID,
                                       alcance_id      UUID NOT NULL,

                                       created_at      TIMESTAMPTZ,
                                       updated_at      TIMESTAMPTZ,
                                       created_by      UUID,
                                       updated_by      UUID,

                                       CONSTRAINT fk_recurso_usuario FOREIGN KEY (usuario_id)
                                           REFERENCES usuarios(user_id) ON DELETE CASCADE,
                                       CONSTRAINT fk_recurso_subcategoria FOREIGN KEY (subcategoria_id)
                                           REFERENCES subcategoria(subcategoria_id) ON DELETE RESTRICT,
                                       CONSTRAINT fk_recurso_ubicacion FOREIGN KEY (ubicacion_id)
                                           REFERENCES ubicacion(ubicacion_id) ON DELETE SET NULL,
                                       CONSTRAINT fk_recurso_alcance FOREIGN KEY (alcance_id)
                                           REFERENCES alcance(alcance_id) ON DELETE RESTRICT,
                                       CONSTRAINT fk_recurso_createdby FOREIGN KEY (created_by)
                                           REFERENCES usuarios(user_id) ON DELETE SET NULL,
                                       CONSTRAINT fk_recurso_updatedby FOREIGN KEY (updated_by)
                                           REFERENCES usuarios(user_id) ON DELETE SET NULL
);

-- =====================================================
-- USUARIOS_UBICACION (N - N)
-- =====================================================
CREATE TABLE IF NOT EXISTS usuarios_ubicacion (
                                                  user_id      UUID NOT NULL,
                                                  ubicacion_id UUID NOT NULL,
                                                  PRIMARY KEY (user_id, ubicacion_id),
                                                  CONSTRAINT fk_uu_user FOREIGN KEY (user_id) REFERENCES usuarios(user_id) ON DELETE CASCADE,
                                                  CONSTRAINT fk_uu_ubic FOREIGN KEY (ubicacion_id) REFERENCES ubicacion(ubicacion_id) ON DELETE CASCADE
);

-- =====================================================
-- RECURSOS_UBICACIONES (N - N)
-- =====================================================
CREATE TABLE IF NOT EXISTS recursos_ubicaciones (
                                                    ubicacion_id UUID NOT NULL,
                                                    recurso_id   UUID NOT NULL,
                                                    created_at   TIMESTAMPTZ DEFAULT NOW(),
                                                    PRIMARY KEY (ubicacion_id, recurso_id),
                                                    CONSTRAINT fk_ru_ubic FOREIGN KEY (ubicacion_id) REFERENCES ubicacion(ubicacion_id) ON DELETE CASCADE,
                                                    CONSTRAINT fk_ru_rec  FOREIGN KEY (recurso_id)   REFERENCES recurso(recurso_id)   ON DELETE CASCADE
);

-- =====================================================
-- TOKENS
-- =====================================================
CREATE TABLE IF NOT EXISTS tokens (
                                      token_id   BIGSERIAL PRIMARY KEY,
                                      token      TEXT NOT NULL,
                                      token_type VARCHAR(30) NOT NULL,
                                      expired    BOOLEAN NOT NULL DEFAULT FALSE,
                                      revoke     BOOLEAN NOT NULL DEFAULT FALSE,
                                      created_at TIMESTAMPTZ DEFAULT NOW(),
                                      user_id    UUID NOT NULL,
                                      CONSTRAINT fk_tokens_user FOREIGN KEY (user_id)
                                          REFERENCES usuarios(user_id) ON DELETE CASCADE
);

-- =====================================================
-- ÍNDICES
-- =====================================================
CREATE INDEX IF NOT EXISTS idx_subcategoria_categoria   ON subcategoria(categoria_id);
CREATE INDEX IF NOT EXISTS idx_ubicacion_padre          ON ubicacion(padre_id);

CREATE INDEX IF NOT EXISTS idx_recurso_activo           ON recurso(activo);
CREATE INDEX IF NOT EXISTS idx_recurso_usuario_id       ON recurso(usuario_id);
CREATE INDEX IF NOT EXISTS idx_recurso_subcategoria_id  ON recurso(subcategoria_id);
CREATE INDEX IF NOT EXISTS idx_recurso_ubicacion_id     ON recurso(ubicacion_id);
CREATE INDEX IF NOT EXISTS idx_recurso_alcance_id       ON recurso(alcance_id);

CREATE UNIQUE INDEX IF NOT EXISTS ux_tokens_token       ON tokens(token);
CREATE INDEX IF NOT EXISTS idx_tokens_user              ON tokens(user_id);
CREATE INDEX IF NOT EXISTS idx_tokens_valid_by_user     ON tokens(user_id)
    WHERE expired = FALSE AND revoke = FALSE;

CREATE INDEX IF NOT EXISTS idx_ru_ubicacion             ON recursos_ubicaciones(ubicacion_id);
CREATE INDEX IF NOT EXISTS idx_ru_recurso               ON recursos_ubicaciones(recurso_id);

-- =====================================================
-- INSERTS POR DEFECTO
-- =====================================================

-- ROLES
INSERT INTO roles (nombre, descripcion, created_at, updated_at)
VALUES
    ('ADMIN',          'ADMINISTRADOR',   NOW(), NOW()),
    ('CUSTOMER',       'CLIENTE',         NOW(), NOW()),
    ('MANAGER',        'DIRECTOR',        NOW(), NOW()),
    ('INFRASTRUCTURE', 'INFRAESTRUCTURA', NOW(), NOW()),
    ('TECHNICAL',      'TECNICO',         NOW(), NOW()),
    ('RECEPTION',      'SECRETARIA',      NOW(), NOW())
ON CONFLICT DO NOTHING;

-- ALCANCES
INSERT INTO alcance (nombre)
VALUES ('PUBLICO'), ('PRIVADO')
ON CONFLICT (nombre) DO NOTHING;

-- USUARIO ADMIN
INSERT INTO usuarios (usuario, nombre, correo, activo)
VALUES ('dorian.armijos', 'Dorian Armijos', 'dorian.armijos@atencionintegral.gob.ec', TRUE)
ON CONFLICT (correo) DO NOTHING;

-- ROL ADMIN AL USUARIO
INSERT INTO usuarios_roles (user_id, rol_id)
SELECT u.user_id, r.rol_id
FROM usuarios u
         JOIN roles r ON r.nombre = 'ADMIN'
WHERE u.correo = 'dorian.armijos@atencionintegral.gob.ec'
ON CONFLICT DO NOTHING;

-- =====================================================
-- UBICACIONES (PLANAS; sin crear padres como "CPL", "UAT", etc.)
-- =====================================================
INSERT INTO ubicacion (nombre, padre_id, created_at, updated_at)
VALUES
    ('PLANTA_CENTRAL', NULL, NOW(), NOW()),
    ('TERRITORIO',     NULL, NOW(), NOW()),

-- CPL
    ('CPL Morona Santiago 1', NULL, NOW(), NOW()),
    ('CPL El Oro 2',          NULL, NOW(), NOW()),
    ('CPL Cañar 2',           NULL, NOW(), NOW()),
    ('CPL Carchi 1',          NULL, NOW(), NOW()),
    ('CPL Azuay 1',           NULL, NOW(), NOW()),
    ('CPL Bolivar 1',         NULL, NOW(), NOW()),
    ('CPL Chimborazo 1',      NULL, NOW(), NOW()),
    ('CPL Chimborazo 2',      NULL, NOW(), NOW()),
    ('CPL Chimborazo 3',      NULL, NOW(), NOW()),
    ('CPL Esmeraldas 2',      NULL, NOW(), NOW()),
    ('CPL Esmeraldas 1',      NULL, NOW(), NOW()),
    ('CPL Guayas 1',          NULL, NOW(), NOW()),
    ('CPL Guayas 2',          NULL, NOW(), NOW()),
    ('CPL Guayas 5',          NULL, NOW(), NOW()),
    ('CPL Imbabura 1',        NULL, NOW(), NOW()),
    ('CPL Loja 1',            NULL, NOW(), NOW()),
    ('CPL Los Rios 2',        NULL, NOW(), NOW()),
    ('CPL Manabi 1',          NULL, NOW(), NOW()),
    ('CPL Manabi 4',          NULL, NOW(), NOW()),
    ('CPL Napo 1',            NULL, NOW(), NOW()),
    ('CPL Sucumbíos 1',       NULL, NOW(), NOW()),
    ('CPL Sto Domingo 1',     NULL, NOW(), NOW()),
    ('CPL Tungurahua 1',      NULL, NOW(), NOW()),
    ('CPL Pichincha 3',       NULL, NOW(), NOW()),
    ('CPL Cotopaxi 1',        NULL, NOW(), NOW()),
    ('CPL El Oro 1',          NULL, NOW(), NOW()),

-- CRS
    ('CRS Cañar 1',     NULL, NOW(), NOW()),
    ('CRS Guayas 3',    NULL, NOW(), NOW()),
    ('CRS Manabí 2',    NULL, NOW(), NOW()),
    ('CRS Manabí 3',    NULL, NOW(), NOW()),
    ('CRS Pichincha 2', NULL, NOW(), NOW()),
    ('CRS Guayas 4',    NULL, NOW(), NOW()),

-- CPPL
    ('CPPL Pichincha 1', NULL, NOW(), NOW()),
    ('CPPL Pastaza 1',   NULL, NOW(), NOW()),

-- UAT
    ('UAT Patria',         NULL, NOW(), NOW()),
    ('UAT Manta',          NULL, NOW(), NOW()),
    ('UAT Ambato',         NULL, NOW(), NOW()),
    ('UAT Esmeraldas',     NULL, NOW(), NOW()),
    ('UAT El Empalme',     NULL, NOW(), NOW()),
    ('UAT Cuartel Modelo', NULL, NOW(), NOW()),
    ('UAT-Cuenca',         NULL, NOW(), NOW()),
    ('UAT-Ibarra',         NULL, NOW(), NOW()),
    ('UAT Quitumbe',       NULL, NOW(), NOW()),
    ('UAT Iñaquito',       NULL, NOW(), NOW()),
    ('UAT Sto Domingo',    NULL, NOW(), NOW()),

-- URS
    ('URS Esmeraldas 1',               NULL, NOW(), NOW()),
    ('URS Guayas',                     NULL, NOW(), NOW()),
    ('URS Carchi',                     NULL, NOW(), NOW()),
    ('URS Pichincha',                  NULL, NOW(), NOW()),
    ('URS Sto Domingo',                NULL, NOW(), NOW()),
    ('URS Napo',                       NULL, NOW(), NOW()),
    ('URS Cotopaxi',                   NULL, NOW(), NOW()),
    ('URS Esmeraldas Winchele',        NULL, NOW(), NOW()),
    ('URS Manabi 3 Bahia de Caraquez', NULL, NOW(), NOW()),
    ('URS Manabi 1 Femenino',          NULL, NOW(), NOW()),
    ('URS Imbabura 1',                 NULL, NOW(), NOW()),
    ('URS Sucumbios',                  NULL, NOW(), NOW()),
    ('URS Manabi 4 El Rodeo',          NULL, NOW(), NOW()),
    ('URS Manabi 2 Jipijapa',          NULL, NOW(), NOW()),
    ('URS El Oro 2 Zaruma',            NULL, NOW(), NOW()),
    ('URS Morona Santiago',            NULL, NOW(), NOW()),
    ('URS Los Rios 2',                 NULL, NOW(), NOW()),
    ('URS Ambato',                     NULL, NOW(), NOW()),
    ('URS Chimborazo 1',               NULL, NOW(), NOW()),
    ('URS Los Rios 1',                 NULL, NOW(), NOW()),
    ('URS Bolivar',                    NULL, NOW(), NOW()),
    ('URS Chimborazo 2 Alausí',        NULL, NOW(), NOW()),
    ('URS El Oro 1 Machala',           NULL, NOW(), NOW()),
    ('URS Loja',                       NULL, NOW(), NOW()),
    ('URS Pichincha 2',                NULL, NOW(), NOW()),
    ('URS Pastaza',                    NULL, NOW(), NOW()),
    ('URS Sto Domingo 2',              NULL, NOW(), NOW()),
    ('URS Cañar 1',                    NULL, NOW(), NOW()),
    ('URS Azuay 1',                    NULL, NOW(), NOW()),
    ('URS Cañar 2',                    NULL, NOW(), NOW()),

-- Otros
    ('CAI Cuenca', NULL, NOW(), NOW())
ON CONFLICT (nombre) DO NOTHING;

-- =====================================================
-- ASIGNAR UBICACION POR DEFECTO AL ADMIN (PLANTA_CENTRAL)
-- =====================================================
INSERT INTO usuarios_ubicacion (user_id, ubicacion_id)
SELECT u.user_id, ub.ubicacion_id
FROM usuarios u
         JOIN ubicacion ub ON ub.nombre = 'PLANTA_CENTRAL'
WHERE u.correo = 'dorian.armijos@atencionintegral.gob.ec'
ON CONFLICT DO NOTHING;
