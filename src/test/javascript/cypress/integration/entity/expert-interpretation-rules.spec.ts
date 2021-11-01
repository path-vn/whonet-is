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

describe('ExpertInterpretationRules e2e test', () => {
  let startingEntitiesCount = 0;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });

    cy.clearCookies();
    cy.intercept('GET', '/api/expert-interpretation-rules*').as('entitiesRequest');
    cy.visit('');
    cy.login('admin', 'admin');
    cy.clickOnEntityMenuItem('expert-interpretation-rules');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.visit('/');
  });

  it('should load ExpertInterpretationRules', () => {
    cy.intercept('GET', '/api/expert-interpretation-rules*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('expert-interpretation-rules');
    cy.wait('@entitiesRequest');
    cy.getEntityHeading('ExpertInterpretationRules').should('exist');
    if (startingEntitiesCount === 0) {
      cy.get(entityTableSelector).should('not.exist');
    } else {
      cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
    }
    cy.visit('/');
  });

  it('should load details ExpertInterpretationRules page', () => {
    cy.intercept('GET', '/api/expert-interpretation-rules*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('expert-interpretation-rules');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityDetailsButtonSelector).first().click({ force: true });
      cy.getEntityDetailsHeading('expertInterpretationRules');
      cy.get(entityDetailsBackButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should load create ExpertInterpretationRules page', () => {
    cy.intercept('GET', '/api/expert-interpretation-rules*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('expert-interpretation-rules');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('ExpertInterpretationRules');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.visit('/');
  });

  it('should load edit ExpertInterpretationRules page', () => {
    cy.intercept('GET', '/api/expert-interpretation-rules*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('expert-interpretation-rules');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityEditButtonSelector).first().click({ force: true });
      cy.getEntityCreateUpdateHeading('ExpertInterpretationRules');
      cy.get(entityCreateSaveButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should create an instance of ExpertInterpretationRules', () => {
    cy.intercept('GET', '/api/expert-interpretation-rules*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('expert-interpretation-rules');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('ExpertInterpretationRules');

    cy.get(`[data-cy="ruleCode"]`).type('CFP Account Cape', { force: true }).invoke('val').should('match', new RegExp('CFP Account Cape'));

    cy.get(`[data-cy="description"]`).type('Granite Home', { force: true }).invoke('val').should('match', new RegExp('Granite Home'));

    cy.get(`[data-cy="organismCode"]`).type('Loan', { force: true }).invoke('val').should('match', new RegExp('Loan'));

    cy.get(`[data-cy="organismCodeType"]`)
      .type('synthesize Kiribati', { force: true })
      .invoke('val')
      .should('match', new RegExp('synthesize Kiribati'));

    cy.get(`[data-cy="ruleCriteria"]`)
      .type('Canyon Quetzal silver', { force: true })
      .invoke('val')
      .should('match', new RegExp('Canyon Quetzal silver'));

    cy.get(`[data-cy="affectedAntibiotics"]`)
      .type('morph Handmade', { force: true })
      .invoke('val')
      .should('match', new RegExp('morph Handmade'));

    cy.get(`[data-cy="antibioticExceptions"]`)
      .type('Jewelery Metal bandwidth', { force: true })
      .invoke('val')
      .should('match', new RegExp('Jewelery Metal bandwidth'));

    cy.get(`[data-cy="result"]`)
      .type('Nicaragua synthesize', { force: true })
      .invoke('val')
      .should('match', new RegExp('Nicaragua synthesize'));

    cy.get(entityCreateSaveButtonSelector).click({ force: true });
    cy.scrollTo('top', { ensureScrollable: false });
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.intercept('GET', '/api/expert-interpretation-rules*').as('entitiesRequestAfterCreate');
    cy.visit('/');
    cy.clickOnEntityMenuItem('expert-interpretation-rules');
    cy.wait('@entitiesRequestAfterCreate');
    cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount + 1);
    cy.visit('/');
  });

  it('should delete last instance of ExpertInterpretationRules', () => {
    cy.intercept('GET', '/api/expert-interpretation-rules*').as('entitiesRequest');
    cy.intercept('GET', '/api/expert-interpretation-rules/*').as('dialogDeleteRequest');
    cy.intercept('DELETE', '/api/expert-interpretation-rules/*').as('deleteEntityRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('expert-interpretation-rules');
    cy.wait('@entitiesRequest').then(({ request, response }) => {
      startingEntitiesCount = response.body.length;
      if (startingEntitiesCount > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
        cy.get(entityDeleteButtonSelector).last().click({ force: true });
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('expertInterpretationRules').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest');
        cy.intercept('GET', '/api/expert-interpretation-rules*').as('entitiesRequestAfterDelete');
        cy.visit('/');
        cy.clickOnEntityMenuItem('expert-interpretation-rules');
        cy.wait('@entitiesRequestAfterDelete');
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount - 1);
      }
      cy.visit('/');
    });
  });
});
