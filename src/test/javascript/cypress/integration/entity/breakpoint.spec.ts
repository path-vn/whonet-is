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

    cy.get(`[data-cy="guidelines"]`)
      .type('Card generate feed', { force: true })
      .invoke('val')
      .should('match', new RegExp('Card generate feed'));

    cy.get(`[data-cy="year"]`).type('Handcrafted', { force: true }).invoke('val').should('match', new RegExp('Handcrafted'));

    cy.get(`[data-cy="testMethod"]`)
      .type('Towels needs-based', { force: true })
      .invoke('val')
      .should('match', new RegExp('Towels needs-based'));

    cy.get(`[data-cy="potency"]`)
      .type('invoice Coordinator Officer', { force: true })
      .invoke('val')
      .should('match', new RegExp('invoice Coordinator Officer'));

    cy.get(`[data-cy="organismCode"]`).type('Phased', { force: true }).invoke('val').should('match', new RegExp('Phased'));

    cy.get(`[data-cy="organismCodeType"]`).type('Avon', { force: true }).invoke('val').should('match', new RegExp('Avon'));

    cy.get(`[data-cy="breakpointType"]`)
      .type('mindshare input global', { force: true })
      .invoke('val')
      .should('match', new RegExp('mindshare input global'));

    cy.get(`[data-cy="host"]`)
      .type('Program Sleek didactic', { force: true })
      .invoke('val')
      .should('match', new RegExp('Program Sleek didactic'));

    cy.get(`[data-cy="siteOfInfection"]`)
      .type('Rubber Shoes Electronics', { force: true })
      .invoke('val')
      .should('match', new RegExp('Rubber Shoes Electronics'));

    cy.get(`[data-cy="referenceTable"]`).type('Officer', { force: true }).invoke('val').should('match', new RegExp('Officer'));

    cy.get(`[data-cy="referenceSequence"]`).type('SCSI', { force: true }).invoke('val').should('match', new RegExp('SCSI'));

    cy.get(`[data-cy="whonetAbxCode"]`).type('Tobago', { force: true }).invoke('val').should('match', new RegExp('Tobago'));

    cy.get(`[data-cy="whonetTest"]`)
      .type('solid capacitor compressing', { force: true })
      .invoke('val')
      .should('match', new RegExp('solid capacitor compressing'));

    cy.get(`[data-cy="r"]`)
      .type('copying connecting Switchable', { force: true })
      .invoke('val')
      .should('match', new RegExp('copying connecting Switchable'));

    cy.get(`[data-cy="i"]`).type('Borders', { force: true }).invoke('val').should('match', new RegExp('Borders'));

    cy.get(`[data-cy="sdd"]`)
      .type('portals analyzing Steel', { force: true })
      .invoke('val')
      .should('match', new RegExp('portals analyzing Steel'));

    cy.get(`[data-cy="s"]`)
      .type('synergize Handmade Research', { force: true })
      .invoke('val')
      .should('match', new RegExp('synergize Handmade Research'));

    cy.get(`[data-cy="ecvEcoff"]`)
      .type('Hat e-markets Profit-focused', { force: true })
      .invoke('val')
      .should('match', new RegExp('Hat e-markets Profit-focused'));

    cy.get(`[data-cy="dateEntered"]`)
      .type('Turnpike Computer', { force: true })
      .invoke('val')
      .should('match', new RegExp('Turnpike Computer'));

    cy.get(`[data-cy="dateModified"]`)
      .type('Loan deposit Franc', { force: true })
      .invoke('val')
      .should('match', new RegExp('Loan deposit Franc'));

    cy.get(`[data-cy="comments"]`).type('Administrator', { force: true }).invoke('val').should('match', new RegExp('Administrator'));

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
