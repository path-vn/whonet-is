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

describe('WhonetResource e2e test', () => {
  let startingEntitiesCount = 0;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });

    cy.clearCookies();
    cy.intercept('GET', '/api/whonet-resources*').as('entitiesRequest');
    cy.visit('');
    cy.login('admin', 'admin');
    cy.clickOnEntityMenuItem('whonet-resource');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.visit('/');
  });

  it('should load WhonetResources', () => {
    cy.intercept('GET', '/api/whonet-resources*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('whonet-resource');
    cy.wait('@entitiesRequest');
    cy.getEntityHeading('WhonetResource').should('exist');
    if (startingEntitiesCount === 0) {
      cy.get(entityTableSelector).should('not.exist');
    } else {
      cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
    }
    cy.visit('/');
  });

  it('should load details WhonetResource page', () => {
    cy.intercept('GET', '/api/whonet-resources*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('whonet-resource');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityDetailsButtonSelector).first().click({ force: true });
      cy.getEntityDetailsHeading('whonetResource');
      cy.get(entityDetailsBackButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should load create WhonetResource page', () => {
    cy.intercept('GET', '/api/whonet-resources*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('whonet-resource');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('WhonetResource');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.visit('/');
  });

  it('should load edit WhonetResource page', () => {
    cy.intercept('GET', '/api/whonet-resources*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('whonet-resource');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityEditButtonSelector).first().click({ force: true });
      cy.getEntityCreateUpdateHeading('WhonetResource');
      cy.get(entityCreateSaveButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should create an instance of WhonetResource', () => {
    cy.intercept('GET', '/api/whonet-resources*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('whonet-resource');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('WhonetResource');

    cy.get(`[data-cy="uploadDate"]`).type('2023-03-15T05:43').invoke('val').should('equal', '2023-03-15T05:43');

    cy.get(`[data-cy="antibiotic"]`)
      .type('Generic withdrawal', { force: true })
      .invoke('val')
      .should('match', new RegExp('Generic withdrawal'));

    cy.get(`[data-cy="organism"]`).type('bluetooth', { force: true }).invoke('val').should('match', new RegExp('bluetooth'));

    cy.get(`[data-cy="intrinsicResistance"]`)
      .type('Small reciprocal', { force: true })
      .invoke('val')
      .should('match', new RegExp('Small reciprocal'));

    cy.get(`[data-cy="expertRule"]`).type('up application', { force: true }).invoke('val').should('match', new RegExp('up application'));

    cy.get(`[data-cy="breakPoint"]`)
      .type('Savings auxiliary payment', { force: true })
      .invoke('val')
      .should('match', new RegExp('Savings auxiliary payment'));

    cy.get(`[data-cy="status"]`)
      .type('Peso Facilitator repurpose', { force: true })
      .invoke('val')
      .should('match', new RegExp('Peso Facilitator repurpose'));

    cy.get(`[data-cy="importedDate"]`).type('2023-03-14T22:50').invoke('val').should('equal', '2023-03-14T22:50');

    cy.get(`[data-cy="message"]`)
      .type('../fake-data/blob/hipster.txt', { force: true })
      .invoke('val')
      .should('match', new RegExp('../fake-data/blob/hipster.txt'));

    cy.get(entityCreateSaveButtonSelector).click({ force: true });
    cy.scrollTo('top', { ensureScrollable: false });
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.intercept('GET', '/api/whonet-resources*').as('entitiesRequestAfterCreate');
    cy.visit('/');
    cy.clickOnEntityMenuItem('whonet-resource');
    cy.wait('@entitiesRequestAfterCreate');
    cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount + 1);
    cy.visit('/');
  });

  it('should delete last instance of WhonetResource', () => {
    cy.intercept('GET', '/api/whonet-resources*').as('entitiesRequest');
    cy.intercept('GET', '/api/whonet-resources/*').as('dialogDeleteRequest');
    cy.intercept('DELETE', '/api/whonet-resources/*').as('deleteEntityRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('whonet-resource');
    cy.wait('@entitiesRequest').then(({ request, response }) => {
      startingEntitiesCount = response.body.length;
      if (startingEntitiesCount > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
        cy.get(entityDeleteButtonSelector).last().click({ force: true });
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('whonetResource').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest');
        cy.intercept('GET', '/api/whonet-resources*').as('entitiesRequestAfterDelete');
        cy.visit('/');
        cy.clickOnEntityMenuItem('whonet-resource');
        cy.wait('@entitiesRequestAfterDelete');
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount - 1);
      }
      cy.visit('/');
    });
  });
});
