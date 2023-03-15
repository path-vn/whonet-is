import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Breakpoint from './breakpoint';
import IntrinsicResistance from './intrinsic-resistance';
import Antibiotic from './antibiotic';
import Organism from './organism';
import ExpertInterpretationRules from './expert-interpretation-rules';
import Execute from './execute';
import WhonetResource from './whonet-resource';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}breakpoint`} component={Breakpoint} />
      <ErrorBoundaryRoute path={`${match.url}intrinsic-resistance`} component={IntrinsicResistance} />
      <ErrorBoundaryRoute path={`${match.url}antibiotic`} component={Antibiotic} />
      <ErrorBoundaryRoute path={`${match.url}organism`} component={Organism} />
      <ErrorBoundaryRoute path={`${match.url}expert-interpretation-rules`} component={ExpertInterpretationRules} />
      <ErrorBoundaryRoute path={`${match.url}execute`} component={Execute} />
      <ErrorBoundaryRoute path={`${match.url}whonet-resource`} component={WhonetResource} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
