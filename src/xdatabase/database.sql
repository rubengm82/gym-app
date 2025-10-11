-- phpMyAdmin SQL Dump
-- version 5.2.2
-- https://www.phpmyadmin.net/
--
-- Host: mysql-rubengmpineda.alwaysdata.net
-- Generation Time: Oct 11, 2025 at 10:48 AM
-- Server version: 10.11.14-MariaDB
-- PHP Version: 7.4.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `rubengmpineda_gym`
--

-- --------------------------------------------------------

--
-- Table structure for table `Administradores`
--

CREATE TABLE `Administradores` (
  `id_user` int(11) NOT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `rol` varchar(100) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `estado` int(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Administradores`
--

INSERT INTO `Administradores` (`id_user`, `mail`, `rol`, `password`, `estado`) VALUES
(1, 'nicolas@a.es', 'admin', 'admin', 1),
(2, 'admin', 'admin', 'admin', 1);

-- --------------------------------------------------------

--
-- Table structure for table `Clases`
--

CREATE TABLE `Clases` (
  `id_clase` int(11) NOT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `duracion` time DEFAULT NULL,
  `dia` varchar(20) DEFAULT NULL,
  `aforo` int(11) DEFAULT NULL,
  `fk_id_inst` int(11) DEFAULT NULL,
  `descripcion` text DEFAULT NULL,
  `estado` int(11) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Clases`
--

INSERT INTO `Clases` (`id_clase`, `nombre`, `duracion`, `dia`, `aforo`, `fk_id_inst`, `descripcion`, `estado`) VALUES
(1, 'Yoga', '01:00:00', 'LUNES', 20, 1, 'Clase de yoga para principiantes', 1),
(2, 'Crossfit', '00:45:00', 'MARTES', 15, 2, 'Entrenamiento de alta intensidad', 1),
(3, 'Pilates', '01:00:00', 'MIERCOLES', 18, 3, 'Pilates para fortalecer el core', 1),
(4, 'Zumba', '01:00:00', 'JUEVES', 25, 1, 'Baile y cardio divertido', 1),
(5, 'Spinning', '00:50:00', 'VIERNES', 20, 2, 'Bicicleta estática en grupo', 1),
(6, 'Boxeo', '01:00:00', 'LUNES', 20, 1, 'Entrenamiento de boxeo para principiantes', 1),
(7, 'HIIT', '00:45:00', 'MARTES', 15, 2, 'Sesión de alta intensidad', 1),
(8, 'Meditación', '01:15:00', 'MIERCOLES', 15, 3, 'Meditación para relajamiento', 1),
(9, 'Funcional', '01:00:00', 'JUEVES', 18, 1, 'Funcional para movimiento total corporal', 1),
(10, 'Cardio Dance', '01:00:00', 'VIERNES', 25, 2, 'Baile para cardio', 1),
(11, 'Judo', '01:00:00', 'SABADO', 20, 3, 'Clase de judo para principiantes', 0),
(12, 'Taekwondo', '01:15:00', 'DOMINGO', 15, 2, 'Taekwondo para todos los niveles', 0);

-- --------------------------------------------------------

--
-- Table structure for table `Clientes`
--

CREATE TABLE `Clientes` (
  `id_cliente` int(11) NOT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `apellido1` varchar(100) DEFAULT NULL,
  `apellido2` varchar(100) DEFAULT NULL,
  `IBAN` varchar(30) DEFAULT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `telefono` varchar(15) DEFAULT NULL,
  `estado` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Clientes`
--

INSERT INTO `Clientes` (`id_cliente`, `nombre`, `apellido1`, `apellido2`, `IBAN`, `mail`, `telefono`, `estado`) VALUES
(24, 'Juan', 'Pérez', 'López', 'ES7620770024003102575766', 'juan.perez@email.com', '600111222', 1),
(25, 'Ana', 'García', 'Fernández', 'ES9121000418450200051332', 'ana.garcia@email.com', '600333444', 0),
(26, 'Carlos', 'Martínez', 'Gómez', 'ES4421000418450200051332', 'carlos.martinez@email.com', '600555666', 1),
(27, 'Laura', 'Sánchez', 'Ruiz', 'ES8121000418450200051332', 'laura.sanchez@email.com', '600777888', 1),
(28, 'Pedro', 'Díaz', 'Morales', 'ES9921000418450200051332', 'pedro.diaz@email.com', '600999000', 1),
(29, 'Marta', 'Romero', 'Navarro', 'ES7621000418450200051333', 'marta.romero@email.com', '601111222', 0),
(30, 'Luis', 'Torres', 'Vega', 'ES9121000418450200051334', 'luis.torres@email.com', '601333444', 1),
(31, 'Sofía', 'Ramírez', 'Cruz', 'ES4421000418450200051335', 'sofia.ramirez@email.com', '601555666', 1),
(32, 'Diego', 'Hernández', 'Ortiz', 'ES8121000418450200051336', 'diego.hernandez@email.com', '601777888', 0),
(33, 'Lucía', 'Moreno', 'Iglesias', 'ES9921000418450200051337', 'lucia.moreno@email.com', '601999000', 1),
(35, 'Alberto', 'Santos', 'Pérez', 'ES7621000418450200051338', 'alberto.santos@email.com', '602111222', 1),
(36, 'Isabel', 'Vargas', 'López', 'ES9121000418450200051339', 'isabel.vargas@email.com', '602333444', 1),
(37, 'Fernando', 'Castillo', 'Ramírez', 'ES4421000418450200051340', 'fernando.castillo@email.com', '602555666', 1),
(38, 'Elena', 'Ríos', 'Gómez', 'ES8121000418450200051341', 'elena.rios@email.com', '602777888', 1),
(39, 'Ricardo', 'Molina', 'Fernández', 'ES9921000418450200051342', 'ricardo.molina@email.com', '602999000', 1),
(40, 'Paula', 'Alonso', 'Sánchez', 'ES7621000418450200051343', 'paula.alonso@email.com', '603111222', 1),
(41, 'Sergio', 'Navarro', 'Díaz', 'ES9121000418450200051344', 'sergio.navarro@email.com', '603333444', 1),
(42, 'Marina', 'Cruz', 'Morales', 'ES4421000418450200051345', 'marina.cruz@email.com', '603555666', 1),
(43, 'Javier', 'Herrera', 'Romero', 'ES8121000418450200051346', 'javier.herrera@email.com', '603777888', 1),
(44, 'Natalia', 'Ortiz', 'Vega', 'ES9921000418450200051347', 'natalia.ortiz@email.com', '603999000', 1),
(45, 'David', 'Gómez', 'Cruz', 'ES7621000418450200051348', 'david.gomez@email.com', '604111222', 1),
(46, 'Cristina', 'Pardo', 'Fernández', 'ES9121000418450200051349', 'cristina.pardo@email.com', '604333444', 1),
(47, 'Manuel', 'Gil', 'Ruiz', 'ES4421000418450200051350', 'manuel.gil@email.com', '604555666', 1),
(48, 'Verónica', 'Medina', 'Moreno', 'ES8121000418450200051351', 'veronica.medina@email.com', '604777888', 1),
(49, 'Pablo', 'Serrano', 'Iglesias', 'ES9921000418450200051352', 'pablo.serrano@email.com', '604999000', 1),
(50, 'Laura', 'Marín', 'Ramos', 'ES7621000418450200051353', 'laura.marin@email.com', '605111222', 1),
(51, 'José', 'Domínguez', 'López', 'ES9121000418450200051354', 'jose.dominguez@email.com', '605333444', 1),
(52, 'Andrea', 'Cabrera', 'Martínez', 'ES4421000418450200051355', 'andrea.cabrera@email.com', '605555666', 1),
(53, 'Raúl', 'Santos', 'Pérez', 'ES8121000418450200051356', 'raul.santos@email.com', '605777888', 0),
(54, 'Clara', 'Méndez', 'Vega', 'ES9921000418450200051357', 'clara.mendez@email.com', '605999000', 1),
(55, 'Antonio', 'Fuentes', 'Navarro', 'ES7621000418450200051358', 'antonio.fuentes@email.com', '606111222', 1),
(56, 'Patricia', 'Román', 'Hernández', 'ES9121000418450200051359', 'patricia.roman@email.com', '606333444', 1),
(57, 'Miguel', 'Castro', 'Ortiz', 'ES4421000418450200051360', 'miguel.castro@email.com', '606555666', 1),
(58, 'Sonia', 'Vega', 'Ramos', 'ES8121000418450200051361', 'sonia.vega@email.com', '606777888', 1),
(59, 'Adrián', 'Blanco', 'Gil', 'ES9921000418450200051362', 'adrian.blanco@email.com', '606999000', 1),
(60, 'Raquel', 'Mora', 'Serrano', 'ES7621000418450200051363', 'raquel.mora@email.com', '607111222', 1),
(61, 'Héctor', 'Campos', 'Pardo', 'ES9121000418450200051364', 'hector.campos@email.com', '607333444', 1),
(62, 'Natalie', 'Vidal', 'Gómez', 'ES4421000418450200051365', 'natalie.vidal@email.com', '607555666', 1),
(63, 'Iván', 'Luna', 'Morales', 'ES8121000418450200051366', 'ivan.luna@email.com', '607777888', 0),
(64, 'Sandra', 'Ramos', 'Cruz', 'ES9921000418450200051367', 'sandra.ramos@email.com', '607999000', 1),
(65, 'Diego', 'Reyes', 'Molina', 'ES7621000418450200051368', 'diego.reyes@email.com', '608111222', 1),
(66, 'Mónica', 'Pérez', 'Sánchez', 'ES9121000418450200051369', 'monica.perez@email.com', '608333444', 1),
(67, 'Francisco', 'Ortiz', 'López', 'ES4421000418450200051370', 'francisco.ortiz@email.com', '608555666', 1),
(68, 'Lorena', 'Vega', 'Fernández', 'ES8121000418450200051371', 'lorena.vega@email.com', '608777888', 1),
(69, 'Alfonso', 'Cano', 'Navarro', 'ES9921000418450200051372', 'alfonso.cano@email.com', '608999000', 1),
(70, 'Julia', 'Guerrero', 'Gómez', 'ES7621000418450200051373', 'julia.guerrero@email.com', '609111222', 1),
(71, 'Raúl', 'Pérez', 'Ruiz', 'ES9121000418450200051374', 'raul.perez@email.com', '609333444', 1),
(72, 'Teresa', 'Santos', 'López', 'ES4421000418450200051375', 'teresa.santos@email.com', '609555666', 1),
(73, 'Gonzalo', 'Vargas', 'Fernández', 'ES8121000418450200051376', 'gonzalo.vargas@email.com', '609777888', 0),
(74, 'Inés', 'Martínez', 'Romero', 'ES9921000418450200051377', 'ines.martinez@email.com', '609999000', 1),
(75, 'Mario', 'López', 'Morales', 'ES7621000418450200051378', 'mario.lopez@email.com', '610111222', 1),
(76, 'Cecilia', 'Fernández', 'Navarro', 'ES9121000418450200051379', 'cecilia.fernandez@email.com', '610333444', 1),
(77, 'Tomás', 'Hernández', 'Cruz', 'ES4421000418450200051380', 'tomas.hernandez@email.com', '610555666', 1),
(78, 'Olga', 'Sánchez', 'Vega', 'ES8121000418450200051381', 'olga.sanchez@email.com', '610777888', 1),
(79, 'Iván', 'Moreno', 'Ramírez', 'ES9921000418450200051382', 'ivan.moreno@email.com', '610999000', 1),
(80, 'Claudia', 'Romero', 'Gómez', 'ES7621000418450200051383', 'claudia.romero@email.com', '611111222', 1),
(81, 'Jorge', 'Torres', 'López', 'ES9121000418450200051384', 'jorge.torres@email.com', '611333444', 1),
(82, 'Verónica', 'Molina', 'Fernández', 'ES4421000418450200051385', 'veronica.molina@email.com', '611555666', 1),
(83, 'Andrés', 'González', 'Navarro', 'ES8121000418450200051386', 'andres.gonzalez@email.com', '611777888', 0),
(84, 'Natalia', 'López', 'Ramírez', 'ES9921000418450200051387', 'natalia.lopez@email.com', '611999000', 1),
(85, 'Raúl', 'Hernández', 'Serrano', 'ES7621000418450200051388', 'raul.hernandez@email.com', '612111222', 1),
(86, 'Sandra', 'Gómez', 'Pérez', 'ES9121000418450200051389', 'sandra.gomez@email.com', '612333444', 1),
(87, 'Luis', 'Vega', 'Martínez', 'ES4421000418450200051390', 'luis.vega@email.com', '612555666', 1),
(88, 'Carla', 'Navarro', 'Ruiz', 'ES8121000418450200051391', 'carla.navarro@email.com', '612777888', 1),
(89, 'Diego', 'Cabrera', 'Gómez', 'ES9921000418450200051392', 'diego.cabrera@email.com', '612999000', 1);

-- --------------------------------------------------------

--
-- Table structure for table `Instructores`
--

CREATE TABLE `Instructores` (
  `id_inst` int(11) NOT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `apellido1` varchar(100) DEFAULT NULL,
  `apellido2` varchar(100) DEFAULT NULL,
  `telefono` varchar(15) DEFAULT NULL,
  `DNI` varchar(20) DEFAULT NULL,
  `estado` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Instructores`
--

INSERT INTO `Instructores` (`id_inst`, `nombre`, `apellido1`, `apellido2`, `telefono`, `DNI`, `estado`) VALUES
(1, 'Ana', 'Pérez', 'García', '612345678', '12345678A', '1'),
(2, 'Carlos', 'Díaz', 'López', '698765432', '87654321B', '1'),
(3, 'Lucía', 'Martínez', 'Hernández', '600123456', '23456789C', '1'),
(4, 'Javier', 'Gómez', 'Santos', '601234567', '34567890D', '1'),
(5, 'María', 'Ruiz', 'Fernández', '602345678', '45678901E', '1'),
(6, 'David', 'Sánchez', 'Moreno', '603456789', '56789012F', '1'),
(7, 'Laura', 'Torres', 'Vega', '604567890', '67890123G', '0'),
(8, 'Pablo', 'Ramírez', 'Castillo', '605678901', '78901234H', '0'),
(9, 'Sara', 'Jiménez', 'Blanco', '606789012', '89012345I', '1'),
(10, 'Miguel', 'Cruz', 'Ortiz', '607890123', '90123456J', '1');

-- --------------------------------------------------------

--
-- Table structure for table `Reservas`
--

CREATE TABLE `Reservas` (
  `id_reserva` int(11) NOT NULL,
  `fk_id_clase` int(11) DEFAULT NULL,
  `fk_id_cliente` int(11) DEFAULT NULL,
  `fecha` timestamp NULL DEFAULT current_timestamp(),
  `estado` int(11) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Administradores`
--
ALTER TABLE `Administradores`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `mail` (`mail`);

--
-- Indexes for table `Clases`
--
ALTER TABLE `Clases`
  ADD PRIMARY KEY (`id_clase`),
  ADD KEY `fk_id_inst` (`fk_id_inst`);

--
-- Indexes for table `Clientes`
--
ALTER TABLE `Clientes`
  ADD PRIMARY KEY (`id_cliente`),
  ADD UNIQUE KEY `mail` (`mail`);

--
-- Indexes for table `Instructores`
--
ALTER TABLE `Instructores`
  ADD PRIMARY KEY (`id_inst`),
  ADD UNIQUE KEY `DNI` (`DNI`);

--
-- Indexes for table `Reservas`
--
ALTER TABLE `Reservas`
  ADD PRIMARY KEY (`id_reserva`),
  ADD KEY `fk_id_clase` (`fk_id_clase`),
  ADD KEY `fk_id_cliente` (`fk_id_cliente`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Administradores`
--
ALTER TABLE `Administradores`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `Clases`
--
ALTER TABLE `Clases`
  MODIFY `id_clase` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `Clientes`
--
ALTER TABLE `Clientes`
  MODIFY `id_cliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=91;

--
-- AUTO_INCREMENT for table `Instructores`
--
ALTER TABLE `Instructores`
  MODIFY `id_inst` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `Reservas`
--
ALTER TABLE `Reservas`
  MODIFY `id_reserva` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Clases`
--
ALTER TABLE `Clases`
  ADD CONSTRAINT `Clases_ibfk_1` FOREIGN KEY (`fk_id_inst`) REFERENCES `Instructores` (`id_inst`) ON DELETE SET NULL;

--
-- Constraints for table `Reservas`
--
ALTER TABLE `Reservas`
  ADD CONSTRAINT `Reservas_ibfk_1` FOREIGN KEY (`fk_id_clase`) REFERENCES `Clases` (`id_clase`) ON DELETE CASCADE,
  ADD CONSTRAINT `Reservas_ibfk_2` FOREIGN KEY (`fk_id_cliente`) REFERENCES `Clientes` (`id_cliente`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
