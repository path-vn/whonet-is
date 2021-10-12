import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import IntrinsicResistance from './intrinsic-resistance';
import IntrinsicResistanceDetail from './intrinsic-resistance-detail';
import IntrinsicResistanceUpdate from './intrinsic-resistance-update';
import IntrinsicResistanceDeleteDialog from './intrinsic-resistance-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={IntrinsicResistanceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={IntrinsicResistanceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={IntrinsicResistanceDetail} />
      <ErrorBoundaryRoute path={match.url} component={IntrinsicResistance} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={IntrinsicResistanceDeleteDialog} />
  </>
);

export default Routes;
