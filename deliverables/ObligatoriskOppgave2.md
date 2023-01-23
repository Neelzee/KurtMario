# Oblig 2 – *Kurt-Mario*

* Team: *KaffeKopp* (Gruppe 3):
  * *Idar Hansen*
  * *Magnus Vestvik*
  * *Nils Michael Fitjar*
  * *Thomas Børdal*

# Deloppgave 1
* Hvordan fungerer rollene i teamet?
  * Rollene fungerer bra. Vi ser ingen grunn il å endre på disse på dette stadiet. Vil bli vurdert også neste oblig.


* Trenger dere andre roller? Skriv ned noen linjer om hva de ulike rollene faktisk innebærer for dere.
  * Nei, vi trenger ikke andre roller på dette stadiet. Thomas sin rolle innebærer og holde project board oppdatert.


* Er det noen erfaringer enten team-messig eller mtp prosjektmetodikk som er verdt å nevne? Synes teamet
  at de valgene dere har tatt er gode? Hvis ikke, hva kan dere gjøre annerledes for å forbedre måten teamet
  fungerer på?
  * Vi er veldig fornøyd med effektiviteten, som gruppe. Daily stand-up meetings (i intensive sprinter) er helt konge! 
  * Det ble gjort noen endringer på project board etter tilbakemelding fra oblig1. Dette vil nok gjøre det lettere å holde boardet oversiktlig og oppdatert.
  
  
* Hvordan er gruppedynamikken? Er det uenigheter som bør løses?
  * Helt konge gruppe! Veldig god tone, saklige diskusjoner, høyt engasjement. Alle tar ansvar. Ingen uenigheter som må løses.  
  

* Hvordan fungerer kommunikasjonen for dere?
  * Fungerer bra. Discord-chatten blir brukt flittig om vi trenger input utenom stand-up. Vi er flinke til å gi beskjed om vi er sene til møter, om vi ikke kommer etc.
  * DevOps sender mail om man blir assignet Task.

## Kort retrospektiv

### Git
* Hvis det ser ut til at det er veldig skjevfordeling på hvem som har committed mye og ikke, så er det nok fordi noen veksler mellom forks og master, mens noen jobber eksklusivt i forks.


### Det som har gått bra:
* Hvor langt vi er kommet
  * Høy effektivitet. Mye er blitt abstrahert og er mye mer scalable. Gir en mye bedre "foundation" for videre utvikling. Vi er nesten på det stadiet at vi kan konsentrere oss mer om gameplay og levels.
* Nå har vi MOM for alle møter! Ligger under Deliverables/MOM
* Vi har vært bedre på DevOps, og brukerhistorier. Vi holder boardet oppdatert og lager brukerhistorier "as we go".
* Vi har vært bedre på å dele opp oppgaver. Det er lettere å dele ut kodeoppgaver nå enn før grunnet bedre abstrahering. Vi kan nå jobbe på forskjellige plasser i prosjektet samtidig uten kollisjoner.   


### Det som ikke gikk så bra:
* Vi kom sent i gang
  * Vi kom sent i gang. Vi har ikke fått jobbet noe særlig med prosjektet siden forrige oblig grunnet andre obligatoriske innleveringer og sykdom. Så denne sprinten blir effektivt tirsdag -> fredag. Ikke helt optimalt.
* Vi må jobbe i forks! Nå har det vært mye fram og tilbake med å jobbe i fork / jobbe i master. Fremover skal vi hovesaklig kun jobbe i fork.


### Forbedringspunkter fra retrospektivet, som skal følges opp under neste sprint:
* Jobbe mest i forks
* Komme tidligere i gang

# Deloppgave 2
### «Stretch goal»
For Oblig2, er Lokal Multiplayer vårt stretch-goal.

### MVP og annet

* Oppdater hvilke krav dere har prioritert, hvor langt dere har kommet og hva dere har gjort siden forrige gang. Er dere kommet forbi MVP? Forklar hvordan dere prioriterer ny funksjonalitet.
  * I Oblig2 har vi har prioritert MVP krav 8-11.
  * Vi har per Oblig2 fyllt alle MVP kravene. 
  * Når MVP er Done, vil vi prioritere penere grafikk før nye features. 
* Husk å skrive hvilke bugs som finnes i de kravene dere har utført (dersom det finnes bugs). 
  * Filepaths i tester er broken. 
  * Når vi går til slutten av siste banen, krasjer spillet.
  * Per nå bruker vi nesten bare bibliotek, så flere bugs regner vi med kommer på et senere stadie.


#### Brukerhistorie 3
* Brukerhistorie: <br/>
  Som en spiller av spillet Kurt-Mario i multiplayer-mode,
  så jeg vil gjerne kunne spille spillet sammen med en venn på samme tastatur.
  <br/><br/>

