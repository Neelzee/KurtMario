#Deloppgave 1
###Gruppenavn: 
KaffeKopp 
<br/><br/>

###Gruppemedlemmer:
* Idar
* Magnus
* Nils Michael
* Thomas
<br/><br/>

###Gruppemedlemmers kompetanse:
Vi ser på oss selv som cirka like gode programmerere, gitt at vi alle studerer Datateknologi, og alle er på andre året.
<br/><br/>

###Git-repo: 
https://git.app.uib.no/kaffekopp/kurt-mario
<br/><br/>

###Rollefordeling:
* Idar: Grafisk ansvarlig, kodeansvarlig
* Magnus: Git-ansvarlig, kodeansvarlig
* Nils Michael: Testeansvarlig, kodeansvarlig 
* Thomas: Scrum Master, Product Owner, kodeansvarlig
<br/><br/>

###Begrunnelse for rollefordeling:
Thomas ble gitt rollen Scrum Master og Product Owner grunnet sin erfaring med Azure DevOps og Scrum.
<br/> Utenom Thomas, ble rollene fordelt litt tilfeldig, da vi ikke kjenner hverandre fra før av, og ikke kjenner hverandres styrker og svakheter.
<br/> Vi ble alle enig om at hvis vi på et senere stadie så at rollen vår ikke passet, skulle vi ta det opp og gjøre et evt. bytte.
<br/><br/>

###Projectboard:
Azure DevOps: https://dev.azure.com/KaffeKopp/Kurt-Mario/

Azure DevOps ble valgt da Thomas hadde erfaring med verktøyet. Boardet ble tilpasset Scrum-metode.
<br/> Alle ble gitt tilgang, og vi verifiserte at alle hadde tilgang til boardet den første gruppetimen etter handout av oblig.
<br/> En kjapp innføring i Azure DevOps, PBIer og tasker ble gjort samme gruppetimen.
<br/> Thomas er hovedansvarlig for å holde boardet oppdatert.

#Deloppgave 2
###Prosjektmetodikk
Vi bestemte oss for å basere prosjektmetodikken vår hovedsakelig på Scrum, da Thomas har tidligere erfaringer med denne metodikken.

###Prosjektorganisering
Vi ble enige om å organisere daglige virtuelle "stand-up" møter på Discord f.o.m. tirsdag 15. feb.
Grunnet utfordringer med konteeksamener, jobb og annet er det ikke alltid alle kan stille på stand-up,
så vi ble enig om at man stiller på de man kan stille på, og hvis det skulle være et eller annet utenom stand-up møtene,
tar vi det opp i Discord-chatten, og får svar fra teamet fortløpende.

All team-kommunikasjon skjer i Discord.
DevOps sender mail dersom en task blir tildelt (men en task skal aldri bli tildelt uten tommel opp fra oppgavetaker på forhånd.)

Arbeidsfordelingen skal være mest mulig rettferdig.

Oppfølging av arbeid skjer hovedsakelig i stand-up møtene, og ellers i Discord chatten.

Alle felles dokumenter skal kun finnes i prosjektes git-repo.

Vi prøver denne metodikken denne første tiden.
I Deloppgave 5 gjør vi et kort retrospektiv, ser på hva som funket og ikke funket/krever justeringer.
Eventuelle justeringer vil da bli gjort innen neste oblig.

#Deloppgave 3
##Spesifikasjon
###En kort beskrivelse av det overordnede målet for applikasjonen:
Det overordnede målet for applikasjonen vår er å lage et 2D-spill der man beveger en karakter rundt ved å gå / hoppe og samler ting mens du passer deg for fiender.

###En liste over brukerhistorier til systemet basert på MVP-kravene:
>**kun for historier dere er ferdige med, holder på med, eller skal til å begynne med

####Brukerhistorie 1
* Brukerhistorie: <br/>
  Som en spiller av spillet Kurt-Mario,
  må jeg kunne se spillbrettet, se spillkarakteren på spillbrettet og flytte karakteren min
  slik at jeg får en følelse av at jeg spiller et spill.
  <br/><br/>

* Akseptansekriterier: <br/>
  Gitt at jeg har startet spillet Kurt-Mario, så skal jeg bli presentert med et spillbrett
  og en spillkarakter på spillbrettet, der karakteren kan beveges på brettet basert på mitt input.
  <br/><br/>

* Arbeidsoppgaver: <br/>
    * Finne bilder som kan brukes som spiller og monster
    * Lage spillbrett til applikasjonen med Tiled
    * Lage metode for å flytte spiller.
    * Legge til texture for å "tegne" spille brett og figur.
      <br/><br/>

