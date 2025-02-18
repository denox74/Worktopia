CREATE TABLE Clientes (
    id_cliente INT AUTO_INCREMENT ,
    dni VARCHAR(10) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    primerApellido VARCHAR(30) NOT NULL,
    segundoApellido VARCHAR(30),
    email VARCHAR(100) NOT NULL UNIQUE,
    telefono VARCHAR(15),
    CONSTRAINT pk_id_cliente PRIMARY KEY (id_cliente)
);

CREATE TABLE Espacios (
    id_espacio INT AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    CONSTRAINT pk_id_espacio PRIMARY KEY (id_espacio)
);

CREATE TABLE Asientos (
    id_asiento INT AUTO_INCREMENT,
    estado ENUM('libre', 'ocupado', 'no disponible') NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    tarifa_hora DECIMAL(10, 2) NOT NULL,
    id_espacio INT,
    CONSTRAINT pk_id_asiento PRIMARY KEY (id_asiento),
    CONSTRAINT fk_id_espacio FOREIGN KEY (id_espacio) REFERENCES Espacios(id_espacio)
);
DROP TABLE IF EXISTS Reservas;
CREATE TABLE Reservas (
    id_reserva INT AUTO_INCREMENT,
    id_cliente INT,
    id_asiento INT,
    fecha_hora_inicio DATETIME NOT NULL,
    fecha_hora_fin DATETIME NOT NULL,
    CONSTRAINT pk_id_reserva PRIMARY KEY (id_reserva),
    CONSTRAINT fk_id_cliente_reserva FOREIGN KEY (id_cliente) REFERENCES Clientes(id_cliente),
    CONSTRAINT fk_id_asiento_reserva FOREIGN KEY (id_asiento) REFERENCES Asientos(id_asiento)
);

DROP TABLE IF EXISTS Facturas;
CREATE TABLE Facturas (
    id_factura INT AUTO_INCREMENT,
    precio_total DECIMAL(10, 2) NOT NULL,
    tiene_descuento BOOLEAN DEFAULT FALSE,
    CONSTRAINT pk_id_factura PRIMARY KEY (id_factura)
);

 DROP TABLE IF EXISTS Facturas;
 CREATE TABLE Facturas (
    id_factura INT AUTO_INCREMENT,
    precio_total DECIMAL(10, 2) NOT NULL,
    tiene_descuento BOOLEAN DEFAULT FALSE,
    fecha_hora_emision DATETIME NOT NULL,
    estado ENUM('Pendiente', 'Pagada') NOT NULL,
    fecha_pago DATETIME,
    CONSTRAINT pk_id_factura PRIMARY KEY (id_factura)
);


DROP TABLE IF EXISTS Factura_Reservas;
CREATE TABLE Factura_Reservas (
    id_factura INT,
    id_reserva INT,
    CONSTRAINT pk_factura_reserva PRIMARY KEY (id_factura, id_reserva),
    CONSTRAINT fk_factura FOREIGN KEY (id_factura) REFERENCES Facturas(id_factura) ON DELETE CASCADE,
    CONSTRAINT fk_reserva FOREIGN KEY (id_reserva) REFERENCES Reservas(id_reserva) ON DELETE CASCADE
);

CREATE TABLE Usuarios (
    id_usuario INT AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    contrasenia VARCHAR(255) NOT NULL,
    categoria ENUM('Admin', 'Empleado') NOT NULL,
    CONSTRAINT pk_id_usuario PRIMARY KEY (id_usuario)
);


DELETE FROM Clientes;
ALTER TABLE Clientes AUTO_INCREMENT = 1;

