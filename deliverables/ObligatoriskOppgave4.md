# Oblig 4 – *Kurt-Mario*

* Team: *KaffeKopp* (Gruppe 3):
  * *Idar Hansen*
  * *Magnus Vestvik*
  * *Nils Michael Fitjar*
  * *Thomas Børdal*

Minutes of meeting finnes her: [MOM](./MOM)


# Deloppgave 1
### Rollefordeling:
* Idar: Grafisk ansvarlig, kodeansvarlig
* Magnus: Git-ansvarlig, kodeansvarlig
* Nils Michael: Testeansvarlig, kodeansvarlig
* Thomas: Scrum Master, Product Owner, kodeansvarlig
  <br/><br/>
* Hvordan fungerer rollene i teamet?
  * Rollene har fungert bra. Vi har ikke sett noen grunn til å endre på disse gjennom semesteret.

* Trenger dere andre roller? Skriv ned noen linjer om hva de ulike rollene faktisk innebærer for dere.
  * Nei, vi trenger ikke andre roller. 
  * Idar sin rolle innebærer å designe og implementere UX, UI, GX, og nye maps. Ellers ansvarlig for å kode.
  * Magnus sin rolle innebærer å sørge for at vi jobber i branches, at vi unngår merge-conflicts og hovedansvar for å løse merge-conflicts. Ellers ansvarlig for å kode.
  * Nils Michael sin rolle innebærer å implementere tester, og ellers overordnet ansvarlig for å teste gameplay. Ellers ansvarlig for å kode.
  * Thomas sin rolle innebærer og holde project board oppdatert, lage nye sprinter i DevOps, føre møtereferat. Ellers ansvarlig for å kode.

  
* Er det noen erfaringer enten team-messig eller mtp prosjektmetodikk som er verdt å nevne? Synes teamet
  at de valgene dere har tatt er gode? Hvis ikke, hva kan dere gjøre annerledes for å forbedre måten teamet
  fungerer på?
  * Vi er veldig fornøyd med effektiviteten, som gruppe. Daily stand-up meetings (i intensive sprinter) er fortsatt helt konge! 
  * Det ble gjort noen endringer på project board etter tilbakemelding fra oblig1. Dette har fungert bedre også i denne oblig4-iterasjonen og vi er flinkere til å holde project boardet oppdatert.
  * Vi hadde ikke et klart mål om hva spillet skulle gå ut på egentlig før vi var ferdig med MVP kravene. 
  Produktet kunne kanskje vært enda bedre om vi hadde hatt et klart og tydelig mål tidligere.
  * Vi ser viktigheten av koordinering: Når skal vi ha neste møte? Hva passer for alle? Hvem har kapasitet? Alle har naturlig nok andre ting på kalenderen, som jobb, ekstra fag, reise.
Vi har vært heldige med at vi går samme studieprogram, og vi har stort sett samme fag, og ellers har alle vært tilpasningsdyktige. Derfor har det stort sett gått fint å koordinere intensive sprinter opp mot obliger.
  * Vi ser viktigheten av dokumentasjon: Mange ganger har vi måtte ta kontakt med forfatter av ulike metoder for å få full forståelse.
    Dokumentasjon og javadocs er derfor veldig viktig.


* Hvordan er gruppedynamikken? Er det uenigheter som bør løses?
  * Fortsatt helt konge gruppe! Veldig god tone, saklige diskusjoner, høyt engasjement. Alle tar ansvar. Ingen uenigheter som må løses.  
  

* Hvordan fungerer kommunikasjonen for dere?
  * Daily stand-up i intensive perioder i sprinten gjør kommunikasjon lett. I disse møtene har vi lagt fram det vi har jobbet med, det vi er ferdig med, det vi lurer på, det vi sliter med, det vi trenger hjelp til og det vi skal jobbe med videre.
På denne måten har vi boostet effektivitet, holdt track på hvor vi skal og unngått dobbeltarbeid.
  * Discord-chatten blir fortsatt brukt flittig om vi trenger input utenom stand-up. Vi er flinke til å gi beskjed om vi er sene til møter, om vi ikke kommer etc.
  * DevOps sender ellers mail om man blir assignet PBI/Task.

## Kort retrospektiv

### Det som har gått bra:
* Vi nådde stretch goal: Public Scoreboard på SQL server.
* Veldig godt første "sprint planning" møte denne obligen også, der vi fikk alles ideer inn i Backlog i project board. 
Så prioriterte vi og assignet tasker, og når man var ferdig med disse kunne man velge å vrake hva man ville jobbe med fra Backlog.
* God fremgang til tross for andre samtidige obliger denne obligen også.


### Det som ikke gikk så bra:
* Vi fikk ikke tid til å perfeksjonere scoreboard scaling.
* Vi fikk et problem med at vi hadde brukt switches med pattern matching, noe som er en preview feature i java17. 
Dette ga oss litt problemer med testing av maven bygging, og noe tid som kunne bli brukt til andre ting gikk med til feilsøking på dette.
* Noen features ble implementert rett mot dev-branch (til tross for at vi har sagt at vi skal implementere i featurebranches), rett og slett slack som gjorde dette...

