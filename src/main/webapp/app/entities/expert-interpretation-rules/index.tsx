import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ExpertInterpretationRules from './expert-interpretation-rules';
import ExpertInterpretationRulesDetail from './expert-interpretation-rules-detail';
import ExpertInterpretationRulesUpdate from './expert-interpretation-rules-update';
import ExpertInterpretationRulesDeleteDialog from './expert-interpretation-rules-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ExpertInterpretationRulesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ExpertInterpretationRulesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ExpertInterpretationRulesDetail} />
      <ErrorBoundaryRoute path={match.url} component={ExpertInterpretationRules} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ExpertInterpretationRulesDeleteDialog} />
  </>
);

export default Routes;
