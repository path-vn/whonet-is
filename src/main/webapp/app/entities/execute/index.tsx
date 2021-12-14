import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Execute from './execute';
import ExecuteDetail from './execute-detail';
import ExecuteUpdate from './execute-update';
import ExecuteDeleteDialog from './execute-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ExecuteUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ExecuteUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ExecuteDetail} />
      <ErrorBoundaryRoute path={match.url} component={Execute} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ExecuteDeleteDialog} />
  </>
);

export default Routes;