### Forbedringspunkter fra retrospektivet, som hadde blitt fulgt opp under neste sprint hvis vi hadde en:
* Alltid implementer features i feature-branches!

# Deloppgave 2
### «Stretch goal»
For Oblig4, er Public Scoreboard på SQL-server vårt stretch-goal.

### MVP og annet

* Oppdater hvilke krav dere har prioritert, hvor langt dere har kommet og hva dere har gjort siden forrige gang. Er dere kommet forbi MVP? Forklar hvordan dere prioriterer ny funksjonalitet.
  * I Oblig4 har vi har prioritert å implementere stretch-goal, finalized mute-knapp, forberedt presentasjon og å ellers finpusset spillet. 
  * Vi har per Oblig4 fyllt alle MVP kravene.
* Husk å skrive hvilke bugs som finnes i de kravene dere har utført (dersom det finnes bugs). 
  * Wall-Jump - Du kan avogtil hoppe veldig høyt om du har kontakt med vegg. (valgt å ikke fikse siden fiksen introduserte veldig buggy jump)  
  * Mute-knapp - knapp er ikke scalerbar (starter du spillet i fullskjerm, og minimerer funker ikke mute-knapp lenger)
  * Lava - noen ganger kan spiller begynne å brenne av at et monster er i lava, og vice-versa
  * MovingPlatforms kan avogtil stoppe, og begynne å vibrere
  * Scoreboard - scoreboard er kun skalert for default window size og full-screen.

### Brukerhistorier

#### Brukerhistorie 10
* Brukerhistorie: <br/>
* Som en spiller av Kurt-Mario vil jeg gjerne ha muligheten til å "mute" spillet slik at om lyden begynner å irritere meg kan jeg fjerne den.
  <br/><br/>

* Akseptansekriterier: <br/>
* Fra in game hud må det være en knapp som jeg kan trykke på som gjør spillet stille.
* Når spilleren går videre til neste bane må man huske "state" som mute knappen hadde ifra forrgie bane, og gjenopprette denne slik at man ikke trenger å trykke mute flere ganger.
  <br/><br/>


#### Brukerhistorie 9
* Brukerhistorie: <br/>
* Som en spiller av Kurt-Mario vil jeg gjerne se hvordan jeg gjør det mot de beste som spiller Kurt-Mario, fordi det vil legge til et konkurranseelement som vil øke underholdningsverdien av spillet.
  <br/><br/>

* Akseptansekriterier: <br/>
* Fra hovedmenyen skal spilleren kunne se Top10 beste spillere av Kurt-Mario
* Når spilleren har runnet ferdig Kurt-Mario, skal man kunne publisere tiden sin hvis den er god nok til Top10
* Når spilleren har runnet ferdig Kurt-Mario, skal man kunne oppdatere tiden sin hvis den er god nok til Top10 og bedre enn den forrige
* Sikker overføring av data over internett
  <br/><br/>

* Arbeidsoppgaver: <br/>
  * Sette opp en SQL server
  * Sørge for at det brukes en kryptert connection for å sende og motta data over internett
  * Implementere en metode som henter top 10 spillere fra databasen, sortert på tid, så score
  * Implementere en metode som submitter tiden sin til databasen
  * Implementere en metode som kan opppdatere tiden sin
  * Implementere en ny screen som viser top 10 spillere fra databasen
  * Implementere en ny knapp som submitter et brukernavn, tid og score til databasen
  * Implementere metode for å oppdatere tid og score for et brukernavn dersom den er bedre enn den forrige
    <br/><br/>
  
# Deloppgave 3
* *Utbedring av feil:* hvis dere har rettet / forbedret noe som dere har fått trekk for tidligere, lag en liste med «Dette har vi fikset siden sist», så det er lett for gruppelederne å få oversikt.
  * Beskrivelse på hva teammedlemmene gjør er forbedret
  * Vi har bedre brukerhistorier med god nytteverdi
  * Vi har kun fungerende tester
  * Vi har brukt SpotBugs som ga oss enda mer å rette opp i!(selv om enkelte ting var litt vel komplisert å få fikset, f.eks. håndtering av passord til databasen i plaintext)
  * Bedre Abstraskjon og bedre SRP
  * Ubrukt kode fra Oblig3 leveransen er borte!
* Prosjektet skal kunne bygge, testes og kjøres på Linux, Windows og OS X – dere kan f.eks. spørre de andre teamene på gruppen om dere ikke har tilgang til alle platformene. *OBS!* Den vanligste grunnen til inkompatibilitet med Linux er at filnavn er *case sensitive*, mens store/små bokstaver ikke spiller noen rolle på Windows og OS X. Det er viktig å sjekke at stiene til grafikk og lyd og slikt matcher eksakt. Det samme vil antakelig også gjelde når man kjører fra JAR-fil.
  * Prosjektet er testet og verifisert på alle platformer nevnt ovenfor. 
* Lag og lever et klassediagram.
  * Klassediagrammet finnes her: [Klassediagram_Oblig4.png](Klassediagram_Oblig4.png)
