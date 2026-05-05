CREATE
DATABASE IF NOT EXISTS proyectoInter;
USE
proyectoInter;

CREATE TABLE IF NOT EXISTS usuario
(
    id
    int
    NOT
    NULL
    AUTO_INCREMENT,
    nombre
    varchar
(
    255
) DEFAULT NULL,
    email varchar
(
    255
) DEFAULT NULL,
    password varchar
(
    255
) DEFAULT NULL,
    rol enum
(
    'ADMIN',
    'TUTOR_CENTRO',
    'TUTOR_EMPRESA',
    'ALUMNO'
) DEFAULT NULL,
    PRIMARY KEY
(
    id
),
    UNIQUE KEY email_unique
(
    email
)
    );

CREATE TABLE IF NOT EXISTS empresa
(
    id
    int
    NOT
    NULL
    AUTO_INCREMENT,
    nombre
    varchar
(
    255
) DEFAULT NULL,
    cif varchar
(
    255
) DEFAULT NULL,
    direccion varchar
(
    255
) DEFAULT NULL,
    contacto varchar
(
    255
) DEFAULT NULL,
    email varchar
(
    255
) DEFAULT NULL,
    PRIMARY KEY
(
    id
),
    UNIQUE KEY cif_unique
(
    cif
)
    );

CREATE TABLE IF NOT EXISTS alumno
(
    id
    int
    NOT
    NULL,
    expediente
    varchar
(
    255
) DEFAULT NULL,
    curso varchar
(
    255
) DEFAULT NULL,
    PRIMARY KEY
(
    id
),
    CONSTRAINT alumno_ibfk_1 FOREIGN KEY
(
    id
) REFERENCES usuario
(
    id
) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS tutor
(
    id
    int
    NOT
    NULL,
    tipo
    enum
(
    'EMPRESA',
    'CENTRO'
) DEFAULT NULL,
    empresa_id int DEFAULT NULL,
    PRIMARY KEY
(
    id
),
    CONSTRAINT tutor_ibfk_1 FOREIGN KEY
(
    id
) REFERENCES usuario
(
    id
) ON DELETE CASCADE,
    CONSTRAINT tutor_ibfk_2 FOREIGN KEY
(
    empresa_id
) REFERENCES empresa
(
    id
)
  ON DELETE SET NULL
    );

CREATE TABLE IF NOT EXISTS practica
(
    id
    int
    NOT
    NULL
    AUTO_INCREMENT,
    alumno_id
    int
    DEFAULT
    NULL,
    empresa_id
    int
    DEFAULT
    NULL,
    tutor_empresa_id
    int
    DEFAULT
    NULL,
    tutor_centro_id
    int
    DEFAULT
    NULL,
    fecha_inicio
    date
    DEFAULT
    NULL,
    fecha_fin
    date
    DEFAULT
    NULL,
    estado
    enum
(
    'PENDIENTE',
    'ACTIVA',
    'FINALIZADA'
) DEFAULT NULL,
    PRIMARY KEY
(
    id
)
    );

CREATE TABLE IF NOT EXISTS seguimiento
(
    id
    int
    NOT
    NULL
    AUTO_INCREMENT,
    practica_id
    int
    DEFAULT
    NULL,
    fecha
    date
    DEFAULT
    NULL,
    horas
    float
    DEFAULT
    NULL,
    descripcion
    varchar
(
    255
) DEFAULT NULL,
    validado tinyint
(
    1
) DEFAULT 0,
    PRIMARY KEY
(
    id
),
    CONSTRAINT seguimiento_ibfk_1 FOREIGN KEY
(
    practica_id
) REFERENCES practica
(
    id
)
    );

CREATE TABLE IF NOT EXISTS evaluacion
(
    id
    int
    NOT
    NULL
    AUTO_INCREMENT,
    practica_id
    int
    DEFAULT
    NULL,
    tutor_id
    int
    DEFAULT
    NULL,
    nota
    float
    DEFAULT
    NULL,
    comentarios
    varchar
(
    255
) DEFAULT NULL,
    PRIMARY KEY
(
    id
)
    );

CREATE TABLE IF NOT EXISTS documento
(
    id
    int
    NOT
    NULL
    AUTO_INCREMENT,
    practica_id
    int
    DEFAULT
    NULL,
    tipo
    varchar
(
    255
) DEFAULT NULL,
    ruta varchar
(
    255
) DEFAULT NULL,
    fecha_subida date DEFAULT NULL,
    subido_por int DEFAULT NULL,
    PRIMARY KEY
(
    id
)
    );

INSERT INTO usuario (id, nombre, email, password, rol)
VALUES (10, 'Tutor Centro', 'tutorcentro@correo.com', '1234', 'TUTOR_CENTRO'),
       (11, 'Tutor Empresa', 'tutorempresa@correo.com', '1234', 'TUTOR_EMPRESA'),
       (15, 'Alumno Test', 'alumno@correo.com', '1234', 'ALUMNO'),
       (17, 'Admin', 'admin@correo.com', '1234', 'ADMIN');

INSERT INTO empresa (id, nombre, cif, direccion, contacto, email)
VALUES (1, 'Empresa', 'B12345678', 'Calle Mayor 1', 'Juan García', 'empresa@test.com');

INSERT INTO alumno (id, expediente, curso)
VALUES (15, 'EXP001', '2DAW');

INSERT INTO tutor (id, tipo, empresa_id)
VALUES (10, 'CENTRO', NULL),
       (11, 'EMPRESA', 1);

INSERT INTO practica (id, alumno_id, empresa_id, tutor_empresa_id, tutor_centro_id, fecha_inicio, fecha_fin, estado)
VALUES (3, 15, 1, 11, 10, '2026-01-01', '2026-03-31', 'ACTIVA');

INSERT INTO seguimiento (id, practica_id, fecha, horas, descripcion, validado)
VALUES (2, 3, '2026-04-25', 5, 'tfg', 1);

INSERT INTO evaluacion (id, practica_id, tutor_id, nota, comentarios)
VALUES (2, 3, 11, 6, 'bien');