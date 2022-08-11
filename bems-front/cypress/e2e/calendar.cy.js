/// <reference types="cypress" />

describe("calendar", () => {
  beforeEach(() => {
    cy.intercept("GET", "/api/events*", { fixture: "events.json" });
    cy.intercept("POST", "/api/events/*/delete", {});
    cy.intercept("POST", "/api/events/*/edit", { fixture: "events-edit.json" });
    cy.visit("/calendar");

    // Go to August 2022
    const today = new Date();
    const numberOfMonths =
      (today.getFullYear() - 2022) * 12 + (today.getMonth() - 7);
    for (let i = 0; i < numberOfMonths; i++) {
      cy.get(
        ".col > :nth-child(1) > .v-sheet > .v-toolbar__content > :nth-child(2)"
      ).click();
    }
  });

  it("list events", () => {
    checkEvent(
      3,
      6,
      "Réunion Kumojin",
      "August 12th, 14:00 to August 12th, 14:30",
      "Démarrage projet client XYZ."
    );
    checkEvent(
      4,
      2,
      "Démo projet ABC",
      "August 15th, 15:00 to August 15th, 17:00",
      "Démo pour toutes les BU + planification du sprint suivant."
    );
    checkEvent(
      4,
      7,
      "Weekend à Bruxelles",
      "August 20th, 09:00 to August 21st, 23:00"
    );
    checkEvent(
      5,
      1,
      "Weekend à Bruxelles",
      "August 20th, 09:00 to August 21st, 23:00"
    );
    checkEvent(
      5,
      2,
      "Sprint #5",
      "August 22nd, 09:00 to August 26th, 17:00",
      "Projet EFG : géolocalisation utilisateur + personnalisation de l'expérience de navigation en conséquence."
    );
  });

  it("create event", () => {
    cy.get(".v-toolbar__content > :nth-child(6) > .v-btn__content").click();
    cy.get("#labelInput").type("Big party!");
    cy.get("#descriptionInput").type("Ou soirée TV, à voir.");
    cy.get("#startDateInput").type("2022-08-13T20:30");
    cy.get("#endDateInput").type("2022-08-14T06:00");
    cy.get("#btn-color-indigo").click();
    cy.get('[type="submit"]').click();
    checkEvent(
      3,
      7,
      "Big party!",
      "August 13th, 20:30 to August 14th, 06:00",
      "Ou soirée TV, à voir."
    );
  });

  it("delete event", () => {
    deleteEventAndCheckIfItWasDeleted(3, 6);
    deleteEventAndCheckIfItWasDeleted(4, 2);
    deleteEventAndCheckIfItWasDeleted(4, 7);
    deleteEventAndCheckIfItWasDeleted(5, 2);
  });

  it("edit event", () => {
    // Open event
    cy.get(
      `:nth-child(3) > :nth-child(6) > .v-event > .pl-1 > .v-event-summary`
    ).click();

    // Edit event
    cy.get(":nth-child(4) > .v-btn__content > .v-icon").click();
    cy.get("#labelInput").clear().type("Poisson d'avril");
    cy.get("#descriptionInput")
      .clear()
      .type("Ai-je vraiment besoin de décrire cela ?");
    cy.get("#startDateInput").type("2022-08-01T00:00");
    cy.get("#endDateInput").type("2022-08-01T23:59");
    cy.get("#btn-color-indigo").click();
    cy.get('[type="submit"]').click();

    checkEvent(
      2,
      2,
      "Poisson d'avril",
      "August 1st, 00:00 to August 1st, 23:59",
      "Ai-je vraiment besoin de décrire cela ?"
    );
  });
});

function checkEvent(row, column, title, dates, description) {
  // Open event
  cy.get(
    `:nth-child(${row}) > :nth-child(${column}) > .v-event > .pl-1 > .v-event-summary`
  ).click();

  // Check title
  cy.get(
    `:nth-child(${row}) > :nth-child(${column}) > .v-event > .pl-1 > .v-event-summary`
  ).contains(title);

  // Check dates
  cy.get(".v-card__text").contains(dates);

  // Check description
  if (description) cy.get(".v-card__text").contains(description);

  // Close event
  cy.get(".v-card > .v-card__actions > .v-btn").click();
}

function deleteEventAndCheckIfItWasDeleted(row, column) {
  // Open event
  cy.get(
    `:nth-child(${row}) > :nth-child(${column}) > .v-event > .pl-1 > .v-event-summary`
  ).click();

  // Delete event
  cy.get(":nth-child(5) > .v-btn__content > .v-icon").click();

  // Check if event was deleted
  cy.get(`:nth-child(${row}) > :nth-child(${column}) .v-event`).should(
    "not.exist"
  );
}