* Beskrivelse av hvilke krav brukerhistorien oppfyller: <br/>
  Denne brukerhistorien oppfyller MVP-krav 1-3.
  <br/><br/>

####Brukerhistorie 2
* Brukerhistorie: <br/>
  Som en spiller av spillet Kurt-Mario,
  ønsker jeg at spillkarakteren skal kunne interragere med terreng og fiender, og ha en poengscore og kan få/miste poeng
  slik at jeg ser økt underholdningsverdi i spillet.
  <br/><br/>

* Akseptansekriterier: <br/>
  Gitt at jeg spiller Kurt-Mario, så skal spilleren min kunne interragere med terreng og fiender, og ha en poengscore som påvirkes av handlinger i spillet.
  <br/><br/>

* Arbeidsoppgaver: <br/>
    * Abstraherte ut spiller ifrå Game.java til ei eigen klasse, som har metoder som pickUp(liste), der den plukker opp,
    * det som er nærmest, og legger til scoren til det "item" til player sin score, og fjerner dette ifrå spillet
    * Fiender går imot spiller viss den er innanfor synsrekkevidde, og kan berre bevege seg høgre eller venstre.
    * Spiller forsvinner viss fienden har "drept" spiller
      <br/><br/>

* Beskrivelse av hvilke krav brukerhistorien oppfyller: <br/>
  Denne brukerhistorien oppfyller MVP-krav 4-6.
  <br/><br/>

###En prioritert liste over hvilke brukerhistorier dere vil ha med i første iterasjon
* Brukerhistorie 1, da den dekker minimal kode for MVP 1-3, som er en del av minstekravet i forventningen til innleveringen vår.
* Brukerhistorie 2, da det er et naturlig steg videre etter at vi er ferdige med Brukerhistorie 1.


<br/><br/>

Brukerhistorietemplate:
Som en [bruker]
Ønsker jeg en [funksjon]
Slik at jeg [oppnår en verdi]

#Deloppgave 4

###Kilder:
<br/>

###PNG bilder:
RobotMonster: https://opengameart.org/content/gum-bot-sprites
Spiller: https://opengameart.org/content/cat-fighter-sprite-sheet
Spillbrett: https://opengameart.org/content/lpc-tile-atlas
<br/><br/>

####Collectibles:
https://opengameart.org/content/gem-jewel-diamond-glass
https://opengameart.org/content/platformer-pickups-pack
(Catnip): https://www.musti.no/yeowww-loose-catnip?52294&gclid=EAIaIQobChMIo_Wy1piM9gIVE-3tCh2FzwyeEAQYAyABEgIsHfD_BwE

#Deloppgave 5
###Det som gikk bra:
* Møtefrekvensen
    * Alle er enig om at daglige korte stand-up møter (når det skal jobbes intensivt) er veldig bra.
* Kodingen
    * Veldig fornøyd med hvor langt vi er kommet ifht. tid. Vi har laget en veldig grei "grunnmur"/foundation for å kunne bygge videre på prosjektet.
    * God dokumentasjon, høy kodekvalitet og oversiktlighet.
* Møtekvaliteten
    * Korte og effektive møter. Alle har vært veldig tilpassningsdyktige ifht tidspunkt av møter.
* Git
    * Så og si ingen git-problemer. Fortsett å "merge" og å oppdatere prosjektet ofte.
* Tonen
    * Veldig god tone i gruppen! Høyt engasjement.


###Det som ikke gikk så bra:
* Uklar sprintlengde
    * Vi bestemte oss egentlig for 1 ukers lange Sprinter, vi går utifra 2 ukers sprinter herifra.
* Vi kom sent i gang
    * Vi kom sent i gang med kodingen, da vi ventet på skjelettkoden før vi begynte. Vi hadde nok kunne komt enda lenger hvis vi
      begynte med kodingen allerede i første uken uten skjelletkoden.
* Utdeling av kodeoppgaver
    * Vanskelig å dele opp kodeoppgavene til 4 personer. Prøve å lage flere oppgaver neste gang / kjøre parprogrammering.
* MOM for alle møter, kun en MOM...

###Prøve ut i neste oblig:
* Abstrahere ut ting som er spesielt for Kurt-Mario i en egen Kurt-Mario klasse
* Få "kamera" til å bevege seg
* Gravitasjon
* Legge til integrering av terreng på en mer "scaleable" måte
* Finne ut om det finnes noe fysikk i LibGDX-biblioteket som vi kan bruke.
* Prøve parprogrammering gjennom "code with me" funksjonen i IntelliJ
*

###Hvor bra traff vi på oppgaven?
* Vi har oppfylt alle forventinger til denne oppgaven.