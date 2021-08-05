swagger url : http://localhost:8080/docs/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config
api docs url : http://localhost:8080/v3/api-docs



| Http metódus | Vég pont                    | Leírás                           | Szerepkör      |
| ------------ | --------------------------- | -------------------------------- | -------------- |
| GET          | `"/api/patient/all"`        | lekérdezi az összes pácienst     | oltóorvos      |
| GET          | `"/api/patient/{id}"`       | lekérdez egy pácienst id alapján | oltóorvos      |
| POST         | `"/api/patient/"`           | létrehoz egy pácienst            | páciens        |
| PUT          | `"/api/patient/{id}"`       | módosít egy pácienst id alapján  | adminisztrátor |
| DELETE       | `"/api/patient/{id}"`       | töröl egy pácienst id alapján    | adminisztrátor |
| DELETE       | `"/api/patient/delete/all"` | törli az összes pácienst         | adminisztrátor |


| Http metódus | Vég pont                                | Leírás                                           | Szerepkör      |
| ------------ | --------------------------------------- | ------------------------------------------------ | -------------- |
| GET          | `"/api/vaccinated/all"`                 | lekérdezi az összes oltást                       | oltóorvos      |
| GET          | `"/api/vaccinated/{id}"`                | lekérdez egy oltást id alapján                   | oltóorvos      |
| POST         | `"/api/vaccinated/patient/{patientId}"` | létrehoz egy oltást egy pácienshez hozzárendelve | oltóorvos      |
| PUT          | `"/api/vaccinated/{id}"`                | módosít egy oltást id alapján                    | oltóorvos      |
| DELETE       | `"/api/vaccinated/{id}"`                | töröl egy oltást id alapján                      | oltóorvos      |
| DELETE       | `"/api/vaccinated/delete/all"`          | törli az összes oltást                           | adminisztrátor |

| Http metódus | Vég pont                                           | Leírás                                             | Szerepkör      |
| ------------ | -------------------------------------------------- | -------------------------------------------------- | -------------- |
| GET          | `"/api/vaccinationpointevent/all"`                 | lekérdezi az összes eseményt                       | oltóorvos      |
| GET          | `"/api/vaccinationpointevent/{id}"`                | lekérdez egy eseményt id alapján                   | oltóorvos      |
| POST         | `"/api/vaccinationpointevent/patient/{patientId}"` | létrehoz egy eseményt egy pácienshez hozzárendelve | páciens        |
| PUT          | `"/api/vaccinationpointevent/{id}"`                | módosít egy eseményt id alapján                    | oltóorvos      |
| DELETE       | `"/api/vaccinationpointevent/{id}"`                | töröl egy eseményt id alapján                      | oltóorvos      |
| DELETE       | `"/api/vaccinationpointevent/delete/all"`          | törli az összes eseményt                           | adminisztrátor |

