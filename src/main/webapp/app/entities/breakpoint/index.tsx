import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Breakpoint from './breakpoint';
import BreakpointDetail from './breakpoint-detail';
import BreakpointUpdate from './breakpoint-update';
import BreakpointDeleteDialog from './breakpoint-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BreakpointUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BreakpointUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BreakpointDetail} />
      <ErrorBoundaryRoute path={match.url} component={Breakpoint} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BreakpointDeleteDialog} />
  </>
);

export default Routes;
