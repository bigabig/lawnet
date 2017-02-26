-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 24. Feb 2017 um 14:08
-- Server-Version: 10.1.21-MariaDB
-- PHP-Version: 7.1.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `webapp`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `metadaten`
--

CREATE TABLE `metadaten` (
  `aktenzeichen` varchar(20) NOT NULL,
  `datum` date NOT NULL,
  `typ` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `metadaten`
--

INSERT INTO `metadaten` (`aktenzeichen`, `datum`, `typ`) VALUES
('123456', '2017-02-01', 'Urteil'),
('123abc', '2017-02-01', 'Urteil'),
('321cba', '2017-02-04', 'Beschluss'),
('654321', '2017-02-01', 'Beschluss'),
('789aer', '2017-02-01', 'Urteil'),
('987zxc', '2017-02-05', 'Beschluss'),
('qwert43', '2017-02-01', 'Urteil'),
('trewq34', '2017-02-01', 'Beschluss');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `zitate`
--

CREATE TABLE `zitate` (
  `id` int(11) NOT NULL,
  `aktenzeichen1` varchar(30) NOT NULL,
  `aktenzeichen2` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `zitate`
--

INSERT INTO `zitate` (`id`, `aktenzeichen1`, `aktenzeichen2`) VALUES
(1, '123abc', '321cba'),
(3, '123abc', '123456'),
(4, '123abc', '654321'),
(5, '321cba', '789aer'),
(6, '789aer', '987zxc'),
(7, '123456', 'qwert43'),
(8, '123abc', 'qwert43'),
(9, '321cba', 'qwert43'),
(10, '987zxc', '123abc'),
(11, '654321', 'trewq34'),
(12, '987zxc', 'trewq34');

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `metadaten`
--
ALTER TABLE `metadaten`
  ADD PRIMARY KEY (`aktenzeichen`);

--
-- Indizes für die Tabelle `zitate`
--
ALTER TABLE `zitate`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_akt1` (`aktenzeichen1`),
  ADD KEY `fk_akt2` (`aktenzeichen2`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `zitate`
--
ALTER TABLE `zitate`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `zitate`
--
ALTER TABLE `zitate`
  ADD CONSTRAINT `fk_akt1` FOREIGN KEY (`aktenzeichen1`) REFERENCES `metadaten` (`aktenzeichen`),
  ADD CONSTRAINT `fk_akt2` FOREIGN KEY (`aktenzeichen2`) REFERENCES `metadaten` (`aktenzeichen`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
