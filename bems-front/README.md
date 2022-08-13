# bems-front

## Project Setup

### Setup config

Fill the `.env` file with your own configuration.

### Install dependencies

```sh
npm install
```

### Compile and Hot-Reload for Development

```sh
npm run dev
```

### Type-Check, Compile and Minify for Production

```sh
npm run build
```

## Run tests

Fill the `cypress.config.ts` file with the `bems-front`'s URL in `baseUrl`.

```sh
npm run cypress:run
```

__NB:__ `bems-back` API and `bems-front` must be running when the tests start.

## Lint with [ESLint](https://eslint.org/)

```sh
npm run lint
```
