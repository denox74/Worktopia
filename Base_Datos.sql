CREATE TABLE Clientes (
    id_cliente INT AUTO_INCREMENT ,
    dni VARCHAR(10) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    primerApellido VARCHAR(30) NOT NULL,
    segundoApellido VARCHAR(30),
    email VARCHAR(100) NOT NULL UNIQUE,
    telefono VARCHAR(15),
    contrasenia VARCHAR(255) NOT NULL,
    frecuente BOOLEAN DEFAULT FALSE,
    descuento DECIMAL(5, 2) DEFAULT 0.00,
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


INSERT INTO Clientes (nombre, email, telefono, contrasenia, frecuente, descuento) 
VALUES ("Agustín Perez Perez", "aguseltin@gmail.com", "646053394", "Kps.129K", FALSE, 0.00),
    ("Beatriz Gomez Lopez", "beatrizgomez@hotmail.com", "654789321", "B123.ASFgt.456L", TRUE, 5.00),
    ("Carlos Martinez Ruiz", "carlosmruiz@gmail.com", "612345678", "Crm.789M", FALSE, 0.00),
    ("Diana Fernandez Soto", "dianafernandez@yahoo.com", "678123456", "Dfs.321D", TRUE, 10.00),
    ("Eduardo Ramirez Torres", "eduardoramirez@gmail.es", "698765432", "Ert.654E", FALSE, 0.00),
    ("Fernando Lopez Garcia", "fernandolopez@gmail.com", "612398765", "FlG.987F", TRUE, 7.50),
    ("Gabriela Sanchez Diaz", "gabrielasanchez@gmail.es", "634567890", "Gsd.123G", FALSE, 0.00),
    ("Hector Jimenez Ortiz", "hectorjimenez@outlook.com", "678901234", "Hjo.456H", TRUE, 5.00),
    ("Isabel Torres Martinez", "isabeltorres@gmail.com", "612345987", "Itm.789I", FALSE, 0.00),
    ("Javier Ruiz Hernandez", "javierruiz@outlook.com", "698765123", "Jrh.321J", TRUE, 10.00),
    ("Karen Morales Perez", "karenmorales@outlook.com", "612398456", "Kmp.654K", FALSE, 0.00),
    ("Luis Fernandez Gomez", "luisfernandez@gmail.com", "634567123", "Lfg.987L", TRUE, 7.50),
    ("Maria Gonzalez Lopez", "mariagonzalez@gmail.com", "678901567", "Mgl.123M", FALSE, 0.00),
    ("Nicolas Ramirez Torres", "nicolasramirez@gmail.com", "612345654", "Nrt.456N", TRUE, 5.00),
    ("Olga Martinez Ruiz", "olgamartinez@outlook.es", "698765890", "Omr.789O", FALSE, 0.00),
    ("Pablo Sanchez Diaz", "pablosanchez@outlook.es", "612398321", "Psd.321P", TRUE, 10.00),
    ("Quintin Jimenez Ortiz", "quintinjimenez@gmail.com", "634567654", "Qjo.654Q", FALSE, 0.00),
    ("Rosa Torres Martinez", "rosatorres@outlook.es", "678901890", "Rtm.987R", TRUE, 7.50),
    ("Sergio Ruiz Hernandez", "sergioruiz@outlook.es", "612345321", "Srg.123S", FALSE, 0.00),
    ("Teresa Morales Perez", "teresamorales@yahoo.com", "698765567", "Tmp.456T", TRUE, 5.00),
    ("Ulises Fernandez Gomez", "ulisesfernandez@gmail.es", "612398789", "Ufg.789U", FALSE, 0.00),
    ("Valeria Gonzalez Lopez", "valeriagonzalez@outlook.com", "634567321", "Vgl.321V", TRUE, 10.00),
    ("Walter Ramirez Torres", "walterramirez@goutlook.com", "678901654", "Wrt.654W", FALSE, 0.00),
    ("Ximena Martinez Ruiz", "ximenamartinez@outlook.com", "612345890", "Xmr.987X", TRUE, 7.50),
    ("Yolanda Sanchez Diaz", "yolandasanchez@outlook.com", "698765321", "Ysd.123Y", FALSE, 0.00);

INSERT INTO Usuarios (nombre, email, contrasenia, categoria)
VALUES ("Eliu", "eliuadmin@worktopia.com", "Eliu.123E", "Admin"),
    ("David", "davidadmin@worktopia.com", "David.123D", "Admin"),
    ("Luis", "luisppps123@hotmail.com", "Luis.123L", "Empleado"),
    ("Sofia", "lsofi@yahoo.es", "Sofia.123S", "Empleado");