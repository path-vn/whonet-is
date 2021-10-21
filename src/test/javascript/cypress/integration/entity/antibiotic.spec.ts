import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('Antibiotic e2e test', () => {
  let startingEntitiesCount = 0;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });

    cy.clearCookies();
    cy.intercept('GET', '/api/antibiotics*').as('entitiesRequest');
    cy.visit('');
    cy.login('admin', 'admin');
    cy.clickOnEntityMenuItem('antibiotic');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.visit('/');
  });

  it('should load Antibiotics', () => {
    cy.intercept('GET', '/api/antibiotics*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('antibiotic');
    cy.wait('@entitiesRequest');
    cy.getEntityHeading('Antibiotic').should('exist');
    if (startingEntitiesCount === 0) {
      cy.get(entityTableSelector).should('not.exist');
    } else {
      cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
    }
    cy.visit('/');
  });

  it('should load details Antibiotic page', () => {
    cy.intercept('GET', '/api/antibiotics*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('antibiotic');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityDetailsButtonSelector).first().click({ force: true });
      cy.getEntityDetailsHeading('antibiotic');
      cy.get(entityDetailsBackButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should load create Antibiotic page', () => {
    cy.intercept('GET', '/api/antibiotics*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('antibiotic');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Antibiotic');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.visit('/');
  });

  it('should load edit Antibiotic page', () => {
    cy.intercept('GET', '/api/antibiotics*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('antibiotic');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityEditButtonSelector).first().click({ force: true });
      cy.getEntityCreateUpdateHeading('Antibiotic');
      cy.get(entityCreateSaveButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should create an instance of Antibiotic', () => {
    cy.intercept('GET', '/api/antibiotics*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('antibiotic');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Antibiotic');

    cy.get(`[data-cy="whonetAbxCode"]`)
      .type('Towels Legacy Madagascar', { force: true })
      .invoke('val')
      .should('match', new RegExp('Towels Legacy Madagascar'));

    cy.get(`[data-cy="whoCode"]`)
      .type('Fields navigate frictionless', { force: true })
      .invoke('val')
      .should('match', new RegExp('Fields navigate frictionless'));

    cy.get(`[data-cy="dinCode"]`)
      .type('Granite Universal Shirt', { force: true })
      .invoke('val')
      .should('match', new RegExp('Granite Universal Shirt'));

    cy.get(`[data-cy="jacCode"]`)
      .type('clear-thinking Massachusetts program', { force: true })
      .invoke('val')
      .should('match', new RegExp('clear-thinking Massachusetts program'));

    cy.get(`[data-cy="eucastCode"]`)
      .type('Stravenue Architect', { force: true })
      .invoke('val')
      .should('match', new RegExp('Stravenue Architect'));

    cy.get(`[data-cy="userCode"]`)
      .type('digital Future digital', { force: true })
      .invoke('val')
      .should('match', new RegExp('digital Future digital'));

    cy.get(`[data-cy="antibiotic"]`).type('Dynamic Hat', { force: true }).invoke('val').should('match', new RegExp('Dynamic Hat'));

    cy.get(`[data-cy="guidelines"]`).type('Shoes', { force: true }).invoke('val').should('match', new RegExp('Shoes'));

    cy.get(`[data-cy="antiboticClass"]`).type('blockchains', { force: true }).invoke('val').should('match', new RegExp('blockchains'));

    cy.get(`[data-cy="clsi"]`)
      .type('Cotton Implementation solution', { force: true })
      .invoke('val')
      .should('match', new RegExp('Cotton Implementation solution'));

    cy.get(`[data-cy="eucast"]`).type('Towels Gambia', { force: true }).invoke('val').should('match', new RegExp('Towels Gambia'));

    cy.get(`[data-cy="sfm"]`)
      .type('Cambridgeshire Designer', { force: true })
      .invoke('val')
      .should('match', new RegExp('Cambridgeshire Designer'));

    cy.get(`[data-cy="srga"]`).type('primary', { force: true }).invoke('val').should('match', new RegExp('primary'));

    cy.get(`[data-cy="bsac"]`).type('virtual', { force: true }).invoke('val').should('match', new RegExp('virtual'));

    cy.get(`[data-cy="din"]`).type('port Minnesota plum', { force: true }).invoke('val').should('match', new RegExp('port Minnesota plum'));

    cy.get(`[data-cy="neo"]`).type('Program', { force: true }).invoke('val').should('match', new RegExp('Program'));

    cy.get(`[data-cy="afa"]`).type('Loan', { force: true }).invoke('val').should('match', new RegExp('Loan'));

    cy.get(`[data-cy="abxNumber"]`).type('Panama Iowa', { force: true }).invoke('val').should('match', new RegExp('Panama Iowa'));

    cy.get(`[data-cy="potency"]`)
      .type('hacking turquoise Synergistic', { force: true })
      .invoke('val')
      .should('match', new RegExp('hacking turquoise Synergistic'));

    cy.get(`[data-cy="atcCode"]`)
      .type('Tasty analyzer Small', { force: true })
      .invoke('val')
      .should('match', new RegExp('Tasty analyzer Small'));

    cy.get(`[data-cy="profClass"]`).type('Health', { force: true }).invoke('val').should('match', new RegExp('Health'));

    cy.get(`[data-cy="ciaCategory"]`).type('firewall', { force: true }).invoke('val').should('match', new RegExp('firewall'));

    cy.get(`[data-cy="clsiOrder"]`)
      .type('Toys National Branding', { force: true })
      .invoke('val')
      .should('match', new RegExp('Toys National Branding'));

    cy.get(`[data-cy="eucastOrder"]`)
      .type('Plains encompassing green', { force: true })
      .invoke('val')
      .should('match', new RegExp('Plains encompassing green'));

    cy.get(`[data-cy="human"]`).type('Market', { force: true }).invoke('val').should('match', new RegExp('Market'));

    cy.get(`[data-cy="veterinary"]`).type('Toys', { force: true }).invoke('val').should('match', new RegExp('Toys'));

    cy.get(`[data-cy="animalGp"]`).type('generate', { force: true }).invoke('val').should('match', new RegExp('generate'));

    cy.get(`[data-cy="loinccomp"]`).type('Estonia', { force: true }).invoke('val').should('match', new RegExp('Estonia'));

    cy.get(`[data-cy="loincgen"]`).type('database', { force: true }).invoke('val').should('match', new RegExp('database'));

    cy.get(`[data-cy="loincdisk"]`).type('synthesizing', { force: true }).invoke('val').should('match', new RegExp('synthesizing'));

    cy.get(`[data-cy="loincmic"]`)
      .type('Fantastic American Loan', { force: true })
      .invoke('val')
      .should('match', new RegExp('Fantastic American Loan'));

    cy.get(`[data-cy="loincetest"]`)
      .type('Pants Kingdom Bacon', { force: true })
      .invoke('val')
      .should('match', new RegExp('Pants Kingdom Bacon'));

    cy.get(`[data-cy="loincslow"]`)
      .type('Fantastic Research hack', { force: true })
      .invoke('val')
      .should('match', new RegExp('Fantastic Research hack'));

    cy.get(`[data-cy="loincafb"]`).type('cohesive', { force: true }).invoke('val').should('match', new RegExp('cohesive'));

    cy.get(`[data-cy="loincsbt"]`).type('Unbranded', { force: true }).invoke('val').should('match', new RegExp('Unbranded'));

    cy.get(`[data-cy="loincmlc"]`).type('partnerships', { force: true }).invoke('val').should('match', new RegExp('partnerships'));

    cy.get(`[data-cy="dateEntered"]`)
      .type('up Connecticut Korean', { force: true })
      .invoke('val')
      .should('match', new RegExp('up Connecticut Korean'));

    cy.get(`[data-cy="dateModified"]`).type('Lead', { force: true }).invoke('val').should('match', new RegExp('Lead'));

    cy.get(`[data-cy="comments"]`).type('Saint Intranet', { force: true }).invoke('val').should('match', new RegExp('Saint Intranet'));

    cy.get(entityCreateSaveButtonSelector).click({ force: true });
    cy.scrollTo('top', { ensureScrollable: false });
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.intercept('GET', '/api/antibiotics*').as('entitiesRequestAfterCreate');
    cy.visit('/');
    cy.clickOnEntityMenuItem('antibiotic');
    cy.wait('@entitiesRequestAfterCreate');
    cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount + 1);
    cy.visit('/');
  });

  it('should delete last instance of Antibiotic', () => {
    cy.intercept('GET', '/api/antibiotics*').as('entitiesRequest');
    cy.intercept('GET', '/api/antibiotics/*').as('dialogDeleteRequest');
    cy.intercept('DELETE', '/api/antibiotics/*').as('deleteEntityRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('antibiotic');
    cy.wait('@entitiesRequest').then(({ request, response }) => {
      startingEntitiesCount = response.body.length;
      if (startingEntitiesCount > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
        cy.get(entityDeleteButtonSelector).last().click({ force: true });
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('antibiotic').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest');
        cy.intercept('GET', '/api/antibiotics*').as('entitiesRequestAfterDelete');
        cy.visit('/');
        cy.clickOnEntityMenuItem('antibiotic');
        cy.wait('@entitiesRequestAfterDelete');
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount - 1);
      }
      cy.visit('/');
    });
  });
});
