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

describe('Execute e2e test', () => {
  let startingEntitiesCount = 0;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });

    cy.clearCookies();
    cy.intercept('GET', '/api/executes*').as('entitiesRequest');
    cy.visit('');
    cy.login('admin', 'admin');
    cy.clickOnEntityMenuItem('execute');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.visit('/');
  });

  it('should load Executes', () => {
    cy.intercept('GET', '/api/executes*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('execute');
    cy.wait('@entitiesRequest');
    cy.getEntityHeading('Execute').should('exist');
    if (startingEntitiesCount === 0) {
      cy.get(entityTableSelector).should('not.exist');
    } else {
      cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
    }
    cy.visit('/');
  });

  it('should load details Execute page', () => {
    cy.intercept('GET', '/api/executes*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('execute');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityDetailsButtonSelector).first().click({ force: true });
      cy.getEntityDetailsHeading('execute');
      cy.get(entityDetailsBackButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should load create Execute page', () => {
    cy.intercept('GET', '/api/executes*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('execute');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Execute');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.visit('/');
  });

  it('should load edit Execute page', () => {
    cy.intercept('GET', '/api/executes*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('execute');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityEditButtonSelector).first().click({ force: true });
      cy.getEntityCreateUpdateHeading('Execute');
      cy.get(entityCreateSaveButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should create an instance of Execute', () => {
    cy.intercept('GET', '/api/executes*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('execute');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Execute');

    cy.get(`[data-cy="request"]`)
      .type('mobile Movies utilize', { force: true })
      .invoke('val')
      .should('match', new RegExp('mobile Movies utilize'));

    cy.get(`[data-cy="response"]`)
      .type('Dale invoice enable', { force: true })
      .invoke('val')
      .should('match', new RegExp('Dale invoice enable'));

    cy.get(`[data-cy="startedAt"]`).type('2021-12-13T09:27').invoke('val').should('equal', '2021-12-13T09:27');

    cy.get(`[data-cy="time"]`).type('23959').should('have.value', '23959');

    cy.get(entityCreateSaveButtonSelector).click({ force: true });
    cy.scrollTo('top', { ensureScrollable: false });
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.intercept('GET', '/api/executes*').as('entitiesRequestAfterCreate');
    cy.visit('/');
    cy.clickOnEntityMenuItem('execute');
    cy.wait('@entitiesRequestAfterCreate');
    cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount + 1);
    cy.visit('/');
  });

  it('should delete last instance of Execute', () => {
    cy.intercept('GET', '/api/executes*').as('entitiesRequest');
    cy.intercept('GET', '/api/executes/*').as('dialogDeleteRequest');
    cy.intercept('DELETE', '/api/executes/*').as('deleteEntityRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('execute');
    cy.wait('@entitiesRequest').then(({ request, response }) => {
      startingEntitiesCount = response.body.length;
      if (startingEntitiesCount > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
        cy.get(entityDeleteButtonSelector).last().click({ force: true });
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('execute').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest');
        cy.intercept('GET', '/api/executes*').as('entitiesRequestAfterDelete');
        cy.visit('/');
        cy.clickOnEntityMenuItem('execute');
        cy.wait('@entitiesRequestAfterDelete');
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount - 1);
      }
      cy.visit('/');
    });
  });
});
