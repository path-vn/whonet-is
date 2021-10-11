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

describe('Breakpoint e2e test', () => {
  let startingEntitiesCount = 0;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });

    cy.clearCookies();
    cy.intercept('GET', '/api/breakpoints*').as('entitiesRequest');
    cy.visit('');
    cy.login('admin', 'admin');
    cy.clickOnEntityMenuItem('breakpoint');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.visit('/');
  });

  it('should load Breakpoints', () => {
    cy.intercept('GET', '/api/breakpoints*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('breakpoint');
    cy.wait('@entitiesRequest');
    cy.getEntityHeading('Breakpoint').should('exist');
    if (startingEntitiesCount === 0) {
      cy.get(entityTableSelector).should('not.exist');
    } else {
      cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
    }
    cy.visit('/');
  });

  it('should load details Breakpoint page', () => {
    cy.intercept('GET', '/api/breakpoints*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('breakpoint');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityDetailsButtonSelector).first().click({ force: true });
      cy.getEntityDetailsHeading('breakpoint');
      cy.get(entityDetailsBackButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should load create Breakpoint page', () => {
    cy.intercept('GET', '/api/breakpoints*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('breakpoint');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Breakpoint');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.visit('/');
  });

  it('should load edit Breakpoint page', () => {
    cy.intercept('GET', '/api/breakpoints*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('breakpoint');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityEditButtonSelector).first().click({ force: true });
      cy.getEntityCreateUpdateHeading('Breakpoint');
      cy.get(entityCreateSaveButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should create an instance of Breakpoint', () => {
    cy.intercept('GET', '/api/breakpoints*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('breakpoint');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Breakpoint');

    cy.get(`[data-cy="path"]`).type('Card generate feed', { force: true }).invoke('val').should('match', new RegExp('Card generate feed'));

    cy.get(`[data-cy="query"]`).type('Handcrafted', { force: true }).invoke('val').should('match', new RegExp('Handcrafted'));

    cy.get(`[data-cy="antibioticQuery"]`)
      .type('Towels needs-based', { force: true })
      .invoke('val')
      .should('match', new RegExp('Towels needs-based'));

    cy.get(`[data-cy="organismQuery"]`)
      .type('invoice Coordinator Officer', { force: true })
      .invoke('val')
      .should('match', new RegExp('invoice Coordinator Officer'));

    cy.get(`[data-cy="intrinsicResistanceQuery"]`).type('Phased', { force: true }).invoke('val').should('match', new RegExp('Phased'));

    cy.setFieldImageAsBytesOfEntity('binaryData', 'integration-test.png', 'image/png');

    cy.get(entityCreateSaveButtonSelector).click({ force: true });
    cy.scrollTo('top', { ensureScrollable: false });
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.intercept('GET', '/api/breakpoints*').as('entitiesRequestAfterCreate');
    cy.visit('/');
    cy.clickOnEntityMenuItem('breakpoint');
    cy.wait('@entitiesRequestAfterCreate');
    cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount + 1);
    cy.visit('/');
  });

  it('should delete last instance of Breakpoint', () => {
    cy.intercept('GET', '/api/breakpoints*').as('entitiesRequest');
    cy.intercept('GET', '/api/breakpoints/*').as('dialogDeleteRequest');
    cy.intercept('DELETE', '/api/breakpoints/*').as('deleteEntityRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('breakpoint');
    cy.wait('@entitiesRequest').then(({ request, response }) => {
      startingEntitiesCount = response.body.length;
      if (startingEntitiesCount > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
        cy.get(entityDeleteButtonSelector).last().click({ force: true });
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('breakpoint').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest');
        cy.intercept('GET', '/api/breakpoints*').as('entitiesRequestAfterDelete');
        cy.visit('/');
        cy.clickOnEntityMenuItem('breakpoint');
        cy.wait('@entitiesRequestAfterDelete');
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount - 1);
      }
      cy.visit('/');
    });
  });
});
