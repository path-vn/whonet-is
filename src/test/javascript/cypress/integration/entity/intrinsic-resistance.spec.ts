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

describe('IntrinsicResistance e2e test', () => {
  let startingEntitiesCount = 0;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });

    cy.clearCookies();
    cy.intercept('GET', '/api/intrinsic-resistances*').as('entitiesRequest');
    cy.visit('');
    cy.login('admin', 'admin');
    cy.clickOnEntityMenuItem('intrinsic-resistance');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.visit('/');
  });

  it('should load IntrinsicResistances', () => {
    cy.intercept('GET', '/api/intrinsic-resistances*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('intrinsic-resistance');
    cy.wait('@entitiesRequest');
    cy.getEntityHeading('IntrinsicResistance').should('exist');
    if (startingEntitiesCount === 0) {
      cy.get(entityTableSelector).should('not.exist');
    } else {
      cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
    }
    cy.visit('/');
  });

  it('should load details IntrinsicResistance page', () => {
    cy.intercept('GET', '/api/intrinsic-resistances*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('intrinsic-resistance');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityDetailsButtonSelector).first().click({ force: true });
      cy.getEntityDetailsHeading('intrinsicResistance');
      cy.get(entityDetailsBackButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should load create IntrinsicResistance page', () => {
    cy.intercept('GET', '/api/intrinsic-resistances*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('intrinsic-resistance');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('IntrinsicResistance');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.visit('/');
  });

  it('should load edit IntrinsicResistance page', () => {
    cy.intercept('GET', '/api/intrinsic-resistances*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('intrinsic-resistance');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityEditButtonSelector).first().click({ force: true });
      cy.getEntityCreateUpdateHeading('IntrinsicResistance');
      cy.get(entityCreateSaveButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should create an instance of IntrinsicResistance', () => {
    cy.intercept('GET', '/api/intrinsic-resistances*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('intrinsic-resistance');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('IntrinsicResistance');

    cy.get(`[data-cy="guideline"]`)
      .type('moratorium Refined Place', { force: true })
      .invoke('val')
      .should('match', new RegExp('moratorium Refined Place'));

    cy.get(`[data-cy="referenceTable"]`).type('Metrics', { force: true }).invoke('val').should('match', new RegExp('Metrics'));

    cy.get(`[data-cy="organismCode"]`).type('Indiana', { force: true }).invoke('val').should('match', new RegExp('Indiana'));

    cy.get(`[data-cy="organismCodeType"]`)
      .type('Jewelery Berkshire', { force: true })
      .invoke('val')
      .should('match', new RegExp('Jewelery Berkshire'));

    cy.get(`[data-cy="exceptionOrganismCode"]`).type('olive', { force: true }).invoke('val').should('match', new RegExp('olive'));

    cy.get(`[data-cy="exceptionOrganismCodeType"]`)
      .type('Vermont Dynamic generating', { force: true })
      .invoke('val')
      .should('match', new RegExp('Vermont Dynamic generating'));

    cy.get(`[data-cy="abxCode"]`)
      .type('Planner generating', { force: true })
      .invoke('val')
      .should('match', new RegExp('Planner generating'));

    cy.get(`[data-cy="abxCodeType"]`)
      .type('Chief projection', { force: true })
      .invoke('val')
      .should('match', new RegExp('Chief projection'));

    cy.get(`[data-cy="antibioticExceptions"]`)
      .type('Bedfordshire', { force: true })
      .invoke('val')
      .should('match', new RegExp('Bedfordshire'));

    cy.get(`[data-cy="dateEntered"]`).type('Account payment', { force: true }).invoke('val').should('match', new RegExp('Account payment'));

    cy.get(`[data-cy="dateModified"]`)
      .type('Future Dynamic Assistant', { force: true })
      .invoke('val')
      .should('match', new RegExp('Future Dynamic Assistant'));

    cy.get(`[data-cy="comments"]`)
      .type('1080p quantifying Sleek', { force: true })
      .invoke('val')
      .should('match', new RegExp('1080p quantifying Sleek'));

    cy.get(entityCreateSaveButtonSelector).click({ force: true });
    cy.scrollTo('top', { ensureScrollable: false });
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.intercept('GET', '/api/intrinsic-resistances*').as('entitiesRequestAfterCreate');
    cy.visit('/');
    cy.clickOnEntityMenuItem('intrinsic-resistance');
    cy.wait('@entitiesRequestAfterCreate');
    cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount + 1);
    cy.visit('/');
  });

  it('should delete last instance of IntrinsicResistance', () => {
    cy.intercept('GET', '/api/intrinsic-resistances*').as('entitiesRequest');
    cy.intercept('GET', '/api/intrinsic-resistances/*').as('dialogDeleteRequest');
    cy.intercept('DELETE', '/api/intrinsic-resistances/*').as('deleteEntityRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('intrinsic-resistance');
    cy.wait('@entitiesRequest').then(({ request, response }) => {
      startingEntitiesCount = response.body.length;
      if (startingEntitiesCount > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
        cy.get(entityDeleteButtonSelector).last().click({ force: true });
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('intrinsicResistance').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest');
        cy.intercept('GET', '/api/intrinsic-resistances*').as('entitiesRequestAfterDelete');
        cy.visit('/');
        cy.clickOnEntityMenuItem('intrinsic-resistance');
        cy.wait('@entitiesRequestAfterDelete');
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount - 1);
      }
      cy.visit('/');
    });
  });
});
