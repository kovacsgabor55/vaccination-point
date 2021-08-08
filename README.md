# Vizsgaremek - Vaccination point administered system

## Leírás

Ezen alkalmazás segítségével lehetőségünk van egy oltási procedúrát adminisztrálni.
A pácienseknek van lehetőségük regisztrálni és oltásra időpontot foglalni.
A kezelő orvos be tudja jegyezni a páciensnek az oltást és lehetősége van új időpontot adnia a következő oltáshoz.

---

## Folyamat menete

A páciens regisztrál majd utánna időpontot foglal egy oltópontra.
Az orvos beadja a paciensnek az oltást függően, hogy a páciens milyen vakcinát választott. Az orvos jelzi a rendszer felé, hogy szükséges-e következő időpont. Ha igen akkor megadja az időpontot is és automatikusan végbemegy az előző vakcinával az időpont foglalás ugyanakkor bejegyzésre kerül az adott oltás.
Amennyiben nincs szükség következő időpontra úgy ezt jelzi az orvos és törlődik az aktuális időpont és nem módosul az oltás pedig bejegyzésre kerül.

---

## Swagger felület és Open API link

[Swagger UI](http://localhost:8080/swagger-ui.html)

[Open API](http://localhost:8080/v3/api-docs)

---

## Entitások

### Patient

A `Patient` entitás a következő attribútumokkal rendelkezik:

* `id` (A páciens egyedi azonosítója)
* `taj` (A páciens TAJ száma)
* `name` (A Páciens neve)
* `dateOfBirth` (A páciens születési ideje)
* `email` (A páciens E-mail címem)
* `vaccinationPointEvent` (A páciens által foglalt vagy az oltóorvos által adott oltási esemény bejegyzése)
* `vaccinateds` (A páciens kapott oltási bejegyzései)
* `lastVaccinationDate` (A páciens utolsó oltási időpontja)
* `doses` (A páciensnek az oltóorvos által adott vakcinák száma)
* `vaccineType` (A legutoljára kapott vakcina típusa.)

A következő végpontokon érjük el az entitást:

| Http metódus | Vég pont                   | Leírás                                 | Szerepkör      |
| ------------ | -------------------------- | -------------------------------------- | -------------- |
| GET          | `"/api/patients/"`         | lekérdezi az összes pácienst           | oltóorvos      |
| GET          | `"/api/patients/{id}"`     | lekérdez egy pácienst id alapján       | oltóorvos      |
| GET          | `"/api/patients/taj/{taj}"`| lekérdez egy pácienst taj szám alapján | oltóorvos      |
| POST         | `"/api/patients/"`         | létrehoz egy pácienst                  | páciens        |
| PUT          | `"/api/patients/{id}"`     | módosít egy pácienst id alapján        | adminisztrátor |
| DELETE       | `"/api/patients/{id}"`     | töröl egy pácienst id alapján          | adminisztrátor |
| DELETE       | `"/api/patients/"`         | törli az összes pácienst               | adminisztrátor |

A `Patient` entitás adatai az adatbázisban a `patients` táblában tárolódnak.

---

### Vaccination Point Event

A `Vaccinated` entitás a következő attribútumokkal rendelkezik:

* `id` (Az oltás egyedi azonosítója)
* `patient` (Az oltandó személy)
* `numberSeriesDoses` (Az oltási széria aktuális száma)
* `overallNumberDoses` (Az oltási széria maximális száma)
* `administered` (Az oltás helye)
* `vaccineType` (Az oltás típusa)
* `lot` (Az oltás gyári száma)

A `Patient` és a `VaccinationPointEvent` entitás között egyirányú `@OneToOne` kapcsolat van.

A következő végpontokon érjük el az entitást

| Http metódus | Vég pont                                             | Leírás                                             | Szerepkör      |
| ------------ | ---------------------------------------------------- | -------------------------------------------------- | -------------- |
| GET          | `"/api/vaccinationpointevents/"`                     | lekérdezi az összes eseményt                       | oltóorvos      |
| GET          | `"/api/vaccinationpointevents/{id}"`                 | lekérdez egy eseményt id alapján                   | oltóorvos      |
| POST         | `"/api/vaccinationpointevents/patients/{patientId}"` | létrehoz egy eseményt egy pácienshez hozzárendelve | páciens        |
| PUT          | `"/api/vaccinationpointevents/{id}"`                 | módosít egy eseményt id alapján                    | oltóorvos      |
| DELETE       | `"/api/vaccinationpointevents/{id}"`                 | töröl egy eseményt id alapján                      | oltóorvos      |
| DELETE       | `"/api/vaccinationpointevents/"`                     | törli az összes eseményt                           | adminisztrátor |

A `VaccinationPointEvent` entitás adatai az adatbázisban a `vaccination_point_events` táblában tárolódnak, mely egy külső
kulcsot tartalmaz az adott oltási eseményhez kapcsolódó páciens `id`-jára.

---

### Vaccinated

A `VaccinationPointEvent` entitás a következő attribútumokkal rendelkezik:

* `id` (Az oltási esemény egyedi azonosítója)
* `patient` (Az oltandó személy)
* `occasion` (Az oltási időpont (előjegyzés))
* `address` (Az oltópont címe)
* `vaccineType` (A beadandó oltás típusa)

A `Patient` és a `Vaccinated` entitás között egyirányú `@OneToMany`-`@ManyToOne` kapcsolat van.

A következő végpontokon érjük el az entitást

| Http metódus | Vég pont                                  | Leírás                                           | Szerepkör      |
| ------------ | ----------------------------------------- | ------------------------------------------------ | -------------- |
| GET          | `"/api/vaccinateds/"`                     | lekérdezi az összes oltást                       | oltóorvos      |
| GET          | `"/api/vaccinateds/{id}"`                 | lekérdez egy oltást id alapján                   | oltóorvos      |
| POST         | `"/api/vaccinateds/patients/{patientId}"` | létrehoz egy oltást egy pácienshez hozzárendelve | oltóorvos      |
| PUT          | `"/api/vaccinateds/{id}"`                 | módosít egy oltást id alapján                    | oltóorvos      |
| DELETE       | `"/api/vaccinateds/{id}"`                 | töröl egy oltást id alapján                      | oltóorvos      |
| DELETE       | `"/api/vaccinateds/"`                     | törli az összes oltást                           | adminisztrátor |

A `Vaccinated` entitás adatai az adatbázisban a `vaccinateds` táblában tárolódnak, mely egy külső
kulcsot tartalmaz az adott oltáshoz kapcsolódó páciens `id`-jára.

---

## Technológiai részletek

Ez egy klasszikus háromrétegű webes alkalmazás, controller, service és repository
réteggel, entitásonként a rétegeknek megfelelően elnevezett osztályokkal. A megvalósítás
Java programnyelven, Spring Boot használatával történt. Az alkalmazás HTTP kéréseket
képes fogadni, ezt a RESTful webszolgáltatások segítségével valósítja meg.
Adattárolásra SQL alapú MariaDB adatbázist használ, melyben a táblákat Flyway hozza létre.
Az adatbáziskezelés Spring Data JPA technológiával történik. A beérkező adatok validálását a
Spring Boot `spring-boot-starter-validation` modulja végzi, az általános hibakezelést pedig
a `problem-spring-web-starter` projekt.
Az alkalmazás tesztelésére RestTemplate-tel implementált integrációs
tesztek állnak rendelkezésre, a kipróbálásához pedig az `src/test/java` könyvtáron belül
HTTP fájlok, valamint egy részletesen feliratozott Swagger felület. A mellékelt `Dockerfile`
segítségével az alkalmazásból layerelt Docker image készíthető.

---
