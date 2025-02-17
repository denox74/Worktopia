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
    tarifa_hora DECIMAL(10, 2) NOT NULL,
    id_espacio INT,
    CONSTRAINT pk_id_asiento PRIMARY KEY (id_asiento),
    CONSTRAINT fk_id_espacio FOREIGN KEY (id_espacio) REFERENCES Espacios(id_espacio)
);

CREATE TABLE Reservas (
    id_reserva INT AUTO_INCREMENT,
    id_cliente INT,
    fecha_hora_inicio DATETIME NOT NULL,
    fecha_hora_fin DATETIME NOT NULL,
    CONSTRAINT pk_id_reserva PRIMARY KEY (id_reserva),
    CONSTRAINT fk_id_cliente FOREIGN KEY (id_cliente) REFERENCES Clientes(id_cliente)
);

CREATE TABLE Reserva_Asientos (
    id_reserva INT,
    id_asiento INT,
    CONSTRAINT pk_reserva_asiento PRIMARY KEY (id_reserva, id_asiento),
    CONSTRAINT fk_reserva FOREIGN KEY (id_reserva) REFERENCES Reservas(id_reserva),
    CONSTRAINT fk_asiento FOREIGN KEY (id_asiento) REFERENCES Asientos(id_asiento)
);

CREATE TABLE Facturas (
    id_factura INT AUTO_INCREMENT,
    id_reserva INT,
    precio_total DECIMAL(10, 2) NOT NULL,
    tiene_descuento BOOLEAN DEFAULT FALSE,
    CONSTRAINT pk_id_factura PRIMARY KEY (id_factura),
    CONSTRAINT fk_id_reserva FOREIGN KEY (id_reserva) REFERENCES Reservas(id_reserva)
);

CREATE TABLE Usuarios (
    id_usuario INT AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    contrasenia VARCHAR(255) NOT NULL,
    categoria ENUM('Admin', 'Empleado') NOT NULL,
    CONSTRAINT pk_id_usuario PRIMARY KEY (id_usuario)
)


    -- Cambio el orden de las columnas
    ALTER TABLE Clientes
    MODIFY COLUMN id_cliente INT AUTO_INCREMENT FIRST,
    MODIFY COLUMN dni VARCHAR(10) NOT NULL AFTER id_cliente,
    MODIFY COLUMN nombre VARCHAR(100) NOT NULL AFTER dni,
    MODIFY COLUMN primerApellido VARCHAR(30) NOT NULL AFTER nombre,
    MODIFY COLUMN segundoApellido VARCHAR(30) AFTER primerApellido,
    MODIFY COLUMN email VARCHAR(100) NOT NULL AFTER segundoApellido,
    MODIFY COLUMN telefono VARCHAR(15) AFTER email,
    MODIFY COLUMN contrasenia VARCHAR(255) NOT NULL AFTER telefono,
    MODIFY COLUMN frecuente BOOLEAN DEFAULT FALSE AFTER contrasenia,
    MODIFY COLUMN descuento DECIMAL(5, 2) DEFAULT 0.00 AFTER frecuente;



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


    -- Alterame la tabla de clientes para que no tenga contrasenia, frecuente ni descuento
    ALTER TABLE Clientes
    DROP COLUMN contrasenia,
    DROP COLUMN frecuente,
    DROP COLUMN descuento;





-- insertar datos en la tabla asientos, crea 18 asientos
INSERT INTO Asientos (estado, tarifa_hora, id_espacio)
VALUES
('libre', 10.00, 1),
('libre', 10.00, 1),
('libre', 10.00, 1),
('libre', 10.00, 1),
('libre', 10.00, 1),
('libre', 10.00, 1),
('libre', 10.00, 1),
('libre', 10.00, 1),
('libre', 10.00, 1),
('libre', 15.00, 2),
('libre', 15.00, 2),
('libre', 15.00, 2),
('libre', 15.00, 2),
('libre', 15.00, 2),
('libre', 15.00, 2),
('libre', 15.00, 2),
('libre', 15.00, 2),
('libre', 15.00, 2);