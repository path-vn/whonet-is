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

describe('Organism e2e test', () => {
  let startingEntitiesCount = 0;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });

    cy.clearCookies();
    cy.intercept('GET', '/api/organisms*').as('entitiesRequest');
    cy.visit('');
    cy.login('admin', 'admin');
    cy.clickOnEntityMenuItem('organism');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.visit('/');
  });

  it('should load Organisms', () => {
    cy.intercept('GET', '/api/organisms*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('organism');
    cy.wait('@entitiesRequest');
    cy.getEntityHeading('Organism').should('exist');
    if (startingEntitiesCount === 0) {
      cy.get(entityTableSelector).should('not.exist');
    } else {
      cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
    }
    cy.visit('/');
  });

  it('should load details Organism page', () => {
    cy.intercept('GET', '/api/organisms*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('organism');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityDetailsButtonSelector).first().click({ force: true });
      cy.getEntityDetailsHeading('organism');
      cy.get(entityDetailsBackButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should load create Organism page', () => {
    cy.intercept('GET', '/api/organisms*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('organism');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Organism');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.visit('/');
  });

  it('should load edit Organism page', () => {
    cy.intercept('GET', '/api/organisms*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('organism');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityEditButtonSelector).first().click({ force: true });
      cy.getEntityCreateUpdateHeading('Organism');
      cy.get(entityCreateSaveButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should create an instance of Organism', () => {
    cy.intercept('GET', '/api/organisms*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('organism');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Organism');

    cy.get(`[data-cy="whonetOrgCode"]`)
      .type('Malaysian systems throughput', { force: true })
      .invoke('val')
      .should('match', new RegExp('Malaysian systems throughput'));

    cy.get(`[data-cy="organism"]`).type('Metal', { force: true }).invoke('val').should('match', new RegExp('Metal'));

    cy.get(`[data-cy="taxonomicStatus"]`).type('Outdoors Mall', { force: true }).invoke('val').should('match', new RegExp('Outdoors Mall'));

    cy.get(`[data-cy="common"]`).type('pixel', { force: true }).invoke('val').should('match', new RegExp('pixel'));

    cy.get(`[data-cy="organismType"]`).type('Belarussian', { force: true }).invoke('val').should('match', new RegExp('Belarussian'));

    cy.get(`[data-cy="anaerobe"]`)
      .type('mindshare world-class eyeballs', { force: true })
      .invoke('val')
      .should('match', new RegExp('mindshare world-class eyeballs'));

    cy.get(`[data-cy="morphology"]`)
      .type('Metal Internal copying', { force: true })
      .invoke('val')
      .should('match', new RegExp('Metal Internal copying'));

    cy.get(`[data-cy="subkingdomCode"]`).type('circuit', { force: true }).invoke('val').should('match', new RegExp('circuit'));

    cy.get(`[data-cy="familyCode"]`)
      .type('hacking payment transmitting', { force: true })
      .invoke('val')
      .should('match', new RegExp('hacking payment transmitting'));

    cy.get(`[data-cy="genusGroup"]`).type('Bacon', { force: true }).invoke('val').should('match', new RegExp('Bacon'));

    cy.get(`[data-cy="genusCode"]`).type('killer Small', { force: true }).invoke('val').should('match', new RegExp('killer Small'));

    cy.get(`[data-cy="speciesGroup"]`).type('Alaska Georgia', { force: true }).invoke('val').should('match', new RegExp('Alaska Georgia'));

    cy.get(`[data-cy="serovarGroup"]`).type('deposit Senior', { force: true }).invoke('val').should('match', new RegExp('deposit Senior'));

    cy.get(`[data-cy="msfGrpClin"]`)
      .type('Myanmar card Texas', { force: true })
      .invoke('val')
      .should('match', new RegExp('Myanmar card Texas'));

    cy.get(`[data-cy="sctCode"]`)
      .type('Computer Garden Function-based', { force: true })
      .invoke('val')
      .should('match', new RegExp('Computer Garden Function-based'));

    cy.get(`[data-cy="sctText"]`).type('Fresh Associate', { force: true }).invoke('val').should('match', new RegExp('Fresh Associate'));

    cy.get(`[data-cy="dwcTaxonId"]`)
      .type('Handcrafted Shoes synthesize', { force: true })
      .invoke('val')
      .should('match', new RegExp('Handcrafted Shoes synthesize'));

    cy.get(`[data-cy="dwcTaxonomicStatus"]`).type('ROI British', { force: true }).invoke('val').should('match', new RegExp('ROI British'));

    cy.get(`[data-cy="gbifTaxonId"]`)
      .type('Bermuda transmitting invoice', { force: true })
      .invoke('val')
      .should('match', new RegExp('Bermuda transmitting invoice'));

    cy.get(`[data-cy="gbifDatasetId"]`)
      .type('Table web-enabled', { force: true })
      .invoke('val')
      .should('match', new RegExp('Table web-enabled'));

    cy.get(`[data-cy="gbifTaxonomicStatus"]`).type('Rubber', { force: true }).invoke('val').should('match', new RegExp('Rubber'));

    cy.get(`[data-cy="kingdom"]`)
      .type('Gorgeous human-resource', { force: true })
      .invoke('val')
      .should('match', new RegExp('Gorgeous human-resource'));

    cy.get(`[data-cy="phylum"]`)
      .type('Licensed Dalasi Strategist', { force: true })
      .invoke('val')
      .should('match', new RegExp('Licensed Dalasi Strategist'));

    cy.get(`[data-cy="organismClass"]`).type('bypass', { force: true }).invoke('val').should('match', new RegExp('bypass'));

    cy.get(`[data-cy="order"]`)
      .type('Azerbaijan e-commerce disintermediate', { force: true })
      .invoke('val')
      .should('match', new RegExp('Azerbaijan e-commerce disintermediate'));

    cy.get(`[data-cy="family"]`).type('Technician', { force: true }).invoke('val').should('match', new RegExp('Technician'));

    cy.get(`[data-cy="genus"]`)
      .type('bandwidth JBOD Account', { force: true })
      .invoke('val')
      .should('match', new RegExp('bandwidth JBOD Account'));

    cy.get(entityCreateSaveButtonSelector).click({ force: true });
    cy.scrollTo('top', { ensureScrollable: false });
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.intercept('GET', '/api/organisms*').as('entitiesRequestAfterCreate');
    cy.visit('/');
    cy.clickOnEntityMenuItem('organism');
    cy.wait('@entitiesRequestAfterCreate');
    cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount + 1);
    cy.visit('/');
  });

  it('should delete last instance of Organism', () => {
    cy.intercept('GET', '/api/organisms*').as('entitiesRequest');
    cy.intercept('GET', '/api/organisms/*').as('dialogDeleteRequest');
    cy.intercept('DELETE', '/api/organisms/*').as('deleteEntityRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('organism');
    cy.wait('@entitiesRequest').then(({ request, response }) => {
      startingEntitiesCount = response.body.length;
      if (startingEntitiesCount > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
        cy.get(entityDeleteButtonSelector).last().click({ force: true });
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('organism').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest');
        cy.intercept('GET', '/api/organisms*').as('entitiesRequestAfterDelete');
        cy.visit('/');
        cy.clickOnEntityMenuItem('organism');
        cy.wait('@entitiesRequestAfterDelete');
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount - 1);
      }
      cy.visit('/');
    });
  });
});
