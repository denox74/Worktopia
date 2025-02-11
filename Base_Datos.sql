CREATE TABLE Clientes (
    id_cliente INT AUTO_INCREMENT ,
    nombre VARCHAR(100) NOT NULL,
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
