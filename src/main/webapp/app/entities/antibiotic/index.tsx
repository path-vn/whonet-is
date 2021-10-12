import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Antibiotic from './antibiotic';
import AntibioticDetail from './antibiotic-detail';
import AntibioticUpdate from './antibiotic-update';
import AntibioticDeleteDialog from './antibiotic-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AntibioticUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AntibioticUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AntibioticDetail} />
      <ErrorBoundaryRoute path={match.url} component={Antibiotic} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AntibioticDeleteDialog} />
  </>
);

export default Routes;