* Akseptansekriterier: <br/>
  Gitt at jeg har startet spillet Kurt-Mario og valgt multiplayer-mode, skal spillet starte med to spillere, der player1 styres av WASD og player2 styres av piltaster.
  <br/><br/>

* Arbeidsoppgaver: <br/>
  * Konstruer to spillere
  * Endre update-metoden slik at kamera flytter seg etter spiller med høyest x-verdi
  * Endre handleInput-metoden slik at player2 blir styrt av piltaster uavhengig av player1
    <br/><br/>

* Beskrivelse av hvilke krav brukerhistorien oppfyller: <br/>
  Denne brukerhistorien oppfyller MVP-krav 11 ("Støtte flere spillere (enten på samme maskin eller over nettverk)").
  <br/><br/>

####Brukerhistorie 4
* Brukerhistorie: <br/>
* Som ein spelar av spelet Kurt-Mario forventer eg at figuren skal stoppe når den krasjer med terrenget.
  <br/><br/>

* Akseptansekriterier: <br/>
* Når eg beveger spelaren mot terreng, så skal den stoppe, når eg hopper opp på ein bit med terreng, skal spelaren stå der.
  <br/><br/>

* Arbeidsoppgaver: <br/>
  * Konstruere hitboxes på all terreng
  * Konstruere hitbox på spelaren
  * Implementere kode som sørger for at hitboxes stopper når dei krasje
    <br/><br/>

####Brukerhistorie 5
* Brukerhistorie: <br/>
* Som en spiller av Kurt-Mario vil jeg ha en hovedmeny som kommer opp når ved oppstart av spillet. Menyen skal ha en
* Play Solo, Play Duos og en Quit knapp som starter spillet i én-player, to-player eller avslutter spillet.
  <br/><br/>

* Akseptansekriterier: <br/>
* Når jeg kjører spillapplikasjonen skal ikke spillet starte med en gang, men en meny skal komme opp. Når jeg trykker
* på Quit skal applikasjonen avsluttes. Når jeg trykker på Play Solo skal spillet kjøres med et spillbrett ment for 1 spiller.
* Når jeg trykker på Play Duos skal spillet kjøres med et spillbrett ment for 2 spillere.
  <br/><br/>

* Arbeidsoppgaver: <br/>
  * Lage/finne bilder som kan brukes som knapper.
  * Vise knappene på en fin og ryddig måte(f.eks. ved bruk av stage og table).
  * Sørge for hele bildet av knappene faktisk telles som en trykkbar knapp.
  * Sørge for at de ulike knappene knappene fører til riktig aksjon.
  * Sørge for at knappene fortsatt fungerer som de skal uansett hvordan spillvinduet endres i størrelse og form.
    <br/><br/>

#Deloppgave 3
Klassediagram finnes i filen klassediagram

`## Kode`
Vi har ikke fjernet så mye av den ubrukte koden denne gangen, da vi vil prøve å få til development-branch og en innleverings-branch til Oblig3.

* *Utbedring av feil:* hvis dere har rettet / forbedret noe som dere har fått trekk for tidligere, lag en liste med «Dette har vi fikset siden sist», så det er lett for gruppelederne å få oversikt.
  * Vi har forbedret projectboardet med nye kolonner og annen måte å føre opp PBIer. PBIene er mer selvstendige nå, og ikke bare rene deloppgaver med sub-tasks.
  * Vi har merget deloppgaver for Oblig1 inn i ObligatoriskOppgave1.
  * Vi har nå tester, i motsetning til Oblig1.
  * Vi har lagt til informasjon om teknsik produktoppsett (hvordan kjøre på macOS og java versjon det er testet for)
* Prosjektet skal kunne bygge, testes og kjøres på Linux, Windows og OS X – dere kan f.eks. spørre de andre teamene på gruppen om dere ikke har tilgang til alle platformene. *OBS!* Den vanligste grunnen til inkompatibilitet med Linux er at filnavn er *case sensitive*, mens store/små bokstaver ikke spiller noen rolle på Windows og OS X. Det er viktig å sjekke at stiene til grafikk og lyd og slikt matcher eksakt. Det samme vil antakelig også gjelde når man kjører fra JAR-fil.
  * Prosjektet er testet og verifisert på alle platformer nevnt ovenfor. 
* Lag og lever et klassediagram.
  * Klassediagrammet finnes her: `Deliverables/Klassediagram_Oblig2.uml`
* Utførte oppgaver skal være ferdige. Slett filer/kode som ikke virker eller ikke er relevant (ennå) for prosjektet. (Så lenge dere har en egen git branch for innlevering, så er det ikke noe stress å fjerne ting fra / rydde den, selv om dere fortsetter utviklingen på en annen gren.
  * Til Oblig3: Lag development-branch og innleverings-branch for å kunnef jerne kode vi ikke bruker.