INSERT INTO Clientes (dni, nombre, primerApellido, segundoApellido, email, telefono) 
VALUES ("12345678A", "Agustín", "Perez", "Perez", "aguseltin@gmail.com", "646053394"),
    ("23456789B", "Beatriz", "Gomez", "Lopez", "beatrizgomez@hotmail.com", "654789321"),
    ("34567890C", "Carlos", "Martinez", "Ruiz", "carlosmruiz@gmail.com", "612345678"),
    ("45678901D", "Diana", "Fernandez", "Soto", "dianafernandez@yahoo.com", "678123456"),
    ("56789012E", "Eduardo", "Ramirez", "Torres", "eduardoramirez@gmail.es", "698765432"),
    ("67890123F", "Fernando", "Lopez", "Garcia", "fernandolopez@gmail.com", "612398765"),
    ("78901234G", "Gabriela", "Sanchez", "Diaz", "gabrielasanchez@gmail.es", "634567890"),
    ("89012345H", "Hector", "Jimenez", "Ortiz", "hectorjimenez@outlook.com", "678901234"),
    ("90123456I", "Isabel", "Torres", "Martinez", "isabeltorres@gmail.com", "612345987"),
    ("01234567J", "Javier", "Ruiz", "Hernandez", "javierruiz@outlook.com", "698765123"),
    ("12345678K", "Karen", "Morales", "Perez", "karenmorales@outlook.com", "612398456"),
    ("23456789L", "Luis", "Fernandez", "Gomez", "luisfernandez@gmail.com", "634567123"),
    ("34567890M", "Maria", "Gonzalez", "Lopez", "mariagonzalez@gmail.com", "678901567"),
    ("45678901N", "Nicolas", "Ramirez", "Torres", "nicolasramirez@gmail.com", "612345654"),
    ("56789012O", "Olga", "Martinez", "Ruiz", "olgamartinez@outlook.es", "698765890"),
    ("67890123P", "Pablo", "Sanchez", "Diaz", "pablosanchez@outlook.es", "612398321"),
    ("78901234Q", "Quintin", "Jimenez", "Ortiz", "quintinjimenez@gmail.com", "634567654"),
    ("89012345R", "Rosa", "Torres", "Martinez", "rosatorres@outlook.es", "678901890"),
    ("90123456S", "Sergio", "Ruiz", "Hernandez", "sergioruiz@outlook.es", "612345321"),
    ("01234567T", "Teresa", "Morales", "Perez", "teresamorales@yahoo.com", "698765567"),
    ("12345678U", "Ulises", "Fernandez", "Gomez", "ulisesfernandez@gmail.es", "612398789"),
    ("23456789V", "Valeria", "Gonzalez", "Lopez", "valeriagonzalez@outlook.com", "634567321"),
    ("34567890W", "Walter", "Ramirez", "Torres", "walterramirez@goutlook.com", "678901654"),
    ("45678901X", "Ximena", "Martinez", "Ruiz", "ximenamartinez@outlook.com", "612345890"),
    ("56789012Y", "Yolanda", "Sanchez", "Diaz", "yolandasanchez@outlook.com", "698765321");

INSERT INTO Usuarios (nombre, email, contrasenia, categoria)
VALUES ("Eliu", "eliuadmin@worktopia.com", "Eliu.123E", "Admin"),
    ("David", "davidadmin@worktopia.com", "David.123D", "Admin"),
    ("Luis", "luisppps123@hotmail.com", "Luis.123L", "Empleado"),
    ("Sofia", "lsofi@yahoo.es", "Sofia.123S", "Empleado");



INSERT INTO Espacios (nombre)
VALUES ("Oficina IESRincón");


DELETE FROM Asientos;
ALTER TABLE Asientos AUTO_INCREMENT = 1;
INSERT INTO Asientos (estado, nombre, tarifa_hora, id_espacio)
VALUES
("libre", "A1", 5.00, 1),
("libre", "A2", 5.00, 1),
("libre", "A3", 5.00, 1),
("libre", "A4", 5.00, 1),
("libre", "A5", 5.00, 1),
("libre", "A6", 5.00, 1),
("libre", "A7", 5.00, 1),
("libre", "A8", 5.00, 1),
("libre", "A9", 5.00, 1),
("libre", "A10", 5.00, 1),
("libre", "A11", 5.00, 1),
("libre", "A12", 5.00, 1),
("libre", "A13", 5.00, 1),
("libre", "A14", 5.00, 1),
("libre", "A15", 5.00, 1),
("libre", "A16", 5.00, 1),
("libre", "A17", 5.00, 1),
("libre", "A18", 5.00, 1),
("libre", "Oficina 1", 10.00, 1),
("libre", "Oficina 2", 10.00, 1),
("libre", "Sala de Conferencias 1", 20.00, 1),
("libre", "Sala de Conferencias 2", 15.00, 1);

DELETE FROM Reservas;
ALTER TABLE Reservas AUTO_INCREMENT = 1;
INSERT INTO Reservas (id_cliente, id_asiento, fecha_hora_inicio, fecha_hora_fin)
VALUES
(1, 1, "2025-01-01 09:00:00", "2025-01-01 12:00:00"),
(1, 1, "2025-01-02 09:00:00", "2025-01-02 12:00:00"),
(1, 1, "2025-01-03 09:00:00", "2025-01-03 12:00:00"),
(1, 4, "2025-01-04 09:00:00", "2025-01-04 12:00:00"),
(1, 1, "2025-01-05 09:00:00", "2025-01-05 12:00:00"),
(2, 3, "2025-02-01 10:00:00", "2025-02-01 15:00:00"),
(2, 3, "2025-02-02 10:00:00", "2025-02-02 15:00:00"),
(2, 8, "2025-02-03 10:00:00", "2025-02-03 15:00:00"),
(2, 4, "2025-02-04 10:00:00", "2025-02-04 15:00:00"),
(2, 10, "2025-02-05 10:00:00", "2025-02-05 15:00:00");


DELETE FROM Facturas;
ALTER TABLE Facturas AUTO_INCREMENT = 1;
INSERT INTO Facturas (precio_total, tiene_descuento)
VALUES
(0.00, FALSE),
(0.00, FALSE);

INSERT INTO Factura_Reservas (id_factura, id_reserva)
VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(2, 6),
(2, 7),
(2, 8),
(2, 9),
(2, 10);
