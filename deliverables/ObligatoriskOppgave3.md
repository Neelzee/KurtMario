# Oblig 3 – *Kurt-Mario*

* Team: *KaffeKopp* (Gruppe 3):
  * *Idar Hansen*
  * *Magnus Vestvik*
  * *Nils Michael Fitjar*
  * *Thomas Børdal*

Minutes of meeting finnes her: [MOM](./MOM)


# Deloppgave 1
* Hvordan fungerer rollene i teamet?
  * Rollene fungerer bra. Vi ser ingen grunn til å endre på disse på dette stadiet. Vil bli vurdert også neste oblig.


* Trenger dere andre roller? Skriv ned noen linjer om hva de ulike rollene faktisk innebærer for dere.
  * Nei, vi trenger ikke andre roller på dette stadiet. Thomas sin rolle innebærer og holde project board oppdatert.
  
* Er det noen erfaringer enten team-messig eller mtp prosjektmetodikk som er verdt å nevne? Synes teamet
  at de valgene dere har tatt er gode? Hvis ikke, hva kan dere gjøre annerledes for å forbedre måten teamet
  fungerer på?
  * Vi er veldig fornøyd med effektiviteten, som gruppe. Daily stand-up meetings (i intensive sprinter) er fortsatt helt konge! 
  * Det ble gjort noen endringer på project board etter tilbakemelding fra oblig1. Dette har fungert bedre i denne oblig3-iterasjonen og vi er flinkere til å holde project boardet oppdatert.
  * Vi hadde ikke et klart mål om hva spillet skulle gå ut på egentlig før vi var ferdig med MVP kravene. 
  Produktet kunne kanskje vært enda bedre om vi hadde hatt et klart og tydelig mål tidligere.
  
  
* Hvordan er gruppedynamikken? Er det uenigheter som bør løses?
  * Fortsatt helt konge gruppe! Veldig god tone, saklige diskusjoner, høyt engasjement. Alle tar ansvar. Ingen uenigheter som må løses.  
  

* Hvordan fungerer kommunikasjonen for dere?
  * Fungerer fortsatt bra. Discord-chatten blir fortsatt brukt flittig om vi trenger input utenom stand-up. Vi er flinke til å gi beskjed om vi er sene til møter, om vi ikke kommer etc.
  * DevOps sender fortsatt mail om man blir assignet Task.

## Kort retrospektiv

### Det som har gått bra:
* Vi begynte tidlig å jobbe denne gangen. 
  * Veldig godt første "sprint planning" møte, der vi fikk alles ideer inn i Backlog i project board. 
Så prioriterte vi og assignet tasker, og når man var ferdig med disse kunne man velge å vrake hva man ville jobbe med fra Backlog.
* Git
  * Funker veldig bra å jobbe i branches og merge med Dev branch.
* God fremgang til tross for andre samtidige obliger.


### Det som ikke gikk så bra:
* Vi fikk ikke tid til å implementere Oblig3's stretch-goal

### Forbedringspunkter fra retrospektivet, som skal følges opp under neste sprint:
* Definer stretch-goal for Oblig4 i første Sprint planning møte

# Deloppgave 2
### «Stretch goal»
For Oblig3, er playeranimasjoner stretch-goal.

### MVP og annet

* Oppdater hvilke krav dere har prioritert, hvor langt dere har kommet og hva dere har gjort siden forrige gang. Er dere kommet forbi MVP? Forklar hvordan dere prioriterer ny funksjonalitet.
  * I Oblig3 har vi har prioritert å finpusse spillet slik at det ser mer "proft" ut, og legge til noen nye features
  * Vi har per Oblig3 fyllt alle MVP kravene. 
  * Når MVP er Done, vil vi prioritere penere grafikk før nye features. 
* Husk å skrive hvilke bugs som finnes i de kravene dere har utført (dersom det finnes bugs). 
  * Monstre sin AI er broken på siste map, de beveger seg ikke.


#### Brukerhistorie 6
* Brukerhistorie: <br/>
* Som en spiller av Kurt-Mario trenger jeg ikke se utenom kartet når karakteren er i en ende av kartet.
  <br/><br/>

* Akseptansekriterier: <br/>
* Når spillet starter skal kartets begynnelse være helt nede og til venstre i bilde, slik at det er kun kartet som vises. 
* Når spilleren beveger seg til enden av kartet, skal ikke man kunne se forbi kartet. 
* Når spilleren hopper i taket i kartet, skal ikke man kunne se forbi taket.
  <br/><br/>

* Arbeidsoppgaver: <br/>
  * Finne ut hva som avgjør hva kameraposisjonen skal være i endene av kartet
  * Implementere logikk for å låse kamera når det er i endene av kartet
    <br/><br/>

#### Brukerhistorie 7
* Brukerhistorie: <br/>
* Som en spiller av Kurt-Mario vil jeg ha lyd effekter.
  <br/><br/>

* Akseptansekriterier: <br/>
* Når spilleren hopper skal det kom en lyd.
* Når spilleren treffer en vegg skal det spille av en lyd.
* Når spilleren tar skade skal det spilles av en lyd.
* Når spilleren brenner skal det spilles av en lyd.
  <br/><br/>

* Arbeidsoppgaver: <br/>
  * Finne passende lyd effekter.
  * Implementere lyd effektene inn i koden.
    <br/><br/>

#### Brukerhistorie 8
* Brukerhistorie: <br/>
* Som en programmerer av Kurt-Mario vil eg kunne implementere eigne reaksjoner når Actors kolliderer.
  <br/><br/>

* Akseptansekriterier: <br/>
* Kunne kalle på ein spesifik funksjon når to GameObjects starter ein kollisjoner
* Kunne kalle på ein spesifik funksjon når to GameObjects slutter å kollidere.
* Kunne spesifikt kalle på dei "instances" av klassene som kolliderer.
  <br/><br/>

* Arbeidsoppgaver: <br/>
  * Ta i bruk av "ContactListener" som er innebygd i LibGDX
  * Implementer ein klasse som har i oppgåve å holde på eit "instance" av ein spesifik klasse.
  * Implementer ein måte å finne ut av kva for nokre klasser som kolliderer.
    <br/><br/>
  
# Deloppgave 3
* *Utbedring av feil:* hvis dere har rettet / forbedret noe som dere har fått trekk for tidligere, lag en liste med «Dette har vi fikset siden sist», så det er lett for gruppelederne å få oversikt.
  * README er utbedret med instrukser på hvordan spillet kan bygges og kjøres fra kommandolinje
  * Ubrukt kode fra oblig3 leveransen er borte!
  * Klassediagram i png, public metoder og feltvariabler inkludert.
  * Relative paths for leveransefiler/mapper i ObligatoriskOppgave3.md 
* Prosjektet skal kunne bygge, testes og kjøres på Linux, Windows og OS X – dere kan f.eks. spørre de andre teamene på gruppen om dere ikke har tilgang til alle platformene. *OBS!* Den vanligste grunnen til inkompatibilitet med Linux er at filnavn er *case sensitive*, mens store/små bokstaver ikke spiller noen rolle på Windows og OS X. Det er viktig å sjekke at stiene til grafikk og lyd og slikt matcher eksakt. Det samme vil antakelig også gjelde når man kjører fra JAR-fil.
  * Prosjektet er testet og verifisert på alle platformer nevnt ovenfor. 
* Lag og lever et klassediagram.
  * Klassediagrammet finnes her: [Klassediagram_Oblig3.png](Klassediagram_Oblig3.png)
