import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/breakpoint">
      <Translate contentKey="global.menu.entities.breakpoint" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/antibiotic">
      <Translate contentKey="global.menu.entities.antibiotic" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/intrinsic-resistance">
      <Translate contentKey="global.menu.entities.intrinsicResistance" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/organism">
      <Translate contentKey="global.menu.entities.organism" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/expert-interpretation-rules">
      <Translate contentKey="global.menu.entities.expertInterpretationRules" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/execute">
      <Translate contentKey="global.menu.entities.execute" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/whonet-resource">
      <Translate contentKey="global.menu.entities.whonetResource" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
