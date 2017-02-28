-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 28. Feb 2017 um 15:59
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
-- Tabellenstruktur für Tabelle `dokumente`
--

CREATE TABLE `dokumente` (
  `aktenzeichen` varchar(40) NOT NULL,
  `dateiname` varchar(30) NOT NULL,
  `text` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `dokumente`
--

INSERT INTO `dokumente` (`aktenzeichen`, `dateiname`, `text`) VALUES
('123456', 'test13', 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.'),
('123abc', 'test12', 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.'),
('321cba', 'test11', 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.'),
('654321', 'test10', 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.'),
('789aer', 'test9', 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.'),
('987zxc', 'test8', 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.'),
('ECLI:DE:BGH:2016:050416B3STR10.16.0', '74726_clean.txt', 'ECLI:DE:BGH:2016:050416B3STR10.16.0 BUNDESGERICHTSHOF BESCHLUSS 3 StR 10/16 vom 5-4-2016 in der Strafsache gegen wegen gewerbsmäßigen Bandenbetrugs u_a_ \nDer 3_ Strafsenat des Bundesgerichtshofs hat nach Anhörung des Beschwerdeführers und des Generalbundesanwalts - zu 2_ auf dessen Antrag - am 5-4-2016 gemäß § 349 Abs_ 2 und 4, § 354 Abs_ 1 analog StPO einstimmig beschlossen:\n1. Auf die Revision des Angeklagten wird das Urteil des Landgerichts Oldenburg vom 8-7-2015 dahin geändert, dass der Angeklagte wegen Beihilfe zum gewerbsmäßigen Bandenbetrug in 26 Fällen sowie wegen Beihilfe zum versuchten gewerbsmäßigen Bandenbetrug in vier Fällen, jeweils in Tateinheit mit gewerbsmäßiger Bandenurkundenfälschung, zu einer Gesamtfreiheitsstrafe von zwei Jahren und neun Monaten verurteilt wird.\n2. Die weitergehende Revision wird verworfen.\n3. Der Beschwerdeführer hat die Kosten seines Rechtsmittels zu tragen.\nGründe:\nDas Landgericht hat den Angeklagten wegen Beihilfe zum gewerbsmäßigen Bandenbetrug in 28 Fällen und wegen Beihilfe zum versuchten gewerbsmäßigen Bandenbetrug in sechs Fällen, jeweils in Tateinheit mit Beihilfe zur gewerbsmäßigen Bandenurkundenfälschung, zu einer Gesamtfreiheitsstrafe von zwei Jahren und neun Monaten verurteilt. Die auf die allgemeine 1\n\nSachrüge gestützte Revision des Angeklagten hat den aus der Beschlussformel ersichtlichen Teilerfolg; im Übrigen ist das Rechtsmittel unbegründet im Sinne des § 349 Abs_ 2 StPO.\n1. Der Schuldspruch wegen 34 selbständiger Beihilfehandlungen des Angeklagten hält rechtlicher Überprüfung nicht stand.\nDie Feststellungen tragen zwar die Verurteilung des Angeklagten wegen Beihilfe zum vollendeten bzw_ versuchten gewerbsmäßigen Bandenbetrug in Tateinheit mit Beihilfe zur gewerbsmäßigen Bandenurkundenfälschung in allen der Verurteilung zugrunde liegenden Fällen, jedoch hat das Landgericht die Konkurrenzen nicht zutreffend beurteilt. Da sich der Tatbeitrag des Angeklagten darin erschöpfte, die von den weiteren Tatbeteiligten S_ und W_ verwendeten falschen Personalausweise mit einer Prägung zu versehen, die unter den falschen Personalien \"K. \", \"H. \", \"Ho. \" und \"We. \"\nhergestellten Ausweise indes jeweils zweimal eingesetzt wurden, hat der Angeklagte nicht 34, sondern nur 30 selbständige Beihilfehandlungen im Sinne von § 53 Abs_ 1 StGB vorgenommen. Durch seine Mitwirkung bei der Herstellung der auf die Namen \"K. \", \"H. \", \"Ho. \" und \"We. \"\nausgestellten Ausweise hat er freilich jeweils zu zwei Haupttaten Hilfe geleistet,\nwobei es in zwei Fällen nur beim versuchten Betrug blieb; insoweit liegt jeweils gleichartige Tateinheit (§ 52 Abs_ 1 Alt_ 2 StGB) vor. Der Senat hat den Schuldspruch entsprechend geändert, wobei er davon abgesehen hat, die Fälle gleichartiger Tateinheit im Tenor zum Ausdruck zu bringen, weil dieser dadurch unübersichtlich würde (vgl_ dazu BGH, Urteile vom 27-6-1996 - 4 StR 3/96,\nNStZ 1996, 610, 611; vom 6-6-2007 - 5 StR 127/07, NJW 2007, 2864,\n2867).\n2 3\n\n2. Die Änderung des Schuldspruchs hat zur Folge, dass vier der 34 vom Landgericht festgesetzten Einzelstrafen, die die Strafkammer jeweils mit einem Jahr Freiheitsstrafe bemessen hat, entfallen. Die Gesamtstrafe bleibt davon indes unberührt. Denn der Gesamtunrechts- und -schuldgehalt verringert sich durch die geänderte Beurteilung der Konkurrenzverhältnisse nicht. Der Senat schließt deshalb angesichts der verbleibenden Einzelstrafen aus, dass das Landgericht bei zutreffender Bewertung der Konkurrenzen auf eine niedrigere Gesamtstrafe erkannt hätte (§ 354 Abs_ 1 analog StPO).\n3. Der geringfügige Erfolg des Rechtsmittels gebietet es nicht, den Angeklagten aus Billigkeitsgründen auch nur teilweise von der Belastung mit Kosten und notwendigen Auslagen freizustellen (§ 473 Abs_ 4 StPO).\nBecker Schäfer RiBGH Mayer ist erkrankt und daher gehindert zu unterschreiben.\nBecker Gericke Tiemann 4\n5\n'),
('ECLI:DE:BGH:2016:061016BIZB48.16.0', 'i_zb__48-16 (6.10.2016).pdf', 'BUNDESGERICHTSHOF I ZB 48/16 BESCHLUSS vom 6-10-2016 in dem Zwangsvollstreckungsverfahren ECLI:DE:BGH:2016:061016BIZB48.16.0 Der I_ Zivilsenat des Bundesgerichtshofs hat am 6-10-2016 durch den Vorsitzenden Richter Professor Doktor Büscher, die Richter Professor Doktor Koch, Doktor Löffler, die Richterin Doktor Schwonke und den Richter Feddersen beschlossen:\r\nDie Anhörungsrüge gegen den Senatsbeschluss vom 18-7-2016 wird auf Kosten des Schuldners zurückgewiesen.\r\nGründe:\r\n1 Die zulässige Anhörungsrüge hat in der Sache keinen Erfolg. Eine entscheidungserhebliche Verletzung des rechtlichen Gehörs ist nicht gegeben. Auch unter Berücksichtigung der vom Schuldner mit der Anhörungsrüge geltend gemachten Umstände bleibt es bei der Unzulässigkeit der von ihm eingelegten Rechtsbeschwerde, weil im Verfahren über den Kostenansatz eine Beschwerde an einen obersten Gerichtshof des Bundes nicht stattfindet (§ 66 Absatz 3 Satz 3 GKG).\r\n\r\nBüscher Schwonke Koch Feddersen Löffler Vorinstanzen: LG Hamburg, Entscheidung vom 16-03-2016 - 316 T 5/16 OLG Hamburg, Entscheidung vom 13-04-2016 - 4 W 36/16 -\r\n\r\n'),
('ECLI:DE:BGH:2016:120416BIIZR224.15.0', '74575_clean.txt', 'ECLI:DE:BGH:2016:120416BIIZR224.15.0 BUNDESGERICHTSHOF BESCHLUSS II ZR 224/15 vom 12-4-2016 in dem Rechtsstreit Der II_ Zivilsenat des Bundesgerichtshofs hat am 12-4-2016 durch den Vorsitzenden Richter Prof_ Dr_ Bergmann, die Richterin Caliebe und die Richter Dr_ Drescher, Born und Sunder beschlossen:\nDer Streitwert für das Nichtzulassungsbeschwerdeverfahren wird auf bis zu 300 € festgesetzt.\nGründe:\nDer Senat bewertet die Beschwer der Beklagten und den Streitwert gemäß § 3 ZPO mit bis zu 300 €.\nNach § 17 Nr_ 3 des Gesellschaftsvertrags der Beklagten (künftig: GV)\nfinden jährliche ordentliche Gesellschafterversammlungen statt, zu denen die Gesellschafter der Beklagten, seien sie Direktkommanditisten oder Treugeber,\ndurch die geschäftsführende Kommanditistin (§ 17 Nr_ 1 GV) geladen werden müssen. Um die Ladungen durchführen zu können, muss die Beklagte die Namen und Anschriften ihrer Gesellschafter kennen, da die Ladungen nach § 17 Nr_ 4 GV an die „zuletzt bekannt gegebene Adresse des Gesellschafters“ zu erfolgen haben. Angesichts dessen bedeutet es für sie nur einen geringfügigen Aufwand, die für die Ladung erforderliche Anschriftenliste an den Kläger herauszugeben. Die Beklagte hat weder dargetan noch glaubhaft gemacht, dass sie vor jeder Gesellschafterversammlung bei der Kapitalverwaltungsgesellschaft 1\n2\n\n(KVG) jeweils 20.000 Zeichnungsscheine per Hand durchsehen lässt, um die Namen und die Anschriften ihrer Gesellschafter jedes Mal neu zu ermitteln.\nSoweit die Beklagte nicht selbst über die Namen und Anschriften der Treugeber verfügen sollte, sondern die Ladung nur an die Treuhandkommanditistin richtet und diese sodann die Treugeber lädt, hätte dies lediglich zur Folge, dass sie bei der Treuhandkommanditistin die bei dieser vorhandene Liste anfordern müsste.\nVortrag dazu, dass die Treuhandkommanditistin die Herausgabe der Liste verweigert, hat die Beklagte nicht gehalten. Auch in diesem Fall würde sich daher der Aufwand der Beklagten über den bloßen Ausdruck der Anschriftenliste ihrer Gesellschafter hinaus nur unwesentlich erhöhen. Kosten für die postalische Vorabinformation der übrigen Treugeber, die die Beklagte mit 14600 € beziffert,\nerhöhen die Beschwer und den Streitwert für die Auskunftserteilung nicht, da sie keinen Aufwand darstellen, der für die Erteilung der Auskunft gegenüber dem Kläger „erforderlich“ ist.\n\nDen Kostenaufwand für die Übersendung einer Liste mit den Namen und Anschriften der Gesellschafter bewertet der Senat in ständiger Rechtsprechung mit bis zu 300 €.\nBergmann Caliebe Drescher Born Sunder Vorinstanzen:\nAG München, Entscheidung vom 13-12-2013 - 283 C 18790/13 -\nLG München I, Entscheidung vom 25-06-2015 - 31 S 438/14 (2) -\n3\n'),
('qwert43', 'test6', 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.'),
('test1', 'test5', 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.'),
('test2', 'test4', 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.'),
('trewq34', 'test1', 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `entities`
--

CREATE TABLE `entities` (
  `id` bigint(20) NOT NULL,
  `count` int(11) NOT NULL,
  `relevance` float NOT NULL,
  `text` varchar(20) NOT NULL,
  `type` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `entities`
--

INSERT INTO `entities` (`id`, `count`, `relevance`, `text`, `type`) VALUES
(1, 1, 0.33, 'IBM Watson', 'Company'),
(2, 1, 0.33, 'Alex Trebek', 'Person');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `metadaten`
--

CREATE TABLE `metadaten` (
  `aktenzeichen` varchar(40) NOT NULL,
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
('bobbyistschwul', '2017-02-27', 'Beschluss'),
('ECLI:DE:BGH:2016:050416B3STR10.16.0', '2016-04-05', 'BESCHLUSS'),
('ECLI:DE:BGH:2016:061016BIZB48.16.0', '2016-10-06', 'BESCHLUSS'),
('ECLI:DE:BGH:2016:120416BIIZR224.15.0', '2016-04-12', 'BESCHLUSS'),
('qwert43', '2017-02-01', 'Urteil'),
('test1', '2017-02-20', 'Urteil'),
('test2', '2017-02-20', 'Urteil'),
('testdemo', '2017-02-27', 'Beschluss'),
('testdemo3', '2017-02-27', 'Beschluss'),
('trewq34', '2017-02-01', 'Beschluss');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `zitate`
--

CREATE TABLE `zitate` (
  `id` int(11) NOT NULL,
  `aktenzeichen1` varchar(40) NOT NULL,
  `aktenzeichen2` varchar(40) NOT NULL
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
(12, '987zxc', 'trewq34'),
(13, 'test1', 'test2'),
(14, 'test2', 'test1');

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `dokumente`
--
ALTER TABLE `dokumente`
  ADD PRIMARY KEY (`aktenzeichen`),
  ADD UNIQUE KEY `dateiname` (`dateiname`);

--
-- Indizes für die Tabelle `entities`
--
ALTER TABLE `entities`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `metadaten`
--
ALTER TABLE `metadaten`
  ADD PRIMARY KEY (`aktenzeichen`);

--
-- Indizes für die Tabelle `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

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
-- AUTO_INCREMENT für Tabelle `entities`
--
ALTER TABLE `entities`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT für Tabelle `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT für Tabelle `zitate`
--
ALTER TABLE `zitate`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `dokumente`
--
ALTER TABLE `dokumente`
  ADD CONSTRAINT `fk_aktenzeichen` FOREIGN KEY (`aktenzeichen`) REFERENCES `metadaten` (`aktenzeichen`);

--
-- Constraints der Tabelle `zitate`
--
ALTER TABLE `zitate`
  ADD CONSTRAINT `fk_akt1` FOREIGN KEY (`aktenzeichen1`) REFERENCES `metadaten` (`aktenzeichen`),
  ADD CONSTRAINT `fk_akt2` FOREIGN KEY (`aktenzeichen2`) REFERENCES `metadaten` (`aktenzeichen`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
