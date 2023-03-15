import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import WhonetResource from './whonet-resource';
import WhonetResourceDetail from './whonet-resource-detail';
import WhonetResourceUpdate from './whonet-resource-update';
import WhonetResourceDeleteDialog from './whonet-resource-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={WhonetResourceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={WhonetResourceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={WhonetResourceDetail} />
      <ErrorBoundaryRoute path={match.url} component={WhonetResource} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={WhonetResourceDeleteDialog} />
  </>
);

export default Routes;
