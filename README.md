# Vizsgaremek - Vaccination point administered system

Ezen alkalmazás segítségével lehetőségünk van egy oltási procedúrát adminisztrálni.

A pácienseknek van lehetőségük regisztrálni és oltásra időpontot foglalni.

A kezelő orvos be tudja jegyezni a páciensnek az oltást és lehetősége van új időpontot adnia a következő oltáshoz.

## Használata

[Swagger UI](http://localhost:8080/swagger-ui.html)

[Open API](http://localhost:8080/v3/api-docs)

### Patient

A következő végpontokon érjük el az entitást

| Http metódus | Vég pont                   | Leírás                                 | Szerepkör      |
| ------------ | -------------------------- | -------------------------------------- | -------------- |
| GET          | `"/api/patients/"`         | lekérdezi az összes pácienst           | oltóorvos      |
| GET          | `"/api/patients/{id}"`     | lekérdez egy pácienst id alapján       | oltóorvos      |
| GET          | `"/api/patients/taj/{taj}"`| lekérdez egy pácienst taj szám alapján | oltóorvos      |
| POST         | `"/api/patients/"`         | létrehoz egy pácienst                  | páciens        |
| PUT          | `"/api/patients/{id}"`     | módosít egy pácienst id alapján        | adminisztrátor |
| DELETE       | `"/api/patients/{id}"`     | töröl egy pácienst id alapján          | adminisztrátor |
| DELETE       | `"/api/patients/"`         | törli az összes pácienst               | adminisztrátor |

### Vaccinated

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

### Vaccination Point Event

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
