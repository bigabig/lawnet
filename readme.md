## Lawnet Dokumentation

### Applikation Starten

#### Voraussetzungen

- MySQL Server (z.B. xampp)

#### Einstellungen

Öffnen Sie die 'application.properties' Datei und setzen Sie ihre eigenen Werte
default DBUSER: dbuser1
default DBPASS: letmein

#### Vom Terminal starten

Navigieren sie zum Root-Verzeichnes dieses Projekts und geben Sie folgendes ein:

    $ mvn spring-boot:run

#### Von IDE

Import as *Existing Maven Project* and run it as *Spring Boot App*.


### Nutzung

- Starten Sie die Applikation und Gehen Sie auf http://localhost:8080/graph
- Ihnen stehen nun folgende Funktionen zur Verfügung:
    * Suchen
    * Hochladen
    * Importieren
    * Löschen

#### Suchen
Hier können Sie die Dokumente in der Datenbank durchsuchen und deren Beziehungen als gerichteten Graphen ausgeben lassen.
Ihnen stehen als Kriterien Aktenzeichen, Datum, Typ und Zitierungsweise zur Verfügung.
    * Aktenzeichen: Hier können sie ein bestimmtes Aktenzeichen eingeben, oder ein partielles Aktenzeichen, sodass mehrere gefunden werden können.
      Achtung: Bei der Suche nach dem Aktenzeichen werden weitere Kriterien ignoriert!
    * Datum: beschränkt die Suche auf ein konkretes Datum
    * Typ:  beschränkt die Suche auf einen konkreten Typ
    * Zitierung: hier haben sie die Auswahl zwischen aktiv, passiv und aktiv&passiv.
        * "aktiv" findet zusätzlich zu den Dokumenten A, die die Suchkriterien erfüllen, alle weiteren Dokumente B, die diese zitieren. Es gilt "A zitiert B".
        * "passiv" findet zusätzlich zu den Dokumenten A, die die Suchkriterien erfüllen, alle weiteren Dokumente B, welche diese zitieren. Es gilt "B zitiert A"

Hinweis: Sie können auf den Knoten eines Graphen klicken, um an den Volltext und die Metadaten des entsprechenden Dokumentes zu gelangen.

#### Hochladen
Hier können sie einen Gerichtsbeschluss als .pdf hochladen.
Warten Sie bitte ca. 10 Sekunden bevor Sie auf den Link 'show Graph' drücken, welcher ihnen das gerade hochgeladene Dokument als Graph darstellt.

#### Importieren
Hier können sie eine .txt Datei hochladen, die Dateinamen von Dokumenten enthält. Alle gelisteten Dokumente werden daraufhin
in die Datenbank importiert, was einige Zeit dauer kann.
Diese Funktion wurde vor allem zum Erstellen eines Anfangsbestands in der Datenbank entwickelt, und wird später ausschließlich
Administratoren dieser Applikation zur Verfügung stehen.

##### Konfiguration von Import
Damit die Import-Funktion funktioniert, müssen alle Dokumente als bereinigte _clean.txt Datei auf dem Server liegen.
Dazu muss im User-Verzeichnis der Ordner "files" angelegt werden und mit den entsprechenden Dokumenten gefüllt werden.
Die Dateinamen in der hochgeladenen .txt Datei müssen diesen Dokumenten im "files" Ordner entsprechen.

#### Löschen
Hier können Sie Dokumente aus dem Datenbestandt löschen. Tippen Sie dazu das exakte Aktenzeichen des Dokuments,
welches Sie löschen möchten in das dafür vorgesehene Feld.
Nach erfolgreicher Löschung erhalten Sie eine Übersicht aller entfernten Einträge aus der Datenbank.

