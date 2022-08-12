# bems-back

## Project Setup

### Setup config

Fill the `src/main/resources/application.properties` file with your own configuration.

__NB:__ put the `bems-front` host's url in `cors.urls` to authorize it on the API.

### Install dependencies

```sh
mvn clean install
```

### Compile and Start for Development

```sh
mvn spring-boot:run
```

### Run tests

```sh
mvn test
```

## Available endpoints

### GET `/api/events`

Parameters:
- `start`: `string`
- `end`: `string`

Request sample:

```
/api/events?start=2022-08-01T00:00:00.000%2B02%3A00&end=2022-08-31T23:59:59.999%2B02%3A00
```

Response:

| HTTP code | Message     |
|-----------|-------------|
| 200       | OK          |
| 400       | Bad Request |

Response body:

```
{
    {
        "id": 2,
        "label": "Sprint #5",
        "description": "Projet EFG : géolocalisation utilisateur + personnalisation de l'expérience de navigation en conséquence.",
        "startDate": "2022-08-22T07:00:00.000+00:00",
        "endDate": "2022-08-26T15:00:00.000+00:00",
        "color": "grey darken-1"
    },
    {
        "id": 3,
        "label": "Démo projet ABC",
        "description": "Démo pour toutes les BU + planification du sprint suivant.",
        "startDate": "2022-08-15T13:00:00.000+00:00",
        "endDate": "2022-08-15T15:00:00.000+00:00",
        "color": "orange"
    }
}
```

---

### GET `/api/events/{id}`

Response:

| HTTP code | Message     |
|-----------|-------------|
| 200       | OK          |
| 404       | Not Found   |

---

### POST `/api/events`

Request body:

```json
{
    "label": "Sprint #5",
    "description": "Projet EFG : géolocalisation utilisateur + personnalisation de l'expérience de navigation en conséquence.",
    "startDate": "2022-08-22T07:00:00.000+00:00",
    "endDate": "2022-08-26T15:00:00.000+00:00",
    "color": "grey darken-1"
}
```

Response:

| HTTP code | Message     |
|-----------|-------------|
| 201       | Created     |
| 400       | Bad Request |

Response body:

```json
{
    "id": "2",
    "label": "Sprint #5",
    "description": "Projet EFG : géolocalisation utilisateur + personnalisation de l'expérience de navigation en conséquence.",
    "startDate": "2022-08-22T07:00:00.000+00:00",
    "endDate": "2022-08-26T15:00:00.000+00:00",
    "color": "grey darken-1"
}
```

---

### POST `/api/events/{id}/delete`

Response:

| HTTP code | Message     |
|-----------|-------------|
| 200       | OK          |
| 404       | Not Found   |

---

### POST `/api/events/{id}/edit`

Request body:

```json
{
    "id": "2",
    "label": "Sprint #5",
    "description": "Projet EFG : géolocalisation utilisateur + personnalisation de l'expérience de navigation en conséquence.",
    "startDate": "2022-08-22T07:00:00.000+00:00",
    "endDate": "2022-08-26T15:00:00.000+00:00",
    "color": "grey darken-1"
}
```

Response:

| HTTP code | Message     |
|-----------|-------------|
| 200       | OK          |
| 400       | Bad Request |
| 404       | Not Found   |

Response body:

```json
{
    "id": "2",
    "label": "Sprint #5",
    "description": "Projet EFG : géolocalisation utilisateur + personnalisation de l'expérience de navigation en conséquence.",
    "startDate": "2022-08-22T07:00:00.000+00:00",
    "endDate": "2022-08-26T15:00:00.000+00:00",
    "color": "grey darken-1"
}
```
