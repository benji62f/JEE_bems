/// <reference types="cypress" />

describe("homepage", () => {
  beforeEach(() => {
    cy.visit("/");
  });

  it("root redirects to about page", () => {
    cy.location().should((location) => {
      expect(location.pathname).to.eq("/about");
    });
  });

  it("about page's button redirects to calendar", () => {
    cy.get(".v-btn__content").click();
    cy.location().should((location) => {
      expect(location.pathname).to.eq("/calendar");
    });
  });
});
