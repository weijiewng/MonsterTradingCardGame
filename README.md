# MonsterTradingCardGame
SWEN - MTCG

Technische Schritte
Zuallererst habe ich damit begonnen, die Logik hinter dem Spiel selber zu entwerfen. Dazu habe ich ein erstes Klassendiagramm gemacht.

Dann habe ich damit begonnen, dieses Diagramm umzusetzen. Nachdem ich auch gelernt habe, wie man die Server Architektur und die Datenbankarchitektur habe ich damit begonnen, das Programm anders aufzubauen. Ich habe mich weitgehend am Curl Script gehalten. 

So habe ich dann ein neues Projekt begonnen, wobei die Logik von dem Spiel sich kaum verändert hat. Was ich gemerkt habe war, dass die meisten Objekte wie Karten, User oder Tauschangebote Ids benötigen und ich diese hinzufügen musste. 

Schwierig habe ich mir bei der Token-Authentifizierung getan, sodass ich es schließlich so gelöst habe, dass der Username sowie das Passwort einzigartig sein müssen und der Token aus dem Namen sowie “mtcgToken” bestehen, welcher auch gleichzeitig die Ids vom User ist. 

Besonders schwierig war das Handling vom Battle. Schlussendlich bin ich auf folgende Lösung gekommen: Der User sucht ein Battle. Dieser wird dann in eine Liste geworfen, wo sich andere User befinden, welche ebenfalls kämpfen wollen. Sollte sich keiner darin befinden, wird der User eine Zeit lang warten (in dem Fall 5 Sekunden). Sollte sich dann immer noch kein anderer User in der Liste befinden, wird der Versuch abgebrochen und der User muss nochmal ein Battle suchen. Sollte sich jedoch währenddessen ein User in der Liste befinden, wird ein Battle gestartet.

Als einzigartiges Feature habe ist die automatische Generierung von Packages, welche sich anhand eines Namens erstellen lassen. Der Preis sowie die Wahrscheinlichkeit, dass sich gute Karten darin befinden variieren. Die Funktionalität ist durch Tests gegeben. Jedoch ist mir nicht gelungen, die Packages bzw die Karten aus den Packages zu arbeiten, da diese zufällig generiert werden und ich sie somit nicht wirklich ins Curl Script integrieren kann.

Zeitaufwand: 100 Stunden

Link zum Git: https://github.com/weijiewng/MonsterTradingCardGame




