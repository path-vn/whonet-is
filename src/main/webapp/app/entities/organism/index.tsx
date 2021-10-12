import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Organism from './organism';
import OrganismDetail from './organism-detail';
import OrganismUpdate from './organism-update';
import OrganismDeleteDialog from './organism-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={OrganismUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={OrganismUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={OrganismDetail} />
      <ErrorBoundaryRoute path={match.url} component={Organism} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={OrganismDeleteDialog} />
  </>
);

export default Routes;
